package org.delaware.tools;

import JinRyuu.JRMCore.JRMCoreH;
import io.github.facuu16.gohan.dbc.model.DbcPlayer;
import io.github.facuu16.gohan.dbc.model.Stat;
import kamkeel.addon.DBCAddon;
import lombok.Getter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import noppes.npcs.api.entity.IDBCPlayer;
import noppes.npcs.scripted.NpcAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collection;
import java.util.HashMap;

public class General {

    public static final String[] ranks = {"founder", "developer", "manager", "admin", "moderador", "quester", "helper", "constructor", "programador", "dev"};
    public static String DEX = "jrmcDexI";
    public static String SPI = "jrmcCncI";
    public static String CON = "jrmcCnsI";
    public static String STR = "jrmcStrI";
    public static String WIL = "jrmcWill";
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

    public static int getSTAT ( Stat stat, Player entity ) {
        DbcPlayer<EntityPlayerMP> jugador = new DbcPlayer<> ( entity.getUniqueId () );
        return jugador.stat ( stat );
    }

    public static int getLVL ( Player player ) {
        DbcPlayer<EntityPlayerMP> jugador = new DbcPlayer<> ( player.getUniqueId () );
        return jugador.level ();
    }

    public static void setPlayerTps ( Player player, int amount ) {
        NpcAPI.Instance ( ).getPlayer ( player.getName ( ) ).getDBCPlayer ( ).setTP ( getPlayerTps ( player ) + amount );
    }

    public static boolean isConvertibleToInt ( String text ) {
        try {
            Integer.parseInt ( text );
            return true;
        } catch (NumberFormatException error) {
            return false;
        }
    }
}
