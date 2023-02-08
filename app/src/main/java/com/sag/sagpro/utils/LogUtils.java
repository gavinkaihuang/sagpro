package com.sag.sagpro.utils;

import com.facebook.stetho.common.LogUtil;

public class LogUtils {

    private static String HEADER = "------------";

    public static void i(String message) {
        LogUtil.i(HEADER + message);
    }

    public static void d(String message) {
        LogUtil.d(HEADER + message);
    }

    public static void e(String message) {
        LogUtil.e(HEADER + message);
    }

    public static void v(String message) {
        LogUtil.v(HEADER + message);
    }
}
