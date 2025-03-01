package org.delaware.commands;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.delaware.tools.BoosterHandler.BoosterDataHandler;
import org.delaware.tools.commands.BaseCommand;
import org.delaware.tools.commands.Command;
import org.delaware.tools.commands.CommandArgs;

import java.io.IOException;

public class CommandBosterMultiplier extends BaseCommand {
    private static final String VERSION = "1.0.0";
    private BoosterDataHandler boosterHandler = new BoosterDataHandler ( );

    @Command(name = "pvboost", aliases = "pvboost", permission = "dtools.pvboost")
    @Override
    public void onCommand ( CommandArgs command ) throws IOException {
        Player player = command.getPlayer ( );
        if (command.length ( ) < 2) {
            player.sendMessage ( ChatColor.DARK_GRAY + "-----------------------------------------" );
            player.sendMessage ( ChatColor.GOLD + "✦ PVBooster Plugin " + ChatColor.YELLOW + "v" + VERSION );
            player.sendMessage ( ChatColor.GRAY + "Usage: " + ChatColor.AQUA + "/pvbooster <rank> <multiplier>" );
            player.sendMessage ( ChatColor.GRAY + "Example: " + ChatColor.GREEN + "/pvbooster VIP 2.0" );
            player.sendMessage ( ChatColor.GRAY + "Author: " + ChatColor.LIGHT_PURPLE + "TheFive" );
            player.sendMessage ( ChatColor.RED + "Invalid usage! Please follow the correct format." );
            player.sendMessage ( ChatColor.DARK_GRAY + "-----------------------------------------" );
            return;
        }
        double multiplier = Double.parseDouble ( command.getArgs ( 1 ) );
        String rank = command.getArgs ( 0 ).toLowerCase ( );
        player.sendMessage ( ChatColor.GREEN + "Booster activated for rank " + ChatColor.GOLD + rank.toUpperCase ( ) +
                ChatColor.GREEN + " with multiplier " + ChatColor.AQUA + multiplier + "x!" );
        boosterHandler.addBoosterData ( rank, multiplier );
    }
}
