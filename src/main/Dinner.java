package main;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import algorithms.GeneticAlgorithm;
import entities.Group;
import entities.Hobby;
import entities.Person;
import entities.Profession;
import entities.Table;

public class Dinner {
	
	public static final String FS = System.getProperty("file.separator");

	public static Person people[];
	public static Group groups[];
	public static Table emptyTables[];
	public static Table bestSolution[];

	public static void main(String[] args) throws IOException {
		if(args.length != 3){
			System.out.println("Usage: Dinner <Input Tables File> <Generate People File> <Output File>");
			return;
		}
		String tablesPath = args[0];
		String peoplePath = args[1];
		String outputPath = args[2];
		
		if(!parseTables(tablesPath)){
			System.out.println("Unable to parse tables file.");
			return;
		}
		
		if(!parsePeople(peoplePath)){
			System.out.println("Unable to parse tables file.");
			return;
		}
		for(int i = 0; i < emptyTables.length; i++){
			System.out.println(emptyTables[i].getMinSeats() + " - " + emptyTables[i].getMaxSeats());
		}
		for(int i = 0; i < people.length; i++){
			System.out.println(people[i].getAge() + " " + people[i].getProfession() + " ");
			for(int j = 0; j < people[i].getHobbies().length; j++){
				System.out.println(people[i].getHobbies()[j]);
			}
		}
		for(int i = 0; i < groups.length; i++){
			System.out.println("GROUP: " + i);
			for(int j = 0; j < groups[i].getMembers().length; j++){
				System.out.println(groups[i].getMembers()[j].getAge() + " " + groups[i].getMembers()[j].getProfession() + " ");
				for(int k = 0; k < groups[i].getMembers()[j].getHobbies().length; k++){
					System.out.println(groups[i].getMembers()[j].getHobbies()[k]);
				}
			}
		}		
		
		bestSolution = GeneticAlgorithm.execute(4, 1);
		
		//TODO output best solution to ouputFile
		writeOutput(bestSolution, outputPath);
	}	

	private static boolean parseTables(String path){

		ArrayList<Table> tmpTables = new ArrayList<Table>();
		
		BufferedReader brTable = null;
		FileReader frTable = null;
		
		try {
			frTable = new FileReader(path);
			brTable = new BufferedReader(frTable);	
			
			String nTables;
			int minSeats;
			int maxSeats;
			
			while((nTables = brTable.readLine()) != null){
				minSeats = Integer.parseInt(brTable.readLine());
				maxSeats = Integer.parseInt(brTable.readLine());
				for(int i = 0; i < Integer.parseInt(nTables); i++){
					tmpTables.add(new Table(minSeats, maxSeats));
				}
			}

			brTable.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		emptyTables = new Table[tmpTables.size()];
		tmpTables.toArray(emptyTables);
		
		return true;
	}
	private static boolean parsePeople(String path){

		ArrayList<Person> peopleFinal = new ArrayList<Person>();
		ArrayList<Group> groupsFinal = new ArrayList<Group>();

		BufferedReader brPeople = null;
		FileReader frPeople = null;
		
		try {

			frPeople = new FileReader(path);
			brPeople = new BufferedReader(frPeople);	
			
			String nPeople;
			int nPeoplei;
			int age;
			String profession;
			int nHobbies;
			Hobby[] tmpHobbies;
			Person[] tmpPeople;
			
			while((nPeople = brPeople.readLine()) != null){
				nPeoplei = Integer.parseInt(nPeople);
				tmpPeople = new Person[nPeoplei];
				for(int i = 0; i < nPeoplei; i++){
					age = Integer.parseInt(brPeople.readLine());
					profession = brPeople.readLine();
					nHobbies = Integer.parseInt(brPeople.readLine());
					tmpHobbies = new Hobby[nHobbies];
					for(int j = 0; j < nHobbies; j++){
						tmpHobbies[j] = Hobby.valueOf(brPeople.readLine());
					}
					tmpPeople[i] = new Person(age, Profession.valueOf(profession), tmpHobbies);
					peopleFinal.add(tmpPeople[i]);
				}
				if(tmpPeople.length > 1){
					groupsFinal.add(new Group(tmpPeople));
				}
			}
			
			brPeople.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		people = new Person[peopleFinal.size()];
		peopleFinal.toArray(people);
		groups = new Group[groupsFinal.size()];
		groupsFinal.toArray(groups);
		
		return true;
	}
	

	
	private static void writeOutput(Table[] bestSolution, String outputPath) throws IOException {
		BufferedWriter br = null;
		FileWriter fr = null;
		fr = new FileWriter(outputPath);
		br = new BufferedWriter(fr);
		
		for(Person person : people){
			br.write(person.getID() + "\n" + person.getAge() + "\n" + person.getProfession() + "\n");
			for(Hobby hobby : person.getHobbies()){
				br.write(hobby + "\n");
			}
		}
		
		for(int i = 0; i < bestSolution.length; i++){
			br.write("Min, Max Lugares : " + bestSolution[i].getMinSeats() + ", " + bestSolution[i].getMaxSeats() + "\n");
			for(int j = 0; j < bestSolution[i].getSeatPeople().size(); j++){
				br.write(bestSolution[i].getSeatPeople().get(j).getID() + "\n");
			}
		}
		br.close();
		fr.close();
	}
	
}
