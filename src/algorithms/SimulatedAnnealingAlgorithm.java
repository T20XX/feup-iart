package algorithms;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import entities.Group;
import entities.Person;
import entities.Table;
import main.Dinner;

public class SimulatedAnnealingAlgorithm {

	public static final Table[] execute(double temp, double alpha) throws IOException{
		Table solution[] = randomInitialSolution();
		double avaliation = Table.getAvaliacaoRoom(solution);
		double diffAvaliation = 0;

		Table bestSolution[] = solution;
		double bestAvaliation = avaliation;
		Random rand = new Random();
		int random;
		//random.nextInt(max - min + 1) + min
		while(temp > 0.00000000000000000001){
			random = rand.nextInt(1-0+1);
			solution = generateNextSolution(bestSolution);
			avaliation = Table.getAvaliacaoRoom(solution);
			diffAvaliation = avaliation - bestAvaliation;

			if(diffAvaliation >= 0) {
				bestSolution = solution;
				bestAvaliation = avaliation;
			}
			else if(Math.exp(diffAvaliation/temp) > random){
				bestSolution = solution;
				bestAvaliation = avaliation;
			}
			
			System.out.println(bestAvaliation + "," + temp);
			temp = temp * alpha;
		}
		return bestSolution;
	}

	private static Table[] generateNextSolution(Table[] bestSolution) {
		Table[] nextSolution = new Table[bestSolution.length];
		for(int i = 0; i < nextSolution.length; i++){
			nextSolution[i] = new Table(bestSolution[i], bestSolution[i].getSeatPeople());
		}
		//Remover pessoa de uma mesa e adicionar em outra
		Random r = new Random();
		int tableIndex = r.nextInt(bestSolution.length);
		while(nextSolution[tableIndex].getSeatPeople().size() == 0){
			tableIndex = r.nextInt(bestSolution.length);
		}
		int personIndex = r.nextInt(nextSolution[tableIndex].getSeatPeople().size());
		Person person = nextSolution[tableIndex].removePerson(personIndex);
		tableIndex = r.nextInt(bestSolution.length);
		nextSolution[tableIndex].addPerson(person);

		return nextSolution;
	}

	private static Table[] randomInitialSolution() {
		Table[] filledTables = new Table[Dinner.emptyTables.length];
		for(int i = 0; i < filledTables.length; i++){
			filledTables[i] = new Table(Dinner.emptyTables[i]);
		}
		Random r = new Random();
		int tableIndex = 0;
		for(Person person:Dinner.people){
			tableIndex = r.nextInt(Dinner.emptyTables.length);
			filledTables[tableIndex].addPerson(person);
		}
		return filledTables;
	}


}
