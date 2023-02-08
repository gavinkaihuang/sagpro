package com.sag.sagpro.ui.messages;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.sag.sagpro.ConstantData;
import com.sag.sagpro.utils.LogUtils;
import com.sag.sagpro.utils.LoggedInUserHelper;
import com.sag.sagpro.databinding.FragmentMessageItemListBinding;
import com.sag.sagpro.ui.InnerBaseFragment;
import com.sag.sagpro.ui.messages.placeholder.MessagePlaceholderContent;
import com.sag.sagpro.ui.messages.placeholder.MessagePlaceholderItem;
import com.sag.sagpro.utils.AndroidNetworkingUtils;
import com.sag.sagpro.utils.RX2AndroidNetworkingUtils;
import com.sag.sagpro.utils.RecyclerItemClickListener;
import com.sag.sagpro.utils.UIUtils;
import com.sag.sagpro.utils.URLLoadCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A fragment representing a list of Items.
 */
public class MessageListFragment extends InnerBaseFragment {

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
        postRequest();
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
                //TODO message item click action handle here
            }

            @Override
            public void onItemLongClick(View view, int position) {
                LogUtils.i("item " + position + " long clicked");
            }
        }));
    }

    @Override
    protected void handleResultForUI(JSONObject result) {
        super.handleResultForUI(result);
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
                LogUtils.e(message);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //update userinterface
        myMessageRecyclerViewAdapter.setItems(placeholderContent.ITEMS);
//        getActivity().runOnUiThread(() -> {
//            LogUtil.i("----------product adapter ask ui reflash");
        myMessageRecyclerViewAdapter.notifyDataSetChanged();
        updatePageFooterHeight(binding.list);
//        });

//        LogUtils.i("----->>>>---Thread.currentThread().hashCode()=" + Thread.currentThread().hashCode());
    }


    /*
     * request data from server start
     */

    public void postRequest() {
        JSONObject paramsObject = generateParams();
        RX2AndroidNetworkingUtils.postForData(ConstantData.MESSAGES, paramsObject, this);
    }

    public JSONObject generateParams() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("language", "en");
            jsonObject.put("app", "Android");
            jsonObject.put("version", "1.0.0");
            jsonObject.put("token", LoggedInUserHelper.getToken(getActivity()));
            return jsonObject;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}