package com.proiect.poo.model;

public class LocSpectacol {
	private Integer identificator;
	private String nume;
	private Boolean disponibilitate;
	private Double pret;

	public LocSpectacol() {
		super();
	}

	public LocSpectacol(Integer identificator, String nume, Boolean disponibilitate, Double pret) {
		super();
		this.identificator = identificator;
		this.nume = nume;
		this.disponibilitate = disponibilitate;
		this.pret = pret;
	}

	public Integer getIdentificator() {
		return identificator;
	}

	public void setIdentificator(Integer identificator) {
		this.identificator = identificator;
	}

	public String getNume() {
		return nume;
	}

	public void setNume(String nume) {
		this.nume = nume;
	}

	public Boolean getDisponibilitate() {
		return disponibilitate;
	}

	public void setDisponibilitate(Boolean disponibilitate) {
		this.disponibilitate = disponibilitate;
	}

	public Double getPret() {
		return pret;
	}

	public void setPret(Double pret) {
		this.pret = pret;
	}

	@Override
	public String toString() {
		return nume;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((disponibilitate == null) ? 0 : disponibilitate.hashCode());
		result = prime * result + ((identificator == null) ? 0 : identificator.hashCode());
		result = prime * result + ((nume == null) ? 0 : nume.hashCode());
		result = prime * result + ((pret == null) ? 0 : pret.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LocSpectacol other = (LocSpectacol) obj;
		if (disponibilitate == null) {
			if (other.disponibilitate != null)
				return false;
		} else if (!disponibilitate.equals(other.disponibilitate))
			return false;
		if (identificator == null) {
			if (other.identificator != null)
				return false;
		} else if (!identificator.equals(other.identificator))
			return false;
		if (nume == null) {
			if (other.nume != null)
				return false;
		} else if (!nume.equals(other.nume))
			return false;
		if (pret == null) {
			if (other.pret != null)
				return false;
		} else if (!pret.equals(other.pret))
			return false;
		return true;
	}
	
	

}
