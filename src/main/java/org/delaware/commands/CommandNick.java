package org.delaware.commands;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.delaware.tools.CC;
import org.delaware.tools.General;
import org.delaware.tools.commands.BaseCommand;
import org.delaware.tools.commands.Command;
import org.delaware.tools.commands.CommandArgs;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

public class CommandNick extends BaseCommand {
    public static ConcurrentHashMap<Player, String> nicknames = new ConcurrentHashMap<> ( );

    @Command(name = "apodo", aliases = {"apodo","name"}, permission = "dtools.nick")
    @Override
    public void onCommand ( CommandArgs command ) throws IOException {
        Player player = command.getPlayer ( );
        if (command.getArgs ( ).length == 0) {
            player.sendMessage(CC.translate("&8[&6✦&8] &cCorrect usage: &e/name <nickname>"));
            return;
        }
        General.getStaffs ( ).forEach ( e -> {
            e.sendMessage(CC.translate("&8[&e⚠ Warning&8] &c" + player.getName() +
                    " &ehas requested a nickname change to:"));
            e.sendMessage ( CC.translate ( "&8[&e⚠ Warning&8]&c " + command.getArgs ( 0 ) ) );
            e.sendMessage(CC.translate("&8[&6✦&8] &7Use &a/nickAccept &7to approve the change."));

            e.playSound ( e.getLocation (), Sound.CAT_MEOW, 1.0f,1.0f );
        } );
        nicknames.put ( player, command.getArgs ( 0 ) );
        player.sendMessage(CC.translate("&7You have requested to change your nickname to: &e" + command.getArgs ( 0 )));
        player.sendMessage(CC.translate("&7Please wait for a staff member to approve it."));
    }
}
