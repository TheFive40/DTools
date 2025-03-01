package org.delaware.tools.BoosterHandler;
import lombok.Getter;
import lombok.Setter;
import org.delaware.tools.Boosters.VIPBooster;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Getter
@Setter
public class BoosterDataHandler {
    // Handle booster data
    @Getter
    private static ConcurrentHashMap<String, Double> boostMultiplier = new ConcurrentHashMap<> ( );
    @Getter
    private static CopyOnWriteArrayList<VIPBooster> boosterData = new CopyOnWriteArrayList<> ( );

    // Add booster data to the map
    public void addBoosterData ( String rank, Double booster ) {
        boostMultiplier.put ( rank, booster );
    }

    // Remove booster data from the map
    public void removeBoosterData ( String rank ) {
        boostMultiplier.remove ( rank );
    }

    // Get booster data from the map
    public Double getBoosterData ( String rank ) {
        return boostMultiplier.get ( rank );
    }

    // Clear all booster data from the map
    public void clearBoosterData () {
        boostMultiplier.clear ( );
    }

    // Add booster data to the list
    public void addBooster ( VIPBooster booster ) {
        boosterData.add ( booster );
    }

    // Remove booster data from the list
    public void removeBooster ( VIPBooster booster ) {
        boosterData.remove ( booster );
    }

    public boolean contains ( UUID uuid ) {
        CopyOnWriteArrayList<VIPBooster> boosters = BoosterDataHandler.getBoosterData ( );
        VIPBooster comparableBooster = new VIPBooster ( uuid );
        return boosters.contains ( comparableBooster );
    }

    public VIPBooster findBooster ( UUID playerUUID ) {
        CopyOnWriteArrayList<VIPBooster> boosters = BoosterDataHandler.getBoosterData ( );
        return boosters.stream ( )
                .filter ( filter -> filter.getPlayerUUID ( ).equals ( playerUUID ) )
                .findFirst ( ).orElse ( null );
    }
}

