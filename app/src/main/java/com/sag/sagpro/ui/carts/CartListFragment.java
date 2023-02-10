package com.sag.sagpro.ui.carts;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.stetho.common.LogUtil;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A fragment representing a list of Items.
 */
public class CartListFragment extends InnerBaseFragment {

    FragmentCartItemListBinding binding = null;
    MyCartListRecyclerViewAdapter adapter = null;
    private CartPlaceholderContent placeholderContent = null;

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
        binding.list.addItemDecoration(UIUtils.getDividerItemLineDecoration(getContext()));
    }


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
            String code = result.getString(ConstantData.CODE);
            if (code.equalsIgnoreCase(ConstantData.CODE_SUCCESS)) {
                JSONArray jsonArray = result.getJSONArray(ConstantData.DATA);
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
    }
}