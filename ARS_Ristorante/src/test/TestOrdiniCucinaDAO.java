package test;

import java.util.List;

import dao.OrdinazioneDAO;
import model.Ordinazione;
import model.StatoOrdinazione;

public class TestOrdiniCucinaDAO {

    public static void main(String[] args) {
        OrdinazioneDAO dao = new OrdinazioneDAO();

        List<Ordinazione> ordiniCucina = dao.getOrdinazioniPerCucina();

        System.out.println("Numero ordini visibili in cucina: " + ordiniCucina.size());

        for (Ordinazione o : ordiniCucina) {
            System.out.println(
                    "Ordine #" + o.getIdOrdinazione() +
                    " - Tavolo: " + o.getIdTavolo() +
                    " - Stato: " + o.getStato()
            );
        }

        if (!ordiniCucina.isEmpty()) {
            Ordinazione ordineTest = ordiniCucina.get(0);
            StatoOrdinazione statoOriginale = ordineTest.getStato();

            dao.aggiornaStatoOrdinazione(ordineTest.getIdOrdinazione(), StatoOrdinazione.IN_PREPARAZIONE);
            System.out.println("Ordine #" + ordineTest.getIdOrdinazione() + " aggiornato a IN_PREPARAZIONE.");

            dao.aggiornaStatoOrdinazione(ordineTest.getIdOrdinazione(), StatoOrdinazione.PRONTO);
            System.out.println("Ordine #" + ordineTest.getIdOrdinazione() + " aggiornato a PRONTO.");

            dao.aggiornaStatoOrdinazione(ordineTest.getIdOrdinazione(), statoOriginale);
            System.out.println("Stato originale ripristinato: " + statoOriginale);
        }

        System.out.println("Test OrdiniCucinaDAO completato.");
    }
}