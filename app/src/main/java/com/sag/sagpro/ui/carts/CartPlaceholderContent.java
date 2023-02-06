package com.sag.sagpro.ui.carts;

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
public class CartPlaceholderContent {


    public static List<CartPlaceholderItem> ITEMS = new ArrayList<CartPlaceholderItem>();


    public static Map<String, CartPlaceholderItem> ITEM_MAP = new HashMap<String, CartPlaceholderItem>();

    public static void addItem(CartPlaceholderItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.getPid(), item);
    }


}