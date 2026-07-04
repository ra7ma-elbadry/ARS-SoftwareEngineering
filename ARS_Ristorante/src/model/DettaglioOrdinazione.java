package model;

public class DettaglioOrdinazione {

    private int idDettaglio;
    private int idOrdinazione;
    private int idMenu;
    private int quantita;
    private String note;
    private double prezzoUnitario;

    public DettaglioOrdinazione(int idDettaglio, int idOrdinazione, int idMenu, int quantita, String note, double prezzoUnitario) {
        this.idDettaglio = idDettaglio;
        this.idOrdinazione = idOrdinazione;
        this.idMenu = idMenu;
        this.quantita = quantita;
        this.note = note;
        this.prezzoUnitario = prezzoUnitario;
    }

    public int getIdDettaglio() {
        return idDettaglio;
    }

    public int getIdOrdinazione() {
        return idOrdinazione;
    }

    public int getIdMenu() {
        return idMenu;
    }

    public int getQuantita() {
        return quantita;
    }

    public String getNote() {
        return note;
    }

    public double getPrezzoUnitario() {
        return prezzoUnitario;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public double calcolaSubtotale() {
        return quantita * prezzoUnitario;
    }

    @Override
    public String toString() {
        return quantita + "x prodotto " + idMenu + " - € " + calcolaSubtotale();
    }
} 