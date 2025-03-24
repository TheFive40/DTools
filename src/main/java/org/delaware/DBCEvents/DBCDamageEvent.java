package org.delaware.DBCEvents;

import kamkeel.npcdbc.api.event.IDBCEvent;
import lombok.Getter;
import noppes.npcs.api.IDamageSource;
import noppes.npcs.api.entity.IPlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
public class DBCDamageEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final float damage;
    private final IDamageSource damageSource;
    private final float type;
    private final boolean isDamageSourceKiAttack;
    private final IDBCEvent.DamagedEvent event;
    private final IPlayer<?> player;
    public DBCDamageEvent(IDBCEvent.DamagedEvent event) {
        this.player = event.getPlayer();
        this.damage = event.getDamage();
        this.damageSource = event.getDamageSource();
        this.type = event.getType();
        this.isDamageSourceKiAttack = event.isDamageSourceKiAttack();
        this.event = event;
    }
    public void setDamage(float dmg) {
        this.event.setDamage(dmg);
    }
    public void setCancelled(boolean cancel) {
        if(cancel) setDamage(0);
    }
    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
