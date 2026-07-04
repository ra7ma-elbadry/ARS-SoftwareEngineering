package dao;

import database.DatabaseConnection;
import model.RuoloUtente;
import model.Utente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UtenteDAO {

    public Utente login(String username, String password) {
        String sql = "SELECT * FROM Utente WHERE username = ? AND password = ?";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Utente utente = new Utente(
                        rs.getInt("id_utente"),
                        rs.getString("nome"),
                        rs.getString("cognome"),
                        rs.getString("username"),
                        rs.getString("password"),
                        RuoloUtente.valueOf(rs.getString("ruolo"))
                );

                rs.close();
                stmt.close();
                conn.close();

                return utente;
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (Exception e) {
            System.out.println("Errore durante il login:");
            e.printStackTrace();
        }

        return null;
    }
}