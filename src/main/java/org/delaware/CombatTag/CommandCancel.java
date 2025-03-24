package org.delaware.CombatTag;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.delaware.tools.CC;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class CommandCancel implements Listener {
    private final Set<String> allowedCommands = new HashSet<>(Arrays.asList("msg", "r", "login"));
    @EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        if(TagListener.isTagged(player)) {
            String command = event.getMessage().substring(1).split(" ")[0].toLowerCase();
            if(!allowedCommands.contains(command)) {
                player.sendMessage(CC.translate("&cNo puedes usar este comando en PvP!"));
                event.setCancelled(true);
            }
        }
    }
}
