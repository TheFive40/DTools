package org.delaware.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryClick implements Listener {
    @EventHandler
    public void onClickEvent ( InventoryClickEvent event ) {

    }

    public static boolean hasFullArmorSet ( Player player ) {
        ItemStack chestplate = player.getInventory ( ).getChestplate ( );
        ItemStack leggings = player.getInventory ( ).getLeggings ( );
        ItemStack boots = player.getInventory ( ).getBoots ( );
        return leggings != null && boots != null && chestplate != null;
    }
}
