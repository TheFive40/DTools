package org.delaware.tools.CustomItems;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.util.com.google.gson.Gson;
import net.minecraft.util.com.google.gson.GsonBuilder;
import net.minecraft.util.com.google.gson.JsonSyntaxException;
import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.delaware.Main;
import org.delaware.tools.CC;
import org.delaware.tools.NbtHandler.NbtHandler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

@Setter
@Getter
public class CustomItems {

    public static HashMap<String, CustomItems> items = new HashMap<>();

    private String displayName;
    private List<String> lore;
    private short durability;
    private int amount;
    private int itemID;
    private Map<Enchantment, Integer> enchantments;
    private String nbtData;
    private String KitName;
    private List<String> stats = new ArrayList<>();
    private List<String> boostIDS = new ArrayList<>();
    private List<String> operations = new ArrayList<>();
    private List<Double> values = new ArrayList<>();
    private int effect;
    private byte level;

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
    public void setUnbreakable(boolean unbreakable) {
        NbtHandler nbt = new NbtHandler(this.toItemStack());
        nbt.setBoolean("Unbreakable", unbreakable);
        this.nbtData = nbt.getCompound().toString();
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
        NbtHandler nbt = new NbtHandler(this.toItemStack());
        nbt.setString("CUSTOMID", key);
        this.nbtData = nbt.getCompound().toString();
        items.put(key, this);
        saveToConfig();
    }
    public void addBoost(String itemID, String stat, String boostID, String operation, double value, boolean unbreakable) {
        this.stats.add(stat);
        this.boostIDS.add(boostID);
        this.operations.add(operation);
        this.values.add(value);
        NbtHandler nbt = new NbtHandler(this.toItemStack());
        if(unbreakable) nbt.setBoolean("Unbreakable", true);
        this.nbtData = nbt.getCompound().toString();
        items.put(itemID, this);
        saveToConfig();
    }
    public boolean hasCustomBoost() {
        if(this.stats == null) return false;
        return !this.stats.isEmpty();
    }
    public void deleteLastBoost(String itemID) {
        if(!this.stats.isEmpty()) this.stats.remove((this.stats.size()-1));
        if(!this.boostIDS.isEmpty()) this.boostIDS.remove((this.boostIDS.size()-1));
        if(!this.operations.isEmpty()) this.operations.remove((this.operations.size()-1));
        if(!this.values.isEmpty()) this.values.remove((this.values.size()-1));
        items.put(itemID, this);
        saveToConfig();
    }
    public void addSetEffect(String itemID, String kitName, String effectID, int level) {
        this.KitName = kitName;
        if(effectID.equals("HEALTHREGEN")) this.effect = 1;
        if(effectID.equals("KIREGEN")) this.effect = 2;
        if(effectID.equals("STAMINAREGEN")) this.effect = 3;
        this.level = (byte) level;
        items.put(itemID, this);
        saveToConfig();
    }
    public boolean hasSetEffect() {
        return KitName != null;
    }
    public void deleteLastEffect(String itemID) {
        KitName = null;
        items.put(itemID, this);
        saveToConfig();
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
        saveToConfig();
    }
    public static CustomItems getFromNbt(ItemStack item) {
        NbtHandler nbt = new NbtHandler(item);
        try {
            if(nbt.getCompound() != null && nbt.getCompound().hasKey("CUSTOMID")) return getCustomItem(nbt.getString("CUSTOMID"));
        }catch(NullPointerException e) {
            return new CustomItems(item);
        }
        return new CustomItems(item);
    }
    //STATIC METHODS
    private static void saveToConfig() {
        try {
            File rootDir = new File (Main.instance.getDataFolder(), "DTools");
            File dataDir = new File (rootDir, "data");
            if (!dataDir.exists ( )) {
                dataDir.mkdirs ( );
            }
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String jsonCustomItems = gson.toJson(items);
            FileWriter writerCustomItems = new FileWriter ( new File( dataDir, "CustomItems.json" ) );
            writerCustomItems.write(jsonCustomItems);
            writerCustomItems.close();
        }catch(IOException | JsonSyntaxException e) {
            Bukkit.getConsoleSender().sendMessage("Error while writing CustomItems data, send log to SpaceyDCO!");
            throw new RuntimeException(e);
        }
    }
}
