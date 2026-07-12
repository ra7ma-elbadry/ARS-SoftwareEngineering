package model;

public class DettaglioOrdineFornitore {

    private int id;
    private int idOrdineFornitore;
    private int idProdotto;
    private double quantita;
    private double prezzoUnitario;

    public DettaglioOrdineFornitore() {
    }

    public DettaglioOrdineFornitore(int id, int idOrdineFornitore, int idProdotto, double quantita, double prezzoUnitario) {
        this.id = id;
        this.idOrdineFornitore = idOrdineFornitore;
        this.idProdotto = idProdotto;
        this.quantita = quantita;
        this.prezzoUnitario = prezzoUnitario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }   

    public int getIdOrdineFornitore() {
        return idOrdineFornitore;
    }

    public void setIdOrdineFornitore(int idOrdineFornitore) {
        this.idOrdineFornitore = idOrdineFornitore;
    }

    public int getIdProdotto() {
        return idProdotto;
    }

    public void setIdProdotto(int idProdotto) {
        this.idProdotto = idProdotto;
    }

    public double getQuantita() {
        return quantita;
    }

    public void setQuantita(double quantita) {
        this.quantita = quantita;
    }

    public double getPrezzoUnitario() {
        return prezzoUnitario;
    }

    public void setPrezzoUnitario(double prezzoUnitario) {
        this.prezzoUnitario = prezzoUnitario;
    }
}