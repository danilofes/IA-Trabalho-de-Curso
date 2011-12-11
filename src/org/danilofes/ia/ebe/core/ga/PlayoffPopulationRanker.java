package org.danilofes.ia.ebe.core.ga;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Random;

import org.danilofes.util.BitString;

public class PlayoffPopulationRanker implements PopulationRanker {

	private ArrayList<BitString> population;
	int size;
	private Comparator<BitString> comparator;
	private Random random;
	
	public PlayoffPopulationRanker(Comparator<BitString> comparator) {
		this.comparator = comparator;
		this.random = new Random(System.currentTimeMillis());
	}

	@Override
	public int getSize() {
		return this.size;
	}
	
	@Override
	public void setPopulation(Collection<BitString> population) {
		this.size = population.size();
		assert this.size > 1;
		// Deve ser uma potência de 2.
		assert Integer.bitCount(this.size) == 1;
		
		this.population = new ArrayList<BitString>(this.size);
		this.population.addAll(population);
		
		this.runPlayoffs();
	}

	private void runPlayoffs() {
		int size = this.size;
		while (size > 1) {
			this.roundOf(size);
			size = size / 2;
		}
	}

	private void roundOf(int size) {
		System.out.println("Beggining round of " + size);
		int half = size/2;
		for (int i = 0; i < half; i++) {
			BitString p0 = this.population.get(i);
			BitString p1 = this.population.get(half + i);
			if (this.comparator.compare(p0, p1) < 0) {
				// Troca de posições para manter a ordem do ranking.
				this.population.set(i, p1);
				this.population.set(half + i, p0);
				
				// Replica o vencedor na lista para aumentar a probabilidade dele ser escolhido
				this.population.add(p1);
			} else {
				// Replica o vencedor na lista para aumentar a probabilidade dele ser escolhido
				this.population.add(p0);
			}
			
		}
	}
	
	@Override
	public BitString selectByRank(int rank) {
		return this.population.get(rank);
	}
	
	@Override
	public BitString selectByProbability() {
		return this.population.get(this.random.nextInt(this.population.size()));
	}

}
