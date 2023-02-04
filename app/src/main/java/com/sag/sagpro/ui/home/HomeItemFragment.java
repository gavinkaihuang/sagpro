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
import com.sag.sagpro.ui.products.ProductListFragment;
import com.sag.sagpro.utils.AndroidNetworkingUtils;
import com.sag.sagpro.utils.ImageLoadCallback;
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

        LogUtil.i("------------------HomeItemFragment onCreate");
        placeholderContent = new HomeItemPlaceholderContent();
        JSONObject jsonObject = new JSONObject();
        AndroidNetworkingUtils.loadURL(ConstantData.CATEGORIES, "CATEGORIES", new JSONObject(), new LoadUrlHandler());

        AndroidNetworkingUtils.loadURL(ConstantData.HOME_IMGS, "HOME_IMGS", jsonObject, new LoadUrlHandler());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeItemListBinding.inflate(inflater, container, false);

        myItemRecyclerViewAdapter = new MyItemRecyclerViewAdapter(placeholderContent.ITEMS);
        binding.list.setAdapter(myItemRecyclerViewAdapter);
        binding.list.addItemDecoration(UIUtils.getDividerItemDecoration(getContext()));

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.categoriesButton.setBackgroundResource(R.drawable.categories);
        binding.categoriesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        binding.productButton.setBackgroundResource(R.drawable.products);
        binding.productButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt(ProductListFragment.PARAMS_CID, 1);
                Navigation.findNavController(v).navigate(R.id.item_navigation_products, bundle);
            }
        });

    }

    private void updateLoopImages() {
        binding.viewBanner.setAdapter(new BannerImageAdapter<String>(placeholderContent.LOOP_IMAGES) {
            @Override
            public void onBindView(BannerImageHolder holder, String data, int position, int size) {
                Glide.with(holder.imageView)
                        .load(data)
                        .into(holder.imageView);
            }
        });
        //轮播图下面的原点
        binding.viewBanner.setIndicator(new CircleIndicator(HomeItemFragment.this.getContext()));
        binding.viewBanner.setIndicatorRadius(50);
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
//            int size = jsonArray.length();
            ArrayList<String> imgList = new ArrayList<String>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jdata = jsonArray.getJSONObject(i);
                placeholderContent.addImage(jdata.getString("img"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        getActivity().runOnUiThread(() -> {
            updateLoopImages();
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

    class LoadImageHandler implements ImageLoadCallback {
        public Bitmap loadImageSucceed(Bitmap bitmap) {
            return bitmap;
        }

        public Exception loadImageFailed(Exception exception) {
            return exception;
        }
    }
}