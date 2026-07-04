package controller;

import java.io.File;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class HomeCuocoController {

    @FXML
    private Button ordiniCucinaButton;

    @FXML
    private Button logoutButton;

    @FXML
    private void apriOrdiniCucina() {
        try {
            File fileFXML = new File("view/OrdiniCucinaView.fxml");
            Parent root = FXMLLoader.load(fileFXML.toURI().toURL());

            Stage stage = (Stage) ordiniCucinaButton.getScene().getWindow();
            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.setTitle("ARS Ristorante - Ordini Cucina");

        } catch (Exception e) {
            System.out.println("Errore apertura Ordini Cucina:");
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