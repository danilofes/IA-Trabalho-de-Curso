package reversi.gui;

import reversi.core.Coordinates;
import reversi.core.GameState;
import reversi.core.Move;
import reversi.core.Player;

public class HumanPlayer extends Player{
	
	public HumanPlayer(String name, byte color){
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
	public void listenToMouse(Coordinates c){
		Move myMove = new Move(this, c);
		if (GameState.getInstance().isValid(myMove)){
			try {
				submitMove(myMove);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
