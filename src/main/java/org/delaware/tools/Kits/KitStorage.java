package org.delaware.tools.Kits;

import org.bukkit.craftbukkit.libs.com.google.gson.Gson;
import org.bukkit.craftbukkit.libs.com.google.gson.GsonBuilder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.Base64;

public class KitStorage {

    private static final Map<String, List<ItemStack>> kitItemsByCategory = new HashMap<>();
    private static final String FILE_NAME = "kits.json";

    public static void removeCategory(String category) {
        kitItemsByCategory.remove(category.toUpperCase());
    }

    public static void addItemToCategory(String category, ItemStack item) {
        kitItemsByCategory.computeIfAbsent(category.toUpperCase(), k -> new ArrayList<>()).add(item);
    }

    public static List<ItemStack> getItemsForCategory(String category) {
        return kitItemsByCategory.getOrDefault(category.toUpperCase(), Collections.emptyList());
    }

    public static Set<String> getAllCategories() {
        return kitItemsByCategory.keySet();
    }

    // ---- SERIALIZACIÓN BASE64 100% SEGURA ----
    private static String itemStackToBase64(ItemStack item) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);
        dataOutput.writeObject(item);
        dataOutput.close();
        return Base64.getEncoder().encodeToString(outputStream.toByteArray());
    }

    private static ItemStack itemStackFromBase64(String data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64.getDecoder().decode(data));
        BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
        ItemStack item = (ItemStack) dataInput.readObject();
        dataInput.close();
        return item;
    }

    public static void save(JavaPlugin plugin) {
        File file = new File(plugin.getDataFolder(), FILE_NAME);

        Map<String, List<String>> serializable = new HashMap<>();

        for (Map.Entry<String, List<ItemStack>> entry : kitItemsByCategory.entrySet()) {
            List<String> itemList = new ArrayList<>();
            for (ItemStack item : entry.getValue()) {
                try {
                    itemList.add(itemStackToBase64(item));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            serializable.put(entry.getKey(), itemList);
        }

        try (Writer writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8)) {
            new GsonBuilder ().setPrettyPrinting().create().toJson(serializable, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void load(JavaPlugin plugin) {
        File file = new File(plugin.getDataFolder(), FILE_NAME);
        if (!file.exists()) return;

        try (Reader reader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8)) {
            Type type = new com.google.gson.reflect.TypeToken<Map<String, List<String>>>() {}.getType();
            Map<String, List<String>> loaded = new Gson ().fromJson(reader, type);

            kitItemsByCategory.clear();

            for (Map.Entry<String, List<String>> entry : loaded.entrySet()) {
                List<ItemStack> items = new ArrayList<>();
                for (String base64 : entry.getValue()) {
                    try {
                        items.add(itemStackFromBase64(base64));
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                kitItemsByCategory.put(entry.getKey(), items);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
