package org.delaware.commands;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import fr.minuskube.inv.content.Pagination;
import fr.minuskube.inv.content.SlotIterator;
import io.github.facuu16.gohan.dbc.model.Stat;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.SkullType;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.delaware.Main;
import org.delaware.tools.CC;
import org.delaware.tools.General;
import org.delaware.tools.SkullUtils;
import org.delaware.tools.commands.BaseCommand;
import org.delaware.tools.commands.Command;
import org.delaware.tools.commands.CommandArgs;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static org.delaware.tools.General.*;

public class ComandTopPlayer extends BaseCommand {
    public SmartInventory INVENTORY;
    public static ConcurrentHashMap<String, Integer> playerLvL = new ConcurrentHashMap<> ( );

    @Command(name = "topnivel", aliases = {"topnivel", "toplvl"})
    @Override
    public void onCommand ( CommandArgs command ) throws IOException {


        INVENTORY = SmartInventory.builder ( ).type ( InventoryType.CHEST )
                .size ( 6, 9 ).title ( CC.translate ( "&a&lTOP NIVELES" ) ).id ( "topniveles" )
                .provider ( new InventoryProvider ( ) {
                    @Override
                    public void init ( Player player, InventoryContents inventoryContents ) {
                        for (Player jugadorOnline : Main.instance.getServer ( ).getOnlinePlayers ( )) {
                            if(!General.hasStaffParent ( jugadorOnline )){
                                playerLvL.put ( jugadorOnline.getName ( ), General.getLVL ( jugadorOnline ) );
                            }
                        }
                        inventoryContents.fillBorders ( ClickableItem.empty ( new ItemStack ( Material.STAINED_GLASS_PANE ) ) );
                        ClickableItem[] clickableItem = new ClickableItem[playerLvL.size ( )];
                        Pagination pagination = inventoryContents.pagination ( );
                        DecimalFormat formatter = new DecimalFormat ( "#,###" );
                        List<Map.Entry<String, Integer>> sortedEntries = playerLvL.entrySet()
                                .stream()
                                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                                .collect(Collectors.toList());

                        AtomicInteger i = new AtomicInteger(0);
                        sortedEntries.forEach(entry -> {
                            String name = entry.getKey();
                            if (name == null || name.isEmpty()) return;
                            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(name); // Soporte para jugadores offline
                            Player playerLVL = offlinePlayer.getPlayer();
                            if (playerLVL == null) return;
                            int lvl = getLVL ( playerLVL );
                            int str = General.getSTAT(Stat.STR, playerLVL);
                            int dex = General.getSTAT(Stat.DEX, playerLVL);
                            int con = General.getSTAT(Stat.CON, playerLVL);
                            int wil = General.getSTAT(Stat.WIL, playerLVL);
                            int mnd = General.getSTAT(Stat.MND, playerLVL);
                            int spi = General.getSTAT(Stat.SPI, playerLVL);

                            ArrayList<String> lore = new ArrayList<>();
                            lore.add(CC.translate("&c✷ STR: " + formatter.format(str)));
                            lore.add(CC.translate("&d۞ DEX: &5" + formatter.format(dex)));
                            lore.add(CC.translate("&6❃ CON: &e" + formatter.format(con)));
                            lore.add(CC.translate("&b✦ WIL: &3" + formatter.format(wil)));
                            lore.add(CC.translate("&9✴ MND: &3" + formatter.format(mnd)));
                            lore.add(CC.translate("&f✮ SPI: &7" + formatter.format(spi)));
                            lore.add(CC.translate("&a&l㊝ &aNivel: &2" + formatter.format(lvl)));
                            ItemStack skullPlayer = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
                            SkullMeta skullMeta = (SkullMeta) skullPlayer.getItemMeta();
                            if (skullMeta != null) {
                                skullMeta.setOwner(playerLVL.getName());
                                skullMeta.setDisplayName(CC.translate("&6➤ &e" + playerLVL.getName() + " &6#" + (i.get ()+1)));
                                skullMeta.setLore(lore);
                                skullPlayer.setItemMeta(skullMeta);
                            }
                            clickableItem[i.getAndIncrement()] = ClickableItem.empty(skullPlayer);
                        });



                        ItemStack previous = new ItemStack ( Material.ARROW );
                        ItemMeta itemMeta = previous.getItemMeta ( );
                        itemMeta.setDisplayName ( CC.translate ( "&c&lAtrás" ) );
                        ItemStack next = new ItemStack ( Material.ARROW );
                        ItemMeta itemMetaNext = next.getItemMeta ( );
                        itemMetaNext.setDisplayName ( CC.translate ( "&2&lSiguiente" ) );
                        previous.setItemMeta ( itemMeta );
                        next.setItemMeta ( itemMetaNext );
                        pagination.setItems ( clickableItem );
                        pagination.setItemsPerPage ( 21 );
                        pagination.addToIterator ( inventoryContents.newIterator ( SlotIterator.Type.HORIZONTAL, 1, 1 )
                                .allowOverride ( false ) );
                        inventoryContents.set ( 4, 3, ClickableItem.of ( previous,
                                e -> INVENTORY.open ( command.getPlayer ( ), pagination.previous ( ).getPage ( ) ) ) );
                        inventoryContents.set ( 4, 5, ClickableItem.of ( next,
                                e -> INVENTORY.open ( command.getPlayer ( ), pagination.next ( ).getPage ( ) ) ) );

                    }

                    @Override
                    public void update ( Player player, InventoryContents inventoryContents ) {
                        inventoryContents.fillBorders ( ClickableItem.empty ( new ItemStack ( Material.STAINED_GLASS_PANE ) ) );
                    }
                } ).build ( );
        INVENTORY.open ( command.getPlayer ( ) );
        return;

    }
}
