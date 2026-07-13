package test;

import java.util.List;

import dao.PrenotazioneEventoDAO;
import model.PrenotazioneEvento;

public class TestPrenotazioneEventoDAO {

    public static void main(String[] args) {
        PrenotazioneEventoDAO dao = new PrenotazioneEventoDAO();

        List<PrenotazioneEvento> prenotazioni = dao.getTuttePrenotazioni();

        System.out.println("Numero prenotazioni evento trovate: " + prenotazioni.size());

        for (PrenotazioneEvento p : prenotazioni) {
            System.out.println(
                    p.getIdPrenotazione() + " - Cliente: " +
                    p.getIdCliente() + " - Sala: " +
                    p.getIdSala() + " - Manager: " +
                    p.getIdManager() + " - Data: " +
                    p.getDataEvento() + " - Ora: " +
                    p.getOraInizio() + "-" +
                    p.getOraFine() + " - Partecipanti: " +
                    p.getNumeroPartecipanti() + " - Stato: " +
                    p.getStato() + " - Note: " +
                    p.getNote()
            );
        }

        boolean disponibile = dao.salaDisponibile(1, "2026-07-15", "18:00", "20:00");
        System.out.println("Sala 1 disponibile il 2026-07-15 dalle 18:00 alle 20:00: " + disponibile);
    }
}