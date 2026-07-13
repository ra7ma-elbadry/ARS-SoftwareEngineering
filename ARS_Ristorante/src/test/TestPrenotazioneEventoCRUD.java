package test;

import java.util.List;

import dao.PrenotazioneEventoDAO;
import model.PrenotazioneEvento;

public class TestPrenotazioneEventoCRUD {

    public static void main(String[] args) {
        PrenotazioneEventoDAO dao = new PrenotazioneEventoDAO();

        // 1. Creazione prenotazione test
        PrenotazioneEvento nuovaPrenotazione = new PrenotazioneEvento(
                0,
                1,
                1,
                3,
                "2026-08-20",
                "18:00",
                "22:00",
                25,
                "CONFERMATA",
                "Prenotazione test"
        );

        dao.salvaPrenotazione(nuovaPrenotazione);
        System.out.println("Prenotazione Test aggiunta.");

        // 2. Lettura prenotazioni
        List<PrenotazioneEvento> prenotazioni = dao.getTuttePrenotazioni();
        System.out.println("Numero prenotazioni dopo aggiunta: " + prenotazioni.size());

        PrenotazioneEvento prenotazioneTest = null;

        for (PrenotazioneEvento p : prenotazioni) {
            if (p.getNote() != null && p.getNote().equals("Prenotazione test")) {
                prenotazioneTest = p;
                break;
            }
        }

        if (prenotazioneTest == null) {
            System.out.println("Prenotazione Test non trovata.");
            return;
        }

        System.out.println("Prenotazione Test trovata con id: " + prenotazioneTest.getIdPrenotazione());

        // 3. Modifica prenotazione
        prenotazioneTest.setNumeroPartecipanti(30);
        prenotazioneTest.setStato("MODIFICATA");
        prenotazioneTest.setNote("Prenotazione test modificata");

        dao.modificaPrenotazione(prenotazioneTest);
        System.out.println("Prenotazione Test modificata.");

        // 4. Verifica modifica
        List<PrenotazioneEvento> prenotazioniModificate = dao.getTuttePrenotazioni();

        for (PrenotazioneEvento p : prenotazioniModificate) {
            if (p.getIdPrenotazione() == prenotazioneTest.getIdPrenotazione()) {
                System.out.println(
                        "Verifica modifica: id " +
                        p.getIdPrenotazione() +
                        " - partecipanti: " +
                        p.getNumeroPartecipanti() +
                        " - stato: " +
                        p.getStato() +
                        " - note: " +
                        p.getNote()
                );
            }
        }

        // 5. Eliminazione prenotazione test
        dao.eliminaPrenotazione(prenotazioneTest.getIdPrenotazione());
        System.out.println("Prenotazione Test eliminata.");

        // 6. Lettura finale
        List<PrenotazioneEvento> prenotazioniFinali = dao.getTuttePrenotazioni();
        System.out.println("Numero prenotazioni finale: " + prenotazioniFinali.size());

        System.out.println("Test PrenotazioneEvento CRUD completato.");
    }
}