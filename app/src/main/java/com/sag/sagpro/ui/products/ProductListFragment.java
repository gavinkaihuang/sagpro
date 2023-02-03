package com.sag.sagpro.ui.products;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.stetho.common.LogUtil;
import com.sag.sagpro.ConstantData;
import com.sag.sagpro.R;
import com.sag.sagpro.databinding.FragmentProductItemListBinding;
import com.sag.sagpro.ui.InnerBaseFragment;
import com.sag.sagpro.ui.products.placeholder.ProductPlaceholderContent;
import com.sag.sagpro.ui.products.placeholder.ProductPlaceholderItem;
import com.sag.sagpro.utils.AndroidNetworkingUtils;
import com.sag.sagpro.utils.ImageLoadCallback;
import com.sag.sagpro.utils.RecyclerItemClickListener;
import com.sag.sagpro.utils.UIUtils;
import com.sag.sagpro.utils.URLLoadCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A fragment representing a list of Items.
 */
public class ProductListFragment extends InnerBaseFragment {

    public static String PARAMS_CID = "PARAMS_CID";
    private int cid = 0;
    private int totalResultsNo = 0;

    MyProductItemRecyclerViewAdapter myProductItemRecyclerViewAdapter = null;
    private ProductPlaceholderContent placeholderContent = null;
    private FragmentProductItemListBinding binding = null;

    public ProductListFragment() {
    }

    @SuppressWarnings("unused")
    public static ProductListFragment newInstance(int cid) {
        ProductListFragment fragment = new ProductListFragment();
        Bundle args = new Bundle();
        args.putInt(PARAMS_CID, cid);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            cid = getArguments().getInt(PARAMS_CID);
        }
        placeholderContent = new ProductPlaceholderContent();
        loadDataFromServer(0);
    }

    private void loadDataFromServer(int startNo) {
        if (cid < 0)
            return;

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("cid", "" + cid);
            jsonObject.put("start", "" + startNo);
            AndroidNetworkingUtils.loadURL(ConstantData.PRODUCTS_LIST, "PRODUCTS_LIST", jsonObject, new LoadUrlHandler());
        } catch (JSONException e) {
            LogUtil.e("-----------" + e.getMessage());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProductItemListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        myProductItemRecyclerViewAdapter = new MyProductItemRecyclerViewAdapter(placeholderContent.ITEMS);
        binding.list.setAdapter(myProductItemRecyclerViewAdapter);
        binding.list.addItemDecoration(UIUtils.getDividerItemDecoration(getContext()));

        binding.list.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), binding.list, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
//                        ToastUtil.showMessage(context, "position = " + position);
//                        LogUtil.i("----------item " + position + " clicked");
                        ProductPlaceholderItem itemClicked = placeholderContent.getItem(position);
                        Bundle bundle = new Bundle();
                        bundle.putString(ProductDetailFragment.PARAMS_PRODUCT_ID, itemClicked.pid);
                        Navigation.findNavController(view).navigate(R.id.item_navigation_product_detail, bundle);
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {
//                        ToastUtil.showMessage(context, "long position = " + position);
                        LogUtil.i("----------item " + position + " long clicked");
                    }
                }));
   }



    /**
     * handle list data from server
     * @param result
     */
    private void handleResult(JSONObject result) {
        try {
            String code = result.getString(ConstantData.CODE);
            if (code.equalsIgnoreCase(ConstantData.CODE_SUCCESS)) {
                totalResultsNo = Integer.parseInt(result.getString("total"));
                JSONArray jsonArray = result.getJSONArray(ConstantData.DATA);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jdata = jsonArray.getJSONObject(i);
                    ProductPlaceholderItem placeholderItem = new ProductPlaceholderItem();
                    placeholderItem.setPid(jdata.getString("pid"));
                    placeholderItem.setCid(jdata.getString("cid"));
                    placeholderItem.setName(jdata.getString("name"));
                    placeholderItem.setFid(jdata.getString("fid"));
                    placeholderItem.setImg(jdata.getString("img"));
//
                    if (placeholderContent == null)
                        placeholderContent = new ProductPlaceholderContent();
                    placeholderContent.addItem(placeholderItem);
                }
            } else {
                String message = result.getString(ConstantData.MSG);
                LogUtil.e("------------------" + message);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //update userinterface
        myProductItemRecyclerViewAdapter.setItems(placeholderContent.ITEMS);
        getActivity().runOnUiThread(() -> {
//            LogUtil.i("----------product adapter ask ui reflash");
            myProductItemRecyclerViewAdapter.notifyDataSetChanged();
            updatePageFooterHeight(binding.list);
        });
    }


    class LoadUrlHandler implements URLLoadCallback {
        public void successURLLoadedCallBack(JSONObject result) {
            handleResult(result);
        }
        public Exception failueURLLoadedCallBack(Exception exception) {
            return exception;
        }
    }

    class LoadImageHandler implements ImageLoadCallback {
        public Bitmap loadImageSucceed(Bitmap bitmap) {
            return bitmap;
        }
        public Exception loadImageFailed(Exception exception) {
            return exception;
        }
    }
}