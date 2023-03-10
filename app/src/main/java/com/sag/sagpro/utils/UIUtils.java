package com.sag.sagpro.utils;

import android.content.Context;
import android.widget.LinearLayout;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;

import com.sag.sagpro.R;

public class UIUtils {

    /**
     * Generate RecycleView Item Decoration
     * @param context
     * @return
     */
    public static DividerItemDecoration getDividerItemLineDecoration(Context context) {
        DividerItemDecoration decoration = new DividerItemDecoration(context, LinearLayout.VERTICAL);
        decoration.setDrawable(ContextCompat.getDrawable(context, R.drawable.recycler_item_divider_line));
        return decoration;
    }

    public static DividerItemDecoration getDividerItemBoxDecoration(Context context) {
        DividerItemDecoration decoration = new DividerItemDecoration(context, LinearLayout.VERTICAL);
        decoration.setDrawable(ContextCompat.getDrawable(context, R.drawable.recycler_item_divider_box));
        return decoration;
    }
}
