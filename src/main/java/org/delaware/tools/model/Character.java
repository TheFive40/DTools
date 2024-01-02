package org.delaware.tools.model;

import org.bukkit.entity.Player;
import org.delaware.tools.DBCClass;
import org.delaware.tools.General;
import org.delaware.tools.Race;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class Character implements Serializable {
    private String name;
    private String UUID;
    private int con, dex, str, wil, mnd, spi;
    private boolean inUse = false;
    private ConcurrentHashMap<String, CopyOnWriteArrayList<Character>> characters;
    private int level;
    private DBCClass dbcClass;
    private Race race;

    public Character(String name, String UUID, int con, int dex, int str, int wil, int mnd, int spi, int tps
            , Race race, DBCClass dbcClass) {
        this.name = name;
        this.UUID = UUID;
        this.con = con;
        this.dex = dex;
        this.str = str;
        this.wil = wil;
        this.mnd = mnd;
        this.spi = spi;
        this.tps = tps;
        this.level = (con + dex + str + wil + mnd + spi) / 5 - 11;
        this.race = race;
        this.dbcClass = dbcClass;
    }

    public Character(ConcurrentHashMap<String, CopyOnWriteArrayList<Character>> characters) {
        this.characters = characters;
    }

    public ConcurrentHashMap<String, CopyOnWriteArrayList<Character>> getCharacters() {
        return characters;
    }

    private int tps;

    public String getName() {
        return name;
    }

    public String getUUID() {
        return UUID;
    }

    public int getCon() {
        return con;
    }

    public int getDex() {
        return dex;
    }

    public int getStr() {
        return str;
    }

    public int getWil() {
        return wil;
    }

    public int getMnd() {
        return mnd;
    }

    public int getSpi() {
        return spi;
    }

    public int getLevel() {
        return level;
    }

    public int getTps() {
        return tps;
    }

    public Boolean isInUse() {
        return inUse;
    }

    public void setInUse(boolean inUse) {
        this.inUse = inUse;
    }

    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    public DBCClass getDbcClass() {
        return dbcClass;
    }

    public void setDbcClass(DBCClass dbcClass) {
        this.dbcClass = dbcClass;
    }

    public static void setPlayerClass(String arg, Player player) {
        DBCClass dbcClass1 = null;
        switch (arg.toUpperCase()) {
            case "WARRIOR":
            case "GUERRERO":
                dbcClass1 = DBCClass.WARRIOR;
                break;
            case "SPIRITUALISTIC":
            case "ESPIRITUALISTA":
                dbcClass1 = DBCClass.SPIRITUALISTIC;
                break;
            case "MARTIAL_ARTIST":
            case "ARTISTA_MARCIAL":
            case "ARTISTA":
                dbcClass1 = DBCClass.MARTIAL_ARTIST;
                break;
        }
        General.setPlayerClass(player, dbcClass1);
    }
}
