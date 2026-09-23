package facade;

import java.util.List;

import dao.PrenotazioneEventoDAO;
import dao.SalaEventoDAO;
import dao.impl.PrenotazioneEventoDAOImpl;
import dao.impl.SalaEventoDAOImpl;
import model.PrenotazioneEvento;
import model.SalaEvento;

public class GestioneSalaEventiFacade {

    private PrenotazioneEventoDAO prenotazioneDAO;
    private SalaEventoDAO salaDAO;

    public GestioneSalaEventiFacade() {
        prenotazioneDAO = new PrenotazioneEventoDAOImpl();
        salaDAO = new SalaEventoDAOImpl();
    }

    public List<PrenotazioneEvento> getTuttePrenotazioni() {
        return prenotazioneDAO.getTuttePrenotazioni();
    }

    public List<SalaEvento> getSaleDisponibili() {
        return salaDAO.getSaleDisponibili();
    }

    public boolean aggiungiPrenotazione(
            PrenotazioneEvento prenotazione) {

        SalaEvento sala =
                salaDAO.getSalaById(
                        prenotazione.getIdSala()
                );

        if (sala == null || !sala.isDisponibile()) {
            return false;
        }

        if (prenotazione.getNumeroPartecipanti() <= 0
                || prenotazione.getNumeroPartecipanti()
                > sala.getCapienza()) {

            return false;
        }

        boolean disponibile =
                prenotazioneDAO.salaDisponibile(
                        prenotazione.getIdSala(),
                        prenotazione.getDataEvento(),
                        prenotazione.getOraInizio(),
                        prenotazione.getOraFine()
                );

        if (!disponibile) {
            return false;
        }

        prenotazioneDAO.salvaPrenotazione(prenotazione);

        return true;
    }

    public boolean modificaPrenotazione(
            PrenotazioneEvento prenotazione) {

        SalaEvento sala =
                salaDAO.getSalaById(
                        prenotazione.getIdSala()
                );

        if (sala == null) {
            return false;
        }

        if (prenotazione.getNumeroPartecipanti() <= 0
                || prenotazione.getNumeroPartecipanti()
                > sala.getCapienza()) {

            return false;
        }

        prenotazioneDAO.modificaPrenotazione(prenotazione);

        return true;
    }

    public void eliminaPrenotazione(int idPrenotazione) {
        prenotazioneDAO.eliminaPrenotazione(idPrenotazione);
    }
}