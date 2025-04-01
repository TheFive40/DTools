package org.delaware.CombatTag;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ForceFieldManager {
    public static Map<UUID, Set<Location>> forceFieldBlocksLocation = new ConcurrentHashMap<>();
    private final Player player;
    private final PacketHandler packetHandler;
    public ForceFieldManager(Player player) {
        this.player = player;
        this.packetHandler = new PacketHandler();
    }

    public void applyForceField(Set<ProtectedRegion> regions, int blockId, byte blockData) {
        Set<Location> locations = forceFieldBlocksLocation.containsKey(player.getUniqueId()) ? forceFieldBlocksLocation.get(player.getUniqueId()) : new HashSet<>();
        for(ProtectedRegion region : regions) {
            Location minPoint = new Location(player.getWorld(), region.getMinimumPoint().getX(), region.getMinimumPoint().getY(), region.getMinimumPoint().getZ());
            Location maxPoint = new Location(player.getWorld(), region.getMaximumPoint().getX(), region.getMaximumPoint().getY(), region.getMaximumPoint().getZ());
            if(minPoint.distance(maxPoint) > 250) continue; //if region is too big, it won't place the forcefield (due to lag)
            if(player.getLocation().distance(minPoint) > 750 || player.getLocation().distance(maxPoint) > 750) continue; //if player is out of 750 blocks range, it won't place the forcefield (again, due to lag)
            for (double x = minPoint.getX(); x <= maxPoint.getX(); x++) { //Bottom and top from the wall
                for (double z = minPoint.getZ(); z <= maxPoint.getZ(); z++) {
                    //Top
                    double y = maxPoint.getY();
                    Location location = new Location(player.getWorld(), x, y, z);
                    if(location.getBlock().getType() == Material.AIR) {
                        packetHandler.sendBlockChange(player, location, blockId, blockData);
                        locations.add(location);
                    }
                    //Bottom
                    y = minPoint.getY();
                    location = new Location(player.getWorld(), x, y, z);
                    if(location.getBlock().getType() == Material.AIR) {
                        packetHandler.sendBlockChange(player, location, blockId, blockData);
                        locations.add(location);
                    }
                }
            }
            for (double x = minPoint.getX(); x <= maxPoint.getX(); x += 0.5) { //2 Sides
                for (double y = minPoint.getY(); y <= maxPoint.getY(); y += 0.5) {
                    //First side
                    double z = maxPoint.getZ();
                    Location location = new Location(player.getWorld(), x, y, z);
                    if(location.getBlock().getType() == Material.AIR) {
                        packetHandler.sendBlockChange(player, location, blockId, blockData);
                        locations.add(location);
                    }
                    //Second side
                    z = minPoint.getZ();
                    location = new Location(player.getWorld(), x, y, z);
                    if(location.getBlock().getType() == Material.AIR) {
                        packetHandler.sendBlockChange(player, location, blockId, blockData);
                        locations.add(location);
                    }
                }
            }
            for (double y = minPoint.getY(); y <= maxPoint.getY(); y += 0.5) { //2 Sides
                for (double z = minPoint.getZ(); z <= maxPoint.getZ(); z += 0.5) {
                    //First side
                    double x = maxPoint.getX();
                    Location location = new Location(player.getWorld(), x, y, z);
                    if(location.getBlock().getType() == Material.AIR) {
                        packetHandler.sendBlockChange(player, location, blockId, blockData);
                        locations.add(location);
                    }
                    //Second side
                    x = minPoint.getX();
                    location = new Location(player.getWorld(), x, y, z);
                    if(location.getBlock().getType() == Material.AIR) {
                        packetHandler.sendBlockChange(player, location, blockId, blockData);
                        locations.add(location);
                    }
                }
            }
        }
        forceFieldBlocksLocation.put(player.getUniqueId(), locations);
    }

    public void removeForceField() {
        if(!forceFieldBlocksLocation.containsKey(player.getUniqueId())) return;
        Set<Location> allLocations = forceFieldBlocksLocation.get(player.getUniqueId());
        for(Location loc : allLocations) {
            packetHandler.restoreBlock(player, loc);
        }
        forceFieldBlocksLocation.remove(player.getUniqueId());
    }
}
