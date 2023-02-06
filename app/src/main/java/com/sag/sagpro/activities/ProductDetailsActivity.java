package com.sag.sagpro.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.sag.sagpro.R;
import com.sag.sagpro.ui.products.ProductDetailFragment;
import com.sag.sagpro.ui.products.ProductListFragment;

public class ProductDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        Intent intent =  getIntent();
//        Bundle bundles = intent.getExtras();
        String pid = intent.getStringExtra(ProductDetailFragment.PARAMS_PRODUCT_ID);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, ProductDetailFragment.newInstance(pid))
                .commit();

    }
}