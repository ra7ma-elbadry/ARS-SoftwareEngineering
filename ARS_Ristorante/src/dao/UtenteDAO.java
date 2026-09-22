package dao;

import model.Utente;

public interface UtenteDAO {

    Utente login(String username, String password);
}