package com.mycompany.paradoxentertainment;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Daniele
 */
public class Tessera {
    private String nomeUtente;
    private String cognomeUtente;
    private String codiceFiscale;
    private LocalDate dataDiNascita;
    private int punti;
    private int idTessera;
    
    public Tessera(String nome, String cognome, LocalDate data, String codiceFiscale) {
        this.nomeUtente = nome;
        this.cognomeUtente = cognome;
        this.dataDiNascita = data;
        this.codiceFiscale = codiceFiscale;
        this.punti = 0;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public int getIdTessera() {
        return idTessera;
    }

    public int getPunti() {
        return punti;
    }

    public void setPunti(int punti) {
        this.punti = punti;
    }

    public void setIdTessera(int idTessera) {
        this.idTessera = idTessera;
    }
    
    void modificaTessera(String nome, String cognome, LocalDate dataDiNascita, String codiceFiscale) {
        this.nomeUtente = nome;
        this.cognomeUtente = cognome;
        this.dataDiNascita = dataDiNascita;
        this.codiceFiscale = codiceFiscale;
    }
    
    @Override
    public String toString() {
        return "\nTessera ID: " + idTessera
                + "\n - Nome: " + nomeUtente 
                + "\n - Cognome: " + cognomeUtente
                + "\n - Data di Nascita: " + dataDiNascita.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                + "\n - Codice Fiscale: " + codiceFiscale
                + "\n - Punti accumulati: " + punti;
    }
}
