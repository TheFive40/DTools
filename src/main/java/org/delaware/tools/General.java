package org.delaware.tools;

import noppes.npcs.scripted.NpcAPI;
import org.bukkit.entity.Player;

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

    public static int getSTR ( Player player ) {
        String str = General.STATS_MAP.get ( "STR" );
        return NpcAPI.Instance ( ).getPlayer ( player.getName ( ) ).getDBCPlayer ( ).getNbt ( ).getCompound ( "PlayerPersisted" )
                .getInteger ( str );
    }

    public static int getCON ( Player player ) {
        String con = General.STATS_MAP.get ( "CON" );
        return NpcAPI.Instance ( ).getPlayer ( player.getName ( ) ).getDBCPlayer ( ).getNbt ( ).getCompound ( "PlayerPersisted" )
                .getInteger ( con );
    }

    public static int getDEX ( Player player ) {
        String dex = General.STATS_MAP.get ( "DEX" );
        return NpcAPI.Instance ( ).getPlayer ( player.getName ( ) ).getDBCPlayer ( ).getNbt ( ).getCompound ( "PlayerPersisted" )
                .getInteger ( dex );
    }

    public static int getMND ( Player player ) {
        String MND = General.STATS_MAP.get ( "MND" );
        return NpcAPI.Instance ( ).getPlayer ( player.getName ( ) ).getDBCPlayer ( ).getNbt ( ).getCompound ( "PlayerPersisted" )
                .getInteger ( MND );
    }

    public static int getSPI ( Player player ) {
        String spi = General.STATS_MAP.get ( "SPI" );
        return NpcAPI.Instance ( ).getPlayer ( player.getName ( ) ).getDBCPlayer ( ).getNbt ( ).getCompound ( "PlayerPersisted" )
                .getInteger ( spi );
    }

    public static int getWIL ( Player player ) {
        String wil = General.STATS_MAP.get ( "WIL" );
        return NpcAPI.Instance ( ).getPlayer ( player.getName ( ) ).getDBCPlayer ( ).getNbt ( ).getCompound ( "PlayerPersisted" )
                .getInteger ( wil );
    }

    public static int getLVL ( Player player ) {
        int str = General.getSTR ( player );
        int dex = General.getDEX ( player );
        int con = General.getCON ( player );
        int wil = General.getWIL ( player );
        int mnd = General.getMND ( player );
        int spi = General.getSPI ( player );
        return (str + dex + con + wil + mnd + spi) / 5 - 11;
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
