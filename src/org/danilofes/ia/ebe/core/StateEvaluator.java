package org.danilofes.ia.ebe.core;

public interface StateEvaluator<S extends GameState<?>> {

	int evaluate(S state, Player player);

}
