package com.mycompany.paradoxentertainment;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Daniele
 */
public abstract class Biglietto implements Serializable {
    protected float prezzoTot;
    protected boolean isVIP, isCategoriaProtetta;
    protected LocalDate data;
    protected Proiezione proiezione;
    protected int idBiglietto;
    
    protected Biglietto(boolean isVIP, boolean isCategoriaProtetta, Proiezione proiezione) {
        this.proiezione = proiezione;
        this.data = LocalDate.now();
        this.isVIP = isVIP;
        this.isCategoriaProtetta = isCategoriaProtetta;
    }

    public int getIdBiglietto() {
        return idBiglietto;
    }

    public float getPrezzoTot() {
        return prezzoTot;
    }

    public boolean getIsVIP() {
        return isVIP;
    }

    public LocalDate getData() {
        return data;
    }

    public boolean isIsVIP() {
        return isVIP;
    }

    public boolean isIsCategoriaProtetta() {
        return isCategoriaProtetta;
    }

    public Proiezione getProiezione() {
        return proiezione;
    }
    
    public void setIdBiglietto(int idBiglietto) {
        this.idBiglietto = idBiglietto;
    }
    
    @Override
    public String toString() {
        String tipoBiglietto, poltrona;
      
        if(isCategoriaProtetta)
            tipoBiglietto = "Ridotto";
        else tipoBiglietto = "Intero";
        
        if(isVIP)
            poltrona = "VIP";
        else poltrona = "Standard";
        
        return "\nBiglietto ID: " + idBiglietto +
               "\n - Spettacolo: " + proiezione.getPellicola().getNomePellicola() +
               "\n - Sala: " + proiezione.getSala().getNomeSala() +
               "\n - Data: " + data.format( DateTimeFormatter.ofPattern("dd/MM/yyyy")) +
               "\n - Orario: " + proiezione.getOrario() +
               "\n - Tipologia: " + tipoBiglietto + 
               "\n - Poltrona: " + poltrona;
    }
}
