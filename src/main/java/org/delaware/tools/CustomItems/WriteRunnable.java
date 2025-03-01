package org.delaware.tools.CustomItems;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class WriteRunnable {
    @Getter
    static BukkitRunnable runnable = new BukkitRunnable() {
        @Override
        public void run() {
            Bukkit.getConsoleSender().sendMessage("Saving customitems data...");
            PlayerBonusesData.saveToConfig();
            CustomItems.saveToConfig();
        }
    };
}
