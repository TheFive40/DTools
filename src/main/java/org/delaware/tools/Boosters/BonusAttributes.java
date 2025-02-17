package org.delaware.tools.Boosters;

import noppes.npcs.api.entity.IDBCPlayer;
import noppes.npcs.scripted.NpcAPI;
import org.bukkit.entity.Player;

public class BonusAttributes {
    IDBCPlayer player;
    public BonusAttributes(Player player) {
        this.player = NpcAPI.Instance().getPlayer(player.getName()).getDBCPlayer();
    }
    //STATS: str, dex, con, wil, spi ... bonusID: boost's id (any) ... operation: +, -, *, /, %
    public void addBonus(String stat, String bonusID, String operation, double value) {
        addBonus(stat, bonusID, operation, value, true);
    }
    public void addBonus(String stat, String bonusID, String operation, double value, boolean endOfLine) {
        player.addBonusAttribute(stat, bonusID, operation, value, endOfLine);
    }
    //Clears all the bonuses applied to the specified stat
    public void clearBonus(String stat) {
        player.clearBonusAttribute(stat);
    }
    //Clears all applied bonuses
    public void clearAllBonuses() {
        player.clearBonusAttribute("str");
        player.clearBonusAttribute("dex");
        player.clearBonusAttribute("con");
        player.clearBonusAttribute("wil");
        player.clearBonusAttribute("mnd");
        player.clearBonusAttribute("spi");
    }
}
