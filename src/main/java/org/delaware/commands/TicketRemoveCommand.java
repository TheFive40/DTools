package org.delaware.commands;

import net.minecraftforge.event.entity.player.PlayerUseItemEvent;
import org.bukkit.entity.Player;
import org.delaware.tools.CC;
import org.delaware.tools.commands.BaseCommand;
import org.delaware.tools.commands.Command;
import org.delaware.tools.commands.CommandArgs;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

public class TicketRemoveCommand extends BaseCommand {
    public static CopyOnWriteArrayList<Integer> ticketsOnClose = new CopyOnWriteArrayList<>();
    @Command(name = "removeticket", permission = "dtools.removeticket", aliases = "ticketremove")
    @Override
    public void onCommand(CommandArgs command) throws IOException {
        Player player = command.getPlayer();
        if(command.getArgs().length == 1){
            int code = Integer.parseInt(command.getArgs(0));
            String username = TicketClaimCommand.getPlayerTicket(code);
            CopyOnWriteArrayList<Integer> codes = TicketCommand.playerOpenTicket.get(username);
            for (Integer integer : codes) {
                if(code == integer){
                    codes.remove(integer);
                    TicketCommand.playerOpenTicket.put(username,codes);
                }
            }
            ticketsOnClose.add(code);
            TicketClaimCommand.ticketClaimed.remove(code);
            player.sendMessage(CC.translate("&cTicket removido correctamente"));
        }
    }
}
