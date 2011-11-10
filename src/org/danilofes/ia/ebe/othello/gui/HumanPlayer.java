package org.danilofes.ia.ebe.othello.gui;

import org.danilofes.ia.ebe.core.Player;
import org.danilofes.ia.ebe.othello.OthelloAction;
import org.danilofes.ia.ebe.othello.OthelloState;
import org.danilofes.util.GridCoordinates;


public class HumanPlayer extends UIPlayer{
	
	public HumanPlayer(String name, Player color){
		super(name, color);
	}
	
	@Override
	public boolean isComputer() {
		return false;
	}

	@Override
	public boolean isHuman() {
		return true;
	}
	
	@Override
	public void listenToMouse(GridCoordinates c){
		OthelloAction myMove = new OthelloAction(this.COLOR, c);
		if (OthelloState.getInstance().isValid(myMove)){
			try {
				submitMove(myMove);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
