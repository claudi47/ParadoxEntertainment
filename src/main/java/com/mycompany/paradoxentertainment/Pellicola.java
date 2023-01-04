package com.mycompany.paradoxentertainment;

import java.io.Serializable;

public class Pellicola implements Serializable {
    private String nomePellicola;
    private String regista;
    private int anno;
    private String genere;
    private int durata;
    private int idPellicola;
    
    public Pellicola(String nomePellicola, String regista, int anno, String genere, int durata) {
        this.nomePellicola = nomePellicola;
        this.regista = regista;
        this.anno = anno;
        this.genere = genere;
        this.durata = durata;
    }
    
    public String getNomePellicola() {
        return nomePellicola;
    }

    public String getRegista() {
        return regista;
    }

    public int getAnno() {
        return anno;
    }

    public int getDurata() {
        return durata;
    }

    public int getIdPellicola() {
        return idPellicola;
    }

    public void setIdPellicola(int idPellicola) {
        this.idPellicola = idPellicola;
    }

    public void setNomePellicola(String nomePellicola) {
        this.nomePellicola = nomePellicola;
    }

    public void setRegista(String regista) {
        this.regista = regista;
    }

    public void setAnno(int anno) {
        this.anno = anno;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }

    public void setDurata(int durata) {
        this.durata = durata;
    }
    
    public void modificaPellicola(String titolo, String regista, int anno, String genere, int durata) {
        this.setNomePellicola(titolo);
        this.setRegista(regista);
        this.setAnno(anno);
        this.setGenere(genere);
        this.setDurata(durata);
    }

    @Override
    public String toString() {
        return nomePellicola + 
                "\n - Regista: " + regista + 
                "\n - Anno: " + anno + 
                "\n - Genere: " + genere + 
                "\n - Durata: " + durata + " minuti" +
                "\n - ID: " + idPellicola 
                //"\n - Locandina:" + locandina
                ;
    }
}
