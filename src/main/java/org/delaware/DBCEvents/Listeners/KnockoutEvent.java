package org.delaware.DBCEvents.Listeners;

import noppes.npcs.api.entity.IDBCPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import org.delaware.DBCEvents.DBCKnockoutEvent;
import org.delaware.Main;
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
        if(handler.getFlagValue(handler.getPlayerRegion(player), "pvp").equalsIgnoreCase("DENY")) {
            startRunnable(event.getPlayer().getDBCPlayer(), event.getPlayer().getDBCPlayer().getRelease());
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jrmcse set KO 0 " + player.getName());
            damager.sendMessage(CC.translate("&cNo puedes hacer PvP en esta zona!"));
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jrmcse set KO 0.1 " + damager.getName());
        }
    }
    private void startRunnable(IDBCPlayer player, byte release) {
        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {
                player.setRelease(release);
            }
        };
        runnable.runTaskLater(Main.instance, 20);
    }
}
