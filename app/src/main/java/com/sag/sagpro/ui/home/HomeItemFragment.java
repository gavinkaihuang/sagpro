package com.sag.sagpro.ui.home;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.facebook.stetho.common.LogUtil;
import com.sag.sagpro.ConstantData;
import com.sag.sagpro.R;
import com.sag.sagpro.data.Result;
import com.sag.sagpro.data.model.LoggedInUser;
import com.sag.sagpro.ui.home.placeholder.PlaceholderContent;
import com.sag.sagpro.ui.home.placeholder.PlaceholderItem;
import com.sag.sagpro.utils.AndroidNetworkingUtils;
import com.sag.sagpro.utils.ImageLoadCallback;
import com.sag.sagpro.utils.URLLoadCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.Executors;

/**
 * A fragment representing a list of Items.
 */
public class HomeItemFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    private PlaceholderContent placeholderContent = null;
    RecyclerView recyclerView = null;
    EditText serchEditText = null;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public HomeItemFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static HomeItemFragment newInstance(int columnCount) {
        HomeItemFragment fragment = new HomeItemFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }


        LogUtil.e("------------------onCreate");
        placeholderContent = new PlaceholderContent();
        AndroidNetworkingUtils.loadURL(ConstantData.CATEGORIES, "CATEGORIES", new JSONObject(), new LoadUrlHandler());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home_item_list, container, false);
        serchEditText = rootView.findViewById(R.id.serchEditText);

        serchEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                recyclerView.notify();
//                notifyAll();
                return false;
            }
        });
        LogUtil.e("------------------onCreateView");

        // Set the adapter
        recyclerView = rootView.findViewById(R.id.list);
        if (recyclerView instanceof RecyclerView) {
            Context context = rootView.getContext();
//            RecyclerView recyclerView = (RecyclerView) recyclerView;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new MyItemRecyclerViewAdapter(placeholderContent.ITEMS));
        }
        return rootView;
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
                    PlaceholderItem placeholderItem = new PlaceholderItem();
                    placeholderItem.setCid(jdata.getString("cid"));
                    placeholderItem.setName(jdata.getString("name"));
                    placeholderItem.setDescription(jdata.getString("description"));
                    placeholderItem.setImg(jdata.getString("img"));

                    if (placeholderContent == null)
                        placeholderContent = new PlaceholderContent();
                    placeholderContent.addItem(placeholderItem);
                }
            } else {
                String message = result.getString(ConstantData.MSG);
                LogUtil.e("------------------" + message);
            }
        } catch (JSONException e) {
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