package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import database.DatabaseConnection;
import model.ProdottoMagazzino;

public class ProdottoMagazzinoDAO {

    public List<ProdottoMagazzino> getTuttiProdotti() {
        List<ProdottoMagazzino> prodotti = new ArrayList<>();

        String sql = """
                SELECT 
                    id_prodotto,
                    nome,
                    descrizione,
                    quantita,
                    unita_misura,
                    soglia_minima
                FROM ProdottoMagazzino
                """;
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ProdottoMagazzino prodotto = new ProdottoMagazzino();

                prodotto.setId(rs.getInt("id_prodotto"));
                prodotto.setNome(rs.getString("nome"));
                prodotto.setDescrizione(rs.getString("descrizione"));
                prodotto.setQuantita(rs.getDouble("quantita"));
                prodotto.setUnitaMisura(rs.getString("unita_misura"));
                prodotto.setSogliaMinima(rs.getDouble("soglia_minima"));
                
                prodotti.add(prodotto);
            }

        } catch (Exception e) {
            System.out.println("Errore nel recupero dei prodotti di magazzino");
            e.printStackTrace();
        }

        return prodotti;
    }

    public List<ProdottoMagazzino> getProdottiSottoSoglia() {
        List<ProdottoMagazzino> prodotti = new ArrayList<>();

        String sql = """
                SELECT 
                    id_prodotto,
                    nome,
                    descrizione,
                    quantita,
                    unita_misura,
                    soglia_minima
                FROM ProdottoMagazzino
                WHERE quantita <= soglia_minima
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ProdottoMagazzino prodotto = new ProdottoMagazzino();

                prodotto.setId(rs.getInt("id_prodotto"));
                prodotto.setNome(rs.getString("nome"));
                prodotto.setDescrizione(rs.getString("descrizione"));
                prodotto.setQuantita(rs.getDouble("quantita"));
                prodotto.setUnitaMisura(rs.getString("unita_misura"));
                prodotto.setSogliaMinima(rs.getDouble("soglia_minima"));

                prodotti.add(prodotto);
            }

        } catch (Exception e) {
            System.out.println("Errore nel recupero dei prodotti sotto soglia");
            e.printStackTrace();
        }

        return prodotti;
    }
    public boolean aggiungiProdotto(ProdottoMagazzino prodotto) {
        String sql = """
                INSERT INTO ProdottoMagazzino (nome, descrizione, quantita, unita_misura, soglia_minima)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, prodotto.getNome());
            stmt.setString(2, prodotto.getDescrizione());
            stmt.setDouble(3, prodotto.getQuantita());
            stmt.setString(4, prodotto.getUnitaMisura());
            stmt.setDouble(5, prodotto.getSogliaMinima());

            int righeInserite = stmt.executeUpdate();
            return righeInserite > 0;

        } catch (Exception e) {
            System.out.println("Errore nell'aggiunta del prodotto");
            e.printStackTrace();
            return false;
        }
    }

    public boolean aggiornaProdotto(ProdottoMagazzino prodotto) {
        String sql = """
                UPDATE ProdottoMagazzino
                SET nome = ?, descrizione = ?, quantita = ?, unita_misura = ?, soglia_minima = ?
                WHERE id_prodotto = ?
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, prodotto.getNome());
            stmt.setString(2, prodotto.getDescrizione());
            stmt.setDouble(3, prodotto.getQuantita());
            stmt.setString(4, prodotto.getUnitaMisura());
            stmt.setDouble(5, prodotto.getSogliaMinima());
            stmt.setInt(6, prodotto.getId());

            int righeAggiornate = stmt.executeUpdate();
            return righeAggiornate > 0;

        } catch (Exception e) {
            System.out.println("Errore nell'aggiornamento del prodotto");
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminaProdotto(int idProdotto) {
        String sql = """
                DELETE FROM ProdottoMagazzino
                WHERE id_prodotto = ?
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idProdotto);

            int righeEliminate = stmt.executeUpdate();
            return righeEliminate > 0;

        } catch (Exception e) {
            System.out.println("Errore nell'eliminazione del prodotto");
            e.printStackTrace();
            return false;
        }
    }
}