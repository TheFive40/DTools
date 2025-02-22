package org.delaware.tools.Boosters;

import kamkeel.npcdbc.controllers.StatusEffectController;
import noppes.npcs.api.INbt;
import noppes.npcs.api.entity.IDBCPlayer;
import noppes.npcs.scripted.NpcAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class BonusAttributes {
    IDBCPlayer player;
    INbt nbt;
    public BonusAttributes(Player player) {
        this.player = NpcAPI.Instance().getPlayer(player.getName()).getDBCPlayer();
        this.nbt = NpcAPI.Instance().getPlayer(player.getName()).getNbt().getCompound("PlayerPersisted");
    }
    //STATS: str, dex, con, wil, spi ... bonusID: boost's id (any) ... operation: +, -, *, /, %
    public void addBonus(String stat, String bonusID, String operation, double value) {
        addBonus(stat, bonusID, operation, value, true);
    }
    public void addBonus(String stat, String bonusID, String operation, double value, boolean endOfLine) {
        if(hasSpecificBonus(stat, bonusID)) {
            if (!nbt.getString("jrmcAttrBonus" + stat).contains(String.valueOf(value))) {
                player.setBonusAttribute(stat, bonusID, operation, value);
            }
            return;
        }
        player.addBonusAttribute(stat, bonusID, operation, value, endOfLine);
    }
    //Clears all the bonuses applied to the specified stat
    public void clearBonus(String stat) {
        player.clearBonusAttribute(stat);
    }
    //Removes an specific bonus
    public void clearBonus(String stat, String bonusID) {
        player.removeBonusAttribute(stat, bonusID);
    }
    //Clears all applied bonuses
    public void clearAllBonuses() {
        nbt.remove("jrmcAttrBonusstr");
        nbt.remove("jrmcAttrBonusdex");
        nbt.remove("jrmcAttrBonuscon");
        nbt.remove("jrmcAttrBonuswil");
        nbt.remove("jrmcAttrBonusmnd");
        nbt.remove("jrmcAttrBonusspi");
    }
    //Checks for any bonus in the specified stat (regardless of bonusID)
    public boolean hasAnyBonus(String stat) {
        return nbt.has("jrmcAttrBonus" + stat);
    }
    //Checks if certain stat has an specific bonus
    public boolean hasSpecificBonus(String stat, String bonusID) {
        if(!hasAnyBonus(stat)) return false;
        return nbt.getString("jrmcAttrBonus" + stat).contains(bonusID);
    }
    public void setCustomEffect(int effectID, int duration, byte level) {
        StatusEffectController.getInstance().applyEffect(NpcAPI.Instance().getPlayer(player.getName()), effectID, duration, level);
    }
    public boolean hasBonus() {
        String[] stats = {"str", "dex", "con", "wil", "mnd", "spi"};
        for(String stat : stats) {
            if(hasAnyBonus(stat)) return true;
        }
        return false;
    }
}
