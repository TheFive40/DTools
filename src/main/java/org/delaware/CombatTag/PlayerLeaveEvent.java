package org.delaware.CombatTag;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLeaveEvent implements Listener {
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if(TagListener.isTagged(player)) {
            TagListener.taggedPlayers.remove(player.getUniqueId());
        }
    }
}
