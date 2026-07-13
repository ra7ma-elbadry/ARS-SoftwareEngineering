package test;

import java.util.List;

import dao.MenuDAO;
import model.Menu;

public class TestMenuDAO {

    public static void main(String[] args) {
        MenuDAO dao = new MenuDAO();

        List<Menu> menuCompleto = dao.getAllMenu();
        System.out.println("Numero piatti nel menu: " + menuCompleto.size());

        for (Menu m : menuCompleto) {
            System.out.println(
                    m.getIdMenu() + " - " +
                    m.getNome() + " - " +
                    m.getCategoria() + " - " +
                    m.getPrezzo() + " - disponibile: " +
                    m.isDisponibile()
            );
        }

        System.out.println();

        List<Menu> menuDisponibile = dao.getMenuDisponibile();
        System.out.println("Numero piatti disponibili: " + menuDisponibile.size());

        for (Menu m : menuDisponibile) {
            System.out.println(
                    m.getIdMenu() + " - " +
                    m.getNome() + " - " +
                    m.getCategoria() + " - " +
                    m.getPrezzo()
            );
        }
    }
}