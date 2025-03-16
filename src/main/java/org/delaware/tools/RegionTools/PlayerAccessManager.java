package org.delaware.tools.RegionTools;

import lombok.Getter;
import lombok.NonNull;
import net.minecraft.util.com.google.gson.Gson;
import net.minecraft.util.com.google.gson.GsonBuilder;
import net.minecraft.util.com.google.gson.JsonSyntaxException;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.delaware.Main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Getter
public class PlayerAccessManager {
    public static HashMap<String, PlayerAccessManager> allPlayers = new HashMap<>();
    private final String uuid;
    private final List<PlayerAccessRecord> accessRecords = new ArrayList<>();
    public PlayerAccessManager(@NonNull Player player) {
        this.uuid = player.getUniqueId().toString();
    }
    public void addAccess(String regionName, int durationInSeconds) {
        Instant expirationDate = Instant.now().plusSeconds(durationInSeconds);
        accessRecords.add(new PlayerAccessRecord(regionName, expirationDate));
    }
    public boolean canEnterRegion(String regionName) {
        boolean hasRecord = false;
        for(PlayerAccessRecord record : accessRecords) {
            if(record.getRegionName().equals(regionName)) {
                hasRecord = true;
                break;
            }
        }
        if(!hasRecord) return true;
        return hasAccess(regionName);
    }
    public void save() {
        allPlayers.put(uuid, this);
    }
    private boolean hasAccess(String regionName) {
        Instant now = Instant.now();
        for(PlayerAccessRecord record : accessRecords) {
            return record.getRegionName().equals(regionName) && record.getExpirationDate().isAfter(now);
        }
        return false;
    }
    //STATIC
    public static void saveToConfig() {
        try {
            File rootDir = new File (Main.instance.getDataFolder(), "DTools");
            File dataDir = new File (rootDir, "data");
            if (!dataDir.exists ( )) {
                dataDir.mkdirs ( );
            }
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String jsonAccessManager = gson.toJson(allPlayers);
            FileWriter writerAccessManager = new FileWriter ( new File( dataDir, "PlayerAccessData.json" ) );
            writerAccessManager.write(jsonAccessManager);
            writerAccessManager.close();
        }catch(IOException | JsonSyntaxException e) {
            Bukkit.getConsoleSender().sendMessage("Error while writing PlayerAccessData data, send log to SpaceyDCO!");
            throw new RuntimeException(e);
        }
    }
    //STATIC
}
