/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.paradoxentertainment;

/**
 *
 * @author Daniele
 */
public class AggiungiPuntiRimborso implements UpdatePointsStrategy {
    
    @Override
    public void aggiornaPunti(Tessera tessera, float costoBiglietto) {
        int puntiDaAggiungere = (int)(costoBiglietto*100);
        tessera.setPunti(tessera.getPunti() + puntiDaAggiungere);
        System.out.println("\nSono stati aggiunti " + puntiDaAggiungere + " punti come rimborso \n");
    }
}
