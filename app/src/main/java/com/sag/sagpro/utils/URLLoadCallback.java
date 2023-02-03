package com.sag.sagpro.utils;

import org.json.JSONObject;

public interface URLLoadCallback {

    public void successURLLoadedCallBack(JSONObject result);
    public Exception failueURLLoadedCallBack(Exception exception);
}
