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
        AndroidNetworkingUtils.loadURL(ConstantData.CATEGORIES, "CATEGORIES", new JSONObject(), new LoadUrlHandler());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeItemListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        myItemRecyclerViewAdapter = new MyItemRecyclerViewAdapter(placeholderContent.ITEMS);
        binding.list.setAdapter(myItemRecyclerViewAdapter);
        binding.list.addItemDecoration(UIUtils.getDividerItemDecoration(getContext()));

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

//        binding.viewBanner.

        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("https://p2.itc.cn/images01/20210510/096eeb9cd3c84bd8ba09b5713679b4f9.jpeg");
        arrayList.add("https://p2.itc.cn/images01/20210510/096eeb9cd3c84bd8ba09b5713679b4f9.jpeg");
        arrayList.add("http://i0.hdslb.com/bfs/article/97549c0fd58b940c1306faac923a8685551a6a2a.jpg");
        binding.viewBanner.setAdapter(new BannerImageAdapter<String>(arrayList) {
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
    private void handleResult(JSONObject result) {
        try {
            String code = result.getString(ConstantData.CODE);
            if (code.equalsIgnoreCase(ConstantData.CODE_SUCCESS)) {
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
            } else {
                String message = result.getString(ConstantData.MSG);
                LogUtil.e("------------------" + message);
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