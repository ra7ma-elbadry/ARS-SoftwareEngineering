package controller;

import java.io.File;

import dao.FornitoreDAO;
import dao.ProdottoMagazzinoDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Fornitore;
import model.ProdottoMagazzino;

public class GestioneMagazzinoController {

    private ProdottoMagazzinoDAO prodottoDAO = new ProdottoMagazzinoDAO();
    private FornitoreDAO fornitoreDAO = new FornitoreDAO();
    
    @FXML
    private Button indietroButton;

    @FXML
    private TableView<ProdottoMagazzino> tabellaProdotti;

    @FXML
    private TableColumn<ProdottoMagazzino, Integer> colIdProdotto;

    @FXML
    private TableColumn<ProdottoMagazzino, String> colNomeProdotto;

    @FXML
    private TableColumn<ProdottoMagazzino, String> colDescrizioneProdotto;

    @FXML
    private TableColumn<ProdottoMagazzino, Double> colQuantitaProdotto;

    @FXML
    private TableColumn<ProdottoMagazzino, String> colUnitaMisuraProdotto;

    @FXML
    private TableColumn<ProdottoMagazzino, Double> colSogliaMinimaProdotto;

    @FXML
    private TextField nomeProdottoField;

    @FXML
    private TextField descrizioneProdottoField;

    @FXML
    private TextField quantitaProdottoField;

    @FXML
    private TextField unitaMisuraProdottoField;

    @FXML
    private TextField sogliaMinimaProdottoField;

    @FXML
    private TableView<Fornitore> tabellaFornitori;

    @FXML
    private TableColumn<Fornitore, Integer> colIdFornitore;

    @FXML
    private TableColumn<Fornitore, String> colNomeFornitore;

    @FXML
    private TableColumn<Fornitore, String> colTelefonoFornitore;

    @FXML
    private TableColumn<Fornitore, String> colEmailFornitore;

    @FXML
    private TableColumn<Fornitore, String> colIndirizzoFornitore;

    @FXML
    private TextField nomeFornitoreField;

    @FXML
    private TextField telefonoFornitoreField;

    @FXML
    private TextField emailFornitoreField;

    @FXML
    private TextField indirizzoFornitoreField;

    @FXML
    public void initialize() {
        inizializzaTabellaProdotti();
        inizializzaTabellaFornitori();

        caricaProdotti();
        caricaFornitori();

        tabellaProdotti.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, prodottoSelezionato) -> mostraProdottoSelezionato(prodottoSelezionato)
        );

        tabellaFornitori.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, fornitoreSelezionato) -> mostraFornitoreSelezionato(fornitoreSelezionato)
        );
    }

    private void inizializzaTabellaProdotti() {
        colIdProdotto.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNomeProdotto.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colDescrizioneProdotto.setCellValueFactory(new PropertyValueFactory<>("descrizione"));
        colQuantitaProdotto.setCellValueFactory(new PropertyValueFactory<>("quantita"));
        colUnitaMisuraProdotto.setCellValueFactory(new PropertyValueFactory<>("unitaMisura"));
        colSogliaMinimaProdotto.setCellValueFactory(new PropertyValueFactory<>("sogliaMinima"));
    }

    private void inizializzaTabellaFornitori() {
        colIdFornitore.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNomeFornitore.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colTelefonoFornitore.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colEmailFornitore.setCellValueFactory(new PropertyValueFactory<>("email"));
        colIndirizzoFornitore.setCellValueFactory(new PropertyValueFactory<>("indirizzo"));
    }

    private void caricaProdotti() {
        ObservableList<ProdottoMagazzino> prodotti =
                FXCollections.observableArrayList(prodottoDAO.getTuttiProdotti());

        tabellaProdotti.setItems(prodotti);
    }

    private void caricaFornitori() {
        ObservableList<Fornitore> fornitori =
                FXCollections.observableArrayList(fornitoreDAO.getTuttiFornitori());

        tabellaFornitori.setItems(fornitori);
    }

    private void mostraProdottoSelezionato(ProdottoMagazzino prodotto) {
        if (prodotto != null) {
            nomeProdottoField.setText(prodotto.getNome());
            descrizioneProdottoField.setText(prodotto.getDescrizione());
            quantitaProdottoField.setText(String.valueOf(prodotto.getQuantita()));
            unitaMisuraProdottoField.setText(prodotto.getUnitaMisura());
            sogliaMinimaProdottoField.setText(String.valueOf(prodotto.getSogliaMinima()));
        }
    }

    private void mostraFornitoreSelezionato(Fornitore fornitore) {
        if (fornitore != null) {
            nomeFornitoreField.setText(fornitore.getNome());
            telefonoFornitoreField.setText(fornitore.getTelefono());
            emailFornitoreField.setText(fornitore.getEmail());
            indirizzoFornitoreField.setText(fornitore.getIndirizzo());
        }
    }

    @FXML
    private void aggiungiProdotto() {
        try {
            ProdottoMagazzino prodotto = new ProdottoMagazzino();

            prodotto.setNome(nomeProdottoField.getText());
            prodotto.setDescrizione(descrizioneProdottoField.getText());
            prodotto.setQuantita(Double.parseDouble(quantitaProdottoField.getText()));
            prodotto.setUnitaMisura(unitaMisuraProdottoField.getText());
            prodotto.setSogliaMinima(Double.parseDouble(sogliaMinimaProdottoField.getText()));

            prodottoDAO.aggiungiProdotto(prodotto);
            caricaProdotti();
            pulisciCampiProdotto();

        } catch (Exception e) {
            System.out.println("Errore aggiunta prodotto");
            e.printStackTrace();
        }
    }

    @FXML
    private void aggiornaProdotto() {
        ProdottoMagazzino prodottoSelezionato = tabellaProdotti.getSelectionModel().getSelectedItem();

        if (prodottoSelezionato == null) {
            return;
        }

        try {
            prodottoSelezionato.setNome(nomeProdottoField.getText());
            prodottoSelezionato.setDescrizione(descrizioneProdottoField.getText());
            prodottoSelezionato.setQuantita(Double.parseDouble(quantitaProdottoField.getText()));
            prodottoSelezionato.setUnitaMisura(unitaMisuraProdottoField.getText());
            prodottoSelezionato.setSogliaMinima(Double.parseDouble(sogliaMinimaProdottoField.getText()));

            prodottoDAO.aggiornaProdotto(prodottoSelezionato);
            caricaProdotti();
            pulisciCampiProdotto();

        } catch (Exception e) {
            System.out.println("Errore aggiornamento prodotto");
            e.printStackTrace();
        }
    }

    @FXML
    private void eliminaProdotto() {
        ProdottoMagazzino prodottoSelezionato = tabellaProdotti.getSelectionModel().getSelectedItem();

        if (prodottoSelezionato == null) {
            return;
        }

        prodottoDAO.eliminaProdotto(prodottoSelezionato.getId());
        caricaProdotti();
        pulisciCampiProdotto();
    }

    private void pulisciCampiProdotto() {
        nomeProdottoField.clear();
        descrizioneProdottoField.clear();
        quantitaProdottoField.clear();
        unitaMisuraProdottoField.clear();
        sogliaMinimaProdottoField.clear();
    }

    @FXML
    private void aggiungiFornitore() {
        Fornitore fornitore = new Fornitore();

        fornitore.setNome(nomeFornitoreField.getText());
        fornitore.setTelefono(telefonoFornitoreField.getText());
        fornitore.setEmail(emailFornitoreField.getText());
        fornitore.setIndirizzo(indirizzoFornitoreField.getText());

        fornitoreDAO.aggiungiFornitore(fornitore);
        caricaFornitori();
        pulisciCampiFornitore();
    }

    @FXML
    private void aggiornaFornitore() {
        Fornitore fornitoreSelezionato = tabellaFornitori.getSelectionModel().getSelectedItem();

        if (fornitoreSelezionato == null) {
            return;
        }

        fornitoreSelezionato.setNome(nomeFornitoreField.getText());
        fornitoreSelezionato.setTelefono(telefonoFornitoreField.getText());
        fornitoreSelezionato.setEmail(emailFornitoreField.getText());
        fornitoreSelezionato.setIndirizzo(indirizzoFornitoreField.getText());

        fornitoreDAO.aggiornaFornitore(fornitoreSelezionato);
        caricaFornitori();
        pulisciCampiFornitore();
    }

    @FXML
    private void eliminaFornitore() {
        Fornitore fornitoreSelezionato = tabellaFornitori.getSelectionModel().getSelectedItem();

        if (fornitoreSelezionato == null) {
            return;
        }

        fornitoreDAO.eliminaFornitore(fornitoreSelezionato.getId());
        caricaFornitori();
        pulisciCampiFornitore();
    }
    @FXML
    private void creaOrdineFornitore() {
        Fornitore fornitoreSelezionato = tabellaFornitori.getSelectionModel().getSelectedItem();

        if (fornitoreSelezionato == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Attenzione");
            alert.setHeaderText(null);
            alert.setContentText("Seleziona prima un fornitore dalla tabella.");
            alert.showAndWait();
            return;
        }

        try {
            CreaOrdineFornitoreController.setFornitoreSelezionato(fornitoreSelezionato);

            File fileFXML = new File("view/CreaOrdineFornitoreView.fxml");
            Parent root = FXMLLoader.load(fileFXML.toURI().toURL());

            Stage stage = (Stage) tabellaFornitori.getScene().getWindow();
            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.setTitle("ARS Ristorante - Crea Ordine Fornitore");

        } catch (Exception e) {
            System.out.println("Errore apertura Crea Ordine Fornitore");
            e.printStackTrace();
        }
    }

    private void pulisciCampiFornitore() {
        nomeFornitoreField.clear();
        telefonoFornitoreField.clear();
        emailFornitoreField.clear();
        indirizzoFornitoreField.clear();
    }
    @FXML
    private void tornaHomeManager() {
        try {
            File fileFXML = new File("view/HomeManagerView.fxml");
            Parent root = FXMLLoader.load(fileFXML.toURI().toURL());

            Stage stage = (Stage) indietroButton.getScene().getWindow();
            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.setTitle("ARS Ristorante - Home Manager");

        } catch (Exception e) {
            System.out.println("Errore ritorno Home Manager");
            e.printStackTrace();
        }
    }
}