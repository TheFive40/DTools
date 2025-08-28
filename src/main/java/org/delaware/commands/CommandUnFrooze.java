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
    public void onCommand(CommandArgs command) throws IOException {
        Player sender = command.getPlayer();

        // Comprobar que el comando lo ejecute un jugador
        if (sender == null) {
            command.getSender().sendMessage(CC.translate("&cSolo los jugadores pueden usar este comando."));
            return;
        }

        if (command.getArgs().length == 0) {
            sender.sendMessage(CC.translate("&cUsage: /frooze <player>"));
            sender.sendMessage(CC.translate("&7This command allows you to freeze a player, preventing them from moving."));
            return;
        }
        OfflinePlayer player = Bukkit.getOfflinePlayer(command.getArgs(0));
        Player froozePlayer = player.getPlayer();
        if (froozePlayer == null) {
            sender.sendMessage(CC.translate("&cThe player is not online or does not exist."));
            return;
        }
        if (playersFrooze.contains(froozePlayer)) {
            sender.sendMessage(CC.translate("&e" + froozePlayer.getName() + " &cis already frozen."));
            return;
        }
        playersFrooze.add(froozePlayer);
        sender.sendMessage(CC.translate("&aYou have successfully frozen &e" + froozePlayer.getName() + "&a."));
        froozePlayer.sendMessage(CC.translate("&cYou have been frozen! You cannot move until an administrator unfreezes you."));
    }

}
