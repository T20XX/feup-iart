package main;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

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

	public static void main(String[] args) throws IOException {
		if(args.length != 3){
			System.out.println("Usage: Dinner <Input Tables File> <Generate People File> <Output File>");
			return;
		}

		generatePeople(args[1]);
		
		String inputTablePath = args[0];
		String outputFilePath = args[2];
		
		if(!parseTables(inputTablePath)){
			System.out.println("Unable to parse tables file.");
			return;
		}
		
		if(!parsePeople(args[1])){
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
		
		Table[] bestSolution = GeneticAlgorithm.execute(4, 1);
		
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
	
	public static void generatePeople(String path) throws IOException{
		int nGroups = 100, randNGroups = 0, age = 0, nProfession = 0, nHobbies = 0, nHobby = 0;
		Profession p = null;
		Hobby h = null;
		Set<Integer> setHobbies = null;
		
		BufferedWriter br = null;
		FileWriter fr = null;
		//random.nextInt(max - min + 1) + min
		Random rand = new Random();
			fr = new FileWriter(path);
			br = new BufferedWriter(fr);

		for(int i = 0; i < nGroups; i++){
			randNGroups = rand.nextInt(5 - 1 + 1) +1;
			br.write(randNGroups + "\n");
			for(int j = 0; j < randNGroups; j++){
				age = rand.nextInt(100 -1 + 1) +1;
				nProfession = rand.nextInt(Profession.values().length);
				p = Profession.values()[nProfession];
				nHobbies = rand.nextInt(Hobby.values().length);
					br.write(age  + "\n" + p + "\n");
					setHobbies = new HashSet<Integer>();
				for(int k = 0; k < nHobbies; k++){
					nHobby = rand.nextInt(Hobby.values().length);
					setHobbies.add(nHobby);
				}
				nHobbies = setHobbies.size();
				br.write(nHobbies + "\n");
				 Iterator<Integer> iterator = setHobbies.iterator();
				 while(iterator.hasNext()){
					 br.write(Hobby.values()[iterator.next()] + "\n");
				    }
			}
		}
		br.close();
		fr.close();
	}
}
