package it.polito.tdp.meteo.bean;

import java.util.List;

public class Citta {

	private String nome;
	private List<Rilevamento> rilevamenti;
	private int counterTotali;
	private int counterConsecutivi;
	
	public Citta(String nome) {
		this.nome = nome;
	}
	
	public Citta(String nome, List<Rilevamento> rilevamenti) {
		this.nome = nome;
		this.rilevamenti = rilevamenti;
		this.counterConsecutivi=0;
		this.counterTotali=0;
	}
	
	
	
	public int getCounterConsecutivi() {
		return counterConsecutivi;
	}

	public void setCounterConsecutivi(int counterConsecutivi) {
		this.counterConsecutivi = counterConsecutivi;
	}

	public void add(Rilevamento r) {
		rilevamenti.add(r);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Rilevamento> getRilevamenti() {
		return rilevamenti;
	}

	public void setRilevamenti(List<Rilevamento> rilevamenti) {
		this.rilevamenti = rilevamenti;
	}

	public int getCounterTotali() {
		return counterTotali;
	}

	public void setCounterTotali(int counter) {
		this.counterTotali = counter;
	}
	
	public void increaseCounter() {
		this.counterConsecutivi += 1;
	}
	
	public void decreaseCounter() {
		this.counterConsecutivi -= 1;
	}
	
	public void increaseCounterTotali() {
		this.counterTotali += 1;
	}
	
	public void decreaseCounterTotali() {
		this.counterTotali -= 1;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
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
		Citta other = (Citta) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return nome;
	}
	
	public String stampaRilevamenti() {
		String ris="";
		for(Rilevamento r : rilevamenti) {
			ris += r.getUmidita()+"\n";
		}
		return nome+ris;
	}
	
	public int getUmiditaPerGiorno(int mese, int giorno) {
		int umidita = 0;
		for(Rilevamento r : rilevamenti) {
			if(r.getData().getMonthValue()==mese && r.getData().getDayOfMonth()==giorno)
				umidita = r.getUmidita();
		}
		return umidita;
	}

	
	
}
