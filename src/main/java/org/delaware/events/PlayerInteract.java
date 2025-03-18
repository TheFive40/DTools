package org.delaware.events;

import JinRyuu.JRMCore.JRMCoreH;
import lombok.NonNull;
import net.minecraft.entity.player.EntityPlayer;
import noppes.npcs.api.entity.ICustomNpc;
import noppes.npcs.api.entity.IDBCPlayer;
import noppes.npcs.scripted.NpcAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.delaware.tools.model.entities.TP;

import java.util.*;
import static org.delaware.commands.CommandTransform.entities;
import static org.delaware.commands.TPSCommand.findTP;

public class PlayerInteract implements Listener {
    private final Map<Player, Long> lastUseTime = new HashMap<> ( );

    @EventHandler
    public void onPlayerInteract ( PlayerInteractEvent event ) {
        Player player = event.getPlayer ( );
        if(player.getItemInHand () == null) return;
        if(player.getItemInHand ().getType () == Material.TNT){
            player.setItemInHand ( new ItemStack(Material.DIRT) );
        }
        if (entities.containsKey ( player.getName ( ) )) {
            IDBCPlayer idbcPlayer = NpcAPI.Instance ( ).getPlayer ( player.getName ( ) ).getDBCPlayer ( );
            ICustomNpc<?> npc = (ICustomNpc<?>) entities.get ( idbcPlayer.getName ( ) );
            if (player.getItemInHand ( ).getType ( ).getId ( ) == 4533 && event.getAction ( ) == Action.RIGHT_CLICK_AIR ||
                    event.getAction ( ) == Action.RIGHT_CLICK_BLOCK) {
                long currentTime = System.currentTimeMillis ( );
                if (lastUseTime.containsKey ( player )) {
                    long lastTime = lastUseTime.get ( player );
                    if (currentTime - lastTime < 5000) {
                        return;
                    }
                }
                lastUseTime.put ( player, currentTime );
                npc.setHealth ( (float) npc.getMaxHealth ( ) );
                npc.setHeldItem ( idbcPlayer.getHeldItem ( ) );
                return;
            }
            npc.setHeldItem ( idbcPlayer.getHeldItem ( ) );
            npc.setHealth ( idbcPlayer.getBody ( ) );
            return;
        }
        //TPS SYSTEM
        if (event.getAction ( ) == Action.RIGHT_CLICK_AIR && player.getItemInHand ( ) != null) {
            ItemStack itemInHand = player.getItemInHand ( );
            TP tp = findTP ( itemInHand );
            if (tp == null) return;
            int amount = itemInHand.getAmount ( );
            int reward = tp.getValue ( ) * amount;
            Bukkit.getServer ( ).dispatchCommand ( Bukkit.getConsoleSender ( ), "dbcadmin givetps " + player.getName ( ) + " "
                    + reward );


            player.playSound ( player.getLocation ( ), "random.orb", 1.0f, 1.0f );
            player.setItemInHand ( null );
        }
        //TPS SYSTEM
    }

    public static void attributeBonus ( @NonNull String stat, @NonNull String value, IDBCPlayer player ) {
        JRMCoreH.setString ( value, (EntityPlayer) player, "jrmcAttrBonus" + stat );
    }


}
