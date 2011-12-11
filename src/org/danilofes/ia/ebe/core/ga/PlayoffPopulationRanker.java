package org.danilofes.ia.ebe.core.ga;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Random;

import org.danilofes.util.BitString;

public class PlayoffPopulationRanker implements PopulationRanker {

	private BitString[] population;
	private Comparator<BitString> comparator;
	private Random random;
	
	public PlayoffPopulationRanker(Comparator<BitString> comparator) {
		this.comparator = comparator;
		this.random = new Random(System.currentTimeMillis());
	}

	@Override
	public void setPopulation(Collection<BitString> population) {
		int size = population.size();
		assert size > 1;
		// Deve ser uma potência de 2.
		assert Integer.bitCount(size) == 1;
		
		this.population = new BitString[size];
		Iterator<BitString> iterator = population.iterator();
		for (int i = 0; iterator.hasNext(); i++) {
			this.population[i] = iterator.next();
		}
		
		this.runPlayoffs();
	}

	private void runPlayoffs() {
		int size = this.population.length;
		while (size > 1) {
			this.roundOf(size);
			size = size / 2;
		}
	}

	private void roundOf(int size) {
		System.out.println("Beggining round of " + size);
		int half = size/2;
		for (int i = 0; i < half; i++) {
			BitString p0 = this.population[i];
			BitString p1 = this.population[half + i];
			if (this.comparator.compare(p0, p1) < 0) {
				this.population[i] = p1;
				this.population[half + i] = p0;
			}
		}
	}
	
	@Override
	public BitString selectByRank(int rank) {
		return this.population[rank];
	}
	
	@Override
	public BitString selectByProbability() {
		return this.population[this.random.nextInt(this.population.length)];
	}

}
