package com.mycompany.paradoxentertainment;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;


class ParadoxEntertainmentTest {

    static ParadoxEntertainment paradox;

    @BeforeAll
    public static void initTest() {
        paradox = ParadoxEntertainment.getInstance();
    }

    @AfterEach
    public void flush() {
        paradox.setElencoPellicole(new HashMap<>());
        paradox.setElencoTessere(new HashMap<>());
    }

    @Test
    void inserisciPellicolaTest() throws IOException {

        InputStream originalIN = System.in;
        String input = "1\n0\n1\n0\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        paradox.setBf(new BufferedReader(new InputStreamReader(System.in)));

        // Verifica che l'inserimento avvenga correttamente
        paradox.inserisciPellicola("Avengers", "Feige", 2012, "Azione", 120);
        paradox.inserisciPellicola("Il discorso del Re", "Hooper", 2010, "Drammatico", 120);

        assertEquals(2, paradox.getNumeroPellicole());

        // Verifica che non si possano inserire duplicati
        if(!paradox.pellicolaGiaEsistente("Avengers", "Feige", 2012)) {
            paradox.inserisciPellicola("Avengers", "Feige", 2012, "Azione", 120);
        }
        assertEquals(2, paradox.getNumeroPellicole());

    }

    @Test
    void eliminaPellicolaTest() throws IOException {

        InputStream originalIN = System.in;
        String input = "1\n0\n1\n0\n1\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        paradox.setBf(new BufferedReader(new InputStreamReader(System.in)));

        paradox.inserisciPellicola("Avengers", "Feige", 2012, "Azione", 120);
        paradox.inserisciPellicola("Il discorso del Re", "Hooper", 2010, "Drammatico", 120);

        /*
            per come è fatta la funzione, l'ID della pellicola viene passato internamente, ergo passeremo l'ID di una
            delle due pellicole inserite sopra secondo la funzione stampaPellicole
        */
        paradox.eliminaPellicola();
        assertEquals(1, paradox.getNumeroPellicole());
    }

    @Test
    void inserisciTesseraTest() throws IOException {

        InputStream originalIN = System.in;
        String input = "1\n1\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        paradox.setBf(new BufferedReader(new InputStreamReader(System.in)));

        // Verifichiamo l'inserimento classico, normale
        paradox.inserisciTessera("Giovanni", "Pancini", LocalDate.parse("23/01/1997", DateTimeFormatter.ofPattern("dd/MM/yyyy")), "PNCGVN97A23C351P");
        paradox.inserisciTessera("Claudio", "Vezzosi", LocalDate.parse("23/01/1998", DateTimeFormatter.ofPattern("dd/MM/yyyy")), "VZSCLD98A23C351F");


        assertEquals(2, paradox.getNumeroTessere());

        // Verifichiamo che, in caso di CF già esistente, l'inserimento non vada a buon fine
        if(!paradox.tesseraGiaEsistente("VZSCLD98A23C351F")) {
            paradox.inserisciTessera("Claudio", "Vezzosi", LocalDate.parse("23/01/1998", DateTimeFormatter.ofPattern("dd/MM/yyyy")), "VZSCLD98A23C351");
        }
        assertEquals(2, paradox.getNumeroTessere());
    }

    @Test
    void eliminaTesseraTest() throws IOException{

        InputStream originalIN = System.in;
        String input = "1\n1\n1\n1\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        paradox.setBf(new BufferedReader(new InputStreamReader(System.in)));

        paradox.inserisciTessera("Giovanni", "Pancini", LocalDate.parse("23/01/1997", DateTimeFormatter.ofPattern("dd/MM/yyyy")), "PNCGVN97A23C351P");
        paradox.inserisciTessera("Claudio", "Vezzosi", LocalDate.parse("23/01/1998", DateTimeFormatter.ofPattern("dd/MM/yyyy")), "VZSCLD98A23C351F");


        paradox.eliminaTessera();
        assertEquals(1, paradox.getNumeroTessere());
    }
}