package test;

import java.util.List;

import dao.ProdottoMagazzinoDAO;
import model.ProdottoMagazzino;

public class TestProdottoMagazzinoDAO {

    public static void main(String[] args) {
        ProdottoMagazzinoDAO dao = new ProdottoMagazzinoDAO();

        List<ProdottoMagazzino> prodotti = dao.getTuttiProdotti();

        System.out.println("Numero prodotti trovati: " + prodotti.size());

        for (ProdottoMagazzino p : prodotti) {
            System.out.println(
                p.getId() + " - " +
                p.getNome() + " - " +
                p.getDescrizione() + " - " +
                p.getQuantita() + " " +
                p.getUnitaMisura() + " - soglia minima: " +
                p.getSogliaMinima()
            );
        }

        System.out.println("\nProdotti sotto soglia:");

        List<ProdottoMagazzino> prodottiSottoSoglia = dao.getProdottiSottoSoglia();

        for (ProdottoMagazzino p : prodottiSottoSoglia) {
            System.out.println(
                p.getId() + " - " +
                p.getNome() + " - quantità: " +
                p.getQuantita() + " - soglia: " +
                p.getSogliaMinima()
            );
        }
    }
}