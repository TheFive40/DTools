package org.delaware.DBCEvents;

import kamkeel.npcdbc.api.event.IDBCEvent;
import noppes.npcs.api.IDamageSource;
import noppes.npcs.api.entity.IPlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class DBCKnockoutEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final IDamageSource damageSource;
    private final IDBCEvent.DBCKnockout event;
    private final IPlayer player;
    public DBCKnockoutEvent(IDBCEvent.DBCKnockout event) {
        this.event = event;
        this.damageSource = event.getDamageSource();
        this.player = event.getPlayer();
    }
    @Override
    public HandlerList getHandlers() {
        return null;
    }
    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
