package facade;

import java.time.LocalDate;
import java.util.List;

import dao.DettaglioOrdineFornitoreDAO;
import dao.FornitoreDAO;
import dao.OrdineFornitoreDAO;
import dao.ProdottoMagazzinoDAO;

import dao.impl.DettaglioOrdineFornitoreDAOImpl;
import dao.impl.FornitoreDAOImpl;
import dao.impl.OrdineFornitoreDAOImpl;
import dao.impl.ProdottoMagazzinoDAOImpl;

import model.DettaglioOrdineFornitore;
import model.Fornitore;
import model.OrdineFornitore;
import model.OrdineFornitoreVisualizzato;
import model.ProdottoMagazzino;

public class GestioneMagazzinoFacade {

    private ProdottoMagazzinoDAO prodottoDAO;
    private FornitoreDAO fornitoreDAO;
    private OrdineFornitoreDAO ordineDAO;
    private DettaglioOrdineFornitoreDAO dettaglioDAO;

    public GestioneMagazzinoFacade() {

        this.prodottoDAO = new ProdottoMagazzinoDAOImpl();
        this.fornitoreDAO = new FornitoreDAOImpl();
        this.ordineDAO = new OrdineFornitoreDAOImpl();
        this.dettaglioDAO = new DettaglioOrdineFornitoreDAOImpl();
    }

    // =========================
    // PRODOTTI
    // =========================

    public List<ProdottoMagazzino> getTuttiProdotti() {
        return prodottoDAO.getTuttiProdotti();
    }

    public List<ProdottoMagazzino> getProdottiSottoSoglia() {
        return prodottoDAO.getProdottiSottoSoglia();
    }

    public boolean aggiungiProdotto(ProdottoMagazzino prodotto) {
        return prodottoDAO.aggiungiProdotto(prodotto);
    }

    public boolean aggiornaProdotto(ProdottoMagazzino prodotto) {
        return prodottoDAO.aggiornaProdotto(prodotto);
    }

    public boolean eliminaProdotto(int idProdotto) {
        return prodottoDAO.eliminaProdotto(idProdotto);
    }

    // =========================
    // FORNITORI
    // =========================

    public List<Fornitore> getTuttiFornitori() {
        return fornitoreDAO.getTuttiFornitori();
    }

    public boolean aggiungiFornitore(Fornitore fornitore) {
        return fornitoreDAO.aggiungiFornitore(fornitore);
    }

    public boolean aggiornaFornitore(Fornitore fornitore) {
        return fornitoreDAO.aggiornaFornitore(fornitore);
    }

    public boolean eliminaFornitore(int idFornitore) {
        return fornitoreDAO.eliminaFornitore(idFornitore);
    }

    // =========================
    // ORDINI FORNITORE
    // =========================

    public List<OrdineFornitore> getTuttiOrdiniFornitore() {
        return ordineDAO.getTuttiOrdiniFornitore();
    }

    public List<OrdineFornitoreVisualizzato>
            getOrdiniVisualizzatiPerFornitore(int idFornitore) {

        return ordineDAO
                .getOrdiniVisualizzatiPerFornitore(idFornitore);
    }

    public boolean aggiornaStatoOrdine(
            int idOrdineFornitore,
            String nuovoStato) {

        return ordineDAO.aggiornaStatoOrdine(
                idOrdineFornitore,
                nuovoStato);
    }

    public boolean eliminaOrdineFornitore(
            int idOrdineFornitore) {

        return ordineDAO.eliminaOrdineFornitore(
                idOrdineFornitore);
    }

    // =========================
    // CREAZIONE ORDINE COMPLETO
    // =========================

    public int creaOrdineFornitore(
            int idFornitore,
            int idManager,
            List<DettaglioOrdineFornitore> dettagli) {

        if (dettagli == null || dettagli.isEmpty()) {
            return -1;
        }

        OrdineFornitore ordine = new OrdineFornitore();

        ordine.setIdFornitore(idFornitore);
        ordine.setIdManager(idManager);
        ordine.setDataOrdine(LocalDate.now());
        ordine.setStato("INVIATO");

        int idOrdineCreato =
                ordineDAO.creaOrdineERestituisciId(ordine);

        if (idOrdineCreato == -1) {
            return -1;
        }

        for (DettaglioOrdineFornitore dettaglio : dettagli) {

            dettaglio.setIdOrdineFornitore(idOrdineCreato);

            dettaglioDAO.aggiungiDettaglio(dettaglio);
        }

        return idOrdineCreato;
    }
}