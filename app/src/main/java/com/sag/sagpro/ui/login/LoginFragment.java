package com.sag.sagpro.ui.login;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.facebook.stetho.common.LogUtil;
import com.sag.sagpro.ConstantData;
import com.sag.sagpro.R;
import com.sag.sagpro.data.model.LoggedInUser;
import com.sag.sagpro.databinding.ActivityLoginBinding;
import com.sag.sagpro.databinding.FragmentLoginBinding;
import com.sag.sagpro.databinding.FragmentProductDetailBinding;
import com.sag.sagpro.ui.InnerBaseFragment;
import com.sag.sagpro.ui.products.ProductDetailViewModel;
import com.sag.sagpro.utils.AndroidNetworkingUtils;
import com.sag.sagpro.utils.LoggedInUserHelper;
import com.sag.sagpro.utils.URLLoadCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends InnerBaseFragment implements URLLoadCallback {

    private FragmentLoginBinding binding;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        getActivity().setTitle(getResources().getString(R.string.title_activity_login));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_login, container, false);
//        if (getArguments() != null) {
//            productID = getArguments().getString(PARAMS_PRODUCT_ID);
//        }

//        productDetailViewModel = new ViewModelProvider(this).get(ProductDetailViewModel.class);
        binding = FragmentLoginBinding.inflate(inflater, container, false);

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String userName = binding.emailEditText.getText().toString();
                String password = binding.passwordEditText.getText().toString();
                loadDataFromServer(userName, password);
            }
        });

        binding.registTextView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.item_navigation_regist, null);
            }
        });
       return binding.getRoot();
    }

    private void loadDataFromServer(String userName, String password) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("email", userName);
            jsonObject.put("password", password);

            AndroidNetworkingUtils.loadURL(ConstantData.SIGN_IN, "SIGN_IN", jsonObject, this);
        } catch (JSONException e) {
            LogUtil.e("-----------" + e.getMessage());
        }
    }

    LoggedInUser loggedInUser = null;
    private void handleResult(JSONObject result) {
        try {
            JSONObject jsonObject = result.getJSONObject(ConstantData.DATA);

            String email = binding.emailEditText.getText().toString();
            String password = binding.passwordEditText.getText().toString();

            loggedInUser = new LoggedInUser(null, email);
            loggedInUser.setPassword(password);
            loggedInUser.setToken(jsonObject.getString("token"));
            loggedInUser.setExpireDate(jsonObject.getString("expiredate"));
            LoggedInUserHelper.saveUserToLocal(getActivity(), loggedInUser);//save to local storeage
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void successURLLoadedCallBack(JSONObject result) {
        try {
            String code = result.getString(ConstantData.CODE);
            if (code.equalsIgnoreCase(ConstantData.CODE_SUCCESS)) {
                String service = result.getString(ConstantData.SERVICE);
                handleResult(result);
            } else {
                String message = result.getString(ConstantData.MSG);
                LogUtil.e("------------------" + message);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        getActivity().runOnUiThread(() -> {
            ((LoginActivity) getActivity()).updateUiWithUser(loggedInUser);
            getActivity().setResult(Activity.RESULT_OK);
            getActivity().finish();
        });
    }

    @Override
    public Exception failueURLLoadedCallBack(Exception exception) {
        return null;
    }


}