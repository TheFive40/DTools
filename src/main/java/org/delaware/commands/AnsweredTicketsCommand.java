package org.delaware.commands;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.delaware.tools.CC;
import org.delaware.tools.commands.BaseCommand;
import org.delaware.tools.commands.Command;
import org.delaware.tools.commands.CommandArgs;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;
public class AnsweredTicketsCommand extends BaseCommand {
    @Command(name = "answeredtickets", aliases = {"ticketAnswered", "ticketanswered"})
    @Override
    public void onCommand(CommandArgs command) throws IOException {
        String mensaje ="&c&l&m-------&2&l&m--------&e&l&m---------&6&l&m---------&d&l&m---------";
        Player player = command.getPlayer();
        if(command.getArgs().length<1){
            player.sendMessage(CC.translate("&cPrueba utilizando &7/ticketanswered <code>"));
            return;
        }
        int code = Integer.parseInt(command.getArgs(0));
        if(TicketCommand.playerOpenTicket.containsKey(player.getName())){
          CopyOnWriteArrayList<Integer> tickets = TicketCommand.playerOpenTicket.get(player.getName());
          tickets.forEach(x->{
              if(x == code){
                  player.sendMessage(CC.translate(mensaje));
                  CopyOnWriteArrayList<String> messages = TicketCommand.tickets.get(code);
                  for(int i = 1;i<  messages.size();i++){
                      player.sendMessage(CC.translate("&8[&3&lSOPORTE&8] &3" +  (messages.get(i).toString())));
                  }
                  player.sendMessage(CC.translate(mensaje));
              }
          });
          player.sendMessage(CC.translate("&a&l➤ &3&lTICKETS &9Ticket atendido por el staff: &c" + TicketClaimCommand.getPlayerTicket(code)));
        }
    }
}
