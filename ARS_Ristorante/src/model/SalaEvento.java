package model;

public class SalaEvento {

    private int idSala;
    private String nome;
    private int capienza;
    private String descrizione;
    private boolean disponibile;

    public SalaEvento(int idSala, String nome, int capienza, String descrizione, boolean disponibile) {
        this.idSala = idSala;
        this.nome = nome;
        this.capienza = capienza;
        this.descrizione = descrizione;
        this.disponibile = disponibile;
    }

    public int getIdSala() {
        return idSala;
    }

    public String getNome() {
        return nome;
    }

    public int getCapienza() {
        return capienza;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public boolean isDisponibile() {
        return disponibile;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCapienza(int capienza) {
        this.capienza = capienza;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public void setDisponibile(boolean disponibile) {
        this.disponibile = disponibile;
    }

    @Override
    public String toString() {
        return nome + " - capienza: " + capienza;
    }
}