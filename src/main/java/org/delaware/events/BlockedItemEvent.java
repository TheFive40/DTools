package org.delaware.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.delaware.Main;
import org.delaware.tools.CC;

import static org.delaware.commands.BlockItemCommand.blockedIds;
import static org.delaware.commands.BlockItemCommand.checkInventory;


public class BlockedItemEvent implements Listener {
    @EventHandler
    public void onPickup( PlayerPickupItemEvent event) {
        Player player = event.getPlayer();
        int id = event.getItem().getItemStack().getTypeId();
        if (!player.hasPermission("dtools.blockeditem") && blockedIds.contains(id)) {
            event.setCancelled(true);
            event.getItem().remove();
            player.sendMessage( CC.translate("&8[&6DBZ&8] &cNo puedes recoger este item bloqueado."));
        }
    }

    @EventHandler
    public void onInventoryClick( InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;
        Player player = (Player) event.getWhoClicked();
        ItemStack item = event.getCurrentItem();
        if (item != null && blockedIds.contains(item.getTypeId()) && !player.hasPermission("dtools.blockeditem")) {
            event.setCancelled(true);
            player.getInventory().remove(item);
            player.sendMessage(CC.translate("&8[&6DBZ&8] &cNo puedes interactuar con un item bloqueado. Ha sido eliminado."));
        }
    }

    public static void startAsyncCheck() {
        new BukkitRunnable () {
            @Override
            public void run() {
                for (Player player : Main.instance.getServer ().getOnlinePlayers()) {
                    checkInventory(player);
                }
            }
        }.runTaskTimerAsynchronously( Main.instance, 0L, 100L);
    }
}
