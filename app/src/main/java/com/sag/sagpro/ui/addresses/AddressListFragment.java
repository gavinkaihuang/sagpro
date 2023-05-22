package com.sag.sagpro.ui.addresses;

import android.content.Context;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sag.sagpro.ConstantData;
import com.sag.sagpro.R;
import com.sag.sagpro.ui.InnerBaseFragment;
import com.sag.sagpro.ui.addresses.placeholder.AddressPlaceholderItem;
import com.sag.sagpro.ui.placeholder.PlaceholderContent;
import com.sag.sagpro.utils.LogUtils;
import com.sag.sagpro.utils.ParamsUtils;
import com.sag.sagpro.utils.RX2AndroidNetworkingUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A fragment representing a list of Items.
 */
public class AddressListFragment extends InnerBaseFragment {

    private int totalResultsNo = 0;
    private PlaceholderContent placeholderContent = null;
    private AddressItemRecyclerViewAdapter itemRecyclerViewAdapter = null;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public AddressListFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static AddressListFragment newInstance() {
        AddressListFragment fragment = new AddressListFragment();
//        Bundle args = new Bundle();
//        args.putInt(ARG_COLUMN_COUNT, columnCount);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(getResources().getString(R.string.title_activity_address_list));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_address_item_list, container, false);

        Context context = view.getContext();
        itemRecyclerViewAdapter = new AddressItemRecyclerViewAdapter();
        RecyclerView recyclerView = (RecyclerView) view;
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(itemRecyclerViewAdapter);
        return view;
    }


    /*
     * request data from server start
     */

    /**
     * Step 1
     */
    protected void postRequest() {
        super.postRequest();
        postRequestForDetails(0);
    }

    private void postRequestForDetails(int startNo) {
//        if (cid == null || "".equals(cid))
//            return;

        try {
            RX2AndroidNetworkingUtils.postForData(ConstantData.ADDRESS_LIST, ParamsUtils.getRequestParamsRoot(getContext()), this);
        } catch (Exception e) {
            LogUtils.e(e.getMessage());
        }
    }

    /**
     * Step 2
     * Handle Data Result,
     * RX2AndroidNetworkingUtils will call back in UI thread
     *
     * @param result
     */
    protected void handleResultForUI(final JSONObject result) {
        super.handleResultForUI(result);
        handleResult(result);
    }


    /**
     * handle list data from server
     *
     * @param result
     */
    private void handleResult(JSONObject result) {
        LogUtils.i(result.toString());
        try {
            String code = result.getString(ConstantData.CODE);
            if (code.equalsIgnoreCase(ConstantData.CODE_SUCCESS)) {
                totalResultsNo = Integer.parseInt(result.getString("total"));
                JSONArray jsonArray = result.getJSONArray(ConstantData.DATA);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jdata = jsonArray.getJSONObject(i);
                    AddressPlaceholderItem placeholderItem = new AddressPlaceholderItem();
                    placeholderItem.setAid(jdata.getString("aid"));
                    placeholderItem.setAddress1(jdata.getString("address"));
                    placeholderItem.setName(jdata.getString("name"));
                    placeholderItem.setPhone(jdata.getString("phone"));
                    placeholderItem.setChoose(jdata.getString("choose"));
                    if (placeholderContent == null)
                        placeholderContent = new PlaceholderContent();
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
        itemRecyclerViewAdapter.setItems(placeholderContent.getITEMS());
        itemRecyclerViewAdapter.notifyDataSetChanged();
//        updatePageFooterHeight(binding.list);
    }

}