package org.delaware.tools.RegionTools;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.delaware.tools.Events.PlayerEnterRegionEvent;

public class RegionEnterEvent implements Listener {
    @EventHandler
    public void onRegionEnter(PlayerEnterRegionEvent event) {
        Bukkit.getConsoleSender().sendMessage("Player " + event.getPlayer() + " entered region " + event.getRegionName());
        PlayerAccessManager manager = new PlayerAccessManager(event.getPlayer());
        Bukkit.getConsoleSender().sendMessage("can player enter? " + manager.canEnterRegion(event.getRegionName()));
    }
}
