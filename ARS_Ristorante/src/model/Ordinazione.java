package model;

public class Ordinazione {

    private int idOrdinazione;
    private int idTavolo;
    private int idCameriere;
    private String dataOra;
    private StatoOrdinazione stato;
    private double totale;

    public Ordinazione(int idOrdinazione, int idTavolo, int idCameriere, String dataOra, StatoOrdinazione stato, double totale) {
        this.idOrdinazione = idOrdinazione;
        this.idTavolo = idTavolo;
        this.idCameriere = idCameriere;
        this.dataOra = dataOra;
        this.stato = stato;
        this.totale = totale;
    }

    public int getIdOrdinazione() {
        return idOrdinazione;
    }

    public int getIdTavolo() {
        return idTavolo;
    }

    public int getIdCameriere() {
        return idCameriere;
    }

    public String getDataOra() {
        return dataOra;
    }

    public StatoOrdinazione getStato() {
        return stato;
    }

    public double getTotale() {
        return totale;
    }

    public void setStato(StatoOrdinazione stato) {
        this.stato = stato;
    }

    public void setTotale(double totale) {
        this.totale = totale;
    }

    @Override
    public String toString() {
        return "Ordine #" + idOrdinazione + " - Tavolo " + idTavolo + " - " + stato + " - € " + totale;
    }
}