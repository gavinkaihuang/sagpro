package com.sag.sagpro.ui.addresses;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.facebook.stetho.common.LogUtil;
import com.sag.sagpro.BaseActivity;
import com.sag.sagpro.ConstantData;
import com.sag.sagpro.R;
import com.sag.sagpro.databinding.FragmentAddressAddBinding;
import com.sag.sagpro.databinding.FragmentAddressEditBinding;
import com.sag.sagpro.ui.InnerBaseFragment;
import com.sag.sagpro.utils.ParamsUtils;
import com.sag.sagpro.utils.RX2AndroidNetworkingUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class AddressEditFragment extends InnerBaseFragment {

    public static final String PARAMS_ADDRESS_ID = "PARAMS_ADDRESS_ID";
    public static final String PARAMS_NAME = "PARAMS_ADDRESS_NAME";
    public static final String PARAMS_ADDRESS = "PARAMS_ADDRESS";
    public static final String PARAMS_PHONE = "PARAMS_PHONE";
    public static final String PARAMS_CHOOSE = "PARAMS_CHOOSE";
    private FragmentAddressEditBinding binding;
    private String aid = null;
    private String name = null;
    private String address = null;
    private String phone = null;
    private String choose = null;

//    public AddressEditFragment() {
//        // Required empty public constructor
//    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (getArguments() != null) {
            aid = getArguments().getString(PARAMS_ADDRESS_ID);
            name = getArguments().getString(PARAMS_NAME);
            address = getArguments().getString(PARAMS_ADDRESS);
            phone = getArguments().getString(PARAMS_PHONE);
            choose = getArguments().getString(PARAMS_CHOOSE);
        } else {
            Navigation.findNavController(binding.updateAddressBT).navigate(R.id.item_navigation_address_list, null);
        }

        binding = FragmentAddressEditBinding.inflate(inflater, container, false);
        binding.nameET.setText(name);
        binding.addressET.setText(address);
        binding.phoneET.setText(phone);
        binding.defaultAddCB.setChecked("1".equals(choose) ? true : false);

        getActivity().setTitle(R.string.title_activity_address_edit);
        ((BaseActivity) getActivity()).showBackArraw(true);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.updateAddressBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = binding.nameET.getText().toString();
                String address = binding.addressET.getText().toString();
                String phone = binding.phoneET.getText().toString();
                String isChecked = binding.defaultAddCB.isChecked() ? "1" : "0";
                if (checkParams(name, address, phone))
                    actionPostUpdateRequest(aid, name, address, phone, isChecked);
            }
        });

        binding.deleteAddressBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionPostDeleteRequest(aid);
            }
        });

        dismissProgressDialog();
    }


    private boolean checkParams(String name, String address, String phone) {
        boolean isPassed = true;
        if ("".equals(name)) {
            isPassed = false;
            binding.labelNamePrompt.setText(R.string.label_address_name_required);
            binding.labelNamePrompt.setVisibility(View.VISIBLE);
        }

        if ("".equals(address)) {
            isPassed = false;
            binding.labelAddressPrompt.setText(R.string.label_address_address_required);
            binding.labelAddressPrompt.setVisibility(View.VISIBLE);
        }

        if ("".equals(phone)) {
            isPassed = false;
            binding.labelPhonePrompt.setText(R.string.label_address_phone_required);
            binding.labelPhonePrompt.setVisibility(View.VISIBLE);
        }

        return isPassed;
    }

    private void actionPostDeleteRequest(String aid) {
        super.postRequest();
        try {
            JSONObject jsonObject = ParamsUtils.getRequestParamsRoot(getContext());
            jsonObject.put("aid", aid);

            RX2AndroidNetworkingUtils.postForData(ConstantData.ADDRESS_DELETE, jsonObject, this);
        } catch (JSONException e) {
            LogUtil.e("-----------" + e.getMessage());
        }
    }

    private void actionPostUpdateRequest(String aid, String name, String address, String phone, String choose) {
        super.postRequest();
        try {
            JSONObject jsonObject = ParamsUtils.getRequestParamsRoot(getContext());
            jsonObject.put("aid", aid);
            jsonObject.put("name", name);
            jsonObject.put("address", address);
            jsonObject.put("phone", phone);
            jsonObject.put("choose", choose);

            RX2AndroidNetworkingUtils.postForData(ConstantData.ADDRESS_EDIT, jsonObject, this);
        } catch (JSONException e) {
            LogUtil.e("-----------" + e.getMessage());
        }
    }

    protected void handleResultForUI(final JSONObject result) {


        Navigation.findNavController(binding.updateAddressBT).navigate(R.id.item_navigation_address_list, null);
    }

    /*
     * request data from server start
     */

//    protected void postRequest() {
//        super.postRequest();
//        postRequestForDetails();
//    }
//
//    private void postRequestForDetails() {
//        try {
//            JSONObject jsonObject = ParamsUtils.getRequestParamsRoot(getContext());
//            jsonObject.put("productid", productID);
////            jsonObject.put("token", LoggedInUserHelper.getToken(getActivity()));
//            RX2AndroidNetworkingUtils.postForData(ConstantData.PRODUCTS_DETAIL, jsonObject, this);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void onSuccess(JSONObject jsonObject) {
//        super.onSuccess(jsonObject);
//    }
//
//        private void handleResult(JSONObject result) {
//        try {
//            JSONObject jsonObject = result.getJSONObject(ConstantData.DATA);
//
//            String email = binding.emailEditText.getText().toString();
//            String password = binding.passwordEditText.getText().toString();
//
//            loggedInUser = new LoggedInUser(null, email);
//            loggedInUser.setPassword(password);
//            loggedInUser.setToken(jsonObject.getString("token"));
//            loggedInUser.setExpireDate(jsonObject.getString("expiredate"));
//            LoggedInUserHelper.saveUserToLocal(getActivity(), loggedInUser);//save to local storeage
//
//            //update UID
//            ((LoginActivity) getActivity()).updateUiWithUser(loggedInUser);
//            getActivity().setResult(Activity.RESULT_OK);
//            getActivity().finish();
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }

}