package org.delaware.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.delaware.tools.CC;
import org.delaware.tools.commands.BaseCommand;
import org.delaware.tools.commands.Command;
import org.delaware.tools.commands.CommandArgs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class TicketListCommand extends BaseCommand {
    @Command(name = "ticketlist", permission = "dtools.tickets", description = "&aRevisa la lista de tickets activos"
            , aliases = {"tl"})
    @Override
    public void onCommand(CommandArgs command) throws IOException {
        String mensaje ="&c&l&m-------&2&l&m--------&e&l&m---------&6&l&m---------&d&l&m---------";
        String separator = "-----------------------------------------";
        int messageWidth = separator.length();
        int spacesToAdd = (int) ((60 - messageWidth) / 2);
        String centeredMessage01 = new String(new char[spacesToAdd]).replace("\0", " ");
        Player player = command.getPlayer();
        try{
            if(command.getArgs().length<1){
                player.sendMessage(CC.translate("&cPrueba utilizando &7/tl <page>"));
                return;
            }
            int paginado = Integer.parseInt(command.getArgs(0));
            ArrayList<Integer> codesOld = Collections.list(TicketCommand.tickets.keys());
            ArrayList<Integer> codes = new ArrayList<>();
            codesOld.forEach(e->{
                if(!TicketRemoveCommand.ticketsOnClose.contains(e) && !TicketClaimCommand.ticketClaimed.containsKey(e)){
                    codes.add(e);
                }
            });
            player.sendMessage( centeredMessage01+CC.translate("&c&lSOPORTE DE TICKETS DBC FUTURE"));
            if(paginado == 1 && codes.size()>10){
                player.sendMessage(CC.translate(mensaje));
                for(int i = (paginado*10)-1;i>paginado-1;i--){
                    player.sendMessage(CC.translate("&7» &cTicket N-" + codes.get(i) + " &7disponible para ser atendido " +
                            "por un STAFF"));
                }
                player.sendMessage(CC.translate(mensaje));
                player.sendMessage(CC.translate("&cPage "+ command.getArgs(0) + "/" +((TicketCommand.tickets.size()>=10) ? (TicketCommand.tickets.size() / 10) : 1)));
                return;
            }else if(paginado == 1 && codes.size() < 10){
                player.sendMessage(CC.translate(mensaje));
                for(int i = 0;i<codes.size();i++){
                    player.sendMessage(CC.translate("&7» &cTicket N-" + codes.get(i) + " &7disponible para ser atendido " +
                            "por un STAFF"));
                }
                player.sendMessage(CC.translate(mensaje));
                player.sendMessage(CC.translate("&cPage "+ command.getArgs(0) + "/" +((TicketCommand.tickets.size()>=10) ? (TicketCommand.tickets.size() / 10) : 1)));
                return;
            }else if (paginado*10 == TicketCommand.tickets.size()){
                player.sendMessage(CC.translate(mensaje));
                for (int i = (paginado * 10)-1; i > (paginado - 1) * 10; i--) {
                    player.sendMessage(CC.translate("&7» &cTicket N-" + codes.get(i) + " &7disponible para ser atendido " +
                            "por un STAFF"));
                }
                player.sendMessage(CC.translate(mensaje));
                player.sendMessage(CC.translate("&cPage "+ command.getArgs(0) + "/" +((TicketCommand.tickets.size()>=10) ? (TicketCommand.tickets.size() / 10) : 1)));
                return;
            }
            player.sendMessage(CC.translate(mensaje));
            for (int i = (paginado * 10)-1; i < (paginado + 1) * 10 && i < TicketCommand.tickets.size(); i++) {
                player.sendMessage(CC.translate("&7» &cTicket N-" +  codes.get(i)  + " &7disponible para ser atendido " +
                        "por un STAFF"));
            }
            player.sendMessage(CC.translate(mensaje));
            player.sendMessage(CC.translate("&cPage "+ command.getArgs(0) + "/" +((TicketCommand.tickets.size()>=10) ? (TicketCommand.tickets.size() / 10) : 1)));
        }catch(ArrayIndexOutOfBoundsException exception){
            exception.printStackTrace();
        }


    }
}
