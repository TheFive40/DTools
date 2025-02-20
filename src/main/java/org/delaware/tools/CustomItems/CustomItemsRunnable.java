package org.delaware.tools.CustomItems;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class CustomItemsRunnable {
    @Getter
    static BukkitRunnable bukkitRunnable = new BukkitRunnable() {
        @Override
        public void run() {
            for(Player player : Bukkit.getServer().getOnlinePlayers()) {
                if(player.getInventory().getChestplate() == null && player.getInventory().getLeggings() == null && player.getInventory().getBoots() == null) {
                    player.sendMessage("no armor");
                    continue;
                }
                player.sendMessage("armor");
            }
        }
    };
}
