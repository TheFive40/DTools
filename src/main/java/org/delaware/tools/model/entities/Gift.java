package org.delaware.tools.model.entities;

import org.bukkit.Location;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Gift implements Serializable {
    private ArrayList<Localizaciones> itemStackHashMap = new ArrayList<>();

    private HashMap<String, Integer> contadorRegalos = new HashMap<>();

    private HashMap<String, ArrayList<Localizaciones>> regalosEncontrados = new HashMap<>();

    private ArrayList<String> misionCompletada = new ArrayList<>();

    public Gift(ArrayList<Localizaciones> itemStackHashMap, HashMap<String, Integer> contadorRegalos, HashMap<String, ArrayList<Localizaciones>> regalosEncontrados, ArrayList<String> misionCompletada) {
        this.itemStackHashMap = itemStackHashMap;
        this.contadorRegalos = contadorRegalos;
        this.regalosEncontrados = regalosEncontrados;
        this.misionCompletada = misionCompletada;
    }

    public ArrayList<Localizaciones> getItemStackHashMap() {
        return itemStackHashMap;
    }

    public HashMap<String, Integer> getContadorRegalos() {
        return contadorRegalos;
    }

    public HashMap<String, ArrayList<Localizaciones>> getRegalosEncontrados() {
        return regalosEncontrados;
    }

    public ArrayList<String> getMisionCompletada() {
        return misionCompletada;
    }
}
