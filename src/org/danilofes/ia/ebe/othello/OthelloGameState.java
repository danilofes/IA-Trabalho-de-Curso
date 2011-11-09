package org.danilofes.ia.ebe.othello;

import java.util.Collections;
import java.util.Set;

import org.danilofes.ia.ebe.core.GameState;

public class OthelloGameState implements GameState<OthelloGameAction> {

	@Override
	public boolean isFinal() {
		return true;
	}

	@Override
	public Set<OthelloGameAction> getActions() {
		return Collections.emptySet();
	}

	@Override
	public GameState<OthelloGameAction> apply(OthelloGameAction action) {
		// TODO Auto-generated method stub
		return null;
	}

}
