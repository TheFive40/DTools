package org.delaware.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import static org.delaware.commands.CommandTransform.detransform;

public class PlayerQuit implements Listener {
    @EventHandler
    public void onPlayerQuintEvent ( PlayerQuitEvent event ) {
        detransform ( event.getPlayer ( ) );
    }
}
