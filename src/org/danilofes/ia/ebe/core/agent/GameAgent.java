package org.danilofes.ia.ebe.core.agent;

import org.danilofes.ia.ebe.core.GameAction;
import org.danilofes.ia.ebe.core.GameState;

public interface GameAgent<A extends GameAction> {

	A chooseAction(GameState<A> state); 

}
