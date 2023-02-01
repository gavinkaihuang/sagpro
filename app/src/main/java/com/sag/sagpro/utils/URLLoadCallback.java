package com.sag.sagpro.utils;

import android.graphics.Bitmap;

import org.json.JSONObject;

public interface URLLoadCallback {

    public void successCallBack(JSONObject result);
    public Exception failedClassBack(Exception exception);
}
