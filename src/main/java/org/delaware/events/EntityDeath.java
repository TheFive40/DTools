package org.delaware.events;

import noppes.npcs.api.entity.ICustomNpc;
import noppes.npcs.api.entity.IDBCPlayer;
import noppes.npcs.api.entity.IEntity;
import noppes.npcs.scripted.NpcAPI;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.delaware.Main;
import org.delaware.tools.model.entities.DBItem;

import java.util.ArrayList;
import java.util.Arrays;

import static org.delaware.Main.*;
import static org.delaware.commands.CommandTransform.detransform;
import static org.delaware.commands.CommandTransform.entitiesBukkit;
import static org.delaware.commands.DBItemsCommand.isDBItem;
import static org.delaware.commands.DBItemsCommand.wrapDBItem;
import static org.delaware.events.InventoryClick.hasFullArmorSet;
import static org.delaware.events.PlayerInteract.setPlayerData;

public class EntityDeath implements Listener {

    @EventHandler
    public void onEntityDeathEvent ( EntityDeathEvent event ) {
        if (entitiesBukkit.containsValue ( event.getEntity ( ) )) {
            entitiesBukkit.forEach ( ( k, v ) -> {
                if (v == event.getEntity ( )) {
                    Player player = Main.instance.getServer ( ).getPlayer ( k );
                    IDBCPlayer idbcPlayer = NpcAPI.Instance ( ).getPlayer ( k ).getDBCPlayer ( );
                    idbcPlayer.setBody ( 0 );
                    detransform ( player );
                }
            } );
        }
        if (event.getEntity ( ).getKiller ( ) != null) {
            Player player = event.getEntity ( ).getKiller ( );
            ItemStack hand = player.getItemInHand ( );
            IEntity<?> entity = NpcAPI.Instance ( ).getIWorld ( event.getEntity ( ).getWorld ( ).getEnvironment ( ).getId ( ) )
                    .getEntityByID ( event.getEntity ( ).getEntityId ( ) );
            if (entity instanceof ICustomNpc<?> && hasFullArmorSet ( player ) && armorsPlayers.containsKey ( player.getName ( ) )) {
                if (isDBItem ( player.getInventory ( ).getChestplate ( ) ) && isDBItem ( player.getInventory ( ).getLeggings ( ) ) &&
                        isDBItem ( player.getInventory ( ).getBoots ( ) )) {
                    ArrayList<DBItem> armaduras = new ArrayList<> ( Arrays.asList ( wrapDBItem ( player.getInventory ( ).getChestplate ( ) ), wrapDBItem ( player.getInventory ( ).getChestplate ( ) ),
                            wrapDBItem ( player.getInventory ( ).getBoots ( ) )) );
                    if (hasSetArmor ( armaduras )){
                        setPlayerData ( player, wrapDBItem ( player.getInventory ( ).getChestplate ( ) ), true );
                    }
                }
            } else if (entity instanceof ICustomNpc<?> && isDBItem ( hand )) {
                setPlayerData ( player, wrapDBItem ( hand ), true );
            }
        }

    }
}
