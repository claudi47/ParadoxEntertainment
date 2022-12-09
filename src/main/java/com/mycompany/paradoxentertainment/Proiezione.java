package com.mycompany.paradoxentertainment;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

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
    private List<Biglietto> bigliettiVenduti;

    public Proiezione(int idProiezione, Sala sala, Pellicola pellicola, LocalTime orario) {
        this.idProiezione = idProiezione;
        this.sala = sala;
        this.pellicola = pellicola;
        this.orario = orario;
        this.postiRimanentiTot = sala.getPostiTot();
        this.postiRimanentiStandard = sala.getPostiStandard();
        this.postiRimanentiVIP = sala.getPostiVIP();
        this.bigliettiVenduti = new ArrayList();
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

    public void setPostiRimanentiTot(int postiRimanentiTot) {
        this.postiRimanentiTot = postiRimanentiTot;
    }

    public void setPostiRimanentiStandard(int postiRimanentiStandard) {
        this.postiRimanentiStandard = postiRimanentiStandard;
    }

    public void setPostiRimanentiVIP(int postiRimanentiVIP) {
        this.postiRimanentiVIP = postiRimanentiVIP;
    }

    public List<Biglietto> getBigliettiVenduti() {
        return bigliettiVenduti;
    }
    
    public void modificaProiezione(Pellicola pellicola, Sala sala, LocalTime orario) {
        this.pellicola = pellicola;
        this.sala = sala;
        this.orario = orario;
        this.postiRimanentiTot = sala.getPostiTot();
        this.postiRimanentiStandard = sala.getPostiStandard();
        this.postiRimanentiVIP = sala.getPostiVIP();
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
    
    public float creaBiglietto(boolean isVIP, boolean isCategoriaProtetta) {
        bigliettoCorrente = TicketFactory.creaBiglietto(isVIP, isCategoriaProtetta, this);
        System.out.println("\nRiepilogo acquisto: \n" + bigliettoCorrente.toString());
        return bigliettoCorrente.prezzoTot;        
    }
    
    public void confermaAcquisto() {
        bigliettiVenduti.add(bigliettoCorrente);
        System.out.println("\nPROIEZIONE: biglietto aggiunto all'elenco\n");
        if(bigliettoCorrente.isVIP) {
            this.postiRimanentiVIP--;
            System.out.println("\nPROIEZIONE: ho tolto 1 posto VIP, adesso sono " +  postiRimanentiVIP + "\n");
        }
        else {
            this.postiRimanentiStandard--;
            System.out.println("\nPROIEZIONE: ho tolto 1 posto standard, adesso sono: " + postiRimanentiStandard + "\n");
        }
        this.postiRimanentiTot--;
        System.out.println("\nPROIEZIONE: ho tolto 1 posto totale, adesso sono: " + postiRimanentiTot + "\n");
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
