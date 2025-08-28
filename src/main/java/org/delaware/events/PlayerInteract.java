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
import org.delaware.commands.GlobalBoosterCommand;
import org.delaware.tools.BoosterHandler.BoosterDataHandler;
import org.delaware.tools.Boosters.PBooster;
import org.delaware.tools.Boosters.VIPBooster;
import org.delaware.tools.CC;
import org.delaware.tools.model.entities.TP;

import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static org.delaware.commands.CommandTransform.entities;
import static org.delaware.commands.GlobalBoosterCommand.boosterActive;
import static org.delaware.commands.GlobalBoosterCommand.boosterMultiplier;
import static org.delaware.commands.TPSCommand.findTP;
import static org.delaware.events.PersonalBoosterEvent.boosterPlayer;

public class PlayerInteract implements Listener {
    private final Map<Player, Long> lastUseTime = new HashMap<> ( );
    private BoosterDataHandler boosterHandler = new BoosterDataHandler();

    @EventHandler
    public void onPlayerInteract ( PlayerInteractEvent event ) {
        Player player = event.getPlayer ( );
        if (player.getItemInHand ( ) == null) return;
        if (player.getItemInHand ( ).getType ( ) == Material.TNT) {
            player.setItemInHand ( new ItemStack ( Material.DIRT ) );
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
            int reward2 = tp.getValue ( ) * amount;
            int reward3 = tp.getValue ( ) * amount;

            if (boosterActive) {
                reward = (int) (reward * boosterMultiplier);
            }

            IDBCPlayer idbcPlayer = NpcAPI.Instance ( ).getPlayer ( player.getName ( ) ).getDBCPlayer ( );
            idbcPlayer.setTP ( reward + idbcPlayer.getTP ( ) );
            DecimalFormat tpsFormat = new DecimalFormat ( "#,###.##" );



            //Vip Booster System
            VIPBooster boosterVIP = boosterHandler.findBooster(player.getUniqueId ());
            if (boosterVIP != null && boosterVIP.isActive()) {
                int bonus = (int) (reward3 * boosterVIP.getMultiplier());
                idbcPlayer.setTP(idbcPlayer.getTP() + bonus);
                player.sendMessage(CC.translate("&e+ &6" + tpsFormat.format(bonus) + " (Booster VIP)"));
            }
            // Booster personalizado (PBooster)
            ConcurrentHashMap<UUID, PBooster> pMultiplier = BoosterDataHandler.getBoosterPMultiplier();
            if (pMultiplier.containsKey(player.getUniqueId())) {
                double multiplier = pMultiplier.get(player.getUniqueId()).getMultiplier();
                int bonus = (int) (reward3 * multiplier);
                idbcPlayer.setTP(idbcPlayer.getTP() + bonus);
                player.sendMessage(CC.translate("&e+ &6" + tpsFormat.format(bonus) + " (Booster)"));
            }

            //Personal Booster System
            if (boosterPlayer.containsKey ( player.getUniqueId ( ) )) {
                double multiplier = boosterPlayer.get ( player.getUniqueId ( ) );
                int boosterGain = (int) (reward2 * multiplier);
                idbcPlayer.setTP ( boosterGain + idbcPlayer.getTP ( ) );
                idbcPlayer.sendMessage ( CC.translate ( "&e+ &6" + tpsFormat.format ( boosterGain ) + " (Booster Pasivo)" ) );
            }

            player.sendMessage ( CC.translate ( "&e+&6 " + tpsFormat.format ( ((long) tp.getValue ( ) * amount) ) ) );
            int booster = reward - tp.getValue ( ) * amount;
            if (boosterActive)
                player.sendMessage ( CC.translate ( "&e+&6 " + tpsFormat.format ( Math.round ( booster ) ) )
                        + CC.translate ( " &a(Booster " + GlobalBoosterCommand.getBoosterMultiplierPercent ( ) + "&a)" ) );
            player.playSound ( player.getLocation ( ), "random.orb", 1.0f, 1.0f );
            player.setItemInHand ( null );
        }
    }

    public static void attributeBonus ( @NonNull String stat, @NonNull String value, IDBCPlayer player ) {
        JRMCoreH.setString ( value, (EntityPlayer) player, "jrmcAttrBonus" + stat );
    }


}
