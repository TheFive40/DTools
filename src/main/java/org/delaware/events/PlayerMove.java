package org.delaware.events;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import kamkeel.npcdbc.constants.DBCForm;
import kamkeel.npcdbc.constants.DBCRace;
import noppes.npcs.api.entity.IDBCPlayer;
import noppes.npcs.scripted.NpcAPI;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.delaware.tools.CC;
import org.delaware.tools.General;
import org.delaware.tools.RegionUtils;

import static org.delaware.commands.CommandFrooze.playersFrooze;
import static org.delaware.commands.CommandTransform.entities;
import static org.delaware.commands.CommandTransform.entitiesBukkit;
import static org.delaware.tools.RegionUtils.restrictedRegions;

public class PlayerMove implements Listener {
    @EventHandler
    public void onPlayerMove ( PlayerMoveEvent event ) {

        RegionUtils regionUtils = new RegionUtils ( );
        Player player = event.getPlayer ( );

        Location location = player.getLocation ( );

        if (location == null) return;

        ProtectedRegion region = regionUtils.getRegionAtLocation ( location );

        if (region == null) {
            return;
        }

        if (restrictedRegions == null) {
            return;
        }

        if (restrictedRegions.contains ( regionUtils.getRegionAtLocation ( player.getLocation ( ) ).getId ( ) ) && !General.hasStaffParent ( player )) {
            if (!regionUtils.hasAccess ( player.getName ( ), "trainingoculto" )) {
                player.sendMessage ( CC.translate ( "&cNo puedes entrar en esta zona." ) );
                player.performCommand ( "warp spawn" );
                event.setCancelled ( true );
                return;
            }
        }

        if (playersFrooze.contains ( player )) {
            event.setCancelled ( true );
            return;
        }
        Location to = event.getTo ( );
        if (entities.containsKey ( player.getName ( ) )) {
            Entity entity = entitiesBukkit.get ( player.getName ( ) );
            location = entity.getLocation ( );
            location.setX ( to.getX ( ) - 1 );
            location.setY ( to.getY ( ) );
            location.setZ ( to.getZ ( ) );
            location.setYaw ( to.getYaw ( ) );
            location.setPitch ( to.getPitch ( ) );
            location.setDirection ( to.getDirection ( ) );
            entity.teleport ( location );
        }
    }
}
