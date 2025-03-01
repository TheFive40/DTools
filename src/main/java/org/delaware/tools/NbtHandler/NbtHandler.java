package org.delaware.tools.NbtHandler;

import lombok.Getter;
import net.minecraft.server.v1_7_R4.*;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

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
        if(compound == null) {
            NBTTagCompound comp = new NBTTagCompound();
            comp.setString(key, value);
            this.compound = comp;
        }else compound.setString(key, value);
        item.setTag(compound);
    }
    public void setInteger(String key, int value) {
        if(compound == null) {
            NBTTagCompound comp = new NBTTagCompound();
            comp.setInt(key, value);
            this.compound = comp;
        }else compound.setInt(key, value);
        item.setTag(compound);
    }
    public void setBoolean(String key, boolean value) {
        if(compound == null) {
            NBTTagCompound comp = new NBTTagCompound();
            comp.setBoolean(key, value);
            this.compound = comp;
        }else compound.setBoolean(key, value);
        item.setTag(compound);
    }
    public void setShort(String key, short value) {
        if(compound == null) {
            NBTTagCompound comp = new NBTTagCompound();
            comp.setShort(key, value);
            this.compound = comp;
        }else compound.setShort(key, value);
        item.setTag(compound);
    }
    public void changeDamage(int damage) {
        if(this.compound == null) this.compound = new NBTTagCompound();
        NBTTagList modifiers = new NBTTagList();
        NBTTagCompound damageTag = new NBTTagCompound();
        damageTag.set("AttributeName", new NBTTagString("generic.attackDamage"));
        damageTag.set("Name", new NBTTagString("generic.attackDamage"));
        damageTag.set("Amount", new NBTTagInt(damage));
        damageTag.set("Operation", new NBTTagInt(0)); //0 add, 1 multiply, 2 add percentage
        damageTag.set("UUIDMost", new NBTTagInt(item.hashCode()));
        damageTag.set("UUIDLeast", new NBTTagInt(item.hashCode()));
        damageTag.set("Slot", new NBTTagString("mainhand"));
        modifiers.add(damageTag);
        this.compound.set("AttributeModifiers", modifiers);
        this.item.setTag(this.compound);
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
