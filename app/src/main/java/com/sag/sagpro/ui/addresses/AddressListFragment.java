package com.sag.sagpro.ui.addresses;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sag.sagpro.ConstantData;
import com.sag.sagpro.R;
import com.sag.sagpro.activities.ProductDetailsActivity;
import com.sag.sagpro.databinding.FragmentAddressItemBinding;
import com.sag.sagpro.databinding.FragmentAddressItemListBinding;
import com.sag.sagpro.databinding.FragmentProductItemListBinding;
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
    private FragmentAddressItemListBinding binding = null;
    NavController navController = null;

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

        binding = FragmentAddressItemListBinding.inflate(inflater, container, false);
        return binding.getRoot();
//        binding = FragmentAddressItemBinding.inflate()
//        View view = inflater.inflate(R.layout.fragment_address_item_list, container, false);
//        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Context context = view.getContext();
        itemRecyclerViewAdapter = new AddressItemRecyclerViewAdapter();

        RecyclerView recyclerView = (RecyclerView) binding.list;
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(itemRecyclerViewAdapter);

        binding.addAddressBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO
//                Intent intent = new Intent();
//                intent.putExtras(bundle);
//                intent.setClass(view.getContext(), ProductDetailsActivity.class);
//                view.getContext().startActivity(intent);

//                navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_login);
//                navController.navigate(R.id.item_navigation_signin);
                Navigation.findNavController(v).navigate(R.id.item_navigation_address_add, null);
            }
        });
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