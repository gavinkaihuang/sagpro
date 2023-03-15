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


    public List<CartPlaceholderItem> ITEMS = new ArrayList<CartPlaceholderItem>();


    public Map<String, CartPlaceholderItem> ITEM_MAP = new HashMap<String, CartPlaceholderItem>();

    private String totalPrice = null;

    public void setTotalPrice(String price) {
        totalPrice = price;
    }

    public String getTotalPrice() {
        if (totalPrice == null)
            return "0.0";
        return totalPrice;
//        Double price = new Double(totalPrice);
//        return price;
    }

    public void addItem(CartPlaceholderItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.getPid(), item);
    }

    public void clearItems() {
        ITEMS.clear();
        ITEM_MAP.clear();
    }


}