package org.delaware.commands;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.delaware.tools.CC;
import org.delaware.tools.commands.BaseCommand;
import org.delaware.tools.commands.Command;
import org.delaware.tools.commands.CommandArgs;
import org.delaware.tools.model.entities.TP;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class TPSCommand extends BaseCommand {
    public static ConcurrentHashMap<Integer, TP> tps = new ConcurrentHashMap<> ( );

    @Command(name = "dbtps", aliases = "dbtps", permission = "DBCFuture.dbtps"
            , usage = "&7Prueba utilizando: &b/dbtps register <amount>")
    @Override
    public void onCommand ( CommandArgs command ) throws IOException {
        if (command.getSender ( ) instanceof Player && command.getArgs ( ).length >= 1) {
            Player player = command.getPlayer ( );
            String args = command.getArgs ( 0 );
            switch (args) {
                case "register":
                    int tpValue = Integer.parseInt ( command.getArgs ( 1 ) );
                    ArrayList<String> lore = new ArrayList<> ( );
                    lore.add ( CC.translate ( "&7Al darle clic derecho " ) );
                    lore.add ( CC.translate ( "&7en el aire te da " + tpValue + " TPS." ) );
                    lore.add ( CC.translate ( "&c " ) );
                    lore.add ( CC.translate ( "&c&lConsumible" ) );

                    TP tp = new TP ( );
                    tp.setDisplayName ( CC.translate ( "&e➤ &f&l" + tpValue + " TPS" ) )
                            .setId ( player.getItemInHand ( ).getType ( ).getId ( ) )
                            .setLore ( lore )
                            .setValue ( tpValue );
                    tps.put ( tpValue, tp );
                    player.sendMessage ( CC.translate ( "&8[&bDBFuture&8] &a&l➤ &eTPS successfully registered and ready to use" ) );
                    player.playSound ( player.getLocation ( ), "random.orb", 1.0f, 1.0f );

                    break;
                case "give":
                    tpValue = Integer.parseInt ( command.getArgs ( 1 ) );
                    player.sendMessage ( CC.translate ( "&8[&bDBFuture&8] &a&l➤ &eYou have received " + tpValue + " TPS in your account" ) );
                    if (!tps.containsKey ( tpValue )) {
                        player.sendMessage ( CC.translate ( "&8[&4&lError&8] &4&l➤ &c TPS amount not registered, please try again!" ) );
                        return;
                    }
                    tp = tps.get ( tpValue );
                    ItemStack item = wrapItemStack ( tp );
                    player.setItemInHand ( item );
                    player.playSound ( player.getLocation ( ), "random.orb", 1.0f, 1.0f );
                    break;
                case "remove":
                case "delete":
                    tpValue = Integer.parseInt ( command.getArgs ( 1 ) );
                    if (!tps.containsKey ( tpValue )) {
                        player.sendMessage ( CC.translate ( "&8[&4&lError&8] &4&l➤ &c TPS amount not registered, please try again!" ) );
                        return;
                    }
                    player.sendMessage ( CC.translate ( "&8[&bDBFuture&8] &a&l➤ &eSuccessfully removed " + tpValue + " TP" ) );
                    tps.remove ( tpValue );
                    player.playSound ( player.getLocation ( ), "random.orb", 1.0f, 1.0f );

                    break;
                case "name":
                    tpValue = Integer.parseInt ( command.getArgs ( 1 ) );
                    if (!tps.containsKey ( tpValue )) {
                        player.sendMessage ( CC.translate ( "&8[&bDBFuture&8] &4&l➤ &c TPS amount not registered, please try again!" ) );
                        return;
                    }
                    StringBuilder display = new StringBuilder ( );
                    for (int i = 2; i < command.length ( ); i++)
                        display.append ( CC.translate ( command.getArgs ( i ) ) ).append ( " " );
                    tp = tps.get ( tpValue );
                    tp.setDisplayName ( CC.translate ( display.toString ( ) ) );
                    player.setItemInHand ( wrapItemStack ( tp ) );
                    player.sendMessage ( CC.translate ( "&8[&bDBFuture&8] &a&l➤ &eName updated successfully" ) );
                    player.playSound ( player.getLocation ( ), "random.orb", 1.0f, 1.0f );
                    break;
                case "list":
                    if (tps.isEmpty ( )) {
                        player.sendMessage ( CC.translate ( "&8[&bDBFuture&8] &eNo hay TPS registrados actualmente." ) );
                    } else {
                        player.sendMessage ( CC.translate ( "&8[&bDBFuture&8] &eLista de TPS registrados:" ) );
                        tps.forEach ( ( value, tps ) -> {
                            player.sendMessage ( CC.translate ( "&6- &e" + value + " TPS: &7" + tps.getDisplayName ( ) ) );
                        } );
                    }
                    player.playSound ( player.getLocation ( ), "random.orb", 1.0f, 1.0f );
                    break;
                case "lore":
                    int index = 0;
                    try {
                        index = Integer.parseInt ( command.getArgs ( 0 ) );
                    } catch (NumberFormatException exception) {
                        player.sendMessage ( CC.translate ( "&8[&4&lError&8] &4&l➤ &cInvalid line number! Please enter a valid number." ) );
                        return;
                    }
                    tp = findTP ( player.getItemInHand ( ) );
                    if (tp != null) {
                        lore = tp.getLore ( );
                        StringBuilder loreText = new StringBuilder ( );
                        for (int i = 1; i < command.length ( ); i++) {
                            loreText.append ( command.getArgs ( i ) ).append ( " " );
                        }
                        if (index >= 0 && index < lore.size ( )) {
                            lore.set ( index, CC.translate ( loreText.toString ( ) ) );
                            tp.setLore ( lore );
                            player.setItemInHand ( wrapItemStack ( tp ) );
                            player.sendMessage ( CC.translate ( "&8[&bDBFuture&8] &a&l��� &eLore line updated successfully at index " + index ) );
                            player.playSound ( player.getLocation ( ), "random.orb", 1.0f, 1.0f );
                            return;
                        }
                        lore.add ( CC.translate ( loreText.toString ( ) ) );
                        tp.setLore ( lore );
                        player.setItemInHand ( wrapItemStack ( tp ) );
                        player.sendMessage ( CC.translate ( "&8[&bDBFuture&8] &a&l��� &eLore line updated successfully at index " + index ) );
                        player.playSound ( player.getLocation ( ), "random.orb", 1.0f, 1.0f );
                        return;
                    }
                    player.sendMessage ( CC.translate ( "&8[&4&lError&8] &4&l��� &cInvalid TPS amount! Please register a TPS amount first." ) );
                    break;
            }
        } else {
            command.getSender ( ).sendMessage ( CC.translate ( "&8[&bDBFuture&8] &eList of available commands:" ) );
            command.getSender ( ).sendMessage ( CC.translate ( "&6/dbtps register <value> &7- Registers a new TPS value." ) );
            command.getSender ( ).sendMessage ( CC.translate ( "&6/dbtps give <value> &7- Gives an item with the registered TPS value." ) );
            command.getSender ( ).sendMessage ( CC.translate ( "&6/dbtps remove <value> &7- Removes a registered TPS value." ) );
            command.getSender ( ).sendMessage ( CC.translate ( "&6/dbtps name <value> <name> &7- Changes the name of the item with the registered TPS value." ) );
            command.getSender ( ).sendMessage ( CC.translate ( "&6/dbtps list &7- Lists all registered TPS values." ) );
            command.getSender ( ).sendMessage ( CC.translate ( "&6/dbtps lore <line> <lore> &7- Modifies the item lore at a specific line." ) );
        }

    }

    public ItemStack wrapItemStack ( TP tp ) {
        ItemStack itemStack = new ItemStack ( tp.getId ( ), 1 );
        ItemMeta meta = itemStack.getItemMeta ( );
        meta.setDisplayName ( CC.translate ( tp.getDisplayName ( ) ) );
        meta.setLore ( tp.getLore ( ) );
        itemStack.setItemMeta ( meta );
        return itemStack;
    }

    public static TP findTP ( ItemStack itemStack ) {
        if (itemStack == null || itemStack.getItemMeta ( ) == null) {
            return null;
        }

        int itemId = itemStack.getType ( ).getId ( );
        String itemDisplayName = itemStack.getItemMeta ( ).getDisplayName ( );
        List<String> itemLore = itemStack.getItemMeta ( ).getLore ( );

        for (TP tp : tps.values ( )) {
            if (tp.getId ( ) == itemId &&
                    Objects.equals ( tp.getDisplayName ( ), CC.translate ( itemDisplayName != null ? itemDisplayName : "" ) ) &&
                    Objects.equals ( tp.getLore ( ), itemLore )) {
                return tp;
            }
        }

        return null;
    }


}
