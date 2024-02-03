package org.delaware.commands;

import net.minecraftforge.event.entity.player.PlayerUseItemEvent;
import org.bukkit.entity.Player;
import org.delaware.Main;
import org.delaware.tools.CC;
import org.delaware.tools.commands.BaseCommand;
import org.delaware.tools.commands.Command;
import org.delaware.tools.commands.CommandArgs;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

public class ResponseTicketCommand extends BaseCommand {
    @Command(name ="rticket", permission = "dtools.rticket", aliases = {"ticketr", "tr"})
    @Override
    public void onCommand(CommandArgs command) throws IOException {
        Player player = command.getPlayer();
        if(command.getArgs().length<1){
            player.sendMessage(CC.translate("&cPrueba utilizando &7/tr <response>"));
        }
        Integer code = Integer.parseInt(command.getArgs(0));
        StringBuilder message = new StringBuilder();
        for(int i = 1; i<command.getArgs().length;i++){
            message.append(CC.translate(command.getArgs(i) + " "));
        }
        if(TicketClaimCommand.ticketClaimed.containsKey(code) && TicketClaimCommand.ticketClaimed.get(code).equalsIgnoreCase(player.getName())){
            CopyOnWriteArrayList messages = TicketCommand.tickets.get(code);
            String playerName = TicketClaimCommand.getPlayerTicket(code);
            Player user = Main.instance.getServer().getPlayer(playerName);
            user.sendMessage(CC.translate("&a&l➤ &3&lTICKETS" +
                    " &8[&3&lSOPORTE&8] &9" + message));
            user.sendMessage(CC.translate("&a&l➤ &3&lTICKETS &cTicket N-" + code));
            messages.add(message.toString());
            TicketCommand.tickets.put(code,messages);
        }else if(TicketClaimCommand.ticketClaimed.containsKey(code) && !(TicketClaimCommand.ticketClaimed.get(code).equalsIgnoreCase(player.getName()))){
            player.sendMessage(CC.translate("&cEste ticket ya fue reclamado por el staff " + TicketClaimCommand.ticketClaimed.get(code)));
        }
    }
}
