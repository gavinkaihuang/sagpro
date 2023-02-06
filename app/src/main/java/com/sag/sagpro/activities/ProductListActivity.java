package com.sag.sagpro.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.sag.sagpro.BaseActivity;
import com.sag.sagpro.R;
import com.sag.sagpro.databinding.ActivityCartListBinding;
import com.sag.sagpro.databinding.ActivityProductListBinding;
import com.sag.sagpro.ui.products.ProductListFragment;

public class ProductListActivity extends BaseActivity {

    private ActivityProductListBinding binding;
    NavController navController = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        Intent intent =  getIntent();
        Bundle bundles = intent.getExtras();
        String cid = intent.getStringExtra(ProductListFragment.PARAMS_CID);
        String cname=intent.getStringExtra(ProductListFragment.PARAMS_CNAME);

//        binding = ActivityProductListBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
        //反射方式无法传递参数，改为老方法
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, ProductListFragment.newInstance(cid, cname))
                .commit();


//        navController = Navigation.findNavController(this, R.id.nav_product_list);
//        navController.navigate(R.id.item_navigation_product_list, bundles);
//        navController.

//        String cid = savedInstanceState.getString(ProductListFragment.PARAMS_CID);
//        String cname = savedInstanceState.getString(ProductListFragment.PARAMS_CNAME);
//        Bundle bundle
//
//        ProductListFragment productListFragment = ProductListFragment.newInstance(cid, cname);
//
////        productListFragment.getView()
////        setContentView(productListFragment.getView());
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
////        transaction.add(R.id.container, productListFragment, ProductListFragment.class.getName());
//        transaction.replace(R.id.container, productListFragment);
//        transaction.commit();

//        binding = ActivityProductListBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//        navController = Navigation.findNavController(this, R.id.nav_product_list);
        showBackArraw(true);


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        //user click back button
        if (item.getItemId() == android.R.id.home) {
            ProductListActivity.this.finish();
        }

        return super.onOptionsItemSelected(item);
    }
}