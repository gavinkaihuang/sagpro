package com.sag.sagpro.ui.carts;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.stetho.common.LogUtil;
import com.paypal.checkout.approve.Approval;
import com.paypal.checkout.approve.OnApprove;
import com.paypal.checkout.cancel.OnCancel;
import com.paypal.checkout.createorder.CreateOrder;
import com.paypal.checkout.createorder.CreateOrderActions;
import com.paypal.checkout.createorder.CurrencyCode;
import com.paypal.checkout.createorder.OrderIntent;
import com.paypal.checkout.createorder.UserAction;
import com.paypal.checkout.error.ErrorInfo;
import com.paypal.checkout.error.OnError;
import com.paypal.checkout.order.Amount;
import com.paypal.checkout.order.AppContext;
import com.paypal.checkout.order.CaptureOrderResult;
import com.paypal.checkout.order.OnCaptureComplete;
import com.paypal.checkout.order.Order;
import com.paypal.checkout.order.PurchaseUnit;
import com.paypal.checkout.shipping.OnShippingChange;
import com.paypal.checkout.shipping.ShippingChangeActions;
import com.paypal.checkout.shipping.ShippingChangeData;
import com.sag.sagpro.ConstantData;
import com.sag.sagpro.R;
import com.sag.sagpro.databinding.FragmentCartItemBinding;
import com.sag.sagpro.databinding.FragmentCartItemListBinding;
import com.sag.sagpro.ui.InnerBaseFragment;
import com.sag.sagpro.ui.messages.placeholder.MessagePlaceholderContent;
import com.sag.sagpro.ui.messages.placeholder.MessagePlaceholderItem;
import com.sag.sagpro.ui.products.MyProductItemRecyclerViewAdapter;
import com.sag.sagpro.ui.products.ProductListFragment;
import com.sag.sagpro.ui.products.placeholder.ProductPlaceholderContent;
import com.sag.sagpro.utils.AndroidNetworkingUtils;
import com.sag.sagpro.utils.LogUtils;
import com.sag.sagpro.utils.LoggedInUserHelper;
import com.sag.sagpro.utils.RX2AndroidNetworkingUtils;
import com.sag.sagpro.utils.UIUtils;
import com.sag.sagpro.utils.URLLoadCallback;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 */
public class CartListFragment extends InnerBaseFragment {

    FragmentCartItemListBinding binding = null;
    MyCartListRecyclerViewAdapter adapter = null;
    private CartPlaceholderContent placeholderContent = null;

    private ActivityResultLauncher<Intent> launcher;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CartListFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static CartListFragment newInstance(int columnCount) {
        CartListFragment fragment = new CartListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        initPayPalConfig();

        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == Activity.RESULT_OK) {
                Intent data = result.getData();
                // 处理返回的数据
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCartItemListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new MyCartListRecyclerViewAdapter(getPlaceholderContentInstant().ITEMS);
        binding.list.setAdapter(adapter);
//        binding.list.addItemDecoration(UIUtils.getDividerItemLineDecoration(getContext()));

//        binding.payPalButton.setup(
//
//        );
        binding.payPalButton.setup(new CreateOrder() {
                                       @Override
                                       public void create(@NotNull CreateOrderActions createOrderActions) {
                                           ArrayList<PurchaseUnit> purchaseUnits = new ArrayList<>();
                                           purchaseUnits.add(
                                                   new PurchaseUnit.Builder()
                                                           .amount(
                                                                   new Amount.Builder()
                                                                           .currencyCode(CurrencyCode.USD)
                                                                           .value("10.00")
                                                                           .build()
                                                           )
                                                           .build()
                                           );
                                           Order order = new Order(
                                                   OrderIntent.CAPTURE,
                                                   new AppContext.Builder()
                                                           .userAction(UserAction.PAY_NOW)
                                                           .build(),
                                                   purchaseUnits
                                           );
                                           createOrderActions.create(order, (CreateOrderActions.OnOrderCreated) null);
                                       }
                                   },
                new OnApprove() {
                    @Override
                    public void onApprove(@NotNull Approval approval) {
                        approval.getOrderActions().capture(new OnCaptureComplete() {
                            @Override
                            public void onCaptureComplete(@NotNull CaptureOrderResult result) {
                                Log.i("CaptureOrder", String.format("CaptureOrderResult: %s", result));
                            }
                        });
                    }
                },
                new OnShippingChange() {
                    @Override
                    public void onShippingChanged(@NonNull ShippingChangeData shippingChangeData, @NonNull ShippingChangeActions shippingChangeActions) {

                    }
                },
                new OnCancel() {
                    @Override
                    public void onCancel() {
                        Log.d("onCancel", "Buyer cancelled the paypal experience.");
                    }
                },
                new OnError() {
                    @Override
                    public void onError(@NonNull ErrorInfo errorInfo) {
                        Log.d("OnError", errorInfo.toString());
                    }
                });
//        binding.payPalButton.setup(
//                new CreateOrder() {
//                    @Override
//                    public void create(@NotNull CreateOrderActions createOrderActions) {
//                        Log.i("CaptureOrder", "create: ");
//                    }
//                },
//                new OnApprove() {
//                    @Override
//                    public void onApprove(@NotNull Approval approval) {
//                        Log.i("CaptureOrder", "getOrderId: " + approval.getData().getOrderId());
//                    }
//                }
////                ,
////                new OnCancel() {
////                    @Override
////                    public void onCancel() {
////                        Log.d("CaptureOrder", "Buyer cancelled the PayPal experience.");
////                    }
////                },
////                new OnError() {
////                    @Override
////                    public void onError(@NotNull ErrorInfo errorInfo) {
////                        Log.d("CaptureOrder", String.format("Error: %s", errorInfo));
////                    }
////                }
//        );

//        binding.payButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                https://www.oodlestechnologies.com/blogs/integrating-paypal-payments-in-an-android-application/
//
////                //生成付款请求
////                PayPalPayment payment = new PayPalPayment(new BigDecimal("0.01"), "USD", "Test Payment",
////                        PayPalPayment.PAYMENT_INTENT_SALE);
////
////                //启动 PayPal 付款页面
////                Intent intent = new Intent(getActivity(), PaymentActivity.class);
////                intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
////                intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
//////                startActivityForResult(intent, 11);
////                launcher.launch(intent);
//
////                Order order = new Order(
////                        PayPalCheckoutConstant.PAYPAL_ORDER_AMOUNT,    // 订单金额
////                        PayPalCheckoutConstant.PAYPAL_ORDER_CURRENCY,  // 货币类型
////                        PayPalCheckoutConstant.PAYPAL_ORDER_INTENT,    // 支付意图
////                        PayPalCheckoutConstant.PAYPAL_ORDER_DESCRIPTION // 订单描述
////                );
//
//            }
//        });
    }

//    https://developer.paypal.com/limited-release/paypal-mobile-checkout/android/
//    private void initPayPalConfig() {
//        config = new PayPalConfiguration()
//                .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX) //设置 PayPal 环境为沙盒测试环境
//                .clientId("AQJMGOlCTW_q7wm1kNwyeUrmvBlYUsemPCZ2HzHCsteNq5CepXJnqGC1U5IKncmE3ieufmbswgQkfOjh") //设置 PayPal 客户端 ID
//                .merchantName("Sample Paypal")
//                .merchantPrivacyPolicyUri(Uri.parse("https://www.example.com/privacy"))
//                .merchantUserAgreementUri(Uri.parse("https://www.example.com/legal"));
////                .merchantName("SAGPro") //设置商家名称
////                .merchantPrivacyPolicyUri(Uri.parse("YOUR_MERCHANT_PRIVACY_POLICY_URI")) //设置隐私政策 URL
////                .merchantUserAgreementUri(Uri.parse("YOUR_MERCHANT_USER_AGREEMENT_URI")) //设置用户协议 URL
////                .acceptCreditCards(false) //设置是否接受信用卡支付
////                .languageOrLocale("en_US") //设置语言或区域
////                .rememberUser(false); //设置是否记住用户
//
//        //创建 PayPalService 对象
//        Intent intent = new Intent(getActivity(), PayPalService.class);
//        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
//        getActivity().startService(intent);
//    }

    @Override
    public void onDestroy() {
//        getActivity().stopService(new Intent(getActivity(), PayPalService.class));
        super.onDestroy();
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//    }

    //    @Override
//    public void successURLLoadedCallBack(JSONObject result) {
//        handleResult(result);
//    }
//
//    @Override
//    public Exception failueURLLoadedCallBack(Exception exception) {
//        return null;
//    }


    public CartPlaceholderContent getPlaceholderContentInstant() {
        if (placeholderContent == null)
            placeholderContent = new CartPlaceholderContent();
        return placeholderContent;
    }


    /**
     * Step 1
     */
    protected void postRequest() {
        super.postRequest();
        postRequestForCartList();
    }

//    private void loadDataFromServer() {
//        //{"language":"en","app":"ios","version":"1.0.0","token":"$Pe!nmRFNhbfUdg9VD5CJWjZMls%uSoO"}
//
//        try {
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("language", "en");
//            jsonObject.put("app", "android");
//            jsonObject.put("version", "1.0.0");
//            jsonObject.put("token", LoggedInUserHelper.getToken(getActivity()));
//            AndroidNetworkingUtils.loadURL(ConstantData.CART_LIST, "CART_LIST", jsonObject, this);
//        } catch (JSONException e) {
//            LogUtil.e("-----------" + e.getMessage());
//        }
//    }

    private void postRequestForCartList() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("language", "en");
            jsonObject.put("app", "android");
            jsonObject.put("version", "1.0.0");
            jsonObject.put("token", LoggedInUserHelper.getToken(getActivity()));
            RX2AndroidNetworkingUtils.postForData(ConstantData.CART_LIST, jsonObject, this);
        } catch (JSONException e) {
            LogUtil.e("-----------" + e.getMessage());
        }
    }

    /**
     * Step 2
     * Handle Data Result,
     * RX2AndroidNetworkingUtils will call back in UI thread
     *
     * @param result
     */
    protected void handleResultForUI(final JSONObject result) {
        super.handleResultForUI(result);
        handleResult(result);
    }

    private void handleResult(JSONObject result) {
        try {
            getPlaceholderContentInstant().clearItems();

            String code = result.getString(ConstantData.CODE);
            if (code.equalsIgnoreCase(ConstantData.CODE_SUCCESS)) {
                JSONObject jsonObject = result.getJSONObject(ConstantData.DATA);
//                JSONArray jsonArray = result.getJSONArray(ConstantData.DATA);
                String totalPrice = jsonObject.getString("total");
                JSONArray jsonArray = jsonObject.getJSONArray("list");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jdata = jsonArray.getJSONObject(i);
                    CartPlaceholderItem placeholderItem = new CartPlaceholderItem();
                    placeholderItem.setCid(jdata.getString("cid"));
                    placeholderItem.setPid(jdata.getString("pid"));
                    placeholderItem.setFid(jdata.getString("fid"));
                    placeholderItem.setName(jdata.getString("name"));
                    placeholderItem.setPrice(jdata.getString("price"));
                    placeholderItem.setNumber(jdata.getString("number"));
                    placeholderItem.setImg(jdata.getString("img"));
                    getPlaceholderContentInstant().addItem(placeholderItem);
                }

                getPlaceholderContentInstant().setTotalPrice(totalPrice);
            } else {
                String message = result.getString(ConstantData.MSG);
                LogUtil.e("------------------" + message);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //update userinterface
        adapter.setItems(getPlaceholderContentInstant().ITEMS);
        adapter.notifyDataSetChanged();
        updatePageFooterHeight(binding.list);
        updatePrice();
    }

    private void updatePrice() {
//        priceTextView
        binding.priceTextView.setText(getPlaceholderContentInstant().getTotalPrice());
    }
}