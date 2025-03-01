package org.delaware.tools.BoosterHandler;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.delaware.Main;
import org.delaware.tools.Boosters.VIPBooster;
import org.delaware.tools.CC;
import org.delaware.tools.General;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class BoosterManager {
    private static final Set<String> VIP_RANKS = new HashSet<> ( Arrays.asList(
            "hakaishin", "kaio", "kami", "kaioshin", "ayudante", "quester",
            "moderador", "admin", "owner", "manager", "helper-manager",
            "quest-manager", "vip", "vip+", "elite"
    ));

    public static void startBoosterCheckTask () {
        new BukkitRunnable ( ) {
            @Override
            public void run () {
                List<VIPBooster> boosters = BoosterDataHandler.getBoosterData ( );
                for (VIPBooster booster : boosters) {
                    if (booster.isActive ( ) && booster.getActivationTime ( ) != null) {
                        Duration elapsedTime = Duration.between ( booster.getActivationTime ( ), LocalDateTime.now ( ) );
                        if (elapsedTime.toHours ( ) >= 1) {
                            booster.setActive ( false );
                            if (Bukkit.getPlayer ( booster.getPlayerUUID ( ) ) != null) {
                                Bukkit.getPlayer ( booster.getPlayerUUID ( ) ).sendMessage ( CC.translate ( "&cYour personal booster has expired!" ) );
                            }
                        }
                    }
                }
            }
        }.runTaskTimerAsynchronously ( Main.instance, 0L, 5 * 20L );
    }
    public static void startVipCheckTask() {
        new BukkitRunnable() {
            @Override
            public void run() {
                Iterator<VIPBooster> iterator = BoosterDataHandler.getBoosterData().iterator();

                while (iterator.hasNext()) {
                    BoosterDataHandler.saveData ();
                    VIPBooster booster = iterator.next();
                    UUID playerUUID = booster.getPlayerUUID();
                    String rank = General.getGroup(playerUUID);
                    if (!isVip(rank)) {
                        iterator.remove();
                        System.out.println("[PVBooster] Removed booster for " + playerUUID + " (No longer VIP)");
                    }
                }
            }
        }.runTaskTimer(Main.instance, 0L, 1728000L); // Ejecuta cada 24h (1728000 ticks)
    }

    private static boolean isVip(String rank) {
        return rank != null && VIP_RANKS.contains(rank.toLowerCase());
    }
}