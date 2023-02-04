package com.sag.sagpro.ui.products;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.sag.sagpro.utils.URLLoadCallback;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProductDetailFragment extends InnerBaseFragment implements URLLoadCallback {

    public static final String PARAMS_PRODUCT_ID = "PARAMS_PRODUCT_ID";
    private ProductDetailViewModel mViewModel;
    private String productID = null;
    private ProductDetailPlaceholder placeholderItem = null;
    ProductDetailViewModel productDetailViewModel = null;
    FragmentProductDetailBinding binding = null;

    public static ProductDetailFragment newInstance() {
        return new ProductDetailFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (getArguments() != null) {
            productID = getArguments().getString(PARAMS_PRODUCT_ID);
        }

//        ViewModelProvider.Factory.class
        productDetailViewModel = new ViewModelProvider(this).get(ProductDetailViewModel.class);
//        ViewModelProvider.of(this).get(ProductDetailViewModel.class);
//        new ViewModelProvider().get(ProductDetailViewModelilViewModel.class);
//        binding = FragmentProductItemListBinding.inflate(inflater, container, false);
//        return binding.getRoot();
        binding = FragmentProductDetailBinding.inflate(inflater, container, false);

        onBindingViews();
        loadDataFromServer();
        return binding.getRoot();

//        return inflater.inflate(R.layout.fragment_product_detail, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ProductDetailViewModel.class);
        // TODO: Use the ViewModel
    }


    private void loadDataFromServer() {
        if (productID == null) return;

        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("productid", productID);
            AndroidNetworkingUtils.loadURL(ConstantData.PRODUCTS_DETAIL, "PRODUCTS_DETAIL", jsonObject, this);
        } catch (JSONException e) {
            LogUtil.e("-----------" + e.getMessage());
        }
    }


    private void onBindingViews() {
        getActivity().runOnUiThread(() -> {

            //TODO
            binding.priceTextView.setText("$" + productDetailViewModel.getPrice());
//            binding.nameTextView.setText(productDetailViewModel.getName());
            binding.contentTextView.setText(productDetailViewModel.getContent());
//            binding.priceTextView.setText(productDetailViewModel.getPrice());
//
//            binding.imageView.setDefaultImageResId(R.drawable.img_default);
//            binding.imageView.setErrorImageResId(R.drawable.img_error);
//            binding.imageView.setImageUrl(productDetailViewModel.getImg());
//
//            updatePageFooterHeight(binding.atoCartButton);

            ArrayList<String> arrayList = productDetailViewModel.getImgList();
            binding.viewBanner.setAdapter(new BannerImageAdapter<String>(arrayList) {
                @Override
                public void onBindView(BannerImageHolder holder, String data, int position, int size) {
                    Glide.with(holder.imageView)
                            .load(data)
                            .into(holder.imageView);
                }
            });
            //轮播图下面的原点
            binding.viewBanner.setIndicator(new CircleIndicator(ProductDetailFragment.this.getContext()));
            binding.viewBanner.setIndicatorRadius(50);

            updatePageFooterHeight(binding.getRoot());
        });


//        binding.nameTextView.setText(productDetailViewModel.getName());
    }


    @Override
    /**
     * Load data from server, than handle it
     */ public void successURLLoadedCallBack(JSONObject result) {
        try {
            String code = result.getString(ConstantData.CODE);
            if (code.equalsIgnoreCase(ConstantData.CODE_SUCCESS)) {
                JSONObject jsonObject = result.getJSONObject(ConstantData.DATA);
                productDetailViewModel.setName(jsonObject.getString("name"));
                productDetailViewModel.setPid(jsonObject.getString("pid"));
                productDetailViewModel.setCid(jsonObject.getString("cid"));
                productDetailViewModel.setFid(jsonObject.getString("fid"));
                productDetailViewModel.setContent(generateTempContent(jsonObject.getString("content")));
                productDetailViewModel.setPrice(jsonObject.getString("price"));

                //TODO change image to list
                String image = jsonObject.getString("img");
                ArrayList<String> imageList = new ArrayList<String>();
                imageList.add(image);
                productDetailViewModel.setImgList(imageList);
                onBindingViews();
            } else {
                String message = result.getString(ConstantData.MSG);
                LogUtil.e("------------------" + message);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


//        getActivity().runOnUiThread(() -> {
////            LogUtil.i("----------product adapter ask ui reflash");
//            myProductItemRecyclerViewAdapter.notifyDataSetChanged();
//            updatePageFooterHeight(binding.list);
//        });
    }

    @Override
    public Exception failueURLLoadedCallBack(Exception exception) {
        return null;
    }

    private String generateTempContent(String content) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 100; i++) {
            sb.append(content + "\n");
        }
        return sb.toString();
    }
}