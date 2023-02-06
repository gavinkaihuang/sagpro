package com.sag.sagpro.interfaces;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

/**
 * use for recycle view
 */
public interface OnItemClickListener {

    void onItemClicked(View view, int position, int sign);
}
