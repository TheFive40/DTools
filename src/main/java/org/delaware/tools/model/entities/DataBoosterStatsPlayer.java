package org.delaware.tools.model.entities;

import java.io.Serializable;

public class DataBoosterStatsPlayer implements Serializable {
    private String stats, nombre, displayName;
    private int aumento, tiempo;
    private boolean multiplicador;

    public String getDisplayName() {
        return displayName;
    }

    public DataBoosterStatsPlayer(String stats, String nombre, int aumento, int tiempo, boolean multiplicador,
                                  String displayName) {
        this.stats = stats;
        this.nombre = nombre;
        this.aumento = aumento;
        this.tiempo = tiempo;
        this.multiplicador = multiplicador;
        this.displayName = displayName;
    }

    public String getStats() {
        return stats;
    }

    public void setStats(String stats) {
        this.stats = stats;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getAumento() {
        return aumento;
    }

    public void setAumento(int aumento) {
        this.aumento = aumento;
    }

    public int getTiempo() {
        return tiempo;
    }

    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }

    public boolean isMultiplicador() {
        return multiplicador;
    }

    public void setMultiplicador(boolean multiplicador) {
        this.multiplicador = multiplicador;
    }
}
