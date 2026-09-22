package controller;

import java.io.File;

import application.SessioneUtente;
import facade.AutenticazioneFacade;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.RuoloUtente;
import model.Utente;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    /*
     * Il Controller non usa più direttamente UtenteDAO.
     * Comunica con la Facade.
     */
    private AutenticazioneFacade autenticazioneFacade =
            new AutenticazioneFacade();

    @FXML
    private void handleLogin() {

        String username = usernameField.getText();
        String password = passwordField.getText();

        /*
         * Chiediamo alla Facade di effettuare il login.
         */
        Utente utente =
                autenticazioneFacade.login(username, password);

        if (utente == null) {

            errorLabel.setText(
                    "Username o password non corretti"
            );

            return;
        }

        /*
         * Salviamo l'utente autenticato nella sessione.
         */
        SessioneUtente
                .getInstance()
                .setUtente(utente);

        System.out.println(
                "Utente loggato: " + utente
        );

        apriHomePerRuolo(utente);
    }

    private void apriHomePerRuolo(Utente utente) {

        try {

            String percorsoFXML = "";

            if (utente.getRuolo() == RuoloUtente.CAMERIERE) {

                percorsoFXML =
                        "view/HomeCameriereView.fxml";

            } else if (utente.getRuolo()
                    == RuoloUtente.CUOCO) {

                percorsoFXML =
                        "view/HomeCuocoView.fxml";

            } else if (utente.getRuolo()
                    == RuoloUtente.MANAGER) {

                percorsoFXML =
                        "view/HomeManagerView.fxml";
            }

            File fileFXML =
                    new File(percorsoFXML);

            Parent root =
                    FXMLLoader.load(
                            fileFXML.toURI().toURL()
                    );

            Stage stage =
                    (Stage) usernameField
                            .getScene()
                            .getWindow();

            Scene scene =
                    new Scene(root);

            stage.setScene(scene);

            stage.setTitle(
                    "ARS Ristorante - "
                            + utente.getRuolo()
            );

        } catch (Exception e) {

            System.out.println(
                    "Errore apertura home:"
            );

            e.printStackTrace();
        }
    }
}