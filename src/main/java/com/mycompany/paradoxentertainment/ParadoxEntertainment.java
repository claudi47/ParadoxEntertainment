package com.mycompany.paradoxentertainment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Daniele
 */
public class ParadoxEntertainment {
    private static ParadoxEntertainment paradoxEntertainment; //Singleton
    private static Cinema c;
    static BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
    private static TreeMap<Integer, Pellicola> elencoPellicole;
    private static Pellicola pellicolaCorrente, pellicolaSelezionata;
    
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
    }
    
    public static void menuLogin() throws IOException {
        int scelta;
        
        System.out.println("Login: \n"
                        + "1. Accedi come Amministratore \n"
                        + "2. Accedi come Addetto");
        
        scelta = Integer.parseInt(bf.readLine());
        
        switch (scelta) {
            case 1:
                menuAmministratore();
                break;
            
            case 2:
                System.out.println("Benenuto Addetto");
                /*
                codice Addetto
                */
                System.out.println("Eseguo... \n.\n.\n.");
                System.out.println("Termino codice addetto, logout...\n\n");
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
    }
        
    public static void menuAmministratore() throws IOException {
        int scelta;
        
        System.out.println("\nMenu Amministratore \n "
                + "1. Aggiungi Sala \n "
                + "2. Aggiungi Film \n"
                + "Altro. Torna al Login \n");
        
        scelta = Integer.parseInt(bf.readLine());
        
        switch (scelta) {
            case 1: //UC1 Inserimento Sala
                inserisciSala();
                menuAmministratore();
                break;
            
            case 2:
                inserisciPellicola();
                menuAmministratore();
                break;
            
            default:
                System.out.println("Logout \n\n");
                break;
        }
    }
            
    public static void inserisciSala() throws IOException {
        String nomeSala;
        int postiStandard;
        int postiVIP;
        int postiTot;
        
        do {
            System.out.println("Inserisci l'identificativo della sala");
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
        
        System.out.println("Inserimento confermato!");
        c.stampaSale();
    }
    
    public static void confermaSala() {
        c.confermaSala();
    }
    
    public static void inserisciPellicola() throws IOException {
        String nomePellicola;
        String regista;
        int anno;
        String genere;
        int durata;
        int idPellicola;
        
        do {
            System.out.println("Inserisci il titolo della pellicola");
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
                    + "Premere 1 per confermare, 0 per annullare l'inserimento"
                    );
        
        if(Integer.parseInt(bf.readLine()) == 0) {
            System.out.println("Inserimento annullato\n");
            return;
        }
        
        // creazione oggetto Pellicola
        pellicolaCorrente = new Pellicola(nomePellicola, regista, anno, genere, durata);
        
        // aggiunta all'elenco delle pellicole
        pellicolaCorrente.setIdPellicola(elencoPellicole.firstKey());
        
        /* verifica come funzionano le TreeMap:
        TreeMaps don't work that way. They're not an indexed collection (unlike Lists), so what you're trying to do just won't work.
        You need to remove the old one, if the key changes, and reinsert the new key-value pair.
        
        forse meglio una List in cui si ha l'oggetto Pellicola col suo ID che viene aggiunto in elencoPellicole[ID]?
        */
        elencoPellicole.
        
    }
    
    public static boolean verificaPellicola(String nomePellicola, String regista, int anno) {
        for (Map.Entry<Integer, Pellicola> entry : elencoPellicole.entrySet()) {
            if(entry.getValue().getNomePellicola().equals(nomePellicola) &&
                    entry.getValue().getRegista().equals(regista) &&
                        entry.getValue().getAnno() == anno) 
                return true;         
        } 
        return false;
    }
            
    public static void main(String[] args) throws IOException {
        getInstance().menuLogin();   
    }
}
