package org.danilofes.ia.ebe.othello.gui;

import org.danilofes.ia.ebe.core.Player;
import org.danilofes.ia.ebe.core.StateEvaluator;
import org.danilofes.ia.ebe.core.agent.MinimaxAgent;
import org.danilofes.ia.ebe.othello.OthelloAction;
import org.danilofes.ia.ebe.othello.OthelloState;


public class ComputerPlayer extends UIPlayer implements Runnable{

	private OthelloState gameState = OthelloState.getInstance();
	private Thread me = null;

	private StateEvaluator<OthelloState> evaluator;

	public ComputerPlayer(String name, Player color, StateEvaluator evaluator){
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
	
	public void run() {
		try {
			while (!gameState.isFinal()){
				waitForTurn();
				
				MinimaxAgent<OthelloAction> agent = new MinimaxAgent<OthelloAction>(this.evaluator);
				OthelloAction action = agent.chooseAction(gameState, gameState.getNextPlayer());
				
				submitMove(action);
			}

		} catch (InterruptedException e) {
			return;
		}
	}


}
