package com.sag.sagpro.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtils {

    /**
     *
     * @param context
     * @param value
     */
    public static void showToast(Context context, String value){
        showToast(context, value);
    }

    /**
     *
     * @param context
     * @param value
     * @param length
     */
    public static void showToast(Context context, String value, int length){
        Toast toast=Toast.makeText(context, value, Toast.LENGTH_SHORT);
        toast.show();
    }
}
