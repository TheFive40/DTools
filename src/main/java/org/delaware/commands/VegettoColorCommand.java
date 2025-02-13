package org.delaware.commands;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.delaware.tools.CC;
import org.delaware.tools.commands.BaseCommand;
import org.delaware.tools.commands.Command;
import org.delaware.tools.commands.CommandArgs;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class VegettoColorCommand extends BaseCommand {
    int[] vegettos = {4951, 4964, 4949, 4981, 4983, 4948, 4965, 4947, 4955, 4953};
    @Command ( name = "changevegetto", permission = "dtools.change.vegetto",
    description = "Cambia el color del kit vegetto a un jugador")
    @Override
    public void onCommand ( CommandArgs command ) throws IOException {
        if (command.getArgs ( ).length == 2) {
            Player player = command.getSender ( ).getServer ( ).getPlayer ( command.getArgs ( 0 ) );
            String color = command.getArgs ( 1 );
            ItemStack item = player.getItemInHand ( );
            List<Integer> vegettoList = Arrays.stream ( vegettos ).boxed ()
                    .collect( Collectors.toList());
            if (vegettoList.contains ( item.getType ().getId () )) {
                switch (color.toLowerCase ( )) {
                    case "rose":
                    case "pink":
                        player.setItemInHand ( new ItemStack ( 4951, 1 ) );
                        break;
                    case "red":
                        player.setItemInHand ( new ItemStack ( 4964, 1 ) );
                        break;
                    case "blue":
                        player.setItemInHand ( new ItemStack ( 4949, 1 ) );
                        break;
                    case "cyan":
                        player.setItemInHand ( new ItemStack ( 4981, 1 ) );
                        break;
                    case "green":
                    case "lima":
                        player.setItemInHand ( new ItemStack ( 4983, 1 ) );
                        break;
                    case "black":
                        player.setItemInHand ( new ItemStack ( 4948, 1 ) );
                        break;
                    case "orange":
                        player.setItemInHand ( new ItemStack ( 4965, 1 ) );
                        break;
                    case "yellow":
                        player.setItemInHand ( new ItemStack ( 4947, 1 ) );
                        break;
                    case "white":
                        player.setItemInHand ( new ItemStack ( 4955, 1 ) );
                        break;
                    case "rainbown":
                        player.setItemInHand ( new ItemStack ( 4953, 1 ) );
                        break;
                    default:
                        command.getSender ().sendMessage ( CC.translate ( "&7Al parecer el color &a" + color + "&7 no existe" ) );
                        break;
                }
                player.sendMessage ( CC.translate ( "&7Color &a" + color + " &7de la vegetto actualizado correctamente" ) );
                command.getSender ( ).sendMessage ( CC.translate ( "&7Color &a" + color + " &7de la vegetto actualizado correctamente" ) );
            }
            return;
        }
        command.getSender ().sendMessage ( CC.translate ( "&3Prueba utilizando &7/changevegetto <player> <color>" ) );
    }
}
