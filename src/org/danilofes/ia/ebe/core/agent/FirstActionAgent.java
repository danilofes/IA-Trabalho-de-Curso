package org.danilofes.ia.ebe.core.agent;

import java.util.Iterator;

import org.danilofes.ia.ebe.core.GameAction;
import org.danilofes.ia.ebe.core.GameState;

public class FirstActionAgent<A extends GameAction> implements GameAgent<A> {

	@Override
	public A chooseAction(GameState<A> state) {
		Iterator<A> actions = state.getActions().iterator();
		if (actions.hasNext()) {
			return actions.next();
		}
		throw new UnsupportedOperationException("No actions to choose.");
	}

}
