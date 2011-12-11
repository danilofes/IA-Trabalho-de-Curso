package org.danilofes.ia.ebe.core.ga;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.danilofes.util.BitString;

public class PopulationEvolver {

	private static final int ELITE_SIZE = 4;
	private final CrossoverOperator co;
	private final MutationOperator mo;
	
	public PopulationEvolver(CrossoverOperator co, MutationOperator mo) {
		this.co = co;
		this.mo = mo;
	}
	
	public Collection<BitString> evolve(PopulationRanker ranker) {
		int size = ranker.getSize();
		
		Collection<BitString> newPopulation = new ArrayList<BitString>();
		
		newPopulation.addAll(this.selectElite(ranker));
		
		while (newPopulation.size() < size) {
			newPopulation.add(this.generateOffspring(ranker));
		}
		
		return newPopulation;
	}

	protected BitString generateOffspring(PopulationRanker ranker) {
		BitString p0 = ranker.selectByProbability();
		BitString p1 = ranker.selectByProbability();
		BitString child = this.co.crossover(p0, p1);
		this.mo.mutate(child);
		return child;
	}

	protected Collection<BitString> selectElite(PopulationRanker ranker) {
		List<BitString> elite = new LinkedList<BitString>();
		for (int i = 0; i < ELITE_SIZE; i++) {
			elite.add(ranker.selectByRank(i));
		}
		return elite;
	}

}
