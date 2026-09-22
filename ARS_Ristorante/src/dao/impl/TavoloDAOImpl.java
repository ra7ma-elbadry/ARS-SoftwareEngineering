package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dao.TavoloDAO;
import database.DatabaseConnection;
import model.StatoTavolo;
import model.Tavolo;

public class TavoloDAOImpl implements TavoloDAO {

    @Override
    public List<Tavolo> getAllTavoli() {

        List<Tavolo> tavoli = new ArrayList<>();

        String sql = "SELECT * FROM Tavolo";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                Tavolo tavolo = creaTavoloDaResultSet(rs);

                tavoli.add(tavolo);
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (Exception e) {

            System.out.println(
                    "Errore durante il caricamento dei tavoli:"
            );

            e.printStackTrace();
        }

        return tavoli;
    }

    @Override
    public List<Tavolo> getTavoliDisponibili() {

        List<Tavolo> tavoli = new ArrayList<>();

        String sql =
                "SELECT * FROM Tavolo WHERE stato = 'LIBERO'";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                Tavolo tavolo = creaTavoloDaResultSet(rs);

                tavoli.add(tavolo);
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (Exception e) {

            System.out.println(
                    "Errore durante il caricamento dei tavoli disponibili:"
            );

            e.printStackTrace();
        }

        return tavoli;
    }

    @Override
    public void aggiornaStatoTavolo(
            int idTavolo,
            StatoTavolo nuovoStato) {

        String sql =
                "UPDATE Tavolo SET stato = ? "
                + "WHERE id_tavolo = ?";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, nuovoStato.name());
            stmt.setInt(2, idTavolo);

            stmt.executeUpdate();

            stmt.close();
            conn.close();

        } catch (Exception e) {

            System.out.println(
                    "Errore durante l'aggiornamento dello stato del tavolo:"
            );

            e.printStackTrace();
        }
    }

    private Tavolo creaTavoloDaResultSet(ResultSet rs)
            throws Exception {

        return new Tavolo(
                rs.getInt("id_tavolo"),
                rs.getInt("numero"),
                rs.getInt("posti"),
                StatoTavolo.valueOf(
                        rs.getString("stato")
                )
        );
    }
}