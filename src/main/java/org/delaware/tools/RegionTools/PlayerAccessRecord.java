package org.delaware.tools.RegionTools;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayerAccessRecord {
    private String regionName;
    private Instant expirationDate;
}
