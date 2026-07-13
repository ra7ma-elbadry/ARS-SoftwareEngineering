package test;

import java.util.List;

import dao.OrdineFornitoreDAO;
import model.OrdineFornitore;

public class TestOrdineFornitoreDAO {

    public static void main(String[] args) {
        OrdineFornitoreDAO dao = new OrdineFornitoreDAO();

        List<OrdineFornitore> ordini = dao.getTuttiOrdiniFornitore();

        System.out.println("Numero ordini fornitore trovati: " + ordini.size());

        for (OrdineFornitore o : ordini) {
            System.out.println(
                o.getId() + " - Fornitore: " +
                o.getIdFornitore() + " - Manager: " +
                o.getIdManager() + " - Data: " +
                o.getDataOrdine() + " - Stato: " +
                o.getStato()
            );
        }
    }
}