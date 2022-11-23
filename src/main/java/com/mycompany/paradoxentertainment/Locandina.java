package com.mycompany.paradoxentertainment;

/**
 *
 * @author Daniele
 */
public class Locandina {
    private String path;
    private int baseStampa;
    private int altezzaStampa;
    private int pellicola;

    public Locandina(String path, int baseStampa, int altezzaStampa, int idPellicola) {
        this.path = path;
        this.baseStampa = baseStampa;
        this.altezzaStampa = altezzaStampa;
        this.pellicola = idPellicola;
    }

    @Override
    public String toString() {
        return " - Locandina della pellicola n°" + pellicola + ":"
                + "\n\t - Percorso: " + path
                + "\n\t - Dimensione base: " + baseStampa 
                + "\n\t - Dimensione altezza: " + altezzaStampa;
    } 

    public int getPellicola() {
        return pellicola;
    }
    
    
}
