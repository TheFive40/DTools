package org.delaware.events;

import noppes.npcs.api.entity.IDBCPlayer;
import noppes.npcs.scripted.NpcAPI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import static org.delaware.commands.DBItemsCommand.isDBItem;
import static org.delaware.events.PlayerInteract.armorCompleted;

public class PlayerJoinEvent implements Listener {
    @EventHandler
    public void onPlayerJoin ( org.bukkit.event.player.PlayerJoinEvent event ) {
        ItemStack[] items = event.getPlayer ( ).getInventory ( ).getArmorContents ( );
        int armorCount = 0;
        for (ItemStack item : items) {
            if (item != null && isDBItem ( item )) {
                armorCount++;
            }
        }
        IDBCPlayer idbcPlayer = NpcAPI.Instance ( ).getPlayer ( event.getPlayer ( ).getName ( ) )
                .getDBCPlayer ( );
        armorCompleted.put ( event.getPlayer ( ).getName ( ), armorCount );
    }
}
