package com.mycompany.paradoxentertainment;

import java.io.Serializable;

/**
 *
 * @author Daniele
 */
public class Locandina implements Serializable {
    private String path;
    private int baseStampa;
    private int altezzaStampa;
    private Pellicola pellicola;

    public Locandina(String path, int baseStampa, int altezzaStampa, Pellicola pellicola) {
        this.path = path;
        this.baseStampa = baseStampa;
        this.altezzaStampa = altezzaStampa;
        this.pellicola = pellicola;
    }

    @Override
    public String toString() {
        return "Locandina della pellicola n°" + pellicola.getIdPellicola() + ", " + pellicola.getNomePellicola() + ":"
                + "\n - Percorso: " + path
                + "\n - Dimensione base: " + baseStampa 
                + "\n - Dimensione altezza: " + altezzaStampa;
    } 

    public int getPellicola() {
        return pellicola.getIdPellicola();
    }
}
