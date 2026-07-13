package test;

import java.util.List;

import dao.OrdinazioneDAO;
import model.Ordinazione;
import model.StatoOrdinazione;

public class TestOrdinazioneCRUD {

    public static void main(String[] args) {
        OrdinazioneDAO dao = new OrdinazioneDAO();

        // 1. Creazione ordinazione test
        Ordinazione nuovaOrdinazione = new Ordinazione(
                0,
                1,
                1,
                "2026-08-20 19:30",
                StatoOrdinazione.IN_ATTESA,
                0.0
        );

        int idCreato = dao.creaOrdinazione(nuovaOrdinazione);

        if (idCreato == -1) {
            System.out.println("Errore nella creazione dell'ordinazione test.");
            return;
        }

        System.out.println("Ordinazione Test creata con id: " + idCreato);

        // 2. Lettura ordinazione per ID
        Ordinazione ordinazioneLetta = dao.getOrdinazioneById(idCreato);

        if (ordinazioneLetta == null) {
            System.out.println("Ordinazione Test non trovata.");
            return;
        }

        System.out.println(
                "Ordinazione trovata: " +
                ordinazioneLetta.getIdOrdinazione() +
                " - Tavolo: " +
                ordinazioneLetta.getIdTavolo() +
                " - Stato: " +
                ordinazioneLetta.getStato()
        );

        // 3. Aggiornamento stato
        dao.aggiornaStatoOrdinazione(idCreato, StatoOrdinazione.IN_PREPARAZIONE);
        System.out.println("Stato aggiornato a IN_PREPARAZIONE.");

        // 4. Verifica aggiornamento
        Ordinazione ordinazioneAggiornata = dao.getOrdinazioneById(idCreato);

        if (ordinazioneAggiornata != null) {
            System.out.println(
                    "Verifica stato: ordine #" +
                    ordinazioneAggiornata.getIdOrdinazione() +
                    " - Stato: " +
                    ordinazioneAggiornata.getStato()
            );
        }

        // 5. Lettura finale
        List<Ordinazione> ordinazioni = dao.getAllOrdinazioni();
        System.out.println("Numero ordinazioni totale: " + ordinazioni.size());

        System.out.println("Test Ordinazione CRUD completato.");
    }
}