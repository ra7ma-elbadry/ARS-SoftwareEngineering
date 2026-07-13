package test;

import java.util.List;

import dao.ProdottoMagazzinoDAO;
import model.ProdottoMagazzino;

public class TestProdottoMagazzinoCRUD {

    public static void main(String[] args) {
        ProdottoMagazzinoDAO dao = new ProdottoMagazzinoDAO();

        // 1. Aggiunta prodotto
        ProdottoMagazzino nuovoProdotto = new ProdottoMagazzino();
        nuovoProdotto.setNome("Prodotto Test");
        nuovoProdotto.setDescrizione("Descrizione prodotto test");
        nuovoProdotto.setQuantita(50);
        nuovoProdotto.setUnitaMisura("kg");
        nuovoProdotto.setSogliaMinima(10);

        boolean aggiunto = dao.aggiungiProdotto(nuovoProdotto);
        System.out.println("Prodotto aggiunto: " + aggiunto);

        // 2. Lettura prodotti
        List<ProdottoMagazzino> prodotti = dao.getTuttiProdotti();
        System.out.println("Numero prodotti dopo aggiunta: " + prodotti.size());

        ProdottoMagazzino prodottoTest = null;

        for (ProdottoMagazzino p : prodotti) {
            if (p.getNome().equals("Prodotto Test")) {
                prodottoTest = p;
                break;
            }
        }

        if (prodottoTest == null) {
            System.out.println("Prodotto Test non trovato.");
            return;
        }

        System.out.println("Prodotto Test trovato con id: " + prodottoTest.getId());

        // 3. Aggiornamento prodotto
        prodottoTest.setQuantita(25);
        prodottoTest.setSogliaMinima(5);
        prodottoTest.setDescrizione("Descrizione modificata");

        boolean aggiornato = dao.aggiornaProdotto(prodottoTest);
        System.out.println("Prodotto aggiornato: " + aggiornato);

        // 4. Eliminazione prodotto
        boolean eliminato = dao.eliminaProdotto(prodottoTest.getId());
        System.out.println("Prodotto eliminato: " + eliminato);

        // 5. Lettura finale
        List<ProdottoMagazzino> prodottiFinali = dao.getTuttiProdotti();
        System.out.println("Numero prodotti finale: " + prodottiFinali.size());
    }
}