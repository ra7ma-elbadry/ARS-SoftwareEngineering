package model;

public class Menu {

    private int idMenu;
    private String nome;
    private String descrizione;
    private double prezzo;
    private CategoriaMenu categoria;
    private boolean disponibile;

    public Menu(int idMenu, String nome, String descrizione, double prezzo, CategoriaMenu categoria, boolean disponibile) {
        this.idMenu = idMenu;
        this.nome = nome;
        this.descrizione = descrizione;
        this.prezzo = prezzo;
        this.categoria = categoria;
        this.disponibile = disponibile;
    }

    public int getIdMenu() {
        return idMenu;
    }

    public String getNome() {
        return nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public CategoriaMenu getCategoria() {
        return categoria;
    }

    public boolean isDisponibile() {
        return disponibile;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }

    public void setCategoria(CategoriaMenu categoria) {
        this.categoria = categoria;
    }

    public void setDisponibile(boolean disponibile) {
        this.disponibile = disponibile;
    }

    @Override
    public String toString() {
        return nome + " - € " + prezzo;
    }
}