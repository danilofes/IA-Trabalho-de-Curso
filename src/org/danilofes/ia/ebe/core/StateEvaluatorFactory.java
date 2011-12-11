package org.danilofes.ia.ebe.core;

import java.util.List;

import org.danilofes.util.BitString;

public abstract class StateEvaluatorFactory<S extends GameState<?>> {

	public abstract StateEvaluator<S> getEvaluator(BitString paramValues);

	public abstract List<Parameter> getParameters();

	protected int[] decodeParams(BitString encodedValues) {
		
		List<Parameter> params = this.getParameters();
		int size = params.size();
		int[] paramValues = new int[size];
		
		int cursor = 0;
		for (int i = 0; i < size; i++) {
			Parameter param = params.get(i);
			int bitLength = param.getBitLength();
			int paramValue = encodedValues.getInt(cursor, bitLength);
			cursor += bitLength;
			paramValues[i] = paramValue;
		}
		
		return paramValues;
	}
}
