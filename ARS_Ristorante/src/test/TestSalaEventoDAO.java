package test;

import java.util.List;

import dao.SalaEventoDAO;
import dao.impl.SalaEventoDAOImpl;
import model.SalaEvento;

public class TestSalaEventoDAO {

    public static void main(String[] args) {
        SalaEventoDAO dao = new SalaEventoDAOImpl();

        List<SalaEvento> sale = dao.getTutteSale();

        System.out.println("Numero sale evento trovate: " + sale.size());

        for (SalaEvento s : sale) {
            System.out.println(
                    s.getIdSala() + " - " +
                    s.getNome() + " - capienza: " +
                    s.getCapienza() + " - disponibile: " +
                    s.isDisponibile() + " - descrizione: " +
                    s.getDescrizione()
            );
        }
    }
}