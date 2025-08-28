package org.delaware.tools.Boosters;

import JinRyuu.JRMCore.ComJrmcStatusEffect;
import kamkeel.npcdbc.api.IDBCAddon;
import kamkeel.npcdbc.constants.Effects;
import kamkeel.npcs.addon.DBCAddon;
import noppes.npcs.api.INbt;
import noppes.npcs.api.entity.IDBCPlayer;
import noppes.npcs.controllers.CustomEffectController;
import noppes.npcs.scripted.NpcAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.delaware.Main;
import org.delaware.tools.CC;
import org.delaware.tools.CustomItems.CustomItems;
import org.delaware.tools.General;
import org.delaware.tools.Multipliers.*;

import java.util.Collection;
import java.util.Collections;

import static org.delaware.tools.General.*;

public class BonusAttributes {
    //0: Human
    //1: Saiyan
    //2: Semi-saiyan
    //3: Namek
    //4: Arcosian
    //5: Majin
    IDBCPlayer player;
    INbt nbt;

    public BonusAttributes ( Player player ) {

        this.player = NpcAPI.Instance ( ).getPlayer ( player.getName ( ) ).getDBCPlayer ( );
        this.nbt = NpcAPI.Instance ( ).getPlayer ( player.getName ( ) ).getNbt ( ).getCompound ( "PlayerPersisted" );
    }

    //STATS: str, dex, con, wil, spi ... bonusID: boost's id (any) ... operation: +, -, *, /, %
    public void addBonus ( String stat, String bonusID, String operation, double value ) {
        addBonus ( stat, bonusID, operation, value, true );
    }

    public void addBonus ( String stat, String bonusID, String operation, double value, boolean endOfLine ) {
        if (operation.equals ( "+" ) || operation.equals ( "-" )) {
            IDBCAddon cnpcPlayer = (IDBCAddon) player;
            int race = player.getRace ( );
            if (stat.equalsIgnoreCase ( "CON" ) || stat.equalsIgnoreCase ( "SPI" )) {
                bonus ( stat, bonusID, operation, Math.floor ( value * getMultiplierByStat ( stat.toUpperCase ( ), race ) ), endOfLine );
            } else {
                bonus ( stat, bonusID, operation, Math.floor ( value * getMultiplierByStat ( stat.toUpperCase ( ), race ) * cnpcPlayer.getCurrentFormMultiplier ( ) ), endOfLine );
            }
            return;
        }
        bonus ( stat, bonusID, operation, value, endOfLine );
    }

    private void bonus ( String stat, String bonusID, String operation, double value, boolean endOfLine ) {
        if (hasSpecificBonus ( stat, bonusID )) {
            if (!nbt.getString ( "jrmcAttrBonus" + stat ).contains ( String.valueOf ( value ) )) {
                player.setBonusAttribute ( stat, bonusID, operation, value );
            }
            return;
        }
        player.addBonusAttribute ( stat, bonusID, operation, value, endOfLine );
    }

    //Clears all the bonuses applied to the specified stat
    public void clearBonus ( String stat ) {
        player.clearBonusAttribute ( stat );
    }

    //Removes an specific bonus
    public void clearBonus ( String stat, String bonusID ) {
        player.removeBonusAttribute ( stat, bonusID );
    }

    //Clears all applied bonuses
    public void clearAllBonuses () {
        nbt.remove ( "jrmcAttrBonusstr" );
        nbt.remove ( "jrmcAttrBonusdex" );
        nbt.remove ( "jrmcAttrBonuscon" );
        nbt.remove ( "jrmcAttrBonuswil" );
        nbt.remove ( "jrmcAttrBonusmnd" );
        nbt.remove ( "jrmcAttrBonusspi" );
    }

    //Checks for any bonus in the specified stat (regardless of bonusID)
    public boolean hasBonus ( String stat ) {
        return nbt.has ( "jrmcAttrBonus" + stat );
    }

    //Checks if certain stat has an specific bonus
    public boolean hasSpecificBonus ( String stat, String bonusID ) {
        if (!hasBonus ( stat )) return false;
        return nbt.getString ( "jrmcAttrBonus" + stat ).contains ( bonusID );
    }

    public void setCustomEffect ( int effectID, int duration, byte level ) {
        CustomEffectController effectController = CustomEffectController.getInstance ( );
        IDBCPlayer idbcPlayer = NpcAPI.Instance ( ).getPlayer ( player.getName ( ) ).getDBCPlayer ( );
        Player player1 = Main.instance.getServer ( ).getPlayer ( idbcPlayer.getName ( ) );
        ItemStack chestplate = player1.getInventory ( ).getChestplate ( );
        ItemStack leggings = player1.getInventory ( ).getLeggings ( );
        ItemStack boots = player1.getInventory ( ).getBoots ( );
        double multiplierBonus = 1.0;
        double multiplierBonusCON = 1.0;

        if (chestplate != null && leggings != null && boots != null) {
            CustomItems cItem1 = CustomItems.getFromNbt ( chestplate );
            CustomItems cItem2 = CustomItems.getFromNbt ( leggings );
            CustomItems cItem3 = CustomItems.getFromNbt ( boots );
            if (cItem1.hasCustomBoost ( ) && cItem2.hasCustomBoost ( ) && cItem3.hasCustomBoost ( )) {
                int indexChest = Collections.binarySearch ( cItem1.getStats ( ), "spi" );
                int indexLeg = Collections.binarySearch ( cItem2.getStats ( ), "spi" );
                int indexBoot = Collections.binarySearch ( cItem3.getStats ( ), "spi" );

                if (indexChest >= 0 && cItem1.getOperations ( ).get ( indexChest ).contains ( "*" )) {
                    multiplierBonus = cItem1.getValues ( ).get ( indexChest );
                } else if (indexLeg >= 0 && cItem2.getOperations ( ).get ( indexLeg ).contains ( "*" )) {
                    multiplierBonus = cItem2.getValues ( ).get ( indexLeg );
                } else if (indexBoot >= 0 && cItem3.getOperations ( ).get ( indexBoot ).contains ( "*" )) {
                    multiplierBonus = cItem3.getValues ( ).get ( indexBoot );
                }

               /* indexChest = Collections.binarySearch(cItem1.getStats(), "con");
                indexLeg = Collections.binarySearch(cItem2.getStats(), "con");
                indexBoot = Collections.binarySearch(cItem3.getStats(), "con");

                if (indexChest >= 0 && cItem1.getOperations().get(indexChest).contains("*")) {
                    multiplierBonusCON = cItem1.getValues().get(indexChest);
                } else if (indexLeg >= 0 && cItem2.getOperations().get(indexLeg).contains("*")) {
                    multiplierBonusCON = cItem2.getValues().get(indexLeg);
                } else if (indexBoot >= 0 && cItem3.getOperations().get(indexBoot).contains("*")) {
                    multiplierBonusCON = cItem3.getValues().get(indexBoot);
                }*/
            }
        }
        if (Effects.REGEN_KI == effectID) {
            int kiMax = General.getKiMax ( idbcPlayer, multiplierBonus );
            int bonus = (int) (((double) level / 100.0) * kiMax);
            idbcPlayer.setKi ( idbcPlayer.getKi ( ) + bonus );
        }
        if (Effects.REGEN_STAMINA == effectID) {
            int bonus = (int) (((double) level / 100.0) * idbcPlayer.getStamina ( ));
            idbcPlayer.setStamina ( idbcPlayer.getStamina ( ) + bonus );
        }
        if (Effects.REGEN_HEALTH == effectID) {
            int maxHealth = General.getMaxHealth ( idbcPlayer, multiplierBonusCON );
            int bonus = (int) (((double) level / 100.0) * maxHealth);
            idbcPlayer.setHP ( idbcPlayer.getHP ( ) + bonus );
        }
        if (Effects.REGEN_KI == effectID)
            spawnHologramKI ( Main.instance.getServer ( ).getPlayer ( player.getName ( ) ), "&b✦" );
        else if (Effects.REGEN_STAMINA == effectID)
            spawnHologramSTAM ( Main.instance.getServer ( ).getPlayer ( player.getName ( ) ), "&e❃" );
        else if (Effects.REGEN_HEALTH == effectID)
            spawnHologramCON ( Main.instance.getServer ( ).getPlayer ( player.getName ( ) ), "&c❤" );
    }

    public boolean hasAnyBonus () {
        String[] stats = {"str", "dex", "con", "wil", "mnd", "spi"};
        for (String stat : stats) {
            if (hasBonus ( stat )) return true;
        }
        return false;
    }

    private double getMultiplierByStat ( String stat, int race ) {
        switch (race) {
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
