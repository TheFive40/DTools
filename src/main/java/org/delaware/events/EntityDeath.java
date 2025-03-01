package org.delaware.events;

import noppes.npcs.api.entity.IDBCPlayer;
import noppes.npcs.scripted.NpcAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.delaware.tools.BoosterHandler.BoosterDataHandler;
import org.delaware.tools.Boosters.VIPBooster;
import org.delaware.tools.CC;

import java.text.DecimalFormat;

import static org.delaware.Main.playersTPS;

public class EntityDeath implements Listener {
    private BoosterDataHandler boosterHandler = new BoosterDataHandler ( );

    public void onDeathEntity ( EntityDeathEvent event ) {
        DecimalFormat formatter = new DecimalFormat ( "#,###" );
        // death event logic here
        Player player = event.getEntity ( ).getKiller ( );
        IDBCPlayer idbcPlayer = NpcAPI.Instance ( ).getPlayer ( player.getName ( ) ).getDBCPlayer ( );
        int currentTP = idbcPlayer.getTP ( );
        int oldTP = playersTPS.get ( player.getName ( ) );
        int gainTP = currentTP - oldTP;
        // apply gainTP to player's stats or whatever you want here
        if (!boosterHandler.contains ( player.getUniqueId ( ) )) return;
        VIPBooster booster = boosterHandler.findBooster ( player.getUniqueId ( ) );
        if (!booster.isActive ( ) || booster.isCooldownActive ( )) return;
        int bonus = (int) (gainTP * booster.getMultiplier ( ));
        idbcPlayer.setTP ( currentTP + bonus );
        idbcPlayer.sendMessage ( CC.translate ( "&6+" + formatter.format ( bonus ) + " (Booster Pasivo)" ) );
        player.playSound ( player.getLocation ( ), "random.orb", 1.0f, 1.0f );

    }
}
