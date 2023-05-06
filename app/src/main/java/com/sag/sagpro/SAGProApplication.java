package com.sag.sagpro;

import android.app.Application;

import com.androidnetworking.AndroidNetworking;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.paypal.checkout.PayPalCheckout;
import com.paypal.checkout.config.CheckoutConfig;
import com.paypal.checkout.config.Environment;
import com.paypal.checkout.config.PaymentButtonIntent;
import com.paypal.checkout.config.SettingsConfig;
import com.paypal.checkout.createorder.CurrencyCode;
import com.paypal.checkout.createorder.UserAction;

import okhttp3.OkHttpClient;

public class SAGProApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        init();
        initPaypal();
    }

    private void init() {
        AndroidNetworking.initialize(this);

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();
        AndroidNetworking.initialize(this.getApplicationContext(), okHttpClient);
    }

    private void initPaypal() {
        SettingsConfig settingsConfig = new SettingsConfig(true, false);
        String returnUrl = "com.sag.sagpro://paypalpay";
        CheckoutConfig checkoutConfig = new CheckoutConfig(
                this,
                 "AWwyvW8ZhWdvytFGXWKfq2kyNHBUnPmSOz-1VIHeig5jLwQymszWC4QzTYSQKOStbWqeeK6ryE8DWVLN",
                 Environment.SANDBOX,
                 CurrencyCode.USD,
                 UserAction.PAY_NOW,
                PaymentButtonIntent.CAPTURE,
                 returnUrl
        );
        PayPalCheckout.setConfig(checkoutConfig);
//        PayPalCheckout.setConfig(new CheckoutConfig(
//                this,
//                "PAYPAL_CLIENT_ID",
//                Environment.SANDBOX,
//                returnUrl,
//                CurrencyCode.USD,
//                UserAction.PAY_NOW
//        ));
    }

//    private void initPaypal() {
//        PayPalCheckout.setConfig(
//                checkoutConfig = CheckoutConfig(
//                        application = this,
//                        clientId = "PAYPAL_CLIENT_ID",
//                        environment = Environment.SANDBOX,
//                        currencyCode = CurrencyCode.USD,
//                        userAction = UserAction.PAY_NOW,
//                        settingsConfig = SettingsConfig(
//                                loggingEnabled = true,
//                                shouldFailEligibility = false
//                        ),
//                        returnUrl = "com.sag.sagpro://paypalpay"
//                )
//        )

        //        CheckoutConfig config = new CheckoutConfig(
//                application = this,
//                clientId = YOUR_CLIENT_ID,
//                environment = Environment.SANDBOX,
//                returnUrl = "${BuildConfig.APPLICATION_ID}://paypalpay",
//                currencyCode = CurrencyCode.USD,
//                userAction = UserAction.PAY_NOW,
//                settingsConfig = SettingsConfig(
//                        loggingEnabled = true
//                )
//        )
//        PayPalCheckout.setConfig(config)
//    }
}
