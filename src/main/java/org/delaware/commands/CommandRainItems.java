package org.delaware.commands;

import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.delaware.Main;
import org.delaware.tools.CC;
import org.delaware.tools.commands.BaseCommand;
import org.delaware.tools.commands.Command;
import org.delaware.tools.commands.CommandArgs;

import java.io.IOException;


public class CommandRainItems extends BaseCommand {
    private String message = "------------------------------------------";
    private int messageWidth = message.length();
    private int spacesToAdd = (60 - messageWidth) / 2;
    String centeredMessage01 = new String(new char[spacesToAdd]).replace("\0", " ");

    @Command(name = "DBCFuture.rain", aliases = "lluvia", permission = "DBCFuture.lluvia")
    @Override
    public void onCommand(CommandArgs command) throws IOException {
        if (!(command.getArgs().length > 2)) {
            command.getPlayer().sendMessage(CC.translate("&3Prueba utilizando &7/lluvia <itemId> <tiempo> <nombre>"));
            return;
        }
        centeredMessage01 += centeredMessage01 + centeredMessage01;
        if(Integer.parseInt(command.getArgs(1))<=0){
            command.getPlayer().sendMessage(CC.translate("&c¡No puedes utilizar segundos negativos ni iguales a cero!"));
            return;
        }
        int time = Integer.parseInt(command.getArgs(1))+3;
        BukkitRunnable runnable = new BukkitRunnable() {

            private int currentTime = 0;
            private int id = Integer.parseInt(command.getArgs(0));

            @Override
            public void run() {
                currentTime+=5;

                if (currentTime >= time*20) {
                    cancel();
                }
                Main.instance.getServer().getOnlinePlayers().forEach(p -> {
                    World world = p.getWorld();
                    Location location = new Location(world, p.getLocation().getX(), p.getLocation().getY() + 5,
                            p.getLocation().getZ());
                    ItemStack item = new ItemStack(id, 1);
                    world.dropItem(location, item);
                });
                currentTime++;
            }
        };
        String name = command.getArgs(2);
        runnable.runTaskTimer(Main.instance, 0L, 5L);
        Server server = command.getPlayer().getServer();
        server.broadcastMessage(CC.translate("&6&m" + message));
        server.broadcastMessage(CC.translate(" &a➤ &bDrop de &e" + name + " &bactivado por &e" + command.getPlayer().getName()));
        server.broadcastMessage(CC.translate("&6&m" + message));
        server.getOnlinePlayers().forEach(x -> {
            x.playSound(x.getLocation(), Sound.WITHER_SHOOT, 20F, 20F);
        });
    }
}
