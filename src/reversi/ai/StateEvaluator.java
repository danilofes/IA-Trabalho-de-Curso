package reversi.ai;

import reversi.core.GameState;
import reversi.core.Player;

public interface StateEvaluator {
	
	public int evaluate(GameState state, Player player);
	
}
