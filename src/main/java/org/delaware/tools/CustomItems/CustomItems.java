package org.delaware.tools.CustomItems;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.delaware.tools.CC;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CustomItems {

    public static HashMap<String, CustomItems> items = new HashMap<>();

    @Getter @Setter private String displayName;
    @Getter @Setter private List<String> lore;
    @Getter @Setter private short durability;
    @Getter @Setter private int amount;
    @Getter @Setter private int itemID;
    @Getter @Setter private Map<Enchantment, Integer> enchantments;
    @Getter @Setter private String nbtData;

    public CustomItems(ItemStack item) {
        this.displayName = item.getItemMeta().getDisplayName();
        this.lore = item.getItemMeta().getLore();
        this.durability = item.getDurability();
        this.amount = item.getAmount();
        this.enchantments = item.getEnchantments();
        this.itemID = item.getTypeId();
        net.minecraft.server.v1_7_R4.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
        if(nmsStack != null && nmsStack.hasTag()) {
            this.nbtData = nmsStack.getTag().toString();
        }else {
            this.nbtData = null;
        }
    }
    public ItemStack toItemStack() {
        ItemStack itemStack = new ItemStack(itemID);
        itemStack.setAmount(amount);
        itemStack.setDurability(durability);
        itemStack.addEnchantments(enchantments);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(displayName);
        meta.setLore(lore);
        itemStack.setItemMeta(meta);
        return itemStack;
    }
    public void addCustomItem(String key) {
        items.put(key.toUpperCase().trim(), this);
    }
    //STATIC METHODS
    public static CustomItems getCustomItem(String key) {
        try {
            return items.get(key.toUpperCase().trim());
        }catch(java.lang.NullPointerException e) {
            Bukkit.getConsoleSender().sendMessage(CC.translate("&cCustomItems list is empty!"));
            throw new RuntimeException(e);
        }
    }
    public static Set<String> getAllCustomItems() {
        return items.keySet();
    }
    public static boolean contains(String key) {
        return items.containsKey(key);
    }
    public static void removeItem(String key) {
        items.remove(key);
    }
    //STATIC METHODS
}
