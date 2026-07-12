package model;

import java.time.LocalDate;

public class OrdineFornitore {

    private int id;
    private int idFornitore;
    private int idManager;
    private LocalDate dataOrdine;
    private String stato;

    public OrdineFornitore() {
    }

    public OrdineFornitore(int id, int idFornitore, int idManager, LocalDate dataOrdine, String stato) {
        this.id = id;
        this.idFornitore = idFornitore;
        this.idManager = idManager;
        this.dataOrdine = dataOrdine;
        this.stato = stato;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }   

    public int getIdFornitore() {
        return idFornitore;
    }

    public void setIdFornitore(int idFornitore) {
        this.idFornitore = idFornitore;
    }

    public int getIdManager() {
        return idManager;
    }

    public void setIdManager(int idManager) {
        this.idManager = idManager;
    }

    public LocalDate getDataOrdine() {
        return dataOrdine;
    }

    public void setDataOrdine(LocalDate dataOrdine) {
        this.dataOrdine = dataOrdine;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public void aggiornaStato(String nuovoStato) {
        this.stato = nuovoStato;
    }

    @Override
    public String toString() {
        return "Ordine #" + id + " - Fornitore: " + idFornitore + " - Stato: " + stato;
    }
}