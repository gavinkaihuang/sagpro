package com.sag.sagpro.data;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.facebook.stetho.common.LogUtil;
import com.sag.sagpro.ConstantData;
import com.sag.sagpro.data.model.LoggedInUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.Executors;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {


//    public Result<LoggedInUser> login(String username, String password) {
//
//        try {
//            // TODO: handle loggedInUser authentication
//            loginAction();
//            LogUtil.i("------------------now return result");
//            LoggedInUser fakeUser =
//                    new LoggedInUser(
//                            java.util.UUID.randomUUID().toString(),
//                            "Jane Doe");
//            return new Result.Success<>(fakeUser);
//        } catch (Exception e) {
//            return new Result.Error(new IOException("Error logging in", e));
//        }
//    }

    public void logout() {
        // TODO: revoke authentication
    }

    public Result<LoggedInUser> login(String userName, String password) {

        try {
            // TODO: handle loggedInUser authentication
            LogUtil.i("------------------now return result");
            LoggedInUser fakeUser =
                    new LoggedInUser(
                            java.util.UUID.randomUUID().toString(),
                            "Jane Doe");
            return new Result.Success<>(fakeUser);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

//    public void doLoginAction(String username, String password, LoginRepository loginRepository) {
//        try {
//            username = "phw82@sohu.com";
//            password = "111111";
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("email", username);
//            jsonObject.put("password", password);
//            LogUtil.i("------postData:$jsonObject");
//
//            AndroidNetworking.post(ConstantData.SIGN_IN)
//                .addJSONObjectBody(jsonObject)
//                .setTag("login")
//                .setPriority(Priority.MEDIUM).setExecutor(Executors.newSingleThreadExecutor())
//
//                .build()
//                    .getAsJSONObject(new JSONObjectRequestListener() {
//                                     @Override
//                                     public void onResponse(JSONObject response) {
//                                         // do anything with response
//                                         LogUtil.i("------------------" + response.toString());
//                                         loginRepository.loginCallBack();
//                                         login(response);
//                                     }
//                                     @Override
//                                     public void onError(ANError error) {
//                                         // handle error
//                                         LogUtil.i("------------------" + error.toString());
//                                     }
//                                 });
//
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//    }
//        AndroidNetworking.post("https://fierce-cove-29863.herokuapp.com/createAnUser")
//                .addBodyParameter("firstname", "Amit")
//                .addBodyParameter("lastname", "Shekhar")
//                .setTag("test")
//                .setPriority(Priority.MEDIUM)
//                .build()
//                .getAsJSONObject(new JSONObjectRequestListener() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        // do anything with response
//                    }
//                    @Override
//                    public void onError(ANError error) {
//                        // handle error
//                    }
//                });

    //    fun loginAction(username: String, password: String): Result<LoggedInUser> {
//        try {
//            // TODO: handle loggedInUser authentication
//            val username = "phw82@sohu.com"
//            val password = "111111"
//            val jsonObject = JSONObject()
//            jsonObject.put("email", username)
//            jsonObject.put("password", password)
////            postData = jsonObject.toString()
//            LogUtil.i("------postData:$jsonObject")
//
//            //                .getAsObject(LoggedInUser::class, ParsedRequestListener<LoggedInUser>) {
////
////            }
//            var fakeUser: LoggedInUser? = null
//            AndroidNetworking.post(Constant.SIGN_IN)
//                .addJSONObjectBody(jsonObject)
//                .setTag("login")
//                .setPriority(Priority.MEDIUM)
//                .build()
//                .getAsJSONObject(object : JSONObjectRequestListener {
//                    override fun onResponse(response: JSONObject) {
//                        // do anything with response
//                        val code = response.get("code")
//                        if (code.equals("200")) {
//                            val dataJson = response.getJSONObject("data")
//                            var token: String = dataJson.getString("token")
//                            val expiredate: String = dataJson.getString("expiredate")
//                            fakeUser = LoggedInUser(java.util.UUID.randomUUID().toString(), "Jane Doe", token, expiredate)
//
//                            LogUtil.i("----------token is $token and expiredata is $expiredate" )
//                        } else {
//                            LogUtil.i("status is not correct")
//                        }
//                        println(response)
//                    }
//
//                    override fun onError(error: ANError) {
//                        // handle error
//                        println(error)
//                    }
//                })
//
//            LogUtil.i("----------try to return fakeUser2" )
//            val fakeUser2 = LoggedInUser(java.util.UUID.randomUUID().toString(), "Jane Doe", "1", "2")
//            return Result.Success(fakeUser2)
//        } catch (e: Throwable) {
//            return Result.Error(IOException("Error logging in", e))
//        }
//    }

}