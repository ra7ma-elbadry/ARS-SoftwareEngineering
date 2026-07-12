package model;

public class ProdottoMagazzino {

    private int id;
    private String nome;
    private String descrizione;
    private double quantita;
    private double sogliaMinima;
    private String unitaMisura;

    public ProdottoMagazzino() {
    }

    public ProdottoMagazzino(int id, String nome, String descrizione, double quantita, double sogliaMinima, String unitaMisura) {
        this.id = id;
        this.nome = nome;
        this.descrizione = descrizione;
        this.quantita = quantita;
        this.sogliaMinima = sogliaMinima;
        this.unitaMisura = unitaMisura;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }   

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public double getQuantita() {
        return quantita;
    }

    public void setQuantita(double quantita) {
        this.quantita = quantita;
    }

    public double getSogliaMinima() {
        return sogliaMinima;
    }

    public void setSogliaMinima(double sogliaMinima) {
        this.sogliaMinima = sogliaMinima;
    }

    public String getUnitaMisura() {
        return unitaMisura;
    }

    public void setUnitaMisura(String unitaMisura) {
        this.unitaMisura = unitaMisura;
    }

    public boolean verificaScortaMinima() {
        return quantita <= sogliaMinima;
    }

    @Override
    public String toString() {
        return nome;
    }
}