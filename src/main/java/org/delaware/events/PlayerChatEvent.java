package org.delaware.events;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import noppes.npcs.api.entity.ICustomNpc;
import noppes.npcs.scripted.NpcAPI;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.delaware.Main;
import org.delaware.tools.CC;
import org.delaware.tools.General;

import java.util.Arrays;
import java.util.Objects;

import static org.delaware.commands.CommandTransform.entities;

public class PlayerChatEvent implements Listener {
    @EventHandler
    public void onPlayerChatEvent ( AsyncPlayerChatEvent event ) {
        String name = event.getPlayer ( ).getName ( );
        String message = event.getMessage ( );
        LuckPerms luckPerms = LuckPermsProvider.get ( );
        User user = luckPerms.getUserManager ( ).getUser ( name );
        if (entities.containsKey ( event.getPlayer ( ).getName ( ) )) {
            ICustomNpc<?> npc = (ICustomNpc<?>) entities.get ( event.getPlayer ( ).getName ( ) );
            npc.say ( CC.translate ( event.getMessage ( ) ) );
            event.setCancelled ( true );
            return;
        }
        assert user != null;
        if (message.startsWith ( "@" ) && Arrays.asList ( General.ranks ).contains ( user.getPrimaryGroup ( ) )) {
            message = message.replace ( '@', ' ' );
            for (Player player : Main.instance.getServer ( ).getOnlinePlayers ( )) {
                String primaryGroup = Objects.requireNonNull ( luckPerms.getUserManager ( ).
                        getUser ( player.getName ( ) ) ).getPrimaryGroup ( );
                if (Arrays.asList ( General.ranks ).contains ( primaryGroup )) {
                    player.sendMessage ( CC.translate ( "&8[&cCHAT&8-&4STAFF&8] &c" + name + ": " + message ) );
                    player.playSound ( player.getLocation ( ), Sound.NOTE_PLING, 10f, 10f );
                }
            }
            event.setCancelled ( true );
        }
    }
}
