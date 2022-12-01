package com.mycompany.paradoxentertainment;
import java.time.LocalTime;

/**
 *
 * @author Daniele
 */
public class Proiezione {
    private int idProiezione;
    private Sala sala;
    private Pellicola pellicola;
    private LocalTime orario;
    private int postiRimanentiTot;
    private int postiRimanentiStandard;
    private int postiRimanentiVIP;
    private Biglietto bigliettoCorrente, bigliettoSelezionato;

    public Proiezione(int idProiezione, Sala sala, Pellicola pellicola, LocalTime orario) {
        this.idProiezione = idProiezione;
        this.sala = sala;
        this.pellicola = pellicola;
        this.orario = orario;
        this.postiRimanentiTot = sala.getPostiTot();
        this.postiRimanentiStandard = sala.getPostiStandard();
        this.postiRimanentiVIP = sala.getPostiVIP();
    }

    public int getIdProiezione() {
        return idProiezione;
    }

    public Sala getSala() {
        return sala;
    }

    public Pellicola getPellicola() {
        return pellicola;
    }

    public LocalTime getOrario() {
        return orario;
    }

    public int getPostiRimanentiTot() {
        return postiRimanentiTot;
    }

    public int getPostiRimanentiStandard() {
        return postiRimanentiStandard;
    }

    public int getPostiRimanentiVIP() {
        return postiRimanentiVIP;
    }
    
    public int creaBiglietto(boolean isVIP, boolean isCategoriaProtetta) {
        return 0;
    }
    
    public String stampaProiezioneConSala() {
        return "Proiezione ID: " + idProiezione + 
                "\n - Pellicola: " + pellicola.getNomePellicola() + 
                "\n - Sala: " + sala.getNomeSala() + 
                "\n - Orario: " + orario + 
                "\n - Posti totali rimanenti: " + postiRimanentiTot + 
                "\n - Posti standard rimanenti: " + postiRimanentiStandard + 
                "\n - Posti VIP rimanenti: " + postiRimanentiVIP;
    }
    
    public String stampaElenco() {
        return " - " + orario + 
                ", Pellicola: " + pellicola.getNomePellicola() + 
                ", Durata: " + pellicola.getDurata() +
                ", Posti standard: " + postiRimanentiStandard + 
                ", Posti VIP: " + postiRimanentiVIP;
    }
    
    @Override
    public String toString() {
        return "Proiezione ID: " + idProiezione + 
                "\n - Pellicola: " + pellicola.getNomePellicola() + 
                "\n - Orario: " + orario + 
                "\n - Posti totali rimanenti: " + postiRimanentiTot + 
                "\n - Posti standard rimanenti: " + postiRimanentiStandard + 
                "\n - Posti VIP rimanenti: " + postiRimanentiVIP;
    }
}
