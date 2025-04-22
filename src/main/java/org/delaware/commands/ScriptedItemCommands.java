package org.delaware.commands;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.delaware.tools.CC;
import org.delaware.tools.CustomItems.ScriptedItems.ScriptedItemManager;
import org.delaware.tools.commands.BaseCommand;
import org.delaware.tools.commands.Command;
import org.delaware.tools.commands.CommandArgs;

import java.io.IOException;

public class ScriptedItemCommands extends BaseCommand {
    @Command(name = "scripteditemsmanager", aliases = "simanager", permission = "DBFUTURE.SIMANAGER")
    @Override
    public void onCommand(CommandArgs command) throws IOException {
        String[] args = command.getArgs();
        Player player = command.getPlayer();
        if(args.length < 1) {
            player.sendMessage(CC.translate("&eComandos disponibles:"));
            player.sendMessage(CC.translate("&7---------------------------------------------"));
            player.sendMessage(CC.translate("&6/simanager changeTexture (newTexture) &e-> Cambia la textura del item en mano"));
            player.sendMessage(CC.translate("&6/simanager changeColor (newColor) &e-> Cambia el color del item en mano"));
            player.sendMessage(CC.translate("&6/simanager changeScale (X) (Y) (Z) &e-> Cambia el tamaño del item en mano"));
            player.sendMessage(CC.translate("&6/simanager changeStackSize (newStackSize) &e-> Cambia el límite de stack del item en mano"));
            player.sendMessage(CC.translate("&7---------------------------------------------"));
            return;
        }
        String action = args[0];
        switch(action.toLowerCase().trim()) {
            case "changetexture":
                if(player.getItemInHand().getType().equals(Material.AIR)) {
                    player.sendMessage(CC.translate("&cDebes tener un item en la mano!"));
                    return;
                }
                if(args.length < 2) {
                    player.sendMessage(CC.translate("&cDebes especificar una nueva textura!"));
                    return;
                }
                ScriptedItemManager texture = new ScriptedItemManager(player.getItemInHand());
                texture.setTexture(args[1].trim());
                player.setItemInHand(texture.getItem());
                player.sendMessage(CC.translate("&aCambios realizados exitosamente"));
                break;
            case "changecolor":
                if(player.getItemInHand().getType().equals(Material.AIR)) {
                    player.sendMessage(CC.translate("&cDebes tener un item en la mano!"));
                    return;
                }
                if(args.length < 2) {
                    player.sendMessage(CC.translate("&cDebes especificar un nuevo color!"));
                    return;
                }
                try {
                    Integer.parseInt(args[1].trim());
                }catch(NumberFormatException e) {
                    player.sendMessage(CC.translate("&cEl nuevo color debe ser un número entero"));
                    return;
                }
                ScriptedItemManager color = new ScriptedItemManager(player.getItemInHand());
                color.setColor(Integer.parseInt(args[1].trim()));
                player.setItemInHand(color.getItem());
                player.sendMessage(CC.translate("&aCambios realizados exitosamente"));
                break;
            case "changescale":
                if(player.getItemInHand().getType().equals(Material.AIR)) {
                    player.sendMessage(CC.translate("&cDebes tener un item en la mano!"));
                    return;
                }
                if(args.length < 4) {
                    player.sendMessage(CC.translate("&cUso correcto: /simanager changeScale <X> <Y> <Z>"));
                    return;
                }
                try {
                    Integer.parseInt(args[1].trim());
                    Integer.parseInt(args[2].trim());
                    Integer.parseInt(args[3].trim());
                }catch(NumberFormatException e) {
                    player.sendMessage(CC.translate("&cDebes utilizar números enteros para el tamaño"));
                    return;
                }
                ScriptedItemManager scale = new ScriptedItemManager(player.getItemInHand());
                scale.setScale(Integer.parseInt(args[1].trim()), Integer.parseInt(args[2].trim()), Integer.parseInt(args[3].trim()));
                player.setItemInHand(scale.getItem());
                player.sendMessage(CC.translate("&aCambios realizados exitosamente"));
                break;
            case "changestacksize":
                if(player.getItemInHand().getType().equals(Material.AIR)) {
                    player.sendMessage(CC.translate("&cDebes tener un item en la mano!"));
                    return;
                }
                if(args.length < 2) {
                    player.sendMessage(CC.translate("&cDebes especificar un nuevo límite de stack!"));
                    return;
                }
                try {
                    Integer.parseInt(args[1].trim());
                }catch(NumberFormatException e) {
                    player.sendMessage(CC.translate("&cEl nuevo límite debe ser un número entero"));
                    return;
                }
                ScriptedItemManager stackSize = new ScriptedItemManager(player.getItemInHand());
                stackSize.setMaxStackSize(Integer.parseInt(args[1].trim()));
                player.setItemInHand(stackSize.getItem());
                player.sendMessage(CC.translate("&aCambios realizados exitosamente"));
                break;
        }
    }
}
