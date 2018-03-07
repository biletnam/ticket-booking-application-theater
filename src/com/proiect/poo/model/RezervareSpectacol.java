package com.proiect.poo.model;

import java.util.List;

public class RezervareSpectacol {
	// identificatorul reprezentatiei spectacolului pentru care se va face
	// rezervarea
	private Integer identificator;
	private String numeSpectacol;
	private String dataReprezentatie;
	private String oraReprezentatie;
	private List<LocSpectacol> locuriRezervate;
	private Integer numarBileteDorite;
	private Persoana persoana;
	private Boolean rezervareConfirmata;

	public Integer getIdentificator() {
		return identificator;
	}

	public void setIdentificator(Integer identificator) {
		this.identificator = identificator;
	}

	public String getNumeSpectacol() {
		return numeSpectacol;
	}

	public void setNumeSpectacol(String numeSpectacol) {
		this.numeSpectacol = numeSpectacol;
	}

	public String getDataReprezentatie() {
		return dataReprezentatie;
	}

	public void setDataReprezentatie(String dataReprezentatie) {
		this.dataReprezentatie = dataReprezentatie;
	}

	public String getOraReprezentatie() {
		return oraReprezentatie;
	}

	public void setOraReprezentatie(String oraReprezentatie) {
		this.oraReprezentatie = oraReprezentatie;
	}

	public List<LocSpectacol> getLocuriRezervate() {
		return locuriRezervate;
	}

	public void setLocuriRezervate(List<LocSpectacol> locuriRezervate) {
		this.locuriRezervate = locuriRezervate;
	}

	public Integer getNumarBileteDorite() {
		return numarBileteDorite;
	}

	public void setNumarBileteDorite(Integer numarBileteDorite) {
		this.numarBileteDorite = numarBileteDorite;
	}

	public Persoana getPersoana() {
		return persoana;
	}

	public void setPersoana(Persoana persoana) {
		this.persoana = persoana;
	}

	public Boolean getRezervareConfirmata() {
		return rezervareConfirmata;
	}

	public void setRezervareConfirmata(Boolean rezervareConfirmata) {
		this.rezervareConfirmata = rezervareConfirmata;
	}

	@Override
	public String toString() {
		String locuri = "";

		// parsare lista locuri rezervate
		for (LocSpectacol locSpectacol : this.locuriRezervate) {
			locuri = locuri + locSpectacol + ", ";
		}

		// va fi folosita la scrierea in fisieurl 'rezervari.txt'
		return rezervareConfirmata + ", " + this.persoana + ", " + this.numeSpectacol + ", " + this.dataReprezentatie
				+ ", " + this.oraReprezentatie + ", " + locuri;
	}
}
