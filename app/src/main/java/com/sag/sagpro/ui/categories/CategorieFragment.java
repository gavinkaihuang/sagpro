package com.sag.sagpro.ui.categories;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sag.sagpro.ConstantData;
import com.sag.sagpro.R;
import com.sag.sagpro.utils.AndroidNetworkingUtils;
import com.sag.sagpro.utils.ImageLoadCallback;
import com.sag.sagpro.utils.URLLoadCallback;

import org.json.JSONObject;

/**
 * A fragment representing a list of Items.
 */
public class CategorieFragment extends Fragment {



    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CategorieFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static CategorieFragment newInstance(int columnCount) {
        CategorieFragment fragment = new CategorieFragment();
//        Bundle args = new Bundle();
//        args.putInt(ARG_COLUMN_COUNT, columnCount);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AndroidNetworkingUtils.loadURL(ConstantData.CATEGORIES, "CATEGORIES", new JSONObject(), new LoadUrlHandler());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category_item_list, container, false);

//        // Set the adapter
//        if (view instanceof RecyclerView) {
//            Context context = view.getContext();
//            RecyclerView recyclerView = (RecyclerView) view;
//            if (mColumnCount <= 1) {
//                recyclerView.setLayoutManager(new LinearLayoutManager(context));
//            } else {
//                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
//            }
//            recyclerView.setAdapter(new MyCategorieRecyclerViewAdapter(PlaceholderContent.ITEMS));
//        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    /**
     * handle list data from server
     * @param result
     */
    private void handleResult(JSONObject result) {
//        try {
//            String code = result.getString(ConstantData.CODE);
//            if (code.equalsIgnoreCase(ConstantData.CODE_SUCCESS)) {
//                JSONArray jsonArray = result.getJSONArray(ConstantData.DATA);
//                for (int i = 0; i < jsonArray.length(); i++) {
//                    JSONObject jdata = jsonArray.getJSONObject(i);
//                    PlaceholderItem placeholderItem = new PlaceholderItem();
//                    placeholderItem.setCid(jdata.getString("cid"));
//                    placeholderItem.setName(jdata.getString("name"));
//                    placeholderItem.setDescription(jdata.getString("description"));
//                    placeholderItem.setImg(jdata.getString("img"));
//
//                    if (placeholderContent == null)
//                        placeholderContent = new PlaceholderContent();
//                    placeholderContent.addItem(placeholderItem);
//                }
//            } else {
//                String message = result.getString(ConstantData.MSG);
//                LogUtil.e("------------------" + message);
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        //update userinterface
//        myItemRecyclerViewAdapter.setItems(placeholderContent.ITEMS);
//        getActivity().runOnUiThread(() -> {
//            myItemRecyclerViewAdapter.notifyDataSetChanged();
//            updatePageFooterHeight();
//        });
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