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
import com.sag.sagpro.databinding.FragmentProductItemListBinding;
import com.sag.sagpro.placeholder.PlaceholderContent;
import com.sag.sagpro.ui.InnerBaseFragment;
import com.sag.sagpro.ui.products.MyProductItemRecyclerViewAdapter;
import com.sag.sagpro.ui.products.ProductListFragment;
import com.sag.sagpro.ui.products.placeholder.ProductPlaceholderContent;
import com.sag.sagpro.utils.AndroidNetworkingUtils;
import com.sag.sagpro.utils.LoggedInUserHelper;
import com.sag.sagpro.utils.URLLoadCallback;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A fragment representing a list of Items.
 */
public class CartListFragment extends InnerBaseFragment implements URLLoadCallback  {

    FragmentCartItemListBinding binding = null;
    MyCartListRecyclerViewAdapter adapter = null;
    CartPlaceholderContent placeholderContent = null;
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
//        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        placeholderContent = new CartPlaceholderContent();

        loadDataFromServer();
        LogUtil.i("--------" + this.hashCode());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCartItemListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new MyCartListRecyclerViewAdapter(placeholderContent.ITEMS);
        binding.list.setAdapter(adapter);

    }

    private void loadDataFromServer() {
        //{"language":"en","app":"ios","version":"1.0.0","token":"$Pe!nmRFNhbfUdg9VD5CJWjZMls%uSoO"}

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("language", "en");
            jsonObject.put("app", "android");
            jsonObject.put("version", "1.0.0");
            jsonObject.put("token", LoggedInUserHelper.getToken(getActivity()));
            AndroidNetworkingUtils.loadURL(ConstantData.CART_LIST, "CART_LIST", jsonObject, this);
        } catch (JSONException e) {
            LogUtil.e("-----------" + e.getMessage());
        }
    }

    @Override
    public void successURLLoadedCallBack(JSONObject result) {

    }

    @Override
    public Exception failueURLLoadedCallBack(Exception exception) {
        return null;
    }
}