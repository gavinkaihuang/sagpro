package com.sag.sagpro.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.sag.sagpro.BaseActivity;
import com.sag.sagpro.MainActivity;
import com.sag.sagpro.R;

public class SplashActivity extends BaseActivity {

    private static int SPLASH_SCREEN_TIMEOUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalsh);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_SCREEN_TIMEOUT);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}