package org.delaware.events;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.delaware.Main;
import org.delaware.tools.CC;
import org.delaware.tools.RegionUtils;

import static org.delaware.tools.RegionUtils.findRegionInAnyWorld;

public class DropItemRegion implements Listener {
    @EventHandler
    public void onPlayerDrop ( PlayerDropItemEvent event ) {
        ItemStack item = event.getItemDrop ( ).getItemStack ( );
        RegionUtils regionUtils = new RegionUtils ( );
        Location location = event.getItemDrop ( ).getLocation ( );
        if (item.getTypeId ( ) == 4429 && RegionUtils.isLocationInRegion ( location, "portalBlack" )
                && regionUtils.hasCooldown ( event.getPlayer ( ) )) {
            String remainingTime = regionUtils.getRemainingCooldown ( event.getPlayer ( ) );
            String msg = CC.translate ( "&8[&c⚠&8] &7Debes esperar &b" + remainingTime + " &7antes de usar este portal nuevamente." );
            event.getPlayer ( ).sendMessage ( CC.translate ( msg ) );
            event.getPlayer ().playSound ( event.getPlayer ().getLocation (),
                    Sound.CAT_MEOW,1.0f,1.0f);

        } else if (item.getTypeId ( ) == 4429 && RegionUtils.isLocationInRegion ( location, "portalBlack" )) {
            regionUtils.grantAccess(event.getPlayer(), "trainingoculto", findRegionInAnyWorld("trainingoculto"));
            event.getPlayer().sendMessage(CC.translate (  "§aAhora tienes acceso al training oculto durante 60 minutos."));
            event.getPlayer ().setItemInHand ( new ItemStack ( Material.STICK ) );
            event.setCancelled ( true );
            new BukkitRunnable() {
                int countdown = 10;
                Player player = event.getPlayer();

                @Override
                public void run() {
                    if (countdown > 0) {
                        String color = "&f";
                        Sound sound = Sound.NOTE_PLING;

                        switch (countdown) {
                            case 10: case 9: case 8:
                                color = "&a"; sound = Sound.NOTE_PLING; // Verde
                                break;
                            case 7: case 6: case 5:
                                color = "&e"; sound = Sound.NOTE_STICKS; // Amarillo
                                break;
                            case 4: case 3: case 2:
                                color = "&c"; sound = Sound.NOTE_BASS; // Rojo
                                break;
                            case 1:
                                color = "&4"; sound = Sound.NOTE_BASS_DRUM; // Rojo oscuro
                                break;
                        }

                        player.sendMessage(CC.translate(color + "Teletransportando en " + countdown + " segundos..."));
                        player.playSound(player.getLocation(), sound, 1.0f, 1.0f);
                        countdown--;

                    } else {
                        player.sendMessage(CC.translate("&6¡Teleportado! &7Disfruta tu entrenamiento."));
                        player.performCommand("warp trainingoculto");
                        player.playSound(player.getLocation(), Sound.ENDERDRAGON_GROWL, 1.0f, 1.0f);
                        cancel();
                    }
                }
            }.runTaskTimer(Main.instance, 0L, 20L);
        }


    }
}
