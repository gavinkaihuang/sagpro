package com.sag.sagpro.ui.products.placeholder;

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
public class ProductPlaceholderContent {

    /**
     * An array of sample (placeholder) items.
     */
    public  List<ProductPlaceholderItem> ITEMS = new ArrayList<ProductPlaceholderItem>();

    /**
     * A map of sample (placeholder) items, by ID.
     */
    public  Map<String, ProductPlaceholderItem> ITEM_MAP = new HashMap<String, ProductPlaceholderItem>();


    public  void addItem(ProductPlaceholderItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.getPid(), item);
    }

    public ProductPlaceholderItem getItem(int position) {
        if (ITEMS != null && (ITEMS.size() >= position + 1))
            return ITEMS.get(position);
        return null;
    }

//    private static PlaceholderItem createPlaceholderItem(int position) {
//        return new PlaceholderItem(String.valueOf(position), "Item " + position, makeDetails(position));
//    }
//



}