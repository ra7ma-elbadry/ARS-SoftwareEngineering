package model;

public class OrdineFornitoreVisualizzato {

    private int idOrdine;
    private String dataOrdine;
    private String stato;
    private String nomeProdotto;
    private double quantita;
    private double prezzoUnitario;

    public OrdineFornitoreVisualizzato() {
    }

    public OrdineFornitoreVisualizzato(int idOrdine, String dataOrdine, String stato,
                                       String nomeProdotto, double quantita, double prezzoUnitario) {
        this.idOrdine = idOrdine;
        this.dataOrdine = dataOrdine;
        this.stato = stato;
        this.nomeProdotto = nomeProdotto;
        this.quantita = quantita;
        this.prezzoUnitario = prezzoUnitario;
    }

    public int getIdOrdine() {
        return idOrdine;
    }

    public void setIdOrdine(int idOrdine) {
        this.idOrdine = idOrdine;
    }

    public String getDataOrdine() {
        return dataOrdine;
    }

    public void setDataOrdine(String dataOrdine) {
        this.dataOrdine = dataOrdine;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public String getNomeProdotto() {
        return nomeProdotto;
    }

    public void setNomeProdotto(String nomeProdotto) {
        this.nomeProdotto = nomeProdotto;
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