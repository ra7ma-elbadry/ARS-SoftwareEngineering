package dao;
import database.DatabaseConnection;
import model.PrenotazioneEvento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PrenotazioneEventoDAO {
	public void salvaPrenotazione(PrenotazioneEvento prenotazione) {
		String sql = "INSERT INTO PrenotazioneEvento "
		        + "(id_cliente, id_sala, id_manager, data_evento, ora_inizio, ora_fine, numero_partecipanti, stato, note) "
		        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try {
		    Connection conn = DatabaseConnection.getConnection();
		    PreparedStatement stmt = conn.prepareStatement(sql);

		    stmt.setInt(1, prenotazione.getIdCliente());
		    stmt.setInt(2, prenotazione.getIdSala());
		    stmt.setInt(3, prenotazione.getIdManager());
		    stmt.setString(4, prenotazione.getDataEvento());
		    stmt.setString(5, prenotazione.getOraInizio());
		    stmt.setString(6, prenotazione.getOraFine());
		    stmt.setInt(7, prenotazione.getNumeroPartecipanti());
		    stmt.setString(8, prenotazione.getStato());
		    stmt.setString(9, prenotazione.getNote());

		    stmt.executeUpdate();

		    stmt.close();
		    conn.close();

		} catch (Exception e) {
		    System.out.println("Errore durante il salvataggio della prenotazione evento:");
		    e.printStackTrace();
		}
	}
	public List<PrenotazioneEvento> getTuttePrenotazioni() {
	    List<PrenotazioneEvento> listaPrenotazioni = new ArrayList<>();

	    String sql = "SELECT * FROM PrenotazioneEvento";

	    try {
	        Connection conn = DatabaseConnection.getConnection();
	        PreparedStatement stmt = conn.prepareStatement(sql);
	        ResultSet rs = stmt.executeQuery();

	        while (rs.next()) {
	            PrenotazioneEvento prenotazione = new PrenotazioneEvento(
	                    rs.getInt("id_prenotazione"),
	                    rs.getInt("id_cliente"),
	                    rs.getInt("id_sala"),
	                    rs.getInt("id_manager"),
	                    rs.getString("data_evento"),
	                    rs.getString("ora_inizio"),
	                    rs.getString("ora_fine"),
	                    rs.getInt("numero_partecipanti"),
	                    rs.getString("stato"),
	                    rs.getString("note")
	            );

	            listaPrenotazioni.add(prenotazione);
	        }

	        rs.close();
	        stmt.close();
	        conn.close();

	    } catch (Exception e) {
	        System.out.println("Errore durante il caricamento delle prenotazioni evento:");
	        e.printStackTrace();
	    }

	    return listaPrenotazioni;
	}
	public void eliminaPrenotazione(int idPrenotazione) {
	    String sql = "DELETE FROM PrenotazioneEvento WHERE id_prenotazione = ?";

	    try {
	        Connection conn = DatabaseConnection.getConnection();
	        PreparedStatement stmt = conn.prepareStatement(sql);

	        stmt.setInt(1, idPrenotazione);
	        stmt.executeUpdate();

	        stmt.close();
	        conn.close();

	    } catch (Exception e) {
	        System.out.println("Errore durante l'eliminazione della prenotazione evento:");
	        e.printStackTrace();
	    }
	    
	}
	public void modificaPrenotazione(PrenotazioneEvento prenotazione) {
	    String sql = "UPDATE PrenotazioneEvento SET id_cliente = ?, id_sala = ?, data_evento = ?, numero_partecipanti = ? WHERE id_prenotazione = ?";

	    try {
	        Connection conn = DatabaseConnection.getConnection();
	        PreparedStatement stmt = conn.prepareStatement(sql);

	        stmt.setInt(1, prenotazione.getIdCliente());
	        stmt.setInt(2, prenotazione.getIdSala());
	        stmt.setString(3, prenotazione.getDataEvento());
	        stmt.setInt(4, prenotazione.getNumeroPartecipanti());
	        stmt.setInt(5, prenotazione.getIdPrenotazione());

	        stmt.executeUpdate();

	        stmt.close();
	        conn.close();

	    } catch (Exception e) {
	        System.out.println("Errore durante la modifica della prenotazione evento:");
	        e.printStackTrace();
	    }
	    
	}
	 public boolean salaDisponibile(int idSala, String dataEvento, String oraInizio, String oraFine) {
		    String sql = "SELECT COUNT(*) FROM PrenotazioneEvento "
		            + "WHERE id_sala = ? "
		            + "AND data_evento = ? "
		            + "AND stato != 'ANNULLATA' "
		            + "AND (ora_inizio < ? AND ora_fine > ?)";

		    try {
		        Connection conn = DatabaseConnection.getConnection();
		        PreparedStatement stmt = conn.prepareStatement(sql);

		        stmt.setInt(1, idSala);
		        stmt.setString(2, dataEvento);
		        stmt.setString(3, oraFine);
		        stmt.setString(4, oraInizio);

		        ResultSet rs = stmt.executeQuery();

		        int count = 0;
		        if (rs.next()) {
		            count = rs.getInt(1);
		        }

		        rs.close();
		        stmt.close();
		        conn.close();

		        return count == 0;

		    } catch (Exception e) {
		        System.out.println("Errore controllo disponibilità sala:");
		        e.printStackTrace();
		    }

		    return false;
	 }
	    
	       
}