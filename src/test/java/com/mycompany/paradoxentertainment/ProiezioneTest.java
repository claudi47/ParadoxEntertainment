package com.mycompany.paradoxentertainment;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class ProiezioneTest {

    static Cinema cinema;
    static ParadoxEntertainment paradox;

    Proiezione Pr;

    @BeforeAll
    public static void initTest() {
        cinema = Cinema.getInstance();
        paradox = ParadoxEntertainment.getInstance();
    }

    @BeforeEach
    public void initProiezione() throws IOException {
        InputStream originalIN = System.in;
        String input = "1\n0\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        paradox.setBf(new BufferedReader(new InputStreamReader(System.in)));

        paradox.inserisciPellicola("Avengers", "Feige", 2012, "Azione", 120);
        cinema.inserisciSala("Orione", 200, 20);
        cinema.confermaSala();

        Pr = new Proiezione(cinema.getSala(1), paradox.getPellicola(1), LocalTime.parse("18:00", DateTimeFormatter.ofPattern("HH:mm")));
        cinema.getElencoProiezioni().get(1).add(Pr);
    }

    @AfterEach
    public void flush() {
        cinema.setElencoSale(new HashMap<>());
        cinema.setElencoProiezioni(new HashMap<>());
        paradox.setElencoPellicole(new HashMap<>());
        Pr.setBigliettiVenduti(new ArrayList<>());
        Pr.setBigliettiRimborsati(new ArrayList<>());
    }

    @Test
    void creaBigliettoTest() {

        Pr.creaBiglietto(true, false);
        Pr.confermaAcquisto(true, 1);
        Pr.creaBiglietto(true, false);
        Pr.confermaAcquisto(true, 2);

        assertEquals(2, Pr.getNumeroBigliettiVenduti());


        assertEquals(218, Pr.getPostiRimanentiTot());
        assertEquals(18, Pr.getPostiRimanentiVIP());
    }

    @Test
    void resoBigliettoTest() throws IOException{

        Pr.creaBiglietto(true, false);
        Pr.confermaAcquisto(true, 1);
        Pr.creaBiglietto(false, true);
        Pr.confermaAcquisto(false, 2);


        Pr.effettuaReso(2);
        Pr.confermaReso(2, false);

        assertEquals(1, Pr.getNumeroBigliettiVenduti());
        assertEquals(1, Pr.getNumeroBigliettiRimborsati());

        assertEquals(219, Pr.getPostiRimanentiTot());
        assertEquals(19, Pr.getPostiRimanentiVIP());
    }
}