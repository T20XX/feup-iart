package algorithms;

import java.util.BitSet;
import java.util.Random;

import entities.Group;
import entities.Person;
import entities.Table;
import main.Dinner;

public class GeneticAlgorithm {

	public static final Table[] execute(){
		//TODO receber config por parametros
		int nBitsPerTable = (int) Math.floor(Math.log(Dinner.emptyTables.length)/Math.log(2) + 1 );
		int nBitsTotal = nBitsPerTable * Dinner.people.length;
		BitSet chromosomes[] = randomInitialPopulation(4, nBitsTotal);
		for(BitSet chromosome : chromosomes){
			System.out.println(chromosome.toString());
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

	}
