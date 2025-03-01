package org.delaware.tools.Boosters;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
public class VIPBooster implements Serializable {
    private UUID playerUUID;
    private String playerRank;
    private double multiplier;
    private LocalDateTime activationTime;
    private static final int COOLDOWN_HOURS = 24;
    boolean isActive;
    public VIPBooster ( UUID playerUUID, String playerRank, double multiplier ) {
        this.playerUUID = playerUUID;
        this.playerRank = playerRank;
        this.multiplier = multiplier;
        this.activationTime = LocalDateTime.now ( );
    }

    public VIPBooster ( String playerRank, double multiplier ) {
        this.playerRank = playerRank;
        this.multiplier = multiplier;
    }

    public VIPBooster ( UUID playerUUID ) {
        this.playerUUID = playerUUID;
    }

    public boolean isCooldownActive () {
        if (activationTime == null) return false;
        return Duration.between ( activationTime, LocalDateTime.now ( ) ).toHours ( ) < COOLDOWN_HOURS;
    }

    public Duration getTimeRemaining () {
        return Duration.ofHours ( COOLDOWN_HOURS ).minus ( Duration.between ( activationTime, LocalDateTime.now ( ) ) );
    }

    @Override
    public boolean equals ( Object object ) {
        if (this == object) return true;
        if (!(object instanceof VIPBooster)) return false;
        VIPBooster that = (VIPBooster) object;
        return Objects.equals ( getPlayerUUID ( ), that.getPlayerUUID ( ) );
    }

    @Override
    public int hashCode () {
        return Objects.hashCode ( getPlayerUUID ( ) );
    }
}
