package org.delaware.events;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import noppes.npcs.api.entity.IDBCPlayer;
import noppes.npcs.scripted.NpcAPI;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.delaware.Main;
import org.delaware.tools.CC;
import org.delaware.tools.General;

import static org.delaware.commands.DBItemsCommand.isDBItem;
import static org.delaware.events.PlayerInteract.armorCompleted;

public class PlayerJoinEvent implements Listener {
    private String welcomeStaff = "---------------------------------------------";

    @EventHandler
    public void onPlayerJoin ( org.bukkit.event.player.PlayerJoinEvent event ) {
        messageJoin ( event.getPlayer () );
        ItemStack[] items = event.getPlayer ( ).getInventory ( ).getArmorContents ( );
        int armorCount = 0;
        for (ItemStack item : items) {
            if (item != null && isDBItem ( item )) {
                armorCount++;
            }
        }
        IDBCPlayer idbcPlayer = NpcAPI.Instance ( ).getPlayer ( event.getPlayer ( ).getName ( ) )
                .getDBCPlayer ( );
        armorCompleted.put ( event.getPlayer ( ).getName ( ), armorCount );
    }
    public void messageJoin(Player player){
        int messageWidth = welcomeStaff.length();
        int spacesToAdd = (int) ((60 - messageWidth) / 2);
        String centeredMessage01 = new String(new char[spacesToAdd]).replace("\0", " ");
        if (General.hasStaffParent ( player)){
            Main.instance.getServer().broadcastMessage(CC.translate("&6&l&m---------------------------------------------"));
            Main.instance.getServer().broadcastMessage("");
            Main.instance.getServer().broadcastMessage(CC.translate("&e☀ &6&l¡ATENCIÓN! &e☀"));
            Main.instance.getServer().broadcastMessage(CC.translate("&eEl staff &6" + player.getName() + " &eha entrado al servidor."));
            Main.instance.getServer().broadcastMessage(CC.translate("&e¡Todos denle una cálida bienvenida! 🎉"));
            Main.instance.getServer().broadcastMessage("");
            Main.instance.getServer().broadcastMessage(CC.translate("&6&l&m---------------------------------------------"));
            for(Player playerOnline : player.getServer().getOnlinePlayers()){
                playerOnline.playSound(playerOnline.getLocation(), Sound.WITHER_DEATH,10f,5f);
            }
        }else if(General.isHakaishin ( player )){
            Main.instance.getServer().broadcastMessage(CC.translate("&5&l&m---------------------------------------------"));
            Main.instance.getServer().broadcastMessage("");
            Main.instance.getServer().broadcastMessage(CC.translate("&d⚡ &5&l¡UN DIOS HA DESCENDIDO! &d⚡"));
            Main.instance.getServer().broadcastMessage(CC.translate("&dEl poderoso &5&lHakaishin &d" + player.getName() + " ha ingresado al servidor."));
            Main.instance.getServer().broadcastMessage(CC.translate("&d¡Inclínense ante su presencia y sientan su poder divino!"));
            Main.instance.getServer().broadcastMessage("");
            Main.instance.getServer().broadcastMessage(CC.translate("&5&l&m---------------------------------------------"));
            for(Player playerOnline : player.getServer().getOnlinePlayers()){
                playerOnline.playSound(playerOnline.getLocation(), Sound.WITHER_DEATH,10f,5f);
            }
        }
    }
}
