package dao;

import java.util.List;

import model.DettaglioOrdinazione;

public interface DettaglioOrdinazioneDAO {

    void aggiungiDettaglio(DettaglioOrdinazione dettaglio);

    List<DettaglioOrdinazione> getDettagliByOrdinazione(int idOrdinazione);

    List<String> getDettagliConNomeProdotto(int idOrdinazione);
}