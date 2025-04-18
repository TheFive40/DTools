package org.delaware.tools.Pastebin;

import org.bukkit.Bukkit;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class PastebinReader {
    private final static Pattern pastebinPattern = Pattern.compile ( "^https?://(?:www\\.)?pastebin\\.com/(?:raw/)?([a-zA-Z0-9]+)$" );

    public static List<String> getFromPastebin ( String pastebinUrl ) {
        Matcher matcher = pastebinPattern.matcher ( pastebinUrl );
        if (!matcher.matches ( )) {
            return null;
        }
        String pastebinId = matcher.group ( 1 );
        String rawPastebinUrl = "https://pastebin.com/raw/" + pastebinId;
        return downloadPastebinContent ( rawPastebinUrl );
    }

    public static String getPasteKey ( String pastebinUrl ) {
        Matcher matcher = pastebinPattern.matcher ( pastebinUrl );
        return matcher.group ( 1 );
    }

    public static String getUserKey ( String apiKey, String username, String password ) {
        try {
            String params = "api_dev_key=" + apiKey +
                    "&api_user_name=" + username +
                    "&api_user_password=" + password;

            URL url = new URL ( "https://pastebin.com/api/api_login.php" );
            URLConnection conn = url.openConnection ( );
            conn.setDoOutput ( true );
            conn.setRequestProperty ( "Content-Type", "application/x-www-form-urlencoded" );
            conn.getOutputStream ( ).write ( params.getBytes ( ) );

            try (BufferedReader reader = new BufferedReader ( new InputStreamReader ( conn.getInputStream ( ) ) )) {
                return reader.readLine ( );
            }
        } catch (Exception e) {
            Bukkit.getLogger ( ).log ( Level.SEVERE, "Error getting Pastebin user_key", e );
            return null;
        }
    }

    public static List<String> getPrivatePasteContent ( String apiKey, String userKey, String pasteUrl ) {
        List<String> text = new ArrayList<> ( );
        try {
            String params = "api_dev_key=" + apiKey +
                    "&api_user_key=" + userKey +
                    "&api_paste_code=" + getPasteKey ( pasteUrl ) +
                    "&api_option=show_paste";

            URL url = new URL ( "https://pastebin.com/api/api_raw.php" );
            URLConnection conn = url.openConnection ( );
            conn.setDoOutput ( true );
            conn.setRequestProperty ( "Content-Type", "application/x-www-form-urlencoded" );
            conn.getOutputStream ( ).write ( params.getBytes ( ) );

            try (BufferedReader reader = new BufferedReader ( new InputStreamReader ( conn.getInputStream ( ) ) )) {
                String line;
                while ((line = reader.readLine ( )) != null) {
                    text.add ( line );
                }
            }
            return text;
        } catch (Exception e) {
            Bukkit.getLogger ( ).log ( Level.SEVERE, "Error fetching private paste content", e );
            return null;
        }
    }

    private static List<String> downloadPastebinContent ( String pastebinUrl ) {
        List<String> text = new ArrayList<> ( );
        try {
            URL url = new URL ( pastebinUrl );
            try (BufferedReader reader = new BufferedReader ( new InputStreamReader ( url.openStream ( ) ) )) {
                String line;
                while ((line = reader.readLine ( )) != null) {
                    text.add ( line );
                }
            }
            return text;
        } catch (Exception e) {
            Bukkit.getLogger ( ).log ( Level.SEVERE, "Error getting pastebin link!, send log to Spacey", e );
            return null;
        }
    }
}
