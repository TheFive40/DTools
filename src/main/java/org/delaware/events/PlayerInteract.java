package org.delaware.events;

import JinRyuu.JRMCore.JRMCEnAttacks;
import JinRyuu.JRMCore.JRMCoreH;
import JinRyuu.JRMCore.JRMCoreHJBRA;
import kamkeel.addon.DBCAddon;
import kamkeel.npcdbc.api.npc.IDBCStats;
import kamkeel.npcdbc.client.model.part.DBCLeftArms;
import kamkeel.npcdbc.constants.DBCAttribute;
import kamkeel.npcdbc.constants.DBCClass;
import kamkeel.npcdbc.constants.DBCDamageSource;
import kamkeel.npcdbc.constants.DBCForm;
import kamkeel.npcdbc.data.dbcdata.DBCData;
import kamkeel.npcdbc.data.dbcdata.DBCDataBonus;
import kamkeel.npcdbc.data.dbcdata.DBCDataStats;
import kamkeel.npcdbc.data.dbcdata.DBCDataUniversal;
import kamkeel.npcdbc.mixins.late.impl.dbc.MixinJRMCoreH;
import kamkeel.npcdbc.scripted.DBCAPI;
import kamkeel.npcdbc.scripted.DBCEventHooks;
import kamkeel.npcdbc.scripted.DBCPlayerEvent;
import kamkeel.npcdbc.util.DBCUtils;
import lombok.NonNull;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.minecraft.entity.player.EntityPlayer;
import noppes.npcs.api.entity.ICustomNpc;
import noppes.npcs.api.entity.IDBCPlayer;
import noppes.npcs.scripted.NpcAPI;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.delaware.Main;
import org.delaware.tools.CC;
import org.delaware.tools.General;
import org.delaware.tools.Stat;
import org.delaware.tools.model.entities.Attribute;
import org.delaware.tools.model.entities.DBItem;
import org.delaware.tools.model.entities.TP;

import java.text.DecimalFormat;
import java.util.*;

import static org.delaware.Main.playersTPS;
import static org.delaware.commands.CommandTransform.entities;
import static org.delaware.commands.DBItemsCommand.wrapDBItem;
import static org.delaware.commands.TPSCommand.findTP;
import static org.delaware.events.PlayerHeld.players;

public class PlayerInteract implements Listener {
    private final Map<Player, Long> lastUseTime = new HashMap<> ( );
    public static HashMap<String, Integer> armorCompleted = new HashMap<> ( );
    public static HashMap<String, Float> playerBonus = new HashMap<> ( );
    public static HashMap<String, Float> playerCost = new HashMap<> ( );
    public static final HashMap<UUID, BukkitRunnable> tasks = new HashMap<> ( );

    @EventHandler
    public void onPlayerInteract ( PlayerInteractEvent event ) {
        Player player = event.getPlayer ( );
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

    public static void attributeBonus( @NonNull String stat, @NonNull String value, IDBCPlayer player) {
        JRMCoreH.setString(value, (EntityPlayer) player, "jrmcAttrBonus" + stat);
    }

    public static void setPlayerData ( Player player, DBItem dbItem, boolean deathNPC ) {
        IDBCPlayer dbcPlayer = NpcAPI.Instance ( ).getPlayer ( player.getName ( ) ).getDBCPlayer ( );
        float bonus = dbItem.getAttribute ( ).getBonus ( );
        float cost = dbItem.getAttribute ( ).getCost ( );
        Attribute attribute = dbItem.getAttribute ( );
        String statBonus = General.STATS_MAP.get ( attribute.getStatBonus ( ) );
        String statFromBonus = General.STATS_MAP.get ( attribute.getStatFromBonus ( ) );
        String statCost = General.STATS_MAP.get ( attribute.getStatCost ( ) );
        if (attribute.getStatBonus ( ).equalsIgnoreCase ( "TPS" )) {
            int currentTP = dbcPlayer.getTP ( );
            if (deathNPC && playersTPS.containsKey ( player.getName ( ) )) {
                int oldTP = playersTPS.get ( player.getName ( ) );
                int tp = currentTP - oldTP;
                Bukkit.getServer ( ).dispatchCommand ( Bukkit.getConsoleSender ( ), "dbcadmin givetps " + player.getName ( ) + " "
                        + attribute.getBonus ( ) * tp );
                player.playSound ( player.getLocation ( ), "random.orb", 1.0f, 1.0f );
                return;
            }
            int currentStatCost = dbcPlayer.getNbt ( ).getCompound ( "PlayerPersisted" ).getInteger ( statCost );
            cost = cost * currentStatCost;
            playerCost.put ( player.getName ( ), cost );
            dbcPlayer.getNbt ( ).getCompound ( "PlayerPersisted" ).setInteger ( statCost, (int) (currentStatCost - cost) );
            return;
        } else if (attribute.getStatCost ( ).equalsIgnoreCase ( "TPS" )) {
            int currentTP = dbcPlayer.getTP ( );
            if (deathNPC && playersTPS.containsKey ( player.getName ( ) )) {
                int oldTP = playersTPS.get ( player.getName ( ) );
                int tp = currentTP - oldTP;
                DecimalFormat formatter = new DecimalFormat ( "#,###" );
                String formattedReward = formatter.format ( attribute.getCost ( ) * tp );
                dbcPlayer.setTP ( (int) (currentTP - (attribute.getBonus ( ) * tp)) );
                dbcPlayer.sendMessage ( CC.translate ( "&c[&4-&c] " + formattedReward ) );
                return;
            }
            int currentStatBonus = dbcPlayer.getNbt ( ).getCompound ( "PlayerPersisted" ).getInteger ( statBonus );
            bonus = bonus * currentStatBonus;
            playerBonus.put ( player.getName ( ), bonus );
            dbcPlayer.getNbt ( ).getCompound ( "PlayerPersisted" ).setInteger ( statBonus, (int) (bonus + currentStatBonus) );
            player.sendMessage ( CC.translate ( "&8[&6Warning&8] &aYou are now holding: &6" + dbItem.getName ( ) + "&a. Effects applied!" ) );
            return;
        }
        if (!attribute.getStatCost ( ).equalsIgnoreCase ( "TPS" ) && !attribute.getStatBonus ( ).equalsIgnoreCase ( "TPS" ) && !deathNPC) {
            int currentStatBonus = dbcPlayer.getNbt ( ).getCompound ( "PlayerPersisted" ).getInteger ( (statFromBonus == null) ? statBonus : statFromBonus );
            int currentStatCost = dbcPlayer.getNbt ( ).getCompound ( "PlayerPersisted" ).getInteger ( statCost );
            int currentStatToBonus = dbcPlayer.getNbt ( ).getCompound ( "PlayerPersisted" ).getInteger ( statBonus );
            bonus = bonus * currentStatBonus;
            cost = cost * currentStatCost;
            playerBonus.put ( player.getName ( ), bonus );
            playerCost.put ( player.getName ( ), cost );
            dbcPlayer.getNbt ( ).getCompound ( "PlayerPersisted" ).setInteger ( statBonus, (int) (bonus + currentStatToBonus) );
            dbcPlayer.getNbt ( ).getCompound ( "PlayerPersisted" ).setInteger ( statCost, (int) (currentStatCost - cost) );
            players.add ( player.getName ( ) );
            player.sendMessage ( CC.translate ( "&8[&6Warning&8] &aYou are now holding: &6" + dbItem.getName ( ) + "&a. Effects applied!" ) );
        }

    }
}
