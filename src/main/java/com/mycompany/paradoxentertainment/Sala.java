package com.mycompany.paradoxentertainment;

public class Sala {
    private String nomeSala;
    private int postiTot;
    private int postiStandard;
    private int postiVIP;
    private int idSala;

    public Sala(String IDSala, int postiStandard, int postiVIP, int idSala) {
        this.nomeSala = IDSala;
        this.postiStandard = postiStandard;
        this.postiVIP = postiVIP;
        this.postiTot = (postiStandard + postiVIP);
        this.idSala = idSala;
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

    public int getIdSala() {
        return idSala;
    }
    
    public void modificaSala(String nomeSala, int postiStandard, int postiVIP) {
        this.nomeSala = nomeSala;
        this.postiStandard = postiStandard;
        this.postiVIP = postiVIP;
        this.postiTot = postiStandard+postiVIP;
    }

    @Override
    public String toString() {
        return "Sala "+ nomeSala + "\n" +
                " - ID: " + idSala + "\n" +
                " - Capienza: " + postiTot + "\n" +
                " - Posti Standard: " + postiStandard + "\n" +
                " - Posti VIP: " + postiVIP; 
    }
}
