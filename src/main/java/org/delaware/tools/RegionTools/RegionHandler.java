package org.delaware.tools.RegionTools;

import com.sk89q.worldguard.bukkit.RegionContainer;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.Set;

public class RegionHandler {
    private final WorldGuardPlugin wg;
    public RegionHandler() {
        this.wg = WorldGuardPlugin.inst();
    }
    public Set<String> getAllPlayersInRegion(Player player, String regionName) {
        RegionManager regionManager = getRegionManager(player);
        if(regionManager == null) return null;
        ProtectedRegion region = regionManager.getRegion(regionName);
        if(region == null) return null;
        return region.getMembers().getPlayers();
    }
    public boolean isInRegion(Player player, String regionName) {
        RegionManager regionManager = getRegionManager(player);
        if(regionManager == null) return false;
        ProtectedRegion region = regionManager.getRegion(regionName);
        if(region == null) return false;
        return region.getMembers().contains(player.getUniqueId());
    }
    //Returns the region the player is standing in
    public ProtectedRegion getPlayerRegion(Player player) {
        RegionManager regionManager = getRegionManager(player);
        if(regionManager == null) return null;
        Map<String, ProtectedRegion> allRegions = regionManager.getRegions();
        for(String region : allRegions.keySet()) {
            ProtectedRegion cRegion = allRegions.get(region);
            if(cRegion.getMembers().contains(player.getUniqueId())) {
                return cRegion;
            }
        }
        return null;
    }
    private RegionManager getRegionManager(Player player) {
        if(wg == null) return null;
        RegionContainer container = wg.getRegionContainer();
        return container.get(player.getWorld());
    }
}
