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
        if (!command.isPlayer()) {
            return;
        }

        if (command.getArgs().length < 1) {
            command.getPlayer().sendMessage(CC.translate("&8[&6✦&8] &cUso: &e/nickAccept <jugador>"));
            command.getPlayer().sendMessage(CC.translate("&7Aprueba la solicitud de cambio de nickname de un jugador."));
            return;
        }

        Player player = Main.instance.getServer().getPlayer(command.getArgs(0));

        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),
                "enick " + player.getName() + " " + CommandNick.nicknames.get(player));

        player.sendMessage(CC.translate("&aTu nickname ha sido aprobado por &c" + command.getPlayer().getName()));
        command.getPlayer().sendMessage(CC.translate("&aHas aprobado el cambio de nickname para &c" + player.getName() +
                " &aa &e" + CommandNick.nicknames.get(player)));
    }
}
