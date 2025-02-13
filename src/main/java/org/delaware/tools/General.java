package org.delaware.tools;

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

}
