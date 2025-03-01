package org.delaware.events;

import io.github.facuu16.gohan.dbc.model.DbcPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import noppes.npcs.api.entity.IDBCPlayer;
import noppes.npcs.scripted.NpcAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.delaware.tools.BoosterHandler.BoosterDataHandler;
import org.delaware.tools.Boosters.VIPBooster;
import org.delaware.tools.CC;

import java.text.DecimalFormat;

import static org.delaware.Main.playersTPS;

public class EntityDeath implements Listener {
    private BoosterDataHandler boosterHandler = new BoosterDataHandler ( );
    @EventHandler
    public void onDeathEntity ( EntityDeathEvent event ) {
        DecimalFormat formatter = new DecimalFormat ( "#,###" );
        // death event logic here
        Player player = event.getEntity ( ).getKiller ( );
        DbcPlayer<EntityPlayerMP> dbc = new DbcPlayer<> ( player.getUniqueId () );
        int currentTP = dbc.tps ();
        int oldTP = playersTPS.get ( player.getName ( ) );
        int gainTP = currentTP - oldTP;
        // apply gainTP to player's stats or whatever you want here
        VIPBooster booster = boosterHandler.findBooster ( player.getUniqueId ( ) );
        if(booster == null) return;
        if (!booster.isActive ( ) ) return;
        int bonus = (int) (gainTP * booster.getMultiplier ( ));
        if (bonus <= 1) return;
        dbc.addTp ( bonus );
        player.sendMessage ( CC.translate ( "&3+" + formatter.format ( gainTP )   ) );
        player.sendMessage ( CC.translate ( "&6+" + formatter.format ( bonus ) + " (Booster Pasivo)" ) );
        player.playSound ( player.getLocation ( ), "random.orb", 1.0f, 1.0f );

    }
}
