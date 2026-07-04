package dao;

import database.DatabaseConnection;
import model.Ordinazione;
import model.StatoOrdinazione;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OrdinazioneDAO {

	public int creaOrdinazione(Ordinazione ordinazione) {
	    String sql = "INSERT INTO Ordinazione (id_tavolo, id_cameriere, data_ora, stato, totale) VALUES (?, ?, ?, ?, ?)";

	    try {
	        Connection conn = DatabaseConnection.getConnection();
	        PreparedStatement stmt = conn.prepareStatement(sql);

	        stmt.setInt(1, ordinazione.getIdTavolo());
	        stmt.setInt(2, ordinazione.getIdCameriere());
	        stmt.setString(3, ordinazione.getDataOra());
	        stmt.setString(4, ordinazione.getStato().name());
	        stmt.setDouble(5, ordinazione.getTotale());

	        stmt.executeUpdate();

	        stmt.close();

	        String sqlId = "SELECT last_insert_rowid()";
	        PreparedStatement stmtId = conn.prepareStatement(sqlId);
	        ResultSet rs = stmtId.executeQuery();

	        int idGenerato = -1;

	        if (rs.next()) {
	            idGenerato = rs.getInt(1);
	        }

	        rs.close();
	        stmtId.close();
	        conn.close();

	        return idGenerato;

	    } catch (Exception e) {
	        System.out.println("Errore durante la creazione dell'ordinazione:");
	        e.printStackTrace();
	    }

	    return -1;
	}

    public List<Ordinazione> getAllOrdinazioni() {
        List<Ordinazione> ordinazioni = new ArrayList<>();

        String sql = "SELECT * FROM Ordinazione";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Ordinazione ordinazione = new Ordinazione(
                        rs.getInt("id_ordinazione"),
                        rs.getInt("id_tavolo"),
                        rs.getInt("id_cameriere"),
                        rs.getString("data_ora"),
                        StatoOrdinazione.valueOf(rs.getString("stato")),
                        rs.getDouble("totale")
                );

                ordinazioni.add(ordinazione);
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (Exception e) {
            System.out.println("Errore durante il caricamento delle ordinazioni:");
            e.printStackTrace();
        }

        return ordinazioni;
    }

    public List<Ordinazione> getOrdinazioniPerCucina() {
        List<Ordinazione> ordinazioni = new ArrayList<>();

        String sql = "SELECT * FROM Ordinazione WHERE stato IN ('IN_ATTESA', 'IN_PREPARAZIONE', 'PRONTO')";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Ordinazione ordinazione = new Ordinazione(
                        rs.getInt("id_ordinazione"),
                        rs.getInt("id_tavolo"),
                        rs.getInt("id_cameriere"),
                        rs.getString("data_ora"),
                        StatoOrdinazione.valueOf(rs.getString("stato")),
                        rs.getDouble("totale")
                );

                ordinazioni.add(ordinazione);
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (Exception e) {
            System.out.println("Errore durante il caricamento delle ordinazioni per cucina:");
            e.printStackTrace();
        }

        return ordinazioni;
    }

    public void aggiornaStatoOrdinazione(int idOrdinazione, StatoOrdinazione nuovoStato) {
        String sql = "UPDATE Ordinazione SET stato = ? WHERE id_ordinazione = ?";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, nuovoStato.name());
            stmt.setInt(2, idOrdinazione);

            stmt.executeUpdate();

            stmt.close();
            conn.close();

        } catch (Exception e) {
            System.out.println("Errore durante l'aggiornamento dello stato dell'ordinazione:");
            e.printStackTrace();
        }
    }
    
    public List<Ordinazione> getOrdinazioniPronteOConsegnate() {
        List<Ordinazione> ordinazioni = new ArrayList<>();

        String sql = "SELECT * FROM Ordinazione WHERE stato IN ('PRONTO', 'CONSEGNATO')";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Ordinazione ordinazione = new Ordinazione(
                        rs.getInt("id_ordinazione"),
                        rs.getInt("id_tavolo"),
                        rs.getInt("id_cameriere"),
                        rs.getString("data_ora"),
                        StatoOrdinazione.valueOf(rs.getString("stato")),
                        rs.getDouble("totale")
                );

                ordinazioni.add(ordinazione);
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (Exception e) {
            System.out.println("Errore durante il caricamento delle ordinazioni pronte/consegnate:");
            e.printStackTrace();
        }

        return ordinazioni;
    }
    
    public Ordinazione getOrdinazioneById(int idOrdinazione) {
        String sql = "SELECT * FROM Ordinazione WHERE id_ordinazione = ?";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, idOrdinazione);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Ordinazione ordinazione = new Ordinazione(
                        rs.getInt("id_ordinazione"),
                        rs.getInt("id_tavolo"),
                        rs.getInt("id_cameriere"),
                        rs.getString("data_ora"),
                        StatoOrdinazione.valueOf(rs.getString("stato")),
                        rs.getDouble("totale")
                );

                rs.close();
                stmt.close();
                conn.close();

                return ordinazione;
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (Exception e) {
            System.out.println("Errore durante il caricamento dell'ordinazione:");
            e.printStackTrace();
        }

        return null;
    }


}

