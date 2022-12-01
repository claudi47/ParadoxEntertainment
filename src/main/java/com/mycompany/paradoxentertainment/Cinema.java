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
    int idProiezione = 0;
    
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
    
    public boolean isElencoProiezioniEmpty() {
        return elencoProiezioni.isEmpty();
    }
    
    public Sala getSala(String idSala) {
        return elencoSale.get(idSala);
    }
    
    public Proiezione getProiezione(int idProiezione) {
        for(Map.Entry<String, List<Proiezione>> proiezioniSala : elencoProiezioni.entrySet())
            for(Proiezione P: proiezioniSala.getValue()) 
                if(P.getIdProiezione() == idProiezione)
                    return P;
        return null;
    }

    public boolean inserisciProiezione(Pellicola p) throws IOException {
        LocalTime orario = null;
        boolean isInvalid = false;
        
        System.out.println("\nInserisci il nome della sala in cui tenere la proiezione");
        stampaSale();
        salaSelezionata = getSala(bf.readLine());
        
        if(salaSelezionata == null) {
            System.out.println("\nErrore: la sala inserita non esiste\n");
            return false;
        }
        
        System.out.println("\nSala selezionata: " + salaSelezionata.getNomeSala());
        if(elencoProiezioni.containsKey(salaSelezionata.getNomeSala())) {
            System.out.println("\nSpettacoli già in programma in Sala " + salaSelezionata.getNomeSala());
            stampaProiezioniSala(elencoProiezioni.get(salaSelezionata.getNomeSala()));
        }
        
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
            elencoProiezioni.put(salaSelezionata.getNomeSala(), new ArrayList());
        
        proiezioneCorrente = new Proiezione(++idProiezione, salaSelezionata, p, orario);
        
        return true;
    }
    
    public int eliminaProiezioniPerPellicola(int idPellicola) {
        int numeroProiezioniEliminate = 0;
        List<Proiezione> proiezioniDaRimuovere = new ArrayList();
        
        for(Map.Entry<String, List<Proiezione>> proiezioniSala : elencoProiezioni.entrySet()) {
            for(Proiezione P: proiezioniSala.getValue()) {
                if(P.getPellicola().getIdPellicola() == idPellicola) {
                    numeroProiezioniEliminate++;
                    System.out.println("\n" + P.stampaProiezioneConSala());
                    proiezioniDaRimuovere.add(P);
                }
            }
            proiezioniSala.getValue().removeAll(proiezioniDaRimuovere);
        }
        
        return numeroProiezioniEliminate;
    }
    
    public void confermaProiezione() {
        elencoProiezioni.get(salaSelezionata.getNomeSala()).add(proiezioneCorrente);
        System.out.println("Inserimento della proiezione completato con successo");
    }
    
    //eventualmente vedi se modifichi e fai in modo che stampaProiezioniSala diventi 
    // stampaProiezioniPERSala e che riceva in ingresso un id e poi stampi la lista
    public void stampaProgrammazione() {
        if(elencoProiezioni.isEmpty()) 
            System.out.println("Non esistono proiezioni\n");
        else 
            for(Map.Entry<String, List<Proiezione>> proiezioniSala : elencoProiezioni.entrySet()) {
                if(!proiezioniSala.getValue().isEmpty()) {
                    System.out.println("\nSala " + proiezioniSala.getKey());
                    stampaProiezioniSala(proiezioniSala.getValue());
                }
            }
    }
    
    public void stampaProiezioniSala(List<Proiezione> proiezioni) {
        if(!proiezioni.isEmpty()) {
            for(Proiezione P: proiezioni)
                System.out.println("\n" + P.toString());
        }
    }
    
    public int proiezioniPerDataPellicola(int idPellicola) throws IOException {
        int numeroProiezioniPerPellicola = 0;
        
        for(Map.Entry<String, List<Proiezione>> proiezioniSala : elencoProiezioni.entrySet()) {
            for(Proiezione P: proiezioniSala.getValue()) {
                if(P.getPellicola().getIdPellicola() == idPellicola) {
                    numeroProiezioniPerPellicola++;
                    System.out.println("\n" + P.stampaProiezioneConSala());
                }
            }
        }
        return numeroProiezioniPerPellicola;
    }
    
    public boolean acquistaBiglietto(int idPellicola) throws IOException {
        if(proiezioniPerDataPellicola(idPellicola) == 0) {
            System.out.println("Non esistono proiezioni per la pellicola selezionata\n");
            return false;
        } else
            return true;
    }
    
    public boolean scegliProiezione(int idProiezione) {
        if((proiezioneSelezionata = getProiezione(idProiezione)) != null) {
            if(proiezioneSelezionata.getPostiRimanentiTot() > 0) {
                System.out.println("\n Proiezione Selezionata: \n" + proiezioneSelezionata);
                return true;
            } else {
                System.out.println("Non sono più disponibili posti per la proiezione selezionata\n");
                return false;
            }
        }
        System.out.println("La proiezione selezionata non esiste\n");
        return false;
    }
    
    public int effettuaAcquisto(boolean isVIP, boolean isCategoriaProtetta) {
        int costoBiglietto;
        
        if(isVIP)
            if(proiezioneSelezionata.getPostiRimanentiVIP() == 0) {
                System.out.println("Non sono più disponibili posti VIP per la proiezione selezionata\n");
                return -1;
            }
        costoBiglietto = proiezioneSelezionata.creaBiglietto(isVIP, isCategoriaProtetta);
        return costoBiglietto;
    }
    
    /*
    public void stampaProiezioniSalaAdElenco(String nomeSala) {
        if(elencoProiezioni.containsKey(nomeSala)) {
            for()
            if(!proiezioni.isEmpty()) {
                for(Proiezione P: proiezioni)
                    System.out.println("\n" + P.stampaElenco());
            }
        }
    }
   
    
    public boolean isPellicolaProiettata(int idPellicola) {
        for(Map.Entry<String, List<Proiezione>> proiezioniSala : elencoProiezioni.entrySet()) {
            for(Proiezione P: proiezioniSala.getValue()) {
                if(P.getPellicola().getIdPellicola() == idPellicola)
                    return true;
            }
        }
        return false;
    }

    
    
    public Proiezione scegliProiezione(int idProiezione) {
        for(Map.Entry<String, List<Proiezione>> proiezioniSala : elencoProiezioni.entrySet()) {
                for(Proiezione P: proiezioniSala.getValue()) {
                    if(P.getIdProiezione() == idProiezione)
                        return P;
                }
            }
        return null;
    }
    
    public boolean haPostiVIP(int idProiezione) {
        for(Map.Entry<String, List<Proiezione>> proiezioniSala : elencoProiezioni.entrySet()) {
            for(Proiezione P: proiezioniSala.getValue()) 
                if(P.getPostiRimanentiVIP() > 0)
                    return true; 
        } 
        return false;
    }    
    
    public boolean verificaProiezioneDaVisionare(int idProiezione, int idPellicola) {
        if(elencoProiezioni.isEmpty()) 
            return false;
        else {
            for(Map.Entry<String, List<Proiezione>> proiezioniSala : elencoProiezioni.entrySet()) {
                for(Proiezione P: proiezioniSala.getValue()) {
                    if(P.getIdProiezione() == idProiezione) {
                        if(P.getPellicola().getIdPellicola() == idPellicola) {
                            //spettacolo è stato trovato correttamente
                            if(P.getPostiRimanentiStandard() > 0)
                                return true;
                            else {
                                System.out.println("\nI posti per lo spettacolo sono esauriti\n");
                                return false; 
                            }
                        } else {
                            //trovato lo spettacolo inserito ma non è del film che ha scelto
                            System.out.println("\nErrore: la proiezione scelta non è valida, essendo relativa ad un'altra pellicola\n");
                            return false;
                        }
                    }
                }
            }
            //ho esplorato ogni proiezione ma nessuna di queste ha l'ID inserito dall'addetto
            System.out.println("\nErrore: la proiezione scelta non esiste nel sistema\n");
            return false;
        }
    }
    */
}
