package com.sag.sagpro.activities;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.sag.sagpro.BaseActivity;
import com.sag.sagpro.R;
import com.sag.sagpro.databinding.ActivityAddressBinding;
import com.sag.sagpro.databinding.ActivityOrderBinding;

public class OrdersActivity extends BaseActivity {

    private ActivityOrderBinding binding;
    NavController navController = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        navController = Navigation.findNavController(this, R.id.nav_host_fragment_order);
        navController.navigate(R.id.item_navigation_order_list);

//        showBackArraw(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        //user click back button
        if (item.getItemId() == android.R.id.home) {
            OrdersActivity.this.finish();
        }

        return super.onOptionsItemSelected(item);
    }
}