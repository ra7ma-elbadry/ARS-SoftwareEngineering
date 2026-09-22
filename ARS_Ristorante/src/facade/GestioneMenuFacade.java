package facade;

import java.util.List;

import dao.MenuDAO;
import dao.impl.MenuDAOImpl;
import model.Menu;

public class GestioneMenuFacade {

    private MenuDAO menuDAO;

    public GestioneMenuFacade() {
        this.menuDAO = new MenuDAOImpl();
    }

    public List<Menu> getAllMenu() {
        return menuDAO.getAllMenu();
    }

    public void aggiungiPiatto(Menu menu) {
        menuDAO.aggiungiMenu(menu);
    }

    public void rendiDisponibile(int idMenu) {
        menuDAO.aggiornaDisponibilita(idMenu, true);
    }

    public void rendiNonDisponibile(int idMenu) {
        menuDAO.aggiornaDisponibilita(idMenu, false);
    }
}