package com.sag.sagpro.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Bundle;
import android.view.MenuItem;

import com.facebook.stetho.common.LogUtil;
import com.sag.sagpro.BaseActivity;
import com.sag.sagpro.R;
import com.sag.sagpro.databinding.ActivityCartListBinding;
import com.sag.sagpro.databinding.ActivityMainBinding;

public class CartListActivity extends BaseActivity {

    private ActivityCartListBinding binding;
    NavController navController = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        navController = Navigation.findNavController(this, R.id.nav_cart_list);

        showBackArraw(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        //user click back button
        if (item.getItemId() == android.R.id.home) {
            CartListActivity.this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Ca
    }
}