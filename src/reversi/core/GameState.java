package reversi.core;

import java.util.ArrayList;
import java.util.List;

public class GameState {
	
	private int turnCount;
	private Board board;
	private Player nextPlayer;
	private boolean gameOver;
	private List<Move> possibleMoves = null;
	
	private GameState(){
		super();
	}
	
	private static GameState instance = null;
	
	public static GameState getInstance(){
		if (instance == null){
			instance = new GameState();
			instance.board = new Board();
		}		
		return instance;
	}
	
	public Player getNextPlayer() {
		return nextPlayer;
	}
	
	public int getTurnNumber() {
		return turnCount;
	}
	
	public Board getBoard(){
		return board;		
	}
	
	public void initialState(){
		turnCount = 0;
		board.initialConfiguration();
		gameOver = false;
		nextPlayer = Player.DARK;
	}
	
	public GameState newState(Move move){
		GameState newState = this.clone();
		newState.changeState(move);
		return newState;
	}
	
	public boolean isGameOver(){
		return this.gameOver;
	}
	
	public int getScore(Player player){
		if (player.COLOR == Board.LIGHT) return board.lightScore;
		else return board.darkScore;
	}
	
	public Player getWinner(){
		int darkScore = getScore(Player.DARK);
		int lightScore = getScore(Player.LIGHT);
		if (darkScore > lightScore){
			return Player.DARK;
		}
		else if (darkScore < lightScore){
			return Player.LIGHT;
		}
		else return null;
	}
	
	public void changeState(Move move){
		if (isValid(move)){
			board.insert(move.player, move.coordinates);
			turnCount++;
			board.processMove(move, true);
			//updateBorder(move);
			nextPlayer = nextPlayer.equals(Player.DARK) ? Player.LIGHT : Player.DARK;
			possibleMoves = null;
			possibleMoves = getPossibleMoves();
			if (possibleMoves.size() == 0){
				nextPlayer = nextPlayer.equals(Player.DARK) ? Player.LIGHT : Player.DARK;
				possibleMoves = null;
				possibleMoves = getPossibleMoves();
				if (possibleMoves.size() == 0){
					gameOver = true;
				}
			}
		}
		else throw new RuntimeException("Jogada invalida submetida: " + move.toString());
	}
	
	public boolean isValid(Move move){
		if (!gameOver && board.isEmpty(move.coordinates)){
			return board.processMove(move, false);
		}
		return false;
	}
	
	public List<Move> getPossibleMoves(){
		//if (possibleMoves == null) possibleMoves = getPossibleMoves(nextPlayer);
		possibleMoves = getPossibleMoves(nextPlayer);
		return possibleMoves;
	}
	
	public List<Move> getPossibleMoves(Player player){
		List<Move> possibleMoves = new ArrayList<Move>();
		for (int i = 0; i < Board.SIZE; i++) {
			for (int j = 0; j < Board.SIZE; j++) {
				if (board.cell[i][j] == Board.EMPTY && board.neighbNumber[i][j] > 0){
					Move move = new Move(player, new Coordinates(i, j));
					if (isValid(move)) possibleMoves.add(move);
				}
			}
		}
		return possibleMoves;
	}
	
	protected GameState clone(){
		GameState clonedState = new GameState();
		clonedState.board = this.board.clone();
		clonedState.gameOver = this.gameOver;
		clonedState.nextPlayer = this.nextPlayer;
		clonedState.possibleMoves = null;
		return clonedState;
	}
	
}
