package reversi.ai;

import reversi.core.GameState;
import reversi.core.Player;
import reversi.core.Coordinates;

public class Evaluator3 implements StateEvaluator{
	
	public static final int middleGame = 48;
	
	public static final Coordinates[] corners = {	new Coordinates(0,0), 
		new Coordinates(0,7), 
		new Coordinates(7,0), 
		new Coordinates(7,7) };

	public static final Coordinates[] edges = {	new Coordinates(0,1),
						new Coordinates(0,6),	new Coordinates(1,0),
						new Coordinates(1,1),	new Coordinates(1,6),
						new Coordinates(1,7),	new Coordinates(6,0),
						new Coordinates(6,1),	new Coordinates(6,6),
						new Coordinates(6,7),	new Coordinates(7,1),
						new Coordinates(7,6) };
	
	/**
	 * Avalia quanto ao n�mero de pe�as, quanto aos cantos (corners) adquiridos e 
	 * quanto �s casas adjacentes aos cantos (edges). Para cada canto que o jogador 
	 * possuir, soma-se um valor para a avalia��o. Para cada canto que o jogador 
	 * advers�rio possuir subtrai-se um valor da avalia��o. Para cada edge que o 
	 * jogador adquirir, subtrai-se um valor da avalia��o. Para cada edge que o 
	 * jogador advers�rio possuir, soma-se um valor para a avalia��o. 
	 */
	public int evaluate (GameState state, Player player){

		if (state.isGameOver()) {
			if (state.getWinner() == null){
				return 0;
			}
			else if (state.getWinner().equals(player)) {
				return Integer.MAX_VALUE;
			}
			else {
				return Integer.MIN_VALUE;
			}
		}
		int PlayerScore = state.getScore(player);
		int OpositePlayerScore = state.getScore(player.getOpponent());
		int CornersScore = 0;
		int EdgesScore = 0;
		for (int i = 0; i < corners.length; i++){
			Player p = state.getBoard().getPlayer(corners[i]);
			if (p != null) {
				if (p.equals(player)) {
					CornersScore += 10;
				}
				else {
					CornersScore -= 10;
				}
			}
		}
		for (int i = 0; i < edges.length; i++) {
			Player p = state.getBoard().getPlayer(edges[i]);
			if (p != null){
				if (p.equals(player)) {
					EdgesScore -= 5;
				}
				else {
					EdgesScore += 5;
				}
			}
		}
		if (state.getTurnNumber() < middleGame) { //interessante ter poucas pe�as			 
			return OpositePlayerScore - PlayerScore + CornersScore + EdgesScore;
		}
		else { //interessante ter muitas pe�as
			return PlayerScore - OpositePlayerScore + CornersScore + EdgesScore;
		}		
	}
}