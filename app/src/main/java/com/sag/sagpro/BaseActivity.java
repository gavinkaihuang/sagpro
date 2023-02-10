package com.sag.sagpro;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import com.sag.sagpro.activities.LoginActivity;

public class BaseActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void redirectToLogin() {
        Intent intent = new Intent();
        intent.setClass(BaseActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    public void showBackArraw(boolean isShow) {
        if (!isShow)
            return;
        try {
            //show back button
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    private synchronized void showProgressDialog() {
//        progressDialog = new ProgressDialog(this);
//        progressDialog.setTitle("请稍后");
//        progressDialog.setMessage("正在努力加载页面");
//        progressDialog.setMax(100);
//        progressDialog.setProgress(0);
//        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//        progressDialog.show();
//    }
//
//    private synchronized void dismissProgressDialog() {
//        if (progressDialog != null && progressDialog.isShowing()) {
//            progressDialog.dismiss();
//        }
//    }
}