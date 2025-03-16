package org.delaware.tools.CustomItems;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.delaware.tools.RegionTools.PlayerAccessManager;

public class WriteRunnable {
    @Getter
    static BukkitRunnable runnable = new BukkitRunnable() {
        @Override
        public void run() {
            Bukkit.getConsoleSender().sendMessage("Saving customitems and region data...");
            PlayerBonusesData.saveToConfig();
            CustomItems.saveToConfig();
            //RegionData
            PlayerAccessManager.saveToConfig();
            //RegionData
        }
    };
}
