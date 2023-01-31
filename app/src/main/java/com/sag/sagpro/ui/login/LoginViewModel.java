package com.sag.sagpro.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.util.Patterns;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.facebook.stetho.common.LogUtil;
import com.sag.sagpro.ConstantData;
import com.sag.sagpro.data.LoginRepository;
import com.sag.sagpro.data.Result;
import com.sag.sagpro.data.model.LoggedInUser;
import com.sag.sagpro.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.Executors;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private LoginRepository loginRepository;

    LoginViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }



    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }


    public void login(String username, String password) {
        // can be launched in a separate asynchronous job
//        Result<LoggedInUser> result = loginRepository.login(username, password);

        doLoginAction(username, password);

//        if (result instanceof Result.Success) {
//            LoggedInUser data = ((Result.Success<LoggedInUser>) result).getData();
//            loginResult.setValue(new LoginResult(new LoggedInUserView(data.getDisplayName())));
//        } else {
//            loginResult.setValue(new LoginResult(R.string.login_failed));
//        }
    }

    /**
     * TODO MOVE METHOD TO LOGOINDATASOURCE.JAVA
     * @param username
     * @param password
     */
    private void doLoginAction(String username, String password) {
        try {
            username = "phw82@sohu.com";
            password = "111111";
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("email", username);
            jsonObject.put("password", password);
            LogUtil.i("------postData:$jsonObject");

            AndroidNetworking.post(ConstantData.SIGN_IN)
                    .addJSONObjectBody(jsonObject)
                    .setTag("login")
                    .setPriority(Priority.MEDIUM).setExecutor(Executors.newSingleThreadExecutor())

                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // do anything with response
                            LogUtil.i("------------------" + response.toString());
                            LoggedInUser fakeUser = new LoggedInUser(
                                            java.util.UUID.randomUUID().toString(),
                                            "Jane Doe");
                            Result result =  new Result.Success<>(fakeUser);
                            LoggedInUser data = ((Result.Success<LoggedInUser>) result).getData();
                            loginResult.postValue(new LoginResult(new LoggedInUserView(data.getUserName())));
                        }
                        @Override
                        public void onError(ANError error) {
                            // handle error
                            LogUtil.i("------------------" + error.toString());
//                            loginResult.setValue(new LoginResult(R.string.login_failed));
                            loginResult.postValue(new LoginResult(R.string.login_failed));
                        }
                    });


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}