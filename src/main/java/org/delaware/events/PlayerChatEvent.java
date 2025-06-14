package org.delaware.events;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import noppes.npcs.api.entity.ICustomNpc;
import noppes.npcs.api.entity.IDBCPlayer;
import noppes.npcs.scripted.NpcAPI;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.delaware.Main;
import org.delaware.tools.BankManager;
import org.delaware.tools.CC;
import org.delaware.tools.General;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static org.delaware.commands.CommandTransform.entities;

public class PlayerChatEvent implements Listener {

    private static final Set<String> awaitingWithdraw = ConcurrentHashMap.newKeySet();
    private static final Set<String> awaitingDeposit = ConcurrentHashMap.newKeySet();

    public static void waitForWithdraw(String playerName) {
        awaitingWithdraw.add(playerName);
        Bukkit.getScheduler().runTaskLater(Main.instance, () -> awaitingWithdraw.remove(playerName), 200L); // 10 segundos
    }

    public static void waitForDeposit(String playerName) {
        awaitingDeposit.add(playerName);
        Bukkit.getScheduler().runTaskLater(Main.instance, () -> awaitingDeposit.remove(playerName), 200L); // 10 segundos
    }

    @EventHandler
    public void onPlayerChatEvent(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String name = player.getName();
        String message = event.getMessage();

        // NPC dialog
        if (entities.containsKey(name)) {
            ICustomNpc<?> npc = (ICustomNpc<?>) entities.get(name);
            npc.say(CC.translate(message));
            event.setCancelled(true);
            return;
        }

        // Staff chat
        LuckPerms luckPerms = LuckPermsProvider.get();
        User user = luckPerms.getUserManager().getUser(name);
        if (user != null && message.startsWith("@") && Arrays.asList(General.ranks).contains(user.getPrimaryGroup())) {
            message = message.replace('@', ' ');
            for (Player online : Main.instance.getServer ().getOnlinePlayers ()) {
                User onlineUser = luckPerms.getUserManager().getUser(online.getName());
                if (onlineUser != null && Arrays.asList(General.ranks).contains(onlineUser.getPrimaryGroup())) {
                    online.sendMessage(CC.translate("&8[&cCHAT&8-&4STAFF&8] &c" + name + ": " + message));
                    online.playSound(online.getLocation(), Sound.NOTE_PLING, 10f, 10f);
                }
            }
            event.setCancelled(true);
            return;
        }

        // Withdraw
        if (awaitingWithdraw.contains(name)) {
            event.setCancelled(true);
            try {
                double amount = Double.parseDouble(message);
                if (amount <= 0) throw new NumberFormatException();
                boolean success = BankManager.withdraw(name, amount);
                if (success) {
                    player.sendMessage(CC.translate("&a✔ Retiraste &f" + amount + " TPS &adel banco."));
                }
            } catch (NumberFormatException e) {
                player.sendMessage(CC.translate("&cIngrese un número válido."));
            }
            awaitingWithdraw.remove(name);
            return;
        }

        // Deposit
        if (awaitingDeposit.contains(name)) {
            event.setCancelled(true);
            try {
                double amount = Double.parseDouble(message);
                if (amount <= 0) throw new NumberFormatException();
                IDBCPlayer dbcPlayer = NpcAPI.Instance().getPlayer(name).getDBCPlayer();
                if (dbcPlayer.getTP() < amount) {
                    player.sendMessage(CC.translate("&cNo tienes suficientes TPs para depositar esa cantidad."));
                    return;
                }
                boolean success = BankManager.deposit(name, amount);
                if (success) {
                    dbcPlayer.setTP((int) (dbcPlayer.getTP() - amount));
                    player.sendMessage(CC.translate("&a✔ Depositaste &f" + amount + " TPS &aen el banco."));
                }
            } catch (NumberFormatException e) {
                player.sendMessage(CC.translate("&cIngrese un número válido."));
            }
            awaitingDeposit.remove(name);
        }
    }
}
