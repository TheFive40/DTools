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
            player.sendMessage(CC.translate("&6/simanager setMaxDurability (newDurability) &e-> Cambia la durabilidad del item en mano"));
            player.sendMessage(CC.translate("&6/simanager setDigSpeed (newSpeed) &e-> Modifica la velocidad de minado del item en mano"));
            player.sendMessage(CC.translate("&6/simanager showDurability (value) &e-> Mostrar o no mostrar la durabilidad del objeto en mano (Valores son: y, yes, true, n, no, false)"));
            player.sendMessage(CC.translate("&6/simanager setIsTool (value) &e-> Cambiar si el item es o no una herramienta (Valores son: y, yes, true, n, no, false)"));
            player.sendMessage(CC.translate("&6NOTA: no sé como funciona la durabilidad esta"));
            player.sendMessage(CC.translate("&7---------------------------------------------"));
            return;
        }
        String action = args[0];
        if(player.getItemInHand().getType().equals(Material.AIR)) {
            player.sendMessage(CC.translate("&cDebes tener un item en la mano!"));
            return;
        }
        switch(action.toLowerCase().trim()) { //to do : setArmorType, setEnchantability
            case "setistool":
                if(args.length < 2) {
                    player.sendMessage(CC.translate("&cDebes especificar si el item es o no una herramienta!"));
                    return;
                }
                String argT = args[1].trim();
                String[] validYesOptionsT = {"y", "yes", "true"};
                String[] validNoOptionsT = {"n", "no", "false"};
                int countT = 0;
                for(String valid : validYesOptionsT) {
                    if(valid.equalsIgnoreCase(argT)) countT++;
                }
                for(String valid : validNoOptionsT) {
                    if(valid.equalsIgnoreCase(argT)) countT++;
                }
                if(countT == 1) {
                    ScriptedItemManager isTool = new ScriptedItemManager(player.getItemInHand());
                    for(String valid : validYesOptionsT) {
                        if(valid.equalsIgnoreCase(argT)) {
                            isTool.setIsTool(true);
                            player.setItemInHand(isTool.getItem());
                            break;
                        }
                    }
                    for(String valid : validNoOptionsT) {
                        if(valid.equalsIgnoreCase(argT)) {
                            isTool.setIsTool(false);
                            player.setItemInHand(isTool.getItem());
                            break;
                        }
                    }
                }else {
                    player.sendMessage(CC.translate("&cUso correcto: /simanager setIsTool <yes/no>"));
                    return;
                }
                break;
            case "showdurability":
                if(args.length < 2) {
                    player.sendMessage(CC.translate("&cDebes especificar si mostrar o no la durabilidad!"));
                    return;
                }
                String arg = args[1].trim();
                String[] validYesOptions = {"y", "yes", "true"};
                String[] validNoOptions = {"n", "no", "false"};
                int count = 0;
                for(String valid : validYesOptions) {
                    if(valid.equalsIgnoreCase(arg)) count++;
                }
                for(String valid : validNoOptions) {
                    if(valid.equalsIgnoreCase(arg)) count++;
                }
                if(count == 1) {
                    ScriptedItemManager durShow = new ScriptedItemManager(player.getItemInHand());
                    for(String valid : validYesOptions) {
                        if(valid.equalsIgnoreCase(arg)) {
                            durShow.setDurabilityShow(true);
                            player.setItemInHand(durShow.getItem());
                            break;
                        }
                    }
                    for(String valid : validNoOptions) {
                        if(valid.equalsIgnoreCase(arg)) {
                            durShow.setDurabilityShow(false);
                            player.setItemInHand(durShow.getItem());
                            break;
                        }
                    }
                }else {
                    player.sendMessage(CC.translate("&cUso correcto: /simanager showDurability <yes/no>"));
                    return;
                }
                break;
            case "setdigspeed":
                if(args.length < 2) {
                    player.sendMessage(CC.translate("&cDebes especificar una velocidad nueva!"));
                    return;
                }
                try {
                    Integer.parseInt(args[1].trim());
                }catch(NumberFormatException e) {
                    player.sendMessage(CC.translate("&cLa velocidad nueva debe ser un número entero"));
                    return;
                }
                ScriptedItemManager digSpeed = new ScriptedItemManager(player.getItemInHand());
                digSpeed.setDigSpeed(Integer.parseInt(args[1].trim()));
                player.setItemInHand(digSpeed.getItem());
                break;
            case "setmaxdurability":
                if(args.length < 2) {
                    player.sendMessage(CC.translate("&cDebes especificar una durabilidad nueva!"));
                    return;
                }
                try {
                    Integer.parseInt(args[1].trim());
                }catch(NumberFormatException e) {
                    player.sendMessage(CC.translate("&cLa durabilidad nueva debe ser un número entero"));
                    return;
                }
                ScriptedItemManager maxDur = new ScriptedItemManager(player.getItemInHand());
                maxDur.setMaxItemUseDuration(Integer.parseInt(args[1].trim()));
                player.setItemInHand(maxDur.getItem());
                break;
            case "changetexture":
                if(args.length < 2) {
                    player.sendMessage(CC.translate("&cDebes especificar una nueva textura!"));
                    return;
                }
                ScriptedItemManager texture = new ScriptedItemManager(player.getItemInHand());
                texture.setTexture(args[1].trim());
                player.setItemInHand(texture.getItem());
                break;
            case "changecolor":
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
                break;
            case "changescale":
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
                }
                ScriptedItemManager scale = new ScriptedItemManager(player.getItemInHand());
                scale.setScale(Integer.parseInt(args[1].trim()), Integer.parseInt(args[2].trim()), Integer.parseInt(args[3].trim()));
                player.setItemInHand(scale.getItem());
                break;
            case "changestacksize":
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
                break;
            default:
                player.sendMessage(CC.translate("&cIncorrect usage"));
                return;
        }
        player.sendMessage(CC.translate("&aCambios realizados exitosamente"));
    }
}
