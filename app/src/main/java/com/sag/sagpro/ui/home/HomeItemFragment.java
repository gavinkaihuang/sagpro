package com.sag.sagpro.ui.home;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.facebook.stetho.common.LogUtil;
import com.sag.sagpro.ConstantData;
import com.sag.sagpro.R;
import com.sag.sagpro.databinding.FragmentHomeItemListBinding;
import com.sag.sagpro.ui.InnerBaseFragment;
import com.sag.sagpro.ui.home.placeholder.HomeItemPlaceholderContent;
import com.sag.sagpro.ui.home.placeholder.HomeItemPlaceholderItem;
import com.sag.sagpro.ui.products.ProductDetailFragment;
import com.sag.sagpro.ui.products.ProductListFragment;
import com.sag.sagpro.ui.products.placeholder.ProductPlaceholderItem;
import com.sag.sagpro.utils.AndroidNetworkingUtils;
import com.sag.sagpro.utils.ImageLoadCallback;
import com.sag.sagpro.utils.RecyclerItemClickListener;
import com.sag.sagpro.utils.UIUtils;
import com.sag.sagpro.utils.URLLoadCallback;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 */
public class HomeItemFragment extends InnerBaseFragment {


    MyItemRecyclerViewAdapter myItemRecyclerViewAdapter = null;
    private HomeItemPlaceholderContent placeholderContent = null;
    FragmentHomeItemListBinding binding = null;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public HomeItemFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeItemListBinding.inflate(inflater, container, false);

        placeholderContent = new HomeItemPlaceholderContent();
        myItemRecyclerViewAdapter = new MyItemRecyclerViewAdapter(placeholderContent.ITEMS);
        myItemRecyclerViewAdapter.setIsShowHeader(true);//添加Header
        myItemRecyclerViewAdapter.setOnItemClickListener(myItemRecyclerViewAdapter);
        binding.list.setAdapter(myItemRecyclerViewAdapter);
        binding.list.addItemDecoration(UIUtils.getDividerItemBoxDecoration(getContext()));

        JSONObject jsonObject = new JSONObject();
        AndroidNetworkingUtils.loadURL(ConstantData.CATEGORIES, "CATEGORIES", new JSONObject(), new LoadUrlHandler());
        AndroidNetworkingUtils.loadURL(ConstantData.HOME_IMGS, "HOME_IMGS", jsonObject, new LoadUrlHandler());

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


    /**
     * handle list data from server
     *
     * @param result
     */
    private void handleCategoryResult(JSONObject result) {
        try {

            JSONArray jsonArray = result.getJSONArray(ConstantData.DATA);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jdata = jsonArray.getJSONObject(i);
                HomeItemPlaceholderItem placeholderItem = new HomeItemPlaceholderItem();
                placeholderItem.setCid(jdata.getString("cid"));
                placeholderItem.setName(jdata.getString("name"));
                placeholderItem.setDescription(jdata.getString("description"));
                placeholderItem.setImg(jdata.getString("img"));

                if (placeholderContent == null)
                    placeholderContent = new HomeItemPlaceholderContent();
                placeholderContent.addItem(placeholderItem);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //update userinterface
        myItemRecyclerViewAdapter.setItems(placeholderContent.ITEMS);
        getActivity().runOnUiThread(() -> {
            myItemRecyclerViewAdapter.notifyDataSetChanged();
            updatePageFooterHeight(binding.list);
        });
    }

    private void handleHomeImgsResult(JSONObject result) {
        try {

            JSONArray jsonArray = result.getJSONArray(ConstantData.DATA);
            ArrayList<String> imgList = new ArrayList<String>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jdata = jsonArray.getJSONObject(i);
                String cid = jdata.getString("cid");
                String name = jdata.getString("name");
                String img = jdata.getString("img");
                LoopImageBean loopImageBean = new LoopImageBean(cid, name,  img);
                placeholderContent.addImageBean(loopImageBean);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        getActivity().runOnUiThread(() -> {
            myItemRecyclerViewAdapter.setmImageBeans(placeholderContent.LOOP_IMAGES);
//            updateLoopImages();
        });
    }


    class LoadUrlHandler implements URLLoadCallback {
        public void successURLLoadedCallBack(JSONObject result) {
            try {
                String code = result.getString(ConstantData.CODE);
                //TODO service not work
                if (!code.equalsIgnoreCase(ConstantData.CODE_SUCCESS)) {
                    String message = result.getString(ConstantData.MSG);
                    LogUtil.e("------------------" + message);
                    return;
                }

                String service = result.getString(ConstantData.SERVICE);
                if ("homeImgs".equals(service)) {
                    handleHomeImgsResult(result);
                } else {
                    handleCategoryResult(result);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        public Exception failueURLLoadedCallBack(Exception exception) {
            return exception;
        }
    }

}