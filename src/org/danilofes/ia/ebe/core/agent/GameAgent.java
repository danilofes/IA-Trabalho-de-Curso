package org.danilofes.ia.ebe.core.agent;

import org.danilofes.ia.ebe.core.GameAction;
import org.danilofes.ia.ebe.core.GameState;
import org.danilofes.ia.ebe.core.Player;

public interface GameAgent<A extends GameAction> {

	A chooseAction(GameState<A> state, Player player); 

}
