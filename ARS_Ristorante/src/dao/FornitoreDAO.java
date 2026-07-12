package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import database.DatabaseConnection;
import model.Fornitore;

public class FornitoreDAO {

    public List<Fornitore> getTuttiFornitori() {
        List<Fornitore> fornitori = new ArrayList<>();

        String sql = "SELECT * FROM Fornitore";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

        	while (rs.next()) {
        	    Fornitore fornitore = new Fornitore();

        	    fornitore.setId(rs.getInt("id_fornitore"));
        	    fornitore.setNome(rs.getString("nome"));
        	    fornitore.setTelefono(rs.getString("telefono"));
        	    fornitore.setEmail(rs.getString("email"));
        	    fornitore.setIndirizzo(rs.getString("indirizzo"));

        	    fornitori.add(fornitore);
        	}

        } catch (Exception e) {
            System.out.println("Errore nel recupero dei fornitori");
            e.printStackTrace();
        }

        return fornitori;
    }
    public boolean aggiungiFornitore(Fornitore fornitore) {
        String sql = """
                INSERT INTO Fornitore (nome, telefono, email, indirizzo)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, fornitore.getNome());
            stmt.setString(2, fornitore.getTelefono());
            stmt.setString(3, fornitore.getEmail());
            stmt.setString(4, fornitore.getIndirizzo());

            int righeInserite = stmt.executeUpdate();
            return righeInserite > 0;

        } catch (Exception e) {
            System.out.println("Errore nell'aggiunta del fornitore");
            e.printStackTrace();
            return false;
        }
    }

    public boolean aggiornaFornitore(Fornitore fornitore) {
        String sql = """
                UPDATE Fornitore
                SET nome = ?, telefono = ?, email = ?, indirizzo = ?
                WHERE id_fornitore = ?
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, fornitore.getNome());
            stmt.setString(2, fornitore.getTelefono());
            stmt.setString(3, fornitore.getEmail());
            stmt.setString(4, fornitore.getIndirizzo());
            stmt.setInt(5, fornitore.getId());

            int righeAggiornate = stmt.executeUpdate();
            return righeAggiornate > 0;

        } catch (Exception e) {
            System.out.println("Errore nell'aggiornamento del fornitore");
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminaFornitore(int idFornitore) {
        String sql = """
                DELETE FROM Fornitore
                WHERE id_fornitore = ?
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idFornitore);

            int righeEliminate = stmt.executeUpdate();
            return righeEliminate > 0;

        } catch (Exception e) {
            System.out.println("Errore nell'eliminazione del fornitore");
            e.printStackTrace();
            return false;
        }
    }
}