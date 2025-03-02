package org.delaware.commands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.delaware.Main;
import org.delaware.tools.CC;
import org.delaware.tools.commands.BaseCommand;
import org.delaware.tools.commands.Command;
import org.delaware.tools.commands.CommandArgs;

import java.io.IOException;

public class CommandNickAccept extends BaseCommand {
    @Command(name = "nickAccept", description = "Accept a nickname", aliases = {"na", "nickAccept"},
            permission = "dtools.nickaccept")
    @Override
    public void onCommand ( CommandArgs command ) throws IOException {
        if (!command.isPlayer ( )) {
            return;
        }
        if (command.getArgs ( ).length < 1) {
            command.getPlayer ( ).sendMessage ( CC.translate ( "&8[&6✦&8] &cUsage: &e/nickAccept <player>" ) );
            command.getPlayer ( ).sendMessage ( CC.translate ( "&7Approve a player's nickname change request." ) );
            return;
        }
        Player player = Main.instance.getServer ( ).getPlayer ( command.getArgs ( 0 ) );
        Bukkit.getServer ( ).dispatchCommand ( Bukkit.getConsoleSender ( ), "enick " + player.getName ( ) + " " + CommandNick.nicknames.get ( player )  );
        player.sendMessage ( CC.translate ( "&aYour nickname has been accepted by &c" + command.getPlayer ( ).getName ( ) ) );
    }
}
