package dao;

import java.util.List;

import model.ProdottoMagazzino;

public interface ProdottoMagazzinoDAO {

    List<ProdottoMagazzino> getTuttiProdotti();

    List<ProdottoMagazzino> getProdottiSottoSoglia();

    boolean aggiungiProdotto(ProdottoMagazzino prodotto);

    boolean aggiornaProdotto(ProdottoMagazzino prodotto);

    boolean eliminaProdotto(int idProdotto);
}