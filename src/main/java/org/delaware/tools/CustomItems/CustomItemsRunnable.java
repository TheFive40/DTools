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
                //DELETES ALL APPLIED BONUSES
                if(!PlayerBonusesData.getPlayerBonusData(player.getUniqueId().toString()).getBonusesIDs().isEmpty()) {
                    PlayerBonusesData data = PlayerBonusesData.getPlayerBonusData(player.getUniqueId().toString());
                    for(int i = 0; i < data.getBonusesIDs().size(); i++) {
                        bonus.clearBonus(data.getStats().get(i), data.getBonusesIDs().get(i));
                    }
                    data.removeAllData(player.getUniqueId().toString());
                 }
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
                    try {
                        if(cItem.hasCustomBoost()) addBonus(cItem, bonus, player.getUniqueId().toString());
                    }catch(java.lang.NullPointerException e) {
                        Bukkit.getConsoleSender().sendMessage("Item " + chestplate.getType() + " from player " + player.getName() + " has nbt but is not registered in the config!");
                        throw new RuntimeException(e);
                    }
                    if(cItem.hasSetEffect()) {
                        kitName = cItem.getKitName();
                        setEffectCheck++;
                    }
                }
                if(leggings != null) {
                    CustomItems cItem = CustomItems.getFromNbt(leggings);
                    try {
                        if(cItem.hasCustomBoost()) addBonus(cItem, bonus, player.getUniqueId().toString());
                    }catch(java.lang.NullPointerException e) {
                        Bukkit.getConsoleSender().sendMessage("Item " + leggings.getType() + " from player " + player.getName() + " has nbt but is not registered in the config!");
                        throw new RuntimeException(e);
                    }
                    if(cItem.hasSetEffect())
                        if(cItem.getKitName().equals(kitName))
                            setEffectCheck++;
                }
                if(boots != null) {
                    CustomItems cItem = CustomItems.getFromNbt(boots);
                    try {
                        if(cItem.hasCustomBoost()) addBonus(cItem, bonus, player.getUniqueId().toString());
                    }catch(java.lang.NullPointerException e) {
                        Bukkit.getConsoleSender().sendMessage("Item " + boots.getType() + " from player " + player.getName() + " has nbt but is not registered in the config!");
                        throw new RuntimeException(e);
                    }
                    if(cItem.hasSetEffect())
                        if(cItem.getKitName().equals(kitName))
                            setEffectCheck++;
                    if(setEffectCheck >= 3) {
                        for(int i = 0; i < cItem.getEffect().size(); i++) {
                            bonus.setCustomEffect(cItem.getEffect().get(i), 7, cItem.getLevel().get(i));
                        }
                    }
                }
                //CHECK FOR CUSTOM ARMOR
            }
        }
        private void addBonus(CustomItems cItem, BonusAttributes bonus, String name) {
            for(int i = 0; i < cItem.getStats().size(); i++) {
                bonus.addBonus(cItem.getStats().get(i), cItem.getBoostIDS().get(i), cItem.getOperations().get(i), cItem.getValues().get(i));
                new PlayerBonusesData(name, cItem.getStats().get(i), cItem.getBoostIDS().get(i));
            }
        }
    };
}
