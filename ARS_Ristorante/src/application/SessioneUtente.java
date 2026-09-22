package application;

import model.Utente;

public class SessioneUtente {

    private static SessioneUtente istanza;
    private Utente utente;

    private SessioneUtente() {
    }

    public static SessioneUtente getInstance() {

        if (istanza == null) {
            istanza = new SessioneUtente();
        }

        return istanza;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public Utente getUtente() {
        return utente;
    }

    public int getIdUtente() {

        if (utente == null) {
            return -1;
        }

        return utente.getIdUtente();
    }

    public boolean isUtenteLoggato() {
        return utente != null;
    }

    public void logout() {
        utente = null;
    }
}