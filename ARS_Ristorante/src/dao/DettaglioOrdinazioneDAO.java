package dao;

import database.DatabaseConnection;
import model.DettaglioOrdinazione;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DettaglioOrdinazioneDAO {

    public void aggiungiDettaglio(DettaglioOrdinazione dettaglio) {
        String sql = "INSERT INTO DettaglioOrdinazione (id_ordinazione, id_menu, quantita, note, prezzo_unitario) VALUES (?, ?, ?, ?, ?)";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, dettaglio.getIdOrdinazione());
            stmt.setInt(2, dettaglio.getIdMenu());
            stmt.setInt(3, dettaglio.getQuantita());
            stmt.setString(4, dettaglio.getNote());
            stmt.setDouble(5, dettaglio.getPrezzoUnitario());

            stmt.executeUpdate();

            stmt.close();
            conn.close();

        } catch (Exception e) {
            System.out.println("Errore durante l'aggiunta del dettaglio ordinazione:");
            e.printStackTrace();
        }
    }

    public List<DettaglioOrdinazione> getDettagliByOrdinazione(int idOrdinazione) {
        List<DettaglioOrdinazione> dettagli = new ArrayList<>();

        String sql = "SELECT * FROM DettaglioOrdinazione WHERE id_ordinazione = ?";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, idOrdinazione);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                DettaglioOrdinazione dettaglio = new DettaglioOrdinazione(
                        rs.getInt("id_dettaglio"),
                        rs.getInt("id_ordinazione"),
                        rs.getInt("id_menu"),
                        rs.getInt("quantita"),
                        rs.getString("note"),
                        rs.getDouble("prezzo_unitario")
                );

                dettagli.add(dettaglio);
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (Exception e) {
            System.out.println("Errore durante il caricamento dei dettagli ordinazione:");
            e.printStackTrace();
        }

        return dettagli;
    }
    
    public List<String> getDettagliConNomeProdotto(int idOrdinazione) {
        List<String> dettagli = new ArrayList<>();

        String sql = "SELECT d.quantita, d.note, d.prezzo_unitario, m.nome " +
                     "FROM DettaglioOrdinazione d " +
                     "JOIN Menu m ON d.id_menu = m.id_menu " +
                     "WHERE d.id_ordinazione = ?";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, idOrdinazione);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int quantita = rs.getInt("quantita");
                String nomeProdotto = rs.getString("nome");
                String note = rs.getString("note");
                double prezzoUnitario = rs.getDouble("prezzo_unitario");
                double subtotale = quantita * prezzoUnitario;

                if (note == null || note.isEmpty()) {
                    note = "nessuna";
                }

                String riga =
                        quantita + "x " + nomeProdotto +
                        " - note: " + note +
                        " - € " + subtotale;

                dettagli.add(riga);
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (Exception e) {
            System.out.println("Errore durante il caricamento dei dettagli con nome prodotto:");
            e.printStackTrace();
        }

        return dettagli;
    }
    
    
}