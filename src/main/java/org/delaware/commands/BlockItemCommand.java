package org.delaware.commands;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.delaware.tools.CC;
import org.delaware.tools.commands.BaseCommand;
import org.delaware.tools.commands.Command;
import org.delaware.tools.commands.CommandArgs;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

public class BlockItemCommand extends BaseCommand {
    public static Set<Integer> blockedIds = new HashSet<> ();

    @Command ( name = "bloquear", inGameOnly = false, aliases = "bloquear", permission = "dtools.bloquear")
    @Override
    public void onCommand ( CommandArgs command ) throws IOException {
        if (command.getArgs().length != 1) {
            command.getSender().sendMessage( CC.translate("&8[&6DBZ&8] &cUso correcto: &e/bloquear <id>&7 - &aBloquea o desbloquea un item por ID"));
            return;
        }
        try {
            int id = Integer.parseInt(command.getArgs()[0]);
            if (blockedIds.contains(id)) {
                blockedIds.remove(id);
                command.getSender().sendMessage(CC.translate("&8[&6DBZ&8] &aItem con ID &e" + id + " &adesbloqueado con &a✔"));
            } else {
                blockedIds.add(id);
                command.getSender().sendMessage(CC.translate("&8[&6DBZ&8] &cItem con ID &e" + id + " &cbloqueado con &4✖"));
            }
        } catch (NumberFormatException e) {
            command.getSender().sendMessage(CC.translate("&8[&6DBZ&8] &cEl ID proporcionado no es válido. Usa solo números."));
        }
    }
    public static void checkInventory( Player player) {
        if (player.hasPermission("dtools.blockeditem")) return;
        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null && blockedIds.contains(item.getTypeId())) {
                player.getInventory().remove(item);
                player.sendMessage(CC.translate("&8[&6DBZ&8] &cUn item bloqueado ha sido eliminado de tu inventario."));
            }
        }
    }
    private static final Gson gson = new Gson();


    public static File getFolder ( JavaPlugin plugin ) {
        File folder = new File(plugin.getDataFolder(), "DBFuture");
        if (!folder.exists()) folder.mkdirs();
        return folder;
    }

    public static void guardarLista( JavaPlugin plugin) {
        File file = new File(getFolder(plugin), "itemsBlocked.json");
        try (Writer writer = new OutputStreamWriter (new FileOutputStream (file), StandardCharsets.UTF_8)) {
            gson.toJson(blockedIds, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void cargarLista(JavaPlugin plugin) {
        File file = new File ( getFolder ( plugin ), "itemsBlocked.json" );
        if (!file.exists ( )) {
            blockedIds = new HashSet<> ( );
            return;
        }
        try (Reader reader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8)) {
            Type type = new TypeToken<HashSet<Integer>> () {}.getType();
            HashSet<Integer> loaded = gson.fromJson(reader, type);
            blockedIds = loaded != null ? loaded : new HashSet<> ();
        } catch (IOException e) {
            e.printStackTrace();
            blockedIds = new HashSet<> ();
        }
    }
}
