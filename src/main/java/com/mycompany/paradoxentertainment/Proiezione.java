package com.mycompany.paradoxentertainment;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Daniele
 */
public class Proiezione implements Serializable {
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
    private transient BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
    UpdateSeatsStrategy updateSeatsStrategy;
    private static int idBiglietti = 0;

    public Proiezione(Sala sala, Pellicola pellicola, LocalTime orario) {
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

    public void setIdProiezione(int idProiezione) {
        this.idProiezione = idProiezione;
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
    
    public void stampaBigliettoVenduto(int idBiglietto) {
        if(getBigliettoVenduto(idBiglietto) != null) {
            getBigliettoVenduto(idBiglietto).toString();
        } else
            System.out.println("\nErrore: biglietto non trovato nel sistema\n");   
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
    
    public void aggiornaPosti(Proiezione proiezione, boolean isVIP) {
        updateSeatsStrategy.aggiornaPosti(this, isVIP);
    }
    
    public void confermaAcquisto(boolean isVIP) {
        bigliettoCorrente.setIdBiglietto(++idBiglietti);
        bigliettiVenduti.add(bigliettoCorrente);
        try { 
            salvaSuFile();
        } catch(IOException e) {
            System.err.println("\nErrore nel salvataggio su file dell'oggetto: \n" + bigliettoCorrente.toString());
            bigliettiVenduti.remove(bigliettoCorrente);
            idBiglietti--;
            System.out.println("\nVendita annullata\n");
            Logger.getLogger(ParadoxEntertainment.class.getName()).log(Level.SEVERE, null, e);
            return;
        }
        System.out.println("\nPROIEZIONE: biglietto aggiunto all'elenco\n");
        updateSeatsStrategy = new RimuoviPosto();
        aggiornaPosti(this, isVIP);
    }
    
    public boolean esisteBigliettoVenduto(int idBiglietto) {
        return getBigliettoVenduto(idBiglietto) != null;
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
    
    public void effettuaReso(int idBiglietto) throws IOException {
        bigliettoSelezionato = getBigliettoVenduto(idBiglietto);
        if(bigliettoSelezionato == null) 
           System.out.println("\nErrore nel recupero della biglietto venduto nel sistema\n");
    }
    
    public void confermaReso(int idBiglietto, boolean isVIP) {
        bigliettiRimborsati.add(getBigliettoVenduto(idBiglietto));
        bigliettiVenduti.remove(getBigliettoVenduto(idBiglietto));
        updateSeatsStrategy = new AggiungiPosto();
        aggiornaPosti(this, isVIP);
    }
    
    public void caricaDaFile() throws FileNotFoundException, IOException, ClassNotFoundException {
        // Biglietti venduti
        ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("BigliettiVenduti.bin"));
        this.bigliettiVenduti = (List<Biglietto>)inputStream.readObject();
        
        // Biglietti rimborsati
        inputStream = new ObjectInputStream(new FileInputStream("BigliettiVenduti.bin"));
        this.bigliettiRimborsati = (List<Biglietto>)inputStream.readObject();
        
        idBiglietti = (Integer)inputStream.readObject();
    }
    
    public void salvaSuFile() throws IOException {
        // Biglietti venduti
        ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("BigliettiVenduti.bin"));
        outputStream.writeObject(this.bigliettiVenduti);
        
        // Biglietti rimborsati
        outputStream = new ObjectOutputStream(new FileOutputStream("BigliettiRimborsati.bin"));
        outputStream.writeObject(this.bigliettiRimborsati);
        
        outputStream.writeObject(idBiglietti);
    }
    
    @Override
    public String toString() {
        return "Proiezione ID: " + idProiezione + 
                "\n - Pellicola: " + pellicola.getNomePellicola() + 
                "\n - Orario: " + orario +
                "\n - Durata: " + pellicola.getDurata() + " minuti" +
                "\n - Posti totali rimanenti: " + postiRimanentiTot + 
                "\n - Posti standard rimanenti: " + postiRimanentiStandard + 
                "\n - Posti VIP rimanenti: " + postiRimanentiVIP;
    }
}
