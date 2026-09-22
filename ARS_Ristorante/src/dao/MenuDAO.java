package dao;

import java.util.List;

import model.Menu;

public interface MenuDAO {

    List<Menu> getAllMenu();

    List<Menu> getMenuDisponibile();

    void aggiungiMenu(Menu menu);

    void aggiornaDisponibilita(int idMenu, boolean disponibile);
}