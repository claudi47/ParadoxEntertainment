package com.mycompany.paradoxentertainment;

import java.time.DayOfWeek;
import java.time.LocalDate;

/**
 *
 * @author Daniele
 */
public class TicketFactory {
   
    // Factory Method
    public static Biglietto creaBiglietto(boolean isVIP, boolean isCategoriaProtetta, Proiezione proiezione) {
        DayOfWeek giornoDellaSettimana = LocalDate.now().getDayOfWeek();
        Biglietto biglietto;
        
        if(giornoDellaSettimana.toString().equals("SATURDAY") || giornoDellaSettimana.toString().equals("SUNDAY"))
            biglietto = new BigliettoWeekend(isVIP, isCategoriaProtetta, proiezione);
        else
            biglietto = new BigliettoFeriale(isVIP, isCategoriaProtetta, proiezione);
        
        return biglietto;
    }
}
