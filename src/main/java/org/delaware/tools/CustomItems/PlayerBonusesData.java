package org.delaware.tools.CustomItems;

import lombok.Getter;
import net.minecraft.util.com.google.gson.Gson;
import net.minecraft.util.com.google.gson.GsonBuilder;
import net.minecraft.util.com.google.gson.JsonSyntaxException;
import org.bukkit.Bukkit;
import org.delaware.Main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Getter
public class PlayerBonusesData {
    public static HashMap<String, PlayerBonusesData> bonusData = new HashMap<>();
    private List<String> stats = new ArrayList<>();
    private List<String> bonusesIDs = new ArrayList<>();
    public PlayerBonusesData(String name, String stat, String bonusID) {
        PlayerBonusesData data = getPlayerBonusData(name);
        data.getBonusesIDs().add(bonusID);
        data.getStats().add(stat);
        bonusData.put(name, data);
    }
    public PlayerBonusesData() {}
    public void removeAllData(String name) {
        if(this.bonusesIDs.isEmpty()) return;
        this.bonusesIDs = new ArrayList<>();
        this.stats = new ArrayList<>();
        bonusData.put(name, this);
    }
    public static PlayerBonusesData getPlayerBonusData(String name) {
        if(!bonusData.containsKey(name)) return new PlayerBonusesData();
        return bonusData.get(name);
    }
    public static void saveToConfig() {
        try {
            File rootDir = new File (Main.instance.getDataFolder(), "DTools");
            File dataDir = new File (rootDir, "data");
            if (!dataDir.exists ( )) {
                dataDir.mkdirs ( );
            }
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String jsonBonusData = gson.toJson(bonusData);
            FileWriter writerPlayerData = new FileWriter ( new File( dataDir, "PlayerBonusesData.json" ) );
            writerPlayerData.write(jsonBonusData);
            writerPlayerData.close();
        }catch(IOException | JsonSyntaxException e) {
            Bukkit.getConsoleSender().sendMessage("Error while writing CustomItems data, send log to SpaceyDCO!");
            throw new RuntimeException(e);
        }
    }
}
