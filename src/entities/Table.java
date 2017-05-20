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
		
		//System.out.print("AGES CARALHO :" + diffAges + "SIZE" + seatPeople.size() + "\n" );
		
		afinidadeEtaria = 1 - (double)diffAges/seatPeople.size();
		return afinidadeEtaria*100;
		
		/*for(Person person : seatPeople){
			totalAge += person.getAge();
		}
		avgAge = totalAge / seatPeople.size();
		for(Person person : seatPeople){
			difAge += Math.abs(person.getAge() - avgAge);
		}
		//afinidadeEtaria = (1/(difAge + 1))*100;
		afinidadeEtaria = 100 - difAge;
		if(afinidadeEtaria < 0){
			return 0;
		} else {
			return afinidadeEtaria;
		}*/
	}

	private double getAfinidadeProfissao(){
		HashSet<Area> areasDistintas = new HashSet<Area>();
		//double afinidadeProfissao = 0;
		for(Person person : seatPeople){
			Area area = person.getProfession().getArea();
			//System.out.println(area);
				areasDistintas.add(area);
		}
		if(areasDistintas.size() == 0)
			return 0;
		double afinidadeProfissao = 1 - (double)areasDistintas.size()/seatPeople.size();
		afinidadeProfissao *= 100;
		return afinidadeProfissao;
	}

	private double getAfinidadeHobbies(){
		//TODO mapa para hobbies ligado a array de pessoas
		//formula para testar Soma(numPessoasGostamHobby/numTotalPessoas)/NumHobbies
		/*ArrayList<Hobby> hobbiesTotal = new ArrayList<Hobby>();
		ArrayList<Hobby> hobbiesComum = new ArrayList<Hobby>();
		double afinidadeHobbies = 0;
		for(Person person : seatPeople){
			Hobby hobbies[] = person.getHobbies();
			for(Hobby hobby: hobbies){
				if(!hobbiesTotal.contains(hobby)){
					hobbiesTotal.add(hobby);
				}else if(!hobbiesComum.contains(hobby)){
					hobbiesComum.add(hobby);
				}
			}
		}
		afinidadeHobbies = (hobbiesTotal.size()/(hobbiesComum.size()+1))*100;*/
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
		//afinidadeHobbies /= hobbiesTotal.size();

		return afinidadeHobbies;
	}

	public double getAvaliacao(){
		return 0.3*getAfinidadeEtaria()
		+ 0.35 * getAfinidadeProfissao()
		+ 0.35 * getAfinidadeHobbies();
	}

	public double getPenalizacao(){
		//TODO penalizacao table
		int nPeople = seatPeople.size();
		double penalizacao = 0;
		/*HashMap<Group, Integer> groupsTable  = new HashMap<Group, Integer>();
		for(Person person : seatPeople){
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
			penalizacao += (double)(1 - (double)membersTable/totalMembers)/(totalGroups) * 100;
			//penalizacao += totalMembers -(membersTable/totalMembers)*totalGroups;
		}*/


		if(nPeople < min){
			penalizacao += Math.pow((min - nPeople),2);
		} else if(nPeople > max){
			penalizacao += Math.pow((nPeople - max),3);
			
		}

		//if(penalizacao > 100)
			//return 100;
		//else
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
