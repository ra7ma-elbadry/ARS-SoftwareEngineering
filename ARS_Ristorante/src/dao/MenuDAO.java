package dao;

import database.DatabaseConnection;
import model.CategoriaMenu;
import model.Menu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MenuDAO {

    public List<Menu> getAllMenu() {
        List<Menu> listaMenu = new ArrayList<>();

        String sql = "SELECT * FROM Menu";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Menu menu = new Menu(
                        rs.getInt("id_menu"),
                        rs.getString("nome"),
                        rs.getString("descrizione"),
                        rs.getDouble("prezzo"),
                        CategoriaMenu.valueOf(rs.getString("categoria")),
                        rs.getInt("disponibile") == 1
                );

                listaMenu.add(menu);
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (Exception e) {
            System.out.println("Errore durante il caricamento del menu:");
            e.printStackTrace();
        }

        return listaMenu;
    }
    public List<Menu> getMenuDisponibile() {
        List<Menu> listaMenu = new ArrayList<>();

        String sql = "SELECT * FROM Menu WHERE disponibile = 1";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Menu menu = new Menu(
                        rs.getInt("id_menu"),
                        rs.getString("nome"),
                        rs.getString("descrizione"),
                        rs.getDouble("prezzo"),
                        CategoriaMenu.valueOf(rs.getString("categoria")),
                        rs.getInt("disponibile") == 1
                );

                listaMenu.add(menu);
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (Exception e) {
            System.out.println("Errore durante il caricamento del menu disponibile:");
            e.printStackTrace();
        }

        return listaMenu;
    }
    
    public void aggiungiMenu(Menu menu) {
        String sql = "INSERT INTO Menu (nome, descrizione, prezzo, categoria, disponibile) VALUES (?, ?, ?, ?, ?)";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, menu.getNome());
            stmt.setString(2, menu.getDescrizione());
            stmt.setDouble(3, menu.getPrezzo());
            stmt.setString(4, menu.getCategoria().name());
            stmt.setInt(5, menu.isDisponibile() ? 1 : 0);

            stmt.executeUpdate();

            stmt.close();
            conn.close();

        } catch (Exception e) {
            System.out.println("Errore durante l'aggiunta del piatto al menu:");
            e.printStackTrace();
        }
    }
    
    public void aggiornaDisponibilita(int idMenu, boolean disponibile) {
        String sql = "UPDATE Menu SET disponibile = ? WHERE id_menu = ?";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, disponibile ? 1 : 0);
            stmt.setInt(2, idMenu);

            stmt.executeUpdate();

            stmt.close();
            conn.close();

        } catch (Exception e) {
            System.out.println("Errore durante l'aggiornamento della disponibilità del piatto:");
            e.printStackTrace();
        }
    }
}

