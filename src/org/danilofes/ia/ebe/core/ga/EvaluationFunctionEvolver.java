package org.danilofes.ia.ebe.core.ga;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import org.danilofes.ia.ebe.core.GameState;
import org.danilofes.ia.ebe.core.Parameter;
import org.danilofes.ia.ebe.core.StateEvaluator;
import org.danilofes.ia.ebe.core.StateEvaluatorFactory;
import org.danilofes.util.BitString;


public class EvaluationFunctionEvolver {
	
	private int popSize;
	private int maxGenerations;

	public EvaluationFunctionEvolver(int popSize, int maxGenerations) {
		this.popSize = popSize;
		this.maxGenerations = maxGenerations;
	}

	public void run(StateEvaluatorFactory<? extends GameState<?>> evaluatorFactory, Comparator<BitString> comparator) {

		List<Parameter> parameters = evaluatorFactory.getParameters();
		int length = this.computeBitStringLength(parameters);
		
		Collection<BitString> currentPopulation = this.generateInitialPopulation(length);
		
		CrossoverOperator co = new SinglePointCrossoverOperator();
		MutationOperator mo = new BitInversionOperator();
		PlayoffPopulationRanker ranker = new PlayoffPopulationRanker(comparator);
		
		PopulationEvolver evolver = new PopulationEvolver(co, mo);
		
		for (int generation = 0; generation < this.maxGenerations; generation++) {
			ranker.setPopulation(currentPopulation);
			
			//this.printGeneration(evaluatorFactory, ranker, generation);
			
			this.computeStatistics(evaluatorFactory, ranker, generation);

			currentPopulation = evolver.evolve(ranker);
		} 
	}

	private void computeStatistics(StateEvaluatorFactory<? extends GameState<?>> factory, PlayoffPopulationRanker ranker, int generation) {
		int params = factory.getParameters().size();
		
		double[] avg = new double[params];
		double[] std = new double[params];
		
		ArrayList<StateEvaluator<? extends GameState<?>>> evals = new ArrayList<StateEvaluator<? extends GameState<?>>>(ranker.getSize());
		for (int i = 0; i < ranker.getSize(); i++) {
			evals.add(factory.getEvaluator(ranker.selectByRank(i)));
		}
		
		for (int i = 0; i < params; i++) {
			avg[i] = 0.0;
			for (StateEvaluator<? extends GameState<?>> eval : evals) {
				avg[i] += (double) eval.getValue(i);
			}
			avg[i] = avg[i] / (double) this.popSize;
			
		}
		
		System.out.println();
		
		for (int i = 0; i < params; i++) {
			std[i] = 0.0;
			for (StateEvaluator<? extends GameState<?>> eval : evals) {
				std[i] += Math.pow((double) eval.getValue(i) - avg[i], 2);
			}
			std[i] = Math.sqrt(std[i] / (double) this.popSize);
		}

		for (int i = 0; i < params; i++) {
			System.out.println(String.format("best[%d]: %d\t%d", i, generation, evals.get(0).getValue(i)));
		}
		for (int i = 0; i < params; i++) {
			System.out.println(String.format("avg[%d]: %d\t%f\t%f", i, generation, avg[i], std[i]));
		}

	}

	private void printGeneration(StateEvaluatorFactory<? extends GameState<?>> evaluatorFactory, PlayoffPopulationRanker ranker, int generation) {
		System.out.println();
		System.out.println("### Generation " + generation + " ###");
		for (int i = 0; i < ranker.getSize(); i++) {
			System.out.println(evaluatorFactory.getEvaluator(ranker.selectByRank(i)).toString());
		}
		System.out.println();
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
