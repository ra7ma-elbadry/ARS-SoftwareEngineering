package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dao.SalaEventoDAO;
import database.DatabaseConnection;
import model.SalaEvento;

public class SalaEventoDAOImpl implements SalaEventoDAO {

    @Override
    public void aggiungiSala(SalaEvento sala) {
        String sql = "INSERT INTO SalaEvento (nome, capienza, descrizione, disponibile) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, sala.getNome());
            stmt.setInt(2, sala.getCapienza());
            stmt.setString(3, sala.getDescrizione());
            stmt.setInt(4, sala.isDisponibile() ? 1 : 0);

            stmt.executeUpdate();

        } catch (Exception e) {
            System.out.println("Errore aggiunta sala evento:");
            e.printStackTrace();
        }
    }

    @Override
    public List<SalaEvento> getTutteSale() {
        return caricaSale("SELECT * FROM SalaEvento");
    }

    @Override
    public List<SalaEvento> getSaleDisponibili() {
        return caricaSale("SELECT * FROM SalaEvento WHERE disponibile = 1");
    }

    @Override
    public SalaEvento getSalaById(int idSala) {
        String sql = "SELECT * FROM SalaEvento WHERE id_sala = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idSala);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return creaSalaDaResultSet(rs);
                }
            }

        } catch (Exception e) {
            System.out.println("Errore lettura sala evento:");
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void eliminaSala(int idSala) {
        String sql = "DELETE FROM SalaEvento WHERE id_sala = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idSala);
            stmt.executeUpdate();

        } catch (Exception e) {
            System.out.println("Errore eliminazione sala evento:");
            e.printStackTrace();
        }
    }

    private List<SalaEvento> caricaSale(String sql) {

        List<SalaEvento> sale = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                sale.add(creaSalaDaResultSet(rs));
            }

        } catch (Exception e) {
            System.out.println("Errore lettura sale eventi:");
            e.printStackTrace();
        }

        return sale;
    }

    private SalaEvento creaSalaDaResultSet(ResultSet rs) throws Exception {

        return new SalaEvento(
                rs.getInt("id_sala"),
                rs.getString("nome"),
                rs.getInt("capienza"),
                rs.getString("descrizione"),
                rs.getInt("disponibile") == 1
        );
    }
}