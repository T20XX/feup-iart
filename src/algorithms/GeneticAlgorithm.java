package algorithms;

import java.util.Arrays;
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
	private static int MAX_LOOPS_WO_EVOLUTION = 10;

	public static final Table[] execute(int populationSize, int eliteSelection){
		//TODO receber config por parametros
		nBitsPerTable = (int) Math.floor(Math.log(Dinner.emptyTables.length)/Math.log(2) + 1 );
		nBitsTotal = nBitsPerTable * Dinner.people.length;
		BitSet chromosomes[] = randomInitialPopulation(populationSize, nBitsTotal);
		Table solutions[][] = new Table[populationSize][Dinner.emptyTables.length];
		double avaliations[] = new double[populationSize];
		double totalAvaliation = 0;
		double selectProbs[] = new double[populationSize + 1];
		
		double bestGenerationAvaliation = 0;
		int bestGenerationIndex = 0;
		

		Table bestSolution[] = null;
		double bestAvaliation = 0;
		int loopsWoEvolution = 0;

		for(BitSet chromosome : chromosomes){
			System.out.println(chromosome.toString());
			System.out.println(bitSetToInt(chromosome)[0]);
		}



		//TODO ciclo de algoritmo genetico
		while(loopsWoEvolution < MAX_LOOPS_WO_EVOLUTION){
			loopsWoEvolution++;
			totalAvaliation = 0;
			bestGenerationAvaliation = 0;
			bestGenerationIndex = 0;

			//AVALIACAO
			for(int nSolution = 0; nSolution < populationSize; nSolution++){
				solutions[nSolution] = fillTablesFromChromosome(chromosomes[nSolution]);
				avaliations[nSolution] = Table.getAvaliacaoRoom(solutions[nSolution]);
				totalAvaliation += avaliations[nSolution];
				//Update best avaliation and solution
				if(avaliations[nSolution] > bestAvaliation){
					bestAvaliation = avaliations[nSolution];
					System.out.println("NEW BEST: " + bestAvaliation);
					bestSolution = solutions[nSolution];
					loopsWoEvolution = 0;
				}
				if(avaliations[nSolution] > bestGenerationAvaliation){
					bestGenerationAvaliation = avaliations[nSolution];
					bestGenerationIndex = nSolution;
				}
				//Debug
				System.out.println(chromosomeToString(chromosomes[nSolution]));
				System.out.println(avaliations[nSolution]);
			}

			// SELECAO
			selectProbs[0] = 0;
			System.out.println("PROBS\n0");
			for(int nSolution = 1; nSolution <= populationSize; nSolution++){
				selectProbs[nSolution] = selectProbs[nSolution-1] + (avaliations[nSolution-1] / totalAvaliation);
				System.out.println(selectProbs[nSolution]);
			}

			BitSet[] tmpChromosomes = new BitSet[populationSize];
			for(int nSolution = 0; nSolution < eliteSelection; nSolution++){
					tmpChromosomes[nSolution] = chromosomes[bestGenerationIndex];
			}
			for(int nSolution = eliteSelection; nSolution < (populationSize - eliteSelection + 1); nSolution++){
				    tmpChromosomes[nSolution] = chromosomes[selectRandomIndex(selectProbs)];
			}
			chromosomes = tmpChromosomes;

			// TODO EMPARELHAMENTO

			// TODO CROSSOVER

			//MUTACAO
			for(int nSolution = 0; nSolution < populationSize; nSolution++){
				chromosomes[nSolution] = mutate(chromosomes[nSolution]);
			}

		}
		return bestSolution;
	}

	private static int selectRandomIndex(double[] selectProbs) {
		double r = Math.random();
		int aux = (int) Math.floor(selectProbs.length/2);
		int index = aux;
		System.out.println(r);
		while(r < selectProbs[index] || r >= selectProbs[index + 1]){
			if(r < selectProbs[index]){
				index -= aux;
			} else if (r >= selectProbs[index + 1]){
				index += aux;
			}
			aux /= 2;
		}
		System.out.println(index);
		return index;
	}

	private static BitSet mutate(BitSet chromosome){
		//a funcionar
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
		//a funcionar
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
		//a funcionar
		Table[] filledTables = new Table[Dinner.emptyTables.length];
		for(int i = 0; i < filledTables.length; i++){
			filledTables[i] = new Table(Dinner.emptyTables[i]);
		}
		int[] tables = bitSetToInt(chromosome);
		for(int i = 0; i < Dinner.people.length; i++){
			filledTables[tables[i]].addPerson(Dinner.people[i]);
		}
		return filledTables;
	}

	private static int[] bitSetToInt(BitSet chromosome){
		//a funcionar
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
		//a funcionar
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
