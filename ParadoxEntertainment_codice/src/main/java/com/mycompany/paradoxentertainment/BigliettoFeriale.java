package com.mycompany.paradoxentertainment;

public class BigliettoFeriale extends Biglietto {
    float prezzoBase = 6.0F;
    
    public BigliettoFeriale(boolean isVIP, boolean isCategoriaProtetta, Proiezione proiezione) {
        super(isVIP, isCategoriaProtetta, proiezione);
        this.prezzoTot = prezzoBase;
        if(isVIP)
            prezzoTot += 1.0;
        if(isCategoriaProtetta)
            prezzoTot -= 2.0;
    }
    
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(super.toString()).append("\n - Costo: ").append(prezzoTot);
        return s.toString();
    } 
}
