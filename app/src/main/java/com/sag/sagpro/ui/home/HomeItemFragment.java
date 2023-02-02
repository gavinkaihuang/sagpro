package com.sag.sagpro.ui.home;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.stetho.common.LogUtil;
import com.sag.sagpro.ConstantData;
import com.sag.sagpro.MainActivity;
import com.sag.sagpro.R;
import com.sag.sagpro.databinding.FragmentHomeItemListBinding;
import com.sag.sagpro.ui.categories.CategorieFragment;
import com.sag.sagpro.ui.home.placeholder.HomeItemPlaceholderContent;
import com.sag.sagpro.ui.home.placeholder.HomeItemPlaceholderItem;
import com.sag.sagpro.ui.products.ProductItemFragment;
import com.sag.sagpro.utils.AndroidNetworkingUtils;
import com.sag.sagpro.utils.ImageLoadCallback;
import com.sag.sagpro.utils.ScreenUtils;
import com.sag.sagpro.utils.UIUtils;
import com.sag.sagpro.utils.URLLoadCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A fragment representing a list of Items.
 */
public class HomeItemFragment extends Fragment {


    MyItemRecyclerViewAdapter myItemRecyclerViewAdapter = null;

//    // TODO: Customize parameter argument names
//    private static final String ARG_COLUMN_COUNT = "column-count";
//    // TODO: Customize parameters
//    private int mColumnCount = 1;

//    MessageHandler messageHandler = null;
    private HomeItemPlaceholderContent placeholderContent = null;
    FragmentHomeItemListBinding binding = null;
//    RecyclerView recyclerView = null;
//    EditText serchEditText = null;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public HomeItemFragment() {
    }

//    // TODO: Customize parameter initialization
//    @SuppressWarnings("unused")
//    public static HomeItemFragment newInstance(int columnCount) {
//        HomeItemFragment fragment = new HomeItemFragment();
//        Bundle args = new Bundle();
//        args.putInt(ARG_COLUMN_COUNT, columnCount);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        if (getArguments() != null) {
//            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
//        }


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
//                navController.navigate(R.id.item_navigation_homeitem);
                Activity activty = HomeItemFragment.this.getActivity();
                if (activty instanceof MainActivity) {
//                    ((MainActivity) activty).navigationTo(R.id.ca)
                   //TODo
//                    ((MainActivity) activty).navigationTo(R.id.item_navigation_categories);
//                    HomeItemFragment.this.a
//                    addToBackStack  https://blog.csdn.net/qq_44920726/article/details/126677674

                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    // 2：获取FragmentTransaction
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                    // 3：将跳转的fragment 添加到栈，这里使用 replace
                    fragmentTransaction.replace(R.id.container, CategorieFragment.newInstance(1));
                    fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                    fragmentTransaction.addToBackStack("");
                    // 4: 提交事务
                    fragmentTransaction.commit();

                }
            }
        });

        binding.productButton.setBackgroundResource(R.drawable.products);
        binding.productButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt(ProductItemFragment.PARAMS_CID, 1);
                Navigation.findNavController(v).navigate(R.id.item_navigation_products, bundle);

////                navController.navigate(R.id.item_navigation_homeitem);
//                Activity activty = HomeItemFragment.this.getActivity();
//                if (activty instanceof MainActivity) {
////                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
////                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
////                    fragmentTransaction.replace(R.id.container, ProductItemFragment.newInstance(1));
////                    fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
////                    fragmentTransaction.addToBackStack(null);
////                    fragmentTransaction.commit();
//
//                    Bundle bundle = new Bundle();
//                    bundle.putInt(ProductItemFragment.PARAMS_CID, 1);
//                    Navigation.findNavController(v).navigate(R.id.item_navigation_products, bundle);
//                }
            }
        });

    }


    /**
     * handle list data from server
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
            updatePageFooterHeight();
        });
    }

    /**
     * make last list item full disabled
     */
    private void updatePageFooterHeight() {
        try {
            Activity activty = this.getActivity();
            if (activty instanceof MainActivity) {
                int footHeight = ((MainActivity) activty).getFooterHeight();
                ViewGroup.LayoutParams lp = binding.list.getLayoutParams();
                int left = ScreenUtils.px2dip(getContext(), binding.list.getPaddingLeft());
                int right = ScreenUtils.px2dip(getContext(), binding.list.getPaddingRight());
                int top = ScreenUtils.px2dip(getContext(), binding.list.getPaddingTop());
                int bottom = ScreenUtils.px2dip(getContext(), binding.list.getPaddingBottom());
                binding.list.setPadding(left, right, top,  footHeight);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class LoadUrlHandler implements URLLoadCallback {
        public void successCallBack(JSONObject result) {
            handleResult(result);
        }
        public Exception failedClassBack(Exception exception) {
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