package org.danilofes.ia.ebe.othello.evaluator;

import java.util.ArrayList;
import java.util.List;

import org.danilofes.ia.ebe.core.Parameter;
import org.danilofes.ia.ebe.core.Player;
import org.danilofes.ia.ebe.core.StateEvaluator;
import org.danilofes.ia.ebe.othello.OthelloBoard;
import org.danilofes.ia.ebe.othello.OthelloState;
import org.danilofes.util.GridCoordinates;

public class OthelloStateEvaluator extends StateEvaluator<OthelloState> {

	private static final int PIECES = 0;
	private static final int CORNERS = 1;
	private static final int MOBILITY = 2;

	public static final List<Parameter> PARAMS = new ArrayList<Parameter>();
	
	static {
		PARAMS.add(new Parameter(8));
		PARAMS.add(new Parameter(8));
		PARAMS.add(new Parameter(8));
	}

	public OthelloStateEvaluator(int[] paramValues) {
		super(paramValues);
		if (paramValues.length != PARAMS.size()) {
			throw new IllegalArgumentException(String.format("paramValues.length is %d, but should be %d", paramValues.length, PARAMS.size()));
		}
	}
	
	@Override
	public int evaluate(OthelloState state, Player player) {
		
		if (state.isFinal()) {
			return state.getScore(player) - state.getScore(player.getOpponent());
		}
		
		int v = 0;
		
		v += this.getPiecesScore(state, player);
		
		v += this.getCornersScore(state, player); 
		
		v += this.getMobilityScore(state, player);
		
		return v;
	}

	private int getPiecesScore(OthelloState state, Player player) {
		int piecesDiff = state.getScore(player) - state.getScore(player.getOpponent());
		return this.getValue(PIECES) * piecesDiff;
	}

	private int getCornersScore(OthelloState state, Player player) {
		int cornersDiff = 0;
		
		cornersDiff += this.countPiece(state, player, 0, 0);
		cornersDiff += this.countPiece(state, player, 0, OthelloBoard.SIZE - 1);
		cornersDiff += this.countPiece(state, player, OthelloBoard.SIZE - 1, 0);
		cornersDiff += this.countPiece(state, player, OthelloBoard.SIZE - 1, OthelloBoard.SIZE - 1);
		
		return this.getValue(CORNERS) * cornersDiff;
	}

	private int countPiece(OthelloState state, Player player, int i, int j) {
		Player piece = state.getBoard().getPlayer(new GridCoordinates(i, j));
		if (piece == null) {
			return 0;
		}
		if (piece == player) {
			return 1;
		}
		return -1;
	}

	private int getMobilityScore(OthelloState state, Player player) {
		int mobilityDiff = state.getPossibleMoves(player).size() - state.getPossibleMoves(player.getOpponent()).size();
		return this.getValue(MOBILITY) * mobilityDiff;
	}

	@Override
	public String toString() {
		return String.format("(%d)PIE + (%d)COR + (%d)MOB", this.getValue(PIECES), this.getValue(CORNERS), this.getValue(MOBILITY));
	}
}
