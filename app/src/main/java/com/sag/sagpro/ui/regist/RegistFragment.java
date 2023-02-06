package com.sag.sagpro.ui.regist;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.stetho.common.LogUtil;
import com.sag.sagpro.ConstantData;
import com.sag.sagpro.R;
import com.sag.sagpro.data.model.LoggedInUser;
import com.sag.sagpro.databinding.FragmentRegistBinding;
import com.sag.sagpro.ui.InnerBaseFragment;
import com.sag.sagpro.activities.LoginActivity;
import com.sag.sagpro.utils.AndroidNetworkingUtils;
import com.sag.sagpro.utils.LoggedInUserHelper;
import com.sag.sagpro.utils.URLLoadCallback;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegistFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegistFragment  extends InnerBaseFragment implements URLLoadCallback {

    FragmentRegistBinding binding = null;
    public RegistFragment() {
        // Required empty public constructor
    }

    public static RegistFragment newInstance(String param1, String param2) {
        RegistFragment fragment = new RegistFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(getResources().getString(R.string.title_activity_regist));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRegistBinding.inflate(inflater, container, false);
        initActions();
        return binding.getRoot();
    }

    private void loadDataFromServer(String firstName, String lastName, String email, String password) {
        try {//{"email":"phw82@sohu.com","password":"111111","firstname":"peter","lastname":"pan"}
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("firstname", firstName);
            jsonObject.put("lastname", lastName);
            jsonObject.put("email", email);
            jsonObject.put("password", password);

            AndroidNetworkingUtils.loadURL(ConstantData.SIGN_UP, "SIGN_UP", jsonObject, this);
        } catch (JSONException e) {
            LogUtil.e("-----------" + e.getMessage());
        }
    }

    private void initActions() {
        binding.registButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String firstName = binding.firstNameEditText.getText().toString();
                String lastName = binding.lastNameEditText.getText().toString();
                String email = binding.emailEditText.getText().toString();
                String password = binding.passwordEditText.getText().toString();

                loadDataFromServer(firstName, lastName, email, password);
            }
        });
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