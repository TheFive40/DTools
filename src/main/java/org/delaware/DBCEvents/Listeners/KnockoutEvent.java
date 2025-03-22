package org.delaware.DBCEvents.Listeners;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.delaware.DBCEvents.DBCKnockoutEvent;
import org.delaware.tools.CC;
import org.delaware.tools.RegionTools.RegionHandler;

import java.util.UUID;

public class KnockoutEvent implements Listener {
    @EventHandler
    public void onKnockout(DBCKnockoutEvent event) {
        if(event.getDamageSource() == null || event.getDamageSource().getTrueSource() == null) return;
        RegionHandler handler = new RegionHandler();
        UUID uuid = UUID.fromString(event.getPlayer().getUniqueID());
        Player player = Bukkit.getPlayer(uuid);
        UUID damagedUUID = UUID.fromString(event.getDamageSource().getTrueSource().getUniqueID());
        Player damager = Bukkit.getPlayer(damagedUUID);
        if(handler.getPlayerRegions(player) == null) return;
        for(ProtectedRegion region : handler.getPlayerRegions(player)) {
            if(handler.getFlagValue(region, "pvp") == null) continue;
            if(handler.getFlagValue(region, "pvp").equalsIgnoreCase("DENY")) {
                event.setCanceled(true);
                damager.sendMessage(CC.translate("&cNo puedes hacer PvP en esta zona!"));
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jrmcse set KO 0.1 " + damager.getName());
                break;
            }
        }
    }
}
