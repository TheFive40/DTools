package org.delaware.tools.Pastebin;

import org.bukkit.Bukkit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class PastebinReader {
    private final static Pattern pastebinPattern = Pattern.compile("^https?://(?:www\\.)?pastebin\\.com/(?:raw/)?([a-zA-Z0-9]+)$");
    public static List<String> getFromPastebin(String pastebinUrl) {
        Matcher matcher = pastebinPattern.matcher(pastebinUrl);
        if (!matcher.matches()) {
            return null;
        }
        String pastebinId = matcher.group(1);
        String rawPastebinUrl = "https://pastebin.com/raw/" + pastebinId;
        return downloadPastebinContent(rawPastebinUrl);
    }
    private static List<String> downloadPastebinContent(String pastebinUrl) {
        List<String> lines = new ArrayList<>();

        try {
            URL url = new URL(pastebinUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36");
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        lines.add(line);
                    }
                }
                return lines;
            } else {
                Bukkit.getLogger().log(Level.WARNING, "Pastebin Error!... Server returned HTTP code: {0} for URL: {1}", new Object[]{responseCode, pastebinUrl});
                return null;
            }
        } catch (IOException e) {
            Bukkit.getLogger().log(Level.SEVERE, "Error getting pastebin link!, send log to Spacey: " + e.getMessage(), e);
            return null;
        }
    }
}
