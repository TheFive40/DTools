package org.delaware.commands;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.delaware.events.interactWithGift;
import org.delaware.tools.CC;
import org.delaware.tools.commands.BaseCommand;
import org.delaware.tools.commands.Command;
import org.delaware.tools.commands.CommandArgs;
import java.io.IOException;

public class UrgentCommand extends BaseCommand {
    @Command(name = "urgent", aliases = "urgente", usage = "&bPrueba utilizando &7/urgente <staff> <motivo>"
    , permission = "dbcplugin.urgent")
    @Override
    public void onCommand(CommandArgs command) throws IOException {
        if(command.getArgs().length>0){
           Player player =  command.getPlayer().getServer().getPlayer(command.getArgs(0));
           StringBuilder stringBuilder = new StringBuilder();
           for(int i = 1;i<command.getArgs().length;i++){
               stringBuilder.append(command.getArgs(i)+ " ");
           }
           player.playSound(player.getLocation(), Sound.GHAST_SCREAM, 10f, 1f);
           player.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.DARK_RED + "AVISO" + ChatColor.DARK_GRAY +
                   "]" + ChatColor.DARK_RED + " El staff " + command.getPlayer().getName() + " ha solicitado tu presencia" +
                   ChatColor.RED + " Motivo: " + ChatColor.DARK_GRAY + stringBuilder);
           command.getPlayer().sendMessage(CC.translate("&a¡El jugador "+ command.getArgs(0)+ " ha sido notificado!"));
           return;
        }
        command.getPlayer().sendMessage(CC.translate(command.getCommand().getUsage()));
    }
}
