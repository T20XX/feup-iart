package algorithms;

import java.util.BitSet;

import entities.Group;
import entities.Person;
import entities.Table;
import main.Dinner;

public class GeneticAlgorithm {
	
	public static final Table[] execute(){
		//TODO receber config por parametros
		int nBitsPerTable = (int) Math.floor(Math.log(Dinner.emptyTables.length)/Math.log(2) + 1 );
		int nBitsTotal = nBitsPerTable * Dinner.people.length;
		BitSet individuo = new BitSet(nBitsTotal);
		individuo.set(0,20);
			System.out.println(mutacao(individuo,23));
		
		
		//TODO ciclo de algoritmo genético
		
		Table bestSolution[] = null;
		return bestSolution;
	}
	
	private static BitSet mutacao(BitSet cromossoma, int mutationIndex){
		BitSet cromossomaMutado = cromossoma;
		
		cromossomaMutado.flip(mutationIndex);
		
		return cromossomaMutado;
	}

}
