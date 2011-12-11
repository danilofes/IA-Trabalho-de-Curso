package org.danilofes.ia.ebe.core.ga;

import java.util.Collection;

import org.danilofes.util.BitString;

public interface PopulationRanker {
	
	void setPopulation(Collection<BitString> population);
	
	BitString selectByRank(int rank);
	
	BitString selectByProbability();

}
