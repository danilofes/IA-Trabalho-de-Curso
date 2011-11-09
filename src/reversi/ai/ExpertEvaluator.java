package reversi.ai;

import reversi.core.Board;
import reversi.core.Coordinates;
import reversi.core.GameState;
import reversi.core.Player;

public class ExpertEvaluator implements StateEvaluator{
	
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
	
	public int evaluate(GameState state, Player player) {
		int myScore = 0;
		int opponentScore = 0;		
		
		if (state.isGameOver()){
			myScore = state.getScore(player);
			opponentScore = state.getScore(player.getOpponent());
			if (myScore == opponentScore) return 0;
			else if (myScore > opponentScore) return Integer.MAX_VALUE - opponentScore;
			else return Integer.MIN_VALUE + myScore;
		}
		else {
			Coordinates c = new Coordinates(0, 0);
			for (c.row = 0; c.row < Board.SIZE; c.row++) {
				for (c.column = 0; c.column < Board.SIZE; c.column++) {
					byte cell = state.getBoard().get(c);
					
					if (cell == player.COLOR){
						myScore += cellValue[c.row][c.column];
						//if (!state.getBoard().isInternal(c)){
						//	myScore--;
						//}
					}
					else if (cell == player.getOpponent().COLOR){
						myScore -= cellValue[c.row][c.column];
						//opponentScore += cellValue[c.row][c.column];
						//if (!state.getBoard().isInternal(c)){
						//	opponentScore--;
						//}
					}		
				}
			}
			
			int mobility = state.getPossibleMoves(player).size() - state.getPossibleMoves(player.getOpponent()).size();
			myScore += mobility * 10;
			
			//myScore += state.getPossibleMoves(player).size();
			//opponentScore += state.getPossibleMoves(player.getOpponent()).size();
			
			return myScore;
		}		
	}

}
