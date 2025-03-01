package org.delaware.events;

import noppes.npcs.api.entity.IDBCPlayer;
import noppes.npcs.scripted.NpcAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.delaware.tools.CC;
import org.delaware.tools.CustomItems.CustomItems;

public class ScytheJoinEvent implements Listener {
    int dmgMultiplier = 5;
    @EventHandler
    public void onJoin(org.bukkit.event.player.PlayerJoinEvent event) {
        Player player = event.getPlayer();
        for(int i = 0; i < player.getInventory().getSize(); i++) {
            ItemStack item = player.getInventory().getItem(i);
            if(item == null) continue;
            if(item.getTypeId() == 4511) {
                player.sendMessage(CC.translate("&1---------------------------------------------------"));
                player.sendMessage(CC.translate("&bTu guadaña ha sido actualizada a tu nivel."));
                player.sendMessage(CC.translate("&bRecuerda que para volverla actualizar, puedes salir y volver a entrar del server."));
                player.sendMessage(CC.translate("&b¡Gracias por tu compra!"));
                player.sendMessage(CC.translate("&1---------------------------------------------------"));
                CustomItems cItem = new CustomItems(item);
                cItem.setDamage((calculateLevel(player)*dmgMultiplier));
                player.getInventory().setItem(i, cItem.toItemStack());
                break;
            }
        }
    }
    private int calculateLevel(Player player) {
        IDBCPlayer dbcPlayer = NpcAPI.Instance().getPlayer(player.getName()).getDBCPlayer();
        int str = dbcPlayer.getStat("str");
        int dex = dbcPlayer.getStat("dex");
        int con = dbcPlayer.getStat("con");
        int wil = dbcPlayer.getStat("wil");
        int mnd = dbcPlayer.getStat("mnd");
        int spi = dbcPlayer.getStat("spi");
        return (((str+dex+con+wil+mnd+spi)/5)-11);
    }
}
