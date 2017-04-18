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
			System.out.println("Usage: Dinner <Input Table File> <Input People File> <Output File>");
			return;
		}

		String inputTablePath = args[0];
		String inputPeoplePath = args[1];
		String outputFilePath = args[2];
		
		
		//TODO Parser input file
		parserTable(inputTablePath);
		
		//As arrays ficam populadas
		ArrayList<Person> tmpPeople = new ArrayList<Person>();
		for(int i= 0; i < 10; i++){
			tmpPeople.add(new Person(12, Profession.Medico, new Hobby[2]));
		}
		people = new Person[tmpPeople.size()];
		tmpPeople.toArray(people);
		
		Table[] bestSolution = GeneticAlgorithm.execute();
		
		//TODO output best solution to ouputFile		
	}	
	
	private static Table[] parserTable(String path){

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
		} catch (IOException e) {
			e.printStackTrace();
		}
		Table[] emptyTables = new Table[tmpTables.size()];
		tmpTables.toArray(emptyTables);
		return emptyTables;
	}
	private static Person[] parserPeople(String path){

		ArrayList<Table> tmpPeople = new ArrayList<Table>();
		BufferedReader brPeople = null;
		FileReader frPeople = null;
		
		try {

			frPeople = new FileReader(path);
			brPeople = new BufferedReader(frTable);	
			
			String nTables;
			int minSeats;
			int maxSeats;
			while((nTables = brPeople.readLine()) != null){
				minSeats = Integer.parseInt(brTable.readLine());
				maxSeats = Integer.parseInt(brTable.readLine());
				for(int i = 0; i < Integer.parseInt(nTables); i++){
					tmpPeople.add(new Table(minSeats, maxSeats));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		Person[] emptyPeople = new Person[tmpPeople.size()];
		tmpPeople.toArray(emptyPeople);
		return emptyPeople;
	}
}
