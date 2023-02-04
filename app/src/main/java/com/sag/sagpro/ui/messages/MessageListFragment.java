package com.sag.sagpro.ui.messages;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.stetho.common.LogUtil;
import com.sag.sagpro.ConstantData;
import com.sag.sagpro.utils.LoggedInUserHelper;
import com.sag.sagpro.databinding.FragmentMessageItemListBinding;
import com.sag.sagpro.ui.InnerBaseFragment;
import com.sag.sagpro.ui.messages.placeholder.MessagePlaceholderContent;
import com.sag.sagpro.ui.messages.placeholder.MessagePlaceholderItem;
import com.sag.sagpro.utils.AndroidNetworkingUtils;
import com.sag.sagpro.utils.RecyclerItemClickListener;
import com.sag.sagpro.utils.UIUtils;
import com.sag.sagpro.utils.URLLoadCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A fragment representing a list of Items.
 */
public class MessageListFragment extends InnerBaseFragment implements URLLoadCallback {

    MyMessageItemRecyclerViewAdapter myMessageRecyclerViewAdapter = null;
    private MessagePlaceholderContent placeholderContent = null;
    private FragmentMessageItemListBinding binding = null;

    public MessageListFragment() {
    }

    public static MessageListFragment newInstance(int columnCount) {
        MessageListFragment fragment = new MessageListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        placeholderContent = new MessagePlaceholderContent();
        loadDataFromServer(0);
    }

    private void loadDataFromServer(int startNo) {

//        {"language":"en","app":"ios","version":"1.0.0","token":"$Pe!nmRFNhbfUdg9VD5CJWjZMls%uSoO"}
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("language", "en");
            jsonObject.put("app", "Android");
            jsonObject.put("version", "1.0.0");
            jsonObject.put("token", LoggedInUserHelper.getToken(getActivity()));
            AndroidNetworkingUtils.loadURL(ConstantData.MESSAGES, "MESSAGES", jsonObject, this);
        } catch (JSONException e) {
            LogUtil.e("-----------" + e.getMessage());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMessageItemListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        myMessageRecyclerViewAdapter = new MyMessageItemRecyclerViewAdapter(placeholderContent.ITEMS);
        binding.list.setAdapter(myMessageRecyclerViewAdapter);
        binding.list.addItemDecoration(UIUtils.getDividerItemLineDecoration(getContext()));

        binding.list.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), binding.list, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
//                        ToastUtil.showMessage(context, "position = " + position);
//                        LogUtil.i("----------item " + position + " clicked");
//                ProductPlaceholderItem itemClicked = placeholderContent.getItem(position);
//                Bundle bundle = new Bundle();
//                bundle.putString(ProductDetailFragment.PARAMS_PRODUCT_ID, itemClicked.pid);
//                Navigation.findNavController(view).navigate(R.id.item_navigation_product_detail, bundle);
            }

            @Override
            public void onItemLongClick(View view, int position) {
                LogUtil.i("----------item " + position + " long clicked");
            }
        }));
    }

    private void handleResult(JSONObject result) {
        try {
            String code = result.getString(ConstantData.CODE);
            if (code.equalsIgnoreCase(ConstantData.CODE_SUCCESS)) {
                JSONArray jsonArray = result.getJSONArray(ConstantData.DATA);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jdata = jsonArray.getJSONObject(i);
                    MessagePlaceholderItem placeholderItem = new MessagePlaceholderItem();
                    placeholderItem.setMid(jdata.getString("mid"));
                    placeholderItem.setTitle(jdata.getString("title"));
                    placeholderItem.setContent(jdata.getString("content"));
                    placeholderItem.setCreated(jdata.getString("created"));
//
                    if (placeholderContent == null)
                        placeholderContent = new MessagePlaceholderContent();
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
        myMessageRecyclerViewAdapter.setItems(placeholderContent.ITEMS);
        getActivity().runOnUiThread(() -> {
//            LogUtil.i("----------product adapter ask ui reflash");
            myMessageRecyclerViewAdapter.notifyDataSetChanged();
            updatePageFooterHeight(binding.list);
        });
    }

    @Override
    public void successURLLoadedCallBack(JSONObject result) {
        handleResult(result);
    }

    @Override
    public Exception failueURLLoadedCallBack(Exception exception) {
        return null;
    }
}