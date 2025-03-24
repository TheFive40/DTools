package org.delaware.CombatTag;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.delaware.Main;
import org.delaware.tools.CC;
import org.delaware.tools.RegionTools.RegionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TagListener implements Listener {
    public static Map<UUID, Long> taggedPlayers = new HashMap<>();
    public static BukkitRunnable clearExpireTagsTask = new BukkitRunnable() {
        @Override
        public void run() {
            clearExpiredTags();
        }
    };

    private final int tagDurationInSeconds = 25;

    public static boolean isTagged(Player player) {
        return taggedPlayers.containsKey(player.getUniqueId());
    }
    public void tagPlayer(Player player) {
        if(!taggedPlayers.containsKey(player.getUniqueId())) {
            player.sendMessage(CC.translate("&cAhora estás en combate por " + tagDurationInSeconds + " segundos."));
            Bukkit.getScheduler().runTaskAsynchronously(Main.instance, () -> {
                ForceFieldManager forceFieldManager = new ForceFieldManager(player);
                RegionHandler regionHandler = new RegionHandler();
                forceFieldManager.applyForceField(regionHandler.getPvPOffRegions(player.getWorld()), Material.STAINED_GLASS.getId(), (byte) 14);
            });
        }
        taggedPlayers.put(player.getUniqueId(), (System.currentTimeMillis() + (tagDurationInSeconds * 1000L)));
    }
    private static void clearExpiredTags() {
        long currentTime = System.currentTimeMillis();
        taggedPlayers.entrySet().removeIf(entry -> {
            boolean isExpired = entry.getValue() <= currentTime;
            if(isExpired) {
                if(Bukkit.getPlayer(entry.getKey()) != null) {
                    Player player = Bukkit.getPlayer(entry.getKey());
                    if(player.isOnline()) player.sendMessage(CC.translate("&aYa no estás en combate."));
                    Bukkit.getScheduler().runTaskAsynchronously(Main.instance, () -> {
                        ForceFieldManager forceFieldManager = new ForceFieldManager(player);
                        forceFieldManager.removeForceField();
                    });
                }
            }
            return isExpired;
        });
    }
    @EventHandler
    public void onPvP(EntityDamageByEntityEvent event) {
        if(event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
            Player attacker = (Player) event.getDamager();
            Player victim = (Player) event.getEntity();
            tagPlayer(attacker);
            tagPlayer(victim);
        }
    }
}
