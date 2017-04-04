package main;
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
		if(args.length != 2){
			System.out.println("Usage: Dinner <Input file> <Output File>");
			return;
		}

		String inputFilePath = args[0];
		String outputFilePath = args[1];
		

		//TODO Parser input file
		//As arrays ficam populadas
		ArrayList<Person> tmpPeople = new ArrayList<Person>();
		for(int i= 0; i < 10; i++){
			tmpPeople.add(new Person(12, Profession.Medico, new Hobby[2]));
		}
		people = new Person[tmpPeople.size()];
		tmpPeople.toArray(people);
		ArrayList<Table> tmpTables = new ArrayList<Table>();
		for(int i= 0; i < 15; i++){
			tmpTables.add(new Table(2,4));
		}
		emptyTables = new Table[tmpTables.size()];
		tmpTables.toArray(emptyTables);
		
		Table[] bestSolution = GeneticAlgorithm.execute();
		
		//TODO output best solution to ouputFile
		
		
	}
	

}
