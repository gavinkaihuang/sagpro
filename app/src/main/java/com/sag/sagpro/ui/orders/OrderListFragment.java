package com.sag.sagpro.ui.orders;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sag.sagpro.BaseActivity;
import com.sag.sagpro.ConstantData;
import com.sag.sagpro.R;
import com.sag.sagpro.databinding.FragmentAddressItemListBinding;
import com.sag.sagpro.databinding.FragmentOrderItemListBinding;
import com.sag.sagpro.ui.InnerBaseFragment;
import com.sag.sagpro.ui.addresses.AddressEditFragment;
import com.sag.sagpro.ui.addresses.AddressItemRecyclerViewAdapter;
import com.sag.sagpro.ui.addresses.placeholder.AddressPlaceholderItem;
import com.sag.sagpro.ui.orders.placeholder.OrderPlaceholderItem;
import com.sag.sagpro.ui.placeholder.PlaceholderContent;
import com.sag.sagpro.ui.placeholder.PlaceholderItem;
import com.sag.sagpro.utils.LogUtils;
import com.sag.sagpro.utils.ParamsUtils;
import com.sag.sagpro.utils.RX2AndroidNetworkingUtils;
import com.sag.sagpro.utils.RecyclerItemClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A fragment representing a list of Items.
 */
public class OrderListFragment extends InnerBaseFragment {

    private int totalResultsNo = 0;
    private PlaceholderContent placeholderContent = null;
    private OrderItemRecyclerViewAdapter itemRecyclerViewAdapter = null;
    private FragmentOrderItemListBinding binding = null;
    NavController navController = null;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public OrderListFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static OrderListFragment newInstance() {
        OrderListFragment fragment = new OrderListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(getResources().getString(R.string.title_activity_order_list));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentOrderItemListBinding.inflate(inflater, container, false);

        getActivity().setTitle(R.string.title_activity_order_list);
        ((BaseActivity) getActivity()).showBackArraw(true);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Context context = view.getContext();
        itemRecyclerViewAdapter = new OrderItemRecyclerViewAdapter();

        RecyclerView recyclerView = (RecyclerView) binding.list;
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(itemRecyclerViewAdapter);

        binding.list.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), binding.list, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
//                PlaceholderItem phi = itemRecyclerViewAdapter.getItem(position);
//                if (!(phi instanceof AddressPlaceholderItem))
//                    return;
//
//                AddressPlaceholderItem addPHI = (AddressPlaceholderItem) phi;
////                String aid = addPHI.getAid();
//                Bundle bundle = new Bundle();
//                bundle.putString(AddressEditFragment.PARAMS_ADDRESS_ID, addPHI.getAid());
//                bundle.putString(AddressEditFragment.PARAMS_NAME, addPHI.getName());
//                bundle.putString(AddressEditFragment.PARAMS_ADDRESS, addPHI.getAddress());
//                bundle.putString(AddressEditFragment.PARAMS_PHONE, addPHI.getPhone());
//                bundle.putString(AddressEditFragment.PARAMS_CHOOSE, addPHI.getChoose());
//                Navigation.findNavController(view).navigate(R.id.item_navigation_address_edit, bundle);
            }

            @Override
            public void onItemLongClick(View view, int position) {
                LogUtils.i("item " + position + " long clicked");
            }
        }));

    }

    /*
     * request data from server start
     */

    /**
     * Step 1
     */
    protected void postRequest() {
        super.postRequest();
        postRequestForOrderList(0);
    }

    private void postRequestForOrderList(int startNo) {
        try {
            RX2AndroidNetworkingUtils.postForData(ConstantData.ORDER_LIST, ParamsUtils.getRequestParamsRoot(getContext()), this);
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
                    OrderPlaceholderItem placeholderItem = new OrderPlaceholderItem();
                    placeholderItem.setOrderid(jdata.getString("orderid"));
                    placeholderItem.setSt(jdata.getString("st"));
                    placeholderItem.setPaystatus(jdata.getString("paystatus"));
                    placeholderItem.setPaystatuslabel(jdata.getString("paystatuslabel"));
                    placeholderItem.setCreated(jdata.getString("created"));
                    placeholderItem.setAmount(jdata.getString("amount"));
                    placeholderItem.setRemark(jdata.getString("remark"));
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