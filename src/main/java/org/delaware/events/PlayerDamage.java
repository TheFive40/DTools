package org.delaware.events;

import noppes.npcs.api.entity.ICustomNpc;
import noppes.npcs.api.entity.IDBCPlayer;
import noppes.npcs.scripted.NpcAPI;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.delaware.commands.CommandTransform;
import org.delaware.tools.CC;

import static org.delaware.Main.playersTPS;
import static org.delaware.commands.CommandTransform.*;

public class PlayerDamage implements Listener {
    @EventHandler
    public void onPlayerDamageEvent ( EntityDamageByEntityEvent event ) {
        if (event.getDamager ( ) instanceof Player &&
                entitiesBukkit.containsValue ( event.getEntity ( ) )) {
            Player player = (Player) event.getDamager ( );
            if (entitiesBukkit.containsKey ( player.getName ( ) )) {
                Entity entity = entitiesBukkit.get ( player.getName ( ) );
                if (event.getEntity ( ).getEntityId ( ) == entity.getEntityId ( )) {
                    ICustomNpc<?> npc = (ICustomNpc<?>) entities.get ( player.getName ( ) );
                    IDBCPlayer idbcPlayer = NpcAPI.Instance ( ).getPlayer ( player.getName ( ) ).getDBCPlayer ( );
                    npc.setHealth ( idbcPlayer.getBody ( ) );
                    player.sendMessage ( CC.translate ( "&cYou can't attack yourself" ) );
                    npc.setHeldItem ( idbcPlayer.getHeldItem ( ) );

                }
            } else {
                Player player1 = CommandTransform.findPlayerByEntity ( event.getEntity ( ) );
                if (player1 != null) {
                    IDBCPlayer dbcPlayer = NpcAPI.Instance ( ).getPlayer ( player1.getName ( ) ).getDBCPlayer ( );
                    ICustomNpc<?> npc = (ICustomNpc<?>) entities.get ( player1.getName ( ) );
                    dbcPlayer.setHP ( (int) npc.getHealth ( ) );
                    npc.setHeldItem ( dbcPlayer.getHeldItem ( ) );
                }
            }
        } else if (entitiesBukkit.containsValue ( event.getEntity ( ) )) {
            Player player = findPlayerByEntity ( event.getEntity ( ) );
            ICustomNpc<?> npc = (ICustomNpc<?>) entities.get ( player.getName ( ) );
            IDBCPlayer dbcPlayer = NpcAPI.Instance ( ).getPlayer ( player.getName ( ) ).getDBCPlayer ( );
            dbcPlayer.setHP ( (int) npc.getHealth ( ) );
        }
        if (event.getDamager ( ) instanceof Player) {
            Player player = (Player) event.getDamager ( );
            IDBCPlayer idbcPlayer = NpcAPI.Instance ( ).getPlayer ( player.getName ( ) ).getDBCPlayer ( );
            playersTPS.put ( player.getName ( ), idbcPlayer.getTP ( ) );
        }
    }
}
