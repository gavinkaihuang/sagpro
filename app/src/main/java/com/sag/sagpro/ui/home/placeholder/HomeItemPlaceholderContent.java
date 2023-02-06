package com.sag.sagpro.ui.home.placeholder;

import com.sag.sagpro.ui.home.LoopImageBean;
import com.sag.sagpro.ui.products.placeholder.ProductPlaceholderItem;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class HomeItemPlaceholderContent {

    /**
     * An array of sample (placeholder) items.
     */
    public  List<HomeItemPlaceholderItem> ITEMS = new ArrayList<HomeItemPlaceholderItem>();

    /**
     * A map of sample (placeholder) items, by ID.
     */
    public  Map<String, HomeItemPlaceholderItem> ITEM_MAP = new HashMap<String, HomeItemPlaceholderItem>();

    public  ArrayList<LoopImageBean> LOOP_IMAGES = new ArrayList<LoopImageBean>();


    public void addItem(HomeItemPlaceholderItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.getCid(), item);
    }

    public void addImageBean(LoopImageBean imageBean) {
        LOOP_IMAGES.add(imageBean);
    }


    /**
     * Add header view for first line
     * So we need minus 1
     * @param position
     * @return
     */
    public HomeItemPlaceholderItem getItem(int position) {
        position -= 1;
        if (ITEMS != null && (ITEMS.size() >= position + 1))
            return ITEMS.get(position);
        return null;
    }

}