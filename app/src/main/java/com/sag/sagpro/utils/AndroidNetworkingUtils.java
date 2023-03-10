package com.sag.sagpro.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.BitmapRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.facebook.stetho.common.LogUtil;

import org.json.JSONObject;

import java.util.concurrent.Executors;

public class AndroidNetworkingUtils {

    public static void loadImageFromURL(String imageURL, String tag, ImageLoadCallback imageLoadCallback) {
        AndroidNetworking.get(imageURL)
                .setTag(tag)
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
    private static void loadURL(String url, String tag,JSONObject paramsObj,  URLLoadCallback urlLoadCallback) {
        try {
//            JSONObject jsonObject = new JSONObject();
            LogUtil.i("------URL:" + url);
            LogUtil.i("------postData:" + paramsObj.toString());
            AndroidNetworking.post(url)
                    .addJSONObjectBody(paramsObj)
                    .setTag(tag)
                    .setPriority(Priority.MEDIUM)
                    .setExecutor(Executors.newSingleThreadExecutor())
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            LogUtil.i("------------------" + response.toString());
                            urlLoadCallback.successURLLoadedCallBack(response);
                        }
                        @Override
                        public void onError(ANError error) {
                            LogUtil.e("------------------" + error.toString());
                            urlLoadCallback.failueURLLoadedCallBack(error);
                        }
                    });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Check network is working
     * @param context
     * @return
     */
    public static boolean checkNetworkAvailable(Context context) {
//        Context context = activity.getApplicationContext();
        //????????????????????????????????????????????????Wi-Fi???net?????????????????????
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager == null) {
            return false;
        } else {
            //??????NetworkInfo??????
            NetworkInfo[] info = manager.getAllNetworkInfo();
            if (info != null && info.length > 0) {
                for (int i = 0; i < info.length; i++) {
//                    System.out.println(i + "??????" + info[i].getState());
//                    System.out.println(i + "??????" + info[i].getTypeName());

                    // ?????????????????????????????????????????????
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


}
