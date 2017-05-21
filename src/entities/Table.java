package entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import main.Dinner;

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
		this.seatPeople = (ArrayList<Person>)seatPeople .clone();
	}


	public Table(Table table) {
		this(table.getMinSeats(), table.getMaxSeats());
	}

	public Table(Table table, ArrayList<Person> seatPeople) {
		this(table.getMinSeats(), table.getMaxSeats(), seatPeople);
	}
	
	private double getAfinidadeEtaria(){
		double avgAge = 0, difAge = 0, afinidadeEtaria = 0;
		int totalAge = 0;
		int ages[] = {0,0,0,0};
		
		int diffAges = 0;
		
		for(Person person : seatPeople){
			if(person.getAge() < 25)
				ages[0]++;
			else if (person.getAge() < 50)
				ages[1]++;
			else if(person.getAge() < 75)
				ages[2]++;
			else ages[3]++;
		}
		
		for(int i = 0; i < ages.length; i++){
			if(ages[i] != 0){
				diffAges++;
			}
		}
		
		
		afinidadeEtaria = 1 - (double)diffAges/seatPeople.size();
		return afinidadeEtaria*100;

	}

	private double getAfinidadeProfissao(){
		HashSet<Area> areasDistintas = new HashSet<Area>();
		for(Person person : seatPeople){
			Area area = person.getProfession().getArea();
				areasDistintas.add(area);
		}
		if(areasDistintas.size() == 0)
			return 0;
		double afinidadeProfissao = 1 - (double)areasDistintas.size()/seatPeople.size();
		afinidadeProfissao *= 100;
		return afinidadeProfissao;
	}

	private double getAfinidadeHobbies(){
		ArrayList<Hobby> hobbiesPessoas = new ArrayList<Hobby>();
		HashSet<Hobby> hobbiesTotal = new HashSet<Hobby>();
		double afinidadeHobbies = 0;
		for(Person person : seatPeople){
			Hobby hobbies[] = person.getHobbies();
			for(Hobby hobby: hobbies){
					hobbiesPessoas.add(hobby);
					hobbiesTotal.add(hobby);
			}
		}
		for (Hobby hobby : hobbiesTotal){
			afinidadeHobbies += (double)((double)Collections.frequency(hobbiesPessoas, hobby)/(double)seatPeople.size())/(double)hobbiesTotal.size(); 
		}
		afinidadeHobbies *=100;

		return afinidadeHobbies;
	}

	public double getAvaliacao(){
		return 0.3*getAfinidadeEtaria()
		+ 0.35 * getAfinidadeProfissao()
		+ 0.35 * getAfinidadeHobbies();
	}

	public double getPenalizacao(){
		int nPeople = seatPeople.size();
		double penalizacao = 0;

		if(nPeople < min){
			penalizacao += Math.pow((min - nPeople),2);
		} else if(nPeople > max){
			penalizacao += Math.pow((nPeople - max),3);
		}

		if(penalizacao < 0)
			return 0;
		else
			return penalizacao;
	}
	
	public static double getPenalizacaoGrupos(Table tables[]){
		Set<Group> allGroups = new HashSet<Group>();
		Set<Group> separatedGroups = new HashSet<Group>();
		for(Table t: tables){
			HashMap<Group, Integer> groupsTable  = new HashMap<Group, Integer>();
			for(Person person : t.getSeatPeople()){
				Group group = person.getGroup();
				if(group != null){
					Integer count = groupsTable.get(group);
					if(count == null){
						groupsTable.put(group, 1);
					} else {
						groupsTable.put(group, count + 1);
					}
				}
			}
			int totalGroups = groupsTable.size();
			for (Map.Entry<Group, Integer> entry : groupsTable.entrySet()){
				int totalMembers = ((Group)entry.getKey()).getMembers().length;
				int membersTable = entry.getValue();
				allGroups.add(entry.getKey());
				if (membersTable != totalMembers){
					separatedGroups.add(entry.getKey());
				}
			}
		}
		return (double)separatedGroups.size()/allGroups.size() * 100;
	}

	public static double getAvaliacaoRoom(Table tables[]){
		double result = 0;
		int usedTables = 0;
		int nPeople = 0;

		for(Table table: tables){
			if(table.getSeatPeople().size() > 0){
				double tmpAvaliation = table.getAvaliacao()- table.getPenalizacao(); 
				if(tmpAvaliation > 0) {
					result += tmpAvaliation * table.getSeatPeople().size();
				}
				usedTables++;
				nPeople += table.getSeatPeople().size(); 
			}
		}
		result /= nPeople;
		result += 100;
		
		result -= getPenalizacaoGrupos(tables);
		return result;
	}

	public ArrayList<Person> getSeatPeople() {
		return seatPeople;
	}

	public void addPerson(Person p){
		seatPeople.add(p);
	}

	public Person removePerson(int index){
		Person p = seatPeople.get(index);
		seatPeople.remove(index);
		return p;
	}

	public int getMinSeats(){
		return this.min;
	}
	public int getMaxSeats(){
		return this.max;
	}
}
