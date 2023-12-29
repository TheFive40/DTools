package org.delaware.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.delaware.tools.CC;

public class PlayerJoin implements Listener {
    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent playerJoinEvent) {
        playerJoinEvent.setJoinMessage(CC.translate("&8[&a+&8] &a" + playerJoinEvent.getPlayer().getName()));
    }
}
