package org.danilofes.ia.ebe.core.ga;

import java.util.Random;

import org.danilofes.util.BitString;

public class SinglePointCrossoverOperator implements CrossoverOperator {

	private Random random = new Random(System.currentTimeMillis());

	@Override
	public BitString crossover(BitString s0, BitString s1) {
		int size = s0.size();
		assert size > 1;
		assert s1.size() == size;
		
		int point = this.random.nextInt(size - 2) + 1;
		
		BitString child = s0.clone();
		
		for (int i = point; i < size; i++) {
			child.set(i, s1.get(i));
		}
		
		return child;
	}

}
