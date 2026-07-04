package model;

public class Tavolo {

    private int idTavolo;
    private int numero;
    private int posti;
    private StatoTavolo stato;

    public Tavolo(int idTavolo, int numero, int posti, StatoTavolo stato) {
        this.idTavolo = idTavolo;
        this.numero = numero;
        this.posti = posti;
        this.stato = stato;
    }

    public int getIdTavolo() {
        return idTavolo;
    }

    public int getNumero() {
        return numero;
    }

    public int getPosti() {
        return posti;
    }

    public StatoTavolo getStato() {
        return stato;
    }

    public void setStato(StatoTavolo stato) {
        this.stato = stato;
    }

    @Override
    public String toString() {
        return "Tavolo " + numero + " (" + posti + " posti) - " + stato;
    }
}