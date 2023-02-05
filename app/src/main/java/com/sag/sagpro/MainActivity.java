package com.sag.sagpro;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationBarView;
import com.sag.sagpro.activities.CartListActivity;
import com.sag.sagpro.utils.LoggedInUserHelper;
import com.sag.sagpro.databinding.ActivityMainBinding;
import com.sag.sagpro.ui.login.LoginActivity;
import com.sag.sagpro.utils.AndroidNetworkingUtils;

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
                boolean isUserLoggedIn =  LoggedInUserHelper.isUserLoginedIn(MainActivity.this);
                switch (item.getItemId()) {
                    case R.id.item_navigation_homeitem:
                        navController.navigate(R.id.item_navigation_homeitem);
//                        navController.addToBackStack
                    break;
                case R.id.item_navigation_messages:

                    if (isUserLoggedIn) {
                        navController.navigate(R.id.item_navigation_messages);
                    } else {
                        //ask user to login
                        redirectToLogin();
                    }
                    break;
                case R.id.item_navigation_account:
                    if (isUserLoggedIn) {
                        navController.navigate(R.id.item_navigation_account);
                    } else {
                        //ask user to login
                        redirectToLogin();
                    }
                    break;
                 };
                return  false;
            }
        });

        if (!AndroidNetworkingUtils.checkNetworkAvailable(this)) {
            Toast.makeText(this, getResources().getString(R.string.error_no_network), Toast.LENGTH_SHORT).show();
        }

//        try {
//            getSupportActionBar().setDisplayShowHomeEnabled(true);
//            getSupportActionBar().setDisplayUseLogoEnabled(true);
////            getSupportActionBar().setLogo(R.drawable.tab_cart);
////            getSupportActionBar().setIcon(R.drawable.tab_cart);
////            getSupportActionBar().setCustomView();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    MenuItem moreItem;

    /**
     * Action Bar setter start
     */

    /**
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        moreItem = menu.add(Menu.NONE, Menu.FIRST, Menu.FIRST, null);
        moreItem.setIcon(R.drawable.xml_cart);
        moreItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
//        moreItem.setOn
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Handle cart on clicked
     * @param item
     * @return
     */
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        boolean isUserLoggedIn =  LoggedInUserHelper.isUserLoginedIn(MainActivity.this);
        if (isUserLoggedIn) {
            //redirect to cart
//            Toast.makeText(MainActivity.this, R.string.account_home_page).show();
//            navController.navigate(R.id.item_navigation_cart_list);
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, CartListActivity.class);
            startActivity(intent);
        } else {
            //ask user to login
            redirectToLogin();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Action Bar setter end
     */


    //    public void onComposeAction(MenuItem mi) {
//        // handle click here
//    }

//    @Nullable
//    @Override
//    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
//        return super.onCreateView(name, context, attrs);
//
////        ActionBar actionBar = getSupportActionBar();
////        actionBar.set
//
////        if (!AndroidNetworkingUtils.checkNetworkAvailable(context)) {
////            Toast.makeText(context, "无网络！", Toast.LENGTH_SHORT).show();
////        }
//
//
//
//    }



    public void navigationTo(int id) {
        navController.navigate(id);

    }


    public int getFooterHeight() {
        if (binding.navView != null)
            return binding.navView.getHeight();
        return 0;
    }

    public void redirectToLogin() {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}

