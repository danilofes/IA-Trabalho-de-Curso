package org.danilofes.ia.ebe.core.ga;

import org.danilofes.util.BitString;

public interface CrossoverOperator {

	BitString crossover(BitString s0, BitString s1);

}
