package org.delaware.commands;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.delaware.tools.BoosterHandler.BoosterDataHandler;
import org.delaware.tools.Boosters.PBooster;
import org.delaware.tools.commands.BaseCommand;
import org.delaware.tools.commands.Command;
import org.delaware.tools.commands.CommandArgs;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

public class CommandBosterMultiplier extends BaseCommand {
    private static final String VERSION = "1.0.0";
    private BoosterDataHandler boosterHandler = new BoosterDataHandler ( );

    @Command(name = "pvboost", aliases = {"pvboost","pvbooster"}, permission = "dtools.pvboost")
    @Override
    public void onCommand ( CommandArgs command ) throws IOException {
        Player player = command.getPlayer ( );
        if (command.length ( ) < 3) {
            player.sendMessage(ChatColor.DARK_GRAY + "-----------------------------------------");
            player.sendMessage(ChatColor.GOLD + "✦ PVBooster Plugin " + ChatColor.YELLOW + "v" + VERSION);
            player.sendMessage(ChatColor.GRAY + "Usage: " + ChatColor.AQUA + "/pvbooster rank <rank> <multiplier>");
            player.sendMessage(ChatColor.GRAY + "Example: " + ChatColor.GREEN + "/pvbooster VIP 2.0");
            player.sendMessage(ChatColor.GRAY + "Usage: " + ChatColor.AQUA + "/pvbooster player <player> <multiplier>");
            player.sendMessage(ChatColor.GRAY + "Example: " + ChatColor.GREEN + "/pvbooster player Steve 1.5");
            player.sendMessage(ChatColor.GRAY + "Author: " + ChatColor.LIGHT_PURPLE + "TheFive");
            player.sendMessage(ChatColor.RED + "Invalid usage! Please follow the correct format.");
            player.sendMessage(ChatColor.DARK_GRAY + "-----------------------------------------");
            return;
        }
        String subAlias = command.getArgs ( 0 );
        String subject = command.getArgs ( 1 ).toLowerCase ( );
        double multiplier = Double.parseDouble ( command.getArgs ( 2 ) );
        if (subAlias.equalsIgnoreCase ( "rank" )) {
            boosterHandler.addRankBooster ( subject, multiplier );
        }else{
            Player player1 = command.getSender ().getServer ().getPlayer ( subject );
            if (player1 == null) {
                player.sendMessage(ChatColor.RED + "Error: " + ChatColor.GRAY + "El jugador especificado no existe o no está conectado.");
                return;
            }
            PBooster pBooster = new PBooster ();
            pBooster.setMultiplier ( multiplier );
            pBooster.setActivationTime ( LocalDateTime.now ( ) );
            pBooster.setPlayerId ( player1.getUniqueId () );
            boosterHandler.addPlayerBooster ( player1.getUniqueId (),pBooster );
        }
        player.sendMessage ( ChatColor.GREEN + "Booster activated for rank " + ChatColor.GOLD + subject.toUpperCase ( ) +
                ChatColor.GREEN + " with multiplier " + ChatColor.AQUA + multiplier + "x!" );
    }
}
