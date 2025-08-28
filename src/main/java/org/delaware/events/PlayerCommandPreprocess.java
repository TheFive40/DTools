package org.delaware.events;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.delaware.tools.CC;
import org.delaware.tools.General;
import org.delaware.tools.Kits.KitMenu;

import static org.delaware.commands.CommandFrooze.playersFrooze;

public class PlayerCommandPreprocess implements Listener {

    @EventHandler
    public void onCommandPreProcess ( PlayerCommandPreprocessEvent event ) {
        Player player = event.getPlayer ( );
        if (playersFrooze.contains ( player )) {
            event.setCancelled ( true );
            player.sendMessage ( CC.translate ( "&cYou are frozen and cannot use commands!" ) );
        }
        if (event.getMessage ( ).startsWith ( "/kitadmin" )) return;
        if (event.getMessage ( ).startsWith ( "/kit" )) {
            event.setCancelled ( true );
            String category = General.getRank ( player );
            KitMenu.getInventory ( player, category ).open ( player );
        }
    }

}
