package org.delaware.tools.RegionTools.Runnable;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.delaware.tools.CC;
import org.delaware.tools.RegionTools.PlayerAccessManager;
import org.delaware.tools.RegionTools.RegionHandler;

import java.time.Instant;

@Getter
public class RegionCheckRunnable {
    public static BukkitRunnable regionCheckRunnable = new BukkitRunnable() {
        @Override
        public void run() {
            for(Player player : Bukkit.getServer().getOnlinePlayers()) {
                if(!PlayerAccessManager.allPlayers.containsKey(player.getUniqueId().toString())) continue;
                RegionHandler regionHandler = new RegionHandler();
                ProtectedRegion region = regionHandler.getPlayerRegion(player);
                if(region != null) {
                    String regionName = region.getId();
                    PlayerAccessManager manager = PlayerAccessManager.allPlayers.get(player.getUniqueId().toString());
                    if(!manager.hasRecord(regionName)) {
                        continue; //no record for this region,continuing...
                    }
                    if(!manager.canEnterRegion(regionName)) {
                        if(manager.regionCooldownCheck(regionName)) {
                            if(!manager.hasActiveRefreshTime(regionName)) { //Time has expired, let player know
                                manager.setRefreshTime(regionName);
                                player.sendMessage(CC.translate("Time's up! you have to wait " + manager.getFormattedCooldown(regionName)));
                                continue;
                            }
                            if(manager.isRefreshable(regionName)) { //Checking if player should be able to re-enter again
                                manager.refresh(regionName);
                                continue;
                            }
                            player.sendMessage(CC.translate("You still have to wait " + manager.getRemainingCooldown(regionName)));
                        }
                        player.sendMessage(CC.translate("&cNo tienes acceso a esta zona!"));
                        //Kick player, he doesn't have access
                    }else manager.addTimeElapsed(regionName, 3);
                }
            }
        }
    };
}
