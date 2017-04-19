package main;
import java.io.BufferedReader;
import java.io.FileReader;
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

	public static void main(String[] args) {
		if(args.length != 3){
			System.out.println("Usage: Dinner <Input Tables File> <Input People File> <Output File>");
			return;
		}

		String inputTablePath = args[0];
		String inputPeoplePath = args[1];
		String outputFilePath = args[2];
		
		if(!parseTables(inputTablePath)){
			System.out.println("Unable to parse tables file.");
			//return;
		}
		
		if(!parsePeople(inputPeoplePath)){
			System.out.println("Unable to parse tables file.");
			//return;
		}
		
		Table[] bestSolution = GeneticAlgorithm.execute(4);
		
		//TODO output best solution to ouputFile		
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
}
