package com.sag.sagpro.ui.products;

import android.content.Intent;
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
import com.sag.sagpro.activities.ProductDetailsActivity;
import com.sag.sagpro.activities.ProductListActivity;
import com.sag.sagpro.databinding.FragmentProductItemListBinding;
import com.sag.sagpro.ui.InnerBaseFragment;
import com.sag.sagpro.ui.products.placeholder.ProductPlaceholderContent;
import com.sag.sagpro.ui.products.placeholder.ProductPlaceholderItem;
import com.sag.sagpro.utils.AndroidNetworkingUtils;
import com.sag.sagpro.utils.ImageLoadCallback;
import com.sag.sagpro.utils.LogUtils;
import com.sag.sagpro.utils.ParamsUtils;
import com.sag.sagpro.utils.RX2AndroidNetworkingUtils;
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
    public static String PARAMS_CNAME = "PARAMS_CNAME";
    private String cid = null;
    private String cname = null;
    private int totalResultsNo = 0;

    MyProductItemRecyclerViewAdapter myProductItemRecyclerViewAdapter = null;
    private ProductPlaceholderContent placeholderContent = null;
    private FragmentProductItemListBinding binding = null;

    public ProductListFragment() {
    }

    public static ProductListFragment newInstance(String cid, String cname) {
        ProductListFragment fragment = new ProductListFragment();
        Bundle args = new Bundle();
        args.putString(PARAMS_CID, cid);
        args.putString(PARAMS_CNAME, cname);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            cid = getArguments().getString(PARAMS_CID);
            cname = getArguments().getString(PARAMS_CNAME);
        }
        if (cname != null) {
            getActivity().setTitle("Product Type: " + cname);
//            loadDataFromServer(0);/
            //TODO product type
        } else {
            getActivity().setTitle("Product Type: All");
        }

        placeholderContent = new ProductPlaceholderContent();
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
        binding.list.addItemDecoration(UIUtils.getDividerItemLineDecoration(getContext()));
        binding.list.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), binding.list, new RecyclerItemClickListener.OnItemClickListener() {

                    @Override
                    public void onItemClick(View view, int position) {
                        ProductPlaceholderItem itemClicked = placeholderContent.getItem(position);
                        Bundle bundle = new Bundle();
                        bundle.putString(ProductDetailFragment.PARAMS_PRODUCT_ID, itemClicked.pid);
                       //Navigation.findNavController(view).navigate(R.id.item_navigation_product_detail, bundle
                       Intent intent = new Intent();
                       intent.putExtras(bundle);
                       intent.setClass(view.getContext(), ProductDetailsActivity.class);
                       view.getContext().startActivity(intent);
                    }


            @Override
            public void onItemLongClick(View view, int position) {
                LogUtils.i("item " + position + " long clicked");
            }
        }));
    }


    class LoadImageHandler implements ImageLoadCallback {
        public Bitmap loadImageSucceed(Bitmap bitmap) {
            return bitmap;
        }

        public Exception loadImageFailed(Exception exception) {
            return exception;
        }
    }

    /**
     * -----------------------------------------------------------------------------------
     */

    /*
     * request data from server start
     */

    /**
     * Step 1
     */
    protected void postRequest() {
        super.postRequest();
        postRequestForDetails(0);
    }

    private void postRequestForDetails(int startNo) {
//        if (cid == null || "".equals(cid))
//            return;

        try {
//            JSONObject jsonObject = new JSONObject();
            JSONObject jsonObject = ParamsUtils.getRequestParamsRoot(getContext());
            jsonObject.put("cid", cid);
            jsonObject.put("start", "" + startNo);
//            AndroidNetworkingUtils.loadURL(ConstantData.PRODUCTS_LIST, "PRODUCTS_LIST", jsonObject, new LoadUrlHandler());
            RX2AndroidNetworkingUtils.postForData(ConstantData.PRODUCTS_LIST, jsonObject, this);
        } catch (JSONException e) {
            LogUtils.e(e.getMessage());
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


    /**
     * handle list data from server
     *
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
                    placeholderItem.setPrice(jdata.getString("price"));
                    placeholderItem.setRemark(jdata.getString("remark"));
//
                    if (placeholderContent == null)
                        placeholderContent = new ProductPlaceholderContent();
                    placeholderContent.addItem(placeholderItem);
                }
            } else {
                String message = result.getString(ConstantData.MSG);
                LogUtils.e(message);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //update userinterface
        myProductItemRecyclerViewAdapter.setItems(placeholderContent.ITEMS);
        myProductItemRecyclerViewAdapter.notifyDataSetChanged();
        updatePageFooterHeight(binding.list);
    }


}