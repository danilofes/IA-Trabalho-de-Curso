package org.danilofes.ia.ebe.core.agent;

import org.danilofes.ia.ebe.core.GameAction;
import org.danilofes.ia.ebe.core.GameState;
import org.danilofes.ia.ebe.core.Player;
import org.danilofes.ia.ebe.core.StateEvaluator;

public class MinimaxAgent<A extends GameAction> implements GameAgent<A> {

	private final int maxDepth = 5;
	private StateEvaluator<GameState<A>> evaluator;
	private A choosenAction;

	public MinimaxAgent(StateEvaluator<? extends GameState<A>> evaluator) {
		this.evaluator = (StateEvaluator<GameState<A>>) evaluator;
	}

	@Override
	public A chooseAction(GameState<A> state, Player player) {
		this.choosenAction = null;
		this.maxValue(state, player, Integer.MIN_VALUE, Integer.MAX_VALUE, 0);
		return this.choosenAction;
	};

	private int maxValue(GameState<A> state, Player player, int alpha, int beta, int depth) {
		if (this.terminalTest(state, depth)) {
			return this.evaluator.evaluate(state, player);
		}
		int v = Integer.MIN_VALUE;
		for (A action : state.getActions()) {
			GameState<A> nextState = state.apply(action);
			v = Math.max(v, this.minValue(nextState, player, alpha, beta, depth + 1));
			if (v >= beta) {
				return v;
			}
			if (v > alpha) {
				alpha = v;
				if (depth == 0) this.choosenAction = action;
			}
		}
		return v;
	}

	private int minValue(GameState<A> state, Player player, int alpha, int beta, int depth) {
		if (this.terminalTest(state, depth)) {
			return this.evaluator.evaluate(state, player);
		}
		int v = Integer.MAX_VALUE;
		for (A action : state.getActions()) {
			GameState<A> nextState = state.apply(action);
			v = Math.min(v, this.maxValue(nextState, player, alpha, beta, depth + 1));
			if (v <= alpha) {
				return v;
			}
			beta = Math.min(beta, v);
		}
		return v;
	}
	
	private boolean terminalTest(GameState<A> state, int depth) {
		return depth >= this.maxDepth || state.isFinal();
	}

}
