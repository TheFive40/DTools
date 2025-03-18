package org.delaware.events;

import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

public class EventsExplosion implements Listener {
    @EventHandler
    public void onExplosion( EntityExplodeEvent event) {
        if (event.getEntity() instanceof TNTPrimed) {
            event.setCancelled(true);
        }
    }
}
