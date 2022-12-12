package com.mycompany.paradoxentertainment;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
    private List<Biglietto> bigliettiRimborsati;
    BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));

    public Proiezione(int idProiezione, Sala sala, Pellicola pellicola, LocalTime orario) {
        this.idProiezione = idProiezione;
        this.sala = sala;
        this.pellicola = pellicola;
        this.orario = orario;
        this.postiRimanentiTot = sala.getPostiTot();
        this.postiRimanentiStandard = sala.getPostiStandard();
        this.postiRimanentiVIP = sala.getPostiVIP();
        this.bigliettiVenduti = new ArrayList();
        this.bigliettiRimborsati = new ArrayList();
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
    
    public Biglietto getBigliettoVenduto(int idBiglietto) {
        for(Biglietto B: bigliettiVenduti) {
            if(B.getIdBiglietto() == idBiglietto) {
                System.out.println("\nBiglietto trovato relativo alla proiezione ID = " + idProiezione + ":\n" + B.toString());
                return B;
            }
        }
        return null;
    }
    
    public void stampaBigliettiVenduti() {
        if(!bigliettiVenduti.isEmpty()) {
            for(Biglietto B: bigliettiVenduti) {
                System.out.println(B.toString());
            }
        }
    }
    
    public float creaBiglietto(boolean isVIP, boolean isCategoriaProtetta) {
        bigliettoCorrente = TicketFactory.creaBiglietto(isVIP, isCategoriaProtetta, this);
        System.out.println("\nRiepilogo acquisto: \n" + bigliettoCorrente.toString());
        return bigliettoCorrente.getPrezzoTot();        
    }
    
    public void confermaAcquisto() {
        bigliettiVenduti.add(bigliettoCorrente);
        System.out.println("\nPROIEZIONE: biglietto aggiunto all'elenco\n");
        if(bigliettoCorrente.getIsVIP()) {
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
    
    public Biglietto trovaBigliettoVenduto() throws IOException {
        String inserimentoBiglietto;
        int idBiglietto;
        
        if(!bigliettiVenduti.isEmpty()) {
            do {
                System.out.println("\nInserire l'ID del biglietto venduto o premere Invio per stamparli tutti:");
                inserimentoBiglietto = bf.readLine();
                if(inserimentoBiglietto.equals("")) {
                    stampaBigliettiVenduti();
                    System.out.println("\nInserisci l'ID del biglietto venduto: ");
                    inserimentoBiglietto = bf.readLine();
                }

                idBiglietto = Integer.parseInt(inserimentoBiglietto);
                if(getBigliettoVenduto(idBiglietto) != null) 
                    return getBigliettoVenduto(idBiglietto);
                else
                    System.out.println("\nBiglietto non trovato: inserire 1 per riprovare, altrimenti per annullare");
            } while(bf.readLine().equals("1"));
            return null;
        }
        System.out.println("\nNon risultano biglietti venduti per la proiezione " + this.getIdProiezione() + " nel sistema");
        return null;
    }
    
    public boolean effettuaReso() throws IOException {
        boolean isBigliettoTrovato = false;
        
        bigliettoSelezionato = trovaBigliettoVenduto();
        
        if(bigliettoSelezionato != null) {
            System.out.println("\nBiglietto selezionato: \n" + bigliettoSelezionato.toString() + 
                    "\nInserire 1 per confermare e proseguire, altrimenti per selezionare un altro biglietto");
            if(!bf.readLine().equals("1"))
                this.effettuaReso();
            else 
                isBigliettoTrovato = true;
        } else
            System.out.println("Operazione di rimborso annullata\n");
        return isBigliettoTrovato;
    }
    
    public float confermaReso() {
        bigliettiRimborsati.add(bigliettoSelezionato);
        bigliettiVenduti.remove(bigliettoSelezionato);
        if(bigliettoSelezionato.getIsVIP())
            this.postiRimanentiVIP++;
        else
            this.postiRimanentiStandard++;
        this.postiRimanentiTot++;
        return bigliettoSelezionato.getPrezzoTot();
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
