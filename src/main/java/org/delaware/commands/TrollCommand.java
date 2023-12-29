package org.delaware.commands;

import org.bukkit.entity.Player;
import org.delaware.tools.CC;
import org.delaware.tools.commands.BaseCommand;
import org.delaware.tools.commands.Command;
import org.delaware.tools.commands.CommandArgs;

import java.io.IOException;


public class TrollCommand extends BaseCommand {
    @Command(name = "troll", usage = "&cPrueba utilizando &7/troll <player> <message>", aliases = "troll",
            description = "&6¡Molesta a otros usuarios usando este divertido comando!")

    @Override
    public void onCommand(CommandArgs command) throws IOException {
        if(command.getArgs().length == 0 || command.getArgs().length == 1){
            command.getSender().sendMessage(CC.translate(command.getCommand().getUsage()));
            return;
        }
        Player player = command.getPlayer().getServer().getPlayer(command.getArgs(0));
        StringBuilder fakeMessage = new StringBuilder();
        for (int i = 1; i < command.getArgs().length; i++) {
            fakeMessage.append(command.getArgs(i) + " ");
        }
        player.chat(CC.translate(fakeMessage.toString()));
        command.getPlayer().sendMessage(CC.translate("&a¡El jugador " + player.getName() + " ha sido molestado!"));
    }
}
