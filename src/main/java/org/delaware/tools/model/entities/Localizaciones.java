package org.delaware.tools.model.entities;

import java.io.Serializable;

public class Localizaciones implements Serializable {
    private int bloqueX;
    private int bloqueY;
    private int bloqueZ;
    private String world;

    public Localizaciones(int bloqueX, int bloqueY, int bloqueZ, String world) {
        this.bloqueX = bloqueX;
        this.bloqueY = bloqueY;
        this.bloqueZ = bloqueZ;
        this.world = world;
    }

    public int getBloqueX() {
        return bloqueX;
    }

    public int getBloqueY() {
        return bloqueY;
    }

    public int getBloqueZ() {
        return bloqueZ;
    }

    public String getWorld() {
        return world;
    }
}
