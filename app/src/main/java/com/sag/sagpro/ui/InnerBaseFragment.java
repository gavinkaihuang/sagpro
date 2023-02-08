package com.sag.sagpro.ui;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.sag.sagpro.MainActivity;
import com.sag.sagpro.utils.ScreenUtils;

import org.json.JSONObject;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

public abstract class InnerBaseFragment extends Fragment implements SingleObserver<JSONObject> {

    private boolean isUpdatePageFooterHeight = false;

    /**
     * make last list item full disabled
     * 用于嵌套在底部有Tab的Fragment留出底部tab的高度
     */
    protected void updatePageFooterHeight(View view) {
        if (isUpdatePageFooterHeight)//only update one time
            return;

        try {
            Activity activty = this.getActivity();
            if (activty instanceof MainActivity) {
                int footHeight = ((MainActivity) activty).getFooterHeight();
                ViewGroup.LayoutParams lp = view.getLayoutParams();
                int left = ScreenUtils.px2dip(getContext(), view.getPaddingLeft());
                int right = ScreenUtils.px2dip(getContext(), view.getPaddingRight());
                int top = ScreenUtils.px2dip(getContext(), view.getPaddingTop());
                int bottom = ScreenUtils.px2dip(getContext(), view.getPaddingBottom());
                view.setPadding(left, right, top,  footHeight);
            }
            isUpdatePageFooterHeight = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Handle Data Result,
     * RX2AndroidNetworkingUtils will call back in UI thread
     * @param result
     */
    protected void handleResultForUI(JSONObject result) {

    }

    /**
     * SingleObserver<JSONObject> mathod start
     */

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onSuccess(JSONObject jsonObject) {
        handleResultForUI(jsonObject);
    }

    @Override
    public void onError(Throwable e) {

    }

    /**
     * SingleObserver<JSONObject> mathod end
     */
}
