package com.mycompany.paradoxentertainment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Daniele
 */
public class Cinema {
    private static Cinema cinema;
    private Sala salaCorrente, salaSelezionata;
    private Proiezione proiezioneCorrente, proiezioneSelezionata;
    private Map<String, Sala> elencoSale;
    private Map<String, List<Proiezione>>elencoProiezioni; // <IdSala, elencoProiezioniQuellaSala[]>
    BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
    
    public static Cinema getInstance() {
        if (cinema == null)
            cinema = new Cinema();
        return cinema;
    }
    
    private Cinema() {
        this.elencoSale = new HashMap();
        this.elencoProiezioni = new HashMap();
    }
    
    public boolean verificaNomeSala(String nomeSala) {
         for(Map.Entry<String, Sala> set : elencoSale.entrySet()) {
            if(nomeSala.equals(set.getValue().getNomeSala())) {
                System.out.println("Nome già in uso");
                return true;
            }
        } 
        return false;
    }
    
    public void inserisciSala(String nomeSala, int postiStandard, int postiVIP) {
        salaCorrente = new Sala(nomeSala, postiStandard, postiVIP);
    }
   
    public void confermaSala() {
        elencoSale.put(salaCorrente.getNomeSala(), salaCorrente);
        salaCorrente.toString();
    }
    
    public void stampaSale() {
        if(elencoSale.isEmpty()) 
            System.out.println("Non esistono sale\n");
        for(Map.Entry<String, Sala> set : elencoSale.entrySet()) {
            System.out.println("\n" + set.getValue().toString());
        }
    }
    
    public boolean isElencoSaleEmpty() {
        return elencoSale.isEmpty();
    }
    
    public Sala getSala(String idSala) {
        return elencoSale.get(idSala);
    }

    public boolean inserisciProiezione(Pellicola p) throws IOException {
        LocalTime orario = null;
        boolean isInvalid = false;
        
        System.out.println("\nInserisci il nome della sala in cui tenere la proiezione");
        stampaSale();
        salaSelezionata = getSala(bf.readLine());
        
        System.out.println("\nSala selezionata: sala " + salaSelezionata.getNomeSala());
        
        // Inserimento orario dello spettacolo
        System.out.println("\nInserisci l'orario nel formato 'ora:minuti'");
        
        try {
            orario = LocalTime.parse(bf.readLine(), DateTimeFormatter.ofPattern("HH:mm"));
            
            //verifica orario per proiezione 
            if(orario.getHour() < 16 || orario.getHour() > 23) {
                System.out.println("Orario inserito non valido: è consentito aggiungere spettacoli tra le 16:00 e 00:00");
                return false;
            } if(orario.getMinute() < 0 || orario.getMinute() > 59) {
                System.out.println("Orario inserito non valido: formato dei minuti non valido (deve essere tra 00 e 59)");
                return false;
            } 
        } catch(DateTimeParseException ex) {
            System.out.println("Inserimento dell'orario non valido: inserire nel formato 'hh:mm'");
            return false;
        }
        
        // verifica dello spettacolo in inserimento 
        if(elencoProiezioni.containsKey(salaSelezionata.getNomeSala()) && !elencoProiezioni.get(salaSelezionata.getNomeSala()).isEmpty()) {
            for (Proiezione P: elencoProiezioni.get(salaSelezionata.getNomeSala())) {
                if( P.getOrario().equals(orario) || 
                        (orario.isAfter(P.getOrario()) && orario.isBefore(P.getOrario().plusMinutes(P.getPellicola().getDurata()))) ||
                            (orario.isBefore(P.getOrario()) && orario.plusMinutes(p.getDurata()).isAfter(P.getOrario()))) {
                    isInvalid = true;
                    if(P.getOrario().equals(orario)) 
                        System.out.println("Errore: l'orario inserito corrisponde a quello dello spettacolo " +P.getIdProiezione()+ ", di " +P.getPellicola().getNomePellicola()+ " in sala " +P.getSala().getNomeSala());
                    if(orario.isAfter(P.getOrario()) && orario.isBefore(P.getOrario().plusMinutes(P.getPellicola().getDurata()))) 
                        System.out.println("Errore: lo spettacolo inizia quando ancora non è terminato il precedente delle " +P.getOrario()+ " di " +P.getPellicola().getNomePellicola()+ " in Sala " +P.getSala().getNomeSala());
                    if(orario.isBefore(P.getOrario()) && orario.plusMinutes(p.getDurata()).isAfter(P.getOrario()))
                        System.out.println("Errore: lo spettacolo si sovrappone al successivo delle " +P.getOrario()+ " di " +P.getPellicola().getNomePellicola()+ " in Sala " +P.getSala().getNomeSala());
                } 
            }
            if(isInvalid)
                return false;
        }
        
        System.out.println("\nRiepilogo:"
                + "\nPellicola: " + p.getNomePellicola() 
                + "\nSala: " + salaSelezionata.getNomeSala() 
                + "\nOrario: " + orario.toString()
                + "\nPremere 1 per confermare, 0 per annullare l'inserimento"); 
        
        if(Integer.parseInt(bf.readLine()) == 0) {
            System.out.println("Inserimento annullato\n");
            return false;
        }
        
        if(!elencoProiezioni.containsKey(salaSelezionata.getNomeSala()))
            elencoProiezioni.put(salaCorrente.getNomeSala(), new ArrayList());
        
        proiezioneCorrente = new Proiezione(elencoProiezioni.get(salaSelezionata.getNomeSala()).size()+1, salaSelezionata, p, orario);
        return true;
    }
    
    public void confermaProiezione() {
        elencoProiezioni.get(salaSelezionata.getNomeSala()).add(proiezioneCorrente);
        System.out.println("Inserimento della proiezione completato con successo");
    }
    
    
    public void stampaProgrammazione() {
        if(elencoProiezioni.isEmpty()) 
            System.out.println("Non esistono proiezioni\n");
        else 
            for(Map.Entry<String, List<Proiezione>> proiezioniSala : elencoProiezioni.entrySet()) {
                System.out.println("\nSala " + proiezioniSala.getKey());
                stampaProiezioniSala(proiezioniSala.getValue());
                //System.out.println("Sala " + elencoProiezioni.get(proiezioniSala.getKey()).toString());
            }
    }
    
    public void stampaProiezioniSala(List<Proiezione> proiezioni) {
        if(!proiezioni.isEmpty()) {
            for(Proiezione P: proiezioni)
                System.out.println("\n" + P.toString());
        }
    }
}
