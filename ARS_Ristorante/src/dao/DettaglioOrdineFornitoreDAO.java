package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import database.DatabaseConnection;
import model.DettaglioOrdineFornitore;

public class DettaglioOrdineFornitoreDAO {

    public boolean aggiungiDettaglio(DettaglioOrdineFornitore dettaglio) {
        String sql = """
                INSERT INTO DettaglioOrdineFornitore 
                (id_ordine_fornitore, id_prodotto, quantita, prezzo_unitario)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, dettaglio.getIdOrdineFornitore());
            stmt.setInt(2, dettaglio.getIdProdotto());
            stmt.setDouble(3, dettaglio.getQuantita());
            stmt.setDouble(4, dettaglio.getPrezzoUnitario());

            int righeInserite = stmt.executeUpdate();
            return righeInserite > 0;

        } catch (Exception e) {
            System.out.println("Errore nell'aggiunta del dettaglio ordine fornitore");
            e.printStackTrace();
            return false;
        }
    }
}