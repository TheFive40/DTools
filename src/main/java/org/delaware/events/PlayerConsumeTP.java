package org.delaware.events;

import io.github.facuu16.gohan.dbc.event.DbcPlayerTpGainEvent;
import noppes.npcs.api.entity.IDBCPlayer;
import noppes.npcs.scripted.NpcAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.delaware.Main;
import org.delaware.tools.BoosterHandler.BoosterDataHandler;
import org.delaware.tools.BoosterHandler.BoosterManager;
import org.delaware.tools.Boosters.VIPBooster;
import org.delaware.tools.CC;
import org.delaware.tools.General;

import java.text.DecimalFormat;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerConsumeTP implements Listener {
    private BoosterDataHandler boosterHandler = new BoosterDataHandler ( );

    @EventHandler
    public void onPlayerGainTPEvent ( DbcPlayerTpGainEvent event ) {
        DecimalFormat formatter = new DecimalFormat ( "#,###" );
        int tp = (int) event.amount ( );
        // TODO: Add your custom logic here, e.g., save TP to a database or log it.
        Player player = Main.instance.getServer ( ).getPlayer ( event.dbcPlayer ( ).player ( ).getUniqueId ( ) );
        VIPBooster booster = boosterHandler.findBooster ( player.getUniqueId ( ) );
        ConcurrentHashMap<UUID, Double> pMultiplier = BoosterDataHandler.getBoosterPMultiplier ( );
        if (booster == null && !pMultiplier.containsKey ( player.getUniqueId ( ) )) return;
        IDBCPlayer idbcPlayer = NpcAPI.Instance ( ).getPlayer ( player.getName ( ) ).getDBCPlayer ( );
        if (booster != null) {
            if (!booster.isActive ( )) return;
            int bonus = (int) (tp * booster.getMultiplier ( ));
            idbcPlayer.setTP ( event.dbcPlayer ( ).tps ( ) + bonus );
            idbcPlayer.sendMessage ( CC.translate ( "&6+" + formatter.format ( bonus ) + " (Booster Pasivo)" ) );
        }
        if (pMultiplier.containsKey ( player.getUniqueId ( ) )) {
            double multiplier = pMultiplier.get ( player.getUniqueId ( ) );
            idbcPlayer.setTP ( event.dbcPlayer ( ).tps ( ) + (int) (tp * multiplier) );
            idbcPlayer.sendMessage ( CC.translate ( "&6+" + formatter.format ( tp * multiplier ) + " (Booster Pasivo)" ) );
        }
        player.playSound ( player.getLocation ( ), "random.orb", 1.0f, 1.0f );

    }

}
