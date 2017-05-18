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

public class GeneticAlgorithm {

	private static int nBitsPerTable;
	private static int nBitsTotal;
	private static int CROSSOVER_PROB = 50; //in 100
	private static int MUTATION_PROB = 10; //in 10000

	public static final Table[] execute(int maxLoops, int populationSize, int eliteSelection) throws IOException{
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

		/*for(BitSet chromosome : chromosomes){
			System.out.println(chromosome.toString());
			System.out.println(bitSetToInt(chromosome)[0]);
		}*/



		//TODO ciclo de algoritmo genetico
		while(loopsWoEvolution < maxLoops){
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
					//System.out.println("NEW BEST: " + bestAvaliation);
					bestSolution = solutions[nSolution];
					loopsWoEvolution = 0;
				}
				if(avaliations[nSolution] > bestGenerationAvaliation){
					bestGenerationAvaliation = avaliations[nSolution];
					bestGenerationIndex = nSolution;
				}
				//Debug
				//System.out.println(chromosomeToString(chromosomes[nSolution]));
				//System.out.println(avaliations[nSolution]);
			}

			// SELECAO
			selectProbs[0] = 0;
			//System.out.println("PROBS\n0");
			for(int nSolution = 1; nSolution <= populationSize; nSolution++){
				selectProbs[nSolution] = selectProbs[nSolution-1] + (avaliations[nSolution-1] / totalAvaliation);
				//System.out.println(selectProbs[nSolution]);
			}

			BitSet[] tmpChromosomes = new BitSet[populationSize];
			for(int nSolution = 0; nSolution < eliteSelection; nSolution++){
				tmpChromosomes[nSolution] = chromosomes[bestGenerationIndex];
			}
			for(int nSolution = eliteSelection; nSolution < (populationSize - eliteSelection + 1); nSolution++){
				tmpChromosomes[nSolution] = chromosomes[selectRandomIndex(selectProbs)];
			}
			chromosomes = tmpChromosomes;

			// EMPARELHAMENTO E CROSSOVER
			List<Integer> pairList = IntStream.rangeClosed(0, populationSize - 1)
					.boxed().collect(Collectors.toList());
			Random r = new Random();
			int index1, value1, index2, value2 = 0;
			BitSet crossoverChromosomes[] = new BitSet[2];
			tmpChromosomes = new BitSet[populationSize];
			for(int nSolution = 0; nSolution < populationSize; nSolution+=2){
				if(pairList.size() >= 2){
					index1 = r.nextInt(pairList.size());
					value1 = pairList.get(index1);
					pairList.remove(index1);
					index2 = r.nextInt(pairList.size());
					value2 = pairList.get(index2);
					pairList.remove(index2);
				} else {
					break;
				}
				//System.out.println(pairList);
				crossoverChromosomes = crossover(chromosomes[value1],chromosomes[value2]);
				tmpChromosomes[nSolution] = crossoverChromosomes[0];
				tmpChromosomes[nSolution+1] = crossoverChromosomes[1];
			}
			chromosomes = tmpChromosomes;
			
			//MUTACAO
			for(int nSolution = 0; nSolution < populationSize; nSolution++){
				chromosomes[nSolution] = mutate(chromosomes[nSolution]);
			}
			System.out.println(bestAvaliation);

		}
		return bestSolution;
	}

	private static BitSet[] crossover(BitSet chromosome1, BitSet chromosome2) {
		BitSet[] result = {new BitSet(nBitsTotal), new BitSet(nBitsTotal)};
		Random r = new Random();

		for(int i = 0; i < nBitsTotal; i+=nBitsPerTable){
			if(r.nextInt(100) < CROSSOVER_PROB){
				for(int j= 0; j < nBitsPerTable; j++){
					result[0].set(i + j,chromosome1.get(i + j));
					result[1].set(i + j,chromosome2.get(i + j));
				}
			} else {
				for(int j= 0; j < nBitsPerTable; j++){
					result[0].set(i + j,chromosome2.get(i + j));
					result[1].set(i + j,chromosome1.get(i + j));
				}
			}
		}

		return result;
	}

	private static int selectRandomIndex(double[] selectProbs) {
		double r = Math.random();
		//int aux = (int) Math.floor(selectProbs.length/2);
		//int index = aux;
		int index = 0;
		//System.out.println(r);
		while(r < selectProbs[index] || r >= selectProbs[index + 1]){
			index++;
			/*if(r < selectProbs[index]){
				index -= aux;
			} else if (r >= selectProbs[index + 1]){
				index += aux;
			}
			aux /= 2;
			System.out.println(aux);*/
		}
		//System.out.println(index);
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
