package com.mycompany.paradoxentertainment;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JFileChooser;

/**
 *
 * @author Daniele
 */
public class ParadoxEntertainment {
    private static ParadoxEntertainment paradoxEntertainment; //Singleton
    private Cinema c;
    BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
    private Map<Integer, Pellicola> elencoPellicole;  //<IdPellicola, Pellicola>
    private Pellicola pellicolaCorrente, pellicolaSelezionata;
    private Map<Integer, Locandina> elencoLocandine; // <IdPellicola, Locandina>
    private Locandina locandinaCorrente, locandinaSelezionata;
    private Map<String, Tessera> elencoTessere;
    private Tessera tesseraCorrente, tesseraSelezionata;
    
    /**
     * Singleton
     */
    public static ParadoxEntertainment getInstance() {
        if (paradoxEntertainment == null)
            paradoxEntertainment = new ParadoxEntertainment();
        else
            System.out.println("Instanza già creata");
        return paradoxEntertainment;
    }
    
    private ParadoxEntertainment() {
        this.c = Cinema.getInstance();
        this.elencoPellicole = new HashMap();
        this.elencoLocandine = new HashMap();
        this.elencoTessere = new HashMap();
    }
    
    // MENU 
    public void menuLogin() throws IOException {
        int scelta;
        
        try {
            System.out.println("Login: \n"
                        + "1. Accedi come Amministratore \n"
                        + "2. Accedi come Addetto");
        
            scelta = Integer.parseInt(bf.readLine());
        
            switch (scelta) {
                case 1:
                    menuAmministratore();
                    break;
            
                case 2:
                    menuAddetto();
                    break;
            
                default:
                    System.out.println("Scelta non valida\n\n");
                    break;
            }
            /*
            TO DO il flush della console
            Runtime.getRuntime().exec("cls");
            */
            menuLogin();
       } catch (NumberFormatException ex) {
           menuLogin();
       }
    }
        
    public void menuAmministratore() throws IOException {
        int scelta;
        
        System.out.println("\nMenu Amministratore \n"
                + "1. Aggiungi Sala \n"
                + "2. Aggiungi Film \n"
                + "3. Aggiungi Proiezione\n"
                + "4. Gestisci Sale\n"
                + "5. Gestisci Pellicole\n"
                + "6. Gestisci Proiezioni\n"
                + "7. Stampa locandine\n"
                + "Altro. Torna al Login");
        
        scelta = Integer.parseInt(bf.readLine());
        
        switch (scelta) {
            case 1: //UC1 Inserimento Sala
                inserisciSala();
                break;
            
            case 2:
                inserisciPellicola();
                break;
                
            case 3:
                inserisciProiezione();
                break;
                
            case 4: 
                if(c.isElencoSaleEmpty()) 
                    System.out.println("\nNon esistono sale");
                else 
                    gestisciSale();
                break;
                
            case 5:
                if(elencoPellicole.isEmpty()) 
                    System.out.println("\nNon esistono pellicole");
                else 
                    gestisciPellicole();
                break;
                
            case 6:
                if(c.isElencoProiezioniEmpty()) 
                    System.out.println("\nNon esistono proiezioni");
                else 
                    gestisciProiezioni();
                break;
            
            case 7:
                if(elencoLocandine.isEmpty())
                    System.out.println("\nNon esistono locandine");
                else
                    stampaLocandine();
                break;
                
            default:
                System.out.println("Logout \n\n");
                menuLogin();
        }
        menuAmministratore();
    }
    
    public void menuAddetto() throws IOException{ 
        int scelta;
        
        System.out.println("\nMenu Addetto \n"
                + "1. Vendita Biglietto \n"
                + "2. Creazione Tessera \n"
                + "3. Gestisci Tessera \n"
                + "Altro. Torna al Login");
        
        scelta = Integer.parseInt(bf.readLine());
        
        switch (scelta) {
            case 1: 
                if(c.isElencoProiezioniEmpty())
                    System.out.println("Non esistono proiezioni\n");
                else 
                    acquistaBiglietto();
                break;
            
            case 2:
                inserisciTessera();
                break;
                
            case 3:
                if(elencoTessere.isEmpty())
                    System.out.println("Non esistono tessere\n");
                else
                    gestisciTessere();
                break;
                
            default:
                System.out.println("Logout \n\n");
                menuLogin();
        }
        menuAddetto();
    }
    
    // SALE
    public void inserisciSala() throws IOException {
        String nomeSala;
        int postiStandard;
        int postiVIP;
        int postiTot;
        
        do {
            System.out.println("\nInserimento Sala: \n"
                    + "Inserisci l'identificativo della sala");
            nomeSala = bf.readLine();
        } while(c.verificaNomeSala(nomeSala) == true);
        
        System.out.println("Inserisci il numero di posti standard");
        postiStandard = Integer.parseInt(bf.readLine());
        
        System.out.println("Inserisci il numero di posti VIP");
        postiVIP = Integer.parseInt(bf.readLine());
        
        postiTot = postiStandard+postiVIP;
        
        System.out.println("\nRiepilogo: \n"
                    + " - Nome Sala: " + nomeSala + "\n"
                    + " - Posti Standard: " + postiStandard + "\n"
                    + " - Posti VIP: " + postiVIP + "\n"
                    + " - Posti Totali: " + postiTot + "\n"
                    + "Premere 1 per confermare, 0 per annullare l'inserimento"
                    );
              
        if(Integer.parseInt(bf.readLine()) == 0) {
           System.out.println("Inserimento annullato\n");
           return;
        } 
        
        //Viene creato l'oggetto Sala
        c.inserisciSala(nomeSala, postiStandard, postiVIP);
        
        //Viene aggiunto all'elenco delle sale del cinema
        confermaSala();
        
        System.out.println("Inserimento completato con successo");
    }
    
    public void confermaSala() {
        c.confermaSala();
    }
    
    public void gestisciSale() throws IOException {
        int scelta;
        
        System.out.println("\nGestione Sale\n"
                + "1. Visualizza Sale \n"
                + "2. Modifica Sala \n"
                + "3. Elimina Sala\n"
                + "Altro. Torna al menu");
        
        scelta = Integer.parseInt(bf.readLine());
        
        switch (scelta) {
            case 1: //UC1 Inserimento Sala
                stampaSale();
                break;
            
            case 2:
                System.out.println("Non ancora implementata...\n");
                break;
                
            case 3:
                System.out.println("Non ancora implementata...\n");
                break;
                
            default:
                menuAmministratore();
        }
        if(c.isElencoSaleEmpty()) 
            return;
        gestisciSale();
    }
    
    public void stampaSale() {
        c.stampaSale();
    }
    
    // PELLICOLE
    public void inserisciPellicola() throws IOException {
        String nomePellicola;
        String regista;
        int anno;
        String genere;
        int durata;
        int idPellicola;
        String path = null;
        int baseStampa = 0, altezzaStampa = 0;
        boolean isLocandina = false;
               
        do {
            System.out.println("\nInserisci il titolo della pellicola");
            nomePellicola = bf.readLine();
            
            System.out.println("Inserisci il nome del regista");
            regista = bf.readLine();
        
            System.out.println("Inserisci l'anno di uscita");
            anno = Integer.parseInt(bf.readLine());
        } while((verificaPellicola(nomePellicola, regista, anno) == true));
        
        System.out.println("Inserisci il genere della pellicola");
        genere = bf.readLine();
        
        System.out.println("Inserisci la durata in minuti");
        durata = Integer.parseInt(bf.readLine());
        
        // Specifica di una locandina 
        System.out.println("Premere 1 se si desidera creare una locandina per la pellicola, 0 altrimenti");
        if(isLocandina = Integer.parseInt(bf.readLine()) == 1) {
            System.out.println("Inserisci il percorso dell'immagine");
            path = bf.readLine();
            System.out.println("Inserisci la base di stampa");
            baseStampa = Integer.parseInt(bf.readLine());
            System.out.println("Inserisci l'altezza di stampa");
            altezzaStampa = Integer.parseInt(bf.readLine());
        }
        
        System.out.println("\nRiepilogo: \n"
                    + " - Titolo: " + nomePellicola + "\n"
                    + " - Regista: " + regista + "\n"
                    + " - Anno: " + anno + "\n"
                    + " - Genere: " + genere + "\n"
                    + " - Durata: " + durata);
       
        if(isLocandina) 
            System.out.println(" - Percorso locandina: " + path + "\n"
                        + " - Dimensione base locandina: " + baseStampa + "\n"
                        + " - Dimensione altezza locandina: " + altezzaStampa);
        
        System.out.println("Premere 1 per confermare, 0 per annullare l'inserimento"); 
        
        if(Integer.parseInt(bf.readLine()) == 0) {
            System.out.println("Inserimento annullato\n");
            return;
        }
        
        // creazione oggetto Pellicola
        pellicolaCorrente = new Pellicola(nomePellicola, regista, anno, genere, durata, elencoPellicole.size()+1);
        confermaPellicola();
        System.out.println("\nInserimento della Pellicola completato con successo\n");
        
        if(isLocandina) {
            locandinaCorrente = pellicolaCorrente.inserisciLocandina(path, baseStampa, altezzaStampa);
            elencoLocandine.put(locandinaCorrente.getPellicola(), locandinaCorrente);
            System.out.println("Inserimento della Locandina completato con successo\n");
        }
    }
    
    public boolean verificaPellicola(String nomePellicola, String regista, int anno) {
        // a tempo perso vedi se riesci ad evitare la ripetizione delle 3 condizioni e il doppio return true
        for (HashMap.Entry<Integer, Pellicola> entry : elencoPellicole.entrySet()) {
            if(pellicolaSelezionata == null) {
                if(entry.getValue().getNomePellicola().equals(nomePellicola) && 
                        entry.getValue().getRegista().equals(regista) && 
                        entry.getValue().getAnno() == anno) {
                    System.out.println("\nErrore: pellicola già presente nel sistema");
                    return true;   
                }
            }
            if(entry.getValue().getNomePellicola().equals(nomePellicola) &&
                    entry.getValue().getRegista().equals(regista) && 
                    entry.getValue().getAnno() == anno && 
                    entry.getValue().getIdPellicola() != pellicolaSelezionata.getIdPellicola()) {
                System.out.println("\nErrore: pellicola già presente nel sistema");
                return true; 
            }
        }
        return false;
    }
            
    public void confermaPellicola() {
        elencoPellicole.put(pellicolaCorrente.getIdPellicola(), pellicolaCorrente);
    }
    
    public void stampaPellicole() {
        for(Map.Entry<Integer, Pellicola> set : elencoPellicole.entrySet()) 
            System.out.println("\n" + set.getValue().toString());
    }
    
    public void stampaPellicolePerTitolo(String titolo) {
        int count = 0;
        if(elencoPellicole.isEmpty()) 
            System.out.println("Non esistono pellicole\n");
        for(Map.Entry<Integer, Pellicola> set : elencoPellicole.entrySet()) {
            if(set.getValue().getNomePellicola().contains(titolo)) {
                count++;
                System.out.println("\n" + set.getValue().toString());
            }
        }
        if(count == 0) {
            System.out.println("\nLa ricerca non ha prodotto alcun risultato. \nStampa di tutte le pellicole:");
            stampaPellicole();
        }
    }
    
    public void gestisciPellicole() throws IOException {
        int scelta;
        
        System.out.println("\nGestione Pellicole\n"
                + "1. Visualizza Pellicole \n"
                + "2. Modifica Pellicole \n"
                + "3. Elimina Pellicole\n"
                + "Altro. Torna al menu");
        
        scelta = Integer.parseInt(bf.readLine());
        
        switch (scelta) {
            case 1: 
                stampaPellicole();
                break;
            
            case 2:
                modificaPellicola();
                break;
                
            case 3:
                eliminaPellicola();
                break;
                
            default:
                menuAmministratore();
        }
        if(elencoPellicole.isEmpty()) 
            return;
        gestisciPellicole();
    }
    
    public void modificaPellicola() throws IOException {
        int idPellicola;
        String titolo, regista, genere;
        int durata, anno;
        boolean variazioneDurata;
        
        // selezione pellicola (uguale in InserisciProiezione, creare un metodo selezionePellicola() ? )
        stampaPellicole();
        System.out.println("\nInserire l'ID della pellicola da modificare");
        idPellicola = Integer.parseInt(bf.readLine());
        
        if((pellicolaSelezionata = elencoPellicole.get(idPellicola)) == null) {
            System.out.println("\nErrore: selezione non valida");
            return;
        } else
            System.out.println("Pellicola selezionata: " + pellicolaSelezionata.getNomePellicola() + ", durata: " + pellicolaSelezionata.getDurata() + " minuti");
       
        // inserimento dati della pellicola (uguale in inserisciPellicola, creare un metodo creaPellicola()? )
        do {
            System.out.println("\nInserisci il titolo della pellicola");
            titolo = bf.readLine();
            
            System.out.println("Inserisci il nome del regista");
            regista = bf.readLine();
        
            System.out.println("Inserisci l'anno di uscita");
            anno = Integer.parseInt(bf.readLine());
        } while((verificaPellicola(titolo, regista, anno) == true)); //
        
        System.out.println("Inserisci il genere della pellicola");
        genere = bf.readLine();
        
        System.out.println("Inserisci la durata in minuti");
        durata = Integer.parseInt(bf.readLine());
        variazioneDurata = (durata > pellicolaSelezionata.getDurata());
        
        System.out.println("\nLa nuova pellicola è: " 
                + "\n - Titolo: " + titolo
                + "\n - Regista: " + regista
                + "\n - Anno: " + anno
                + "\n - Genere: " + genere
                + "\n - Durata: " + durata
                + "\nConfermare? 1. Si, 0. No");
        if(bf.readLine().equals("1")) {
            pellicolaSelezionata.modificaPellicola(titolo, regista, anno, genere, durata);
            System.out.println("Modifica effettuata con successo\n");
            if(variazioneDurata && c.proiezioniPerDataPellicola(idPellicola) > 0) {
                System.out.println("\nPoiché la pellicola ha subito un aumento della durata, le seguenti proiezioni sono state eliminate:");
                c.eliminaProiezioniPerPellicola(pellicolaSelezionata.getIdPellicola());
            }
        } else 
            System.out.println("Modifica annullata\n");
    }
    
    public void eliminaPellicola() throws IOException {
        int idPellicola;
        
        stampaPellicole();
        System.out.println("\nInserisci l'ID della pellicola da eliminare");
        idPellicola = Integer.parseInt(bf.readLine());
        pellicolaSelezionata = elencoPellicole.get(idPellicola);
        
        if(pellicolaSelezionata != null) {
            System.out.println("\nPellicola: " + pellicolaSelezionata.getNomePellicola() + " eliminata correttamente");
            if(elencoLocandine.containsKey(idPellicola)) 
                eliminaLocandina(idPellicola);
            if(c.proiezioniPerDataPellicola(idPellicola) > 0) {
                System.out.println("\nLe seguenti proiezioni della pellicola eliminata, sono state anch'esse eliminate:");
                c.eliminaProiezioniPerPellicola(idPellicola);
            }
            pellicolaSelezionata = elencoPellicole.remove(idPellicola);
        } else
            System.out.println("Scelta non valida: pellicola inesistente nel sistema\n");
    }
    
    // PROIEZIONI
    public void inserisciProiezione() throws IOException {
        int idPellicola;
        
        // Verifica che esista almeno una Sala e una Pellicola prima di inserire una Proiezione
        if(elencoPellicole.isEmpty() || c.isElencoSaleEmpty()) {
            System.out.println("\nErrore: non sono presenti sale o pellicole nel sistema, necessarie per creare uno spettacolo");
            return;
        }
        
        stampaPellicole();
        System.out.println("\nInserire l'ID della pellicola da proiettare");
        idPellicola = Integer.parseInt(bf.readLine());
        
        if((pellicolaSelezionata = elencoPellicole.get(idPellicola)) == null) {
            System.out.println("\nErrore: selezione non valida");
            return;
        } else
            System.out.println("Pellicola selezionata: " + pellicolaSelezionata.getNomePellicola() + ", durata: " + pellicolaSelezionata.getDurata() + " minuti");
       
        // Seleziona Sala in cui proiettare, restutisce salaSelezionata (selezionaSalaPerProiezione)
        if(c.inserisciProiezione(pellicolaSelezionata))
            confermaProiezione();
    }
    
    public void confermaProiezione() {
        c.confermaProiezione();
    }
    
    public void stampaLocandine() {
        for(Map.Entry<Integer, Locandina> entryLocandina : elencoLocandine.entrySet()) 
            System.out.println("\n" + entryLocandina.getValue().toString());
    }
    
    public void eliminaLocandina(int idPellicola) {
        locandinaSelezionata = elencoLocandine.remove(idPellicola);
        
        if(locandinaSelezionata != null)
            System.out.println("Locandina di " + elencoPellicole.get(idPellicola).getNomePellicola() + " eliminata correttamente");    
    }
    
    public void stampaProgrammazione() {
        c.stampaProgrammazione();
    }
    
    public void gestisciProiezioni() throws IOException {
        int scelta;
        
        System.out.println("\nGestione Proiezioni\n"
                + "1. Visualizza Programmazione \n"
                + "2. Modifica Proiezioni \n"
                + "3. Elimina Proiezioni\n"
                + "Altro. Torna al menu");
        
        scelta = Integer.parseInt(bf.readLine());
        
        switch (scelta) {
            case 1: //UC1 Inserimento Sala
                stampaProgrammazione();
                break;
            
            case 2:
                System.out.println("Non ancora implementata...\n");
                break;
                
            case 3:
                System.out.println("Non ancora implementata...\n");
                break;
                
            default:
                menuAmministratore();
        }
        if(c.isElencoProiezioniEmpty()) 
            return;
        gestisciProiezioni();
    }
    
    // TESSERE
    public void inserisciTessera() throws IOException {
        String nome, cognome, codiceFiscale;
        LocalDate dataDiNascita;
        
        System.out.println("\nInserisci il nome del cliente");
        nome = bf.readLine();
        
        System.out.println("Inserisci il cognome del cliente");
        cognome = bf.readLine();
        
        System.out.println("Inserisci la data di nascita del cliente nel formato 'gg/mm/aaaa'");
        
        try {
            dataDiNascita = LocalDate.parse(bf.readLine(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            if( LocalDate.from(dataDiNascita).until(LocalDate.now(), ChronoUnit.YEARS) < 18) {
                System.out.println("Errore: il cliente è minorenne e non può quindi sottoscrivere una tessera\n");
                return;
            }
        } catch(DateTimeParseException ex) {
            System.out.println("Inserimento della data di nascita errato: inserire nel formato 'gg/mm/aaaa'");
            return;
        }
            
        do {
            System.out.println("Inserisci il codice fiscale del cliente");
            codiceFiscale = bf.readLine();
        } while((verificaTesseraInserita(codiceFiscale) == true));
        
        System.out.println("\nRiepilogo:"
                + "\n - Nome: " + nome 
                + "\n - Cognome: " + cognome 
                + "\n - Data di Nascita: " + dataDiNascita.format( DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                + "\n - Codice fiscale: " + codiceFiscale
                + "\nPremere 1 per confermare, 0 per annullare l'inserimento"); 
        
        if(bf.readLine().equals("0")) {
            System.out.println("Inserimento annullato\n");
            return;
        }
        
        tesseraCorrente = new Tessera(nome, cognome, dataDiNascita, codiceFiscale);
        confermaTessera();
    }
    
    public void confermaTessera() {
        elencoTessere.put(tesseraCorrente.getCodiceFiscale(), tesseraCorrente);
        System.out.println("\nInserimento della tessera completato con successo");
    }
    
    public boolean verificaTesseraInserita(String codiceFiscale) {
        if(elencoTessere.containsKey(codiceFiscale)) {
            System.out.println("Esiste già una tessera con il codice fiscale inserito\n");
            return true;
        } else return false;
    }
    
    public void stampaTessere() {
        int i=0;
        if(elencoTessere.isEmpty()) 
            System.out.println("Non esistono tessere nel sistema\n");
        else 
            for(Map.Entry<String, Tessera> set : elencoTessere.entrySet()) 
                System.out.println("\nTessera " + ++i + "\n" + set.getValue().toString());
    }
    
    public void gestisciTessere() throws IOException {
        int scelta;
        
        System.out.println("\nGestione Tessere\n"
                + "1. Visualizza Tessere \n"
                + "2. Modifica Tessera \n"
                + "3. Elimina Tessera\n"
                + "Altro. Torna al menu");
        
        scelta = Integer.parseInt(bf.readLine());
        
        switch (scelta) {
            case 1: 
                stampaTessere();
                break;
            
            case 2:
                System.out.println("Non ancora implementata...\n");
                break;
                
            case 3:
                System.out.println("Non ancora implementata...\n");
                break;
                
            default:
                menuAddetto();
        }
        if(elencoTessere.isEmpty())
            return;
        gestisciTessere();
    }
    
    // VENDITA BIGLIETTO
    public void acquistaBiglietto() throws IOException {
        int idPellicola, idProiezione;
        
        // Scelta della pellicola da visionare
        stampaPellicole();
        System.out.println("\nInserisci l'ID della pellicola da visionare");
        idPellicola = Integer.parseInt(bf.readLine());
        
        if((pellicolaSelezionata = elencoPellicole.get(idPellicola)) == null) {
            System.out.println("\nErrore: selezione non valida");
            return;
        }
        
        //se esistono proiezioni per quella pellicola
        if(c.acquistaBiglietto(idPellicola)) { 
            System.out.println("\nScegli l'ID dello spettacolo");
            idProiezione = Integer.parseInt(bf.readLine());
            scegliProiezione(idProiezione);
        } else { //non esistono proiezioni per quella pellicola
            return;
        }
    }
    
    public void scegliProiezione(int idProiezione) throws IOException {
        if(c.scegliProiezione(idProiezione)) {
            System.out.println("\nPremi 1 per confermare, 0 per uscire");
            if(bf.readLine().equals("1")) 
                effettuaAcquisto();
        }
    }
    
    public void effettuaAcquisto() throws IOException {
        boolean isVIP, isCategoriaProtetta;
        
        // L'addetto specifica se il cliente ha diritto ad un biglietto ridotto
        System.out.println("\nDiritto a biglietto ridotto (minore di 12 anni, over-65, invalido): \n 1. Si \n 2. No");
        isCategoriaProtetta = Boolean.parseBoolean(bf.readLine());
        
        System.out.println("\nPoltrona VIP: \n 1. Si \n 2. No");
        isVIP = Boolean.parseBoolean(bf.readLine());
        
        if(c.effettuaAcquisto(isVIP, isCategoriaProtetta) > 0) {
            //il biglietto è stato creato e il prezzo restituito correttamente
        }
    }
    
    /*
    public void acquistaBiglietto() throws IOException { 
        String titolo; 
        int idPellicola, idProiezione;
        boolean isVIP = false, isCategoriaProtetta, codiceTessera, proiezioneValida;
        
        // Viene inserito il titolo o una parte di esso per stampare i film che sono in proiezione
        System.out.println("\nInserisci il titolo, o una parte, del film da proiettare per filtrare la ricerca "
                + "altrimenti premi Invio per stamparli tutti");
        titolo = bf.readLine();
        
        // Se non vengono trovate pellicole in proiezione con quel titolo, si esce
        if(pellicoleInProiezione(titolo) == 0) {
            System.out.println("\nNon esistono pellicole in proiezione per la ricerca effettuata\n");
            return;
        }
        
        // se vengono trovati dei film in proiezione, si chiede la specifica di quale film visionare
        System.out.println("\nInserire l'ID della pellicola da visionare");
        idPellicola = Integer.parseInt(bf.readLine());
        
        // Controllo aggiuntivo se viene inserito un ID con un film che non era fra quelli stampati
        // e che non ha proiezioni, terminando l'acquisto in tal caso 
        if(numeroProiezioniPerDataPellicola(idPellicola) == 0) {
            System.out.println("Non sono state trovate proiezioni per la pellicola selezionata\n");
            return;
        }
        
        // Scelta dell'ID dello spettacolo da visionare 
        System.out.println("\nScegli lo spettacolo: ");
        idProiezione = Integer.parseInt(bf.readLine());
        
        // Si verifica che la proiezione scelta sia effettivamente del film richiesto prima e 
        // che ci siano ancora posti 
        proiezioneValida = c.verificaProiezioneDaVisionare(idProiezione, idPellicola);
        
        // Se la proezione non è valida esce (non esiste, non è del film richiesto, non ha posti disponibili)
        if(proiezioneValida == false)
            return;
        
        // se ci sono ancora poltrone VIP viene chiesto al cliente se lo desidera
        if(c.haPostiVIP(idProiezione)) {
            System.out.println("Poltrona VIP: \n 1. Si \n 2. No");
            isVIP = Boolean.parseBoolean(bf.readLine());
        }
        
        // L'addetto specifica se il cliente ha diritto ad un biglietto ridotto
        System.out.println("Diritto a biglietto ridotto (minore di 12 anni, over-65, invalido): \n 1. Si \n 2. No");
        isCategoriaProtetta = Boolean.parseBoolean(bf.readLine());
        
        // qui agisce la classe Cinema creando il biglietto e restituisce il costo totale del biglietto
        
        // poi ritorna dentro Paradox con il prezzo del biglietto generato
        
        // si chiede se il cliente ha la tessera
        
        // la esibisce e si verifica se ha abbastanza punti per richiedere l'omaggio, altrimenti aggiunge i punti
        
        // dopo il pagamento confermaBiglietto lo aggiunge all'elenco dei biglietti venduti 
        
        // Se il cliente la possiede, viene richiesto di esibirla per accumulare punti
        System.out.println("Tessera fedeltà: \n 1. Si \n 2. No");
        do {
            if(Boolean.parseBoolean(bf.readLine())) {
                System.out.println("Inserire il codice identificativo della tessera");
                if((tesseraSelezionata = trovaTesseraEsibita(bf.readLine())) != null) 
                    System.out.println("Tessera trovata: \n" + tesseraSelezionata.toString());
                else
                    System.out.println("Tessera non trovata: riprovare? \n 1. Si \n 2. No");
            }
        } while (bf.readLine().equals("1"));
        
    }
    
    
    public Proiezione scegliProiezione(int idProiezione) {
        return c.scegliProiezione(idProiezione);
    }
    
    
    public int pellicoleInProiezione(String titolo) {
        int count = 0;
        for(Map.Entry<Integer, Pellicola> entryPellicola : elencoPellicole.entrySet()) {
            if(entryPellicola.getValue().getNomePellicola().contains(titolo))
                if(c.isPellicolaProiettata(entryPellicola.getKey())) {
                    count++;
                    System.out.println("\n" + entryPellicola.getValue().toString());
                }
        }
        return count;
    }
    
    public int numeroProiezioniPerDataPellicola(int idPellicola) throws IOException {
        return c.stampaProiezioniPerDataPellicola(idPellicola);
    }
    
    */
    
    public Tessera trovaTesseraEsibita(String codiceTessera) {
        return elencoTessere.get(codiceTessera);
    }
    
    public static String getInputPath(String s) {
         /*Send a path (a String path) to open in a specific directory
         or if null default directory */
         JFileChooser jd = s == null ? new JFileChooser() : new JFileChooser(s);

         jd.setDialogTitle("Choose input file");
         int returnVal= jd.showOpenDialog(null);

         /* If user didn't select a file and click ok, return null Path object*/
         if (returnVal != JFileChooser.APPROVE_OPTION) return null;
         return jd.getSelectedFile().toString();
    }
    
    public static void main(String[] args) throws IOException {
        getInstance().menuLogin();   
    }
}
