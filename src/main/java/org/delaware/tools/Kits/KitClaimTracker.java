package org.delaware.tools.Kits;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class KitClaimTracker {

    private static final Map<UUID, Set<String>> claimed = new HashMap<>();
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final String FILE_NAME = "kit_claims.json";

    public static boolean hasClaimed(UUID uuid, String category) {
        return claimed.getOrDefault(uuid, new HashSet<>()).contains(category.toLowerCase());
    }

    public static void markClaimed(UUID uuid, String category) {
        claimed.computeIfAbsent(uuid, k -> new HashSet<>()).add(category.toLowerCase());
    }
    public static void removeClaim(UUID uuid, String category) {
        Set<String> claims = claimed.get(uuid);
        if (claims != null) {
            claims.remove(category.toLowerCase());
            if (claims.isEmpty()) {
                claimed.remove(uuid); 
            }
        }
    }

    public static void save(JavaPlugin plugin) {
        File file = new File(plugin.getDataFolder(), FILE_NAME);
        Map<String, List<String>> serializable = new HashMap<>();
        for (Map.Entry<UUID, Set<String>> entry : claimed.entrySet()) {
            serializable.put(entry.getKey().toString(), new ArrayList<>(entry.getValue()));
        }

        try (Writer writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8)) {
            gson.toJson(serializable, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void load(JavaPlugin plugin) {
        File file = new File(plugin.getDataFolder(), FILE_NAME);
        if (!file.exists()) return;

        try (Reader reader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8)) {
            Type type = new TypeToken<Map<String, List<String>>>() {}.getType();
            Map<String, List<String>> loaded = gson.fromJson(reader, type);

            claimed.clear();
            for (Map.Entry<String, List<String>> entry : loaded.entrySet()) {
                UUID uuid = UUID.fromString(entry.getKey());
                Set<String> set = new HashSet<>();
                for (String category : entry.getValue()) {
                    set.add(category.toLowerCase());
                }
                claimed.put(uuid, set);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void resetClaims() {
        claimed.clear();
    }
}
