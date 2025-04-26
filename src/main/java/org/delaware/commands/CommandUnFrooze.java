package org.delaware.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.delaware.tools.CC;
import org.delaware.tools.commands.BaseCommand;
import org.delaware.tools.commands.Command;
import org.delaware.tools.commands.CommandArgs;

import java.io.IOException;

import static org.delaware.commands.CommandFrooze.playersFrooze;

public class CommandUnFrooze extends BaseCommand {
    @Command ( name = "unfrooze",aliases = {"unfrooze","descongelar"}, permission = "dtools.unfrooze"
    , inGameOnly = false)
    @Override
    public void onCommand ( CommandArgs command ) throws IOException {
        Player sender = command.getPlayer();
        if (command.getArgs().length == 0) {
            sender.sendMessage(CC.translate("&cUsage: /unfrooze <player>"));
            sender.sendMessage(CC.translate("&7This command allows you to unfrooze a player, preventing them from moving."));
            return;
        }
        OfflinePlayer player = Bukkit.getOfflinePlayer ( command.getArgs ( 0 ) );
        Player froozePlayer = player.getPlayer ( );
        if (froozePlayer == null) {
            sender.sendMessage( CC.translate("&cThe player is not online or does not exist."));
            return;
        }
        if (playersFrooze.contains(froozePlayer)) {
            playersFrooze.remove ( froozePlayer );
            sender.sendMessage(CC.translate("&aYou have successfully unfrozen &e" + froozePlayer.getName() + "&a."));
            froozePlayer.sendMessage(CC.translate("&aYou have been unfrozen! You can now move freely."));
            return;
        }
        sender.sendMessage(CC.translate("&e" + froozePlayer.getName() + " &cis not frozen."));

    }
}
