package org.delaware.tools.model.entities;

import org.bukkit.Material;

import java.io.Serializable;

public class itemBooster implements Serializable {
    private String name, stats, description, displayName;
    private int time, aumento;
    private boolean esMultiplicador;

    private Material material;

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public String getDisplayName() {
        return displayName;
    }

    public itemBooster(String name, String stats, int time, int aumento, boolean esMultiplicador, Material material,
                       String displayName) {
        this.name = name;
        this.stats = stats;
        this.time = time;
        this.aumento = aumento;
        this.esMultiplicador = esMultiplicador;
        this.material = material;
        this.displayName = displayName;
    }

    public itemBooster(String name, String stats, String description, int time) {
        this.name = name;
        this.stats = stats;
        this.description = description;
        this.time = time;
    }

    public itemBooster(String name, String stats, int time) {
        this.name = name;
        this.stats = stats;
        this.time = time;
    }

    public itemBooster(String name, String stats, int time, int aumento, boolean esMultiplicador) {
        this.name = name;
        this.stats = stats;
        this.time = time;
        this.esMultiplicador = esMultiplicador;
        this.aumento = aumento;
    }

    @Override
    public String toString() {
        return "itemBooster{" +
                "name='" + name + '\'' +
                ", stats='" + stats + '\'' +
                ", description='" + description + '\'' +
                ", time=" + time +
                ", aumento=" + aumento +
                ", esMultiplicador=" + esMultiplicador +
                '}';
    }

    public int getAumento() {
        return aumento;
    }

    public void setAumento(int aumento) {
        this.aumento = aumento;
    }

    public boolean isEsMultiplicador() {
        return esMultiplicador;
    }

    public void setEsMultiplicador(boolean esMultiplicador) {
        this.esMultiplicador = esMultiplicador;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStats() {
        return stats;
    }

    public void setStats(String stats) {
        this.stats = stats;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
