package com.sag.sagpro.interfaces;

import com.sag.sagpro.ui.ui.ItemNoAdjustView;

/**
 * 自定义控件ItemNoAdjustView 处理接口
 */
public interface NumberAdjustHandler {

    void handleMinus(ItemNoAdjustView view, int result);

    void handleAdd(ItemNoAdjustView view, int result);

}
