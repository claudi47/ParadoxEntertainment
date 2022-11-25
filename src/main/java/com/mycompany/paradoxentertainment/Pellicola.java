package com.mycompany.paradoxentertainment;

public class Pellicola {
    private String nomePellicola;
    private String regista;
    private int anno;
    private String genere;
    private int durata;
    private int idPellicola;
    
    public Pellicola(String nomePellicola, String regista, int anno, String genere, int durata, int idPellicola) {
        this.nomePellicola = nomePellicola;
        this.regista = regista;
        this.anno = anno;
        this.genere = genere;
        this.durata = durata;
        this.idPellicola = idPellicola;
    }  

    public Locandina inserisciLocandina(String path, int baseStampa, int altezzaStampa) {
        return new Locandina(path, baseStampa, altezzaStampa, this.idPellicola);
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
