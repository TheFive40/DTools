package org.delaware.tools.CustomItems.ScriptedItems;

import lombok.Getter;
import net.minecraft.server.v1_7_R4.NBTTagCompound;
import org.bukkit.Color;
import org.bukkit.inventory.ItemStack;
import org.delaware.tools.NbtHandler.NbtHandler;

@Getter
public class ScriptedItemManager {
    private ItemStack item;
    public ScriptedItemManager(ItemStack item) {
        NbtHandler nbtHandler = new NbtHandler(item);
        if(nbtHandler.containsCompound("ItemData")) {
            this.item = item;
            return;
        }
        NBTTagCompound comp = new NBTTagCompound();
        comp.setString("ItemTexture", "minecraft:textures/items/iron_sword.png");
        comp.setInt("ItemColor", Color.WHITE.asRGB() & 0xFFFFFF);
        comp.setDouble("ScaleX", 1);
        comp.setDouble("ScaleY", 1);
        comp.setDouble("ScaleZ", 1);
        comp.setFloat("RotationX", 0);
        comp.setFloat("RotationY", 0);
        comp.setFloat("RotationZ", 0);
        comp.setFloat("RotationXRate", 0);
        comp.setFloat("RotationYRate", 0);
        comp.setFloat("RotationZRate", 0);
        comp.setFloat("TranslateX", 0);
        comp.setFloat("TranslateY", 0);
        comp.setFloat("TranslateZ", 0);
        comp.setInt("DigSpeed", 1);
        comp.setInt("MaxItemUseDuration", 20);
        comp.setBoolean("IsTool", false);
        comp.setInt("ArmorType", -2);
        comp.setInt("Enchantability", 1);
        comp.setInt("MaxStackSize", 64);
        comp.setInt("DurabilityColor", Color.GREEN.asRGB() & 0xFFFFFF);
        comp.setDouble("DurabilityValue", 1);
        comp.setBoolean("DurabilityShow", false);
        comp.setInt("ItemUseAction", 0);
        nbtHandler.setCompound("ItemData", comp);
        this.item = nbtHandler.getItemStack();
    }
    public String getTexture() {return new NbtHandler(this.item).getCompound("ItemData").getString("ItemTexture");}
    public int getColor() {return new NbtHandler(this.item).getCompound("ItemData").getInt("ItemColor");}
    public int getScaleX() {return new NbtHandler(this.item).getCompound("ItemData").getInt("ScaleX");}
    public int getScaleY() {return new NbtHandler(this.item).getCompound("ItemData").getInt("ScaleY");}
    public int getScaleZ() {return new NbtHandler(this.item).getCompound("ItemData").getInt("ScaleZ");}
    public int getMaxStackSize() {return new NbtHandler(this.item).getCompound("ItemData").getInt("MaxStackSize");}

    public void setTexture(String newTexture) {
        NbtHandler handler = new NbtHandler(this.item);
        NBTTagCompound comp = handler.getCompound("ItemData");
        comp.setString("ItemTexture", newTexture);
        handler.setCompound("ItemData", comp);
        this.item = handler.getItemStack();
    }
    public void setColor(int newColor) {
        NbtHandler handler = new NbtHandler(this.item);
        NBTTagCompound comp = handler.getCompound("ItemData");
        comp.setInt("ItemColor", newColor);
        handler.setCompound("ItemData", comp);
        this.item = handler.getItemStack();
    }
    public void setScale(int X, int Y, int Z) {
        NbtHandler handler = new NbtHandler(this.item);
        NBTTagCompound comp = handler.getCompound("ItemData");
        comp.setInt("ScaleX", X);
        comp.setInt("ScaleY", Y);
        comp.setInt("ScaleZ", Z);
        handler.setCompound("ItemData", comp);
        this.item = handler.getItemStack();
    }
    public void setMaxStackSize(int newSize) {
        NbtHandler handler = new NbtHandler(this.item);
        NBTTagCompound comp = handler.getCompound("ItemData");
        comp.setInt("MaxStackSize", newSize);
        handler.setCompound("ItemData", comp);
        this.item = handler.getItemStack();
    }
}
