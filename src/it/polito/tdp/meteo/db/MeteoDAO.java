package it.polito.tdp.meteo.db;

import java.sql.Connection;
import java.time.Month;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.meteo.bean.Citta;
import it.polito.tdp.meteo.bean.Rilevamento;

public class MeteoDAO {
	
	

	public List<Rilevamento> getAllRilevamenti() {

		final String sql = "SELECT Localita, Data, Umidita FROM situazione ORDER BY data ASC";

		List<Rilevamento> rilevamenti = new ArrayList<Rilevamento>();
		Citta citt = null;

		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Rilevamento r = new Rilevamento(rs.getString("Localita"), rs.getDate("Data"), rs.getInt("Umidita"));
				rilevamenti.add(r);
				
			}

			conn.close();
			return rilevamenti;

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public List<Rilevamento> getAllRilevamentiLocalitaMese(int mese, String localita) {

		List<Rilevamento> rilevamentiCittaMese = new ArrayList<Rilevamento>();
		
		final String sql = "SELECT localita, data, umidita FROM situazione WHERE localita = ? AND MONTH(data) = ? ";
		
		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, localita);
			st.setInt(2, mese);
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				Rilevamento r = new Rilevamento(rs.getString("Localita"), rs.getDate("Data"), rs.getInt("Umidita"));
				rilevamentiCittaMese.add(r);
			}
			
			conn.close();
			return rilevamentiCittaMese;
		}
		catch(SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public Double getAvgRilevamentiLocalitaMese(int mese, String localita) {

		final String sql = "SELECT AVG(umidita) FROM situazione WHERE localita =? AND MONTH(DATA)=? ";
		Double media = null;
		
		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setString(1, localita);
			st.setInt(2, mese);
			ResultSet rs = st.executeQuery();
			
			if(rs.next()) {
				media = rs.getDouble("AVG(umidita)");
			}
			
			conn.close();
			
		}
		catch(SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return media;
	}
	
	public List<Citta> getCitta(){
		List<Citta> citta = new ArrayList<Citta>();
		
        final String sql = "SELECT localita FROM situazione GROUP BY localita";
		
		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				Citta c = new Citta(rs.getString("localita"));
				citta.add(c);
				this.aggiungiRilevamentoACitta(c);
			}
			
			conn.close();
			
			return citta;
		}
		catch(SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}
	
	public void aggiungiRilevamentoACitta(Citta citta) {
        final String sql = "SELECT localita, data, umidita FROM situazione WHERE localita = ? ";
        List<Rilevamento> rilevamentiCitta = new ArrayList<Rilevamento>();
		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, citta.getNome());
			
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				Rilevamento r = new Rilevamento(rs.getString("Localita"), rs.getDate("Data"), rs.getInt("Umidita"));
				rilevamentiCitta.add(r);
			}
			citta.setRilevamenti(rilevamentiCitta);
			conn.close();
			
		}
		catch(SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
