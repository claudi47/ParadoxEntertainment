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
        return "Locandina della pellicola n°" + pellicola + ":"
                + "\n - Percorso: " + path
                + "\n - Dimensione base: " + baseStampa 
                + "\n - Dimensione altezza: " + altezzaStampa;
    } 

    public int getPellicola() {
        return pellicola;
    }
    
    
}
