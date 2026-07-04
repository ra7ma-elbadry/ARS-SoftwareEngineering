package model;

public class Utente {

    private int idUtente;
    private String nome;
    private String cognome;
    private String username;
    private String password;
    private RuoloUtente ruolo;

    public Utente(int idUtente, String nome, String cognome, String username, String password, RuoloUtente ruolo) {
        this.idUtente = idUtente;
        this.nome = nome;
        this.cognome = cognome;
        this.username = username;
        this.password = password;
        this.ruolo = ruolo;
    }

    public int getIdUtente() {
        return idUtente;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public RuoloUtente getRuolo() {
        return ruolo;
    }

    @Override
    public String toString() {
        return nome + " " + cognome + " (" + ruolo + ")";
    }
}