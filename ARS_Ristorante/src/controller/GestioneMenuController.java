package controller;

import java.io.File;

import dao.MenuDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Menu;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import model.CategoriaMenu;

public class GestioneMenuController {

    @FXML
    private TableView<Menu> menuTable;

    @FXML
    private TableColumn<Menu, String> nomeColumn;

    @FXML
    private TableColumn<Menu, String> categoriaColumn;

    @FXML
    private TableColumn<Menu, Double> prezzoColumn;

    @FXML
    private TableColumn<Menu, Boolean> disponibileColumn;

    @FXML
    private Button indietroButton;
    
    @FXML
    private TextField nomeField;

    @FXML
    private TextField descrizioneField;

    @FXML
    private TextField prezzoField;

    @FXML
    private ComboBox<CategoriaMenu> categoriaComboBox;

    @FXML
    private Button aggiungiButton;

    @FXML
    private Button disponibileButton;

    @FXML
    private Button nonDisponibileButton;

    private MenuDAO menuDAO = new MenuDAO();

    @FXML
    private void initialize() {
        nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
        categoriaColumn.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        prezzoColumn.setCellValueFactory(new PropertyValueFactory<>("prezzo"));
        disponibileColumn.setCellValueFactory(new PropertyValueFactory<>("disponibile"));

        categoriaComboBox.getItems().addAll(CategoriaMenu.values());

        caricaMenu();
    }

    private void caricaMenu() {
        ObservableList<Menu> lista = FXCollections.observableArrayList(menuDAO.getAllMenu());
        menuTable.setItems(lista);
    }
    
    @FXML
    private void aggiungiPiatto() {
        String nome = nomeField.getText();
        String descrizione = descrizioneField.getText();
        String prezzoTesto = prezzoField.getText();
        CategoriaMenu categoria = categoriaComboBox.getSelectionModel().getSelectedItem();

        if (nome.isEmpty() || descrizione.isEmpty() || prezzoTesto.isEmpty() || categoria == null) {
            System.out.println("Compila tutti i campi per aggiungere un piatto.");
            return;
        }

        double prezzo;

        try {
            prezzo = Double.parseDouble(prezzoTesto);
        } catch (NumberFormatException e) {
            System.out.println("Prezzo non valido.");
            return;
        }

        if (prezzo <= 0) {
            System.out.println("Il prezzo deve essere maggiore di 0.");
            return;
        }

        Menu nuovoPiatto = new Menu(
                0,
                nome,
                descrizione,
                prezzo,
                categoria,
                true
        );

        menuDAO.aggiungiMenu(nuovoPiatto);

        System.out.println("Piatto aggiunto al menu.");

        pulisciCampi();
        caricaMenu();
    }

    
    @FXML
    private void rendiDisponibile() {
        Menu piattoSelezionato = menuTable.getSelectionModel().getSelectedItem();

        if (piattoSelezionato == null) {
            System.out.println("Seleziona un piatto dalla tabella.");
            return;
        }

        menuDAO.aggiornaDisponibilita(piattoSelezionato.getIdMenu(), true);

        System.out.println("Piatto reso disponibile.");

        caricaMenu();
    }
    
    @FXML
    private void rendiNonDisponibile() {
        Menu piattoSelezionato = menuTable.getSelectionModel().getSelectedItem();

        if (piattoSelezionato == null) {
            System.out.println("Seleziona un piatto dalla tabella.");
            return;
        }

        menuDAO.aggiornaDisponibilita(piattoSelezionato.getIdMenu(), false);

        System.out.println("Piatto reso non disponibile.");

        caricaMenu();
    }
    
    private void pulisciCampi() {
        nomeField.clear();
        descrizioneField.clear();
        prezzoField.clear();
        categoriaComboBox.getSelectionModel().clearSelection();
    }
    
    
    @FXML
    private void tornaHome() {
        try {
            File fileFXML = new File("view/HomeManagerView.fxml");
            Parent root = FXMLLoader.load(fileFXML.toURI().toURL());

            Stage stage = (Stage) indietroButton.getScene().getWindow();
            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.setTitle("ARS Ristorante - Home Manager");

        } catch (Exception e) {
            System.out.println("Errore ritorno alla Home Manager:");
            e.printStackTrace();
        }
    }
}