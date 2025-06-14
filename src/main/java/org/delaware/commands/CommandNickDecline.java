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
            command.getPlayer().sendMessage(CC.translate("&8[&6✦&8] &cUso: &e/nickDecline <jugador>"));
            command.getPlayer().sendMessage(CC.translate("&7Rechaza la solicitud de cambio de nickname de un jugador."));
            return;
        }

        Player player = Main.instance.getServer().getPlayer(command.getArgs(0));

        if (player == null || !CommandNick.nicknames.containsKey(player)) {
            command.getPlayer().sendMessage(CC.translate("&cEse jugador no tiene una solicitud de nickname pendiente."));
            return;
        }

        CommandNick.nicknames.remove(player);

        player.sendMessage(CC.translate("&cTu solicitud de cambio de nickname ha sido rechazada por &e" + command.getPlayer().getName()));
        command.getPlayer().sendMessage(CC.translate("&aHas rechazado la solicitud de cambio de nickname de &c" + player.getName()));
    }

}
