package test;

import java.util.List;

import dao.SalaEventoDAO;
import model.SalaEvento;

public class TestSalaEventoCRUD {

    public static void main(String[] args) {
        SalaEventoDAO dao = new SalaEventoDAO();

        // 1. Aggiunta sala evento
        SalaEvento nuovaSala = new SalaEvento(
                0,
                "Sala Test",
                30,
                "Sala evento di test",
                true
        );

        dao.aggiungiSala(nuovaSala);
        System.out.println("Sala Test aggiunta.");

        // 2. Lettura sale
        List<SalaEvento> sale = dao.getTutteSale();
        System.out.println("Numero sale dopo aggiunta: " + sale.size());

        SalaEvento salaTest = null;

        for (SalaEvento s : sale) {
            if (s.getNome().equals("Sala Test")) {
                salaTest = s;
                break;
            }
        }

        if (salaTest == null) {
            System.out.println("Sala Test non trovata.");
            return;
        }

        System.out.println("Sala Test trovata con id: " + salaTest.getIdSala());

        // 3. Eliminazione sala test
        dao.eliminaSala(salaTest.getIdSala());
        System.out.println("Sala Test eliminata.");

        // 4. Lettura finale
        List<SalaEvento> saleFinali = dao.getTutteSale();
        System.out.println("Numero sale finale: " + saleFinali.size());

        System.out.println("Test SalaEvento CRUD completato.");
    }
}