package dao;

import java.util.List;

import model.PrenotazioneEvento;

public interface PrenotazioneEventoDAO {

    void salvaPrenotazione(PrenotazioneEvento prenotazione);

    List<PrenotazioneEvento> getTuttePrenotazioni();

    void eliminaPrenotazione(int idPrenotazione);

    void modificaPrenotazione(PrenotazioneEvento prenotazione);

    boolean salaDisponibile(
            int idSala,
            String dataEvento,
            String oraInizio,
            String oraFine
    );
}