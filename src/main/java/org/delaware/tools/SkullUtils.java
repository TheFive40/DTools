package org.delaware.tools;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import net.minecraft.util.com.mojang.authlib.GameProfile;
import net.minecraft.util.com.mojang.authlib.properties.Property;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.UUID;
public class SkullUtils {
    public static String getTexture(String playerName) {
        try {
            URL uuidURL = new URL("https://api.mojang.com/users/profiles/minecraft/" + playerName);
            HttpURLConnection uuidConn = (HttpURLConnection) uuidURL.openConnection();
            uuidConn.setRequestMethod("GET");
            uuidConn.setConnectTimeout(5000);
            uuidConn.setReadTimeout(5000);

            if (uuidConn.getResponseCode() == 200) {
                JsonObject uuidJson = JsonParser.parseReader(new InputStreamReader(uuidConn.getInputStream())).getAsJsonObject();
                String uuid = uuidJson.get("id").getAsString();
                URL textureURL = new URL ("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid);
                HttpURLConnection textureConn = (HttpURLConnection) textureURL.openConnection();
                textureConn.setRequestMethod("GET");
                textureConn.setConnectTimeout(5000);
                textureConn.setReadTimeout(5000);

                if (textureConn.getResponseCode() == 200) {
                    JsonObject textureJson = JsonParser.parseReader(new InputStreamReader (textureConn.getInputStream())).getAsJsonObject();
                    return textureJson.getAsJsonArray("properties").get(0).getAsJsonObject().get("value").getAsString();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static ItemStack getSkull( String playerName) {
        ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();

        if (skullMeta != null) {
            skullMeta.setDisplayName("§6➤ §e" + playerName);
            GameProfile profile = new GameProfile(UUID.randomUUID(), playerName);
            profile.getProperties().put("textures", new Property ("textures", getTexture ( playerName )));

            try {
                Field profileField = skullMeta.getClass().getDeclaredField("profile");
                profileField.setAccessible(true);
                profileField.set(skullMeta, profile);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }

            skull.setItemMeta(skullMeta);
        }
        return skull;
    }
    public static ItemStack getSkull( String playerName, String texture) {
        ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();

        if (skullMeta != null) {
            skullMeta.setDisplayName("§6➤ §e" + playerName);
            GameProfile profile = new GameProfile(UUID.randomUUID(), playerName);
            profile.getProperties().put("textures", new Property ("textures", texture));

            try {
                Field profileField = skullMeta.getClass().getDeclaredField("profile");
                profileField.setAccessible(true);
                profileField.set(skullMeta, profile);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }

            skull.setItemMeta(skullMeta);
        }
        return skull;
    }
}
