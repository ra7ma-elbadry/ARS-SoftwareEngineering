package controller;

import java.io.File;
import java.util.List;

import dao.DettaglioOrdinazioneDAO;
import dao.OrdinazioneDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Ordinazione;
import model.StatoOrdinazione;

public class OrdiniCucinaController {

    @FXML
    private TableView<Ordinazione> ordiniTable;

    @FXML
    private TableColumn<Ordinazione, Integer> idColumn;

    @FXML
    private TableColumn<Ordinazione, Integer> tavoloColumn;

    @FXML
    private TableColumn<Ordinazione, String> statoColumn;

    @FXML
    private TableColumn<Ordinazione, Double> totaleColumn;

    @FXML
    private ListView<String> dettagliListView;

    @FXML
    private Button preparazioneButton;

    @FXML
    private Button prontoButton;

    @FXML
    private Button indietroButton;

    private OrdinazioneDAO ordinazioneDAO = new OrdinazioneDAO();
    private DettaglioOrdinazioneDAO dettaglioDAO = new DettaglioOrdinazioneDAO();

    @FXML
    private void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("idOrdinazione"));
        tavoloColumn.setCellValueFactory(new PropertyValueFactory<>("idTavolo"));
        statoColumn.setCellValueFactory(new PropertyValueFactory<>("stato"));
        totaleColumn.setCellValueFactory(new PropertyValueFactory<>("totale"));

        caricaOrdini();

        ordiniTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, vecchioOrdine, nuovoOrdine) -> {
                    if (nuovoOrdine != null) {
                        caricaDettagliOrdine(nuovoOrdine);
                    }
                }
        );
    }

    private void caricaOrdini() {
        ObservableList<Ordinazione> ordini = FXCollections.observableArrayList(
                ordinazioneDAO.getOrdinazioniPerCucina()
        );

        ordiniTable.setItems(ordini);
    }

    private void caricaDettagliOrdine(Ordinazione ordinazione) {
        dettagliListView.getItems().clear();

        List<String> dettagli = dettaglioDAO.getDettagliConNomeProdotto(
                ordinazione.getIdOrdinazione()
        );

        dettagliListView.getItems().addAll(dettagli);
    }

    @FXML
    private void mettiInPreparazione() {
        Ordinazione ordineSelezionato = ordiniTable.getSelectionModel().getSelectedItem();

        if (ordineSelezionato == null) {
            System.out.println("Seleziona un ordine.");
            return;
        }

        ordinazioneDAO.aggiornaStatoOrdinazione(
                ordineSelezionato.getIdOrdinazione(),
                StatoOrdinazione.IN_PREPARAZIONE
        );

        System.out.println("Ordine messo in preparazione.");

        caricaOrdini();
        dettagliListView.getItems().clear();
    }

    @FXML
    private void mettiPronto() {
        Ordinazione ordineSelezionato = ordiniTable.getSelectionModel().getSelectedItem();

        if (ordineSelezionato == null) {
            System.out.println("Seleziona un ordine.");
            return;
        }

        ordinazioneDAO.aggiornaStatoOrdinazione(
                ordineSelezionato.getIdOrdinazione(),
                StatoOrdinazione.PRONTO
        );

        System.out.println("Ordine pronto.");

        caricaOrdini();
        dettagliListView.getItems().clear();
    }

    @FXML
    private void tornaHome() {
        try {
            File fileFXML = new File("view/HomeCuocoView.fxml");
            Parent root = FXMLLoader.load(fileFXML.toURI().toURL());

            Stage stage = (Stage) indietroButton.getScene().getWindow();
            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.setTitle("ARS Ristorante - Home Cuoco");

        } catch (Exception e) {
            System.out.println("Errore ritorno alla Home Cuoco:");
            e.printStackTrace();
        }
    }
}