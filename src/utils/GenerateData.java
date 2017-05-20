package utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import entities.Hobby;
import entities.Profession;

public class GenerateData {
	public static void main(String args[]) throws IOException{
	if(args.length != 2){
		System.out.println("Usage: GenerateData <Input Tables File> <Generate People File>");
		return;
	}
	String tablesPath = args[0];
	String peoplePath = args[1];
	generateTables(tablesPath);
	generatePeople(peoplePath);	
	}
	
	
	public static void generatePeople(String path) throws IOException{
		int nGroups = 50, randNGroups = 0, age = 0, nProfession = 0, nHobbies = 0, nHobby = 0;
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
	
	public static void generateTables(String path) throws IOException{
		int nTablesTypes = 10, nTables = 0, tmpMin = 0, tmpMax = 0, seats = 0;
		BufferedWriter br = null;
		FileWriter fr = null;
		Set<Integer> tablesSeats = null;
		boolean sup = false;
		//random.nextInt(max - min + 1) + min
		Random rand = new Random();
		fr = new FileWriter(path);
		br = new BufferedWriter(fr);
		tablesSeats = new HashSet<Integer>();
		for(int i = 0; i < nTablesTypes; i++){
			nTables = rand.nextInt(10 - 2 + 1) + 2;
			tmpMin = rand.nextInt(18-1+1)+1;
			while(!sup){
				tmpMax = rand.nextInt(20-1+1)+1;
				if(tmpMax > tmpMin)
					sup = true;
			}
			sup = false;
			seats = tmpMin* 100 + tmpMax;
			if(tablesSeats.add(seats)){
				br.write(nTables + "\n" + tmpMin + "\n" + tmpMax + "\n");
			}
		}
		br.close();
		fr.close();
	}
}
