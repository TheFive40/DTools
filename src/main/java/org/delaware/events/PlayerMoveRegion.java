package org.delaware.events;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.delaware.Main;
import org.delaware.tools.Events.PlayerEnterRegionEvent;
import org.delaware.tools.RegionUtils;

public class PlayerMoveRegion implements Listener {
    @EventHandler
    public void onPlayerMove ( PlayerMoveEvent event ) {
        Player player = event.getPlayer ( );
        RegionUtils regionUtils = new RegionUtils ( );
        ProtectedRegion region = regionUtils.getRegionAtLocation ( player.getLocation ( ) );
        if (region != null) {
            PlayerEnterRegionEvent customEvent = new PlayerEnterRegionEvent ( player, region.getId ( ),
                    region );
            Main.instance.getServer ( ).getPluginManager ( ).callEvent ( customEvent );
        }
    }
}
