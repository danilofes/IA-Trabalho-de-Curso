package org.danilofes.ia.ebe.core;

import java.util.Collection;

public interface GameState<A extends GameAction> {

	boolean isFinal();

	Collection<A> getActions();

	GameState<A> apply(A action);
}
