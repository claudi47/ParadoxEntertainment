/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.paradoxentertainment;

import java.io.Serializable;

/**
 *
 * @author Daniele
 */
public class AggiungiPosto implements UpdateSeatsStrategy, Serializable {

    @Override
    public void aggiornaPosti(Proiezione proiezione, boolean isVIP) {
        if(isVIP)
            proiezione.setPostiRimanentiVIP(proiezione.getPostiRimanentiVIP()+1);
        else 
            proiezione.setPostiRimanentiStandard(proiezione.getPostiRimanentiStandard()+1);
        proiezione.setPostiRimanentiTot(proiezione.getPostiRimanentiStandard()+proiezione.getPostiRimanentiVIP());
    }
}
