package com.sag.sagpro.utils;

import android.graphics.Bitmap;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.BitmapRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.facebook.stetho.common.LogUtil;
import com.sag.sagpro.ConstantData;

import org.json.JSONObject;

import java.util.concurrent.Executors;

public class AndroidNetworkingUtils {

    public static void loadImageFromURL(String imageURL, ImageLoadCallback imageLoadCallback) {
        AndroidNetworking.get(imageURL)
                .setTag("imageRequestTag")
                .setPriority(Priority.MEDIUM)
                .setBitmapMaxHeight(100)
                .setBitmapMaxWidth(100)
                .setBitmapConfig(Bitmap.Config.ARGB_8888)
                .build()
                .getAsBitmap(new BitmapRequestListener() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        imageLoadCallback.loadImageSucceed(bitmap);
                    }

                    @Override
                    public void onError(ANError error) {
                        imageLoadCallback.loadImageFailed(error);
                    }
                });
    }

    /**
     * Load URL from server
     * @param url
     * @param tag
     * @param paramsObj
     * @param urlLoadCallback
     */
    public static void loadURL(String url, String tag,JSONObject paramsObj,  URLLoadCallback urlLoadCallback) {
        try {
//            JSONObject jsonObject = new JSONObject();
            LogUtil.i("------URL:" + url);
            LogUtil.i("------postData:" + paramsObj.toString());
            AndroidNetworking.post(ConstantData.CATEGORIES)
                    .addJSONObjectBody(paramsObj)
                    .setTag(tag)
                    .setPriority(Priority.MEDIUM)
                    .setExecutor(Executors.newSingleThreadExecutor())
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            LogUtil.i("------------------" + response.toString());
                            urlLoadCallback.successCallBack(response);
                        }
                        @Override
                        public void onError(ANError error) {
                            LogUtil.e("------------------" + error.toString());
                            urlLoadCallback.failedClassBack(error);
                        }
                    });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
