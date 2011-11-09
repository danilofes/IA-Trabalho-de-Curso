package reversi.core;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public abstract class Player {
	
	public static final int HUMAN = 0;
	public static final int COMPUTER_EASY = 1;
	public static final int COMPUTER_NORMAL = 2;
	public static final int COMPUTER_HARD = 3;
	
	private static final long TIMEOUT = 5000;
	
	public static Player DARK;
	public static Player LIGHT;
	
	private String name;
	private Player opponent;
	private Move nextMove = null;
	private Lock lock = new ReentrantLock();
	private Condition moveChosen = lock.newCondition();
	private boolean myTurn = false;
	private Condition myTurnToPlay = lock.newCondition();
	public final byte COLOR;
	
	public Player(String name, byte color){
		super();
		this.name = name;
		COLOR = color;
	}
	
	public void start(){
		// implementacao default nao faz nada
	}
	
	public void setOpponent(Player opponent){
		this.opponent = opponent;
	}
	
	public Player getOpponent(){
		return this.opponent;
	}
	
	public abstract boolean isHuman();
	
	public abstract boolean isComputer();
	
	public final Move getMove() throws InterruptedException{
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
	
	protected final void submitMove(Move move) throws InterruptedException{
		lock.lock();
		nextMove = move;
		moveChosen.signal();
		myTurn = false;
		lock.unlock();
	}
	
	public void die() {
		// implementacao default nao faz nada
	}
	
	public void listenToMouse(Coordinates c){
		// implementacao default nao faz nada
	}
	
	public String toString(){
		return this.name;
	}
	
}
