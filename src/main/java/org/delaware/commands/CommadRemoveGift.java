package org.delaware.commands;


import org.bukkit.Location;
import org.delaware.tools.CC;
import org.delaware.tools.commands.BaseCommand;
import org.delaware.tools.commands.Command;
import org.delaware.tools.commands.CommandArgs;
import org.delaware.tools.model.entities.Localizaciones;

import java.io.IOException;

public class CommadRemoveGift extends BaseCommand {
    @Command(name="removeGift", usage = "&cPrueba utilizando &7/removegift", permission = "removeGift.use")
    @Override
    public void onCommand( CommandArgs command) throws IOException {
        Location playerLocation = command.getPlayer().getLocation();
        for(Localizaciones localizaciones : CommandAddGift.itemStackHashMap){
            if(localizaciones.getBloqueX() == playerLocation.getBlockX()
             && localizaciones.getBloqueY() == playerLocation.getBlockY() && localizaciones.getBloqueZ()
            == playerLocation.getBlockZ() && playerLocation.getBlock().getWorld().getName().equalsIgnoreCase(
                    localizaciones.getWorld()
            )){
                CommandAddGift.itemStackHashMap.remove(localizaciones);
                command.getPlayer().sendMessage( CC.translate("&6¡Localización borrada!"));
                break;
            }
        }
    }


}
