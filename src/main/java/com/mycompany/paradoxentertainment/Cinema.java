package com.mycompany.paradoxentertainment;

import java.util.LinkedList;

/**
 *
 * @author Daniele
 */
public class Cinema {
    private static Cinema cinema;
    private Sala salaCorrente, salaSelezionata;
    LinkedList<Sala> elencoSale;
    
    
    public static Cinema getInstance() {
        if (cinema == null) {
            cinema = new Cinema();
        }
        return cinema;
    }
    
    private Cinema() {
        this.elencoSale = new LinkedList();
    }
    
    public boolean verificaNomeSala(String nomeSala) {
        for(Sala s: elencoSale) {
            if(nomeSala.equals(s.getNomeSala())) {
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
        elencoSale.add(salaCorrente);
        salaCorrente.toString();
    }
    
    public void stampaSale() {
        for(Sala S: elencoSale) 
            System.out.println(S.toString());
    }
}
