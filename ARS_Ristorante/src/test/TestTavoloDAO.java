package test;

import java.util.List;

import dao.TavoloDAO;
import model.StatoTavolo;
import model.Tavolo;

public class TestTavoloDAO {

    public static void main(String[] args) {
        TavoloDAO dao = new TavoloDAO();

        List<Tavolo> tavoli = dao.getAllTavoli();
        System.out.println("Numero tavoli trovati: " + tavoli.size());

        for (Tavolo t : tavoli) {
            System.out.println(
                    t.getIdTavolo() + " - Tavolo numero: " +
                    t.getNumero() + " - posti: " +
                    t.getPosti() + " - stato: " +
                    t.getStato()
            );
        }

        System.out.println();

        List<Tavolo> tavoliDisponibili = dao.getTavoliDisponibili();
        System.out.println("Numero tavoli disponibili: " + tavoliDisponibili.size());

        for (Tavolo t : tavoliDisponibili) {
            System.out.println(
                    "Disponibile → Tavolo " +
                    t.getNumero() +
                    " - posti: " +
                    t.getPosti()
            );
        }

        if (!tavoli.isEmpty()) {
            Tavolo primoTavolo = tavoli.get(0);
            StatoTavolo statoOriginale = primoTavolo.getStato();

            dao.aggiornaStatoTavolo(primoTavolo.getIdTavolo(), StatoTavolo.OCCUPATO);
            System.out.println("Tavolo " + primoTavolo.getNumero() + " aggiornato a OCCUPATO.");

            dao.aggiornaStatoTavolo(primoTavolo.getIdTavolo(), statoOriginale);
            System.out.println("Stato originale ripristinato: " + statoOriginale);
        }

        System.out.println("Test TavoloDAO completato.");
    }
}