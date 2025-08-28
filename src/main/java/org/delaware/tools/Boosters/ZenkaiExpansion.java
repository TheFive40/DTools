package org.delaware.tools.Boosters;

import kamkeel.npcdbc.constants.DBCRace;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import noppes.npcs.api.entity.IDBCPlayer;
import noppes.npcs.scripted.NpcAPI;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.delaware.Main;
import org.delaware.commands.GlobalBoosterCommand;
import org.delaware.tools.BankManager;
import org.delaware.tools.General;
import java.util.List;

import static net.minecraftforge.oredict.RecipeSorter.getCategory;

public class ZenkaiExpansion extends PlaceholderExpansion {
    @Override
    public  String getIdentifier () {
        return "zenkai";
    }

    @Override
    public  String getAuthor () {
        return "DelawareX";
    }

    @Override
    public  String getVersion () {
        return "1.0";
    }

    @Override
    public boolean persist () {
        return true;
    }

    @Override
    public boolean canRegister () {
        return true;
    }

    @Override
    public String onRequest(OfflinePlayer offline, String params) {
        if (offline == null) return "";

        Player player = offline.getPlayer();
        if (player == null && !params.equals("online") && !params.startsWith("booster")) {
            return "";
        }

        switch (params) {
            case "level":
                return String.valueOf(getLevel(player));
            case "tps":
                return String.valueOf(getTps(player));
            case "bank_balance":
                return String.valueOf(getBankBalance(player));
            case "online":
                return Bukkit.getServer().getOnlinePlayers().length + "";
            case "booster-global-multiplier":
                return getGlobalBoosterMultiplier();
            case "booster-global-remaining":
                return getBoosterRemainingTime();
            case "race":
                return getRace(player);
            case "category":
                return General.getRankColorCode (player);
            default:
                return null;
        }
    }


    private String getRace ( Player player ) {
        IDBCPlayer dbcPlayer = NpcAPI.Instance ( ).getPlayer ( player.getName ( ) ).getDBCPlayer ( );
        switch (dbcPlayer.getRace ( )) {
            case DBCRace.HALFSAIYAN:
                return "Semi-Saiyan";
            case DBCRace.ARCOSIAN:
                return "Arcosiano";
            case DBCRace.MAJIN:
                return "Majin";
            case DBCRace.HUMAN:
                return "Humano";
            case DBCRace.SAIYAN:
                return "Saiyan";
            case DBCRace.NAMEKIAN:
                return "Namekiano";
        }
        return "N/A";
    }

    private int getLevel ( Player player ) {
        return General.getLVL ( player );
    }

    private int getTps ( Player player ) {
        return General.getPlayerTps ( player );
    }

    private int getBankBalance ( Player player ) {
        return (int) BankManager.getBalance ( player.getName ( ) );
    }

    private String getGlobalBoosterMultiplier () {
        return GlobalBoosterCommand.getBoosterMultiplierPercent ( );
    }

    private String getBoosterRemainingTime () {
        return GlobalBoosterCommand.getBoosterRemainingTime ( );
    }
}
