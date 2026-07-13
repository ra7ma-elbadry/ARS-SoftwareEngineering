package test;

import java.util.List;

import dao.OrdinazioneDAO;
import model.Ordinazione;

public class TestOrdinazioneDAO {

    public static void main(String[] args) {
        OrdinazioneDAO dao = new OrdinazioneDAO();

        List<Ordinazione> ordinazioni = dao.getAllOrdinazioni();

        System.out.println("Numero ordinazioni trovate: " + ordinazioni.size());

        for (Ordinazione o : ordinazioni) {
            System.out.println(
                    o.getIdOrdinazione() + " - Tavolo: " +
                    o.getIdTavolo() + " - Cameriere: " +
                    o.getIdCameriere() + " - Data/Ora: " +
                    o.getDataOra() + " - Stato: " +
                    o.getStato() + " - Totale: " +
                    o.getTotale()
            );
        }

        System.out.println();

        List<Ordinazione> ordinazioniCucina = dao.getOrdinazioniPerCucina();
        System.out.println("Numero ordinazioni per cucina: " + ordinazioniCucina.size());

        for (Ordinazione o : ordinazioniCucina) {
            System.out.println(
                    "Cucina → Ordine #" +
                    o.getIdOrdinazione() +
                    " - Stato: " +
                    o.getStato()
            );
        }

        System.out.println();

        List<Ordinazione> ordinazioniPronte = dao.getOrdinazioniPronteOConsegnate();
        System.out.println("Numero ordinazioni pronte/consegnate: " + ordinazioniPronte.size());

        for (Ordinazione o : ordinazioniPronte) {
            System.out.println(
                    "Pronta/Consegnata → Ordine #" +
                    o.getIdOrdinazione() +
                    " - Stato: " +
                    o.getStato()
            );
        }

        System.out.println();

        Ordinazione ordinazione = dao.getOrdinazioneById(1);

        if (ordinazione != null) {
            System.out.println(
                    "Ordinazione con ID 1 trovata: " +
                    ordinazione.getIdOrdinazione() +
                    " - Stato: " +
                    ordinazione.getStato()
            );
        } else {
            System.out.println("Ordinazione con ID 1 non trovata.");
        }
    }
}