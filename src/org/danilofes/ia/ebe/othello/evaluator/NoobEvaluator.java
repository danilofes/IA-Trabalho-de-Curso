package org.danilofes.ia.ebe.othello.evaluator;

import org.danilofes.ia.ebe.core.Player;
import org.danilofes.ia.ebe.core.StateEvaluator;
import org.danilofes.ia.ebe.othello.OthelloState;

public class NoobEvaluator implements StateEvaluator<OthelloState> {

	public int evaluate(OthelloState state, Player player) {
		int scoreDiff = state.getScore(player) - state.getScore(player.getOpponent());
		if (state.isGameOver()){
			if (scoreDiff > 0) return Integer.MAX_VALUE;
			else if (scoreDiff < 0) return Integer.MIN_VALUE;
			else return 0;
		}
		else return scoreDiff;
	}

}
