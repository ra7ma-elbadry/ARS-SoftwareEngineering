package facade;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import dao.DettaglioOrdinazioneDAO;
import dao.MenuDAO;
import dao.OrdinazioneDAO;
import dao.TavoloDAO;
import dao.impl.DettaglioOrdinazioneDAOImpl;
import dao.impl.MenuDAOImpl;
import dao.impl.OrdinazioneDAOImpl;
import dao.impl.TavoloDAOImpl;
import model.DettaglioOrdinazione;
import model.Menu;
import model.Ordinazione;
import model.StatoOrdinazione;
import model.StatoTavolo;
import model.Tavolo;

public class GestioneOrdinazioniFacade {

    private OrdinazioneDAO ordinazioneDAO;
    private DettaglioOrdinazioneDAO dettaglioDAO;
    private TavoloDAO tavoloDAO;
    private MenuDAO menuDAO;

    public GestioneOrdinazioniFacade() {

        this.ordinazioneDAO = new OrdinazioneDAOImpl();
        this.dettaglioDAO = new DettaglioOrdinazioneDAOImpl();
        this.tavoloDAO = new TavoloDAOImpl();
        this.menuDAO = new MenuDAOImpl();
    }

    /*
     * Restituisce i tavoli attualmente liberi.
     */
    public List<Tavolo> getTavoliDisponibili() {
        return tavoloDAO.getTavoliDisponibili();
    }

    /*
     * Restituisce i prodotti del menu attualmente disponibili.
     */
    public List<Menu> getMenuDisponibile() {
        return menuDAO.getMenuDisponibile();
    }

    /*
     * Restituisce gli ordini pronti o consegnati.
     * Serve al cameriere.
     */
    public List<Ordinazione> getOrdinazioniPronteOConsegnate() {
        return ordinazioneDAO.getOrdinazioniPronteOConsegnate();
    }

    /*
     * Crea una nuova ordinazione.
     *
     * La Facade:
     * 1. crea l'ordinazione
     * 2. salva i dettagli
     * 3. occupa il tavolo
     */
    public int creaOrdinazione(
            int idTavolo,
            int idCameriere,
            List<DettaglioOrdinazione> dettagli,
            double totale) {

        String dataOra = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        Ordinazione ordinazione = new Ordinazione(
                0,
                idTavolo,
                idCameriere,
                dataOra,
                StatoOrdinazione.IN_ATTESA,
                totale
        );

        int idOrdinazioneGenerato =
                ordinazioneDAO.creaOrdinazione(ordinazione);

        if (idOrdinazioneGenerato == -1) {
            return -1;
        }

        for (DettaglioOrdinazione dettaglio : dettagli) {

            DettaglioOrdinazione dettaglioDaSalvare =
                    new DettaglioOrdinazione(
                            0,
                            idOrdinazioneGenerato,
                            dettaglio.getIdMenu(),
                            dettaglio.getQuantita(),
                            dettaglio.getNote(),
                            dettaglio.getPrezzoUnitario()
                    );

            dettaglioDAO.aggiungiDettaglio(dettaglioDaSalvare);
        }

        tavoloDAO.aggiornaStatoTavolo(
                idTavolo,
                StatoTavolo.OCCUPATO
        );

        return idOrdinazioneGenerato;
    }

    /*
     * Segna un ordine come consegnato.
     */
    public void segnaConsegnato(int idOrdinazione) {

        ordinazioneDAO.aggiornaStatoOrdinazione(
                idOrdinazione,
                StatoOrdinazione.CONSEGNATO
        );
    }

    /*
     * Chiude il conto e libera il tavolo.
     */
    public void chiudiConto(
            int idOrdinazione,
            int idTavolo) {

        ordinazioneDAO.aggiornaStatoOrdinazione(
                idOrdinazione,
                StatoOrdinazione.CHIUSO
        );

        tavoloDAO.aggiornaStatoTavolo(
                idTavolo,
                StatoTavolo.LIBERO
        );
    }

    /*
     * Metodi utilizzati anche dal controller della cucina.
     */

    public List<Ordinazione> getOrdinazioniPerCucina() {
        return ordinazioneDAO.getOrdinazioniPerCucina();
    }

    public List<DettaglioOrdinazione> getDettagliOrdinazione(
            int idOrdinazione) {

        return dettaglioDAO
                .getDettagliByOrdinazione(idOrdinazione);
    }

    public List<String> getDettagliConNomeProdotto(
            int idOrdinazione) {

        return dettaglioDAO
                .getDettagliConNomeProdotto(idOrdinazione);
    }

    public void aggiornaStatoOrdinazione(
            int idOrdinazione,
            StatoOrdinazione nuovoStato) {

        ordinazioneDAO.aggiornaStatoOrdinazione(
                idOrdinazione,
                nuovoStato
        );
    }
}