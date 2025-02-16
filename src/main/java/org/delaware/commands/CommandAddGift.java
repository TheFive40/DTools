package org.delaware.commands;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.delaware.tools.CC;
import org.delaware.tools.commands.BaseCommand;
import org.delaware.tools.commands.Command;
import org.delaware.tools.commands.CommandArgs;
import org.delaware.tools.model.entities.Localizaciones;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;

public class CommandAddGift extends BaseCommand {
    public static ArrayList<Localizaciones> itemStackHashMap = new ArrayList<>();
    public static ArrayList<ItemStack> itemStacks = new ArrayList<>();
    private static int contador = 0;

    @Command(name = "agregarregalo", aliases = "addgift", permission = "addgift.use")
    @Override
    public void onCommand( CommandArgs command) throws IOException {
        Player player = command.getPlayer();
        ItemStack regalo = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta skullMeta = (SkullMeta) regalo.getItemMeta();
        skullMeta.setOwner("defib");
        regalo.setItemMeta(skullMeta);
        player.getInventory().addItem(regalo);

        Localizaciones localizaciones = new Localizaciones(player.getLocation().getBlockX(),player.getLocation().getBlockY(), player.getLocation().getBlockZ(),
                player.getLocation().getBlock().getWorld().getName());

        player.sendMessage(CC.translate("&c¡Por favor coloca el item en las coordenadas x: " +
                player.getLocation().getBlockX() + " y: " + player.getLocation().getBlockY() + " z: " + player.getLocation().getBlockZ()));

        itemStackHashMap.add(localizaciones);

        contador++;
        setItemInMenu ( regalo, skullMeta, localizaciones );
    }

    public static void setItemInMenu ( ItemStack regalo, SkullMeta skullMeta, Localizaciones localizaciones ) {
        contador++;
        ArrayList<String> skullLore = new ArrayList<>();
        skullLore.add( CC.translate("&cCoordenadas &4x: " + localizaciones.getBloqueX() + " y: " + localizaciones.getBloqueY() + "" +
                " z: " + localizaciones.getBloqueZ()));
        skullLore.add(CC.translate("&cMundo: &4" + localizaciones.getWorld()));
        skullMeta.setLore(skullLore);
        skullMeta.setDisplayName(CC.translate( "&c&lRegalo #" + contador));
        regalo.setItemMeta(skullMeta);
        itemStacks.add(regalo);
    }
}
