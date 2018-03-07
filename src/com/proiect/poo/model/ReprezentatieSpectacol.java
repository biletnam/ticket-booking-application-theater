package com.proiect.poo.model;

public class ReprezentatieSpectacol {
	private Integer identificator;
	private String nume;
	private String data;
	private String ora;

	public ReprezentatieSpectacol() {
		super();
	}

	public ReprezentatieSpectacol(Integer identificator, String nume, String data, String ora) {
		super();
		this.identificator = identificator;
		this.nume = nume;
		this.data = data;
		this.ora = ora;
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

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getOra() {
		return ora;
	}

	public void setOra(String ora) {
		this.ora = ora;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((identificator == null) ? 0 : identificator.hashCode());
		result = prime * result + ((nume == null) ? 0 : nume.hashCode());
		result = prime * result + ((ora == null) ? 0 : ora.hashCode());
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
		ReprezentatieSpectacol other = (ReprezentatieSpectacol) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
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
		if (ora == null) {
			if (other.ora != null)
				return false;
		} else if (!ora.equals(other.ora))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ReprezentatieSpectacol [identificator=" + identificator + ", nume=" + nume + ", data=" + data + ", ora="
				+ ora + "]";
	}

}
