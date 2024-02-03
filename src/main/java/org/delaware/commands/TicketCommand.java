package org.delaware.commands;

import org.bukkit.entity.Player;
import org.delaware.Main;
import org.delaware.tools.CC;
import org.delaware.tools.commands.BaseCommand;
import org.delaware.tools.commands.Command;
import org.delaware.tools.commands.CommandArgs;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.delaware.commands.TicketRemoveCommand.ticketsOnClose;

public class TicketCommand extends BaseCommand {
    public static ConcurrentHashMap<Integer, CopyOnWriteArrayList<String>> tickets = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<String, CopyOnWriteArrayList<Integer>> playerOpenTicket = new ConcurrentHashMap<>();

    @Command(name = "ticket", aliases = "ticket", description = "&a¡Resuelve tus dudas con el equipo del staff usando el sistema de tickets!")
    @Override
    public void onCommand(CommandArgs command) throws IOException {
        Player player = command.getPlayer();
        if (!(command.getArgs().length < 2)) {
            if (command.getArgs(0).equalsIgnoreCase("open")) {
                CopyOnWriteArrayList<String> messages = new CopyOnWriteArrayList<>();
                StringBuilder message = new StringBuilder();
                if(playerOpenTicket.containsKey(player.getName())){
                    if (playerOpenTicket.get(player.getName()).size() == 10) {
                        player.sendMessage(CC.translate("&cNo puedes seguir abriendo mas tickets debes esperar a que se cierre uno"));
                        return;
                    }
                }
                for (int i = 1; i < command.getArgs().length; i++) {
                    message.append(command.getArgs(i) + " ");
                }
                CopyOnWriteArrayList<Integer> ticketsOpen = null;
                messages.add(message.toString());
                int code = tickets.size();
                if (!playerOpenTicket.containsKey(command.getPlayer().getName())) {
                    ticketsOpen = new CopyOnWriteArrayList<>();
                    ticketsOpen.add(code);
                } else {
                    ticketsOpen = playerOpenTicket.get(command.getPlayer().getName());
                    ticketsOpen.add(code);
                }
                playerOpenTicket.put(command.getPlayer().getName(), ticketsOpen);
                tickets.put(code, messages);
                command.getPlayer().sendMessage(CC.translate("&cTicket &aN-" + code + " &cabierto correctamente" +
                        " espera a la respuesta de un STAFF"));
              for(Player player1 : Main.instance.getServer().getOnlinePlayers()){
                  PermissionUser permissionsEx = PermissionsEx.getUser(player1.getName());
                  if(permissionsEx.inGroup("ayudante") || permissionsEx.inGroup("admin")
                  || permissionsEx.inGroup("manager") || permissionsEx.inGroup("moderador")
                  || permissionsEx.inGroup("ayudantebeta") || permissionsEx.inGroup("programador")){
                      player1.sendMessage(CC.translate("&7» &cTicket N-" + code + " &7disponible para ser atendido "));
                  }
              }
            } else if (command.getArgs(0).equalsIgnoreCase("close")) {
                CopyOnWriteArrayList<Integer> tickets = playerOpenTicket.get(player.getName());
                int code = Integer.parseInt(command.getArgs(1));
                if (TicketClaimCommand.ticketClaimed.containsKey(code)) {
                    player.sendMessage(CC.translate("&c¡Tu ticket ya fue reclamado por un staff!"));
                    return;
                }
                for (Integer ticket : tickets) {
                    if (ticket.intValue() == code) {
                        tickets.remove(ticket);
                        tickets.remove(ticket);
                    }
                }
                ticketsOnClose.add(code);
                playerOpenTicket.put(player.getName(), tickets);
                player.sendMessage(CC.translate("&a&l➤ &3&lTICKETS &9Ticket &cN-" + code + " &9ha sido cerrado"));
            }
        } else if (command.getArgs().length == 1) {
            if(command.getArgs(0).equalsIgnoreCase("waiting")){
                CopyOnWriteArrayList<Integer> codes = playerOpenTicket.get(player.getName());
                player.sendMessage(CC.translate("&8&l---------------------------------------------"));
                for (int i = 0; i < codes.size(); i++) {
                    player.sendMessage(CC.translate("&7» &cTicket N-" + codes.get(i) + " &7en espera de ser atendido por un staff"));
                }
                player.sendMessage(CC.translate("&8&l---------------------------------------------"));
            }else{
                player.sendMessage(CC.translate("&8&l---------------------------------------------"));
                player.sendMessage(CC.translate("&3Prueba utilizando &3/ticket open &7<arguments>"));
                player.sendMessage(CC.translate("&3Prueba utilizando &3/ticket close &7<code>"));
                player.sendMessage(CC.translate("&3Prueba utilizando &3/ticket waiting"));
                player.sendMessage(CC.translate("&8&l---------------------------------------------"));
            }
        } else {
            player.sendMessage(CC.translate("&8&l---------------------------------------------"));
            player.sendMessage(CC.translate("&3Prueba utilizando &3/ticket open &7<arguments>"));
            player.sendMessage(CC.translate("&3Prueba utilizando &3/ticket close &7<code>"));
            player.sendMessage(CC.translate("&3Prueba utilizando &3/ticket waiting"));
            player.sendMessage(CC.translate("&8&l---------------------------------------------"));
        }
    }
}
