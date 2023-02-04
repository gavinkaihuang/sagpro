package com.sag.sagpro.data;

import android.content.Context;

import com.sag.sagpro.data.model.LoggedInUser;
import com.sag.sagpro.utils.LoggedInUserHelper;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
public class LoginRepository {

    private static volatile LoginRepository instance;

    private LoginDataSource dataSource;

    // If user credentials will be cached in local storage, it is recommended it be encrypted
    // @see https://developer.android.com/training/articles/keystore
    private LoggedInUser user = null;

    // private constructor : singleton access
    private LoginRepository(LoginDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static LoginRepository getInstance(LoginDataSource dataSource) {
        if (instance == null) {
            instance = new LoginRepository(dataSource);
        }
        return instance;
    }

    public boolean isLoggedIn() {
        return user != null;
    }

    public void logout() {
        user = null;
        dataSource.logout();
    }

//    private void setLoggedInUser(LoggedInUser user) {
//        this.user = user;
//        // If user credentials will be cached in local storage, it is recommended it be encrypted
//        // @see https://developer.android.com/training/articles/keystore
//    }

//    /**
//     * TODO now not use this function
//     * @param username
//     * @param password
//     * @return
//     */
//    private Result<LoggedInUser> login(String username, String password) {
//        // handle login
//        Result<LoggedInUser> result = dataSource.login(username, password);
//        if (result instanceof Result.Success) {
//            setLoggedInUser(((Result.Success<LoggedInUser>) result).getData());
//        }
//        return result;
////        dataSource.doLoginAction(username, password, LoginRepository.this);
//    }

    public void setLoginuser(Context context, LoggedInUser loggedInUser) {
        this.user = loggedInUser;

        //save to local storeage
        LoggedInUserHelper.saveUserToLocal(context, loggedInUser);
//        LocalDataSaver localDataSaver = new LocalDataSaver(context, ConstantData.SHARE_DATA);
//        localDataSaver.putString("user_id", user.getUserId());
//        localDataSaver.putString("username", user.getUserName());
//        localDataSaver.putString("password", user.getPassword());
//        localDataSaver.putString("token", user.getToken());
//        localDataSaver.putString("expireDate", user.getExpireDate());
    }
//
//    public Result<LoggedInUser> loginCallBack(Result result) {
//        if (resultresult instanceof Result.Success) {
//            setLoggedInUser(((Result.Success<LoggedInUser>) result).getData());
//        }
//        return result;
//    }
}