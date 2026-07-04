package controller;

import java.io.File;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class HomeManagerController {

    @FXML
    private Button gestioneMenuButton;

    @FXML
    private Button logoutButton;

    @FXML
    private void apriGestioneMenu() {
        try {
            File fileFXML = new File("view/GestioneMenuView.fxml");
            Parent root = FXMLLoader.load(fileFXML.toURI().toURL());

            Stage stage = (Stage) gestioneMenuButton.getScene().getWindow();
            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.setTitle("ARS Ristorante - Gestione Menu");

        } catch (Exception e) {
            System.out.println("Errore apertura Gestione Menu:");
            e.printStackTrace();
        }
    }

    @FXML
    private void logout() {
        try {
            File fileFXML = new File("view/LoginView.fxml");
            Parent root = FXMLLoader.load(fileFXML.toURI().toURL());

            Stage stage = (Stage) logoutButton.getScene().getWindow();
            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.setTitle("ARS Ristorante - Login");

        } catch (Exception e) {
            System.out.println("Errore durante il logout:");
            e.printStackTrace();
        }
    }
}