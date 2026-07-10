package controller;

import java.io.File;

import dao.PrenotazioneEventoDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.PrenotazioneEvento;

public class GestioneSalaEventiController {

	@FXML
	private Button indietroButton;
	
    @FXML
    private TableView<PrenotazioneEvento> prenotazioniTable;

    @FXML
    private TableColumn<PrenotazioneEvento, Integer> clienteColumn;

    @FXML
    private TableColumn<PrenotazioneEvento, Integer> salaColumn;

    @FXML
    private TableColumn<PrenotazioneEvento, String> dataColumn;

    @FXML
    private TableColumn<PrenotazioneEvento, Integer> partecipantiColumn;

    @FXML
    private TableColumn<PrenotazioneEvento, String> statoColumn;

    @FXML
    private TextField clienteField;

    @FXML
    private ComboBox<String> salaComboBox;

    @FXML
    private DatePicker dataPicker;

    @FXML
    private TextField partecipantiField;

    @FXML
    private Button aggiungiButton;

    @FXML
    private Button modificaButton;

    @FXML
    private Button eliminaButton;

    private PrenotazioneEventoDAO prenotazioneDAO = new PrenotazioneEventoDAO();

    @FXML
    public void initialize() {
        clienteColumn.setCellValueFactory(new PropertyValueFactory<>("idCliente"));
        salaColumn.setCellValueFactory(new PropertyValueFactory<>("idSala"));
        dataColumn.setCellValueFactory(new PropertyValueFactory<>("dataEvento"));
        partecipantiColumn.setCellValueFactory(new PropertyValueFactory<>("numeroPartecipanti"));
        statoColumn.setCellValueFactory(new PropertyValueFactory<>("stato"));

        salaComboBox.getItems().addAll("1", "2");

        caricaPrenotazioni();
        prenotazioniTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, nuovaPrenotazione) -> {
                    if (nuovaPrenotazione != null) {
                        clienteField.setText(String.valueOf(nuovaPrenotazione.getIdCliente()));
                        salaComboBox.setValue(String.valueOf(nuovaPrenotazione.getIdSala()));
                        dataPicker.setValue(java.time.LocalDate.parse(nuovaPrenotazione.getDataEvento()));
                        partecipantiField.setText(String.valueOf(nuovaPrenotazione.getNumeroPartecipanti()));
                    }
                }
        );
    }

    private void caricaPrenotazioni() {
        ObservableList<PrenotazioneEvento> lista =
                FXCollections.observableArrayList(prenotazioneDAO.getTuttePrenotazioni());

        prenotazioniTable.setItems(lista);
    }

    @FXML
    private void aggiungiPrenotazione() {
    	if (clienteField.getText().isEmpty()
    	        || salaComboBox.getValue() == null
    	        || dataPicker.getValue() == null
    	        || partecipantiField.getText().isEmpty()) {

    	    mostraAlert("Attenzione", "Compila tutti i campi.");
    	    return;
    	}
    	
    	PrenotazioneEvento prenotazione = new PrenotazioneEvento();

    	prenotazione.setIdCliente(Integer.parseInt(clienteField.getText()));
    	prenotazione.setIdSala(Integer.parseInt(salaComboBox.getValue()));
    	prenotazione.setNumeroPartecipanti(Integer.parseInt(partecipantiField.getText()));
    	prenotazione.setDataEvento(dataPicker.getValue().toString());

    	boolean disponibile = prenotazioneDAO.salaDisponibile(
    	        prenotazione.getIdSala(),
    	        prenotazione.getDataEvento(),
    	        "19:00",
    	        "22:00"
    	);

    	if (!disponibile) {
    		mostraAlert("Prenotazione", "La sala è già prenotata in questa data.");
    	  return;
    	}

        prenotazione.setIdManager(3);
        prenotazione.setOraInizio("19:00");
        prenotazione.setOraFine("23:00");
        prenotazione.setStato("CONFERMATA");
        prenotazione.setNote("");

        prenotazioneDAO.salvaPrenotazione(prenotazione);

        System.out.println("Prenotazione evento salvata.");

        caricaPrenotazioni();
    }

    @FXML
    private void modificaPrenotazione() {
    	PrenotazioneEvento selezionata =
    	        prenotazioniTable.getSelectionModel().getSelectedItem();
    	if (selezionata == null) {
    	    mostraAlert("Attenzione", "Seleziona una prenotazione.");
    	    return;
    	}

    	if (selezionata != null) {
    	    selezionata.setIdCliente(Integer.parseInt(clienteField.getText()));
    	    selezionata.setIdSala(Integer.parseInt(salaComboBox.getValue()));
    	    selezionata.setDataEvento(dataPicker.getValue().toString());
    	    selezionata.setNumeroPartecipanti(Integer.parseInt(partecipantiField.getText()));

    	    prenotazioneDAO.modificaPrenotazione(selezionata);

    	    caricaPrenotazioni();



    	}

    }

    @FXML
    private void eliminaPrenotazione() {
    	PrenotazioneEvento selezionata =
    	        prenotazioniTable.getSelectionModel().getSelectedItem();

    	if (selezionata != null) {
    	    prenotazioneDAO.eliminaPrenotazione(selezionata.getIdPrenotazione());
    	    caricaPrenotazioni();
    	    System.out.println("Prenotazione evento eliminata.");
    	}
    }
    @FXML
    private void tornaHome() {
        try {
            File fileFXML = new File("view/HomeManagerView.fxml");
            Parent root = FXMLLoader.load(fileFXML.toURI().toURL());

            Stage stage = (Stage) indietroButton.getScene().getWindow();
            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.setTitle("ARS Ristorante - MANAGER");
        } catch (Exception e) {
            System.out.println("Errore ritorno Home Manager:");
            e.printStackTrace();
        }
    }
        private void mostraAlert(String titolo, String messaggio) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titolo);
        alert.setHeaderText(null);
        alert.setContentText(messaggio);
        alert.showAndWait();
    }
    }
    