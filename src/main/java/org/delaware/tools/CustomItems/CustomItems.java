package org.delaware.tools.CustomItems;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.delaware.tools.CC;
import org.delaware.tools.NbtHandler.NbtHandler;
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

    public CustomItems(ItemStack item) { //IMPORTANT TO REMEMBER: ADD METADATA, FOR SAME ID ITEMS (NUMBER AFTER THE :)
        if(item.hasItemMeta()) {
            this.displayName = item.getItemMeta().getDisplayName();
            this.lore = item.getItemMeta().getLore();
        }
        this.durability = item.getDurability();
        this.amount = item.getAmount();
        this.enchantments = item.getEnchantments();
        this.itemID = item.getTypeId();
        NbtHandler nbt = new NbtHandler(item);
        if(nbt.hasNBT()) {
            this.nbtData = nbt.getCompound().toString();
        }else {
            this.nbtData = null;
        }
    }
    public ItemStack toItemStack() {
        ItemStack itemStack = new ItemStack(itemID);
        itemStack.setAmount(amount);
        itemStack.setDurability(durability);
        itemStack.addEnchantments(enchantments);
        if(this.displayName != null) {
            if(this.displayName.isEmpty()) {
                ItemMeta meta = itemStack.getItemMeta();
                meta.setDisplayName(displayName);
                meta.setLore(lore);
                itemStack.setItemMeta(meta);
            }
        }
        if(this.nbtData != null && !this.nbtData.isEmpty()) {
            NbtHandler nbt = new NbtHandler(itemStack);
            nbt.setCompoundFromString(this.nbtData);
            itemStack = nbt.getItemStack();
        }
        return itemStack;
    }
    public void addCustomItem(String key) {
        items.put(key.toUpperCase().trim(), this);
    }
    public void addBoost(String itemID, String stat, String boostID, String operation, String value, boolean unbreakable) {
        NbtHandler nbt = new NbtHandler(this.toItemStack());
        nbt.setString("STAT", stat);
        nbt.setString("BOOSTID", boostID);
        nbt.setString("OPERATION", operation);
        nbt.setString("VALUE", value);
        if(unbreakable) nbt.setBoolean("Unbreakable", true);
        this.nbtData = nbt.getCompound().toString();
        items.put(itemID, this);
    }
    public boolean hasCustomBoost() {
        NbtHandler nbt = new NbtHandler(this.toItemStack());
        if(nbt.getCompound() == null) return false;
        return nbt.getCompound().hasKey("STAT");
    }
    //STAT , BOOSTID , OPERATION , VALUE
    public String[] getCustomBoostValues() {
        NbtHandler nbt = new NbtHandler(this.toItemStack());
        if(nbt.getCompound() == null || !this.hasCustomBoost()) return null;
        return new String[] {nbt.getCompound().getString("STAT"), nbt.getCompound().getString("BOOSTID"), nbt.getCompound().getString("OPERATION"), nbt.getCompound().getString("VALUE")};
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
