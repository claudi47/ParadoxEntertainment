package com.mycompany.paradoxentertainment;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class CinemaTest {

    static Cinema cinema;
    static ParadoxEntertainment paradox;

    @BeforeAll
    public static void initTest() {
        cinema = Cinema.getInstance();
        paradox = ParadoxEntertainment.getInstance();
    }

    @AfterEach
    public void flush() {
        cinema.setElencoSale(new HashMap<>());
        cinema.setElencoProiezioni(new HashMap<>());
        paradox.setElencoPellicole(new HashMap<>());
    }

    @Test
    void inserisciSalaTest() throws IOException {

        cinema.inserisciSala("Orione", 200, 20);
        cinema.confermaSala();
        cinema.inserisciSala("Saturno", 180, 20);
        cinema.confermaSala();

        assertEquals(2, cinema.getNumeroSale());

        if(!cinema.salaGiaEsistente("Orione")) {
            cinema.inserisciSala("Saturno", 180, 20);
            cinema.confermaSala();
        }
        assertEquals(2, cinema.getNumeroSale());
    }

    @Test
    void eliminaSalaTest() throws IOException {

        InputStream originalIN = System.in;
        String input = "2\n1\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        cinema.setBf(new BufferedReader(new InputStreamReader(System.in)));

        cinema.inserisciSala("Orione", 200, 20);
        cinema.confermaSala();
        cinema.inserisciSala("Saturno", 180, 20);
        cinema.confermaSala();

        cinema.eliminaSala();
        assertEquals(1, cinema.getNumeroSale());
    }

    @Test
    void inserisciProiezioneTest() throws IOException {
        InputStream originalIN = System.in;
        String input = "1\n0\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        paradox.setBf(new BufferedReader(new InputStreamReader(System.in)));

        paradox.inserisciPellicola("Avengers", "Feige", 2012, "Azione", 120);
        cinema.inserisciSala("Orione", 200, 20);
        cinema.confermaSala();
        cinema.inserisciProiezione(paradox.getPellicola(1), 1, LocalTime.parse("18:00", DateTimeFormatter.ofPattern("HH:mm")));
        cinema.confermaProiezione();
        cinema.inserisciProiezione(paradox.getPellicola(1), 1, LocalTime.parse("20:30", DateTimeFormatter.ofPattern("HH:mm")));
        cinema.confermaProiezione();

        assertEquals(2, cinema.getNumeroProiezioni());

        // Qui verifichiamo che la Proiezione inserita non si sovrapponga con gli orari di altre pellicole
        if(cinema.isOrarioSpettacoloInseritoValido(LocalTime.parse("19:00", DateTimeFormatter.ofPattern("HH:mm")), 1, 120))
            paradox.inserisciProiezione(1, 1, LocalTime.parse("19:00", DateTimeFormatter.ofPattern("HH:mm")));
        assertEquals(2, cinema.getNumeroProiezioni());
    }

    @Test
    void eliminaProiezioneTest() throws IOException {
        InputStream originalIN = System.in;
        String input1 = "1\n0\n";
        InputStream in1 = new ByteArrayInputStream(input1.getBytes());
        System.setIn(in1);
        paradox.setBf(new BufferedReader(new InputStreamReader(System.in)));

        paradox.inserisciPellicola("Avengers", "Feige", 2012, "Azione", 120);
        cinema.inserisciSala("Orione", 200, 20);
        cinema.confermaSala();
        cinema.inserisciProiezione(paradox.getPellicola(1), 1, LocalTime.parse("18:00", DateTimeFormatter.ofPattern("HH:mm")));
        cinema.confermaProiezione();
        cinema.inserisciProiezione(paradox.getPellicola(1), 1, LocalTime.parse("20:30", DateTimeFormatter.ofPattern("HH:mm")));
        cinema.confermaProiezione();

        String input2 = "1\n1\n";
        InputStream in2 = new ByteArrayInputStream(input2.getBytes());
        System.setIn(in2);
        cinema.setBf(new BufferedReader(new InputStreamReader(System.in)));

        cinema.eliminaProiezione();
        assertEquals(1, cinema.getNumeroProiezioni());
    }
}