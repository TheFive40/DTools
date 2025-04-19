package org.delaware.tools.Boosters;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;
@Getter
@Setter
public class PBooster implements Serializable {
    private LocalDateTime activationTime;
    private double multiplier;
    private UUID playerId;
}
