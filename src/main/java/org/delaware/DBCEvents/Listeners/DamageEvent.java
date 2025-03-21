package org.delaware.DBCEvents.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.delaware.DBCEvents.DBCDamageEvent;
import org.delaware.tools.RegionTools.RegionHandler;

import java.util.UUID;

public class DamageEvent implements Listener {
    @EventHandler
    public void onDamage(DBCDamageEvent event) {
        if(event.getDamageSource() == null || event.getDamageSource().getTrueSource() == null) return;
        if(event.getDamageSource().getTrueSource().getType() != 1 || !event.isDamageSourceKiAttack()) return;
        UUID uuid = UUID.fromString(event.getPlayer().getUniqueID());
        Player player = Bukkit.getPlayer(uuid);
        RegionHandler handler = new RegionHandler();
        if(handler.getPlayerRegion(player) == null) return;
        if(handler.getFlagValue(handler.getPlayerRegion(player), "pvp").equalsIgnoreCase("DENY")) {
            event.setCanceled(true);
        }
    }
}
