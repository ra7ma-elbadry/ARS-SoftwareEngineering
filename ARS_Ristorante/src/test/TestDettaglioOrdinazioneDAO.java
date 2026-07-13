package test;

import java.util.List;

import dao.DettaglioOrdinazioneDAO;
import model.DettaglioOrdinazione;

public class TestDettaglioOrdinazioneDAO {

    public static void main(String[] args) {
        DettaglioOrdinazioneDAO dao = new DettaglioOrdinazioneDAO();

        int idOrdinazioneTest = 1;

        List<DettaglioOrdinazione> dettagli = dao.getDettagliByOrdinazione(idOrdinazioneTest);

        System.out.println("Numero dettagli per ordinazione #" + idOrdinazioneTest + ": " + dettagli.size());

        for (DettaglioOrdinazione d : dettagli) {
            System.out.println(
                    d.getIdDettaglio() + " - Ordinazione: " +
                    d.getIdOrdinazione() + " - Menu: " +
                    d.getIdMenu() + " - Quantità: " +
                    d.getQuantita() + " - Note: " +
                    d.getNote() + " - Prezzo unitario: " +
                    d.getPrezzoUnitario()
            );
        }

        System.out.println();

        List<String> dettagliConNome = dao.getDettagliConNomeProdotto(idOrdinazioneTest);

        System.out.println("Dettagli con nome prodotto:");

        for (String riga : dettagliConNome) {
            System.out.println(riga);
        }

        System.out.println("Test DettaglioOrdinazioneDAO completato.");
    }
}