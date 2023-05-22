package com.sag.sagpro.ui.placeholder;

import com.sag.sagpro.ui.messages.placeholder.MessagePlaceholderItem;

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
public class PlaceholderContent {

    public List<PlaceholderItem> getITEMS() {
        return ITEMS;
    }

    /**
     * An array of sample (placeholder) items.
     */
    private  List<PlaceholderItem> ITEMS = new ArrayList<PlaceholderItem>();

    /**
     * A map of sample (placeholder) items, by ID.
     */
//    public  Map<String, MessagePlaceholderItem> ITEM_MAP = new HashMap<String, MessagePlaceholderItem>();


    public  void addItem(PlaceholderItem item) {
        ITEMS.add(item);
//        ITEM_MAP.put(item.getMid(), item);
    }

    public PlaceholderItem getItem(int position) {
        if (ITEMS != null && (ITEMS.size() >= position + 1))
            return ITEMS.get(position);
        return null;
    }
}