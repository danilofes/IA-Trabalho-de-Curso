package org.danilofes.ia.ebe.core.agent;

import org.danilofes.ia.ebe.core.GameAction;
import org.danilofes.ia.ebe.core.GameState;
import org.danilofes.ia.ebe.core.Player;

public class MatchExecutor<A extends GameAction> {

	final GameState<A> initialState;

	public MatchExecutor(GameState<A> initialState) {
		super();
		this.initialState = initialState;
	}

	public Player executeMatch(GameAgent<A> p1Agent, GameAgent<A> p2Agent) {
		GameState<A> state = this.initialState;
		while (!state.isFinal()) {
			Player nextPlayer = state.getNextPlayer();
			GameAgent<A> nextAgent = nextPlayer == Player.PLAYER_1 ? p1Agent : p2Agent; 
			A action = nextAgent.chooseAction(state, nextPlayer);
			state = state.apply(action);
		}
		return state.getWinner();
	}

}
