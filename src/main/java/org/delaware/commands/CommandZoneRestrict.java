package org.delaware.commands;

import org.bukkit.entity.Player;
import org.delaware.tools.CC;
import org.delaware.tools.commands.BaseCommand;
import org.delaware.tools.commands.Command;
import org.delaware.tools.commands.CommandArgs;

import java.util.ArrayList;
import java.util.List;

import static org.delaware.tools.RegionUtils.restrictedRegions;

public class CommandZoneRestrict extends BaseCommand {
    @Command(name = "zonarestringida", aliases = {"zonarestringida", "zr"}, permission = "dbfuture.admin")
    @Override
    public void onCommand ( CommandArgs command ) {
        Player sender = command.getPlayer ( );

        if (command.getArgs ( ).length < 1) {
            sender.sendMessage ( CC.translate ( "&cUso: /zonarestringida <add|remove|list> [region]" ) );
            return;
        }

        String action = command.getArgs ( 0 ).toLowerCase ( );

        switch (action) {
            case "add":
                if (command.getArgs ( ).length < 2) {
                    sender.sendMessage ( CC.translate ( "&cDebes especificar una región." ) );
                    return;
                }
                String regionToAdd = command.getArgs ( 1 );
                restrictedRegions.add ( regionToAdd );
                sender.sendMessage ( CC.translate ( "&aLa región '&e" + regionToAdd + "&a' ha sido añadida a la lista de zonas restringidas." ) );
                break;

            case "remove":
                if (command.getArgs ( ).length < 2) {
                    sender.sendMessage ( CC.translate ( "&cDebes especificar una región." ) );
                    return;
                }
                String regionToRemove = command.getArgs ( 1 );
                if (restrictedRegions.remove ( regionToRemove )) {
                    sender.sendMessage ( CC.translate ( "&aLa región '&e" + regionToRemove + "&a' ha sido eliminada de la lista." ) );
                } else {
                    sender.sendMessage ( CC.translate ( "&cLa región '&e" + regionToRemove + "&c' no estaba en la lista." ) );
                }
                break;

            case "list":
            case "l":
                int page = 1;
                if (command.getArgs ( ).length > 1) {
                    try {
                        page = Integer.parseInt ( command.getArgs ( 1 ) );
                    } catch (NumberFormatException e) {
                        sender.sendMessage ( CC.translate ( "&cLa página debe ser un número válido." ) );
                        return;
                    }
                }
                listRestrictedRegions ( sender, page );
                break;

            default:
                sender.sendMessage ( CC.translate ( "&cUso: /zonarestringida <add|remove|list> [region]" ) );
                break;
        }
    }

    private static final int ITEMS_PER_PAGE = 5;

    private void listRestrictedRegions ( Player sender, int page ) {
        List<String> regions = new ArrayList<> ( restrictedRegions );

        int totalPages = (int) Math.ceil ( (double) regions.size ( ) / ITEMS_PER_PAGE );
        if (page > totalPages || page < 1) {
            sender.sendMessage ( CC.translate ( "&cPágina fuera de rango. Páginas disponibles: 1 - " + totalPages ) );
            return;
        }

        sender.sendMessage ( CC.translate ( "&6Zonas Restringidas &7(Página " + page + "/" + totalPages + ")" ) );

        int startIndex = (page - 1) * ITEMS_PER_PAGE;
        int endIndex = Math.min ( startIndex + ITEMS_PER_PAGE, regions.size ( ) );

        for (int i = startIndex; i < endIndex; i++) {
            sender.sendMessage ( CC.translate ( "&e- " + regions.get ( i ) ) );
        }

        if (page < totalPages) {
            sender.sendMessage ( CC.translate ( "&7Usa &e/zonarestringida list " + (page + 1) + " &7para la siguiente página." ) );
        }
    }
}
