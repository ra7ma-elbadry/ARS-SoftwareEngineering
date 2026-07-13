package test;

import java.time.LocalDate;
import java.util.List;

import dao.OrdineFornitoreDAO;
import model.OrdineFornitore;

public class TestOrdineFornitoreCRUD {

    public static void main(String[] args) {
        OrdineFornitoreDAO dao = new OrdineFornitoreDAO();

        // 1. Aggiunta ordine fornitore
        OrdineFornitore nuovoOrdine = new OrdineFornitore();
        nuovoOrdine.setIdFornitore(1);
        nuovoOrdine.setIdManager(3);
        nuovoOrdine.setDataOrdine(LocalDate.now());
        nuovoOrdine.setStato("CREATO");

        boolean aggiunto = dao.aggiungiOrdineFornitore(nuovoOrdine);
        System.out.println("Ordine fornitore aggiunto: " + aggiunto);

        // 2. Lettura ordini
        List<OrdineFornitore> ordini = dao.getTuttiOrdiniFornitore();
        System.out.println("Numero ordini dopo aggiunta: " + ordini.size());

        OrdineFornitore ordineTest = null;

        for (OrdineFornitore o : ordini) {
            if (o.getIdFornitore() == 1 && o.getIdManager() == 3 && o.getStato().equals("CREATO")) {
                ordineTest = o;
            }
        }

        if (ordineTest == null) {
            System.out.println("Ordine Test non trovato.");
            return;
        }

        System.out.println("Ordine Test trovato con id: " + ordineTest.getId());

        // 3. Aggiornamento stato
        boolean aggiornato = dao.aggiornaStatoOrdine(ordineTest.getId(), "INVIATO");
        System.out.println("Ordine aggiornato: " + aggiornato);

        // 4. Eliminazione ordine
        boolean eliminato = dao.eliminaOrdineFornitore(ordineTest.getId());
        System.out.println("Ordine eliminato: " + eliminato);

        // 5. Lettura finale
        List<OrdineFornitore> ordiniFinali = dao.getTuttiOrdiniFornitore();
        System.out.println("Numero ordini finale: " + ordiniFinali.size());
    }
}