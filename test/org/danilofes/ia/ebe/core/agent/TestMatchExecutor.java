package org.danilofes.ia.ebe.core.agent;

import junit.framework.Assert;

import org.danilofes.ia.ebe.core.Player;
import org.danilofes.ia.ebe.othello.OthelloAction;
import org.danilofes.ia.ebe.othello.OthelloState;
import org.danilofes.ia.ebe.othello.evaluator.ExpertEvaluator;
import org.junit.Test;

public class TestMatchExecutor {

	@Test
	public void testExecuteMatch() {
		
		GameAgent<OthelloAction> agent = new MinimaxAgent<OthelloAction>(new ExpertEvaluator());
		
		OthelloState initialState = new OthelloState();
		MatchExecutor<OthelloAction> executor = new MatchExecutor<OthelloAction>(initialState);
		
		Player winner = executor.executeMatch(agent, agent);
		
		Assert.assertEquals(Player.PLAYER_2, winner);
	}

}
