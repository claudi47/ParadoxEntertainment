package com.mycompany.paradoxentertainment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author Daniele
 */
public class ParadoxEntertainment {
    private static ParadoxEntertainment paradoxEntertainment; //Singleton
    public static Cinema c;
    static Sala salaCorrente;
    static BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
    
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
                System.out.println("Aggiungi Film");
                System.out.println("Eseguo... \n.\n.\n.");
                System.out.println("Termino codice addetto, logout...\n\n");
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
        boolean conferma;
        
        do {
            System.out.println("Inserisci l'identificativo della sala");
            nomeSala = bf.readLine();
        } while(c.verificaNomeSala(nomeSala) == true);
        
        System.out.println("Inserisci il numero di posti standard");
        postiStandard = Integer.parseInt(bf.readLine());
        
        System.out.println("Inserisci il numero di posti VIP");
        postiVIP = Integer.parseInt(bf.readLine());
        
        postiTot = postiStandard+postiVIP;
        
        //Cinema crea l'oggetto Sala e lo restituisce a Paradox
        c.inserisciSala(nomeSala, postiStandard, postiVIP);
        
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
        
        //Funzione di conferma dell'aggiunta della sala all'elenco Sale del cinema
        confermaSala();
        
        System.out.println("Inserimento confermato!");
        c.stampaSale();
    }
    
    public static void confermaSala() {
        c.confermaSala();
    }
            
    public static void main(String[] args) throws IOException {
        getInstance().menuLogin();   
    }
}
