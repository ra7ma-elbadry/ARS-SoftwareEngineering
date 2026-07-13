package test;

import java.util.List;

import dao.FornitoreDAO;
import model.Fornitore;

public class TestFornitoreCRUD {

    public static void main(String[] args) {
        FornitoreDAO dao = new FornitoreDAO();

        // 1. Aggiunta fornitore
        Fornitore nuovoFornitore = new Fornitore();
        nuovoFornitore.setNome("Fornitore Test");
        nuovoFornitore.setTelefono("123456789");
        nuovoFornitore.setEmail("test@fornitore.it");
        nuovoFornitore.setIndirizzo("Via Test 1, Pavia");

        boolean aggiunto = dao.aggiungiFornitore(nuovoFornitore);
        System.out.println("Fornitore aggiunto: " + aggiunto);

        // 2. Lettura fornitori
        List<Fornitore> fornitori = dao.getTuttiFornitori();
        System.out.println("Numero fornitori dopo aggiunta: " + fornitori.size());

        Fornitore fornitoreTest = null;

        for (Fornitore f : fornitori) {
            if (f.getNome().equals("Fornitore Test")) {
                fornitoreTest = f;
                break;
            }
        }

        if (fornitoreTest == null) {
            System.out.println("Fornitore Test non trovato.");
            return;
        }

        System.out.println("Fornitore Test trovato con id: " + fornitoreTest.getId());

        // 3. Aggiornamento
        fornitoreTest.setTelefono("999999999");
        fornitoreTest.setEmail("test.modificato@fornitore.it");

        boolean aggiornato = dao.aggiornaFornitore(fornitoreTest);
        System.out.println("Fornitore aggiornato: " + aggiornato);

        // 4. Eliminazione
        boolean eliminato = dao.eliminaFornitore(fornitoreTest.getId());
        System.out.println("Fornitore eliminato: " + eliminato);

        // 5. Lettura finale
        List<Fornitore> fornitoriFinali = dao.getTuttiFornitori();
        System.out.println("Numero fornitori finale: " + fornitoriFinali.size());
    }
}