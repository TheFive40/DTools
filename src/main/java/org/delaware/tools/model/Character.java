package org.delaware.tools.model;

import org.bukkit.inventory.Inventory;

public class Character {
    private String name;
    private String UUID;
    private int con, dex, str,wil,mnd,spi;
    private Boolean inUse = false;
    private int level;

    public Character(String name, String UUID, int con, int dex, int str, int wil, int mnd, int spi, int tps) {
        this.name = name;
        this.UUID = UUID;
        this.con = con;
        this.dex = dex;
        this.str = str;
        this.wil = wil;
        this.mnd = mnd;
        this.spi = spi;
        this.tps = tps;
        this.level = (con+dex+str+wil+mnd+spi)/5-11;
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
}
