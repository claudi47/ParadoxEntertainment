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
    private int idPellicole = 0;
    private Map<Integer, Locandina> elencoLocandine; // <IdPellicola, Locandina>
    private Locandina locandinaCorrente, locandinaSelezionata;
    private Map<Integer, Tessera> elencoTessere;
    private int idTessere = 0;
    private Tessera tesseraCorrente, tesseraSelezionata;
    UpdatePointsStrategy updatePointsStrategy;
    
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
                + "2. Aggiungi Pellicola \n"
                + "3. Aggiungi Proiezione\n"
                + "4. Gestisci Sale\n"
                + "5. Gestisci Pellicole\n"
                + "6. Gestisci Proiezioni\n"
                + "7. Stampa Locandine\n"
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
                if(c.getNumeroProiezioni() == 0) 
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
                + "1. Visulizza Proiezioni \n"
                + "2. Vendita Biglietto \n"
                + "3. Creazione Tessera \n"
                + "4. Gestisci Tessere \n"
                + "5. Rimborso Biglietto \n"
                + "Altro. Torna al Login");
        
        scelta = Integer.parseInt(bf.readLine());
        
        switch (scelta) {
            case 1:
                if(c.getNumeroProiezioni() == 0)
                    System.out.println("Non esistono proiezioni\n");
                else
                    stampaProgrammazione();
                break;
                
            case 2: 
                if(c.getNumeroProiezioni() == 0)
                    System.out.println("Non esistono proiezioni\n");
                else 
                    acquistaBiglietto();
                break;
            
            case 3:
                inserisciTessera();
                break;
                
            case 4:
                if(elencoTessere.isEmpty())
                    System.out.println("Non esistono tessere\n");
                else
                    gestisciTessere();
                break;
            
            case 5:
                effettuaReso();
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
        
        System.out.println("\nInserimento Sala:");
        do {
            System.out.println("Inserisci il nome/n° della sala");
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
                    + "Premere 1 per confermare, altrimenti per annullare l'inserimento"
                    );
              
        if(!bf.readLine().equals("1")) {
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
                modificaSala();
                break;
                
            case 3:
                eliminaSala();
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
    
    public void modificaSala() throws IOException {
        c.modificaSala();
    }
    
    public void eliminaSala() throws IOException {
        c.eliminaSala();
    }
    
    // PELLICOLE
    public void inserisciPellicola() throws IOException {
        String nomePellicola;
        String regista;
        int anno;
        String genere;
        int durata;
        
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
        
        System.out.println("\nRiepilogo: \n"
                    + " - Titolo: " + nomePellicola + "\n"
                    + " - Regista: " + regista + "\n"
                    + " - Anno: " + anno + "\n"
                    + " - Genere: " + genere + "\n"
                    + " - Durata: " + durata + "\n"
                    + "Premere 1 per confermare, altrimenti per annullare l'inserimento");
        
        if(!bf.readLine().equals("1")) {
            System.out.println("Inserimento pellicola annullato\n");
            return;
        }
        
        // creazione oggetto Pellicola
        pellicolaCorrente = new Pellicola(nomePellicola, regista, anno, genere, durata, ++idPellicole);
        confermaPellicola();
        
        // Specifica di una locandina 
        System.out.println("Premere 1 se si desidera creare una locandina per la pellicola, altrimenti per saltare questo passaggio");
        if(bf.readLine().equals("1")) {
            inserisciLocandina();
        }
    }
    
    public void confermaPellicola() {
        elencoPellicole.put(pellicolaCorrente.getIdPellicola(), pellicolaCorrente);
        System.out.println("\nInserimento della Pellicola completato con successo\n");
    }
    
    public boolean verificaPellicola(String nomePellicola, String regista, int anno) {
        for (HashMap.Entry<Integer, Pellicola> entry : elencoPellicole.entrySet()) {
            if(entry.getValue().getNomePellicola().equals(nomePellicola) && 
                        entry.getValue().getRegista().equals(regista) && 
                        entry.getValue().getAnno() == anno) {
                    System.out.println("\nErrore: pellicola già presente nel sistema");
                    return true;   
            }   
        }
        return false;
    }
    
    public boolean verificaPellicola(String nomePellicola, String regista, int anno, Pellicola pellicolaDaModificare) {
        for (HashMap.Entry<Integer, Pellicola> entry : elencoPellicole.entrySet()) {
            if(entry.getValue().getNomePellicola().equals(nomePellicola) && 
                        entry.getValue().getRegista().equals(regista) && 
                        entry.getValue().getAnno() == anno &&
                        !entry.getValue().equals(pellicolaDaModificare)){
                    System.out.println("\nErrore: pellicola già presente nel sistema");
                    return true;   
            }   
        }
        return false;
    }
    
    public void stampaPellicole() {
        for(Map.Entry<Integer, Pellicola> entryPellicola : elencoPellicole.entrySet()) 
            System.out.println("\n" + entryPellicola.getValue().toString());
    }
    
    public Pellicola getPellicola(int idPellicola) {
        return elencoPellicole.get(idPellicola);
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
        } while((verificaPellicola(titolo, regista, anno, pellicolaSelezionata) == true));
        
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
                + "\n\nInserire 1 per confermare, altrimenti per annullare la modifica");
        if(bf.readLine().equals("1")) {
            pellicolaSelezionata.modificaPellicola(titolo, regista, anno, genere, durata);
            System.out.println("Modifica effettuata con successo\n");
            if(variazioneDurata && c.proiezioniPerDataPellicola(idPellicola) > 0) {
                System.out.println("\nPoiché la pellicola ha subito un aumento della durata, le relative proiezioni sopra elencate sono state eliminate");
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
                System.out.println("\nIn seguito all'eliminazione della pellicola, anche le relative proiezioni sopra elencate sono state eliminate");
                c.eliminaProiezioniPerPellicola(idPellicola);
            }
            pellicolaSelezionata = elencoPellicole.remove(idPellicola);
        } else
            System.out.println("Scelta non valida: pellicola inesistente nel sistema\n");
    }
    
    public boolean isPellicolaProiettata(Pellicola pellicola) {
        return c.isPellicolaProiettata(pellicola.getIdPellicola());
    }
    
    // Stampa tutte le pellicole che risultano avere almeno una proiezione associata
    public void stampaPellicoleInProiezione() {
        if(!elencoPellicole.isEmpty()) {
            for(Map.Entry<Integer, Pellicola> entryPellicola : elencoPellicole.entrySet()) {
                if(isPellicolaProiettata(entryPellicola.getValue())) {
                    System.out.println("\n" + entryPellicola.getValue().toString());
                }
            }
        } else
            System.out.println("Non esistono pellicole\n");
    }
    
    
    // LOCANDINE
    public void inserisciLocandina() throws IOException {
        String path = null;
        int baseStampa = 0, altezzaStampa = 0;
        
        System.out.println("Inserisci il percorso dell'immagine");
        path = bf.readLine();
        System.out.println("Inserisci la base di stampa");
        baseStampa = Integer.parseInt(bf.readLine());
        System.out.println("Inserisci l'altezza di stampa");
        altezzaStampa = Integer.parseInt(bf.readLine());
        
        System.out.println("\nRiepilogo: \n"
                        + " - Percorso locandina: " + path + "\n"
                        + " - Dimensione base locandina: " + baseStampa + "\n"
                        + " - Dimensione altezza locandina: " + altezzaStampa + "\n"
                        + "Premere 1 per confermare, altrimenti per annullare l'inserimento");
        if(bf.readLine().equals("1")) {
            locandinaCorrente = new Locandina(path, baseStampa, altezzaStampa, pellicolaCorrente);
            confermaLocandina();
        } else 
            System.out.println("\nInserimento locandina annullato\n");
    }
    
    public void confermaLocandina() {
        elencoLocandine.put(locandinaCorrente.getPellicola(), locandinaCorrente);
        System.out.println("\nInserimento della Locandina completato con successo\n");
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
        
        if((pellicolaSelezionata = getPellicola(idPellicola)) == null) {
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
                modificaProiezione();
                break;
                
            case 3:
                eliminaProiezione();
                break;
                
            default:
                menuAmministratore();
        }
        if(c.getNumeroProiezioni() == 0) 
            return;
        gestisciProiezioni();
    }
    
    public void modificaProiezione() throws IOException {
        int idProiezione, idPellicola, numeroBigliettiVenduti;
        stampaProgrammazione();
        System.out.println("\nInserisci l'ID della proiezione da modificare");
        idProiezione = Integer.parseInt(bf.readLine());
        
        if(c.isProiezioneEsistente(idProiezione)) {
            if( (numeroBigliettiVenduti = c.bigliettiVendutiProiezione(idProiezione)) == 0) {
                stampaPellicole();
                System.out.println("\nInserire l'ID della pellicola da proiettare");
                idPellicola = Integer.parseInt(bf.readLine());
                
                if((pellicolaSelezionata = elencoPellicole.get(idPellicola)) != null) {
                    System.out.println("Pellicola selezionata: " + pellicolaSelezionata.getNomePellicola() + ", durata: " + pellicolaSelezionata.getDurata() + " minuti");
                    c.modificaProiezione(pellicolaSelezionata);
                } else 
                    System.out.println("\nErrore: selezione non valida\n");
            } else 
                System.out.println("\nPer la proiezione selezionata risulta già venduta una quantità di biglietti pari a " + numeroBigliettiVenduti + " e non può quindi essere modificata\n");
        } else 
            System.out.println("\nLa proiezione selezionata non esiste\n");
    }
    
    public void eliminaProiezione() throws IOException {
        c.eliminaProiezione();
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
                System.out.println("\nErrore: il cliente è minorenne e non può quindi sottoscrivere una tessera\n");
                return;
            }
        } catch(DateTimeParseException ex) {
            System.out.println("Inserimento della data di nascita errato: inserire nel formato 'gg/mm/aaaa'");
            return;
        }
            
        System.out.println("Inserisci il codice fiscale del cliente");
        codiceFiscale = bf.readLine();
        
        if(verificaTessera(codiceFiscale) == true) 
            return;
        
        System.out.println("\nRiepilogo:"
                + "\n - Nome: " + nome 
                + "\n - Cognome: " + cognome 
                + "\n - Data di Nascita: " + dataDiNascita.format( DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                + "\n - Codice fiscale: " + codiceFiscale
                + "\n\nPremere 1 per confermare, altrimenti per annullare l'inserimento della tessera"); 
        
        if(!bf.readLine().equals("1")) {
            System.out.println("Inserimento annullato\n");
            return;
        }
        
        tesseraCorrente = new Tessera(nome, cognome, dataDiNascita, codiceFiscale, ++idTessere);
        confermaTessera();
    }
    
    public void confermaTessera() {
        elencoTessere.put(tesseraCorrente.getIdTessera(), tesseraCorrente);
        System.out.println("\nInserimento della tessera completato con successo");
    }
    
    public boolean verificaTessera(String codiceFiscale) {
        for(Map.Entry<Integer, Tessera> entryTessera : elencoTessere.entrySet()) {
            if(entryTessera.getValue().getCodiceFiscale().equals(codiceFiscale)) {
                System.out.println("Esiste già una tessera con il codice fiscale: \n" + codiceFiscale);
                return true;
            }
        }
        return false;
    }
    
    public boolean verificaTessera(String codiceFiscale, Tessera tesseraDaModificare) {
        for(Map.Entry<Integer, Tessera> entryTessera : elencoTessere.entrySet()) {
            if(entryTessera.getValue().getCodiceFiscale().equals(codiceFiscale) &&
                    !entryTessera.getValue().equals(tesseraDaModificare)) {
                System.out.println("Esiste già una tessera con il codice fiscale inserito\n" + codiceFiscale);
                return true;
            }
        }
        return false;
    }
    
    public void stampaTessere() {
        int i=0;
        if(elencoTessere.isEmpty()) 
            System.out.println("Non esistono tessere nel sistema\n");
        else 
            for(Map.Entry<Integer, Tessera> entryTessera : elencoTessere.entrySet()) 
                System.out.println(entryTessera.getValue().toString());
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
                modificaTessera();
                break;
                
            case 3:
                eliminaTessera();
                break;
                
            default:
                menuAddetto();
        }
        if(elencoTessere.isEmpty())
            return;
        gestisciTessere();
    }
    
    public void eliminaTessera() throws IOException {
        stampaTessere();
        System.out.println("\nInserire l'ID della tessera da eliminare: ");
        tesseraSelezionata = elencoTessere.get(Integer.parseInt(bf.readLine()));
        
        if(tesseraSelezionata != null) {
            System.out.println("\nTessera selezionata:" + tesseraSelezionata.toString() 
                + "\nInserisci 1 per confermare l'eliminazione, altro per annullare");
            if(bf.readLine().equals("1")) {
                elencoTessere.remove(tesseraSelezionata.getIdTessera());
                System.out.println("\nEliminazione effettuata con successo\n");
            } else
                System.out.println("\nEliminazione annullata\n");
        } else 
            System.out.println("\nTessera selezionata non valida\n");
    }
    
    public void modificaTessera() throws IOException {
        String nome, cognome, codiceFiscale;
        LocalDate dataDiNascita;
        
        stampaTessere();
        System.out.println("\nInserire l'ID della tessera da modificare:");
        tesseraSelezionata = elencoTessere.get(Integer.parseInt(bf.readLine()));
        
        if(tesseraSelezionata != null) {
            System.out.println("\nTessera selezionata:" + tesseraSelezionata.toString());
            
            System.out.println("\nInserisci il nome del cliente");
            nome = bf.readLine();

            System.out.println("Inserisci il cognome del cliente");
            cognome = bf.readLine();

            System.out.println("Inserisci la data di nascita del cliente nel formato 'gg/mm/aaaa'");

            try {
                dataDiNascita = LocalDate.parse(bf.readLine(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                if( LocalDate.from(dataDiNascita).until(LocalDate.now(), ChronoUnit.YEARS) < 18) {
                    System.out.println("\nErrore: il cliente è minorenne e non può quindi sottoscrivere una tessera\n");
                    return;
                }
            } catch(DateTimeParseException ex) {
                System.out.println("\nInserimento della data di nascita errato: inserire nel formato 'gg/mm/aaaa'");
                return;
            }

            System.out.println("Inserisci il codice fiscale del cliente");
            codiceFiscale = bf.readLine();
            if(verificaTessera(codiceFiscale, tesseraSelezionata) == true) 
                return;

            System.out.println("\nRiepilogo:"
                    + "\n - Nome: " + nome 
                    + "\n - Cognome: " + cognome 
                    + "\n - Data di Nascita: " + dataDiNascita.format( DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                    + "\n - Codice fiscale: " + codiceFiscale
                    + "\n\nInserire 1 per confermare la modifica, altro per annullare"); 

            if(bf.readLine().equals("1")) {
                tesseraSelezionata.modificaTessera(nome, cognome, dataDiNascita, codiceFiscale);
                System.out.println("Modifica completata con successo\n");
            } else
                System.out.println("Modifica annullata\n");
        } else
            System.out.println("\nLa tessera selezionata non esiste\n");
    }
    
    public Tessera getTessera(int idTessera) {
        return elencoTessere.get(idTessera);
    }
    
    public Tessera trovaTessera() throws IOException {
        String inserimentoTessera;
        int idTessera;
        
        if(!elencoTessere.isEmpty()) {
            do {
                System.out.println("\nInserire l'ID della tessera o premere Invio per stamparle tutte:");
                inserimentoTessera = bf.readLine();
                if(inserimentoTessera.equals("")) {
                    stampaTessere();
                    System.out.println("\nInserisci l'ID della tessera: ");
                    inserimentoTessera = bf.readLine();
                }

                idTessera = Integer.parseInt(inserimentoTessera);
                if(getTessera(idTessera) != null) 
                    return getTessera(idTessera);
                else
                    System.out.println("\nTessera non trovata: inserire 1 per riprovare, altrimenti per annullare");
            } while(bf.readLine().equals("1"));
            return null;
        }
        System.out.println("\nNon sono presenti tessere nel sistema");
        return null;
    }
    
    // VENDITA BIGLIETTO
    
    // ACQUISTA BIGLIETTO PARADOX: SCELTA DI UNA PELLICOLA E VERIFICA DELLA SUA ESISTENZA
    public void acquistaBiglietto() throws IOException {
        int idPellicola;
        
        // Scelta della pellicola da visionare
        stampaPellicoleInProiezione();
        System.out.println("\nInserisci l'ID della pellicola da visionare");
        idPellicola = Integer.parseInt(bf.readLine());
        
        // Verifica della pellicola selezionata
        if((pellicolaSelezionata = elencoPellicole.get(idPellicola)) == null) {
            System.out.println("\nErrore: la pellicola selezionata non esiste nel sistema\n");
            return;
        }
        
        if(isPellicolaProiettata(pellicolaCorrente) == false) {
            System.out.println("\nErrore: la pellicola selezionata non ha alcuna proiezione programmata\n");
            return;
        }
        
        // ACQUISTA BIGLIETTO CINEMA: VERIFICA SE LA PELLICOLA SCELTA HA DELLE PROIEZIONI VALIDE 
        if(c.acquistaBiglietto(idPellicola)) {
            // SCEGLI PROIEZIONE PARADOX: SCELTA DELLA PROIEZIONE TRA QUELLE VALIDE DEL FILM SCELTO
            scegliProiezione();
        }
    }
    
    // SCEGLI PROIEZIONE PARADOX: SCELTA DELLA PROIEZIONE TRA QUELLE VALIDE DEL FILM SCELTO
    public void scegliProiezione() throws IOException {
        int idProiezione; 
        
        System.out.println("\nScegli l'ID dello spettacolo");
        idProiezione = Integer.parseInt(bf.readLine());
        
        // SCEGLI PROIEZIONE CINEMA: VERIFICA CHE LA PROIEZIONE SCELTA ESISTA E ABBIA ANCORA POSTI LIBERI
        if(c.scegliProiezione(idProiezione)) {
            System.out.println("\nPremi 1 per confermare, altrimenti per uscire");
            if(bf.readLine().equals("1")) 
                // EFFETTUA ACQUISTO PARADOX: SCEGLIE IL TIPO DI POSTO, DI BIGLIETTO E FA IL CONTROLLO TESSERA
                effettuaAcquisto();
        }
    }
    
    // EFFETTUA ACQUISTO PARADOX: SCEGLIE IL TIPO DI POSTO, DI BIGLIETTO E FA IL CONTROLLO TESSERA
    public void effettuaAcquisto() throws IOException {
        boolean isVIP, isCategoriaProtetta, isTesseraEsibita = false;
        float costoBiglietto;
        int idTessera;
        String inserimentoTessera;
        
        // L'addetto specifica se il cliente ha diritto ad un biglietto ridotto
        System.out.println("\nInserire 1 se il cliente ha diritto ad un biglietto ridotto (minore di 12 anni, over-65, invalido), altrimenti per un biglietto intero");
        isCategoriaProtetta = (bf.readLine().equals("1"));
        
        System.out.println("\nInserire 1 per la poltrona VIP, altrimenti per la poltrona standard");
        isVIP = (bf.readLine().equals("1"));
        
        if( (costoBiglietto = c.effettuaAcquisto(isVIP, isCategoriaProtetta)) > 0) {
            System.out.println("PARADOX: " + costoBiglietto + "\n");
            
            if(!elencoTessere.isEmpty()) {
                // Inserimento tessera fedeltà
                System.out.println("\nInserire 1 se il cliente esibisce la tessera fedeltà, altrimenti per saltare il passaggio");
                if(bf.readLine().equals("1")) {
                    do {
                        System.out.println("\nInserire l'ID della tessera o premere Invio per stamparle prima tutte:");
                        inserimentoTessera = bf.readLine();
                        if(inserimentoTessera.equals("")) {
                            stampaTessere();
                            System.out.println("\nInserisci l'ID della tessera: ");
                            inserimentoTessera = bf.readLine();
                        }

                        idTessera = Integer.parseInt(inserimentoTessera);
                        tesseraSelezionata = getTessera(idTessera);

                        if(tesseraSelezionata != null) {
                            isTesseraEsibita = true;
                            System.out.println("\nTessera selezionata: \n " + tesseraSelezionata.toString());
                            
                            if(tesseraSelezionata.getPunti() >= costoBiglietto*100)
                                updatePointsStrategy = new SottraiPuntiBigliettoOmaggio();
                            else
                                updatePointsStrategy = new AggiungiPuntiVendita();
                            break;
                        }
                        else
                            System.out.println("\nTessera non trovata, inserire 1 per riprovare, altrimenti per annullare l'inserimento della tessera");
                    } while(bf.readLine().equals("1"));
                }
            }
            System.out.println("\nCosto biglietto: " + costoBiglietto + "€, inserire 1 per proseguire con l'acquisto");
            if(bf.readLine().equals("1"))
                confermaAcquisto(costoBiglietto, isTesseraEsibita);
            else
                System.out.println("\nVendita annullata\n");
        }
    }
    
    private void confermaAcquisto(float costoBiglietto, boolean isTesseraEsibita) throws IOException {
        c.confermaAcquisto();
        if(isTesseraEsibita)
            updatePointsStrategy.aggiornaPunti(tesseraSelezionata, costoBiglietto);
        System.out.println("\nVendita completata");
    }
    
    public void effettuaReso() throws IOException {
        // Trova tessera
        tesseraSelezionata = trovaTessera();
        
        if(tesseraSelezionata != null) {
            System.out.println("\nTessera selezionata: \n" + tesseraSelezionata.toString() + 
                    "\nInserire 1 per confermare e proseguire, altrimenti per selezionare un'altra tessera");
            if(!bf.readLine().equals("1"))
                effettuaReso();
            else {
                if(c.effettuaReso()) {
                    System.out.println("\nInserire 1 per confermare il rimborso del biglietto, altrimenti per annullare");
                    if(bf.readLine().equals("1")) {
                        confermaReso();
                    }
                }
            }
        } else
            System.out.println("Operazione di rimborso annullata\n");
    }
    
    public void confermaReso() {
        float prezzoBiglietto;
        prezzoBiglietto = c.confermaReso();
        if(prezzoBiglietto > 0) {
            updatePointsStrategy = new AggiungiPuntiRimborso();
            updatePointsStrategy.aggiornaPunti(tesseraSelezionata, prezzoBiglietto);
            System.out.println("Procedura di rimborso completata");
        }
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