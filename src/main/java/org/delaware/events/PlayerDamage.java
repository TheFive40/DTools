package org.delaware.events;

import noppes.npcs.api.entity.IDBCPlayer;
import noppes.npcs.scripted.NpcAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.delaware.DBCEvents.DBCDamageEvent;
import org.delaware.Main;
import org.delaware.tools.CC;
import org.delaware.tools.General;
import org.delaware.tools.RegionTools.RegionHandler;
import org.delaware.tools.RegionUtils;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


public class PlayerDamage implements Listener {
    public static ConcurrentHashMap<Player, Player> pvp = new ConcurrentHashMap<> ( );
    private final ConcurrentHashMap<String, ConcurrentHashMap<String, Integer>> killCount = new ConcurrentHashMap<> ( );
    private final ConcurrentHashMap<String, Long> killTimestamps = new ConcurrentHashMap<> ( );

    @EventHandler
    public void onPlayerDamageEvent ( EntityDamageByEntityEvent event ) {
        if (event.getDamager ( ) instanceof Player && event.getEntity ( ) instanceof Player) {
            pvp.put ( (Player) event.getEntity ( ), (Player) event.getDamager ( ) );
        }
    }

    @EventHandler
    public void onPlayerDeath ( PlayerDeathEvent event ) {
        Player victim = event.getEntity ( );
        Player killer = pvp.get ( victim );

        if (killer == null || killer.equals ( victim )) return;

        String killerName = killer.getName ( );
        String victimName = victim.getName ( );
        String key = killerName + ":" + victimName;

        killCount.putIfAbsent ( killerName, new ConcurrentHashMap<> ( ) );
        killCount.get ( killerName ).putIfAbsent ( victimName, 0 );
        killTimestamps.putIfAbsent ( key, System.currentTimeMillis ( ) );

        long firstKillTime = killTimestamps.get ( key );
        long now = System.currentTimeMillis ( );
        long resetTime = 10 * 60 * 1000;

        if (now - firstKillTime > resetTime) {
            killCount.get ( killerName ).put ( victimName, 0 );
            killTimestamps.put ( key, now );
        }

        int currentKills = killCount.get ( killerName ).get ( victimName );
        if (currentKills >= 2) {
            killer.sendMessage ( CC.translate ( "&c[DBZ] &7Ya has recibido recompensa por matar 2 veces a este jugador." ) );
            return;
        }

        killCount.get ( killerName ).put ( victimName, currentKills + 1 );

        if (RegionUtils.isLocationInRegion ( victim.getLocation ( ), "training2" )
                || RegionUtils.isLocationInRegion ( victim.getLocation ( ), "training3" )
                || RegionUtils.isLocationInRegion ( victim.getLocation ( ), "training4" )) {

            int lvl = General.getLVL ( victim );
            int reward = lvl * 16;
            IDBCPlayer dbcPlayer = NpcAPI.Instance ( ).getPlayer ( killerName ).getDBCPlayer ( );
            Bukkit.dispatchCommand ( Main.instance.getServer ( ).getConsoleSender ( ), "dartps " + dbcPlayer.getName ( ) + " " + reward );
        }
    }

}
