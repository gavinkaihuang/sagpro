package com.sag.sagpro.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Bundle;

import com.sag.sagpro.R;
import com.sag.sagpro.databinding.ActivityCartListBinding;
import com.sag.sagpro.databinding.ActivityMainBinding;

public class CartListActivity extends AppCompatActivity {

    private ActivityCartListBinding binding;
    NavController navController = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_cart_list);
        binding = ActivityCartListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        navController = Navigation.findNavController(this, R.id.nav_cart_list);
//        navController.navigate(R.id.item_navigation_cart_list);
//        navController.
    }
}