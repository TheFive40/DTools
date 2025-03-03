package org.delaware.tools.BoosterHandler;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.craftbukkit.libs.com.google.gson.Gson;
import org.bukkit.craftbukkit.libs.com.google.gson.GsonBuilder;
import org.bukkit.craftbukkit.libs.com.google.gson.JsonSyntaxException;
import org.bukkit.craftbukkit.libs.com.google.gson.reflect.TypeToken;
import org.delaware.Main;
import org.delaware.tools.Boosters.VIPBooster;
import java.io.*;
import java.lang.reflect.Type;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Getter
@Setter
public class BoosterDataHandler implements Serializable{
    // Handle booster data
    @Getter
    @Setter
    private static ConcurrentHashMap<String, Double> boostMultiplier = new ConcurrentHashMap<> ( );
    @Getter
    @Setter
    private static CopyOnWriteArrayList<VIPBooster> boosterData = new CopyOnWriteArrayList<> ( );
    private static final  Gson GSON = new GsonBuilder ().setPrettyPrinting().create();
    private static final File rootDir = new File( Main.instance.getDataFolder(), "DTools");
    private static final File dataDir = new File(rootDir, "data");
    private static final File MULTIPLIER_FILE = new File(dataDir, "pvboost_multiplier.json");
    private static final File BOOSTERS_FILE = new File(dataDir, "pvboost_boosters.json");

    public static void saveData() {
        if (!dataDir.exists()) dataDir.mkdirs();
        if (boostMultiplier.isEmpty () || boosterData.isEmpty () ) return;
        // Guardar boostMultiplier
        try (FileWriter writer = new FileWriter(MULTIPLIER_FILE)) {
            GSON.toJson(boostMultiplier, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Guardar boosterData
        try (FileWriter writer = new FileWriter(BOOSTERS_FILE)) {
            GSON.toJson(boosterData, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadData() {
        if (!dataDir.exists()) dataDir.mkdirs();

        // Cargar boostMultiplier
        if (MULTIPLIER_FILE.exists()) {
            try (FileReader reader = new FileReader(MULTIPLIER_FILE)) {
                Type type = new TypeToken<ConcurrentHashMap<String, Double>>() {}.getType();
                ConcurrentHashMap<String, Double> loadedData = GSON.fromJson(reader, type);
                if (loadedData != null) boostMultiplier = loadedData;
            } catch (IOException | JsonSyntaxException e) {
                e.printStackTrace();
            }
        }

        // Cargar boosterData
        if (BOOSTERS_FILE.exists()) {
            try (FileReader reader = new FileReader(BOOSTERS_FILE)) {
                Type type = new TypeToken<CopyOnWriteArrayList<VIPBooster>>() {}.getType();
                CopyOnWriteArrayList<VIPBooster> loadedBoosters = GSON.fromJson(reader, type);
                if (loadedBoosters != null) boosterData = loadedBoosters;
            } catch (IOException | JsonSyntaxException e) {
                e.printStackTrace();
            }
        }
    }


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

