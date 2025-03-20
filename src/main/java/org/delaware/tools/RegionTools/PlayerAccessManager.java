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
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Getter
public class PlayerAccessManager {
    public static HashMap<String, PlayerAccessManager> allPlayers = new HashMap<>();
    private final String uuid;
    private final List<PlayerAccessRecord> accessRecords = new ArrayList<>();
    //add field for refresh time
    public PlayerAccessManager(@NonNull Player player) {
        this.uuid = player.getUniqueId().toString();
    }
    //ADDS access to a region, if the player already has access to that region, it extends the time
    public void addAccess(String regionName, int durationInSeconds) {
        addAccess(regionName, durationInSeconds, -1);
    }
    //Adds to the time elapsed variable
    public void addTimeElapsed(String regionName, int time) {
        for(PlayerAccessRecord record : accessRecords) {
            if(record.getRegionName().equalsIgnoreCase(regionName)) {
                int timeElapsed = record.getTimeElapsed();
                record.setTimeElapsed((timeElapsed + time));
                save();
            }
        }
    }
    public String getFormattedCooldown(String regionName) {
        for(PlayerAccessRecord record : accessRecords) {
            if(record.getRegionName().equalsIgnoreCase(regionName)) {
                return formatSeconds(record.getCooldown());
            }
        }
        return null;
    }
    //Returns the remaining time the player has to wait to get access to the region again
    public String getRemainingCooldown(String regionName) {
        for(PlayerAccessRecord record : accessRecords) {
            if(record.getRegionName().equalsIgnoreCase(regionName)) {
                int remaining = (int) Duration.between(Instant.now(), record.getRefreshTime()).getSeconds();
                return formatSeconds(remaining);
            }
        }
        return null;
    }
    //Returns the time the player still has left | null if he doesn't have any record for that region
    public String getRemainingTime(String regionName) {
        for(PlayerAccessRecord record : accessRecords) {
            if(record.getRegionName().equalsIgnoreCase(regionName)) {
                int remaining = (record.getMaxDuration() - record.getTimeElapsed());
                return formatSeconds(remaining);
            }
        }
        return null;
    }
    //Refreshes a record...
    public void refresh(String regionName) {
        for(PlayerAccessRecord record : accessRecords) {
            if(record.getRegionName().equalsIgnoreCase(regionName)) {
                int maxDuration = record.getBaseMaxDuration();
                record.setStartTime(Instant.now());
                record.setMaxDuration(maxDuration);
                record.setTimeElapsed(0);
                record.setRefreshTime(null);
                save();
            }
        }
    }
    //Checks if cooldown has already passed
    public boolean isRefreshable(String regionName) {
        for(PlayerAccessRecord record : accessRecords) {
            if(record.getRegionName().equalsIgnoreCase(regionName)) {
                return record.getRefreshTime().isBefore(Instant.now());
            }
        }
        return false;
    }
    //Checks if there's an active refreshTime, if it is, returns true
    public boolean hasActiveRefreshTime(String regionName) {
        for(PlayerAccessRecord record : accessRecords) {
            if(record.getRegionName().equalsIgnoreCase(regionName)) {
                return record.getRefreshTime() != null;
            }
        }
        return false;
    }
    public void setRefreshTime(String regionName) {
        for(PlayerAccessRecord record : accessRecords) {
            if(record.getRegionName().equalsIgnoreCase(regionName)) {
                record.setRefreshTime(Instant.now().plusSeconds(record.getCooldown()));
                save();
            }
        }
    }
    //Checks whether a region has a defined refresh time or not
    //true if cooldown
    public boolean regionCooldownCheck(String regionName) {
        for(PlayerAccessRecord record : accessRecords) {
            if(record.getRegionName().equalsIgnoreCase(regionName)) {
                return record.getCooldown() > 0;
            }
        }
        return false; //record doesnt exist so cooldown doesnt exist
    }
    //Returns true if there's a record for this region
    public boolean hasRecord(String regionName) {
        for(PlayerAccessRecord record : accessRecords) {
            if(record.getRegionName().equalsIgnoreCase(regionName)) return true;
        }
        return false;
    }
    public void addAccess(String regionName, int durationInSeconds, int refreshDurationInSeconds) {
        for(PlayerAccessRecord record : accessRecords) {
            if(record.getRegionName().equalsIgnoreCase(regionName)) {
                record.setMaxDuration(record.getMaxDuration()+durationInSeconds); //adds duration in seconds
                if(refreshDurationInSeconds > 0) record.setCooldown(refreshDurationInSeconds); //changes the cooldown
                save();
                return;
            }
        }
        PlayerAccessRecord record = new PlayerAccessRecord(regionName, Instant.now(), durationInSeconds);
        if(refreshDurationInSeconds > 0) record.setCooldown(refreshDurationInSeconds);
        accessRecords.add(record);
        save();
    }
    //SETS access to a zone, it overrides the current expiration date
    public void setAccess(String regionName, int durationInSeconds) {
        setAccess(regionName, durationInSeconds, -1);
    }
    public void setAccess(String regionName, int durationInSeconds, int refreshDurationInSeconds) {
        for(PlayerAccessRecord record : accessRecords) {
            if(record.getRegionName().equalsIgnoreCase(regionName)) {
                record.setMaxDuration(durationInSeconds);
                record.setStartTime(Instant.now());
                record.setTimeElapsed(0);
                if(refreshDurationInSeconds > 0) record.setCooldown(refreshDurationInSeconds);
                save();
                return;
            }
        }
        PlayerAccessRecord record = new PlayerAccessRecord(regionName, Instant.now(), durationInSeconds);
        if(refreshDurationInSeconds > 0) record.setCooldown(refreshDurationInSeconds);
        accessRecords.add(record);
        save();
    }
    //Grants access to a region, will only work 1 time for 1 region, because it'll do nothing if the player was given an expiration date for a region already
    public void grantAccess(String regionName, int durationInSeconds) {
        grantAccess(regionName, durationInSeconds, -1);
    }
    public void grantAccess(String regionName, int durationInSeconds, int refreshDurationInSeconds) {
        for(PlayerAccessRecord record : accessRecords) {
            if(record.getRegionName().equalsIgnoreCase(regionName)) return; //found a record, return (dont matter if it's active or not)
        }
        setAccess(regionName, durationInSeconds, refreshDurationInSeconds);
    }
    public boolean canEnterRegion(String regionName) {
        boolean hasRecord = false;
        for(PlayerAccessRecord record : accessRecords) {
            if(record.getRegionName().equalsIgnoreCase(regionName)) {
                hasRecord = true;
                break;
            }
        }
        if(!hasRecord) return true;
        return hasAccess(regionName);
    }
    //Sets permanent access to a region
    public void setPermanentAccess(String regionName) {
        for(PlayerAccessRecord record : accessRecords) {
            if(record.getRegionName().equalsIgnoreCase(regionName)) {
                setAccess(record.getRegionName(), -10);
                save();
            }
        }
    }
    public void save() {
        allPlayers.put(uuid, this);
    }
    private boolean hasAccess(String regionName) {
        for(PlayerAccessRecord record : accessRecords) {
            if(record.getRegionName().equalsIgnoreCase(regionName)) {
                if(record.getMaxDuration() == -10) return true;
                return record.getTimeElapsed() < record.getMaxDuration();
            }
        }
        return false;
    }
    private String formatSeconds(int totalSeconds) {
        int hours = totalSeconds / 3600;
        int minutes = (totalSeconds % 3600) / 60;
        int seconds = totalSeconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
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
