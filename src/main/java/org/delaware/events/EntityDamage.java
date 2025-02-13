package org.delaware.events;

import noppes.npcs.api.entity.ICustomNpc;
import noppes.npcs.api.entity.IDBCPlayer;
import noppes.npcs.scripted.NpcAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;

import static org.delaware.commands.CommandTransform.*;

public class EntityDamage implements Listener {
    @EventHandler
    public void onEntityDamage( EntityDamageByBlockEvent event ){
        if (entitiesBukkit.containsValue ( event.getEntity () )){
            Player player = findPlayerByEntity ( event.getEntity () );
            ICustomNpc<?> npc = (ICustomNpc<?>) entities.get ( player.getName () );
            IDBCPlayer dbcPlayer = NpcAPI.Instance ( ).getPlayer ( player.getName ( ) ).getDBCPlayer ( );
            dbcPlayer.setHP ( (int) npc.getHealth ( ) );
        }
    }
}
