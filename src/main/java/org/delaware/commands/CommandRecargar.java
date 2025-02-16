package org.delaware.commands;

import org.delaware.tools.*;

import org.bukkit.Location;
import org.delaware.tools.commands.*;

import java.io.IOException;

public class CommandRecargar extends BaseCommand {
    @Command(aliases = {"recargar"}, name = "recargar")
    @Override
    public void onCommand(CommandArgs command) throws IOException {
        Location location = command.getPlayer().getLocation();
        command.getPlayer().getServer().dispatchCommand(command.getPlayer(), "warp recargar");
        command.getPlayer().teleport(location);
        command.getPlayer().sendMessage(CC.translate("&6¡Chunks recargados correctamente! si sigues teniendo problemas" +
                " puedes volver a intentar usar este comando para recargar nuevamente"));
        command.getPlayer().teleport(location);

    }
}
