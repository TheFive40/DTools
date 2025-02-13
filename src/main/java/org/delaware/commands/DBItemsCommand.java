package org.delaware.commands;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.delaware.tools.CC;
import org.delaware.tools.General;
import org.delaware.tools.commands.BaseCommand;
import org.delaware.tools.commands.Command;
import org.delaware.tools.commands.CommandArgs;
import org.delaware.tools.model.entities.Attribute;
import org.delaware.tools.model.entities.DBItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import static org.delaware.Main.armors;

public class DBItemsCommand extends BaseCommand {


    public static ConcurrentHashMap<String, DBItem> itemsRegistry = new ConcurrentHashMap<> ( );

    @Command(aliases = "dbitem", name = "dbitem")
    @Override
    public void onCommand ( CommandArgs command ) throws IOException {
        Player player = command.getPlayer ( );
        if (command.getArgs ( ).length == 0) {
            player.performCommand ( "dbitem help" );
            return;
        }
        if (command.getArgs ( ).length >= 3 && command.getArgs ( 1 ).equalsIgnoreCase ( "name" )) {
            ItemStack item = player.getItemInHand ( );
            if (item != null) {
                DBItem dbItem;
                try {
                    dbItem = itemsRegistry.get ( command.getArgs ( 2 ) );
                    StringBuilder name = new StringBuilder ( );
                    for (int i = 3; i < command.length ( ); i++)
                        name.append ( CC.translate ( command.getArgs ( i ) ) ).append ( " " );
                    dbItem.setName ( CC.translate ( name.toString ( ).trim ( ) ) );
                    itemsRegistry.put ( dbItem.getIdentifier ( ), dbItem );
                    ItemMeta meta = item.getItemMeta ( );
                    meta.setDisplayName ( CC.translate ( name.toString ( ) ) );
                    item.setItemMeta ( meta );
                    player.sendMessage ( CC.translate ( "&8[&6&lDBNetwork&8] &aSuccess: &7The item's name has been changed to &6" + dbItem.getName ( ) + "&7." ) );
                } catch (NullPointerException exception) {
                    player.sendMessage ( CC.translate ( "&8[&6&lDBNetwork&8] &cError: &7The item could not be processed because it is null or invalid. Please check and try again." ) );
                    return;
                }

            }
            return;
        }
        switch (command.length ( )) {
            case 1:
                String arg0 = command.getArgs ( 0 );
                if (arg0.equalsIgnoreCase ( "list" )) {
                    if (itemsRegistry.isEmpty ( )) {
                        command.getSender ( ).sendMessage ( CC.translate ( "&8[&6&lDBNetwork&8] &cError: &7No items have been registered yet." ) );
                        return;
                    }

                    command.getSender ( ).sendMessage ( CC.translate ( "&a&l&m----------------------------------------" ) );
                    command.getSender ( ).sendMessage ( CC.translate ( "&6&lRegistered Items:" ) );
                    for (String itemId : itemsRegistry.keySet ( )) {
                        DBItem dbItem = itemsRegistry.get ( itemId );
                        command.getSender ( ).sendMessage ( CC.translate ( "&7- &6" + itemId + " &7(&a" + dbItem.getId ( ) + "&7)" ) );
                    }
                    command.getSender ( ).sendMessage ( CC.translate ( "&a&l&m---------------------------------------------" ) );
                } else if (arg0.equalsIgnoreCase ( "help" )) {
                    command.getSender ( ).sendMessage ( CC.translate ( "&8[&6&lDBNetwork&8] &eAvailable Commands for &6dbitem&e:" ) );
                    command.getSender ( ).sendMessage ( CC.translate ( "&a&l&m---------------------------------------------" ) );
                    command.getSender ( ).sendMessage ( CC.translate ( "&6/dbitem give <item> &7- Gives an item with a specific booster." ) );
                    command.getSender ( ).sendMessage ( CC.translate ( "&6/dbitem list &7- Lists all registered items with boosters." ) );
                    command.getSender ( ).sendMessage ( CC.translate ( "&6/dbitem help &7- Displays the list of available commands for &6dbitem&7." ) );
                    command.getSender ( ).sendMessage ( CC.translate ( "&6/dbitem author &7- Shows information about the creator of the booster system." ) );
                    command.getSender ( ).sendMessage ( CC.translate ( "&6/dbitem register <name> &7- Registers a new item with the specified name." ) );
                    command.getSender ( ).sendMessage ( CC.translate ( "&6/dbitem set bonus <id> <Attribute> <bonus> &7- Sets a specific bonus for an item's attribute." ) );
                    command.getSender ( ).sendMessage ( CC.translate ( "&6/dbitem set cost <id> <Attribute> <cost> &7- Sets the cost of a specific attribute for an item." ) );
                    command.getSender ( ).sendMessage ( CC.translate ( "&6/dbitem set name <id> <displayName> &7- Sets the visible name of an item." ) );
                    command.getSender ( ).sendMessage ( CC.translate ( "&6/dbitem set type <id> <itemType> &7- Sets the type of the item (e.g., Armor, Sword, Consumable)." ) );
                    command.getSender ( ).sendMessage ( CC.translate ( "&6/dbitem remove <id> &7- Removes a registered item using its ID." ) );
                    command.getSender ( ).sendMessage ( CC.translate ( "&6/dbitem commit <id> &7- Commits the changes made to an item." ) );
                    command.getSender ( ).sendMessage ( CC.translate ( "&6/dbitem set armor <chestplate> <leggings> <boots> &7- Sets the armor pieces for an item." ) );
                    command.getSender ( ).sendMessage ( CC.translate ( "&a&l&m---------------------------------------------" ) );
                } else if (arg0.equalsIgnoreCase ( "author" )) {
                    command.getSender ( ).sendMessage ( CC.translate ( "&8[&6&lDBNetwork&8] &eThis booster system was created by &6TheFive &e(or &6DelawareX&e)." ) );
                    command.getSender ( ).sendMessage ( CC.translate ( "&8[&6&lDBNetwork&8] &7Thank you for using our system!" ) );
                }
                break;
            case 2:
                arg0 = command.getArgs ( 0 );
                if (arg0.equalsIgnoreCase ( "give" )) {
                    String itemName = command.getArgs ( 1 ).toUpperCase ( );
                    DBItem item = itemsRegistry.get ( itemName );
                    if (item != null) {
                        ItemStack itemStack = wrapItemStack ( item );
                        player.getInventory ( ).addItem ( itemStack );
                        command.getSender ( ).sendMessage ( CC.translate ( "&aEl ítem &6" + item.getName ( ) + " &aha sido añadido a tu inventario." ) );

                    }
                } else if (arg0.equalsIgnoreCase ( "register" )) {
                    String dbItemId = command.getArgs ( 1 );
                    DBItem dbItem = new DBItem ( );
                    ItemStack item = player.getItemInHand ( );
                    if (item != null) {
                        ItemMeta meta = item.getItemMeta ( );
                        meta.setDisplayName ( dbItemId );
                        item.setItemMeta ( meta );
                        dbItem.setId ( item.getType ( ).getId ( ) );
                        dbItem.setName ( dbItemId );
                        dbItem.setIdentifier ( dbItemId );
                        itemsRegistry.put ( dbItemId, dbItem );
                        player.sendMessage ( CC.translate ( "&aThe item &6" + dbItemId + " &ahas been successfully registered." ) );
                        return;
                    }
                    player.sendMessage ( CC.translate ( "&8[&6&lDBNetwork&8] &cError: &7You must hold an item in your hand to register it!" ) );
                } else if (arg0.equalsIgnoreCase ( "remove" )) {
                    if (command.length ( ) < 2) {
                        command.getSender ( ).sendMessage ( CC.translate ( "&8[&6&lDBNetwork&8] &cError: &7You must specify an item ID to remove." ) );
                        return;
                    }

                    String itemId = command.getArgs ( 1 );
                    if (itemsRegistry.containsKey ( itemId )) {
                        itemsRegistry.remove ( itemId );
                        command.getSender ( ).sendMessage ( CC.translate ( "&8[&6&lDBNetwork&8] &aThe item &6" + itemId + " &ahas been successfully removed." ) );
                    } else {
                        command.getSender ( ).sendMessage ( CC.translate ( "&8[&6&lDBNetwork&8] &cError: &7No item found with the ID &6" + itemId + "&7." ) );
                    }
                } else if (arg0.equalsIgnoreCase ( "commit" )) {
                    command.getSender ( ).sendMessage ( CC.translate ( "&8[&6&lDBNetwork&8] &eCommitting changes..." ) );
                    String id = command.getArgs ( 1 );
                    DBItem dbItem = itemsRegistry.get ( id );
                    if (dbItem != null) {
                        List<String> lore = new ArrayList<> ( );
                        lore.add ( "" );
                        lore.add ( CC.translate ( "&c" + dbItem.getName ( ) + " &7(#" + dbItem.getId ( ) + ") " + dbItem.getId ( ) ) );
                        lore.add ( CC.translate ( "&7This item belongs to &6The Legendary Realm." ) );
                        lore.add ( CC.translate ( "&a&lAdvantages:" ) );
                        if (dbItem.getAttribute ( ) != null) {
                            Attribute attribute = dbItem.getAttribute ( );
                            if (attribute.getBonus ( ) > 0) {
                                lore.add ( CC.translate ( "&7- Increases &6" + attribute.getStatBonus ( ) + " &7by &a+" + (int) (attribute.getBonus ( ) * 100) + "%." ) );
                            }
                            lore.add ( CC.translate ( "&7- Improves overall performance." ) );
                        } else {
                            lore.add ( CC.translate ( "&7- No specific advantages available." ) );
                        }
                        lore.add ( CC.translate ( "&c&lNegative Effect:" ) );
                        if (dbItem.getAttribute ( ) != null) {
                            Attribute attribute = dbItem.getAttribute ( );
                            lore.add ( CC.translate ( "&7- Costs &6" + attribute.getCost ( ) + "&7 resources." ) );
                            lore.add ( CC.translate ( "&7- Reduces your &6" + attribute.getStatCost ( ) + " &7by &c-" + (int) (attribute.getCost ( ) * 100) + "%." ) );
                        } else {
                            lore.add ( CC.translate ( "&7- No negative effects defined." ) );
                        }
                        lore.add ( "" );
                        lore.add ( CC.translate ( "&d&lLEGENDARY" ) );
                        dbItem.setLore ( lore );
                        command.getSender ( ).sendMessage ( CC.translate ( "&8[&6&lDBNetwork&8] &aThe lore for item &6" + dbItem.getName ( ) + " &ahas been successfully committed." ) );
                        itemsRegistry.put ( id, dbItem );
                    } else {
                        command.getSender ( ).sendMessage ( CC.translate ( "&8[&6&lDBNetwork&8] &cError: &7No item found with the ID &6" + id + "&7." ) );
                    }
                }
                break;
            case 4:
                String subCommand = command.getArgs ( 1 ).toLowerCase ( );
                if (subCommand.equalsIgnoreCase ( "type" )) {
                    String id = command.getArgs ( 2 );
                    DBItem dbItem = itemsRegistry.get ( id );
                    if (dbItem == null) {
                        command.getSender ( ).sendMessage ( CC.translate ( "&8[&6&lDBNetwork&8] &cError: &7No item found with the ID &6" + id + "&7." ) );
                        return;
                    }
                    String type = command.getArgs ( 3 ).toUpperCase ( );
                    if (!type.equals ( "ARMOR" ) && !type.equals ( "SWORD" ) && !type.equals ( "CONSUMABLE" )) {
                        command.getSender ( ).sendMessage ( CC.translate ( "&8[&6&lDBNetwork&8] &cError: &7Invalid type. Valid types are: &6Armor, Sword, Consumable." ) );
                        return;
                    }
                    dbItem.setType ( type );
                    itemsRegistry.put ( id, dbItem );
                    command.getSender ( ).sendMessage ( CC.translate ( "&8[&6&lDBNetwork&8] &aThe type for item &6" + dbItem.getName ( ) + " &ahas been set to &6" + type + "&a." ) );
                    return;
                }
                break;
            case 5:
                String arg1 = command.getArgs ( 1 );
                if (arg1.equalsIgnoreCase ( "armor" )) {
                    String id_chesplate = command.getArgs ( 2 );
                    String id_leggins = command.getArgs ( 3 );
                    String id_boots = command.getArgs ( 4 );
                    DBItem chestplate = itemsRegistry.get ( id_chesplate );
                    DBItem leggings = itemsRegistry.get ( id_leggins );
                    DBItem boots = itemsRegistry.get ( id_boots );
                    armors.add ( new ArrayList<> ( Arrays.asList ( chestplate, leggings, boots ) ) );
                    command.getSender().sendMessage(CC.translate(
                            "&8[&6&lDBNetwork&8] &aThe armor pieces have been set: &6Chestplate: &e"
                                    + chestplate.getName () + "&6, Leggings: &e" + leggings.getName () + "&6, Boots: &e" + boots.getName () + "&a."
                    ));
                    player.playSound ( player.getLocation ( ), "random.orb", 1.0f, 1.0f );
                    return;
                }
                String attribute = command.getArgs ( 3 ).toUpperCase ( );
                double bonus;
                try {
                    bonus = Double.parseDouble ( command.getArgs ( 4 ) );
                } catch (NumberFormatException exception) {
                    command.getSender ( ).sendMessage ( CC.translate ( "&8[&6&lDBNetwork&8] &cError: &7The value &6'" + command.getArgs ( 3 ) + "' &7is not a valid number." ) );
                    return;
                }
                if (arg1.equalsIgnoreCase ( "bonus" )) {
                    if (General.STATS_MAP.containsKey ( attribute )) {
                        DBItem item = itemsRegistry.get ( command.getArgs ( 2 ).toUpperCase ( ) );
                        if (item == null) {
                            command.getSender ( ).sendMessage ( CC.translate ( "&8[&6&lDBNetwork&8] &cError: &7The item is null or not properly registered. Please check and try again." ) );
                            return;
                        }
                        Attribute attributeStat = item.getAttribute ( );
                        attributeStat.setStatBonus ( attribute );
                        attributeStat.setBonus ( (float) bonus );
                        command.getSender ( ).sendMessage ( CC.translate ( "&8[&6&lDBNetwork&8] &aThe attribute &6" + attribute + " &ahas been set to &6" + (bonus * 100) + "%. &afor the item in your hand." ) );
                        item.setAttribute ( attributeStat );
                        itemsRegistry.put ( item.getIdentifier ( ), item );
                    } else {
                        DBItem item = itemsRegistry.get ( command.getArgs ( 2 ).toUpperCase ( ) );
                        Attribute attributeStat = item.getAttribute ( );
                        attributeStat.setStatBonus ( attribute );
                        attributeStat.setBonus ( (float) bonus );
                        command.getSender ( ).sendMessage ( CC.translate ( "&8[&6&lDBNetwork&8] &aThe attribute &6" + attribute + " &ahas been set to &6" + (bonus * 100) + "%. &afor the item in your hand." ) );
                        item.setAttribute ( attributeStat );
                        itemsRegistry.put ( item.getIdentifier ( ), item );
                    }


                } else if (arg1.equalsIgnoreCase ( "cost" )) {
                    if (General.STATS_MAP.containsKey ( attribute )) {
                        DBItem item = itemsRegistry.get ( command.getArgs ( 2 ).toUpperCase ( ) );
                        if (item == null) {
                            command.getSender ( ).sendMessage ( CC.translate ( "&8[&6&lDBNetwork&8] &cError: &7The item is null or not properly registered. Please check and try again." ) );
                            return;
                        }
                        Attribute attributeStat = item.getAttribute ( );
                        attributeStat.setStatCost ( attribute );
                        attributeStat.setCost ( (float) bonus );
                        command.getSender ( ).sendMessage ( CC.translate ( "&8[&6&lDBNetwork&8] &aThe attribute &6" + attribute + " &ahas been set to &6" + (bonus * 100) + "% &afor the item in your hand." ) );
                        item.setAttribute ( attributeStat );
                        itemsRegistry.put ( item.getIdentifier ( ), item );
                    } else {
                        DBItem item = itemsRegistry.get ( command.getArgs ( 2 ).toUpperCase ( ) );
                        Attribute attributeStat = item.getAttribute ( );
                        attributeStat.setStatCost ( attribute );
                        attributeStat.setCost ( (float) bonus );
                        command.getSender ( ).sendMessage ( CC.translate ( "&8[&6&lDBNetwork&8] &aThe attribute &6" + attribute + " &ahas been set to &6" + (bonus * 100) + "%. &afor the item in your hand." ) );
                        item.setAttribute ( attributeStat );
                        itemsRegistry.put ( item.getIdentifier ( ), item );
                    }

                }
                break;
            default:
                player.performCommand ( "dbitem help" );
                break;
        }
    }

    public static ItemStack wrapItemStack ( DBItem item ) {
        ItemStack itemStack = new ItemStack ( item.getId ( ), 1 );
        ItemMeta meta = itemStack.getItemMeta ( );
        meta.setDisplayName ( CC.translate ( item.getName ( ) ) );
        meta.setLore ( item.getLore ( ) );
        itemStack.setItemMeta ( meta );
        return itemStack;
    }

    public static DBItem wrapDBItem ( ItemStack item ) {
        if (item == null || item.getItemMeta ( ) == null || item.getItemMeta ( ).getLore ( ) == null) {
            return null;
        }

        AtomicReference<DBItem> dbItem = new AtomicReference<> ( new DBItem ( ) );
        dbItem.get ( ).setId ( item.getType ( ).getId ( ) );
        dbItem.get ( ).setLore ( item.getItemMeta ( ).getLore ( ) );
        dbItem.get ( ).setName ( CC.translate ( item.getItemMeta ( ).getDisplayName ( ) ) );

        itemsRegistry.forEach ( ( k, v ) -> {
            if (v.equals ( dbItem.get ( ) )) {
                dbItem.set ( v );
            }
        } );

        return dbItem.get ( );
    }


    public static boolean isDBItem ( ItemStack itemStack ) {
        DBItem dbItem = new DBItem ( );
        AtomicBoolean isDBItem = new AtomicBoolean ( false );
        if (itemStack == null) return false;
        try {
            if (itemStack.getItemMeta ( ).getLore ( ) != null) {
                dbItem.setId ( itemStack.getType ( ).getId ( ) );
                dbItem.setLore ( itemStack.getItemMeta ( ).getLore ( ) );
                dbItem.setName ( CC.translate ( itemStack.getItemMeta ( ).getDisplayName ( ) ) );
            }
            itemsRegistry.forEach ( ( k, v ) -> {
                if (v.equals ( dbItem )) {
                    isDBItem.set ( true );
                }
            } );
        } catch (NullPointerException exception) {
            return false;
        }

        return isDBItem.get ( );
    }
}
