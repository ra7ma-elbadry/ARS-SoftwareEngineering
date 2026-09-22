package dao;

import java.util.List;

import model.Ordinazione;
import model.StatoOrdinazione;

public interface OrdinazioneDAO {

    int creaOrdinazione(Ordinazione ordinazione);

    List<Ordinazione> getAllOrdinazioni();

    List<Ordinazione> getOrdinazioniPerCucina();

    void aggiornaStatoOrdinazione(int idOrdinazione, StatoOrdinazione nuovoStato);

    List<Ordinazione> getOrdinazioniPronteOConsegnate();

    Ordinazione getOrdinazioneById(int idOrdinazione);
}