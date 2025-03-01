package org.delaware.events;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import java.util.ArrayList;


public class PlayerHeld implements Listener {
    public static ArrayList<String> players = new ArrayList<> ( );

    @EventHandler
    public void onPlayerHeldItem ( PlayerItemHeldEvent event ) {

    }


}
