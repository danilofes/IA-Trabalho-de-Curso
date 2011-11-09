package reversi.ai;

import reversi.core.GameState;
import reversi.core.Player;

public class NoobEvaluator implements StateEvaluator{
		
	public int evaluate(GameState state, Player player) {
		int scoreDiff = state.getScore(player) - state.getScore(player.getOpponent());
		if (state.isGameOver()){
			if (scoreDiff > 0) return Integer.MAX_VALUE;
			else if (scoreDiff < 0) return Integer.MIN_VALUE;
			else return 0;
		}
		else return scoreDiff;
	}

}
