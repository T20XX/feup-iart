package entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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


	public Table(Table table) {
		this(table.getMinSeats(), table.getMaxSeats());
	}

	private double getAfinidadeEtaria(){
		double avgAge = 0, difAge = 0, afinidadeEtaria = 0;
		int totalAge = 0;
		for(Person person : seatPeople){
			totalAge += person.getAge();
		}
		avgAge = totalAge / seatPeople.size();
		for(Person person : seatPeople){
			difAge += Math.abs(person.getAge() - avgAge);
		}
		afinidadeEtaria = (1/(difAge + 1))*100;
		return afinidadeEtaria;
	}

	private double getAfinidadeProfissao(){
		ArrayList<Area> areasDistintas = new ArrayList<Area>();
		double afinidadeProfissao = 0;
		for(Person person : seatPeople){
			Area area = person.getProfession().getArea();
			if(!areasDistintas.contains(area)){
				areasDistintas.add(area);
			}
		}
		afinidadeProfissao = (1/(areasDistintas.size() + 1))*100;
		return afinidadeProfissao;
	}

	private double getAfinidadeHobbies(){
		//TODO mapa para hobbies ligado a array de pessoas
		//formula para testar Soma(numPessoasGostamHobby/numTotalPessoas)/NumHobbies
		ArrayList<Hobby> hobbiesTotal = new ArrayList<Hobby>();
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
		afinidadeHobbies = (hobbiesTotal.size()/(hobbiesComum.size()+1))*100;
		return afinidadeHobbies;
	}

	public double getAvaliacao(){
		return 0.3 * getAfinidadeEtaria()
				+ 0.35 * getAfinidadeProfissao()
				+ 0.35 * getAfinidadeHobbies();
	}

	public double getPenalizacao(){
		//TODO penalizacao table
		int nPeople = seatPeople.size();
		double penalizacao = 0;
		HashMap<Group, Integer> groupsTable  = new HashMap<Group, Integer>();
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
			penalizacao += (1 - membersTable/totalMembers)/(totalMembers*totalGroups) * 100;
			//penalizacao += totalMembers -(membersTable/totalMembers)*totalGroups;
		}


		if(nPeople < min){
			penalizacao += (min - totalGroups);
		} else if(nPeople > max){
			penalizacao += (totalGroups - max);
		}
		return penalizacao;
	}

	public static double getAvaliacaoRoom(Table tables[]){
		double result = 0;
		int usedTables = 0;

		for(Table table: tables){
			if(table.getSeatPeople().size() > 0){
				result += (table.getAvaliacao() - table.getPenalizacao());
				usedTables++;
			}
		}
		result /= usedTables;

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
