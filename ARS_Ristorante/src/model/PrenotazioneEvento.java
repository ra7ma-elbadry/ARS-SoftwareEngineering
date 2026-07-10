package model;

public class PrenotazioneEvento {
    private int idPrenotazione;
    private int idCliente;
    private int idSala;
    private int idManager;

    private String dataEvento;
    private String oraInizio;
    private String oraFine;

    private int numeroPartecipanti;

    private String stato;
    public int getIdPrenotazione() {
		return idPrenotazione;
	}

	public void setIdPrenotazione(int idPrenotazione) {
		this.idPrenotazione = idPrenotazione;
	}

	public int getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}

	public int getIdSala() {
		return idSala;
	}

	public void setIdSala(int idSala) {
		this.idSala = idSala;
	}

	public int getIdManager() {
		return idManager;
	}

	public void setIdManager(int idManager) {
		this.idManager = idManager;
	}

	public String getDataEvento() {
		return dataEvento;
	}

	public void setDataEvento(String dataEvento) {
		this.dataEvento = dataEvento;
	}

	public String getOraInizio() {
		return oraInizio;
	}

	public void setOraInizio(String oraInizio) {
		this.oraInizio = oraInizio;
	}

	public String getOraFine() {
		return oraFine;
	}

	public void setOraFine(String oraFine) {
		this.oraFine = oraFine;
	}

	public int getNumeroPartecipanti() {
		return numeroPartecipanti;
	}

	public void setNumeroPartecipanti(int numeroPartecipanti) {
		this.numeroPartecipanti = numeroPartecipanti;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	private String note;
    
    public PrenotazioneEvento(int idPrenotazione, int idCliente, int idSala,
            int idManager, String dataEvento, String oraInizio,
            String oraFine, int numeroPartecipanti,
            String stato, String note) {

        this.idPrenotazione = idPrenotazione;
        this.idCliente = idCliente;
        this.idSala = idSala;
        this.idManager = idManager;
        this.dataEvento = dataEvento;
        this.oraInizio = oraInizio;
        this.oraFine = oraFine;
        this.numeroPartecipanti = numeroPartecipanti;
        this.stato = stato;
        this.note = note;
    }
    public PrenotazioneEvento() {
}
}