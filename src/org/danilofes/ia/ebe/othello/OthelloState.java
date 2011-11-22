package org.danilofes.ia.ebe.othello;

import java.util.ArrayList;
import java.util.List;

import org.danilofes.ia.ebe.core.GameState;
import org.danilofes.ia.ebe.core.Player;
import org.danilofes.util.GridCoordinates;

public class OthelloState implements GameState<OthelloAction>{
	
	private int turnCount;
	private OthelloBoard board;
	private Player nextPlayer;
	private boolean gameOver;
	private List<OthelloAction> possibleMoves = null;
	
	public OthelloState(){
		board = new OthelloBoard();
		this.initialState();
	}
	
	private static OthelloState instance = null;
	
	public static OthelloState getInstance(){
		if (instance == null){
			instance = new OthelloState();
		}		
		return instance;
	}
	
	public Player getNextPlayer() {
		return nextPlayer;
	}
	
	public int getTurnNumber() {
		return turnCount;
	}
	
	public OthelloBoard getBoard(){
		return board;		
	}
	
	public void initialState(){
		turnCount = 0;
		board.initialConfiguration();
		gameOver = false;
		nextPlayer = Player.PLAYER_1;
	}
	
	public int getScore(Player player){
		if (player == Player.PLAYER_2) return board.lightScore;
		else return board.darkScore;
	}
	
	public Player getWinner(){
		if (!this.isFinal()) {
			throw new IllegalStateException();
		}
		
		int darkScore = getScore(Player.PLAYER_1);
		int lightScore = getScore(Player.PLAYER_2);
		if (darkScore > lightScore){
			return Player.PLAYER_1;
		}
		else if (darkScore < lightScore){
			return Player.PLAYER_2;
		}
		else return null;
	}
	
	public void changeState(OthelloAction move){
		if (isValid(move)){
			board.insert(move.player, move.coordinates);
			turnCount++;
			board.processMove(move, true);
			//updateBorder(move);
			nextPlayer = nextPlayer == Player.PLAYER_1 ? Player.PLAYER_2 : Player.PLAYER_1;
			possibleMoves = null;
			possibleMoves = getPossibleMoves();
			if (possibleMoves.size() == 0){
				nextPlayer = nextPlayer == Player.PLAYER_1 ? Player.PLAYER_2 : Player.PLAYER_1;
				possibleMoves = null;
				possibleMoves = getPossibleMoves();
				if (possibleMoves.size() == 0){
					gameOver = true;
				}
			}
		}
		else throw new RuntimeException("Jogada invalida submetida: " + move.toString());
	}
	
	public boolean isValid(OthelloAction move){
		if (!gameOver && board.isEmpty(move.coordinates)){
			return board.processMove(move, false);
		}
		return false;
	}
	
	public List<OthelloAction> getPossibleMoves(){
		//if (possibleMoves == null) possibleMoves = getPossibleMoves(nextPlayer);
		possibleMoves = getPossibleMoves(nextPlayer);
		return possibleMoves;
	}
	
	public List<OthelloAction> getPossibleMoves(Player player){
		List<OthelloAction> possibleMoves = new ArrayList<OthelloAction>();
		for (int i = 0; i < OthelloBoard.SIZE; i++) {
			for (int j = 0; j < OthelloBoard.SIZE; j++) {
				if (board.cell[i][j] == OthelloBoard.EMPTY && board.neighbNumber[i][j] > 0){
					OthelloAction move = new OthelloAction(player, new GridCoordinates(i, j));
					if (isValid(move)) possibleMoves.add(move);
				}
			}
		}
		return possibleMoves;
	}
	
	protected OthelloState clone(){
		OthelloState clonedState = new OthelloState();
		clonedState.board = this.board.clone();
		clonedState.gameOver = this.gameOver;
		clonedState.nextPlayer = this.nextPlayer;
		clonedState.possibleMoves = null;
		return clonedState;
	}
	
	@Override
	public boolean isFinal() {
		return this.gameOver;
	}

	@Override
	public List<OthelloAction> getActions() {
		return this.getPossibleMoves();
	}

	@Override
	public OthelloState apply(OthelloAction action) {
		OthelloState newState = this.clone();
		newState.changeState(action);
		return newState;
	}

}
