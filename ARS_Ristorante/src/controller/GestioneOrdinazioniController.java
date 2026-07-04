package controller;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import dao.DettaglioOrdinazioneDAO;
import dao.MenuDAO;
import dao.OrdinazioneDAO;
import dao.TavoloDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.DettaglioOrdinazione;
import model.Menu;
import model.Ordinazione;
import model.StatoOrdinazione;
import model.StatoTavolo;
import model.Tavolo;

public class GestioneOrdinazioniController {

    @FXML
    private ComboBox<Tavolo> tavoliComboBox;

    @FXML
    private TableView<Menu> menuTable;

    @FXML
    private TableColumn<Menu, String> nomeColumn;

    @FXML
    private TableColumn<Menu, String> categoriaColumn;

    @FXML
    private TableColumn<Menu, Double> prezzoColumn;

    @FXML
    private TextField quantitaField;

    @FXML
    private TextField noteField;

    @FXML
    private Button aggiungiButton;

    @FXML
    private ListView<String> ordineListView;

    @FXML
    private Label totaleLabel;

    @FXML
    private Button svuotaButton;

    @FXML
    private Button inviaButton;

    @FXML
    private Button indietroButton;
    
    @FXML
    private TableView<Ordinazione> ordiniProntiTable;

    @FXML
    private TableColumn<Ordinazione, Integer> prontoIdColumn;

    @FXML
    private TableColumn<Ordinazione, Integer> prontoTavoloColumn;

    @FXML
    private TableColumn<Ordinazione, String> prontoStatoColumn;

    @FXML
    private TableColumn<Ordinazione, Double> prontoTotaleColumn;

    @FXML
    private Button consegnatoButton;

    @FXML
    private Button chiudiContoButton;
    
    private TavoloDAO tavoloDAO = new TavoloDAO();
    private MenuDAO menuDAO = new MenuDAO();
    private OrdinazioneDAO ordinazioneDAO = new OrdinazioneDAO();
    private DettaglioOrdinazioneDAO dettaglioDAO = new DettaglioOrdinazioneDAO();

    private List<DettaglioOrdinazione> dettagliOrdine = new ArrayList<>();
    private double totale = 0.0;

    private static final int ID_CAMERIERE_TEST = 1;

    @FXML
    private void initialize() {
        nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
        categoriaColumn.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        prezzoColumn.setCellValueFactory(new PropertyValueFactory<>("prezzo"));

        prontoIdColumn.setCellValueFactory(new PropertyValueFactory<>("idOrdinazione"));
        prontoTavoloColumn.setCellValueFactory(new PropertyValueFactory<>("idTavolo"));
        prontoStatoColumn.setCellValueFactory(new PropertyValueFactory<>("stato"));
        prontoTotaleColumn.setCellValueFactory(new PropertyValueFactory<>("totale"));

        caricaTavoli();
        caricaMenu();
        caricaOrdiniPronti();
        aggiornaTotale();
    }

    private void caricaOrdiniPronti() {
        ObservableList<Ordinazione> ordiniPronti = FXCollections.observableArrayList(
                ordinazioneDAO.getOrdinazioniPronteOConsegnate()
        );

        ordiniProntiTable.setItems(ordiniPronti);
    }
    
    private void caricaTavoli() {
        ObservableList<Tavolo> tavoli = FXCollections.observableArrayList(tavoloDAO.getTavoliDisponibili());
        tavoliComboBox.setItems(tavoli);
    }

    private void caricaMenu() {
        ObservableList<Menu> menuDisponibile = FXCollections.observableArrayList(menuDAO.getMenuDisponibile());
        menuTable.setItems(menuDisponibile);
    }

    @FXML
    private void aggiungiProdotto() {
        Menu prodottoSelezionato = menuTable.getSelectionModel().getSelectedItem();

        if (prodottoSelezionato == null) {
            System.out.println("Seleziona un prodotto dal menu.");
            return;
        }

        int quantita;

        try {
            quantita = Integer.parseInt(quantitaField.getText());
        } catch (NumberFormatException e) {
            System.out.println("Inserisci una quantità valida.");
            return;
        }

        if (quantita <= 0) {
            System.out.println("La quantità deve essere maggiore di 0.");
            return;
        }

        String note = noteField.getText();

        DettaglioOrdinazione dettaglio = new DettaglioOrdinazione(
                0,
                0,
                prodottoSelezionato.getIdMenu(),
                quantita,
                note,
                prodottoSelezionato.getPrezzo()
        );

        dettagliOrdine.add(dettaglio);

        double subtotale = dettaglio.calcolaSubtotale();
        totale += subtotale;

        ordineListView.getItems().add(
                quantita + "x " + prodottoSelezionato.getNome() + " - € " + subtotale
        );

        quantitaField.clear();
        noteField.clear();

        aggiornaTotale();
    }

    @FXML
    private void svuotaOrdine() {
        dettagliOrdine.clear();
        ordineListView.getItems().clear();
        totale = 0.0;
        aggiornaTotale();
    }

    @FXML
    private void inviaInCucina() {
        Tavolo tavoloSelezionato = tavoliComboBox.getSelectionModel().getSelectedItem();

        if (tavoloSelezionato == null) {
            System.out.println("Seleziona un tavolo.");
            return;
        }

        if (dettagliOrdine.isEmpty()) {
            System.out.println("Aggiungi almeno un prodotto all'ordine.");
            return;
        }

        String dataOra = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        Ordinazione ordinazione = new Ordinazione(
                0,
                tavoloSelezionato.getIdTavolo(),
                ID_CAMERIERE_TEST,
                dataOra,
                StatoOrdinazione.IN_ATTESA,
                totale
        );

        int idOrdinazioneGenerato = ordinazioneDAO.creaOrdinazione(ordinazione);

        if (idOrdinazioneGenerato == -1) {
            System.out.println("Errore: ordinazione non salvata.");
            return;
        }

        for (DettaglioOrdinazione dettaglio : dettagliOrdine) {
            DettaglioOrdinazione dettaglioDaSalvare = new DettaglioOrdinazione(
                    0,
                    idOrdinazioneGenerato,
                    dettaglio.getIdMenu(),
                    dettaglio.getQuantita(),
                    dettaglio.getNote(),
                    dettaglio.getPrezzoUnitario()
            );

            dettaglioDAO.aggiungiDettaglio(dettaglioDaSalvare);
        }

        tavoloDAO.aggiornaStatoTavolo(tavoloSelezionato.getIdTavolo(), StatoTavolo.OCCUPATO);

        System.out.println("Ordinazione inviata in cucina con id: " + idOrdinazioneGenerato);

        svuotaOrdine();
        caricaTavoli();
        caricaOrdiniPronti();
    }

    @FXML
    private void segnaConsegnato() {
        Ordinazione ordineSelezionato = ordiniProntiTable.getSelectionModel().getSelectedItem();

        if (ordineSelezionato == null) {
            System.out.println("Seleziona un ordine pronto.");
            return;
        }

        if (ordineSelezionato.getStato() != StatoOrdinazione.PRONTO) {
            System.out.println("Puoi consegnare solo un ordine PRONTO.");
            return;
        }

        ordinazioneDAO.aggiornaStatoOrdinazione(
                ordineSelezionato.getIdOrdinazione(),
                StatoOrdinazione.CONSEGNATO
        );

        System.out.println("Ordine consegnato.");

        caricaOrdiniPronti();
    }
    
    @FXML
    private void chiudiConto() {
        Ordinazione ordineSelezionato = ordiniProntiTable.getSelectionModel().getSelectedItem();

        if (ordineSelezionato == null) {
            System.out.println("Seleziona un ordine da chiudere.");
            return;
        }

        if (ordineSelezionato.getStato() != StatoOrdinazione.CONSEGNATO) {
            System.out.println("Puoi chiudere il conto solo dopo aver consegnato l'ordine.");
            return;
        }

        ordinazioneDAO.aggiornaStatoOrdinazione(
                ordineSelezionato.getIdOrdinazione(),
                StatoOrdinazione.CHIUSO
        );

        tavoloDAO.aggiornaStatoTavolo(
                ordineSelezionato.getIdTavolo(),
                StatoTavolo.LIBERO
        );

        System.out.println("Conto chiuso. Tavolo liberato.");

        caricaOrdiniPronti();
        caricaTavoli();
    }
    
    @FXML
    private void tornaHome() {
        try {
            File fileFXML = new File("view/HomeCameriereView.fxml");
            Parent root = FXMLLoader.load(fileFXML.toURI().toURL());

            Stage stage = (Stage) indietroButton.getScene().getWindow();
            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.setTitle("ARS Ristorante - Home Cameriere");

        } catch (Exception e) {
            System.out.println("Errore ritorno alla Home Cameriere:");
            e.printStackTrace();
        }
    }

    private void aggiornaTotale() {
        totaleLabel.setText(String.format("Totale: € %.2f", totale));
    }
}