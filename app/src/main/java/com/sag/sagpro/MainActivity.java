package com.sag.sagpro;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.facebook.stetho.common.LogUtil;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationBarView;
import com.sag.sagpro.data.model.LoggedInUserHelper;
import com.sag.sagpro.databinding.ActivityMainBinding;
import com.sag.sagpro.ui.login.LoginActivity;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    NavController navController = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.item_navigation_homeitem, R.id.item_navigation_messages, R.id.item_navigation_account)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
        navView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item_navigation_homeitem:
                        navController.navigate(R.id.item_navigation_homeitem);
//                        navController.addToBackStack
                    break;
                case R.id.item_navigation_messages:
                    navController.navigate(R.id.item_navigation_messages);
                    break;
                case R.id.item_navigation_account:

                    boolean isUserLoggedIn =  LoggedInUserHelper.isUserLoginedIn(MainActivity.this);
                    if (isUserLoggedIn) {
                        navController.navigate(R.id.item_navigation_account);
                    } else {
                        //ask user to login
                        Intent intent = new Intent();
                        intent.setClass(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                    break;
                 };
                return  false;
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);

//        ActionBar actionBar = getSupportActionBar();
//        actionBar.set
    }

    public void navigationTo(int id) {
        navController.navigate(id);

    }


    public int getFooterHeight() {
        if (binding.navView != null)
            return binding.navView.getHeight();
        return 0;
    }
}

