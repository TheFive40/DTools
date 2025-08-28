package org.delaware.tools;

import JinRyuu.JRMCore.JRMCoreH;
import com.gmail.filoghost.holograms.api.Hologram;
import com.gmail.filoghost.holograms.api.HolographicDisplaysAPI;
import kamkeel.npcdbc.constants.DBCClass;
import kamkeel.npcdbc.constants.DBCRace;
import kamkeel.npcdbc.data.PlayerBonus;
import kamkeel.npcdbc.scripted.DBCAPI;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.minecraft.entity.player.EntityPlayerMP;
import noppes.npcs.api.entity.IDBCPlayer;
import noppes.npcs.scripted.NpcAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.NPC;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.delaware.Main;
import org.delaware.tools.BoosterHandler.BoosterDataHandler;
import org.delaware.tools.Boosters.VIPBooster;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class General {


    public static final String[] ranks = {"founder", "developer", "manager", "admin", "moderador", "quester", "helper", "constructor", "programador", "dev"
            , "staff", "moderador-manager", "dueño", "owner", "admin-plus", "ayudante", "helper", "builder", "builder-manager"};
    public static String DEX = "jrmcDexI";
    public static String SPI = "jrmcCncI";
    public static String CON = "jrmcCnsI";
    public static String STR = "jrmcStrI";
    public static String WIL = "jrmcWilI";
    public static String MND = "jrmcIntI";
    public static HashMap<String, String> STATS_MAP = new HashMap<> ( );

    static {
        STATS_MAP.put ( "STR", STR );
        STATS_MAP.put ( "DEX", DEX );
        STATS_MAP.put ( "CON", CON );
        STATS_MAP.put ( "WIL", WIL );
        STATS_MAP.put ( "MND", MND );
        STATS_MAP.put ( "SPI", SPI );
    }

    public static String joinText ( String[] args, int start ) {
        StringBuilder reason = new StringBuilder ( );
        for (int i = start; i < args.length; i++) {
            reason.append ( CC.translate ( args[i] + " " ) );
        }
        return reason.toString ( );
    }

    public static int getPlayerTps ( Player player ) {
        return NpcAPI.Instance ( ).getPlayer ( player.getName ( ) ).getDBCPlayer ( ).getTP ( );
    }

    public static String getBoosterTimeLeft ( UUID playerUUID ) {
        VIPBooster booster = BoosterDataHandler.getBoosterByPlayer ( playerUUID );
        if (booster == null || !booster.isActive ( ) || booster.getActivationTime ( ) == null) {
            return "00:00";
        }

        Duration elapsedTime = Duration.between ( booster.getActivationTime ( ), LocalDateTime.now ( ) );
        long remainingSeconds = (60 * 60) - elapsedTime.getSeconds ( );

        if (remainingSeconds <= 0) {
            return "00:00";
        }

        long minutes = remainingSeconds / 60;
        long seconds = remainingSeconds % 60;
        return String.format ( "%02d:%02d", minutes, seconds );
    }

    public static int getSTAT ( String stat, Player entity ) {
        return JRMCoreH.getInt ( toPlayerMP ( entity ), STATS_MAP.get ( stat.toUpperCase ( ) ) );
    }

    public static EntityPlayerMP toPlayerMP ( Player player ) {
        return (EntityPlayerMP) NpcAPI.Instance ( ).getPlayer ( player.getName ( ) ).getDBCPlayer ( )
                .getMCEntity ( );
    }

    public static int getLVL ( Player player ) {
        int str = JRMCoreH.getInt ( toPlayerMP ( player ), STR );
        int dex = JRMCoreH.getInt ( toPlayerMP ( player ), DEX );
        int con = JRMCoreH.getInt ( toPlayerMP ( player ), CON );
        int wil = JRMCoreH.getInt ( toPlayerMP ( player ), WIL );
        int mnd = JRMCoreH.getInt ( toPlayerMP ( player ), MND );
        int spi = JRMCoreH.getInt ( toPlayerMP ( player ), SPI );
        int lvl = (str + dex + con + wil + mnd + spi) / 5 - 11;
        return lvl;
    }

    public static void spawnHologram ( Player player, String text ) {
        Location loc = player.getLocation ( );
        loc.setY ( loc.getY ( ) + 1.5 );
        loc.setZ ( loc.getZ ( ) + 1.0 );
        Hologram hologram = HolographicDisplaysAPI.createHologram ( Main.instance, loc, org.delaware.tools.CC.translate ( text ) );
        Bukkit.getScheduler ( ).runTaskLater ( Main.instance, hologram::delete, 20L );
    }

    public static void spawnHologramKI ( Player player, String text ) {
        Location loc = player.getLocation ( );
        loc.setY ( loc.getY ( ) + 1.0 );
        loc.setZ ( loc.getZ ( ) + 1.0 );
        Hologram hologram = HolographicDisplaysAPI.createHologram ( Main.instance, loc, org.delaware.tools.CC.translate ( text ) );
        Bukkit.getScheduler ( ).runTaskLater ( Main.instance, hologram::delete, 20L );
    }

    public static void spawnHologramSTAM ( Player player, String text ) {
        Location loc = player.getLocation ( );
        loc.setY ( loc.getY ( ) + 1.5 );
        loc.setZ ( loc.getZ ( ) - 1.0 );
        Hologram hologram = HolographicDisplaysAPI.createHologram ( Main.instance, loc, org.delaware.tools.CC.translate ( text ) );
        Bukkit.getScheduler ( ).runTaskLater ( Main.instance, hologram::delete, 20L );
    }

    public static void spawnHologramCON ( Player player, String text ) {
        Location loc = player.getLocation ( );
        loc.setY ( loc.getY ( ) + 0.4 );
        loc.setZ ( loc.getZ ( ) + 1.0 );
        Hologram hologram = HolographicDisplaysAPI.createHologram ( Main.instance, loc, org.delaware.tools.CC.translate ( text ) );
        Bukkit.getScheduler ( ).runTaskLater ( Main.instance, hologram::delete, 20L );
    }

    public static String getRankColorCode ( Player player ) {
        int level = getLVL ( player );
        if (level >= 300 && level <= 1000) {
            return "&8[&fF&8]";
        } else if (level >= 1001 && level <= 3000) {
            return "&8[&fE&8]";
        } else if (level >= 3001 && level <= 6000) {
            return "&8[&fD&8]";
        } else if (level >= 6001 && level <= 10000) {
            return "&8[&2C&8]";
        } else if (level >= 10001 && level <= 14000) {
            return "&8[&2B&8]";
        } else if (level >= 14001 && level <= 18000) {
            return "&8[&aA&8]";
        } else if (level >= 18001 && level <= 28000) {
            return "&8[&aA&c+&8]";
        } else if (level >= 28001 && level <= 38000) {
            return "&8[&5S&8]";
        } else if (level >= 38001 && level <= 50000) {
            return "&8[&5S&c+&8]";
        } else if (level >= 50001 && level <= 70000) {
            return "&8[&cZ&8]";
        } else if (level >= 70001) {
            return "&8[&cZ&4+&8]";
        } else {
            return "&8[?]";
        }
    }

    public static int getMaxHealth ( IDBCPlayer idbcPlayer, double level ) {
        int race = idbcPlayer.getRace ( );
        int dbcclass = idbcPlayer.getDBCClass ( );
        int kiMax = 0;
        int lvlWIL = General.getSTAT ( "CON", Main.instance.getServer ( ).getPlayer ( idbcPlayer.getName ( ) ) );
        String[] skills = idbcPlayer.getNbt ( ).getCompound ( "PlayerPersisted" )
                .getString ( "jrmcSSlts" ).split ( "," );
        int lvl = 0;
        for (String sk : skills) {
            sk = sk.trim ( );
            if (sk.startsWith ( "DF" )) {
                try {
                    int num = Integer.parseInt ( sk.substring ( 2 ) );
                    lvl = Math.max ( lvl, num + 1 );
                } catch (NumberFormatException ignored) {
                }
            }
        }
        int outputExtra = 0;
        switch (race) {
            case DBCRace.HUMAN:
                if (DBCClass.Spiritualist == dbcclass) {
                    double multiplo = 24;
                    outputExtra = (int) ((lvlWIL * multiplo) * (lvl / 100.0));
                    int previousMax = (int) (lvlWIL * multiplo);
                    ;
                    return (int) (previousMax * level);
                } else if (dbcclass == DBCClass.Warrior) {
                    double multiplo = 25.5;
                    outputExtra = (int) ((lvlWIL * multiplo) * (lvl / 100.0));
                    int previousMax = (int) (lvlWIL * multiplo);
                    ;
                    return (int) (previousMax * level);
                } else {
                    double multiplo = 25;
                    outputExtra = (int) ((lvlWIL * multiplo) * (lvl / 100.0));
                    int previousMax = (int) (lvlWIL * multiplo);
                    return (int) (previousMax * level);
                }
            case DBCRace.SAIYAN:
                if (DBCClass.Spiritualist == dbcclass) {
                    double multiplo = 23.5;
                    outputExtra = (int) ((lvlWIL * multiplo) * (lvl / 100.0));
                    int previousMax = (int) (lvlWIL * multiplo);
                    return (int) (previousMax * level);
                } else if (dbcclass == DBCClass.Warrior) {
                    double multiplo = 24.5;
                    outputExtra = (int) (lvlWIL * multiplo) * (lvl / 100);
                    int previousMax = (int) (lvlWIL * multiplo);
                    return (int) (previousMax * level);
                } else {
                    double multiplo = 24;
                    outputExtra = (int) ((lvlWIL * multiplo) * (lvl / 100.0));
                    int previousMax = (int) (lvlWIL * multiplo);
                    return (int) (previousMax * level);
                }
            case DBCRace.HALFSAIYAN:
                if (DBCClass.Spiritualist == dbcclass) {
                    double multiplo = 23.5;
                    outputExtra = (int) ((lvlWIL * multiplo) * (lvl / 100.0));
                    int previousMax = (int) (lvlWIL * multiplo);
                    return (int) (previousMax * level);
                } else if (dbcclass == DBCClass.Warrior) {
                    double multiplo = 24.5;
                    outputExtra = (int) ((lvlWIL * multiplo) * (lvl / 100.0));
                    int previousMax = (int) (lvlWIL * multiplo);
                    return (int) (previousMax * level);
                } else {
                    double multiplo = 24.5;
                    outputExtra = (int) ((lvlWIL * multiplo) * (lvl / 100.0));
                    int previousMax = (int) (lvlWIL * multiplo);
                    return (int) (previousMax * level);
                }
            case DBCRace.NAMEKIAN:
                if (DBCClass.Spiritualist == dbcclass) {
                    double multiplo = 24;
                    outputExtra = (int) ((lvlWIL * multiplo) * (lvl / 100.0));
                    int previousMax = (int) (lvlWIL * multiplo);
                    return (int) (previousMax * level);
                } else if (dbcclass == DBCClass.Warrior) {
                    double multiplo = 25.5;
                    outputExtra = (int) ((lvlWIL * multiplo) * (lvl / 100.0));
                    int previousMax = (int) (lvlWIL * multiplo);
                    return (int) (previousMax * level);
                } else {
                    double multiplo = 24.5;
                    outputExtra = (int) ((lvlWIL * multiplo) * (lvl / 100.0));
                    int previousMax = (int) (lvlWIL * multiplo);
                    return (int) (previousMax * level);
                }
            case DBCRace.ARCOSIAN:
                if (DBCClass.Spiritualist == dbcclass) {
                    double multiplo = 23;
                    outputExtra = (int) ((lvlWIL * multiplo) * (lvl / 100.0));
                    int previousMax = (int) (lvlWIL * multiplo);
                    return (int) (previousMax * level);
                } else if (dbcclass == DBCClass.Warrior) {
                    double multiplo = 23.5;
                    outputExtra = (int) ((lvlWIL * multiplo) * (lvl / 100.0));
                    int previousMax = (int) (lvlWIL * multiplo);
                    return (int) (previousMax * level);
                } else {
                    double multiplo = 23.5;
                    outputExtra = (int) ((lvlWIL * multiplo) * (lvl / 100.0));
                    int previousMax = (int) (lvlWIL * multiplo);
                    return (int) (previousMax * level);
                }
            case DBCRace.MAJIN:
                if (DBCClass.Spiritualist == dbcclass) {
                    double multiplo = 25.5;
                    outputExtra = (int) ((lvlWIL * multiplo) * (lvl / 100.0));
                    int previousMax = (int) (lvlWIL * multiplo);
                    return (int) (previousMax * level);
                } else if (dbcclass == DBCClass.Warrior) {
                    double multiplo = 27.5;
                    outputExtra = (int) ((lvlWIL * multiplo) * (lvl / 100.0));
                    int previousMax = (int) (lvlWIL * multiplo);
                    return (int) (previousMax * level);
                } else {
                    double multiplo = 26.5;
                    outputExtra = (int) ((lvlWIL * multiplo) * (lvl / 100.0));
                    int previousMax = (int) (lvlWIL * multiplo);
                    return (int) (previousMax * level);
                }
        }
        return kiMax;
    }


    public static int getKiMax ( IDBCPlayer idbcPlayer, double level ) {
        int race = idbcPlayer.getRace ( );
        int dbcclass = idbcPlayer.getDBCClass ( );
        int kiMax = 0;
        int lvlWIL = General.getSTAT ( "SPI", Main.instance.getServer ( ).getPlayer ( idbcPlayer.getName ( ) ) );
        String[] skills = idbcPlayer.getNbt ( ).getCompound ( "PlayerPersisted" )
                .getString ( "jrmcSSlts" ).split ( "," );
        int lvl = 0;
        for (String sk : skills) {
            sk = sk.trim ( );
            if (sk.startsWith ( "KB" )) {
                try {
                    int num = Integer.parseInt ( sk.substring ( 2 ) );
                    lvl = Math.max ( lvl, num + 1 );
                } catch (NumberFormatException ignored) {
                }
            }
        }
        int outputExtra = 0;
        switch (race) {
            case DBCRace.HUMAN:
                if (DBCClass.Spiritualist == dbcclass) {
                    double multiplo = 42;
                    outputExtra = (int) ((lvlWIL * multiplo) * (lvl / 100.0));
                    int previousMax = (int) (lvlWIL * multiplo) + outputExtra;
                    ;
                    return (int) (previousMax * level);
                } else if (dbcclass == DBCClass.Warrior) {
                    double multiplo = 39;
                    outputExtra = (int) ((lvlWIL * multiplo) * (lvl / 100.0));
                    int previousMax = (int) (lvlWIL * multiplo) + outputExtra;
                    ;
                    return (int) (previousMax * level);
                } else {
                    double multiplo = 38;
                    outputExtra = (int) ((lvlWIL * multiplo) * (lvl / 100.0));
                    int previousMax = (int) (lvlWIL * multiplo) + outputExtra;
                    ;
                    return (int) (previousMax * level);
                }
            case DBCRace.SAIYAN:
                if (DBCClass.Spiritualist == dbcclass) {
                    double multiplo = 41;
                    outputExtra = (int) ((lvlWIL * multiplo) * (lvl / 100.0));
                    int previousMax = (int) (lvlWIL * multiplo) + outputExtra;
                    ;
                    return (int) (previousMax * level);
                } else if (dbcclass == DBCClass.Warrior) {
                    double multiplo = 38;
                    outputExtra = (int) (lvlWIL * multiplo) * (lvl / 100);
                    int previousMax = (int) (lvlWIL * multiplo) + outputExtra;
                    ;
                    return (int) (previousMax * level);
                } else {
                    double multiplo = 42;
                    outputExtra = (int) ((lvlWIL * multiplo) * (lvl / 100.0));
                    int previousMax = (int) (lvlWIL * multiplo) + outputExtra;
                    ;
                    return (int) (previousMax * level);
                }
            case DBCRace.HALFSAIYAN:
                if (DBCClass.Spiritualist == dbcclass) {
                    double multiplo = 42;
                    outputExtra = (int) ((lvlWIL * multiplo) * (lvl / 100.0));
                    int previousMax = (int) (lvlWIL * multiplo) + outputExtra;
                    ;
                    return (int) (previousMax * level);
                } else if (dbcclass == DBCClass.Warrior) {
                    double multiplo = 39;
                    outputExtra = (int) ((lvlWIL * multiplo) * (lvl / 100.0));
                    int previousMax = (int) (lvlWIL * multiplo) + outputExtra;
                    ;
                    return (int) (previousMax * level);
                } else {
                    double multiplo = 40;
                    outputExtra = (int) ((lvlWIL * multiplo) * (lvl / 100.0));
                    int previousMax = (int) (lvlWIL * multiplo) + outputExtra;
                    ;
                    return (int) (previousMax * level);
                }
            case DBCRace.NAMEKIAN:
                if (DBCClass.Spiritualist == dbcclass) {
                    double multiplo = 45;
                    outputExtra = (int) ((lvlWIL * multiplo) * (lvl / 100.0));
                    int previousMax = (int) (lvlWIL * multiplo) + outputExtra;
                    ;
                    return (int) (previousMax * level);
                } else if (dbcclass == DBCClass.Warrior) {
                    double multiplo = 40;
                    outputExtra = (int) ((lvlWIL * multiplo) * (lvl / 100.0));
                    int previousMax = (int) (lvlWIL * multiplo) + outputExtra;
                    ;
                    return (int) (previousMax * level);
                } else {
                    double multiplo = 44;
                    outputExtra = (int) ((lvlWIL * multiplo) * (lvl / 100.0));
                    int previousMax = (int) (lvlWIL * multiplo) + outputExtra;
                    ;
                    return (int) (previousMax * level);
                }
            case DBCRace.ARCOSIAN:
                if (DBCClass.Spiritualist == dbcclass) {
                    double multiplo = 50;
                    outputExtra = (int) ((lvlWIL * multiplo) * (lvl / 100.0));
                    int previousMax = (int) (lvlWIL * multiplo) + outputExtra;
                    ;
                    return (int) (previousMax * level);
                } else if (dbcclass == DBCClass.Warrior) {
                    double multiplo = 44;
                    outputExtra = (int) ((lvlWIL * multiplo) * (lvl / 100.0));
                    int previousMax = (int) (lvlWIL * multiplo) + outputExtra;
                    ;
                    return (int) (previousMax * level);
                } else {
                    double multiplo = 46;
                    outputExtra = (int) ((lvlWIL * multiplo) * (lvl / 100.0));
                    int previousMax = (int) (lvlWIL * multiplo) + outputExtra;
                    ;
                    return (int) (previousMax * level);
                }
            case DBCRace.MAJIN:
                if (DBCClass.Spiritualist == dbcclass) {
                    double multiplo = 40;
                    outputExtra = (int) ((lvlWIL * multiplo) * (lvl / 100.0));
                    int previousMax = (int) (lvlWIL * multiplo) + outputExtra;
                    ;
                    return (int) (previousMax * level);
                } else if (dbcclass == DBCClass.Warrior) {
                    double multiplo = 36;
                    outputExtra = (int) ((lvlWIL * multiplo) * (lvl / 100.0));
                    int previousMax = (int) (lvlWIL * multiplo) + outputExtra;
                    ;
                    return (int) (previousMax * level);
                } else {
                    double multiplo = 36;
                    outputExtra = (int) ((lvlWIL * multiplo) * (lvl / 100.0));
                    int previousMax = (int) (lvlWIL * multiplo) + outputExtra;
                    ;
                    return (int) (previousMax * level);
                }
        }
        return kiMax;
    }

    public static String getRank ( Player player ) {
        int level = getLVL ( player );

        if (level >= 300 && level <= 1000) {
            return "F";
        } else if (level >= 1001 && level <= 3000) {
            return "E";
        } else if (level >= 3001 && level <= 6000) {
            return "D";
        } else if (level >= 6001 && level <= 10000) {
            return "C";
        } else if (level >= 10001 && level <= 14000) {
            return "B";
        } else if (level >= 14001 && level <= 18000) {
            return "A";
        } else if (level >= 18001 && level <= 28000) {
            return "A+";
        } else if (level >= 28001 && level <= 38000) {
            return "S";
        } else if (level >= 38001 && level <= 50000) {
            return "S+";
        } else if (level >= 50001 && level <= 70000) {
            return "Z";
        } else if (level >= 70001) {
            return "Z+";
        } else {
            return "?";
        }
    }

    public static boolean hasStaffParent ( Player player ) {
        LuckPerms luckPerms = LuckPermsProvider.get ( );
        User user = luckPerms.getUserManager ( ).getUser ( player.getUniqueId ( ) );

        if (user == null) return false;

        return user.getPrimaryGroup ( ).equalsIgnoreCase ( "staff" ) ||
                user.getInheritedGroups ( user.getQueryOptions ( ) ).stream ( )
                        .anyMatch ( group -> group.getName ( ).equalsIgnoreCase ( "staff" ) );
    }

    public static boolean isHakaishin ( Player player ) {
        LuckPerms luckPerms = LuckPermsProvider.get ( );
        User user = luckPerms.getUserManager ( ).getUser ( player.getUniqueId ( ) );

        if (user == null) return false;

        return user.getPrimaryGroup ( ).equalsIgnoreCase ( "hakaishin" ) ||
                user.getInheritedGroups ( user.getQueryOptions ( ) ).stream ( )
                        .anyMatch ( group -> group.getName ( ).equalsIgnoreCase ( "hakaishin" ) );
    }

    public static String getGroup ( UUID playerUUID ) {
        LuckPerms luckPerms = LuckPermsProvider.get ( );
        if (luckPerms == null) {
            System.out.println ( "[PVBooster] Error: LuckPerms no está disponible." );
            return "default";
        }

        User user = luckPerms.getUserManager ( ).getUser ( playerUUID );
        if (user == null) {
            System.out.println ( "[PVBooster] Advertencia: No se encontró el usuario en LuckPerms para UUID " + playerUUID );
            return "default";
        }

        return user.getPrimaryGroup ( ).toLowerCase ( );
    }

    public static void setPlayerTps ( Player player, int amount ) {
        NpcAPI.Instance ( ).getPlayer ( player.getName ( ) ).getDBCPlayer ( ).setTP ( getPlayerTps ( player ) + amount );
    }

    public static String formatDuration ( Duration duration ) {
        long hours = duration.toHours ( ) % 24;
        long minutes = duration.toMinutes ( ) % 60;
        long seconds = duration.getSeconds ( ) % 60;
        return String.format ( "%02dh %02dm %02ds", hours, minutes, seconds );
    }

    public static List<Player> getStaffs () {
        ArrayList<Player> staffs = new ArrayList<> ( );
        for (Player player : Main.instance.getServer ( ).getOnlinePlayers ( )) {
            if (hasStaffParent ( player )) {
                staffs.add ( player );
            }
        }
        return staffs;
    }

    public static boolean isConvertibleToInt ( String text ) {
        try {
            Integer.parseInt ( text );
            return true;
        } catch (NumberFormatException error) {
            return false;
        }
    }

    public static int getRandomNumber ( int min, int max ) {
        return (int) (Math.random ( ) * ((max - min) + 1)) + min;
    }
}
