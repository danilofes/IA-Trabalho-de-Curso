package org.danilofes.ia.ebe.core.ga;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.danilofes.util.BitString;

public class PopulationEvolver {

	private static final int ELITE_SIZE = 4;
	private final PopulationRanker ranker;
	private final CrossoverOperator co;
	private final MutationOperator mo;
	
	public PopulationEvolver(PopulationRanker ranker, CrossoverOperator co, MutationOperator mo) {
		this.ranker = ranker;
		this.co = co;
		this.mo = mo;
	}
	
	public Collection<BitString> evolve(Collection<BitString> population) {
		int size = population.size();
		this.ranker.setPopulation(population);
		
		Collection<BitString> newPopulation = new ArrayList<BitString>();
		
		newPopulation.addAll(this.selectElite());
		
		while (newPopulation.size() < size) {
			newPopulation.add(this.generateOffspring());
		}
		
		return newPopulation;
	}

	protected BitString generateOffspring() {
		BitString p0 = this.ranker.selectByProbability();
		BitString p1 = this.ranker.selectByProbability();
		BitString child = this.co.crossover(p0, p1);
		this.mo.mutate(child);
		return child;
	}

	protected Collection<BitString> selectElite() {
		List<BitString> elite = new LinkedList<BitString>();
		for (int i = 0; i < ELITE_SIZE; i++) {
			elite.add(this.ranker.selectByRank(i));
		}
		return elite;
	}

}
