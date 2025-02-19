package org.delaware.tools.CustomItems;

import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Set;

public class CustomItems extends ItemStack {
    public static HashMap<String, ItemStack> items = new HashMap<>();
    ItemStack item;
    public CustomItems(ItemStack item) {
        this.item = item;
    }
    public void addCustomItem(String key) {
        items.put(key, item);
    }
    public CustomItems getCustomItem(String key) {
        return new CustomItems(items.get(key));
    }
    public static Set<String> getAllCustomItems() {
        return items.keySet();
    }
}
