package com.sag.sagpro.ui.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.sag.sagpro.R;

public class ItemNoAdjustView extends LinearLayout {

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.layout_item_no_ajust, this);
    }

    public ItemNoAdjustView(Context context) {
        super(context);

        init();
    }

    public ItemNoAdjustView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    public ItemNoAdjustView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    public ItemNoAdjustView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        init();
    }


}
