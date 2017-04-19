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

	public static final Table[] execute(int initialPopulationSize){
		//TODO receber config por parametros
		nBitsPerTable = 3; //(int) Math.floor(Math.log(Dinner.emptyTables.length)/Math.log(2) + 1 );
		nBitsTotal = nBitsPerTable * 5;//Dinner.people.length;
		BitSet chromosomes[] = randomInitialPopulation(initialPopulationSize, nBitsTotal);
		for(BitSet chromosome : chromosomes){
			System.out.println(chromosome.toString());
			System.out.println(bitSetToInt(chromosome)[0]);
		}



			//TODO ciclo de algoritmo genético
			for(int i=0; i< 100; i++){

			}

			Table bestSolution[] = null;
			return bestSolution;
		}

		private static BitSet mutacao(BitSet cromossoma, int mutationIndex){
			BitSet cromossomaMutado = cromossoma;

			cromossomaMutado.flip(mutationIndex);

			return cromossomaMutado;
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
			Table[] filledTables = Dinner.emptyTables;
			int[] tables = bitSetToInt(chromosome);
			
			
			return filledTables;
		}
		private static int[] bitSetToInt(BitSet chromosome){
			int[] result = new int[5];;//Dinner.people.length];
			
			for(int i = 0; i < 5; i++){
				for(int j = 0; j < nBitsPerTable; j++){
					result[i] += chromosome.get(i*nBitsPerTable + j) ? (1L << j) : 0L;
				}
			}
			
			
			return result;
		}
			

	}
