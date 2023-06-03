package com.sag.sagpro.ui.account;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.sag.sagpro.BaseActivity;
import com.sag.sagpro.R;
import com.sag.sagpro.activities.AddressActivity;
import com.sag.sagpro.activities.CartListActivity;
import com.sag.sagpro.activities.OrdersActivity;
import com.sag.sagpro.data.model.LoggedInUser;
import com.sag.sagpro.databinding.FragmentAccountBinding;
import com.sag.sagpro.utils.LogUtils;
import com.sag.sagpro.utils.LoggedInUserHelper;

public class AccountFragment extends Fragment {

    private FragmentAccountBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AccountViewModel notificationsViewModel =
                new ViewModelProvider(this).get(AccountViewModel.class);

        binding = FragmentAccountBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        LoggedInUser loggedInUser = LoggedInUserHelper.getLoggedInUser(getContext());
        final TextView userNameTextView = binding.userNameTextView;
        userNameTextView.setText(loggedInUser.getUserName());

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        onClickListeners();
    }

    public void onClickListeners() {
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (LoggedInUserHelper.isUserLoginedIn(getContext())) {
                    LoggedInUserHelper.clearUserToLocal(getActivity());
                    updateViewsForNoLoginUser();
                } else {
                    Activity activity = getActivity();
                    if (activity instanceof BaseActivity) {
                        ((BaseActivity) activity).redirectToLogin();;
                    }
                }
            }
        });

        binding.shipImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), AddressActivity.class);
                startActivity(intent);
            }
        });
        binding.payImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), CartListActivity.class);
                startActivity(intent);
            }
        });
        binding.reciveImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), OrdersActivity.class);
                startActivity(intent);
            }
        });
    }

    public void updateUserUI() {
        if (LoggedInUserHelper.isUserLoginedIn(getContext()))
            updateViewsForLoginUser();
        else
            updateViewsForNoLoginUser();
    }

    private void updateViewsForLoginUser() {

        LoggedInUser loggedInUser = LoggedInUserHelper.getLoggedInUser(getContext());
        binding.button.setText(getResources().getString(R.string.account_login_out));
        binding.userNameTextView.setText(loggedInUser.getUserName());
        binding.userInfoTextView.setText("");
        binding.imageView.setImageResource(R.drawable.default_user_icon);
//        binding.imageView.setErrorImageResId(R.drawable.tab_mine);

    }

    private void updateViewsForNoLoginUser() {
        binding.button.setText(getResources().getString(R.string.account_login_in));
        binding.userNameTextView.setText(getResources().getString(R.string.account_default_user));
        binding.userInfoTextView.setText("");
        binding.imageView.setImageResource(R.drawable.tab_mine);
//        binding.imageView.setErrorImageResId(R.drawable.tab_mine);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUserUI();
        LogUtils.i("AccountFragment onResume");
    }
}