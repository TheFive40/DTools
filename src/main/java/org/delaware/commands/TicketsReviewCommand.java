package org.delaware.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.delaware.tools.CC;
import org.delaware.tools.commands.BaseCommand;
import org.delaware.tools.commands.Command;
import org.delaware.tools.commands.CommandArgs;

import java.io.IOException;

public class TicketsReviewCommand extends BaseCommand {
    @Command(name = "ticketsreview", aliases = {"ticketsreview"}, permission = "dtools.ticketsreview")
    @Override
    public void onCommand(CommandArgs command) throws IOException {
        String mensaje ="&c&l&m-------&2&l&m--------&e&l&m---------&6&l&m---------&d&l&m---------";
        Player player = command.getPlayer();
        player.sendMessage(CC.translate(mensaje));
        TicketClaimCommand.ticketClaimed.forEach((k,v)->{
            if(v.equalsIgnoreCase(player.getName())){
                player.sendMessage(CC.translate("&7» &cTicket N-"+ k.intValue() + " &a atendido por el staff " + player.getName()));
            }
        });
        player.sendMessage(CC.translate(mensaje));

    }
}
