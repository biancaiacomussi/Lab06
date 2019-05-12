package it.polito.tdp.meteo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import it.polito.tdp.meteo.bean.Citta;
import it.polito.tdp.meteo.bean.SimpleCity;
import it.polito.tdp.meteo.db.MeteoDAO;

public class Model {

	private final static int COST = 100;
	private final static int NUMERO_GIORNI_CITTA_CONSECUTIVI_MIN = 3;
	private final static int NUMERO_GIORNI_CITTA_MAX = 6;
	private final static int NUMERO_GIORNI_TOTALI = 15;
	
	private List<Citta> cittaParziali;
	private List<Citta> citta;
	private List<SimpleCity> best;
	private double costo_best;
	int ric;
	

	public Model() {
		MeteoDAO dao = new MeteoDAO();
		/*cittaParziali = dao.getCitta();
		
		citta = new ArrayList<Citta>();	//voglio una lista con 6 copie della stessa città
		for(Citta c : cittaParziali) {
			citta.add(c);
			citta.add(c);
			citta.add(c);
			citta.add(c);
			citta.add(c);
			citta.add(c);
		}*/
		citta = dao.getCitta();
		ric=0;
			}
	

	public String getUmiditaMedia(int mese) {

		MeteoDAO dao = new MeteoDAO();
		String ris="";
		for(Citta c : dao.getCitta()) {
			ris += c.getNome()+ " umidità media: "+dao.getAvgRilevamentiLocalitaMese(mese, c.getNome())+"\n";
		}
		return ris;
	}

	public String trovaSequenza(int mese) {
		best = null;
		costo_best = Double.MAX_VALUE;
		List<SimpleCity> parziale = new ArrayList<SimpleCity>();
		cerca(parziale, 0, mese);
		
		return best.toString();
		}
	
	public List<SimpleCity> getBest() {
		return best;
	}


	public double getCosto_best() {
		return costo_best;
	}


	/*private void cerca(List<SimpleCity> parziale, int level, int mese) {
		//il livello è la posizione nella lista di città
		
		//int i = citta.get(level).getCounterConsecutivi();
		//int j= citta.get(level).getCounterTotali();
		
		if(parziale.size()>15) {
			return;
		}
		if(parziale.size()<=15) {
			if(this.punteggioSoluzione(parziale) > costo_best)
				return;	
		}
			if(parziale.size()==15 && this.controllaParziale(parziale)==true) {
			if(this.punteggioSoluzione(parziale) < costo_best) {
				costo_best = this.punteggioSoluzione(parziale);
				best = new ArrayList<SimpleCity> (parziale);
				return;
				}
		}
			
			if(level==citta.size())
				return;
			
			
			if(citta.get(level).getCounterTotali() <6) {
			
			if(level>=1)
			if(!citta.get(level).equals(citta.get(level-1))) { //ho cambiato città: devo azzerare il contatore
				citta.get(level).setCounterConsecutivi(0);
				
			}
			
			//provo ad aggiungere la città
			if(citta.get(level).getCounterConsecutivi()<3 ) { //devo aggiungere la stessa città
			SimpleCity s = new SimpleCity(citta.get(level).getNome(), citta.get(level).getUmiditaPerGiorno(mese, parziale.size()+1));
			parziale.add(s);
			citta.get(level).increaseCounter();
			citta.get(level).increaseCounterTotali();
			cerca(parziale, level, mese);
			parziale.remove(s);
			citta.get(level).decreaseCounter();
			citta.get(level).decreaseCounterTotali();
			}
			
			//provo a non aggiungere la città
			cerca(parziale,level+1,mese);
			
			//provo ad aggiungere la città
			SimpleCity s = new SimpleCity(citta.get(level).getNome(), citta.get(level).getUmiditaPerGiorno(mese, parziale.size()+1));
			parziale.add(s);
			citta.get(level).increaseCounter();
			citta.get(level).increaseCounterTotali();
			cerca(parziale, level, mese);
			parziale.remove(s);
			citta.get(level).decreaseCounter();
			citta.get(level).decreaseCounterTotali();
			}	else return;
			
	}
			
			
			
	
	//}*/

	private void cerca(List<SimpleCity> parziale, int level, int mese) {
		
		ric++;
		SimpleCity s = null;
		
		// condizioni di terminazione
		if(level<15) {
		if(this.punteggioSoluzione(parziale) > costo_best)
			return;	
		}
		
		if(level==15 && controllaParziale(parziale)==true) {
		if(this.punteggioSoluzione(parziale) < costo_best) { //come faccio qui perchè se non ho raggiunto 15 è possibile che abbia un costo inferiore
			costo_best = this.punteggioSoluzione(parziale);
			best = new ArrayList<SimpleCity> (parziale);
			return;
			} else return;
		}
		
		
		
		for(Citta c : citta) {
			
			s = new SimpleCity(c.getNome(), c.getUmiditaPerGiorno(mese, level+1));
			
			if(aggiuntaValida(s,parziale)) {
				
				parziale.add(s);
				cerca(parziale, level+1, mese);
				parziale.remove(parziale.size()-1);
			}
			}
		}
		
		public int getRic() {
		return ric;
	}


		private boolean aggiuntaValida(SimpleCity prova, List<SimpleCity> parziale) {
		
			int conta=0; //conto quante volte la citta di prova è presente nel parziale
			for(SimpleCity precedente: parziale) {
				if(precedente.equals(prova))
					conta++;
			}
			if(conta>=6) //non posso aggiungere se la citta è presente già 6 volte
				return false;
			
			//verifica giorni minimi (3)
			if(parziale.size()==0) //primo giorno, posso aggiungere
				return true;
			if(parziale.size()==1 || parziale.size()==2) { //secondo o terzo giorno: non posso cambiare
				return parziale.get(parziale.size()-1).equals(prova); //posso aggiungere solo l'ultima città aggiunta
			}
			if(parziale.get(parziale.size()-1).equals(prova)) //giorni successivi, posso rimanere
				return true;
			if(parziale.get(parziale.size()-1).equals(parziale.get(parziale.size()-2)) && parziale.get(parziale.size()-2).equals(parziale.get(parziale.size()-3)))
				return true;
			
		return false;
	}



	private Double punteggioSoluzione(List<SimpleCity> soluzioneCandidata) {

		double score = 0.0;
		int punteggio = 0;
		for(int i=0; i<soluzioneCandidata.size(); i++) {
			punteggio = soluzioneCandidata.get(i).getCosto();
			if(i!=0)
				if(!soluzioneCandidata.get(i).equals(soluzioneCandidata.get(i-1)))
					punteggio += 100;
			score += punteggio;
		}
		return score;
	}

	private boolean controllaParziale(List<SimpleCity> parziale) {

		boolean ris = true;
		
			for(Citta c : citta) {
				SimpleCity s = new SimpleCity(c.getNome(),0);
				if(!parziale.contains(s))
					ris = false;
			}
		
		return ris;
	}

}
