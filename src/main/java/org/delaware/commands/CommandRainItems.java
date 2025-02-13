package org.delaware.commands;

import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.delaware.Main;
import org.delaware.tools.CC;
import org.delaware.tools.commands.BaseCommand;
import org.delaware.tools.commands.Command;
import org.delaware.tools.commands.CommandArgs;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;


public class CommandRainItems extends BaseCommand {
    private boolean isActive;
    private BukkitRunnable bukkit;
    private String message = "------------------------------------------";
    private int messageWidth = message.length ( );
    private int spacesToAdd = (60 - messageWidth) / 2;
    String centeredMessage01 = new String ( new char[spacesToAdd] ).replace ( "\0", " " );

    @Command(name = "DBCFuture.rain", aliases = "lluvia", permission = "DBCFuture.lluvia")
    @Override
    public void onCommand ( CommandArgs command ) throws IOException {
        if (!(command.getArgs ( ).length > 2)) {
            command.getPlayer ( ).sendMessage ( CC.translate ( "&3Prueba utilizando &7/lluvia <itemId> <tiempo> <nombre>" ) );
            return;
        }
        centeredMessage01 += centeredMessage01 + centeredMessage01;
        if (Integer.parseInt ( command.getArgs ( 1 ) ) <= 0) {
            command.getPlayer ( ).sendMessage ( CC.translate ( "&c¡No puedes utilizar segundos negativos ni iguales a cero!" ) );
            return;
        }
        String name = command.getArgs ( 2 );
        Server server = command.getPlayer ( ).getServer ( );
        server.broadcastMessage ( CC.translate ( "&6&m" + message ) );
        server.broadcastMessage ( CC.translate ( " &a➤ &bDrop de &e" + name + " &bactivado por &e" + command.getPlayer ( ).getName ( ) ) );
        server.broadcastMessage ( CC.translate ( "&6&m" + message ) );
        for (Player onlinePlayer : server.getOnlinePlayers ( ))
            onlinePlayer.playSound ( onlinePlayer.getLocation ( ), Sound.WITHER_SHOOT, 20F, 20F );

        if (bukkit != null && command.length ( ) == 1 && command.getArgs ( 0 ).toLowerCase ( ).equals ( "disable" )) {
            bukkit.cancel ( );
            bukkit = null;
            isActive = false;
            command.getSender ( ).sendMessage ( CC.translate ( "&aLa lluvia ha sido detenida." ) );
            return;
        }
        if (isActive) {
            command.getSender ( ).sendMessage ( CC.translate ( "&cYa hay una lluvia activa." ) );
            return;
        }
        if (command.getArgs ( ).length != 3) {
            command.getSender ( ).sendMessage ( CC.translate ( command.getCommand ( ).getUsage ( ) ) );
            return;
        }
        int itemId;
        int duration;
        try {
            itemId = Integer.parseInt ( command.getArgs ( 0 ) );
            duration = Integer.parseInt ( command.getArgs ( 1 ) );
        } catch (NumberFormatException exception) {
            command.getSender ( ).sendMessage ( CC.translate ( "&cDebe ingresar un número válido." ) );
            return;
        }
        if (duration <= 0) {
            command.getSender ( ).sendMessage ( CC.translate ( "&cEl tiempo debe ser mayor a 0 segundos." ) );
            return;
        }
        ItemStack stack = new ItemStack ( itemId, 1 );
        if (itemId <= 0) {
            Player player;
            try {
                player = (Player) command.getSender ( );
            } catch (ClassCastException exception) {
                command.getSender ( ).sendMessage ( CC.translate ( "&c¡Escribe un ID valido para ejecutar este comando!" ) );
                return;
            }
            stack = player.getItemInHand ( );
            if (stack == null) {
                player.sendMessage ( CC.translate ( "&c¡Debes sostener un item en la mano!" ) );
                return;
            }
        }
        isActive = true;
        int durationInSeconds = duration;
        int ticksPerIteration = 4;
        ItemStack finalStack = stack;
        bukkit = new BukkitRunnable ( ) {
            long startTime = System.currentTimeMillis ( );
            long durationInMillis = durationInSeconds * 1000L;

            @Override
            public void run () {
                long elapsedTime = System.currentTimeMillis ( ) - startTime;
                if (elapsedTime >= durationInMillis) {
                    isActive = false;
                    cancel ( );
                }
                CopyOnWriteArrayList<Player> onlines = new CopyOnWriteArrayList<> ( command.getSender ( ).getServer ( ).
                        getOnlinePlayers ( ) );
                for (Player player : onlines) {
                    double x = player.getLocation ( ).getX ( );
                    double y = player.getLocation ( ).getY ( ) + 3;
                    double z = player.getLocation ( ).getZ ( );
                    Location location = new Location ( player.getWorld ( ), x, y, z );
                    player.getWorld ( ).dropItem ( location, finalStack );
                    player.playSound ( player.getLocation ( ), "random.pop", 1.0F, 2.0F );
                }
            }
        };
        BukkitRunnable runnable = new BukkitRunnable ( ) {
            @Override
            public void run () {
                for (Player onlinePlayer : Main.instance.getServer ( ).getOnlinePlayers ( )) {
                    onlinePlayer.playSound ( onlinePlayer.getLocation ( ), "random.orb", 1.0F, 1.0F );
                }
            }
        };
        runnable.runTask ( Main.instance );
        bukkit.runTaskTimerAsynchronously ( Main.instance, 0L, ticksPerIteration );
    }
}
