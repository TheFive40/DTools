package org.delaware.CombatTag;

import lombok.Getter;
import lombok.NoArgsConstructor;
import net.minecraft.server.v1_7_R4.Block;
import net.minecraft.server.v1_7_R4.PacketPlayOutBlockChange;
import net.minecraft.server.v1_7_R4.World;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@NoArgsConstructor
public class PacketHandler {
    private static final Map<UUID, Map<Location, BlockState>> playerOriginalBlocks = new ConcurrentHashMap<>();

    public void sendBlockChange(Player player, Location location, int newBlockId, byte newBlockData) {
        CraftPlayer craftPlayer = (CraftPlayer) player;
        CraftWorld craftWorld = (CraftWorld) location.getWorld();
        World world = craftWorld.getHandle ();
        Material originalType = location.getBlock().getType();
        byte originalData = location.getBlock().getData();

        Map<Location, BlockState> playerMap = playerOriginalBlocks.computeIfAbsent(player.getUniqueId(), k -> new ConcurrentHashMap<>());
        playerMap.put(location, new BlockState(originalType.getId(), originalData));

        PacketPlayOutBlockChange packet = new PacketPlayOutBlockChange(
                location.getBlockX(),
                location.getBlockY(),
                location.getBlockZ(),
                world
        );
        packet.block = Block.getById(newBlockId);
        packet.data = newBlockData;
        craftPlayer.getHandle().playerConnection.sendPacket(packet);
    }
    public void restoreBlock(Player player, Location location) {
        UUID playerUUID = player.getUniqueId();
        if(!playerOriginalBlocks.containsKey(playerUUID)) return; //No packets sent for this player
        Map<Location, BlockState> playerMap = playerOriginalBlocks.get(playerUUID);
        if(!playerMap.containsKey(location)) return; //No packets sent for this location

        CraftPlayer craftPlayer = (CraftPlayer) player;
        CraftWorld craftWorld = (CraftWorld) location.getWorld();
        World world = craftWorld.getHandle();
        PacketPlayOutBlockChange packet = new PacketPlayOutBlockChange(
                location.getBlockX(),
                location.getBlockY(),
                location.getBlockZ(),
                world
        );
        BlockState originalBlockState = playerMap.get(location);
        packet.block = Block.getById(originalBlockState.getBlockId());
        packet.data = originalBlockState.getBlockData();
        craftPlayer.getHandle().playerConnection.sendPacket(packet);
        //Updating Map
        playerMap.remove(location); //Removing this location as its original block was restored
        if(playerMap.isEmpty()) { //Remove the player from the map if it doesnt have any more locations
            playerOriginalBlocks.remove(playerUUID);
        }
    }
    @Getter
    private static class BlockState {
        private final int blockId;
        private final byte blockData;
        public BlockState(int blockId, byte blockData) {
            this.blockId = blockId;
            this.blockData = blockData;
        }
    }
}
