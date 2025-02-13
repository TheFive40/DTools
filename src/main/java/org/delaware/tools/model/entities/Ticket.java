package org.delaware.tools.model.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.delaware.Main;
import org.delaware.tools.CC;
import org.delaware.tools.commands.CommandArgs;

import java.util.ArrayList;
import java.util.Objects;

import static org.delaware.commands.TicketCommand.tickets;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Ticket {
    private String staff, username, reason;
    private ArrayList<String> messages;
    private Long idTicket;

    public Ticket ( Long id ) {
        this.idTicket = id;
    }

    @Override
    public boolean equals ( Object o ) {
        if (this == o) return true;
        if (!(o instanceof Ticket)) return false;
        Ticket ticket = (Ticket) o;
        return Objects.equals ( getIdTicket ( ), ticket.getIdTicket ( ) );
    }

    @Override
    public int hashCode () {
        return Objects.hashCode ( getIdTicket ( ) );
    }

    public void message ( String message, String username ) {
        messages.add ( message );
        Player staff = Main.instance.getServer ( ).getPlayer ( getStaff ( ) );
        Player player = Main.instance.getServer ( ).getPlayer ( getUsername ( ) );
        player.sendMessage ( CC.translate ( "&aSistema de Tickets N-" + getIdTicket ( ) ) );
        try {
            staff.sendMessage ( username + ": " + CC.translate ( message ) );
            player.sendMessage ( username + ": " + CC.translate ( message ) );
        } catch (NullPointerException exception) {
            staff.sendMessage ( CC.translate ( "&c¡Al parecer el usuario no se encuentra conectado, o no existe!" ) );
        }
    }

    public void message ( CommandArgs message, String username ) {
        StringBuilder text = new StringBuilder ( " " );
        for (int i = 1; i < message.length ( ); i++) {
            text.append ( message.getArgs ( i ) ).append ( " " );
        }

        Player staff = Main.instance.getServer ( ).getPlayer ( getStaff ( ) );
        Player player = Main.instance.getServer ( ).getPlayer ( getUsername ( ) );
        player.sendMessage ( CC.translate ( "&aSistema de Tickets N-" + getIdTicket ( ) ) );
        try {
            staff.sendMessage ( username + ": " + CC.translate ( text.toString ( ) ) );
            player.sendMessage ( username + ": " + CC.translate ( text.toString ( ) ) );
        } catch (NullPointerException exception) {
            staff.sendMessage ( CC.translate ( "&c¡Al parecer el usuario no se encuentra conectado, o no existe!" ) );
        }
    }

    public static Ticket getTicketById ( Long id ) {
        return tickets.get ( tickets.indexOf ( new Ticket ( id ) ) );
    }

    public static Ticket getTicketById ( String id ) {
        return tickets.get ( tickets.indexOf ( new Ticket ( Long.parseLong ( id ) ) ) );
    }

}
