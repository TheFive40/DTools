package org.delaware.tools.Pastebin;

import org.bukkit.Bukkit;

import java.io.BufferedReader;
import java.io.InputStreamReader;
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
        List<String> text = new ArrayList<>();
        try {
            URL url = new URL(pastebinUrl);
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    text.add(line);
                }
            }
            return text;
        } catch (Exception e) {
            Bukkit.getLogger().log(Level.SEVERE, "Error getting pastebin link!, send log to Spacey", e);
            return null;
        }
    }
}
