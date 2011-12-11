package org.danilofes.ia.ebe.core;


public abstract class StateEvaluator<S extends GameState<?>> {

	private int[] paramValues; 

	public StateEvaluator(int[] paramValues) {
		this.paramValues = paramValues;
	}
	
	protected int getValue(int param) {
		return this.paramValues[param];
	}
	
	public abstract int evaluate(S state, Player player);

}
