package org.delaware.tools.BoosterHandler;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.delaware.Main;
import org.delaware.tools.Boosters.PBooster;
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
                Collection<PBooster> pBoosters = BoosterDataHandler.getBoosterPMultiplier ().values ();
                for (VIPBooster booster : boosters) {
                    if (booster.isActive ( ) && booster.getActivationTime ( ) != null) {
                        Duration elapsedTime = Duration.between ( booster.getActivationTime ( ), LocalDateTime.now ( ) );
                        if (elapsedTime.toMinutes ( ) >= 60) {
                            booster.setActive ( false );
                            if (Bukkit.getPlayer ( booster.getPlayerUUID ( ) ) != null) {
                                Bukkit.getPlayer ( booster.getPlayerUUID ( ) ).sendMessage ( CC.translate ( "&cYour personal booster has expired!" ) );
                            }
                        }
                    }
                }
                pBoosters.forEach ( e-> {
                    if (e.getActivationTime ( ) != null){
                        Duration elapsedTime = Duration.between ( e.getActivationTime ( ), LocalDateTime.now ( ) );
                        if(elapsedTime.toDays ()>=31 || elapsedTime.toDays () < 0 || elapsedTime.toMinutes () >= 44640 ){
                            BoosterDataHandler.removeBoosterPlayer ( e.getPlayerId () );
                        }
                    }
                });
                BoosterDataHandler.saveData ();
            }
        }.runTaskTimerAsynchronously ( Main.instance, 0L, 5 * 20L );
    }


    private static boolean isVip(String rank) {
        return rank != null && VIP_RANKS.contains(rank.toLowerCase());
    }
}