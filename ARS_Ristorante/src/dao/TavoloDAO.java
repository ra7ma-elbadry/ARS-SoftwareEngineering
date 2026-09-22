package dao;

import java.util.List;

import model.StatoTavolo;
import model.Tavolo;

public interface TavoloDAO {

    List<Tavolo> getAllTavoli();

    List<Tavolo> getTavoliDisponibili();

    void aggiornaStatoTavolo(int idTavolo, StatoTavolo nuovoStato);
}