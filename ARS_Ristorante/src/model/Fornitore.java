package model;

public class Fornitore {

    private int id;
    private String nome;
    private String telefono;
    private String email;
    private String indirizzo;

    public Fornitore() {
    }

    public Fornitore(int id, String nome, String telefono, String email, String indirizzo) {
        this.id = id;
        this.nome = nome;
        this.telefono = telefono;
        this.email = email;
        this.indirizzo = indirizzo;
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }   

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    @Override
    public String toString() {
        return nome;
    }
}