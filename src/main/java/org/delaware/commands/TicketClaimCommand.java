package org.delaware.commands;

import net.minecraftforge.event.entity.player.PlayerUseItemEvent;
import org.bukkit.entity.Player;
import org.delaware.Main;
import org.delaware.tools.CC;
import org.delaware.tools.commands.BaseCommand;
import org.delaware.tools.commands.Command;
import org.delaware.tools.commands.CommandArgs;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

public class TicketClaimCommand extends BaseCommand {
    public static ConcurrentHashMap<Integer, String> ticketClaimed = new ConcurrentHashMap<>();
    @Command(name = "ticketclaim", permission = "dtools.ticketclaim", aliases = {"tclaim","tc"})
    @Override
    public void onCommand(CommandArgs command) throws IOException {
        Player staff = command.getPlayer();
        if(command.getArgs().length<1){
            staff.sendMessage(CC.translate("&cPrueba utilizando &7/tclaim <code>"));
            return;
        }
        Integer code = Integer.parseInt(command.getArgs(0));
        AtomicReference<String> playerName = new AtomicReference<>("");
        TicketCommand.tickets.forEach((k,v)->{
            if(k == code){
                ticketClaimed.put(k, command.getPlayer().getName());
            }
        });
        playerName.set(getPlayerTicket(code));
        command.getPlayer().sendMessage(CC.translate("&cTicket N-" + code + " claimeado por el staff " +
                staff.getName()));
        Main.instance.getServer().getPlayer(playerName.get()).sendMessage(CC.translate("&a&l➤ &3&lTICKETS &c El staff " + staff.getName() + " ha atendido " +
                "tu ticket &4N-" + code));
    }
    public static String getPlayerTicket(int code){
        AtomicReference<String> playerName = new AtomicReference<>("");
        TicketCommand.playerOpenTicket.forEach((k,v)->{
            v.forEach(e->{
                if(e.intValue() == code){
                    playerName.set(k);
                }
            });
        });
        return playerName.toString();
    }
}
