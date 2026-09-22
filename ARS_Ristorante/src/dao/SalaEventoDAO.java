package dao;

import java.util.List;

import model.SalaEvento;

public interface SalaEventoDAO {

    void aggiungiSala(SalaEvento sala);

    List<SalaEvento> getTutteSale();

    List<SalaEvento> getSaleDisponibili();

    SalaEvento getSalaById(int idSala);

    void eliminaSala(int idSala);
}