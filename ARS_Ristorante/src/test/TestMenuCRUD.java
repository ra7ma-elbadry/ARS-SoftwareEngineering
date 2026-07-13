package test;

import java.util.List;

import dao.MenuDAO;
import model.CategoriaMenu;
import model.Menu;

public class TestMenuCRUD {

    public static void main(String[] args) {
        MenuDAO dao = new MenuDAO();

        // 1. Aggiunta nuovo piatto
        Menu nuovoPiatto = new Menu(
                0,
                "Piatto Test",
                "Descrizione piatto test",
                9.99,
                CategoriaMenu.PRIMO,
                true
        );

        dao.aggiungiMenu(nuovoPiatto);
        System.out.println("Piatto test aggiunto.");

        // 2. Lettura menu
        List<Menu> menuCompleto = dao.getAllMenu();
        System.out.println("Numero piatti dopo aggiunta: " + menuCompleto.size());

        Menu piattoTest = null;

        for (Menu m : menuCompleto) {
            if (m.getNome().equals("Piatto Test")) {
                piattoTest = m;
                break;
            }
        }

        if (piattoTest == null) {
            System.out.println("Piatto Test non trovato.");
            return;
        }

        System.out.println("Piatto Test trovato con id: " + piattoTest.getIdMenu());

        // 3. Aggiornamento disponibilità
        dao.aggiornaDisponibilita(piattoTest.getIdMenu(), false);
        System.out.println("Disponibilità aggiornata a false.");

        // 4. Verifica aggiornamento
        List<Menu> menuDopoAggiornamento = dao.getAllMenu();

        for (Menu m : menuDopoAggiornamento) {
            if (m.getIdMenu() == piattoTest.getIdMenu()) {
                System.out.println(
                        "Verifica: " +
                        m.getNome() +
                        " - disponibile: " +
                        m.isDisponibile()
                );
            }
        }

        System.out.println("Test Menu CRUD completato.");
    }
}