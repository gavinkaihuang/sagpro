package com.sag.sagpro.utils;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

public class PramsUtils {

    public static JSONObject getRequestParamsRoot(Context context) {
        JSONObject jsonObject = new JSONObject();
//        "language":"en","app":"ios","version":"1.0.0","token":"$Pe!nmRFNhbfUdg9VD5CJWjZMls%uSoO"
        //String language = Locale.getDefault().getLanguage();
        try {
            jsonObject.put("language", Locale.getDefault().getLanguage());
            jsonObject.put("app", "android");
            jsonObject.put("version", VersionUtils.getPackageInfo(context, VersionUtils.VERSION_NAME));
            jsonObject.put("token", LoggedInUserHelper.getToken(context));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
