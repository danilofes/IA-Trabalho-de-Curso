package org.danilofes.ia.ebe.core;

import java.util.Set;

public interface GameState<A extends GameAction> {

	boolean isFinal();

	Set<A> getActions();

	GameState<A> apply(A action);
}
