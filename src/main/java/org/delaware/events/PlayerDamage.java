package org.delaware.events;

import noppes.npcs.api.entity.IDBCPlayer;
import noppes.npcs.scripted.NpcAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import static org.delaware.Main.playersTPS;

public class PlayerDamage implements Listener {
    @EventHandler
    public void onPlayerDamageEvent ( EntityDamageByEntityEvent event ) {
        if (event.getDamager ( ) instanceof Player) {
            Player player = (Player) event.getDamager ( );
            IDBCPlayer idbcPlayer = NpcAPI.Instance ( ).getPlayer ( player.getName ( ) ).getDBCPlayer ( );
            playersTPS.put ( player.getName ( ), idbcPlayer.getTP ( ) );
        }
    }
}
