package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import database.DatabaseConnection;
import model.SalaEvento;

public class SalaEventoDAO {

    public void aggiungiSala(SalaEvento sala) {
        String sql = "INSERT INTO SalaEvento (nome, capienza, descrizione, disponibile) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, sala.getNome());
            pstmt.setInt(2, sala.getCapienza());
            pstmt.setString(3, sala.getDescrizione());
            pstmt.setInt(4, sala.isDisponibile() ? 1 : 0);

            pstmt.executeUpdate();

        } catch (Exception e) {
            System.out.println("Errore aggiunta sala evento:");
            e.printStackTrace();
        }
    }

    public List<SalaEvento> getTutteSale() {
        List<SalaEvento> sale = new ArrayList<>();
        String sql = "SELECT * FROM SalaEvento";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                SalaEvento sala = new SalaEvento(
                        rs.getInt("id_sala"),
                        rs.getString("nome"),
                        rs.getInt("capienza"),
                        rs.getString("descrizione"),
                        rs.getInt("disponibile") == 1
                );

                sale.add(sala);
            }

        } catch (Exception e) {
            System.out.println("Errore lettura sale eventi:");
            e.printStackTrace();
        }

        return sale;
    }

    public void eliminaSala(int idSala) {
        String sql = "DELETE FROM SalaEvento WHERE id_sala = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idSala);
            pstmt.executeUpdate();

        } catch (Exception e) {
            System.out.println("Errore eliminazione sala evento:");
            e.printStackTrace();
        }
    }
}