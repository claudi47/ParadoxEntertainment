package com.mycompany.paradoxentertainment;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.HashMap;
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
    private Map<Integer, Pellicola> elencoPellicole;
    private Pellicola pellicolaCorrente, pellicolaSelezionata;
    private Map<Integer, Locandina> elencoLocandine;
    
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
    }
    
    public void menuLogin() throws IOException {
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
        
    public void menuAmministratore() throws IOException {
        int scelta;
        
        System.out.println("\nMenu Amministratore \n"
                + "1. Aggiungi Sala \n"
                + "2. Aggiungi Film \n"
                + "3. Aggiungi Proiezione\n"
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
                
            default:
                System.out.println("Logout \n\n");
                break;
        }
        menuAmministratore();
    }
            
    public void inserisciSala() throws IOException {
        String nomeSala;
        int postiStandard;
        int postiVIP;
        int postiTot;
        
        do {
            System.out.println("\nInserisci l'identificativo della sala");
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
    
    public void confermaSala() {
        c.confermaSala();
    }
    
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
        Locandina locandina = null;
               
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
        System.out.println("\nInserimento completato della Pellicola:\n" + elencoPellicole.get(pellicolaCorrente.getIdPellicola()));
        
        if(isLocandina) {
            locandina = pellicolaCorrente.inserisciLocandina(path, baseStampa, altezzaStampa);
            elencoLocandine.put(locandina.getPellicola(), locandina);
            System.out.println(elencoLocandine.get(locandina.getPellicola()));
        }
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
            
    public void confermaPellicola() {
        elencoPellicole.put(pellicolaCorrente.getIdPellicola(), pellicolaCorrente);
    }
    
    public void inserisciProiezione() throws IOException {
        if(elencoPellicole.isEmpty() || c.elencoSale.isEmpty()) {
            System.out.println("Errore: non sono presenti sale o pellicole nel sistema, necessarie per creare uno spettacolo\n");
            return;
        }
        
        c.stampaSale();
        System.out.println("Inserisci l'id della sala in cui tenere la proiezione\n");
        System.out.println((c.elencoSale.get(Integer.parseInt(bf.readLine())-1)).toString());
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
