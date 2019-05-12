package it.polito.tdp.meteo.db;

import java.util.List;

import it.polito.tdp.meteo.bean.Citta;
import it.polito.tdp.meteo.bean.Rilevamento;

public class TestMeteoDAO {

	public static void main(String[] args) {

		MeteoDAO dao = new MeteoDAO();

		List<Rilevamento> list = dao.getAllRilevamenti();

		// STAMPA: localita, giorno, mese, anno, umidita (%)
		for (Rilevamento r : list) {
			System.out.format("%-10s %2td/%2$2tm/%2$4tY %3d%%\n", r.getLocalita(), r.getData(), r.getUmidita());
		}
		
		System.out.println(dao.getAllRilevamentiLocalitaMese(1, "Genova"));
//		System.out.println(dao.getAvgRilevamentiLocalitaMese(1, "Genova"));
//		
//		System.out.println(dao.getAllRilevamentiLocalitaMese(5, "Milano"));
//		System.out.println(dao.getAvgRilevamentiLocalitaMese(5, "Milano"));
//		
//		System.out.println(dao.getAllRilevamentiLocalitaMese(5, "Torino"));
//		System.out.println(dao.getAvgRilevamentiLocalitaMese(5, "Torino"));
	
	System.out.println(dao.getCitta().toString());
	
	for(Citta c: dao.getCitta()) {
		System.out.println(c.getNome());
		for(int i = 1; i<=31; i++)
		System.out.println(c.getUmiditaPerGiorno(5, i));
	}
		
	}

}
