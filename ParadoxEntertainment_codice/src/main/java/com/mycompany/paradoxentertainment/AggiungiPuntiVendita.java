package com.mycompany.paradoxentertainment;

/**
 *
 * @author Daniele
 */
public class AggiungiPuntiVendita implements UpdatePointsStrategy {
    
    @Override
    public void aggiornaPunti(Tessera tessera, float costoBiglietto) {
        int puntiDaAggiungere = (int)(costoBiglietto*10);
        tessera.setPunti(tessera.getPunti() + puntiDaAggiungere);
        System.out.println("\nSono stati aggiunti " + puntiDaAggiungere + " punti per l'acquisto del biglietto");
    }
}
