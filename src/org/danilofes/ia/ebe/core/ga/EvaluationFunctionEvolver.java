package org.danilofes.ia.ebe.core.ga;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import org.danilofes.ia.ebe.core.GameState;
import org.danilofes.ia.ebe.core.Parameter;
import org.danilofes.ia.ebe.core.StateEvaluatorFactory;
import org.danilofes.util.BitString;


public class EvaluationFunctionEvolver {
	
	private int popSize = 32;

	public void run(StateEvaluatorFactory<? extends GameState<?>> evaluatorFactory, Comparator<BitString> comparator) {

		List<Parameter> parameters = evaluatorFactory.getParameters();
		int length = this.computeBitStringLength(parameters);
		
		Collection<BitString> currentPopulation = this.generateInitialPopulation(length);
		
		CrossoverOperator co = new SinglePointCrossoverOperator();
		MutationOperator mo = new BitInversionOperator();
		PlayoffPopulationRanker ranker = new PlayoffPopulationRanker(comparator);
		
		PopulationEvolver evolver = new PopulationEvolver(ranker, co, mo);
		
		for (int i = 0; i < 10; i++) {
			currentPopulation = evolver.evolve(currentPopulation);
			
			System.out.println();
			System.out.println("### Generation " + i + " ###");
			for (BitString individual : currentPopulation) {
				System.out.println(evaluatorFactory.getEvaluator(individual).toString());
			}
			System.out.println();
		} 
	}

	private int computeBitStringLength(List<Parameter> parameters) {
		int length = 0;
		for (Parameter param : parameters) {
			length += param.getBitLength();
		}
		return length;
	}

	private Collection<BitString> generateInitialPopulation(int length) {
		Random random = new Random(System.currentTimeMillis());
		
		Collection<BitString> pop = new ArrayList<BitString>();
		
		for (int i = 0; i < this.popSize; i++) {
			BitString individual = new BitString(length);
			for (int j = 0; j < length; j++) {
				individual.set(j, random.nextBoolean());
			}
			pop.add(individual);
		}
		
		return pop;
	}

}
