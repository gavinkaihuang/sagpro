package com.sag.sagpro;

import android.app.Application;

import com.androidnetworking.AndroidNetworking;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import okhttp3.OkHttpClient;

public class SAGApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    private void init() {
        AndroidNetworking.initialize(getApplicationContext());

        // Adding an Network Interceptor for Debugging purpose :
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();
        AndroidNetworking.initialize(this.getApplicationContext(), okHttpClient);
    }
}
