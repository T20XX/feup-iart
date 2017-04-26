package algorithms;

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

	public static final Table[] execute(int maxLoopsWoEvolution){
		Table solution[] = randomInitialSolution();
		double avaliation = Table.getAvaliacaoRoom(solution);
		double diffAvaliation = 0;

		Table bestSolution[] = solution;
		double bestAvaliation = avaliation;
		int loopsWoEvolution = 0;

		while(loopsWoEvolution < maxLoopsWoEvolution){
			loopsWoEvolution++;

			solution = generateNextSolution(bestSolution);
			avaliation = Table.getAvaliacaoRoom(solution);
			diffAvaliation = avaliation - bestAvaliation;

			if(diffAvaliation > 0){
				bestSolution = solution;
				bestAvaliation = avaliation;
				loopsWoEvolution = 0;
			} else {
				//TODO TEMPERATURA
			}


		}
		return bestSolution;
	}

	private static Table[] generateNextSolution(Table[] bestSolution) {
		Table[] nextSolution = new Table[bestSolution.length];
		for(int i = 0; i < nextSolution.length; i++){
			nextSolution[i] = new Table(bestSolution[i]);
		}

		//Remover pessoa de uma mesa e adicionar em outra
		Random r = new Random();
		int tableIndex = r.nextInt(bestSolution.length);
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
