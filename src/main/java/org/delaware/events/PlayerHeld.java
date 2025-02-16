package org.delaware.events;

import noppes.npcs.api.entity.IDBCPlayer;
import noppes.npcs.scripted.NpcAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.delaware.tools.CC;
import org.delaware.tools.General;
import org.delaware.tools.model.entities.Attribute;
import org.delaware.tools.model.entities.DBItem;

import java.util.ArrayList;

import static org.delaware.commands.DBItemsCommand.isDBItem;
import static org.delaware.commands.DBItemsCommand.wrapDBItem;
import static org.delaware.events.PlayerInteract.playerBonus;
import static org.delaware.events.PlayerInteract.playerCost;

public class PlayerHeld implements Listener {
    public static ArrayList<String> players = new ArrayList<> ( );

    @EventHandler
    public void onPlayerHeldItem ( PlayerItemHeldEvent event ) {
        Player player = event.getPlayer ( );
        ItemStack newItem = player.getInventory ( ).getItem ( event.getNewSlot ( ) );
        ItemStack previousItem = player.getInventory ( ).getItem ( event.getPreviousSlot ( ) );
        DBItem dbItem = (newItem != null) ? wrapDBItem ( newItem ) : null;
        DBItem dbItemPrevious = (previousItem != null) ? wrapDBItem ( previousItem ) : null;
        if (previousItem != null && dbItemPrevious != null) {
            if ("SWORD".equalsIgnoreCase ( dbItemPrevious.getType ( ) )) {
                restorePlayerData ( player, dbItemPrevious );
            }
        }
        if (dbItem != null && dbItem.getType ( ) != null) {
            if (isDBItem ( newItem ) && dbItem.getType ( ).
                    equalsIgnoreCase ( "SWORD" )) {
                PlayerInteract.setPlayerData ( player, dbItem, false );
            }
        }
    }

    public static void restorePlayerData ( Player player, DBItem dbItem, IDBCPlayer dbcPlayer ) {
        if (dbcPlayer == null) {
            Bukkit.getLogger ( ).warning ( "IDBCPlayer es null para el jugador: " + player.getName ( ) );
            return;
        }
        if (dbItem == null) {
            Bukkit.getLogger ( ).warning ( "DBItem es null para el jugador: " + player.getName ( ) );
            return;
        }
        double cost = 0.0;
        double bonus = 0.0;
        Attribute attribute = dbItem.getAttribute ( );
        String statBonus = General.STATS_MAP.get ( attribute.getStatBonus ( ) );
        String statCost = General.STATS_MAP.get ( attribute.getStatCost ( ) );
        if (attribute.getStatBonus ( ).equalsIgnoreCase ( "TPS" )) {
            int currentStatCost = dbcPlayer.getNbt ( ).getCompound ( "PlayerPersisted" ).getInteger ( statCost );
            cost = playerCost.get ( player.getName ( ) );
            currentStatCost = (int) (currentStatCost + cost);
            dbcPlayer.getNbt ( ).getCompound ( "PlayerPersisted" ).setInteger ( statCost, currentStatCost );
            player.playSound ( player.getLocation ( ), "random.orb", 1.0f, 1.0f );
            player.sendMessage ( CC.translate ( "&8[&6Warning&8] &eYou removed: &6" + dbItem.getName ( ) + "&e. Effects are no longer active. " ) );
            players.remove ( player.getName ( ) );
            return;
        } else if (attribute.getStatCost ( ).equalsIgnoreCase ( "TPS" )) {
            int currentStatBonus = dbcPlayer.getNbt ( ).getCompound ( "PlayerPersisted" ).getInteger ( statBonus );
            bonus = playerBonus.get ( player.getName ( ) );
            currentStatBonus = (int) (currentStatBonus + bonus);
            dbcPlayer.getNbt ( ).getCompound ( "PlayerPersisted" ).setInteger ( statBonus, currentStatBonus );
            player.sendMessage ( CC.translate ( "&8[&6Warning&8] &eYou removed: &6" + dbItem.getName ( ) + "&e. Effects are no longer active. " ) );
            players.remove ( player.getName ( ) );
            return;
        }
        int currentStatBonus = dbcPlayer.getNbt ( ).getCompound ( "PlayerPersisted" ).getInteger ( statBonus );
        bonus = playerBonus.get ( player.getName ( ) );

        currentStatBonus = (int) (currentStatBonus - bonus);
        if (currentStatBonus % 2 != 0) currentStatBonus = currentStatBonus + 1;
        int currentStatCost = dbcPlayer.getNbt ( ).getCompound ( "PlayerPersisted" ).getInteger ( statCost );
        cost = playerCost.get ( player.getName ( ) );
        currentStatCost = (int) (currentStatCost + cost);
        dbcPlayer.getNbt ( ).getCompound ( "PlayerPersisted" ).setInteger ( statBonus, currentStatBonus );
        dbcPlayer.getNbt ( ).getCompound ( "PlayerPersisted" ).setInteger ( statCost, currentStatCost );
        players.remove ( player.getName ( ) );
        if (dbItem.getType ( ) != null && (dbItem.getType ( ).equalsIgnoreCase ( "SWORD" ) || dbItem.getType ( ).equalsIgnoreCase ( "ITEM" )))
            player.sendMessage ( CC.translate ( "&8[&6Warning&8] &eYou stopped holding: &6" + dbItem.getName ( ) + "&e. Effects removed!" ) );
        else
            player.sendMessage ( CC.translate ( "&8[&6Warning&8] &eYou removed: &6" + dbItem.getName ( ) + "&e. Effects are no longer active. " ) );

    }

    static void restorePlayerData ( Player player, DBItem dbItem ) {
        IDBCPlayer dbcPlayer = NpcAPI.Instance ( ).getPlayer ( player.getName ( ) ).getDBCPlayer ( );
        restorePlayerData ( player, dbItem, dbcPlayer );
    }
}
