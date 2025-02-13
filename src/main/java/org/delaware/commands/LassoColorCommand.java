package org.delaware.commands;

import org.bukkit.Material;
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

public class LassoColorCommand extends BaseCommand {
    int[] lazos = {4976, 4993, 4952, 4989, 4974, 4950, 4968, 4991, 4958};

    @Command(name = "changelasso", permission = "dtools.lasso.change"
            , description = "Cambia el color del lazo divino a un jugador")
    @Override
    public void onCommand ( CommandArgs command ) throws IOException {
        if (command.getArgs ( ).length == 2) {
            Player player = command.getSender ( ).getServer ( ).getPlayer ( command.getArgs ( 0 ) );
            String color = command.getArgs ( 1 );
            ItemStack item = player.getItemInHand ( );
            if (item.getType () == Material.AIR){
                player.sendMessage ( CC.translate ( "&8[&aError&8] &7Por favor sosten el lazo divino en tu mano" ) );
                return;
            }
            List<Integer> lassoList = Arrays.stream ( lazos ).boxed ( )
                    .collect ( Collectors.toList ( ) );
            if (lassoList.contains ( item.getType ( ).getId ( ) )) {
                switch (color.toLowerCase ( )) {
                    case "rose":
                    case "pink":
                        player.setItemInHand ( new ItemStack ( 4976, 1 ) );
                        break;
                    case "red":
                        player.setItemInHand ( new ItemStack ( 4993, 1 ) );
                        break;
                    case "blue":
                        player.setItemInHand ( new ItemStack ( 4952, 1 ) );
                        break;
                    case "cyan":
                        player.setItemInHand ( new ItemStack ( 4989, 1 ) );
                        break;
                    case "green":
                    case "lima":
                        player.setItemInHand ( new ItemStack ( 4974, 1 ) );
                        break;
                    case "black":
                        player.setItemInHand ( new ItemStack ( 4950, 1 ) );
                        break;
                    case "orange":
                        player.setItemInHand ( new ItemStack ( 4968, 1 ) );
                        break;
                    case "yellow":
                        player.setItemInHand ( new ItemStack ( 4991, 1 ) );
                        break;
                    case "white":
                        player.setItemInHand ( new ItemStack ( 4958, 1 ) );
                        break;
                    default:
                        command.getSender ( ).sendMessage ( CC.translate ( "&7Al parecer el color &a" + color + "&7 no existe" ) );
                        break;
                }
                player.sendMessage ( CC.translate ( "&7Color de lazo &a" + color + " &7actualizado correctamente" ) );
                command.getSender ( ).sendMessage ( CC.translate ( "&7Color de lazo &a" + color + " &7actualizado correctamente" ) );
            }
            return;
        }
        command.getSender ().sendMessage ( CC.translate ( "&3Prueba utilizando &7/changelasso <player> <color>" ) );
    }
}
