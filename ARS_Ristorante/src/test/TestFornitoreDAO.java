package test;

import java.util.List;

import dao.FornitoreDAO;
import model.Fornitore;

public class TestFornitoreDAO {

    public static void main(String[] args) {
        FornitoreDAO dao = new FornitoreDAO();

        List<Fornitore> fornitori = dao.getTuttiFornitori();

        System.out.println("Numero fornitori trovati: " + fornitori.size());

        for (Fornitore f : fornitori) {
            System.out.println(
                f.getId() + " - " +
                f.getNome() + " - " +
                f.getTelefono() + " - " +
                f.getEmail() + " - " +
                f.getIndirizzo()
            );
        }
    }
}