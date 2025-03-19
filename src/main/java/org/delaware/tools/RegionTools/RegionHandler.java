package org.delaware.tools.RegionTools;

import com.sk89q.worldguard.bukkit.RegionContainer;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class RegionHandler {
    private final WorldGuardPlugin wg;
    public RegionHandler() {
        this.wg = WorldGuardPlugin.inst();
    }
    public Set<String> getAllPlayersInRegion(String regionName, Player p) {
        RegionManager regionManager = getRegionManager(p);
        if (regionManager == null) return null;

        ProtectedRegion region = regionManager.getRegion(regionName);
        if (region == null) return null;

        Set<String> playersInRegion = new HashSet<>();
        World world = p.getWorld();
        for (Player player : world.getPlayers()) {
            if (isInRegion(player, regionName)) {
                playersInRegion.add(player.getName());
            }
        }
        return playersInRegion;
    }
    public boolean isInRegion(Player player, String regionName) {
        RegionManager regionManager = getRegionManager(player);
        if (regionManager == null) return false;

        ProtectedRegion region = regionManager.getRegion(regionName);
        if (region == null) return false;

        Location playerLocation = player.getLocation();
        return region.contains(playerLocation.getBlockX(), playerLocation.getBlockY(), playerLocation.getBlockZ()); // Proper method to check if player is within region's bounds
    }
    //Returns the region the player is standing in
    public ProtectedRegion getPlayerRegion(Player player) {
        RegionManager regionManager = getRegionManager(player);
        if(regionManager == null) return null;
        Location playerLocation = player.getLocation();
        ApplicableRegionSet set = regionManager.getApplicableRegions(playerLocation);
        if (set.size() == 0) {
            return null;
        }
        Iterator<ProtectedRegion> it = set.iterator();
        return it.next();
    }
    private RegionManager getRegionManager(Player player) {
        if(wg == null) return null;
        RegionContainer container = wg.getRegionContainer();
        return container.get(player.getWorld());
    }
    //STATIC
    public static ProtectedRegion getRegion(Player player) {
        WorldGuardPlugin wg = WorldGuardPlugin.inst();
        if(wg == null) return null;
        RegionContainer container = wg.getRegionContainer();
        RegionManager regionManager = container.get(player.getWorld());
        if(regionManager == null) return null;
        Location playerLocation = player.getLocation();
        ApplicableRegionSet set = regionManager.getApplicableRegions(playerLocation);
        if (set.size() == 0) {
            return null;
        }
        Iterator<ProtectedRegion> it = set.iterator();
        return it.next();
    }
    //STATIC
}
