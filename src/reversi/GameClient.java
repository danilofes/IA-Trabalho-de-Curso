package reversi;

import javax.swing.JOptionPane;

import reversi.ai.AverageEvaluator;
import reversi.ai.ComputerPlayer;
import reversi.ai.ExpertEvaluator;
import reversi.ai.NoobEvaluator;
import reversi.core.Board;
import reversi.core.GameState;
import reversi.core.Player;
import reversi.gui.GameUI;
import reversi.gui.HumanPlayer;

public class GameClient extends Thread {
	
	private static final int UNSTARTED = 0;
	private static final int STARTED = 1;
	private static final int FINISHED = 2;
	
	public static final int HUMAN = 0;
	public static final int COMPUTER_EASY = 1;
	public static final int COMPUTER_NORMAL = 2;
	public static final int COMPUTER_HARD = 3;
	
	private static GameUI gameUI;
	private static GameState gameState;
	private static int darkPlayerType = Player.HUMAN;
	private static int lightPlayerType = Player.COMPUTER_NORMAL;
	private static int state = UNSTARTED;
	
	private static Thread currentGame;
	
	public static void newGame(){
		if (state == STARTED){
			currentGame.interrupt();
			try {
				currentGame.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}		
		state = STARTED;
		
		switch (lightPlayerType){
		case HUMAN:{
			Player.LIGHT = new HumanPlayer("Branco", Board.LIGHT);
			break;
		}
		case COMPUTER_EASY:{
			Player.LIGHT = new ComputerPlayer("Branco", Board.LIGHT, new NoobEvaluator());
			break;
		}
		case COMPUTER_NORMAL:{
			Player.LIGHT = new ComputerPlayer("Branco", Board.LIGHT, new AverageEvaluator());
			break;
		}
		case COMPUTER_HARD:{
			Player.LIGHT = new ComputerPlayer("Branco", Board.LIGHT, new ExpertEvaluator());
			break;
		}
		}
		
		switch (darkPlayerType){
		case HUMAN:{
			Player.DARK = new HumanPlayer("Preto", Board.DARK);
			break;
		}
		case COMPUTER_EASY:{
			Player.DARK = new ComputerPlayer("Preto", Board.DARK, new NoobEvaluator());
			break;
		}
		case COMPUTER_NORMAL:{
			Player.DARK = new ComputerPlayer("Preto", Board.DARK, new AverageEvaluator());
			break;
		}
		case COMPUTER_HARD:{
			Player.DARK = new ComputerPlayer("Preto", Board.DARK, new ExpertEvaluator());
			break;
		}
		}
				
		Player.DARK.setOpponent(Player.LIGHT);
		Player.LIGHT.setOpponent(Player.DARK);
		
		gameState.initialState();
		Player.DARK.start();
		Player.LIGHT.start();
		currentGame = new GameClient();
		currentGame.start();
	}
	
	public static void setDarkPlayer(int type){
		darkPlayerType = type;
	}
	
	public static void setLightPlayer(int type){
		lightPlayerType = type;
	}
	
	public static void exit(){
		System.exit(0);
	}
	
	public void run() {
		while (!gameState.isGameOver()){			
			try {
				if (interrupted()) throw new InterruptedException();
				gameUI.refresh("Esperando jogada de " + gameState.getNextPlayer() + "...");
				yield();
				gameState.changeState(gameState.getNextPlayer().getMove());
			} catch (InterruptedException e) {
				Player.DARK.die();
				Player.LIGHT.die();
				return;
			}
		}
		gameUI.refresh("Fim do Jogo.");
		Player winner = gameState.getWinner();
		String message;
		if (winner == null) message = "Empate!\n";
		else message = gameState.getWinner() + " ganhou!\n";
		message += Player.DARK + " " + gameState.getScore(Player.DARK) + " x " + 
			gameState.getScore(Player.LIGHT) + " " + Player.LIGHT;
		JOptionPane.showMessageDialog(gameUI, message, "Fim do Jogo", JOptionPane.INFORMATION_MESSAGE);
		state = FINISHED;
		return;
	}
	
	public static void main(String[] args){		
		gameState = GameState.getInstance();
		gameUI = new GameUI();
		gameUI.refresh("Escolha (Jogo > Novo) no menu para iniciar.");
	}
	
}