package com.sag.sagpro.activities;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.sag.sagpro.BaseActivity;
import com.sag.sagpro.R;
import com.sag.sagpro.databinding.ActivityAddressBinding;

public class AddressActivity extends BaseActivity {

    private ActivityAddressBinding binding;
    NavController navController = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_address_list);

//        Intent intent =  getIntent();
//        Bundle bundles = intent.getExtras();
//        String cid = intent.getStringExtra(ProductListFragment.PARAMS_CID);
//        String cname=intent.getStringExtra(ProductListFragment.PARAMS_CNAME);


//        //反射方式无法传递参数，改为老方法
//        getSupportFragmentManager()
//                .beginTransaction()
//                .add(R.id.container, AddressListFragment.newInstance())
//                .commit();

        binding = ActivityAddressBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        navController = Navigation.findNavController(this, R.id.nav_host_fragment_address);
        navController.navigate(R.id.item_navigation_address_list);

//        showBackArraw(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        //user click back button
        if (item.getItemId() == android.R.id.home) {
            AddressActivity.this.finish();
        }

        return super.onOptionsItemSelected(item);
    }
}