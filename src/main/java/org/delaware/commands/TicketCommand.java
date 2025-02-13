package org.delaware.commands;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.delaware.Main;
import org.delaware.tools.CC;
import org.delaware.tools.General;
import org.delaware.tools.commands.BaseCommand;
import org.delaware.tools.commands.CommandArgs;
import org.delaware.tools.model.entities.Ticket;
import java.io.IOException;
import java.util.LinkedList;

@Getter
@Setter
public class TicketCommand extends BaseCommand {
    public static LinkedList<Ticket> tickets = new LinkedList<> ( );

    @Override
    public void onCommand ( CommandArgs command ) throws IOException {
        Player player = command.getPlayer ( );
        if (command.getArgs ( ).length > 0) {
            String alias = command.getArgs ( 0 );
            long ticketId;
            Ticket ticket;
            switch (alias) {
                case "open":
                    String username = player.getName ( );
                    ticket = new Ticket ( );
                    ticket.setIdTicket ( (long) tickets.size ( ) );
                    ticket.setUsername ( username );
                    ticket.setReason ( General.joinText ( command.getArgs ( ), 1 ) );
                    tickets.add ( ticket );
                    player.sendMessage ( CC.translate ( "&a¡Nuevo ticket a la espera de ser atendido! &cid: " + ticket.getIdTicket ( ) ) );
                    break;
                case "close":
                    ticketId = Long.parseLong ( command.getArgs ( 1 ) );
                    ticket = Ticket.getTicketById ( ticketId );
                    player.sendMessage ( CC.translate ( "&c¡El staff " + ticket.getStaff ( ) ) + " ha cerrado tu ticket!" );
                    tickets.remove ( ticket );
                    break;
                case "response":
                    Ticket.getTicketById ( command.getArgs ( 1 ) ).message ( command, player.getName ( ) );
                case "r":
                    break;
                case "l":
                case "list":
                    if (command.getArgs ( ).length >= 2) {
                        int page = 1;
                        try {
                            page = Integer.parseInt ( command.getArgs ( 1 ) );
                        } catch (NumberFormatException ignored) {
                        }
                        String text = getTicketPage ( page );
                        player.sendMessage ( CC.translate ( text ) );
                    }
                    break;
                case "help":
                    break;
                case "claimed":
                case "claims":
                    break;
                case "claim":
                    ticket = Ticket.getTicketById ( command.getArgs ( 1 ) );
                    ticket.setStaff ( player.getName ( ) );
                    Player player1 = Main.instance.getServer ( ).getPlayer ( ticket.getUsername ( ) );
                    if (player1 != null && player1.isOnline ( )) {
                        player1.sendMessage ( CC.translate ( "&a¡El staff " + player1.getName ( ) + " ha reclamado tu ticket!" ) );
                        player.sendMessage ( CC.translate ( "&a¡Haz reclamado el ticket ID " + command.getArgs ( 1 ) + "!" ) );
                    }
                    tickets.set ( tickets.indexOf ( new Ticket ( Long.parseLong ( command.getArgs ( 1 ) ) ) ),
                            ticket );
                    break;
            }
        }
    }

    protected String getTicketPage ( int page ) {
        String message = "------------------------------------------";
        int messageWidth = message.length ( );
        int spacesToAdd = (60 - messageWidth) / 2;
        String centeredMessage01 = new String ( new char[spacesToAdd] ).replace ( "\0", " " );
        String centered = centeredMessage01 + centeredMessage01 + centeredMessage01 + centeredMessage01;
        StringBuilder text = new StringBuilder ( );
        text.append ( "&6&m" ).append ( message ).append ( "\n" );
        tickets.stream ( ).limit ( page * 7L ).forEach ( e -> {
            text.append ( "&cTicket N-&a" ).append ( e.getIdTicket ( ) ).append ( " &4" )
                    .append ( e.getReason ( ) ).append ( "\n" );
        } );
        text.append ( "&6&m" ).append ( message ).append ( "\n" );
        text.append ( "&7Page: " ).append ( page ).append ( "/" ).append ( tickets.size ( ) / page );
        return text.toString ( );
    }
}
