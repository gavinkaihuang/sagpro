package com.sag.sagpro.ui.addresses;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.stetho.common.LogUtil;
import com.sag.sagpro.ConstantData;
import com.sag.sagpro.R;
import com.sag.sagpro.activities.LoginActivity;
import com.sag.sagpro.data.model.LoggedInUser;
import com.sag.sagpro.databinding.FragmentAddressAddBinding;
import com.sag.sagpro.databinding.FragmentLoginBinding;
import com.sag.sagpro.ui.InnerBaseFragment;
import com.sag.sagpro.utils.LoggedInUserHelper;
import com.sag.sagpro.utils.ParamsUtils;
import com.sag.sagpro.utils.RX2AndroidNetworkingUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class AddressAddFragment extends InnerBaseFragment {


    private FragmentAddressAddBinding binding;

    public AddressAddFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddressAddBinding.inflate(inflater, container, false);
       return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.addAddressBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = binding.nameET.getText().toString();
                String address = binding.addressET.getText().toString();
                String phone = binding.phoneET.getText().toString();
                String isChecked = binding.defaultAddCB.isChecked() ? "1" : "0";
                if (checkParams(name, address, phone))
                    actionPostRequest(name, address, phone, isChecked);


            }
        });

        dismissProgressDialog();
    }


    private boolean checkParams(String name, String address, String phone) {

        return true;
    }

    private void actionPostRequest(String name, String address, String phone, String choose) {
        super.postRequest();
        try {
            JSONObject jsonObject = ParamsUtils.getRequestParamsRoot(getContext());
//            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", name);
            jsonObject.put("address", address);
            jsonObject.put("phone", phone);
            jsonObject.put("choose", choose);

            RX2AndroidNetworkingUtils.postForData(ConstantData.ADDRESS_CREATE, jsonObject, this);
//            AndroidNetworkingUtils.loadURL(ConstantData.SIGN_IN, "SIGN_IN", jsonObject, this);
        } catch (JSONException e) {
            LogUtil.e("-----------" + e.getMessage());
        }
    }

    protected void handleResultForUI(final JSONObject result) {


        Navigation.findNavController(binding.addAddressBT).navigate(R.id.item_navigation_address_list, null);
    }

    @Override
    public void onSuccess(JSONObject jsonObject) {
        super.onSuccess(jsonObject);
    }

    //    private void handleResult(JSONObject result) {
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