package it.polito.tdp.meteo;

public class TestModel {

	public static void main(String[] args) {

		Model m = new Model();
		
		System.out.println(m.getUmiditaMedia(12));
		
		
		
		System.out.println(m.trovaSequenza(5));
		
		System.out.println(m.getRic());
		
		System.out.println(m.getBest().toString());
		
		System.out.println((m.getCosto_best()));
		
		System.out.println(m.trovaSequenza(4));
		
		System.out.println(m.getBest().toString());
		
		System.out.println((m.getCosto_best()));
	}

}
