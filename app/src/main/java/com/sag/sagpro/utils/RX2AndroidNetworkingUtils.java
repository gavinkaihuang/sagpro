package com.sag.sagpro.utils;

import android.util.Log;


import com.androidnetworking.interfaces.AnalyticsListener;
import com.facebook.stetho.common.LogUtil;
import com.rx2androidnetworking.Rx2AndroidNetworking;
import com.sag.sagpro.ConstantData;

import org.json.JSONObject;

import java.util.Map;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RX2AndroidNetworkingUtils {

    public static void getForData(String url, Map<String, String> params, SingleObserver<JSONObject> observer) {
        Rx2AndroidNetworking.get(url)
                .addPathParameter(params)
                .setUserAgent("getAnUser")
                .build()
                .setAnalyticsListener(new AnalyticsListener() {
                    @Override
                    public void onReceived(long timeTakenInMillis, long bytesSent, long bytesReceived, boolean isFromCache) {
                        LogUtils.i("timeTakenInMillis : " + timeTakenInMillis);
                        LogUtil.i("bytesSent : " + bytesSent);
                        LogUtil.i("bytesReceived : " + bytesReceived);
                        LogUtil.i("isFromCache : " + isFromCache);
                    }
                })
                .getJSONObjectSingle()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public static void postForData(String url, JSONObject params, SingleObserver<JSONObject> observer) {
        Rx2AndroidNetworking.post(url)
                .addJSONObjectBody(params)
                .setUserAgent("getAnUser")
                .build()
                .setAnalyticsListener(new AnalyticsListener() {
                    @Override
                    public void onReceived(long timeTakenInMillis, long bytesSent, long bytesReceived, boolean isFromCache) {
                        LogUtil.i("timeTakenInMillis : " + timeTakenInMillis);
                        LogUtil.i("bytesSent : " + bytesSent);
                        LogUtil.i("bytesReceived : " + bytesReceived);
                        LogUtil.i("isFromCache : " + isFromCache);
                    }
                })
                .getJSONObjectSingle()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    private static void getAnUserDemo() {

        Rx2AndroidNetworking.get(ConstantData.HOME_IMGS)
                .addPathParameter("userId", "1")
                .setUserAgent("getAnUser")
                .build()
                .setAnalyticsListener(new AnalyticsListener() {
                    @Override
                    public void onReceived(long timeTakenInMillis, long bytesSent, long bytesReceived, boolean isFromCache) {
                        LogUtil.i(" timeTakenInMillis : " + timeTakenInMillis);
                        LogUtil.i(" bytesSent : " + bytesSent);
                        LogUtil.i(" bytesReceived : " + bytesReceived);
                        LogUtil.i(" isFromCache : " + isFromCache);
                    }
                })
//                .getObjectSingle(User.class)
                .getJSONObjectSingle()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<JSONObject>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        System.out.println("-------" + d);
                    }

                    @Override
                    public void onSuccess(JSONObject jsonObject) {
                        System.out.println("-------" + jsonObject);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

//    public  static void testMathod() {
//        RxAndroidNetworking.get(BASE_URL + GET_JSON_OBJECT)
//                .addPathParameter("userId", "1")
//                .setUserAgent("getAnUser")
//                .build()
//                .setAnalyticsListener(new AnalyticsListener() {
//                    @Override
//                    public void onReceived(long timeTakenInMillis, long bytesSent, long bytesReceived, boolean isFromCache) {
//                        Log.d(TAG, " timeTakenInMillis : " + timeTakenInMillis);
//                        Log.d(TAG, " bytesSent : " + bytesSent);
//                        Log.d(TAG, " bytesReceived : " + bytesReceived);
//                        Log.d(TAG, " isFromCache : " + isFromCache);
//                    }
//                })
//                .getObjectObservable(User.class)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(new SignleObserver<JSONObject>() {
//
//                });
////                .subscribe(new Observer<User>() {
////                    @Override
////                    public void onCompleted() {
////                        Log.d(TAG, "onComplete Detail : getAnUser completed");
////                    }
////
////                    @Override
////                    public void onError(Throwable e) {
//////                        Utils.logError(TAG, e);
////                    }
////
////                    @Override
////                    public void onNext(User user) {
////                        Log.d(TAG, "onResponse isMainThread : " + String.valueOf(Looper.myLooper() == Looper.getMainLooper()));
////                        Log.d(TAG, "id : " + user.id);
////                        Log.d(TAG, "firstname : " + user.firstname);
////                        Log.d(TAG, "lastname : " + user.lastname);
////                    }
////                });
//    }
}
