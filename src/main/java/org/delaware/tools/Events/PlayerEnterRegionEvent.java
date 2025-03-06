package org.delaware.tools.Events;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
@Getter
@Setter
public class PlayerEnterRegionEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList ( );
    private String regionName;
    private Player player;
    private ProtectedRegion region;
    public PlayerEnterRegionEvent ( Player player, String regionName, ProtectedRegion region ) {
        this.player = player;
        this.regionName = regionName;
        this.region = region;
    }
    @Override
    public HandlerList getHandlers () {
        return HANDLERS;
    }
}
