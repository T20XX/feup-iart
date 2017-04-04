package entities;

import java.util.ArrayList;

public class Table {

	int min;
	int max;
	ArrayList<Person> seatPeople;
	
	public Table(int min, int max) {
		this.min = min;
		this.max = max;
		this.seatPeople = new ArrayList<Person>();
	}
	
	public Table(int min, int max, ArrayList<Person> seatPeople) {
		this.min = min;
		this.max = max;
		this.seatPeople = seatPeople;
	}
	
	
	private double getAfinidadeEtaria(){
		//TODO Afinidade etaria table
		return 0.0;
	}
	
	private double getAfinidadeProfissao(){
		//TODO Afinidade profissao table
		return 0.0;
	}
	
	private double getAfinidadeHobbies(){
		//TODO Afinidade hobbies table
		return 0.0;
	}
	
	public double getAvaliacao(){
		return 0.3 * getAfinidadeEtaria()
				+ 0.35 * getAfinidadeProfissao()
				+ 0.35 * getAfinidadeHobbies();
	}
	
	public double getPenalizacao(Group groups[]){
		//TODO penalizacao table
		return 0.0;
	}
	
	public static double getAvaliacaoRoom(Table tables[], Group groups[]){
		double result = 0;
		
		for(Table table: tables){
			result += (table.getAvaliacao() - table.getPenalizacao(groups));
		}
		
		return result;
	}

}
