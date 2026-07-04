/*package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import database.DatabaseConnection;
import java.sql.Connection;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        Label label = new Label("Sistema Gestione Ristorante ARS");

        VBox root = new VBox(label);

        Scene scene = new Scene(root, 1000, 800);

        primaryStage.setTitle("ARS Ristorante");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
    	
    	try {
    		Connection conn= DatabaseConnection.getConnection();
    		System.out.println("Connessione al database riuscita!");
    		conn.close();
    		
    		
    		
    		dao.MenuDAO menuDAO =new dao.MenuDAO();
    		for(model.Menu menu : menuDAO.getAllMenu()) {
    			System.out.println(menu);
    		}
    		
    		
    		
    		dao.UtenteDAO utenteDAO = new dao.UtenteDAO();
    		model.Utente utente=utenteDAO.login("averdi", "1234");
    		if(utente!=null) {
    			System.out.println("Login riuscito: " + utente);
    		} else {
    			System.out.println("Login fallito");
    		}
    		
    		
    		dao.TavoloDAO tavoloDAO = new dao.TavoloDAO();
    		System.out.println("Tavoli disponibili:");
    		for (model.Tavolo tavolo : tavoloDAO.getTavoliDisponibili()) {
    		    System.out.println(tavolo);
    		}
    		
    		
    		dao.OrdinazioneDAO ordinazioneDAO = new dao.OrdinazioneDAO();
    		System.out.println("Ordinazioni per cucina:");
    		for (model.Ordinazione ordinazione : ordinazioneDAO.getOrdinazioniPerCucina()) {
    		    System.out.println(ordinazione);
    		}
    		
    		
    		
    		dao.DettaglioOrdinazioneDAO dettaglioDAO = new dao.DettaglioOrdinazioneDAO();
    		System.out.println("Dettagli ordinazione #1:");
    		for (model.DettaglioOrdinazione dettaglio : dettaglioDAO.getDettagliByOrdinazione(1)) {
    		    System.out.println(dettaglio);
    		}
    		
    		
    		
    	} catch (Exception e) {
    		System.out.println("Errore di connessione al database: ");
    		e.printStackTrace();
    	}
    	
        launch(args);
    }
}
*/



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