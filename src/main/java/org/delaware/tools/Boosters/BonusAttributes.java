package org.delaware.tools.Boosters;

import kamkeel.npcdbc.api.IDBCAddon;
import kamkeel.npcdbc.controllers.StatusEffectController;
import noppes.npcs.api.INbt;
import noppes.npcs.api.entity.IDBCPlayer;
import noppes.npcs.scripted.NpcAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.delaware.tools.Multipliers.*;

public class BonusAttributes {
    //0: Human
    //1: Saiyan
    //2: Semi-saiyan
    //3: Namek
    //4: Arcosian
    //5: Majin
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
        if(operation.equals("+") || operation.equals("-")) {
            IDBCAddon cnpcPlayer = (IDBCAddon) player;
            int race = player.getRace();
            if(stat.equals("CON") || stat.equals("SPI")) {
                bonus(stat, bonusID, operation, Math.floor(value * getMultiplierByStat(stat.toUpperCase(), race)), endOfLine);
            }
            else {
                bonus(stat, bonusID, operation, Math.floor(value * getMultiplierByStat(stat.toUpperCase(), race) * cnpcPlayer.getCurrentFormMultiplier()), endOfLine);
            }
            return;
        }
        bonus(stat, bonusID, operation, value, endOfLine);
    }
    private void bonus(String stat, String bonusID, String operation, double value, boolean endOfLine) {
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
    public boolean hasBonus(String stat) {
        return nbt.has("jrmcAttrBonus" + stat);
    }
    //Checks if certain stat has an specific bonus
    public boolean hasSpecificBonus(String stat, String bonusID) {
        if(!hasBonus(stat)) return false;
        return nbt.getString("jrmcAttrBonus" + stat).contains(bonusID);
    }
    public void setCustomEffect(int effectID, int duration, byte level) {
        StatusEffectController effectController = StatusEffectController.getInstance();
        if(effectController.hasEffect(NpcAPI.Instance().getPlayer(player.getName()), effectID))
            effectController.removeEffect(NpcAPI.Instance().getPlayer(player.getName()), effectID);
        effectController.applyEffect(NpcAPI.Instance().getPlayer(player.getName()), effectID, duration, level);
    }
    public boolean hasAnyBonus() {
        String[] stats = {"str", "dex", "con", "wil", "mnd", "spi"};
        for(String stat : stats) {
            if(hasBonus(stat)) return true;
        }
        return false;
    }
    private double getMultiplierByStat(String stat, int race) {
        switch(race) {
            case 0:
                switch (stat) {
                    case "STR":
                        return Human.strMultiplier;
                    case "DEX":
                        return Human.dexMultiplier;
                    case "CON":
                        return Human.conMultiplier;
                    case "WIL":
                        return Human.wilMultiplier;
                    case "SPI":
                        return Human.spiMultiplier;
                }
                break;
            case 1:
                switch (stat) {
                    case "STR":
                        return Saiyan.strMultiplier;
                    case "DEX":
                        return Saiyan.dexMultiplier;
                    case "CON":
                        return Saiyan.conMultiplier;
                    case "WIL":
                        return Saiyan.wilMultiplier;
                    case "SPI":
                        return Saiyan.spiMultiplier;
                }
                break;
            case 2:
                switch (stat) {
                    case "STR":
                        return SemiSaiyan.strMultiplier;
                    case "DEX":
                        return SemiSaiyan.dexMultiplier;
                    case "CON":
                        return SemiSaiyan.conMultiplier;
                    case "WIL":
                        return SemiSaiyan.wilMultiplier;
                    case "SPI":
                        return SemiSaiyan.spiMultiplier;
                }
                break;
            case 3:
                switch (stat) {
                    case "STR":
                        return Namekian.strMultiplier;
                    case "DEX":
                        return Namekian.dexMultiplier;
                    case "CON":
                        return Namekian.conMultiplier;
                    case "WIL":
                        return Namekian.wilMultiplier;
                    case "SPI":
                        return Namekian.spiMultiplier;
                }
                break;
            case 4:
                switch (stat) {
                    case "STR":
                        return Arcosian.strMultiplier;
                    case "DEX":
                        return Arcosian.dexMultiplier;
                    case "CON":
                        return Arcosian.conMultiplier;
                    case "WIL":
                        return Arcosian.wilMultiplier;
                    case "SPI":
                        return Arcosian.spiMultiplier;
                }
                break;
            case 5:
                switch (stat) {
                    case "STR":
                        return Majin.strMultiplier;
                    case "DEX":
                        return Majin.dexMultiplier;
                    case "CON":
                        return Majin.conMultiplier;
                    case "WIL":
                        return Majin.wilMultiplier;
                    case "SPI":
                        return Majin.spiMultiplier;
                }
                break;
        }
        return 1;
    }
}
