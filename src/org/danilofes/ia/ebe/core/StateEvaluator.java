package org.danilofes.ia.ebe.core;

import java.util.List;


public abstract class StateEvaluator<S extends GameState<?>> {

	private int[] paramValues; 

	public StateEvaluator(int[] paramValues) {
		this.paramValues = paramValues;
	}
	
	public int getValue(int param) {
		return this.paramValues[param];
	}
	
	public abstract List<Parameter> getParameters();
	
	public abstract int evaluate(S state, Player player);

}
