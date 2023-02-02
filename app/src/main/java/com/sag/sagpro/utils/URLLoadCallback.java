package com.sag.sagpro.utils;

import org.json.JSONObject;

public interface URLLoadCallback {

    public void successCallBack(JSONObject result);
    public Exception failueCallBack(Exception exception);
}
