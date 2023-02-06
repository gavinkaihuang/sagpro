package com.sag.sagpro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.sag.sagpro.activities.LoginActivity;

public class BaseActivity extends AppCompatActivity {

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
}