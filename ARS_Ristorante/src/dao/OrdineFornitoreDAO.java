package dao;

import java.util.List;

import model.OrdineFornitore;
import model.OrdineFornitoreVisualizzato;

public interface OrdineFornitoreDAO {

    List<OrdineFornitore> getTuttiOrdiniFornitore();

    boolean aggiungiOrdineFornitore(OrdineFornitore ordine);

    boolean aggiornaStatoOrdine(int idOrdineFornitore, String nuovoStato);

    boolean eliminaOrdineFornitore(int idOrdineFornitore);

    int creaOrdineERestituisciId(OrdineFornitore ordine);

    List<OrdineFornitoreVisualizzato> getOrdiniVisualizzatiPerFornitore(int idFornitore);
}