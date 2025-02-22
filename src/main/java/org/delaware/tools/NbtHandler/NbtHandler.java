package org.delaware.tools.NbtHandler;

import lombok.Getter;
import net.minecraft.server.v1_7_R4.MojangsonParser;
import net.minecraft.server.v1_7_R4.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class NbtHandler {
    private final net.minecraft.server.v1_7_R4.ItemStack item;
    @Getter NBTTagCompound compound;
    public NbtHandler(ItemStack item) {
        net.minecraft.server.v1_7_R4.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
        this.item = nmsStack;
        if(nmsStack.getTag() != null) this.compound = nmsStack.getTag();
        else this.compound = null;
    }
    public boolean hasNBT() {
        return item != null && item.hasTag();
    }
    public boolean isEmpty() {
        return compound.isEmpty();
    }
    public void setCompoundFromString(String comp) {
        try {
            NBTTagCompound nbt = getCompoundFromString(comp);
            item.setTag(nbt);
        }catch(Exception e) {
            Bukkit.getConsoleSender().sendMessage("Error while trying to apply NBT data " + e.getMessage());
            e.printStackTrace();
        }
    }
    public void setString(String key, String value) {
        compound.setString(key, value);
        item.setTag(compound);
    }
    public void setInteger(String key, int value) {
        compound.setInt(key, value);
        item.setTag(compound);
    }
    public void setBoolean(String key, boolean value) {
        compound.setBoolean(key, value);
        item.setTag(compound);
    }
    public String getString(String key) {
        return compound.getString(key);
    }
    public int getInteger(String key) {
        return compound.getInt(key);
    }
    public boolean getBoolean(String key) {
        return compound.getBoolean(key);
    }
    public ItemStack getItemStack() {
        return CraftItemStack.asBukkitCopy(item);
    }
    //STATIC METHODS
    public static NBTTagCompound getCompoundFromString(String sNBT) {
        return (NBTTagCompound) MojangsonParser.parse(sNBT);
    }
    //STATIC METHODS
}
