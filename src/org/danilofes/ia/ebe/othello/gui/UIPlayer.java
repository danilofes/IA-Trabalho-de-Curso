package org.danilofes.ia.ebe.othello.gui;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.danilofes.ia.ebe.core.Player;
import org.danilofes.ia.ebe.othello.OthelloAction;
import org.danilofes.util.GridCoordinates;

public abstract class UIPlayer {
	
	public static final int HUMAN = 0;
	public static final int COMPUTER_EASY = 1;
	public static final int COMPUTER_NORMAL = 2;
	public static final int COMPUTER_HARD = 3;
	
	private static final long TIMEOUT = 5000;
	
	public static UIPlayer DARK;
	public static UIPlayer LIGHT;
	
	private String name;
	private UIPlayer opponent;
	private OthelloAction nextMove = null;
	private Lock lock = new ReentrantLock();
	private Condition moveChosen = lock.newCondition();
	private boolean myTurn = false;
	private Condition myTurnToPlay = lock.newCondition();
	public final Player COLOR;
	
	public UIPlayer(String name, Player color){
		super();
		this.name = name;
		COLOR = color;
	}
	
	public static UIPlayer getFromSlot(Player player) {
		if (player == Player.PLAYER_1) {
			return DARK;
		} else {
			return LIGHT;
		}
	}
	
	public void start(){
		// implementacao default nao faz nada
	}
	
	public void setOpponent(UIPlayer opponent){
		this.opponent = opponent;
	}
	
	public UIPlayer getOpponent(){
		return this.opponent;
	}
	
	public abstract boolean isHuman();
	
	public abstract boolean isComputer();
	
	public final OthelloAction getMove() throws InterruptedException{
		lock.lock();		
		try {
			nextMove = null;
			myTurn = true;
			myTurnToPlay.signal();
			//if (isComputer()){
			//	boolean done = moveChosen.await(TIMEOUT, TimeUnit.MILLISECONDS);
			//	if (!done) throw new RuntimeException("Tempo limite para jogada excedido.");
			//}
			//else {
				while (nextMove == null) moveChosen.await();
			//}
			
			return nextMove;
		}
		finally{
			lock.unlock();
		}
	}
	
	protected final void waitForTurn() throws InterruptedException{
		lock.lock();
		while (!myTurn) myTurnToPlay.await();
		lock.unlock();
	}
	
	protected final void submitMove(OthelloAction move) throws InterruptedException{
		lock.lock();
		nextMove = move;
		moveChosen.signal();
		myTurn = false;
		lock.unlock();
	}
	
	public void die() {
		// implementacao default nao faz nada
	}
	
	public void listenToMouse(GridCoordinates c){
		// implementacao default nao faz nada
	}
	
	public String toString(){
		return this.name;
	}
	
}
