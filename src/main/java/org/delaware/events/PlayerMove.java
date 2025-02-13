package org.delaware.events;

import noppes.npcs.api.entity.IEntity;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import static org.delaware.commands.CommandTransform.entities;
import static org.delaware.commands.CommandTransform.entitiesBukkit;

public class PlayerMove implements Listener {
    @EventHandler
    public void onPlayerMove( PlayerMoveEvent event ){
        Player player = event.getPlayer ( );
        Location to = event.getTo ( );
        if (entities.containsKey ( player.getName () )) {
            Entity entity = entitiesBukkit.get ( player.getName () );
            Location location = entity.getLocation ();
            location.setX ( to.getX ()-1 );
            location.setY ( to.getY () );
            location.setZ ( to.getZ () );
            location.setYaw ( to.getYaw () );
            location.setPitch ( to.getPitch () );
            location.setDirection ( to.getDirection () );
            entity.teleport ( location );
        }
    }
}
