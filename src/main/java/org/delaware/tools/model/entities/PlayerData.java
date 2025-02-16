package org.delaware.tools.model.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter

@NoArgsConstructor
@AllArgsConstructor
public class PlayerData {
    private String name;
    private int WIL;
    private int STR;
    private int DEX;
    private int CON;
    private int MND;
    private int SPI;

    public PlayerData setName ( String name ) {
        this.name = name;
        return this;
    }

    public PlayerData setWIL ( int WIL ) {
        this.WIL = WIL;
        return this;
    }

    public PlayerData setSTR ( int STR ) {
        this.STR = STR;
        return this;
    }

    public PlayerData setDEX ( int DEX ) {
        this.DEX = DEX;
        return this;
    }

    public PlayerData setCON ( int CON ) {
        this.CON = CON;
        return this;
    }

    public PlayerData setMND ( int MND ) {
        this.MND = MND;
        return this;
    }

    public PlayerData setSPI ( int SPI ) {
        this.SPI = SPI;
        return this;
    }

    @Override
    public boolean equals ( Object object ) {
        if (this == object) return true;
        if (!(object instanceof PlayerData)) return false;
        PlayerData that = (PlayerData) object;
        return Objects.equals ( getName ( ), that.getName ( ) );
    }

    @Override
    public int hashCode () {
        return Objects.hashCode ( getName ( ) );
    }
}
