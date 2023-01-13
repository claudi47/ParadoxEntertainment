package com.mycompany.paradoxentertainment;

/**
 *
 * @author Daniele
 */
public class SottraiPuntiBigliettoOmaggio implements UpdatePointsStrategy {

    @Override
    public void aggiornaPunti(Tessera tessera, float costoBiglietto) {
        int puntiDaSottrarre = (int)(costoBiglietto*100);
        tessera.setPunti(tessera.getPunti() - puntiDaSottrarre);
        System.out.println("\nSulla tessera sono presenti sufficienti punti per ottenere il biglietto omaggio, riscattato al costo di " + puntiDaSottrarre + " punti");
    }
}
