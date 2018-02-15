package com.example.dto;

public class SearchForm {
	private String kategorija;
	private String verzija;
	private String kljucna_rec;
	private String ime;
	private String prezime;
	private String institucija;
	private String mod;
	
	
	public String getKategorija() {
		return kategorija;
	}
	
	public void setKategorija(String kategorija) {
		this.kategorija = kategorija;
	}
	
	public String getVerzija() {
		return verzija;
	}
	
	public void setVerzija(String verzija) {
		this.verzija = verzija;
	}
	
	public String getKljucna_rec() {
		return kljucna_rec;
	}
	
	public void setKljucna_rec(String kljucna_rec) {
		this.kljucna_rec = kljucna_rec;
	}
	
	public String getIme() {
		return ime;
	}
	
	public void setIme(String ime) {
		this.ime = ime;
	}
	
	public String getPrezime() {
		return prezime;
	}
	
	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}
	
	public String getInstitucija() {
		return institucija;
	}
	
	public void setInstitucija(String institucija) {
		this.institucija = institucija;
	}
	
	public String getMod() {
		return mod;
	}
	
	public void setMod(String mod) {
		this.mod = mod;
	}
	
	public SearchForm(String kategorija, String verzija, String kljucna_rec, String ime, String prezime,
			String institucija, String mod) {
		super();
		this.kategorija = kategorija;
		this.verzija = verzija;
		this.kljucna_rec = kljucna_rec;
		this.ime = ime;
		this.prezime = prezime;
		this.institucija = institucija;
		this.mod = mod;
	}
	
	public SearchForm(){
		super();
	}

	@Override
	public String toString() {
		return "SearchForm [kategorija=" + kategorija + ", verzija=" + verzija + ", kljucna_rec=" + kljucna_rec
				+ ", ime=" + ime + ", prezime=" + prezime + ", institucija=" + institucija + ", mod=" + mod + "]";
	}
	
	
}
