package org.delaware.tools.CustomItems.Scythe;

import lombok.Getter;
import net.minecraft.util.com.google.gson.Gson;
import net.minecraft.util.com.google.gson.GsonBuilder;
import net.minecraft.util.com.google.gson.JsonSyntaxException;
import noppes.npcs.api.entity.IDBCPlayer;
import noppes.npcs.scripted.NpcAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.delaware.Main;
import org.delaware.tools.CC;
import org.delaware.tools.CustomItems.CustomItems;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class ScytheRunnable {
    @Getter
    static BukkitRunnable run = new BukkitRunnable() {
        @Override
        public void run() {
            for(Player player : Bukkit.getServer().getOnlinePlayers()) {
                if(player.getItemInHand() == null) return;
                ItemStack item = player.getItemInHand();
                if(Main.scytheConfig.isEmpty()) {
                    Bukkit.getConsoleSender().sendMessage("Scythe's ID and multiplier is not configured!, please use /ScytheConfig for help");
                    return;
                }
                if(item.getTypeId () == Main.scytheConfig.get("ID")) {
                    CustomItems cItem = new CustomItems(item);
                    cItem.setDamage((calculateLevel(player)*Main.scytheConfig.get("dmgMultiplier"))+14000);
                    player.setItemInHand(cItem.toItemStack());
                }
            }
        }
    };
    private static int calculateLevel(Player player) {
        IDBCPlayer dbcPlayer = NpcAPI.Instance().getPlayer(player.getName()).getDBCPlayer();
        int str = dbcPlayer.getStat("str");
        int dex = dbcPlayer.getStat("dex");
        int con = dbcPlayer.getStat("con");
        int wil = dbcPlayer.getStat("wil");
        int mnd = dbcPlayer.getStat("mnd");
        int spi = dbcPlayer.getStat("spi");
        return (((str+dex+con+wil+mnd+spi)/5)-11);
    }
    public static void saveToConfig() {
        try {
            File rootDir = new File (Main.instance.getDataFolder(), "DTools");
            File dataDir = new File (rootDir, "data");
            if (!dataDir.exists ( )) {
                dataDir.mkdirs ( );
            }
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            HashMap<String, Integer> config = Main.scytheConfig;
            String jsonConfig = gson.toJson(config);
            FileWriter writerConfig = new FileWriter ( new File( dataDir, "ScytheConfig.json" ) );
            writerConfig.write(jsonConfig);
            writerConfig.close();
        }catch(IOException | JsonSyntaxException e) {
            Bukkit.getConsoleSender().sendMessage("Error while writing CustomItems data, send log to SpaceyDCO!");
            throw new RuntimeException(e);
        }
    }
}
