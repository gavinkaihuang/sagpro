package com.sag.sagpro.ui.products;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.stetho.common.LogUtil;
import com.sag.sagpro.ConstantData;
import com.sag.sagpro.R;
import com.sag.sagpro.databinding.FragmentProductDetailBinding;
import com.sag.sagpro.databinding.FragmentProductItemListBinding;
import com.sag.sagpro.ui.InnerBaseFragment;
import com.sag.sagpro.ui.home.HomeItemFragment;
import com.sag.sagpro.ui.products.placeholder.ProductDetailPlaceholder;
import com.sag.sagpro.ui.products.placeholder.ProductPlaceholderContent;
import com.sag.sagpro.ui.products.placeholder.ProductPlaceholderItem;
import com.sag.sagpro.utils.AndroidNetworkingUtils;
import com.sag.sagpro.utils.LoggedInUserHelper;
import com.sag.sagpro.utils.RX2AndroidNetworkingUtils;
import com.sag.sagpro.utils.ToastUtils;
import com.sag.sagpro.utils.URLLoadCallback;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.util.LogUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProductDetailFragment extends InnerBaseFragment {

    public static final String PARAMS_PRODUCT_ID = "PARAMS_PRODUCT_ID";
    private ProductDetailViewModel mViewModel;
    private String productID = null;
    private ProductDetailPlaceholder placeholderItem = null;
    ProductDetailViewModel productDetailViewModel = null;
    FragmentProductDetailBinding binding = null;

    public static ProductDetailFragment newInstance(String productID) {
        ProductDetailFragment fragment = new ProductDetailFragment();
        Bundle args = new Bundle();
        args.putString(PARAMS_PRODUCT_ID, productID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (getArguments() != null) {
            productID = getArguments().getString(PARAMS_PRODUCT_ID);
        }

        productDetailViewModel = new ViewModelProvider(this).get(ProductDetailViewModel.class);
        binding = FragmentProductDetailBinding.inflate(inflater, container, false);

        binding.addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                addToCartToServer();
                postRequestForAddCart();
            }
        });

//        loadDataFromServer();
        return binding.getRoot();
    }



    /**
     * ???????????????????????????????????????
     */
    private void onBindingViews() {
        getActivity().runOnUiThread(() -> {

            binding.priceTextView.setText("$" + productDetailViewModel.getPrice());
            binding.contentTextView.setText(productDetailViewModel.getContent());

            ArrayList<String> arrayList = productDetailViewModel.getImgList();
            binding.viewBanner.setAdapter(new BannerImageAdapter<String>(arrayList) {
                @Override
                public void onBindView(BannerImageHolder holder, String data, int position, int size) {
                    Glide.with(holder.imageView)
                            .load(data)
                            .into(holder.imageView);
                }
            });
            //????????????????????????
            binding.viewBanner.setIndicator(new CircleIndicator(ProductDetailFragment.this.getContext()));
            binding.viewBanner.setIndicatorRadius(50);

            updatePageFooterHeight(binding.getRoot());

            getActivity().setTitle(productDetailViewModel.getName());
        });
    }



//    /**
//     * Load data from server, than handle it
//     */
//    public void successURLLoadedCallBack(JSONObject result) {
//        try {
//            String code = result.getString(ConstantData.CODE);
//            if (code.equalsIgnoreCase(ConstantData.CODE_SUCCESS)) {
//                String service = result.getString(ConstantData.SERVICE);
//                if ("addCart".equalsIgnoreCase(service)) {
//                    handleAddToCartResult(result);
//                } else if ("productDetail".equalsIgnoreCase(service)){
//                    handleDetailResult(result);
//                }
//           } else {
//                String message = result.getString(ConstantData.MSG);
//                LogUtil.e("------------------" + message);
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    public Exception failueURLLoadedCallBack(Exception exception) {
//        return null;
//    }




    /*
     * request data from server start
     */

    protected void postRequest() {
        super.postRequest();
        postRequestForDetails();
    }

    private void postRequestForDetails() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("productid", productID);
//            jsonObject.put("token", LoggedInUserHelper.getToken(getActivity()));
            RX2AndroidNetworkingUtils.postForData(ConstantData.PRODUCTS_DETAIL, jsonObject, this);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void postRequestForAddCart() {
        try {
            JSONObject paramsObject = new JSONObject();
            paramsObject.put("productid", productID);
            paramsObject.put("price", "100.0");
            paramsObject.put("number", binding.itemNoAdjustView.getValue());
            paramsObject.put("token", LoggedInUserHelper.getToken(getActivity()));
            RX2AndroidNetworkingUtils.postForData(ConstantData.ADD_TO_CART, paramsObject, this);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
//
//    public JSONObject generateParams() {
//        try {
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("productid", productID);
//            jsonObject.put("token", LoggedInUserHelper.getToken(getActivity()));
//            return jsonObject;
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

//    public JSONObject generateAddToCartParams() {
//        try {
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("productid", productID);
//            jsonObject.put("price", "100.0");
//            jsonObject.put("number", binding.itemNoAdjustView.getValue());
//            jsonObject.put("token", LoggedInUserHelper.getToken(getActivity()));
//            return jsonObject;
//        } catch (JSONException e) {
//            LogUtil.e("-----------" + e.getMessage());
//        }
//        return null;
//    }


//    private void addToCartToServer() {
//        //{"productid":"1001","price": 100.00,"number":"10","token":"$Pe!nmRFNhbfUdg9VD5CJWjZMls%uSoO"}
//
////        String number = binding.itemNoAdjustView.getValue();
//        try {
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("productid", productID);
//            jsonObject.put("price", "100.0");
//            jsonObject.put("number", binding.itemNoAdjustView.getValue());
//            jsonObject.put("token", LoggedInUserHelper.getToken(getActivity()));
//            AndroidNetworkingUtils.loadURL(ConstantData.ADD_TO_CART, "ADD_TO_CART", jsonObject, this);
//        } catch (JSONException e) {
//            LogUtil.e("-----------" + e.getMessage());
//        }
//    }
//
//    private void loadDataFromServer() {
//        if (productID == null) return;
//
//        try {
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("productid", productID);
//            AndroidNetworkingUtils.loadURL(ConstantData.PRODUCTS_DETAIL, "PRODUCTS_DETAIL", jsonObject, this);
//        } catch (JSONException e) {
//            LogUtil.e("-----------" + e.getMessage());
//        }
//    }


    /**
     * Step 2
     * Handle Data Result,
     * RX2AndroidNetworkingUtils will call back in UI thread
     * @param result
     */
    protected void handleResultForUI(JSONObject result) {
        super.handleResultForUI(result);
        try {
            String code = result.getString(ConstantData.CODE);
            if (code.equalsIgnoreCase(ConstantData.CODE_SUCCESS)) {
                String service = result.getString(ConstantData.SERVICE);
                if ("addCart".equalsIgnoreCase(service)) {
                    handleAddToCartResult(result);
                } else if ("productDetail".equalsIgnoreCase(service)){
                    handleDetailResult(result);
                }
           } else {
                String message = result.getString(ConstantData.MSG);
                LogUtil.e("------------------" + message);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     *  ??????????????????
     * @param result
     */
    private void handleDetailResult(JSONObject result) {
        try {
            JSONObject jsonObject = result.getJSONObject(ConstantData.DATA);
            productDetailViewModel.setName(jsonObject.getString("name"));
            productDetailViewModel.setPid(jsonObject.getString("pid"));
            productDetailViewModel.setCid(jsonObject.getString("cid"));
            productDetailViewModel.setFid(jsonObject.getString("fid"));
            productDetailViewModel.setContent(jsonObject.getString("content"));
            productDetailViewModel.setPrice(jsonObject.getString("price"));

            ArrayList<String> imageList = new ArrayList<String>();
            JSONArray imageArray = jsonObject.getJSONArray("imgs");
            for (int i = 0; i < imageArray.length(); i++) {
                imageList.add(imageArray.getString(i));
            }
            productDetailViewModel.setImgList(imageList);
            onBindingViews();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * ????????????????????????
     * @param result
     */
    private void handleAddToCartResult(JSONObject result) {
        try {
            String msg = result.getString("msg");
            JSONObject jsonObject = result.getJSONObject(ConstantData.DATA);
            String productID = jsonObject.getString("productid");
            String price = jsonObject.getString("price");
            String number = jsonObject.getString("number");
            String uid = jsonObject.getString("uid");
            String created = jsonObject.getString("created");
            getActivity().runOnUiThread(() -> {
                ToastUtils.showToast(getActivity().getApplicationContext(), msg);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}