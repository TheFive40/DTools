package org.delaware.tools.CustomItems;

import kamkeel.npcdbc.controllers.StatusEffectController;
import lombok.Getter;
import net.minecraft.entity.player.EntityPlayer;
import noppes.npcs.scripted.NpcAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.delaware.tools.Boosters.BonusAttributes;

public class CustomItemsRunnable {
    @Getter
    static BukkitRunnable bukkitRunnable = new BukkitRunnable() {
        @Override
        public void run() {
            for(Player player : Bukkit.getServer().getOnlinePlayers()) {
                BonusAttributes bonus = new BonusAttributes(player);
                if(bonus.hasBonus()) bonus.clearAllBonuses();
                if(player.getInventory().getChestplate() == null && player.getInventory().getLeggings() == null && player.getInventory().getBoots() == null) {
                    continue;
                }
                ItemStack chestplate = player.getInventory().getChestplate();
                ItemStack leggings = player.getInventory().getLeggings();
                ItemStack boots = player.getInventory().getBoots();
                int setEffectCheck = 0;
                String kitName = "";
                if(chestplate != null) {
                    CustomItems cItem = new CustomItems(chestplate);
                    if(cItem.hasCustomBoost()) {
                        String[] values = cItem.getCustomBoostValues();
                        if(bonus.hasAnyBonus(values[0])) {
                            bonus.clearBonus(values[0]);
                        }
                        bonus.addBonus(values[0], values[1], values[2], Double.parseDouble(values[3]));
                        if(cItem.hasSetEffect()) {
                            kitName = cItem.getKitName();
                            setEffectCheck++;
                        }
                    }
                }
                if(leggings != null) {
                    CustomItems cItem = new CustomItems(leggings);
                    if(cItem.hasCustomBoost()) {
                        String[] values = cItem.getCustomBoostValues();
                        if(bonus.hasAnyBonus(values[0])) {
                            bonus.clearBonus(values[0]);
                        }
                        bonus.addBonus(values[0], values[1], values[2], Double.parseDouble(values[3]));
                        if(cItem.hasSetEffect()) {
                            if(cItem.getKitName().equals(kitName)) setEffectCheck++;
                        }
                    }
                }
                if(boots != null) {
                    CustomItems cItem = new CustomItems(boots);
                    if(cItem.hasCustomBoost()) {
                        String[] values = cItem.getCustomBoostValues();
                        if(bonus.hasAnyBonus(values[0])) {
                            bonus.clearBonus(values[0]);
                        }
                        bonus.addBonus(values[0], values[1], values[2], Double.parseDouble(values[3]));
                        if(cItem.hasSetEffect()) {
                            if(cItem.getKitName().equals(kitName)) setEffectCheck++;
                        }
                    }
                    if(setEffectCheck >= 3) {
                        int[] values = cItem.getSetEffectValuesIntegers();
                        bonus.setCustomEffect(values[0], 5, (byte) values[1]);
                    }
                }
            }
        }
    };
}
