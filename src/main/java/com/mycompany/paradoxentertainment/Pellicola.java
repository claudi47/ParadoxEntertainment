package com.mycompany.paradoxentertainment;

public class Pellicola {
    private String nomePellicola;
    private String regista;
    private int anno;
    private String genere;
    private int durata;
    private int idPellicola;
    private Locandina locandina;
    
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

    public void setIdPellicola(int idPellicola) {
        this.idPellicola = idPellicola;
    }
    
    public void setLocandina(Locandina locandina) {
        this.locandina = locandina;
    }
}
