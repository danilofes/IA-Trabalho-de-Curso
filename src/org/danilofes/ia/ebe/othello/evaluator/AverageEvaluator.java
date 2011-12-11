package org.danilofes.ia.ebe.othello.evaluator;

import org.danilofes.ia.ebe.core.Player;
import org.danilofes.ia.ebe.othello.OthelloBoard;
import org.danilofes.ia.ebe.othello.OthelloState;
import org.danilofes.util.GridCoordinates;

public class AverageEvaluator extends AbstractOthelloStateEvaluator {
	
	public static final int cellValue[][] = {
		{ 99, -8,  8,  6,  6,  8, -8, 99}, 
		{ -8,-24, -4, -3, -3, -4,-24, -8}, 
		{  8, -4,  7,  4,  4,  7, -4,  8}, 
		{  6, -3,  4,  0,  0,  4, -3,  6}, 
		{  6, -3,  4,  0,  0,  4, -3,  6}, 
		{  8, -4,  7,  4,  4,  7, -4,  8}, 
		{ -8,-24, -4, -3, -3, -4,-24, -8}, 
		{ 99, -8,  8,  6,  6,  8, -8, 99}
	};
	
	public int evaluate(OthelloState state, Player player) {		
		int myScore = 0;
		int opponentScore = 0;
		
		if (state.isFinal()){
			myScore = state.getScore(player);
			opponentScore = state.getScore(player.getOpponent());
			if (myScore == opponentScore) return 0;
			else if (myScore > opponentScore) return Integer.MAX_VALUE - opponentScore;
			else return Integer.MIN_VALUE + myScore;
		}
		else {
			GridCoordinates c = new GridCoordinates(0, 0);
			for (c.row = 0; c.row < OthelloBoard.SIZE; c.row++) {
				for (c.col = 0; c.col < OthelloBoard.SIZE; c.col++) {
					byte cell = state.getBoard().get(c);
					if (cell == player.getValue()){
						myScore += cellValue[c.row][c.col];
					}
					else if (cell == player.getOpponent().getValue()){
						myScore -= cellValue[c.row][c.col];
					}		
				}
			}
			
			return myScore;
		}
	}

}
