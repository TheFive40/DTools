package org.delaware.DBCEvents.Listeners;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
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
        if(handler.getPlayerRegions(player) == null) return;
        for(ProtectedRegion region : handler.getPlayerRegions(player)) {
            if(handler.getFlagValue(region, "pvp").equalsIgnoreCase("DENY")) {
                event.setCanceled(true);
                break;
            }
        }
    }
}
