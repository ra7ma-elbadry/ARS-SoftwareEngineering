package dao;

import java.util.List;

import model.Fornitore;

public interface FornitoreDAO {

    List<Fornitore> getTuttiFornitori();

    boolean aggiungiFornitore(Fornitore fornitore);

    boolean aggiornaFornitore(Fornitore fornitore);

    boolean eliminaFornitore(int idFornitore);
}