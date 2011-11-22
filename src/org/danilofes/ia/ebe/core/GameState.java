package org.danilofes.ia.ebe.core;

import java.util.Collection;

public interface GameState<A extends GameAction> {

	Player getNextPlayer();

	Collection<A> getActions();

	GameState<A> apply(A action);

	boolean isFinal();
	
	Player getWinner();

}
