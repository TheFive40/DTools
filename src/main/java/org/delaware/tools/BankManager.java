package org.delaware.tools;

import com.avaje.ebean.validation.NotNull;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import noppes.npcs.api.entity.IDBCPlayer;
import noppes.npcs.scripted.NpcAPI;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.delaware.Main;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BankManager {

    private static final ConcurrentHashMap<String, Double> bankBalances = new ConcurrentHashMap<> ( );
    private static final ConcurrentHashMap<String, Integer> bankLevels = new ConcurrentHashMap<> ( );
    private static final Gson gson = new GsonBuilder ( ).setPrettyPrinting ( ).create ( );
    private static final File bankFolder = new File ( "plugins/DTools/bank" );
    private static final File levelFile = new File ( bankFolder, "levels.json" );
    private static final File lastInterestFile = new File ( bankFolder, "last_interest.txt" );

    public static final Map<Integer, Long> levelLimits = new HashMap<> ( );
    public static final Map<Integer, Double> levelInterestRates = new HashMap<> ( );

    static {
        levelInterestRates.put ( 0, 0.005 ); // 0.5%
        levelInterestRates.put ( 1, 0.007 ); // 0.7%
        levelInterestRates.put ( 2, 0.01 );  // 1.0%
        levelInterestRates.put ( 3, 0.012 ); // 1.2%
        levelInterestRates.put ( 4, 0.015 ); // 1.5%
        levelInterestRates.put ( 5, 0.02 );  // 2.0%
    }

    private static void applyDailyInterest () {
        if (hasInterestBeenAppliedToday ( )) return;
        for (Map.Entry<String, Double> entry : bankBalances.entrySet ( )) {
            String key = entry.getKey ( );
            int level = getBankLevel ( key );
            if (level < 0) continue;
            double balance = entry.getValue ( );
            double rate = levelInterestRates.getOrDefault ( level, 0.0 );
            if (rate <= 0) continue;
            long limit = levelLimits.getOrDefault ( level, 3_000_000L );
            double interest = balance * rate;
            if (balance + interest > limit) {
                bankBalances.put ( key, (double) limit );
            } else {
                bankBalances.put ( key, balance + interest );
            }
        }
        markInterestAppliedToday ( );
    }


    static {
        levelLimits.put ( 0, 3_000_000L );
        levelLimits.put ( 1, 20_000_000L );
        levelLimits.put ( 2, 100_000_000L );
        levelLimits.put ( 3, 500_000_000L );
        levelLimits.put ( 4, 1_000_000_000L );
        levelLimits.put ( 5, 2_000_000_000L );
        levelLimits.put ( 6, 3_000_000_000L );
    }

    public static void init(JavaPlugin plugin) {
        if (!bankFolder.exists()) {
            bankFolder.mkdirs();
        }

        loadAll();

        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, () -> {
            try {
                saveAll();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 100L, 100L);

        scheduleDailyInterest(plugin);
    }
    private static void scheduleDailyInterest(JavaPlugin plugin) {
        long now = System.currentTimeMillis();
        long midnight = java.time.LocalDate.now().plusDays(1).atStartOfDay(java.time.ZoneId.systemDefault()).toInstant().toEpochMilli();
        long delay = (midnight - now) / 50;
        Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, () -> {
            applyDailyInterest();
            Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, BankManager::applyDailyInterest, 0L, 1728000L);
        }, delay);
    }
  /*private static void scheduleDailyInterest(JavaPlugin plugin) {
      long testDelay = 20L * 5;
      long testInterval = 20L * 60;

      Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, () -> {
          applyDailyInterest();
          Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, BankManager::applyDailyInterest, 0L, testInterval);
      }, testDelay);
  }*/


    public static double getBalance ( String playerName ) {
        return bankBalances.getOrDefault ( playerName.toLowerCase ( ), 0.0 );
    }

    public static int getBankLevel ( String playerName ) {
        return bankLevels.getOrDefault ( playerName.toLowerCase ( ), 0 );
    }

    public static void setBankLevel ( String playerName, int level ) {
        bankLevels.put ( playerName.toLowerCase ( ), level );
    }

    private static boolean hasInterestBeenAppliedToday () {
        if (!lastInterestFile.exists ( )) return false;
        try (BufferedReader reader = new BufferedReader ( new FileReader ( lastInterestFile ) )) {
            String lastDate = reader.readLine ( );
            String today = java.time.LocalDate.now ( ).toString ( );
            return today.equals ( lastDate );
        } catch (IOException e) {
            e.printStackTrace ( );
        }
        return false;
    }

    private static void markInterestAppliedToday () {
        try (BufferedWriter writer = new BufferedWriter ( new FileWriter ( lastInterestFile ) )) {
            writer.write ( java.time.LocalDate.now ( ).toString ( ) );
        } catch (IOException e) {
            e.printStackTrace ( );
        }
    }

    public static boolean deposit ( String playerName, double amount ) {
        String key = playerName.toLowerCase ( );
        int level = getBankLevel ( key );
        long limit = levelLimits.getOrDefault ( level, 3_000_000L );
        double current = bankBalances.getOrDefault ( key, 0.0 );

        if (current + amount > limit) {
            Player player = Bukkit.getPlayer ( playerName );
            if (player != null) {
                player.sendMessage ( CC.translate ( "&8[&6DBZ&8] &cNo puedes depositar esa cantidad. Límite de almacenamiento: &f" + limit + " TPS" ) );
                player.playSound ( player.getLocation ( ), Sound.ANVIL_BREAK, 1f, 1f );
            }
            return false;
        }

        bankBalances.merge ( key, amount, Double::sum );
        return true;
    }

    public static boolean withdraw ( String playerName, double amount ) {
        String key = playerName.toLowerCase ( );
        double current = bankBalances.getOrDefault ( key, 0.0 );
        Player player = Bukkit.getPlayer ( playerName );

        if (player == null) return false;

        if (current >= amount) {
            IDBCPlayer dbcPlayer = NpcAPI.Instance ( ).getPlayer ( playerName ).getDBCPlayer ( );
            if (Math.min ( 2_000_000_000, dbcPlayer.getTP ( ) + amount ) == 2_000_000_000) {
                player.sendMessage ( CC.translate ( "&8[&6DBZ&8] &cNo puedes retirar esa cantidad, alcanzarías el límite máximo de TPs (&f2,000,000,000&c)." ) );
                player.playSound ( player.getLocation ( ), Sound.ORB_PICKUP, 1f, 1f );
                player.playSound ( player.getLocation ( ), Sound.ANVIL_BREAK, 1f, 1f );
                return false;
            }
            bankBalances.put ( key, current - amount );
            dbcPlayer.setTP ( (int) (dbcPlayer.getTP ( ) + amount) );
            player.playSound ( player.getLocation ( ), Sound.ORB_PICKUP, 1f, 1f );
            return true;
        }

        player.sendMessage ( CC.translate ( "&8[&6DBZ&8] &cNo tienes suficiente saldo en el banco." ) );
        player.playSound ( player.getLocation ( ), Sound.ANVIL_BREAK, 1f, 1f );
        return false;
    }

    public static void saveAll () {
        for (Map.Entry<String, Double> entry : bankBalances.entrySet ( )) {
            savePlayer ( entry.getKey ( ), entry.getValue ( ) );
        }
        saveLevels ( );
    }

    public static void loadAll () {
        File[] files = bankFolder.listFiles ( ( dir, name ) -> name.endsWith ( ".json" ) && !name.equals ( "levels.json" ) );
        if (files != null) {
            for (File file : files) {
                String playerName = file.getName ( ).replace ( ".json", "" );
                try (Reader reader = new InputStreamReader ( new FileInputStream ( file ), StandardCharsets.UTF_8 )) {
                    Type type = new TypeToken<Double> ( ) {
                    }.getType ( );
                    Double balance = gson.fromJson ( reader, type );
                    bankBalances.put ( playerName.toLowerCase ( ), balance );
                } catch (Exception e) {
                    e.printStackTrace ( );
                }
            }
        }
        loadLevels ( );
    }

    private static void savePlayer ( String playerName, Double balance ) {
        try (Writer writer = new OutputStreamWriter ( new FileOutputStream ( new File ( bankFolder, playerName + ".json" ) ), StandardCharsets.UTF_8 )) {
            gson.toJson ( balance, writer );
        } catch (Exception e) {
            e.printStackTrace ( );
        }
    }

    private static void saveLevels () {
        try (Writer writer = new OutputStreamWriter ( new FileOutputStream ( levelFile ), StandardCharsets.UTF_8 )) {
            gson.toJson ( bankLevels, writer );
        } catch (Exception e) {
            e.printStackTrace ( );
        }
    }

    private static void loadLevels () {
        if (!levelFile.exists ( )) return;
        try (Reader reader = new InputStreamReader ( new FileInputStream ( levelFile ), StandardCharsets.UTF_8 )) {
            Type type = new TypeToken<ConcurrentHashMap<String, Integer>> ( ) {
            }.getType ( );
            ConcurrentHashMap<String, Integer> loaded = gson.fromJson ( reader, type );
            if (loaded != null) {
                bankLevels.putAll ( loaded );
            }
        } catch (Exception e) {
            e.printStackTrace ( );
        }
    }
}
