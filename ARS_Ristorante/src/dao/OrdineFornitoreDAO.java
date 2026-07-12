package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import database.DatabaseConnection;
import model.OrdineFornitore;
import model.OrdineFornitoreVisualizzato;

public class OrdineFornitoreDAO {

    public List<OrdineFornitore> getTuttiOrdiniFornitore() {
        List<OrdineFornitore> ordini = new ArrayList<>();

        String sql = """
                SELECT 
                    id_ordine_fornitore,
                    id_fornitore,
                    id_manager,
                    data_ordine,
                    stato
                FROM OrdineFornitore
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                OrdineFornitore ordine = new OrdineFornitore();

                ordine.setId(rs.getInt("id_ordine_fornitore"));
                ordine.setIdFornitore(rs.getInt("id_fornitore"));
                ordine.setIdManager(rs.getInt("id_manager"));
                ordine.setDataOrdine(LocalDate.parse(rs.getString("data_ordine")));
                ordine.setStato(rs.getString("stato"));

                ordini.add(ordine);
            }

        } catch (Exception e) {
            System.out.println("Errore nel recupero degli ordini fornitore");
            e.printStackTrace();
        }

        return ordini;
    }
    public boolean aggiungiOrdineFornitore(OrdineFornitore ordine) {
        String sql = """
                INSERT INTO OrdineFornitore (id_fornitore, id_manager, data_ordine, stato)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, ordine.getIdFornitore());
            stmt.setInt(2, ordine.getIdManager());
            stmt.setString(3, ordine.getDataOrdine().toString());
            stmt.setString(4, ordine.getStato());

            int righeInserite = stmt.executeUpdate();
            return righeInserite > 0;

        } catch (Exception e) {
            System.out.println("Errore nell'aggiunta dell'ordine fornitore");
            e.printStackTrace();
            return false;
        }
    }

    public boolean aggiornaStatoOrdine(int idOrdineFornitore, String nuovoStato) {
        String sql = """
                UPDATE OrdineFornitore
                SET stato = ?
                WHERE id_ordine_fornitore = ?
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nuovoStato);
            stmt.setInt(2, idOrdineFornitore);

            int righeAggiornate = stmt.executeUpdate();
            return righeAggiornate > 0;

        } catch (Exception e) {
            System.out.println("Errore nell'aggiornamento dello stato dell'ordine fornitore");
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminaOrdineFornitore(int idOrdineFornitore) {
        String sql = """
                DELETE FROM OrdineFornitore
                WHERE id_ordine_fornitore = ?
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idOrdineFornitore);

            int righeEliminate = stmt.executeUpdate();
            return righeEliminate > 0;

        } catch (Exception e) {
            System.out.println("Errore nell'eliminazione dell'ordine fornitore");
            e.printStackTrace();
            return false;
        }
    }
    public int creaOrdineERestituisciId(OrdineFornitore ordine) {
        String sql = """
                INSERT INTO OrdineFornitore (id_fornitore, id_manager, data_ordine, stato)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, ordine.getIdFornitore());
            stmt.setInt(2, ordine.getIdManager());
            stmt.setString(3, ordine.getDataOrdine().toString());
            stmt.setString(4, ordine.getStato());

            int righeInserite = stmt.executeUpdate();

            if (righeInserite > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Errore nella creazione dell'ordine fornitore con ID");
            e.printStackTrace();
        }

        return -1;
    }
    public List<OrdineFornitoreVisualizzato> getOrdiniVisualizzatiPerFornitore(int idFornitore) {
        List<OrdineFornitoreVisualizzato> ordiniVisualizzati = new ArrayList<>();

        String sql = """
                SELECT 
                    o.id_ordine_fornitore,
                    o.data_ordine,
                    o.stato,
                    p.nome AS nome_prodotto,
                    d.quantita,
                    d.prezzo_unitario
                FROM OrdineFornitore o
                JOIN DettaglioOrdineFornitore d
                    ON o.id_ordine_fornitore = d.id_ordine_fornitore
                JOIN ProdottoMagazzino p
                    ON d.id_prodotto = p.id_prodotto
                WHERE o.id_fornitore = ?
                ORDER BY o.id_ordine_fornitore DESC
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idFornitore);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    OrdineFornitoreVisualizzato ordine = new OrdineFornitoreVisualizzato();

                    ordine.setIdOrdine(rs.getInt("id_ordine_fornitore"));
                    ordine.setDataOrdine(rs.getString("data_ordine"));
                    ordine.setStato(rs.getString("stato"));
                    ordine.setNomeProdotto(rs.getString("nome_prodotto"));
                    ordine.setQuantita(rs.getDouble("quantita"));
                    ordine.setPrezzoUnitario(rs.getDouble("prezzo_unitario"));

                    ordiniVisualizzati.add(ordine);
                }
            }

        } catch (Exception e) {
            System.out.println("Errore nel recupero degli ordini visualizzati per fornitore");
            e.printStackTrace();
        }

        return ordiniVisualizzati;
    }
}