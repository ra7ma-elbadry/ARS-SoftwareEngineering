package application;

import java.io.File;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {

            File fileFXML = new File("view/LoginView.fxml");

            Parent root = FXMLLoader.load(fileFXML.toURI().toURL());

            Scene scene = new Scene(root);

            primaryStage.setTitle("ARS Ristorante");
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e) {
            System.out.println("Errore caricamento schermata Login:");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}