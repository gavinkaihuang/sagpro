package com.sag.sagpro.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.sag.sagpro.R;

public class SepLineView extends LinearLayout {

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.layout_item_line, this);
    }

    public SepLineView(Context context) {
        super(context);

        init();
    }

    public SepLineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    public SepLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    public SepLineView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        init();
    }


}
