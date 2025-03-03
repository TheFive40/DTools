package org.delaware.commands;

import org.bukkit.entity.Player;
import org.delaware.Main;
import org.delaware.tools.CC;
import org.delaware.tools.commands.BaseCommand;
import org.delaware.tools.commands.Command;
import org.delaware.tools.commands.CommandArgs;

import java.io.IOException;

public class CommandNickDecline extends BaseCommand {
    @Command(name = "nickDecline", description = "Decline a nickname", aliases = {"nd", "nickDecline"},
            permission = "dtools.nickdecline")
    @Override
    public void onCommand(CommandArgs command) throws IOException {
        if (!command.isPlayer()) {
            return;
        }
        if (command.getArgs().length < 1) {
            command.getPlayer().sendMessage( CC.translate("&8[&6✦&8] &cUsage: &e/nickDecline <player>"));
            command.getPlayer().sendMessage(CC.translate("&7Reject a player's nickname change request."));
            return;
        }

        Player player = Main.instance.getServer().getPlayer(command.getArgs(0));
        if (player == null || !CommandNick.nicknames.containsKey(player)) {
            command.getPlayer().sendMessage(CC.translate("&cThat player doesn't have a pending nickname request."));
            return;
        }
        CommandNick.nicknames.remove(player);
        player.sendMessage(CC.translate("&cYour nickname request has been declined by &e" + command.getPlayer().getName()));
        command.getPlayer().sendMessage(CC.translate("&aYou have declined the nickname change request from &c" + player.getName()));
    }

}
