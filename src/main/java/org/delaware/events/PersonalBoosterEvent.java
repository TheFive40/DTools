package org.delaware.events;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.delaware.Main;
import org.delaware.tools.CC;
import org.delaware.tools.CustomItems.CustomItems;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PersonalBoosterEvent implements Listener {
    public static int[] boosters = {6181, 6198, 6222, 6193};
    public static ConcurrentHashMap<UUID, Double> boosterPlayer = new ConcurrentHashMap<> ( );
    public static ConcurrentHashMap<UUID, LocalDateTime> timeBoosterPlayer = new ConcurrentHashMap<> ( );

    @EventHandler
    public void onInteract ( PlayerInteractEvent event ) {

        if (event.getAction ( ) != Action.RIGHT_CLICK_AIR) return;
        Player player = event.getPlayer ( );
        ItemStack item = player.getItemInHand ( );
        if (item == null) return;
        try{
            if(CustomItems.getLinkedCustomItem ( item ) == null) return;
            if (Arrays.stream ( boosters ).anyMatch ( value -> value == item.getTypeId ( ) )) {
                if (boosterPlayer.containsKey ( player.getUniqueId ( ) )) {
                    player.sendMessage ( CC.translate ( "&eYa tienes un booster personal &aActivo." ) );
                    return;
                }
                timeBoosterPlayer.put ( player.getUniqueId ( ), LocalDateTime.now ( ) );
                player.playSound ( player.getLocation (),Sound.WITHER_SPAWN,1.0F,1.0F );
                switch (item.getTypeId ( )) {
                    case 6181:
                        player.sendMessage ( CC.translate ( "&eBooster Personal del &a(10%) &eActivado por &c15 Minutos." ) );
                        boosterPlayer.put ( player.getUniqueId ( ), 0.1 );
                        break;
                    case 6198:
                        player.sendMessage ( CC.translate ( "&eBooster Personal del &a(25%) &eActivado por &c15 Minutos." ) );
                        boosterPlayer.put ( player.getUniqueId ( ), 0.25 );
                        break;
                    case 6222:
                        player.sendMessage ( CC.translate ( "&eBooster Personal del &a(50%) &eActivado por &c10 Minutos." ) );
                        boosterPlayer.put ( player.getUniqueId ( ), 0.5 );
                        break;
                    case 6193:
                        player.sendMessage ( CC.translate ( "&eBooster Personal del &a(100%) &eActivado por &c10 Minutos." ) );
                        boosterPlayer.put ( player.getUniqueId ( ), 1.0 );
                        break;
                }
                if(item.getAmount () == 1){
                    player.setItemInHand ( null );
                    return;
                }
                item.setAmount ( item.getAmount ( ) - 1 );
                player.setItemInHand ( item );
            }
        }catch(Exception ignored){}

    }

    public static void boosterTask () {
        BukkitRunnable runnable = new BukkitRunnable ( ) {
            @Override
            public void run () {
                for (Player player : Main.instance.getServer ( ).getOnlinePlayers ( )) {
                    if (boosterPlayer.containsKey ( player.getUniqueId ( ) )) {
                        double multiplier = boosterPlayer.get ( player.getUniqueId ( ) );
                        LocalDateTime startTime = timeBoosterPlayer.get ( player.getUniqueId ( ) );
                        LocalDateTime endTime = LocalDateTime.now ( );
                        if ((multiplier == 0.1 || multiplier == 0.25) && Duration.between ( startTime, endTime )
                                .toMinutes ( ) >= 15) {
                            boosterPlayer.remove ( player.getUniqueId () );
                            timeBoosterPlayer.remove ( player.getUniqueId () );
                            player.sendMessage ( CC.translate ( "&cBooster Personal &4expirado." ) );
                            player.playSound ( player.getLocation (), Sound.ANVIL_BREAK,1.0F,1.0F);
                        }else if((multiplier == 0.5 || multiplier == 1.0 || multiplier == 2.0) && Duration.between (startTime,  endTime ).toMinutes () >= 10){
                            boosterPlayer.remove ( player.getUniqueId () );
                            timeBoosterPlayer.remove ( player.getUniqueId () );
                            player.sendMessage ( CC.translate ( "&cBooster Personal &4expirado." ) );
                            player.playSound ( player.getLocation (), Sound.ANVIL_BREAK,1.0F,1.0F);
                        }else if(multiplier == 5.0 && Duration.between (startTime,  endTime ).toMinutes () >= 5){
                            boosterPlayer.remove ( player.getUniqueId () );
                            timeBoosterPlayer.remove ( player.getUniqueId () );
                            player.sendMessage ( CC.translate ( "&cBooster Personal &4expirado." ) );
                            player.playSound ( player.getLocation (), Sound.ANVIL_BREAK,1.0F,1.0F);
                        }else if(multiplier == 0.4 && Duration.between (startTime,  endTime ).toMinutes () >= 15){
                            boosterPlayer.remove ( player.getUniqueId () );
                            timeBoosterPlayer.remove ( player.getUniqueId () );
                            player.sendMessage ( CC.translate ( "&cBooster Personal &4expirado." ) );
                            player.playSound ( player.getLocation (), Sound.ANVIL_BREAK,1.0F,1.0F);
                        }
                    }
                }
            }
        };
        runnable.runTaskTimer ( Main.instance, 0L, 20L );
    }
}
