package org.danilofes.ia.ebe.othello;

import java.util.Comparator;

import org.danilofes.ia.ebe.core.Player;
import org.danilofes.ia.ebe.core.StateEvaluator;
import org.danilofes.ia.ebe.core.StateEvaluatorFactory;
import org.danilofes.ia.ebe.core.agent.GameAgent;
import org.danilofes.ia.ebe.core.agent.MatchExecutor;
import org.danilofes.ia.ebe.core.agent.MinimaxAgent;
import org.danilofes.util.BitString;

public class OthelloEvaluatorComparator implements Comparator<BitString> {

	private StateEvaluatorFactory<OthelloState> factory;
	
	public OthelloEvaluatorComparator(StateEvaluatorFactory<OthelloState> factory) {
		this.factory = factory;
	}

	@Override
	public int compare(BitString o0, BitString o1) {
		
		StateEvaluator<OthelloState> evaluator0 = this.factory.getEvaluator(o0);
		GameAgent<OthelloAction> agent0 = new MinimaxAgent<OthelloAction>(evaluator0);
		StateEvaluator<OthelloState> evaluator1 = this.factory.getEvaluator(o1);
		GameAgent<OthelloAction> agent1 = new MinimaxAgent<OthelloAction>(evaluator1);
		
		OthelloState initialState = new OthelloState();
		MatchExecutor<OthelloAction> executor = new MatchExecutor<OthelloAction>(initialState);
		
		int outcome = 0;
		Player winner = executor.executeMatch(agent0, agent1);
		outcome += (winner == Player.PLAYER_1) ? 1 : -1;

		winner = executor.executeMatch(agent1, agent0);
		outcome += (winner == Player.PLAYER_1) ? -1 : 1;
		
		System.out.println(evaluator0.toString() + " vs " + evaluator1.toString() + " = " + outcome);
		return outcome;
	}

}
