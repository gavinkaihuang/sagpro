package com.sag.sagpro

import android.app.Application
import com.androidnetworking.AndroidNetworking
import okhttp3.OkHttpClient
import com.facebook.stetho.okhttp3.StethoInterceptor


class SAGApplicaiton : Application() {

    override fun onCreate() {
        super.onCreate()
        init()
    }

    fun init() {
        AndroidNetworking.initialize(getApplicationContext());

        // Adding an Network Interceptor for Debugging purpose :
        val okHttpClient = OkHttpClient().newBuilder()
            .addNetworkInterceptor(StethoInterceptor())
            .build()
        AndroidNetworking.initialize(applicationContext, okHttpClient)
    }

}