package org.danilofes.ia.ebe.core.ga;

import java.util.Random;

import org.danilofes.util.BitString;

public class BitInversionOperator implements MutationOperator {

	private final Random random = new Random(System.currentTimeMillis());
	private final float mutationRatio;
	
	public BitInversionOperator() {
		this(0.06f);
	}
	
	public BitInversionOperator(float mutationRatio) {
		this.mutationRatio = mutationRatio;
	}
	
	@Override
	public void mutate(BitString bitString) {
		for (int i = 0; i < bitString.size(); i++) {
			if (this.random.nextFloat() <= this.mutationRatio) {
				bitString.set(i, !bitString.get(i));
			}
		}
	}

}
