package facade;

import dao.UtenteDAO;
import dao.impl.UtenteDAOImpl;
import model.Utente;

public class AutenticazioneFacade {

    private UtenteDAO utenteDAO;

    public AutenticazioneFacade() {
        this.utenteDAO = new UtenteDAOImpl();
    }

    public Utente login(String username, String password) {

        if (username == null || username.isBlank()
                || password == null || password.isBlank()) {

            return null;
        }

        return utenteDAO.login(username, password);
    }
}