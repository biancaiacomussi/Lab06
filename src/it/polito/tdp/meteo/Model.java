package it.polito.tdp.meteo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import it.polito.tdp.meteo.bean.Citta;
import it.polito.tdp.meteo.bean.SimpleCity;
import it.polito.tdp.meteo.db.MeteoDAO;

public class Model {

	private final static int COST = 100;
	private final static int NUMERO_GIORNI_CITTA_CONSECUTIVI_MIN = 3;
	private final static int NUMERO_GIORNI_CITTA_MAX = 6;
	private final static int NUMERO_GIORNI_TOTALI = 15;
	
	private List<Citta> citta;
	private List<SimpleCity> best;
	private double costo_best;

	public Model() {
		MeteoDAO dao = new MeteoDAO();
		citta = dao.getCitta();
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
		costo_best = 30000000.0;
		List<SimpleCity> parziale = new ArrayList<SimpleCity>();
		cerca(parziale, 0, mese);
		
		return best.toString();
	}

	private void cerca(List<SimpleCity> parziale, int level, int mese) {
		// condizioni di terminazione
		if(this.punteggioSoluzione(parziale) > costo_best)
			return;	
		
		if(this.punteggioSoluzione(parziale) < costo_best) {
			costo_best = this.punteggioSoluzione(parziale);
			best = new ArrayList<SimpleCity> (parziale);
		}
		
		if(level==15) 
			return;
		
	}


	private Double punteggioSoluzione(List<SimpleCity> soluzioneCandidata) {

		double score = 0.0;
		return score;
	}

	private boolean controllaParziale(List<SimpleCity> parziale) {

		return true;
	}

}
