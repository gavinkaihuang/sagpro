package com.sag.sagpro.data.model;

import android.content.Context;

import com.sag.sagpro.ConstantData;
import com.sag.sagpro.data.LocalDataSaver;

public class LoggedInUserHelper {
    public static void saveUserToLocal(Context context, LoggedInUser loggedInUser) {
        LocalDataSaver localDataSaver = new LocalDataSaver(context, ConstantData.SHARE_DATA);
        localDataSaver.putString("userid", loggedInUser.getUserId());
        localDataSaver.putString("username", loggedInUser.getUserName());
        localDataSaver.putString("password", loggedInUser.getPassword());
        localDataSaver.putString("token", loggedInUser.getToken());
        localDataSaver.putString("expiredate", loggedInUser.getExpireDate());
    }

    public static boolean isUserLoginedIn(Context context) {
        LocalDataSaver localDataSaver = new LocalDataSaver(context, ConstantData.SHARE_DATA);
        String userName = localDataSaver.getString("username");
        if (userName != null && !"".equals(userName))
            return true;
        return false;
    }

    public static LoggedInUser getLoggedInUser(Context context) {
        LocalDataSaver localDataSaver = new LocalDataSaver(context, ConstantData.SHARE_DATA);
        String userName = localDataSaver.getString("username");
        if (userName != null && !"".equals(userName))
            return null;

        LoggedInUser loggedInUser = new LoggedInUser();
        loggedInUser.setUserId(localDataSaver.getString("userid"));
        loggedInUser.setUserName(localDataSaver.getString("username"));
        loggedInUser.setPassword(localDataSaver.getString("password"));
        loggedInUser.setToken(localDataSaver.getString("token"));
        loggedInUser.setExpireDate(localDataSaver.getString("expiredate"));
        return loggedInUser;
    }

    public static String getUserInfo(Context context, String key) {
        LocalDataSaver localDataSaver = new LocalDataSaver(context, ConstantData.SHARE_DATA);
        String value = localDataSaver.getString(key);
        return value;
    }
}
