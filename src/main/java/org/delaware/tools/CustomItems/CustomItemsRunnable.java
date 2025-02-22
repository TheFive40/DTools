package org.delaware.tools.CustomItems;

import lombok.Getter;
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
                    CustomItems cItem = CustomItems.getFromNbt(chestplate);
                    if(cItem.hasCustomBoost()) {
                        clearAndAddBonus(cItem, bonus);
                    }
                    if(cItem.hasSetEffect()) {
                        kitName = cItem.getKitName();
                        setEffectCheck++;
                    }
                }
                if(leggings != null) {
                    CustomItems cItem = CustomItems.getFromNbt(leggings);
                    if(cItem.hasCustomBoost()) {
                        clearAndAddBonus(cItem, bonus);
                    }
                    if(cItem.hasSetEffect()) {
                        if(cItem.getKitName().equals(kitName)) setEffectCheck++;
                    }
                }
                if(boots != null) {
                    CustomItems cItem = CustomItems.getFromNbt(boots);
                    if(cItem.hasCustomBoost()) {
                        clearAndAddBonus(cItem, bonus);
                        if(cItem.hasSetEffect()) {
                            if(cItem.getKitName().equals(kitName)) setEffectCheck++;
                        }
                    }
                    if(cItem.hasSetEffect()) {
                        if(cItem.getKitName().equals(kitName)) setEffectCheck++;
                    }
                    if(setEffectCheck >= 3) {
                        bonus.setCustomEffect(cItem.getEffect(), 5, cItem.getLevel());
                    }
                }
            }
        }
        private void clearAndAddBonus(CustomItems cItem, BonusAttributes bonus) {
            for(int i = 0; i < cItem.getStats().size(); i++) {
                if(bonus.hasAnyBonus(cItem.getStats().get(i))) {
                    bonus.clearBonus(cItem.getStats().get(i));
                }
                bonus.addBonus(cItem.getStats().get(i), cItem.getBoostIDS().get(i), cItem.getOperations().get(i), cItem.getValues().get(i));
            }
        }
    };
}
