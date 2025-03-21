package org.delaware.tools.RegionTools.Runnable;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.delaware.tools.CC;
import org.delaware.tools.RegionTools.PlayerAccessManager;
import org.delaware.tools.RegionTools.RegionHandler;

@Getter
public class RegionCheckRunnable {
    public static BukkitRunnable regionCheckRunnable = new BukkitRunnable() {
        @Override
        public void run() {
            for(Player player : Bukkit.getServer().getOnlinePlayers()) {
                if(!PlayerAccessManager.allPlayers.containsKey(player.getUniqueId().toString())) continue;
                RegionHandler regionHandler = new RegionHandler();
                if(regionHandler.getPlayerRegions(player) == null) continue;
                for(ProtectedRegion region : regionHandler.getPlayerRegions(player)) {
                    String regionName = region.getId();
                    PlayerAccessManager manager = PlayerAccessManager.allPlayers.get(player.getUniqueId().toString());
                    if(!manager.hasRecord(regionName)) {
                        continue; //no record for this region, continuing...
                    }
                    if(!manager.canEnterRegion(regionName)) {
                        if(manager.regionCooldownCheck(regionName)) {
                            if(!manager.hasActiveRefreshTime(regionName)) { //Time has expired, let player know
                                manager.setRefreshTime(regionName);
                                player.sendMessage(CC.translate("&cHa expirado tu tiempo de acceso para esta zona!, vuelve en " + manager.getFormattedCooldown(regionName)));
                                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "warp Spawn " + player.getName());
                                continue;
                            }
                            if(manager.isRefreshable(regionName)) { //Checking if player should be able to re-enter again
                                manager.refresh(regionName);
                                continue;
                            }
                            player.sendMessage(CC.translate("&cAún tienes que esperar " + manager.getRemainingCooldown(regionName) + "&c para entrar a esta zona!"));
                        }
                        player.sendMessage(CC.translate("&cNo tienes acceso a esta zona!"));
                        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "warp Spawn " + player.getName());
                        //Kick player, he doesn't have access
                    }else manager.addTimeElapsed(regionName, 3);
                }
            }
        }
    };
}
