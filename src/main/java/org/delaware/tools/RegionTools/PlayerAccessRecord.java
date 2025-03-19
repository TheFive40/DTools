package org.delaware.tools.RegionTools;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
public class PlayerAccessRecord {
    private String regionName;
    private Instant startTime;
    private Instant refreshTime;
    private int cooldown;
    private int timeElapsed;
    private int maxDuration;
    private int baseMaxDuration;
    //add cooldown, refreshTime and baseMaxDuration
    public PlayerAccessRecord(String regionName, Instant startTime, int maxDuration) {
        this.regionName = regionName;
        this.startTime = startTime;
        this.maxDuration = maxDuration;
        this.baseMaxDuration = maxDuration;
        this.timeElapsed = 0;
        this.cooldown = -1;
    }
}
