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
import com.sag.sagpro.utils.LoggedInUserHelper;
import com.sag.sagpro.utils.RX2AndroidNetworkingUtils;
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
        postRequest();
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

//        JSONObject jsonObject = new JSONObject();
//        AndroidNetworkingUtils.loadURL(ConstantData.CATEGORIES, "CATEGORIES", new JSONObject(), new LoadUrlHandler());
//        AndroidNetworkingUtils.loadURL(ConstantData.HOME_IMGS, "HOME_IMGS", jsonObject, new LoadUrlHandler());
        //        //List item click listener
        binding.list.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), binding.list, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (position == 0) {
                    LogUtil.i("----------head view clicked, ignore it");
                    return;
                }

                HomeItemPlaceholderItem itemClicked = placeholderContent.getItem(position);
                Bundle bundle = new Bundle();
                bundle.putString(ProductListFragment.PARAMS_CID, itemClicked.cid);
                LogUtil.i("----------item " + position + " clicked: redirect to category: " + itemClicked.cid + ">>" + itemClicked.name);
                Navigation.findNavController(view).navigate(R.id.item_navigation_products, bundle);
            }

            @Override
            public void onItemLongClick(View view, int position) {
                LogUtil.i("----------item " + position + " long clicked");
            }
        }));

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }




//    class LoadUrlHandler implements URLLoadCallback {
//        public void successURLLoadedCallBack(JSONObject result) {
//            try {
//                String code = result.getString(ConstantData.CODE);
//                //TODO service not work
//                if (!code.equalsIgnoreCase(ConstantData.CODE_SUCCESS)) {
//                    String message = result.getString(ConstantData.MSG);
//                    LogUtil.e("------------------" + message);
//                    return;
//                }
//
//                String service = result.getString(ConstantData.SERVICE);
//                if ("homeImgs".equals(service)) {
//                    handleHomeImgsResult(result);
//                } else {
//                    handleCategoryResult(result);
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//        }
//
//        public Exception failueURLLoadedCallBack(Exception exception) {
//            return exception;
//        }
//    }


    /*
     * request data from server start
     */

//    JSONObject jsonObject = new JSONObject();
//        AndroidNetworkingUtils.loadURL(ConstantData.CATEGORIES, "CATEGORIES", new JSONObject(), new LoadUrlHandler());
//        AndroidNetworkingUtils.loadURL(ConstantData.HOME_IMGS, "HOME_IMGS", jsonObject, new LoadUrlHandler());

    public void postRequest() {
        postRequestForCategories();
        postRequestForHomeImages();
    }

    private void postRequestForCategories() {
        JSONObject paramsObject = generateParams();
        RX2AndroidNetworkingUtils.postForData(ConstantData.CATEGORIES, paramsObject, this);
    }

    private void postRequestForHomeImages() {
        JSONObject paramsObject = generateParams();
        RX2AndroidNetworkingUtils.postForData(ConstantData.HOME_IMGS, paramsObject, this);
    }

    public JSONObject generateParams() {
        try {
            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("language", "en");
//            jsonObject.put("app", "Android");
//            jsonObject.put("version", "1.0.0");
//            jsonObject.put("token", LoggedInUserHelper.getToken(getActivity()));
            return jsonObject;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void handleResultForUI(JSONObject result) {
        super.handleResultForUI(result);

        try {
            String code = result.getString(ConstantData.CODE);
            //TODO service not work
            if (!code.equalsIgnoreCase(ConstantData.CODE_SUCCESS)) {
                String message = result.getString(ConstantData.MSG);
                LogUtil.e("------------------" + message);
                return;
            }

            String service = result.getString(ConstantData.SERVICE);
            if ("homeImgs".equalsIgnoreCase(service)) {
                handleHomeImgsResult(result);
            } else {
                handleCategoryResult(result);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

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
//        getActivity().runOnUiThread(() -> {
            myItemRecyclerViewAdapter.notifyDataSetChanged();
            updatePageFooterHeight(binding.list);
//        });
    }

    private void handleHomeImgsResult(JSONObject result) {
        try {

            JSONArray jsonArray = result.getJSONArray(ConstantData.DATA);
            ArrayList<String> imgList = new ArrayList<String>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jdata = jsonArray.getJSONObject(i);
                placeholderContent.addImage(jdata.getString("img"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

//        getActivity().runOnUiThread(() -> {
            myItemRecyclerViewAdapter.setmImages(placeholderContent.LOOP_IMAGES);
//            updateLoopImages();
//        });
    }

}