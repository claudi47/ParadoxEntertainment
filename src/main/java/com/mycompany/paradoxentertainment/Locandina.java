package com.mycompany.paradoxentertainment;

/**
 *
 * @author Daniele
 */
public class Locandina {
    private String path;
    private int baseStampa;
    private int altezzaStampa;

    public Locandina(String path, int baseStampa, int altezzaStampa) {
        this.path = path;
        this.baseStampa = baseStampa;
        this.altezzaStampa = altezzaStampa;
    }

    @Override
    public String toString() {
        return "Locandina" + "\n - Percorso: " + path + "\n - Dimensione base: " + baseStampa + "\n - Dimensione altezza: " + altezzaStampa;
    } 
}
