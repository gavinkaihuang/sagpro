package com.sag.sagpro.ui.account;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.sag.sagpro.BaseActivity;
import com.sag.sagpro.R;
import com.sag.sagpro.data.model.LoggedInUser;
import com.sag.sagpro.utils.LogUtils;
import com.sag.sagpro.utils.LoggedInUserHelper;
import com.sag.sagpro.databinding.FragmentAccountBinding;

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

        onClickListeners();

        return root;
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
        updateViewsForLoginUser();
        LogUtils.i("AccountFragment onResume");
    }
}