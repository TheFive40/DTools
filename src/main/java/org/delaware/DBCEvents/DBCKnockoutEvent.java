package org.delaware.DBCEvents;

import kamkeel.npcdbc.api.event.IDBCEvent;
import lombok.Getter;
import noppes.npcs.api.IDamageSource;
import noppes.npcs.api.entity.IDBCPlayer;
import noppes.npcs.api.entity.IPlayer;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.scheduler.BukkitRunnable;
import org.delaware.Main;

@Getter
public class DBCKnockoutEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final IDamageSource damageSource;
    private final IDBCEvent.DBCKnockout event;
    private final IPlayer<?> player;
    public DBCKnockoutEvent(IDBCEvent.DBCKnockout event) {
        this.event = event;
        this.damageSource = event.getDamageSource();
        this.player = event.getPlayer();
    }
    public void setCanceled(boolean cancel) {
        if(cancel) {
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "jrmcse set KO 0 " + player.getName());
            startRunnable(player.getDBCPlayer(), event.getPlayer().getDBCPlayer().getRelease());
        }
    }
    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }
    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
    private void startRunnable(IDBCPlayer player, byte release) {
        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {
                player.setRelease(release);
            }
        };
        runnable.runTaskLater(Main.instance, 20);
    }
}
