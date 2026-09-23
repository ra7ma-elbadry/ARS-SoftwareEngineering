package controller;

import java.io.File;

import application.SessioneUtente;
import facade.GestioneMagazzinoFacade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.DettaglioOrdineFornitore;
import model.Fornitore;
import model.OrdineFornitoreVisualizzato;
import model.ProdottoMagazzino;

public class CreaOrdineFornitoreController {

    private Fornitore fornitoreSelezionato;

    private GestioneMagazzinoFacade facade =
            new GestioneMagazzinoFacade();

    private ObservableList<DettaglioOrdineFornitore> dettagliOrdine =
            FXCollections.observableArrayList();

    @FXML
    private Label fornitoreLabel;

    @FXML
    private ComboBox<ProdottoMagazzino> prodottoComboBox;

    @FXML
    private TextField quantitaField;

    @FXML
    private TextField prezzoUnitarioField;

    @FXML
    private TableView<DettaglioOrdineFornitore> tabellaDettagli;

    @FXML
    private TableColumn<DettaglioOrdineFornitore, Integer> colProdotto;

    @FXML
    private TableColumn<DettaglioOrdineFornitore, Double> colQuantita;

    @FXML
    private TableColumn<DettaglioOrdineFornitore, Double> colPrezzoUnitario;

    @FXML
    private TableView<OrdineFornitoreVisualizzato> tabellaOrdiniInviati;

    @FXML
    private TableColumn<OrdineFornitoreVisualizzato, Integer> colIdOrdineInviato;

    @FXML
    private TableColumn<OrdineFornitoreVisualizzato, String> colDataOrdineInviato;

    @FXML
    private TableColumn<OrdineFornitoreVisualizzato, String> colStatoOrdineInviato;

    @FXML
    private TableColumn<OrdineFornitoreVisualizzato, String> colProdottoOrdineInviato;

    @FXML
    private TableColumn<OrdineFornitoreVisualizzato, Double> colQuantitaOrdineInviato;

    @FXML
    private TableColumn<OrdineFornitoreVisualizzato, Double> colPrezzoOrdineInviato;

    @FXML
    public void initialize() {

        prodottoComboBox.setItems(
                FXCollections.observableArrayList(
                        facade.getTuttiProdotti()
                )
        );

        colProdotto.setCellValueFactory(
                new PropertyValueFactory<>("idProdotto")
        );

        colQuantita.setCellValueFactory(
                new PropertyValueFactory<>("quantita")
        );

        colPrezzoUnitario.setCellValueFactory(
                new PropertyValueFactory<>("prezzoUnitario")
        );

        tabellaDettagli.setItems(dettagliOrdine);

        inizializzaTabellaOrdiniInviati();
    }

    public void setFornitoreSelezionato(Fornitore fornitore) {

        this.fornitoreSelezionato = fornitore;

        if (fornitoreLabel != null && fornitore != null) {
            fornitoreLabel.setText(
                    "Fornitore: " + fornitore.getNome()
            );
        }

        caricaOrdiniInviati();
    }

    private void inizializzaTabellaOrdiniInviati() {

        if (tabellaOrdiniInviati == null) {
            return;
        }

        colIdOrdineInviato.setCellValueFactory(
                new PropertyValueFactory<>("idOrdine")
        );

        colDataOrdineInviato.setCellValueFactory(
                new PropertyValueFactory<>("dataOrdine")
        );

        colStatoOrdineInviato.setCellValueFactory(
                new PropertyValueFactory<>("stato")
        );

        colProdottoOrdineInviato.setCellValueFactory(
                new PropertyValueFactory<>("nomeProdotto")
        );

        colQuantitaOrdineInviato.setCellValueFactory(
                new PropertyValueFactory<>("quantita")
        );

        colPrezzoOrdineInviato.setCellValueFactory(
                new PropertyValueFactory<>("prezzoUnitario")
        );
    }

    @FXML
    private void aggiungiDettaglio() {

        ProdottoMagazzino prodotto =
                prodottoComboBox
                        .getSelectionModel()
                        .getSelectedItem();

        if (prodotto == null) {
            mostraMessaggio(
                    "Attenzione",
                    "Seleziona un prodotto."
            );
            return;
        }

        try {

            double quantita =
                    Double.parseDouble(
                            quantitaField.getText()
                    );

            double prezzoUnitario =
                    Double.parseDouble(
                            prezzoUnitarioField.getText()
                    );

            if (quantita <= 0 || prezzoUnitario <= 0) {
                mostraMessaggio(
                        "Errore",
                        "Quantità e prezzo devono essere maggiori di zero."
                );
                return;
            }

            DettaglioOrdineFornitore dettaglio =
                    new DettaglioOrdineFornitore();

            dettaglio.setIdProdotto(
                    prodotto.getId()
            );

            dettaglio.setQuantita(
                    quantita
            );

            dettaglio.setPrezzoUnitario(
                    prezzoUnitario
            );

            dettagliOrdine.add(
                    dettaglio
            );

            quantitaField.clear();
            prezzoUnitarioField.clear();

            prodottoComboBox
                    .getSelectionModel()
                    .clearSelection();

        } catch (NumberFormatException e) {

            mostraMessaggio(
                    "Errore",
                    "Inserisci valori numerici validi per quantità e prezzo."
            );
        }
    }

    @FXML
    private void inviaOrdine() {

        if (fornitoreSelezionato == null) {

            mostraMessaggio(
                    "Errore",
                    "Nessun fornitore selezionato."
            );

            return;
        }

        if (dettagliOrdine.isEmpty()) {

            mostraMessaggio(
                    "Attenzione",
                    "Aggiungi almeno un prodotto all'ordine."
            );

            return;
        }

        int idManager =
                SessioneUtente
                        .getInstance()
                        .getIdUtente();

        if (idManager == -1) {

            mostraMessaggio(
                    "Errore",
                    "Nessun utente autenticato."
            );

            return;
        }

        int idOrdineCreato =
                facade.creaOrdineFornitore(
                        fornitoreSelezionato.getId(),
                        idManager,
                        dettagliOrdine
                );

        if (idOrdineCreato == -1) {

            mostraMessaggio(
                    "Errore",
                    "Errore durante la creazione dell'ordine."
            );

            return;
        }

        mostraMessaggio(
                "Ordine inviato",
                "Ordine inviato correttamente al fornitore."
        );

        dettagliOrdine.clear();

        caricaOrdiniInviati();
    }

    private void caricaOrdiniInviati() {

        if (tabellaOrdiniInviati == null
                || fornitoreSelezionato == null) {
            return;
        }

        ObservableList<OrdineFornitoreVisualizzato> ordini =
                FXCollections.observableArrayList(
                        facade
                                .getOrdiniVisualizzatiPerFornitore(
                                        fornitoreSelezionato.getId()
                                )
                );

        tabellaOrdiniInviati.setItems(
                ordini
        );
    }

    @FXML
    private void tornaGestioneMagazzino() {

        try {

            File fileFXML =
                    new File(
                            "view/GestioneMagazzinoView.fxml"
                    );

            Parent root =
                    FXMLLoader.load(
                            fileFXML.toURI().toURL()
                    );

            Stage stage =
                    (Stage) tabellaDettagli
                            .getScene()
                            .getWindow();

            Scene scene =
                    new Scene(root);

            stage.setScene(scene);

            stage.setTitle(
                    "ARS Ristorante - Gestione Magazzino"
            );

        } catch (Exception e) {

            System.out.println(
                    "Errore ritorno Gestione Magazzino"
            );

            e.printStackTrace();
        }
    }

    private void mostraMessaggio(
            String titolo,
            String messaggio) {

        Alert alert =
                new Alert(
                        Alert.AlertType.INFORMATION
                );

        alert.setTitle(
                titolo
        );

        alert.setHeaderText(
                null
        );

        alert.setContentText(
                messaggio
        );

        alert.showAndWait();
    }
}