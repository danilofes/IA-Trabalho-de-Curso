package reversi.ai;

import java.util.List;

import reversi.core.GameState;
import reversi.core.Move;
import reversi.core.Player;

public class ComputerPlayer extends Player implements Runnable{

	private static final int MAX_DEPTH = 5;

	private GameState gameState = GameState.getInstance();
	private Thread me = null;

	private StateEvaluator evaluator;

	private Move bestMove;

	public ComputerPlayer(String name, byte color, StateEvaluator evaluator){
		super(name, color);
		this.evaluator = evaluator;
		me = new Thread(this);
	}

	public void start(){
		me.start();
	}

	@Override
	public boolean isComputer() {
		return true;
	}

	@Override
	public boolean isHuman() {
		return false;
	}

	public void die() {
		me.interrupt();
		try {
			me.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private int alphaBetaSearch(GameState state) throws InterruptedException{
		int v = maxValue(state, Integer.MIN_VALUE, Integer.MAX_VALUE, 0);
		return v;
	}
	
	private int maxValue(GameState state, int alpha, int beta, int depth) throws InterruptedException{
		if (Thread.interrupted()) throw new InterruptedException();
		if (depth >= MAX_DEPTH || state.isGameOver()){
			return evaluator.evaluate(state, this);			
		}
		int v = Integer.MIN_VALUE;
		int moveIndex = 0;
		List<Move> list = state.getPossibleMoves();
		//Collections.sort(list);
		for (int i = 0; i < list.size(); i++){
			int min = minValue(state.newState(list.get(i)), alpha, beta, depth + 1);
			if (min > v){
				v = min;
				moveIndex = i;
			}
			if (v >= beta) {
				if (depth == 0) bestMove = list.get(i);
				return v;
			}
			alpha = (v > alpha) ? v : alpha;
		}
		if (depth == 0) bestMove = list.get(moveIndex);
		return v;		
	}
	
	private int minValue(GameState state, int alpha, int beta, int depth) throws InterruptedException{
		if (Thread.interrupted()) throw new InterruptedException();
		if (depth >= MAX_DEPTH || state.isGameOver()){
			return evaluator.evaluate(state, this);
		}
		int v = Integer.MAX_VALUE;
		List<Move> list = state.getPossibleMoves();
		//Collections.sort(list);
		for (int i = 0; i < list.size(); i++){
			int max = maxValue(state.newState(list.get(i)), alpha, beta, depth + 1);
			v = (max < v) ? max : v;
			if (v <= alpha) return v;
			beta = (v < beta) ? v : beta;
		}
		return v;
	}
	
	public void run() {
		try {
			while (!gameState.isGameOver()){
				waitForTurn();
				// escolhe a jogada
				bestMove = null;
				int minimax = alphaBetaSearch(gameState);
				// escolhe a jogada	
				submitMove(bestMove);
			}

		} catch (InterruptedException e) {
			return;
		}
	}


}
