package algorithms;

import java.util.BitSet;
import java.util.Random;

import entities.Group;
import entities.Person;
import entities.Table;
import main.Dinner;

public class GeneticAlgorithm {

	private static int nBitsPerTable;
	private static int nBitsTotal;
	private static int MUTATION_PROB = 1; //in 10000

	public static final Table[] execute(int populationSize){
		//TODO receber config por parametros
		nBitsPerTable = (int) Math.floor(Math.log(Dinner.emptyTables.length)/Math.log(2) + 1 );
		nBitsTotal = nBitsPerTable * Dinner.people.length;
		BitSet chromosomes[] = randomInitialPopulation(populationSize, nBitsTotal);
		Table solutions[][] = new Table[populationSize][Dinner.emptyTables.length];
		double avaliations[] = new double[populationSize];
		for(BitSet chromosome : chromosomes){
			System.out.println(chromosome.toString());
			System.out.println(bitSetToInt(chromosome)[0]);
		}



		//TODO ciclo de algoritmo genetico
		for(int i=0; i< 1; i++){
			for(int nSolution = 0; nSolution < populationSize; nSolution++){
				solutions[nSolution] = fillTablesFromChromosome(chromosomes[nSolution]);
				avaliations[nSolution] = Table.getAvaliacaoRoom(solutions[nSolution]);
				System.out.println(chromosomeToString(chromosomes[nSolution]));
				System.out.println(avaliations[nSolution]);
			}

		}

		Table bestSolution[] = null;
		return bestSolution;
	}

	private static BitSet mutate(BitSet chromosome){
		BitSet mutatedChromosome = chromosome;
		Random r = new Random();
		
		for(int i = 0; i < nBitsTotal; i++){
			if(r.nextInt(10000) < MUTATION_PROB){
				mutatedChromosome.flip(i);	
			}
		}

		return mutatedChromosome;
	}

	private static BitSet[] randomInitialPopulation(int populationSize, int nBits){
		BitSet chromosomes[] = new BitSet[populationSize];
		Random r = new Random();
		for(int i = 0; i < populationSize; i++){
			chromosomes[i] = new BitSet(nBits);
		}

		for(BitSet chromosome : chromosomes){
			for(int i = 0; i < nBits; i++){
				if(r.nextInt(100) < 50){
					chromosome.set(i);
				}
			}
		}

		return chromosomes;
	}
	private static Table[] fillTablesFromChromosome(BitSet chromosome){
		Table[] filledTables = Dinner.emptyTables.clone();
		int[] tables = bitSetToInt(chromosome);
		for(int i = 0; i < Dinner.people.length; i++){
			filledTables[tables[i]].addPerson(Dinner.people[i]);
		}
		return filledTables;
	}
	private static int[] bitSetToInt(BitSet chromosome){
		int[] result = new int[Dinner.people.length];

		for(int i = 0; i < Dinner.people.length; i++){
			for(int j = 0; j < nBitsPerTable; j++){
				result[i] += chromosome.get(i * nBitsPerTable + j) ? (1L << (nBitsPerTable - j - 1)) : 0L;
			}
			result[i] = result[i] % Dinner.emptyTables.length;
		}
		return result;
	}

	private static String chromosomeToString(BitSet chromosome){
		String result = new String();
		int[] tables = bitSetToInt(chromosome);


		result += "|";
		for(int i = 0; i < nBitsTotal; i++){
			if (chromosome.get(i)){
				result += "1";
			} else {
				result += "0";
			}
			if(((i + 1) % nBitsPerTable) == 0){
				result += "|";
			}
		}
		result += "\n";

		result += "|";
		for(int i = 0; i < Dinner.people.length; i++){
			result += String.format("%"+nBitsPerTable+"s", tables[i]);
			result += "|";
		}

		return result;
	}


}
