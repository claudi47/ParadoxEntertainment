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
    private int numeroProiezioni = 0;
    private Map<Integer, Sala> elencoSale;
    private int idSale = 0;
    private Map<Integer, List<Proiezione>>elencoProiezioni; // <IdSala, elencoProiezioniQuellaSala[]>
    BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
    int idProiezioni = 0;
    
    public static Cinema getInstance() {
        if (cinema == null)
            cinema = new Cinema();
        return cinema;
    }
    
    private Cinema() {
        this.elencoSale = new HashMap();
        this.elencoProiezioni = new HashMap();
    }
    
    // SALE
    public boolean verificaNomeSala(String nomeSala) {
        for(Map.Entry<Integer, Sala> entrySala : elencoSale.entrySet()) {
            if(salaSelezionata != null) 
                if(nomeSala.equals(salaSelezionata.getNomeSala()))
                    return false;
            if(nomeSala.equals(entrySala.getValue().getNomeSala())) {
                System.out.println("\nEsiste già una Sala " + entrySala.getKey());
                return true;
            }
        }
        return false;
    }
    
    public void inserisciSala(String nomeSala, int postiStandard, int postiVIP) {
        salaCorrente = new Sala(nomeSala, postiStandard, postiVIP, ++idSale);
    }
   
    public void confermaSala() {
        elencoSale.put(salaCorrente.getIdSala(), salaCorrente);
        elencoProiezioni.put(salaCorrente.getIdSala(), new ArrayList());
        salaCorrente.toString();
    }
    
    public void stampaSale() {
        if(elencoSale.isEmpty()) 
            System.out.println("Non esistono sale\n");
        for(Map.Entry<Integer, Sala> entrySala : elencoSale.entrySet()) {
            System.out.println("\n" + entrySala.getValue().toString());
        }
    }
    
    public boolean isElencoSaleEmpty() {
        return elencoSale.isEmpty();
    }
    
    public Sala getSala(int idSala) {
        return elencoSale.get(idSala);
    }
    
    public void modificaSala() throws IOException {
        int idSala;
        String nomeSala;
        int postiStandard, postiVIP, postiTot;
        
        stampaSale();
        System.out.println("\nInserire l'ID della sala da modificare");
        idSala = Integer.parseInt(bf.readLine());
        
        if((salaSelezionata = elencoSale.get(idSala)) == null) {
            System.out.println("\nErrore: selezione non valida");
            return;
        } else
            System.out.println("\nSala selezionata: " + salaSelezionata.getNomeSala());
       
        do {
            System.out.println("\nInserisci il nome della sala");
            nomeSala = bf.readLine();
        } while(verificaNomeSala(nomeSala) == true);
        
        System.out.println("Inserisci il numero di posti standard");
        postiStandard = Integer.parseInt(bf.readLine());
        
        System.out.println("Inserisci il numero di posti VIP");
        postiVIP = Integer.parseInt(bf.readLine());
        
        postiTot = postiStandard+postiVIP;
        
        System.out.println("\nRiepilogo: \n"
                    + " - ID: " + salaSelezionata.getIdSala() + "\n"
                    + " - Nome Sala: " + nomeSala + "\n"
                    + " - Posti Standard: " + postiStandard + "\n"
                    + " - Posti VIP: " + postiVIP + "\n"
                    + " - Posti Totali: " + postiTot + "\n"
                    + "Premere 1 per confermare, 0 per annullare l'inserimento"
                    );
             
        if(bf.readLine().equals("1")) {
            salaSelezionata.modificaSala(nomeSala, postiStandard, postiVIP);
            System.out.println("Modifica effettuata con successo\n");
        } else 
            System.out.println("Modifica annullata\n");
    }
    
    public void eliminaSala() throws IOException {
        int idSala;
        
        stampaSale();
        System.out.println("\nInserire l'ID della sala da modificare");
        idSala = Integer.parseInt(bf.readLine());
        
        if((salaSelezionata = elencoSale.get(idSala)) == null) {
            System.out.println("\nErrore: selezione non valida");
        } else {
            System.out.println("\nSala selezionata: " + salaSelezionata.toString());
            System.out.println("\nInserire 1 per confermare l'eliminazione, 0 altrimenti");
            if(bf.readLine().equals("1")) {
                elencoSale.remove(salaSelezionata.getIdSala());
                elencoProiezioni.remove(salaSelezionata.getIdSala());
                System.out.println("Eliminazione completata\n");
            } else
                System.out.println("Eliminazione annullata\n");
        }
    }
    
    // PROIEZIONI
    public boolean isElencoProiezioniEmpty() {
        return elencoProiezioni.isEmpty();
    }
    
    public Proiezione getProiezione(int idProiezione) {
        for(Map.Entry<Integer, List<Proiezione>> proiezioniSala : elencoProiezioni.entrySet())
            for(Proiezione P: proiezioniSala.getValue()) 
                if(P.getIdProiezione() == idProiezione)
                    return P;
        return null;
    }

    public boolean inserisciProiezione(Pellicola p) throws IOException {
        LocalTime orario = null;
        boolean isInvalid = false;
        
        System.out.println("\nInserisci l'ID della sala in cui tenere la proiezione");
        stampaSale();
        salaSelezionata = getSala(Integer.parseInt(bf.readLine()));
        
        if(salaSelezionata == null) {
            System.out.println("\nErrore: la sala inserita non esiste\n");
            return false;
        }
        
        System.out.println("\nSala selezionata: " + salaSelezionata.getNomeSala());
        if(elencoProiezioni.containsKey(salaSelezionata.getIdSala()) && !elencoProiezioni.get(salaSelezionata.getIdSala()).isEmpty()) {
            System.out.println("\nSpettacoli già in programma in Sala " + salaSelezionata.getNomeSala());
            stampaProiezioniSala(elencoProiezioni.get(salaSelezionata.getIdSala()));
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
        if(elencoProiezioni.containsKey(salaSelezionata.getIdSala()) && !elencoProiezioni.get(salaSelezionata.getIdSala()).isEmpty()) {
            for (Proiezione P: elencoProiezioni.get(salaSelezionata.getIdSala())) {
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
                + "\n\nPremere 1 per confermare, 0 per annullare l'inserimento"); 
        
        if(Integer.parseInt(bf.readLine()) == 0) {
            System.out.println("\nInserimento annullato\n");
            return false;
        }
        
        proiezioneCorrente = new Proiezione(++idProiezioni, salaSelezionata, p, orario);
        
        return true;
    }
    
    public void confermaProiezione() {
        elencoProiezioni.get(salaSelezionata.getIdSala()).add(proiezioneCorrente);
        numeroProiezioni++;
        System.out.println("\nInserimento della proiezione completato con successo");
    }
    
    public void modificaProiezione(Proiezione proiezioneDaModificare, Pellicola p) throws IOException {
        LocalTime orario = null;
        boolean isInvalid = false;
        
        proiezioneSelezionata = proiezioneDaModificare;
        
        System.out.println("\nInserisci l'ID della sala in cui tenere la proiezione");
        stampaSale();
        salaSelezionata = getSala(Integer.parseInt(bf.readLine()));
        
        if(salaSelezionata == null) {
            System.out.println("\nErrore: la sala inserita non esiste\n");
            return;
        }
        
        System.out.println("\nSala selezionata: " + salaSelezionata.getNomeSala());
        if(elencoProiezioni.containsKey(salaSelezionata.getIdSala()) && !elencoProiezioni.get(salaSelezionata.getIdSala()).isEmpty()) {
            System.out.println("\nSpettacoli già in programma in Sala " + salaSelezionata.getNomeSala());
            stampaProiezioniSala(elencoProiezioni.get(salaSelezionata.getIdSala()));
        }
        
        // Inserimento orario dello spettacolo
        System.out.println("\nInserisci l'orario nel formato 'ora:minuti'");
        
        try {
            orario = LocalTime.parse(bf.readLine(), DateTimeFormatter.ofPattern("HH:mm"));
            
            //verifica orario per proiezione 
            if(orario.getHour() < 16 || orario.getHour() > 23) {
                System.out.println("Orario inserito non valido: è consentito aggiungere spettacoli tra le 16:00 e 00:00");
                return;
            } if(orario.getMinute() < 0 || orario.getMinute() > 59) {
                System.out.println("Orario inserito non valido: formato dei minuti non valido (deve essere tra 00 e 59)");
                return;
            } 
        } catch(DateTimeParseException ex) {
            System.out.println("Inserimento dell'orario non valido: inserire nel formato 'hh:mm'");
            return;
        }
        
        // verifica dello spettacolo in inserimento 
        if(elencoProiezioni.containsKey(salaSelezionata.getIdSala()) && !elencoProiezioni.get(salaSelezionata.getIdSala()).isEmpty()) {
            for (Proiezione P: elencoProiezioni.get(salaSelezionata.getIdSala())) {
                if(P.getIdProiezione() != proiezioneSelezionata.getIdProiezione()) {
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
            }
            if(isInvalid)
                return;
        }
        
        System.out.println("\nRiepilogo:"
                + "\nPellicola: " + p.getNomePellicola() 
                + "\nSala: " + salaSelezionata.getNomeSala() 
                + "\nOrario: " + orario.toString()
                + "\n\nPremere 1 per confermare la modifica, altro per annullare"); 
        
        if(bf.readLine().equals("1")) {
            elencoProiezioni.get(proiezioneSelezionata.getSala().getIdSala()).remove(proiezioneSelezionata);
            proiezioneSelezionata.modificaProiezione(p, salaCorrente, orario);
            elencoProiezioni.get(salaSelezionata.getIdSala()).add(proiezioneSelezionata);
            System.out.println("\nModifica effettuata con successo\n");
        } else
            System.out.println("\nModifica annullata\n");
    }
    
    public void stampaProiezione(int idProiezione) {
        if(elencoProiezioni.containsKey(idProiezione))
            System.out.println(elencoProiezioni.get(idProiezione).toString());
        else
            System.out.println("\nProiezione non esistente\n");
    }
    
    // Restituisce il numero di biglietti venduti data una certa Proiezione
    public int bigliettiVendutiProiezione(Proiezione proiezione) {
        return proiezione.getBigliettiVenduti().size();
    }
    
    // Restituisce il numero di biglietti venduti dato l'ID di una certa Proiezine
    public int bigliettiVendutiProiezione(int idProiezione) {
        proiezioneSelezionata = getProiezione(idProiezione);
        return bigliettiVendutiProiezione(proiezioneSelezionata);
    }
    
    // Elimina tutte le proiezioni di una certa Pellicola dato il suo ID, 
    // restituendo il numero di pellicole eliminate
    public int eliminaProiezioniPerPellicola(int idPellicola) {
        int numeroProiezioniEliminate = 0;
        List<Proiezione> proiezioniDaRimuovere = new ArrayList();
        
        for(Map.Entry<Integer, List<Proiezione>> proiezioniSala : elencoProiezioni.entrySet()) {
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
    
    public void eliminaProiezione() throws IOException {
        int idProiezioneDaEliminare;
        stampaProgrammazione();
        System.out.println("\nInserire l'ID della proiezione da eliminare");
        
        idProiezioneDaEliminare = Integer.parseInt(bf.readLine());
        proiezioneSelezionata = getProiezione(idProiezioneDaEliminare);
        
        // Se è stata inserita una proiezione realmente esistente
        if(proiezioneSelezionata != null) {
            System.out.println("\nProiezione selezionata: \n" + proiezioneSelezionata.toString() + "\n"
            + "\nPremi 1 per confermare l'eliminazione, altro per annullare");
            // Se si è confermata l'eliminazione inserendo 1
            if(bf.readLine().equals("1")) {
                elencoProiezioni.get(proiezioneSelezionata.getSala().getIdSala()).remove(proiezioneSelezionata);
                numeroProiezioni--;
                System.out.println("\nEliminazione completata\n");
                /*
                for(Map.Entry<Integer, List<Proiezione>> proiezioniSala : elencoProiezioni.entrySet()) {
                    for(Proiezione P: proiezioniSala.getValue()) {
                        if(P.getIdProiezione() == proiezioneSelezionata.getIdProiezione()) {
                            proiezioniSala.getValue().remove(P);
                                                    }
                    }
                }
                */
            } else // Se si è inserito un qualunque altro input procede all'annullamento
                System.out.println("\nEliminazione annullata\n");
        } else //Se è stato inserito un ID di proiezione non valido
            System.out.println("\nScelta non valida");   
    }
    
    //eventualmente vedi se modifichi e fai in modo che stampaProiezioniSala diventi 
    // stampaProiezioniPERSala e che riceva in ingresso un id e poi stampi la lista
    public void stampaProgrammazione() {
        if(elencoProiezioni.isEmpty()) 
            System.out.println("Non esistono proiezioni\n");
        else 
            for(Map.Entry<Integer, List<Proiezione>> proiezioniSala : elencoProiezioni.entrySet()) {
                if(!proiezioniSala.getValue().isEmpty()) {
                    System.out.println("\nSala " + elencoSale.get(proiezioniSala.getKey()).getNomeSala());
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
        
        for(Map.Entry<Integer, List<Proiezione>> proiezioniSala : elencoProiezioni.entrySet()) {
            for(Proiezione P: proiezioniSala.getValue()) {
                if(P.getPellicola().getIdPellicola() == idPellicola) {
                    numeroProiezioniPerPellicola++;
                    System.out.println("\n" + P.stampaProiezioneConSala());
                }
            }
        }
        return numeroProiezioniPerPellicola;
    }

    public int getNumeroProiezioni() {
        return numeroProiezioni;
    }
    
    public Proiezione trovaProiezione() throws IOException {
        String inserimentoProiezione;
        int idProiezione;
        
        if(!elencoProiezioni.isEmpty()) {
            do {
                System.out.println("\nInserire l'ID della proiezione o premere Invio per stamparle tutte:");
                inserimentoProiezione = bf.readLine();
                if(inserimentoProiezione.equals("")) {
                    stampaProgrammazione();
                    System.out.println("\nInserisci l'ID della proiezione: ");
                    inserimentoProiezione = bf.readLine();
                }

                idProiezione = Integer.parseInt(inserimentoProiezione);
                if(getProiezione(idProiezione) != null) 
                    return getProiezione(idProiezione);
                else
                    System.out.println("\nProiezione non trovata: inserire 1 per riprovare, altrimenti per annullare");
            } while(bf.readLine().equals("1"));
            return null;
        }
        System.out.println("\nNon ci sono Proiezioni nel sistema");
        return null;
    }
    
    // VENDITA BIGLIETTO
    public boolean acquistaBiglietto(int idPellicola) throws IOException {
        // Il cinema stampa le sue proiezioni durante la vendita del biglietto
        if(proiezioniPerDataPellicola(idPellicola) == 0) {
            System.out.println("Non esistono proiezioni per la pellicola selezionata\n");
            return false;
        } else
            return true;
    }
    
    public boolean scegliProiezione(int idProiezione) {
        // verifica se la proiezione selezionata esiste
        if((proiezioneSelezionata = getProiezione(idProiezione)) != null) {
            // verifica se la proiezione selezionata ha ancora posti rimanenti
            if(proiezioneSelezionata.getPostiRimanentiTot() > 0) {
                System.out.println("\nProiezione Selezionata: \n" + proiezioneSelezionata);
                return true;
            } else {
                System.out.println("Non sono più disponibili posti per la proiezione selezionata\n");
                return false;
            }
        }
        System.out.println("La proiezione selezionata non esiste\n");
        return false;
    }
    
    public float effettuaAcquisto(boolean isVIP, boolean isCategoriaProtetta) {
        float costoBiglietto;
        
        if(proiezioneSelezionata.getPostiRimanentiTot() > 0) {
            if(isVIP) {
                if(proiezioneSelezionata.getPostiRimanentiVIP() == 0) {
                    System.out.println("Non sono più disponibili posti VIP per la proiezione selezionata\n");
                    return -1;
                }
            } else {
                if(proiezioneSelezionata.getPostiRimanentiStandard()== 0) {
                    System.out.println("Non sono più disponibili posti standard per la proiezione selezionata\n");
                    return -1;
                }
            }
            costoBiglietto = proiezioneSelezionata.creaBiglietto(isVIP, isCategoriaProtetta);
            System.out.println("CINEMA: " + costoBiglietto);
            return costoBiglietto;
        } else {
            System.out.println("I posti per la proiezione " + proiezioneSelezionata.getIdProiezione() + " sono tutti terminati\n");
            return -1;
        }
    }
    
    public void confermaAcquisto() {
        proiezioneSelezionata.confermaAcquisto();
    }
    
    public boolean effettuaReso() throws IOException {
        proiezioneSelezionata = trovaProiezione();
        
        if(proiezioneSelezionata != null) {
            System.out.println("\nProiezione selezionata: \n" + proiezioneSelezionata.toString() + 
                    "\nInserire 1 per confermare e proseguire, altrimenti per selezionare un'altra proiezione");
            if(!bf.readLine().equals("1"))
                this.effettuaReso();
            else 
                return proiezioneSelezionata.effettuaReso();
        } else
            System.out.println("Operazione di rimborso annullata\n");
        return false;
    }
    
    public float confermaReso() {
        return proiezioneSelezionata.confermaReso();
    }
   
}
