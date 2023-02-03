package com.sag.sagpro.ui.messages.placeholder;

import com.sag.sagpro.ui.products.placeholder.ProductPlaceholderItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 */
public class MessagePlaceholderContent {

    /**
     * An array of sample (placeholder) items.
     */
    public  List<MessagePlaceholderItem> ITEMS = new ArrayList<MessagePlaceholderItem>();

    /**
     * A map of sample (placeholder) items, by ID.
     */
    public  Map<String, MessagePlaceholderItem> ITEM_MAP = new HashMap<String, MessagePlaceholderItem>();


    public  void addItem(MessagePlaceholderItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.getMid(), item);
    }

    public MessagePlaceholderItem getItem(int position) {
        if (ITEMS != null && (ITEMS.size() >= position + 1))
            return ITEMS.get(position);
        return null;
    }
}