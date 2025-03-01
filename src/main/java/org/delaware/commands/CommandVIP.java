package org.delaware.commands;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.delaware.tools.BoosterHandler.BoosterDataHandler;
import org.delaware.tools.Boosters.VIPBooster;
import org.delaware.tools.CC;
import org.delaware.tools.General;
import org.delaware.tools.commands.BaseCommand;
import org.delaware.tools.commands.Command;
import org.delaware.tools.commands.CommandArgs;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;

import static org.delaware.tools.General.formatDuration;

public class CommandVIP extends BaseCommand {
    private BoosterDataHandler boosterHandler = new BoosterDataHandler ( );

    @Override
    @Command(aliases = "vip", name = "vip", permission = "dtools.vip")
    public void onCommand ( CommandArgs command ) throws IOException {
        SmartInventory trainingInventory = SmartInventory.builder ( ).title ( CC.translate ( "&6&k::&c&lVIP&6&k::" ) )
                .type ( InventoryType.CHEST )
                .provider ( new InventoryProvider ( ) {
                    @Override
                    public void init ( Player player, InventoryContents inventoryContents ) {
                        inventoryContents.fillBorders ( ClickableItem.empty ( new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.YELLOW.getData ( ) ) ) );
                        ItemStack effects = new ItemStack ( 4943 );
                        ItemMeta effectsItemMeta = effects.getItemMeta ( );
                        effectsItemMeta.setDisplayName ( CC.translate ( "&cEfectos" ) );
                        effects.setItemMeta ( effectsItemMeta );
                        inventoryContents.set ( 1, 2, ClickableItem.of ( effects, e -> {
                            SmartInventory dbcInventory = SmartInventory.builder ( ).size ( 6, 9 )
                                    .type ( InventoryType.CHEST )
                                    .title ( CC.translate ( "&c&lSistema de Efectos" ) )
                                    .provider ( new InventoryProvider ( ) {
                                        @Override
                                        public void init ( Player player, InventoryContents inventoryContents ) {
                                            inventoryContents.fillBorders ( ClickableItem.empty ( new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.YELLOW.getData ( ) ) ) );
                                            ItemStack Divino = new ItemStack ( 5016 );
                                            ItemMeta divinoItemMeta = Divino.getItemMeta ( );
                                            divinoItemMeta.setDisplayName ( CC.translate ( "&d&lDivino" ) );
                                            Divino.setItemMeta ( divinoItemMeta );
                                            inventoryContents.set ( 1, 3, ClickableItem.of ( Divino, e -> {
                                                Bukkit.getServer ( ).dispatchCommand ( Bukkit.getConsoleSender ( ), "jrmcse set Divine -1 " + player.getName ( ) );
                                            } ) );
                                            ItemStack legendario = new ItemStack ( 4962 );
                                            ItemMeta legendarioItemMeta = legendario.getItemMeta ( );
                                            legendarioItemMeta.setDisplayName ( CC.translate ( "&a&lLegendario" ) );
                                            legendario.setItemMeta ( legendarioItemMeta );
                                            inventoryContents.set ( 1, 4, ClickableItem.of ( legendario, e -> {
                                                Bukkit.getServer ( ).dispatchCommand ( Bukkit.getConsoleSender ( ), "jrmcse set Legendary -1 " + player.getName ( ) );

                                            } ) );
                                            ItemStack Majin = new ItemStack ( 4990 );
                                            ItemMeta majinItemMeta = Majin.getItemMeta ( );
                                            majinItemMeta.setDisplayName ( CC.translate ( "&5&lMajin" ) );
                                            Majin.setItemMeta ( majinItemMeta );
                                            inventoryContents.set ( 1, 5, ClickableItem.of ( Majin, e -> {
                                                Bukkit.getServer ( ).dispatchCommand ( Bukkit.getConsoleSender ( ), "jrmcse set Majin -1 " + player.getName ( ) );
                                            } ) );
                                            ItemStack KO = new ItemStack ( 4959 );
                                            ItemMeta koItemMeta = KO.getItemMeta ( );
                                            koItemMeta.setDisplayName ( CC.translate ( "&6&lK.O" ) );
                                            KO.setItemMeta ( koItemMeta );
                                            inventoryContents.set ( 2, 3, ClickableItem.of ( KO, e -> {
                                                Bukkit.getServer ( ).dispatchCommand ( Bukkit.getConsoleSender ( ), "jrmcse set KO 0 " + player.getName ( ) );
                                            } ) );
                                            ItemStack NoFuse = new ItemStack ( 5019 );
                                            ItemMeta noFuseItemMeta = NoFuse.getItemMeta ( );
                                            noFuseItemMeta.setDisplayName ( CC.translate ( "&4&lNo Fuse" ) );
                                            NoFuse.setItemMeta ( noFuseItemMeta );
                                            inventoryContents.set ( 2, 4, ClickableItem.of ( NoFuse, e -> {
                                                Bukkit.getServer ( ).dispatchCommand ( Bukkit.getConsoleSender ( ), "jrmcse set NoFuse 0 " + player.getName ( ) );
                                            } ) );
                                            ItemStack fatigue = new ItemStack ( 4941 );
                                            ItemMeta fatigueItemMeta = fatigue.getItemMeta ( );
                                            fatigueItemMeta.setDisplayName ( CC.translate ( "&e&lFatigue" ) );
                                            fatigue.setItemMeta ( fatigueItemMeta );
                                            inventoryContents.set ( 2, 5, ClickableItem.of ( fatigue, e -> {
                                                Bukkit.getServer ( ).dispatchCommand ( Bukkit.getConsoleSender ( ), "jrmcse set Fatigue 0 " + player.getName ( ) );
                                            } ) );


                                            ItemStack Evil = new ItemStack ( 4996 );
                                            ItemMeta evilItemMeta = Evil.getItemMeta ( );
                                            evilItemMeta.setDisplayName ( CC.translate ( "&cEvil" ) );
                                            Evil.setItemMeta ( evilItemMeta );
                                            inventoryContents.set ( 4, 2, ClickableItem.of ( Evil, e -> {
                                                Bukkit.getServer ( ).dispatchCommand ( Bukkit.getConsoleSender ( ), "jrmca set Alignment 0 " + player.getName ( ) );

                                            } ) );
                                            ItemStack Neutral = new ItemStack ( 4956 );
                                            ItemMeta neutralItemMeta = Neutral.getItemMeta ( );
                                            neutralItemMeta.setDisplayName ( CC.translate ( "&9Neutral" ) );
                                            Neutral.setItemMeta ( neutralItemMeta );
                                            inventoryContents.set ( 4, 4, ClickableItem.of ( Neutral, e -> {
                                                Bukkit.getServer ( ).dispatchCommand ( Bukkit.getConsoleSender ( ), "jrmca set Alignment 50 " + player.getName ( ) );

                                            } ) );
                                            ItemStack Good = new ItemStack ( 4963 );
                                            ItemMeta goodItemMeta = Good.getItemMeta ( );
                                            goodItemMeta.setDisplayName ( CC.translate ( "&bGood" ) );
                                            Good.setItemMeta ( goodItemMeta );
                                            inventoryContents.set ( 4, 6, ClickableItem.of ( Good, e -> {
                                                Bukkit.getServer ( ).dispatchCommand ( Bukkit.getConsoleSender ( ), "jrmca set Alignment 100 " + player.getName ( ) );
                                            } ) );

                                            ItemStack BoosterGlobal = new ItemStack ( 4979 );
                                            ItemMeta BoosterGlobalMeta = BoosterGlobal.getItemMeta ( );
                                            BoosterGlobalMeta.setDisplayName ( CC.translate ( "&dBooster Global" ) );
                                            BoosterGlobal.setItemMeta ( goodItemMeta );
                                            inventoryContents.set ( 1, 7, ClickableItem.of ( BoosterGlobal, e -> {
                                                Bukkit.getServer ( ).dispatchCommand ( Bukkit.getConsoleSender ( ), "jrmca set Alignment 100 " + player.getName ( ) );
                                            } ) );
                                            ItemStack BoosterPersonal = new ItemStack ( 4957 );
                                            ItemMeta BoosterPersonalMeta = BoosterPersonal.getItemMeta ( );
                                            BoosterPersonalMeta.setDisplayName ( CC.translate ( "&cBooster Personal" ) );
                                            BoosterPersonal.setItemMeta ( goodItemMeta );
                                            inventoryContents.set ( 1, 1, ClickableItem.of ( BoosterPersonal, e -> {
                                                if (boosterHandler.contains ( player.getUniqueId ( ) )) {
                                                    VIPBooster vipBooster = boosterHandler.findBooster ( player.getUniqueId ( ) );
                                                    if (!BoosterDataHandler.getBoostMultiplier ( ).containsKey ( General.getGroup ( player.getUniqueId ( ) ) ))
                                                        return;
                                                    String rank = General.getGroup ( player.getUniqueId ( ) );
                                                    double multiplier = BoosterDataHandler.getBoostMultiplier ( ).get ( rank.toLowerCase ( ) );
                                                    if (vipBooster == null) return;
                                                    vipBooster.setMultiplier ( multiplier );
                                                    vipBooster.setPlayerRank ( rank );
                                                    if (!vipBooster.isCooldownActive ( )) {
                                                        player.sendMessage ( CC.translate ( "&aYou have activated your personal weekly booster!" ) );
                                                        vipBooster.setActivationTime ( LocalDateTime.now ( ) );
                                                        vipBooster.setActive ( true );
                                                        player.playSound ( player.getLocation ( ), Sound.valueOf ( "LEVEL_UP" ), 1.0f, 1.0f );
                                                        return;
                                                    }
                                                    Duration remainingTime = vipBooster.getTimeRemaining ( );
                                                    player.sendMessage ( CC.translate ( "&cYou cannot activate your booster yet!" ) );
                                                    vipBooster.setActive ( false );
                                                    player.sendMessage ( CC.translate ( "&7Time remaining: &e" + formatDuration ( remainingTime ) ) );
                                                    return;
                                                }
                                                if (!BoosterDataHandler.getBoostMultiplier ( ).containsKey ( General.getGroup ( player.getUniqueId ( ) ) ))
                                                    return;
                                                String rank = General.getGroup ( player.getUniqueId ( ) );
                                                double multiplier = BoosterDataHandler.getBoostMultiplier ( ).get ( rank.toLowerCase ( ) );
                                                VIPBooster vipBooster = new VIPBooster ( player.getUniqueId ( ), rank, multiplier );
                                                vipBooster.setActive ( true );
                                                BoosterDataHandler.getBoosterData ( ).add ( vipBooster );
                                                player.sendMessage ( CC.translate ( "&aYou have activated your personal weekly booster!" ) );
                                                player.playSound ( player.getLocation ( ), Sound.valueOf ( "LEVEL_UP" ), 1.0f, 1.0f );
                                            } ) );
                                            ItemStack NoPain = new ItemStack ( 4967 );
                                            ItemMeta NoPainMeta = NoPain.getItemMeta ( );
                                            NoPainMeta.setDisplayName ( CC.translate ( "&1No Pain" ) );
                                            NoPain.setItemMeta ( goodItemMeta );
                                            inventoryContents.set ( 3, 1, ClickableItem.of ( NoPain, e -> {
                                                Bukkit.getServer ( ).dispatchCommand ( Bukkit.getConsoleSender ( ), "jrmca set Alignment 100 " + player.getName ( ) );
                                            } ) );
                                            ItemStack NoStrain = new ItemStack ( 4985 );
                                            ItemMeta NoStrainMeta = NoStrain.getItemMeta ( );
                                            NoStrainMeta.setDisplayName ( CC.translate ( "&2No Strain" ) );
                                            NoStrain.setItemMeta ( goodItemMeta );
                                            inventoryContents.set ( 3, 7, ClickableItem.of ( NoStrain, e -> {
                                                Bukkit.getServer ( ).dispatchCommand ( Bukkit.getConsoleSender ( ), "jrmca set Alignment 100 " + player.getName ( ) );
                                            } ) );
                                            inventoryContents.set ( 1, 2, ClickableItem.empty ( new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.GREEN.getData ( ) ) ) );
                                            inventoryContents.set ( 2, 2, ClickableItem.empty ( new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.GREEN.getData ( ) ) ) );
                                            inventoryContents.set ( 3, 2, ClickableItem.empty ( new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.GREEN.getData ( ) ) ) );
                                            inventoryContents.set ( 3, 3, ClickableItem.empty ( new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.GREEN.getData ( ) ) ) );
                                            inventoryContents.set ( 3, 4, ClickableItem.empty ( new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.GREEN.getData ( ) ) ) );
                                            inventoryContents.set ( 3, 5, ClickableItem.empty ( new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.GREEN.getData ( ) ) ) );
                                            inventoryContents.set ( 1, 6, ClickableItem.empty ( new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.GREEN.getData ( ) ) ) );
                                            inventoryContents.set ( 2, 6, ClickableItem.empty ( new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.GREEN.getData ( ) ) ) );
                                            inventoryContents.set ( 3, 6, ClickableItem.empty ( new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.GREEN.getData ( ) ) ) );
                                            inventoryContents.set ( 2, 1, ClickableItem.empty ( new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.WHITE.getData ( ) ) ) );
                                            inventoryContents.set ( 4, 1, ClickableItem.empty ( new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.WHITE.getData ( ) ) ) );
                                            inventoryContents.set ( 4, 3, ClickableItem.empty ( new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.WHITE.getData ( ) ) ) );
                                            inventoryContents.set ( 4, 5, ClickableItem.empty ( new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.WHITE.getData ( ) ) ) );
                                            inventoryContents.set ( 4, 7, ClickableItem.empty ( new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.WHITE.getData ( ) ) ) );
                                            inventoryContents.set ( 2, 7, ClickableItem.empty ( new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.WHITE.getData ( ) ) ) );

                                        }

                                        @Override
                                        public void update ( Player player, InventoryContents inventoryContents ) {
                                            inventoryContents.fillBorders ( ClickableItem.empty ( new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.YELLOW.getData ( ) ) ) );
                                        }
                                    } )
                                    .build ( );
                            dbcInventory.open ( player );
                        } ) );
                        ItemStack trainings = new ItemStack ( 4951 );
                        ItemMeta trainingsItemMeta = trainings.getItemMeta ( );
                        trainingsItemMeta.setDisplayName ( CC.translate ( "&eTVIP" ) );
                        trainings.setItemMeta ( trainingsItemMeta );
                        inventoryContents.set ( 1, 4, ClickableItem.of ( trainings, e -> {
                            player.performCommand ( "warp tvip" );
                        } ) );
                        ItemStack zonaVIP = new ItemStack ( 6084 );
                        zonaVIP = (zonaVIP.getItemMeta () == null) ? new ItemStack ( 5944 ) : zonaVIP;
                        ItemMeta zonaVIPItemMeta = zonaVIP.getItemMeta ( );
                        zonaVIPItemMeta.setDisplayName ( CC.translate ( "&aZona VIP" ) );
                        zonaVIP.setItemMeta ( zonaVIPItemMeta );
                        inventoryContents.set ( 1, 6, ClickableItem.of ( zonaVIP, e -> {
                            player.performCommand ( "warp zvip" );
                        } ) );
                    }

                    @Override
                    public void update ( Player player, InventoryContents inventoryContents ) {
                        inventoryContents.fillBorders ( ClickableItem.empty ( new ItemStack ( Material.STAINED_GLASS_PANE, 1, DyeColor.YELLOW.getData ( ) ) ) );
                    }
                } ).id ( "trainingsVIP" )
                .size ( 3, 9 ).id ( "FUTURE_VIP" ).build ( );
        trainingInventory.open ( command.getPlayer ( ) );
    }
}
