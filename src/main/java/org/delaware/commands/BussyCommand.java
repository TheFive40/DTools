package org.delaware.commands;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.delaware.Main;
import org.delaware.tools.CC;
import org.delaware.tools.commands.BaseCommand;
import org.delaware.tools.commands.Command;
import org.delaware.tools.commands.CommandArgs;
import java.io.IOException;
import java.util.HashMap;

public class BussyCommand extends BaseCommand {
    public static HashMap<String, String> staff = new HashMap<> ( );

    @Command(name = "ocupado", description = "Ponte modo ocupado en el servidor"
            , permission = "dtools.staffbussy")
    @Override
    public void onCommand ( CommandArgs command ) throws IOException {
        String message = "--------------------------------------------";
        int messageWidth = message.length ( );
        int spacesToAdd = (60 - messageWidth) / 2;
        String centeredMessage01 = new String ( new char[spacesToAdd] ).replace ( "\0", " " );
        String espacios = centeredMessage01 + centeredMessage01 + centeredMessage01 + centeredMessage01;
        StringBuilder razon = new StringBuilder ( );
        String username = command.getPlayer ( ).getName ( );
        if (staff.containsKey ( username )) {
            command.getSender ( ).getServer ( ).broadcastMessage ( CC.translate ( "&2&l&m" + message ) );
            command.getSender ( ).getServer ( ).broadcastMessage ( CC.translate ( "&a¡El Staff &2" + username + "&a" +
                    " ya no esta ocupado!") );
            command.getSender ( ).getServer ( ).broadcastMessage ( CC.translate ( "&2&l&m" + message ) );
            BukkitRunnable runnable = new BukkitRunnable ( ) {
                @Override
                public void run () {
                    for (Player onlinePlayer : command.getSender ( ).getServer ( ).getOnlinePlayers ( )) {
                        onlinePlayer.playSound ( onlinePlayer.getLocation ( ), Sound.ANVIL_BREAK, 10f, 10f );

                    }
                }
            };
            runnable.runTaskAsynchronously ( Main.instance );
            staff.remove ( username );
        }else if (command.getArgs ( ).length >= 1) {
            for (int i = 0; i < command.getArgs ( ).length; i++)
                razon.append ( command.getArgs ( i ) + " " );
            command.getSender ( ).getServer ( ).broadcastMessage ( CC.translate ( "&6&l&m" + message ) );
            command.getSender ( ).getServer ( ).broadcastMessage ( CC.translate ( "&e¡El Staff " + command.getPlayer ( )
                    .getName ( ) + " ahora esta ocupado!" ) );
            command.getSender ( ).getServer ( ).broadcastMessage ( CC.translate ( "&e¡Evita ser molesto o podrás recibir una sancion!" ) );
            command.getSender ( ).getServer ( ).broadcastMessage ( CC.translate ( "&eRazón: &c" + razon ) );
            command.getSender ( ).getServer ( ).broadcastMessage ( CC.translate ( "&6&l&m" + message ) );
            BukkitRunnable runnable = new BukkitRunnable ( ) {
                @Override
                public void run () {
                    for (Player onlinePlayer : command.getSender ( ).getServer ( ).getOnlinePlayers ( )) {
                        onlinePlayer.playSound ( onlinePlayer.getLocation ( ), Sound.ANVIL_LAND, 10f, 10f );

                    }
                }
            };
            runnable.runTaskAsynchronously ( Main.instance );
            staff.put ( command.getPlayer ( ).getName ( ), razon.toString ( ) );
        } else {
            command.getPlayer ( ).sendMessage ( CC.translate ( "&3Prueba utilizando &7/ocupado <reason>" ) );
        }
    }
}
