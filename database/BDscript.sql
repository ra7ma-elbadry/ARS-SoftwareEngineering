PRAGMA foreign_keys = ON;

DROP TABLE IF EXISTS DettaglioOrdineFornitore;
DROP TABLE IF EXISTS OrdineFornitore;
DROP TABLE IF EXISTS PrenotazioneEvento;
DROP TABLE IF EXISTS DettaglioOrdinazione;
DROP TABLE IF EXISTS Ordinazione;
DROP TABLE IF EXISTS SalaEvento;
DROP TABLE IF EXISTS Fornitore;
DROP TABLE IF EXISTS ProdottoMagazzino;
DROP TABLE IF EXISTS Menu;
DROP TABLE IF EXISTS Tavolo;
DROP TABLE IF EXISTS Cliente;
DROP TABLE IF EXISTS Utente;

CREATE TABLE Utente (
    id_utente INTEGER PRIMARY KEY AUTOINCREMENT,
    nome TEXT NOT NULL,
    cognome TEXT NOT NULL,
    username TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL,
    ruolo TEXT NOT NULL CHECK (ruolo IN ('CAMERIERE', 'CUOCO', 'MANAGER'))
);

CREATE TABLE Cliente (
    id_cliente INTEGER PRIMARY KEY AUTOINCREMENT,
    nome TEXT NOT NULL,
    cognome TEXT NOT NULL,
    telefono TEXT NOT NULL,
    email TEXT
);

CREATE TABLE Tavolo (
    id_tavolo INTEGER PRIMARY KEY AUTOINCREMENT,
    numero INTEGER NOT NULL UNIQUE,
    posti INTEGER NOT NULL CHECK (posti > 0),
    stato TEXT NOT NULL CHECK (stato IN ('LIBERO', 'OCCUPATO', 'PRENOTATO'))
);

CREATE TABLE Menu (
    id_menu INTEGER PRIMARY KEY AUTOINCREMENT,
    nome TEXT NOT NULL,
    descrizione TEXT,
    prezzo REAL NOT NULL CHECK (prezzo >= 0),
    categoria TEXT NOT NULL CHECK (categoria IN ('ANTIPASTO', 'PRIMO', 'SECONDO', 'DOLCE', 'BEVANDA')),
    disponibile INTEGER NOT NULL CHECK (disponibile IN (0, 1))
);

CREATE TABLE Ordinazione (
    id_ordinazione INTEGER PRIMARY KEY AUTOINCREMENT,
    id_tavolo INTEGER NOT NULL,
    id_cameriere INTEGER NOT NULL,
    data_ora TEXT NOT NULL,
    stato TEXT NOT NULL CHECK (stato IN ('IN_ATTESA', 'IN_PREPARAZIONE', 'PRONTO', 'CONSEGNATO', 'CHIUSO')),
    totale REAL NOT NULL CHECK (totale >= 0),
    FOREIGN KEY (id_tavolo) REFERENCES Tavolo(id_tavolo),
    FOREIGN KEY (id_cameriere) REFERENCES Utente(id_utente)
);

CREATE TABLE DettaglioOrdinazione (
    id_dettaglio INTEGER PRIMARY KEY AUTOINCREMENT,
    id_ordinazione INTEGER NOT NULL,
    id_menu INTEGER NOT NULL,
    quantita INTEGER NOT NULL CHECK (quantita > 0),
    note TEXT,
    prezzo_unitario REAL NOT NULL CHECK (prezzo_unitario >= 0),
    FOREIGN KEY (id_ordinazione) REFERENCES Ordinazione(id_ordinazione),
    FOREIGN KEY (id_menu) REFERENCES Menu(id_menu)
);

CREATE TABLE ProdottoMagazzino (
    id_prodotto INTEGER PRIMARY KEY AUTOINCREMENT,
    nome TEXT NOT NULL,
    descrizione TEXT,
    quantita REAL NOT NULL CHECK (quantita >= 0),
    unita_misura TEXT NOT NULL,
    soglia_minima REAL NOT NULL CHECK (soglia_minima >= 0)
);

CREATE TABLE Fornitore (
    id_fornitore INTEGER PRIMARY KEY AUTOINCREMENT,
    nome TEXT NOT NULL,
    telefono TEXT,
    email TEXT,
    indirizzo TEXT
);

CREATE TABLE OrdineFornitore (
    id_ordine_fornitore INTEGER PRIMARY KEY AUTOINCREMENT,
    id_fornitore INTEGER NOT NULL,
    id_manager INTEGER NOT NULL,
    data_ordine TEXT NOT NULL,
    stato TEXT NOT NULL CHECK (stato IN ('CREATO', 'INVIATO', 'CONSEGNATO', 'ANNULLATO')),
    FOREIGN KEY (id_fornitore) REFERENCES Fornitore(id_fornitore),
    FOREIGN KEY (id_manager) REFERENCES Utente(id_utente)
);

CREATE TABLE DettaglioOrdineFornitore (
    id_dettaglio_ordine INTEGER PRIMARY KEY AUTOINCREMENT,
    id_ordine_fornitore INTEGER NOT NULL,
    id_prodotto INTEGER NOT NULL,
    quantita REAL NOT NULL CHECK (quantita > 0),
    prezzo_unitario REAL NOT NULL CHECK (prezzo_unitario >= 0),
    FOREIGN KEY (id_ordine_fornitore) REFERENCES OrdineFornitore(id_ordine_fornitore),
    FOREIGN KEY (id_prodotto) REFERENCES ProdottoMagazzino(id_prodotto)
);

CREATE TABLE SalaEvento (
    id_sala INTEGER PRIMARY KEY AUTOINCREMENT,
    nome TEXT NOT NULL,
    capienza INTEGER NOT NULL CHECK (capienza > 0),
    descrizione TEXT,
    disponibile INTEGER NOT NULL CHECK (disponibile IN (0, 1))
);

CREATE TABLE PrenotazioneEvento (
    id_prenotazione INTEGER PRIMARY KEY AUTOINCREMENT,
    id_cliente INTEGER NOT NULL,
    id_sala INTEGER NOT NULL,
    id_manager INTEGER NOT NULL,
    data_evento TEXT NOT NULL,
    ora_inizio TEXT NOT NULL,
    ora_fine TEXT NOT NULL,
    numero_partecipanti INTEGER NOT NULL CHECK (numero_partecipanti > 0),
    stato TEXT NOT NULL CHECK (stato IN ('CONFERMATA', 'MODIFICATA', 'ANNULLATA')),
    note TEXT,
    FOREIGN KEY (id_cliente) REFERENCES Cliente(id_cliente),
    FOREIGN KEY (id_sala) REFERENCES SalaEvento(id_sala),
    FOREIGN KEY (id_manager) REFERENCES Utente(id_utente)
);

INSERT INTO Utente (nome, cognome, username, password, ruolo) VALUES
('Mario', 'Rossi', 'mrossi', '1234', 'CAMERIERE'),
('Luca', 'Bianchi', 'lbianchi', '1234', 'CUOCO'),
('Anna', 'Verdi', 'averdi', '1234', 'MANAGER');

INSERT INTO Cliente (nome, cognome, telefono, email) VALUES
('Giulia', 'Neri', '3331112222', 'giulia.neri@email.com'),
('Marco', 'Galli', '3334445555', 'marco.galli@email.com');

INSERT INTO Tavolo (numero, posti, stato) VALUES
(1, 2, 'LIBERO'),
(2, 4, 'LIBERO'),
(3, 6, 'OCCUPATO'),
(4, 4, 'LIBERO'),
(5, 8, 'PRENOTATO');

INSERT INTO Menu (nome, descrizione, prezzo, categoria, disponibile) VALUES
('Bruschetta al pomodoro', 'Pane tostato con pomodoro fresco, aglio e basilico', 5.00, 'ANTIPASTO', 1),
('Tagliere di salumi', 'Selezione di salumi misti', 12.00, 'ANTIPASTO', 1),
('Caprese', 'Mozzarella, pomodoro e basilico', 8.00, 'ANTIPASTO', 1),
('Verdure grigliate', 'Verdure miste alla griglia', 7.00, 'ANTIPASTO', 1),

('Spaghetti al pomodoro', 'Pasta con salsa di pomodoro e basilico', 9.00, 'PRIMO', 1),
('Penne all arrabbiata', 'Pasta con pomodoro, aglio e peperoncino', 9.50, 'PRIMO', 1),
('Risotto ai funghi', 'Risotto cremoso con funghi', 12.00, 'PRIMO', 1),
('Lasagne al forno', 'Lasagne con ragù e besciamella', 11.00, 'PRIMO', 1),

('Cotoletta con patatine', 'Cotoletta servita con patatine fritte', 13.00, 'SECONDO', 1),
('Tagliata di manzo', 'Tagliata di manzo con rucola e grana', 18.00, 'SECONDO', 1),
('Filetto di salmone', 'Salmone alla griglia con contorno', 17.00, 'SECONDO', 1),
('Pollo alla griglia', 'Petto di pollo grigliato con verdure', 12.00, 'SECONDO', 1),

('Tiramisù', 'Dolce al caffè con mascarpone', 6.00, 'DOLCE', 1),
('Panna cotta', 'Panna cotta con salsa ai frutti di bosco', 5.50, 'DOLCE', 1),
('Cheesecake', 'Cheesecake con topping a scelta', 6.50, 'DOLCE', 1),
('Gelato misto', 'Coppa di gelato con gusti misti', 5.00, 'DOLCE', 1),

('Acqua naturale', 'Bottiglia da 1 litro', 2.00, 'BEVANDA', 1),
('Acqua frizzante', 'Bottiglia da 1 litro', 2.00, 'BEVANDA', 1),
('Coca Cola', 'Lattina 33 cl', 3.00, 'BEVANDA', 1),
('Caffè', 'Espresso', 1.50, 'BEVANDA', 1);

INSERT INTO Ordinazione (id_tavolo, id_cameriere, data_ora, stato, totale) VALUES
(3, 1, '2026-06-30 20:15', 'IN_ATTESA', 26.00);

INSERT INTO DettaglioOrdinazione (id_ordinazione, id_menu, quantita, note, prezzo_unitario) VALUES
(1, 5, 2, 'Senza formaggio', 9.00),
(1, 17, 2, NULL, 2.00),
(1, 13, 1, NULL, 6.00);

INSERT INTO ProdottoMagazzino (nome, descrizione, quantita, unita_misura, soglia_minima) VALUES
('Pasta', 'Pasta secca', 20, 'kg', 5),
('Pomodoro', 'Passata di pomodoro', 15, 'litri', 3),
('Farina', 'Farina tipo 00', 10, 'kg', 2),
('Acqua', 'Bottiglie di acqua naturale', 100, 'pezzi', 20),
('Mozzarella', 'Mozzarella fresca', 8, 'kg', 2),
('Carne di manzo', 'Carne per secondi piatti', 12, 'kg', 3);

INSERT INTO Fornitore (nome, telefono, email, indirizzo) VALUES
('Forniture Alimentari SRL', '0382123456', 'info@forniturealimentari.it', 'Via Roma 10, Pavia'),
('Bevande Italia', '0382654321', 'ordini@bevandeitalia.it', 'Via Milano 20, Pavia');

INSERT INTO OrdineFornitore (id_fornitore, id_manager, data_ordine, stato) VALUES
(1, 3, '2026-06-30', 'CREATO');

INSERT INTO DettaglioOrdineFornitore (id_ordine_fornitore, id_prodotto, quantita, prezzo_unitario) VALUES
(1, 1, 10, 1.50),
(1, 2, 8, 2.00);

INSERT INTO SalaEvento (nome, capienza, descrizione, disponibile) VALUES
('Sala Rubino', 50, 'Sala per feste private e compleanni', 1),
('Sala Diamante', 100, 'Sala grande per cerimonie ed eventi', 1);

INSERT INTO PrenotazioneEvento (
    id_cliente,
    id_sala,
    id_manager,
    data_evento,
    ora_inizio,
    ora_fine,
    numero_partecipanti,
    stato,
    note
) VALUES
(1, 1, 3, '2026-07-15', '19:00', '23:00', 35, 'CONFERMATA', 'Compleanno');
