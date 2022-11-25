package com.mycompany.paradoxentertainment;

public class Sala {
    private String nomeSala;
    private int postiTot;
    private int postiStandard;
    private int postiVIP;

    public Sala(String IDSala, int postiStandard, int postiVIP) {
        this.nomeSala = IDSala;
        this.postiStandard = postiStandard;
        this.postiVIP = postiVIP;
        this.postiTot = (postiStandard + postiVIP);
    }
    
    public String getNomeSala() {
        return nomeSala;
    }

    public int getPostiTot() {
        return postiTot;
    }

    public int getPostiStandard() {
        return postiStandard;
    }

    public int getPostiVIP() {
        return postiVIP;
    }

    @Override
    public String toString() {
        return "Sala "+ nomeSala + "\n" +
                " - Capienza: " + postiTot + "\n" +
                " - Posti Standard: " + postiStandard + "\n" +
                " - Posti VIP: " + postiVIP; 
    }
}
