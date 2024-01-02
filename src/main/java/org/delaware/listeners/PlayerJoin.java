package org.delaware.listeners;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.delaware.Main;
import org.delaware.commands.SelectCharacterCommand;
import org.delaware.tools.CC;
import org.delaware.tools.model.Character;
import java.util.concurrent.CopyOnWriteArrayList;

public class PlayerJoin implements Listener {
    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent playerJoinEvent) {
        Player player = playerJoinEvent.getPlayer();
        playerJoinEvent.setJoinMessage(CC.translate("&8[&a+&8] &a" + playerJoinEvent.getPlayer().getName()));
        ConsoleCommandSender consoleCommandSender = Main.instance.getServer().getConsoleSender();
        if(SelectCharacterCommand.characterHashMap.containsKey(player.getName())){
            CopyOnWriteArrayList<Character> characters = SelectCharacterCommand.characterHashMap.get(player.getName());
            characters.forEach(e->{
                if(e.isInUse()){
                    player.getServer().dispatchCommand(consoleCommandSender,"nick " + player.getName() + " " +e.getName());
                }
            });
        }
    }
}
