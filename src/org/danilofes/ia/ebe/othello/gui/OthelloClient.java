package org.danilofes.ia.ebe.othello.gui;

import javax.swing.JOptionPane;

import org.danilofes.ia.ebe.core.Player;
import org.danilofes.ia.ebe.othello.OthelloAction;
import org.danilofes.ia.ebe.othello.OthelloState;
import org.danilofes.ia.ebe.othello.evaluator.AverageEvaluator;
import org.danilofes.ia.ebe.othello.evaluator.ExpertEvaluator;
import org.danilofes.ia.ebe.othello.evaluator.NoobEvaluator;


public class OthelloClient extends Thread {
	
	private static final int UNSTARTED = 0;
	private static final int STARTED = 1;
	private static final int FINISHED = 2;
	
	public static final int HUMAN = 0;
	public static final int COMPUTER_EASY = 1;
	public static final int COMPUTER_NORMAL = 2;
	public static final int COMPUTER_HARD = 3;
	
	private static GameUI gameUI;
	private static OthelloState gameState;
	private static int darkPlayerType = UIPlayer.HUMAN;
	private static int lightPlayerType = UIPlayer.COMPUTER_NORMAL;
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
			UIPlayer.LIGHT = new HumanPlayer("Branco", Player.PLAYER_2);
			break;
		}
		case COMPUTER_EASY:{
			UIPlayer.LIGHT = new ComputerPlayer("Branco", Player.PLAYER_2, new NoobEvaluator());
			break;
		}
		case COMPUTER_NORMAL:{
			UIPlayer.LIGHT = new ComputerPlayer("Branco", Player.PLAYER_2, new AverageEvaluator());
			break;
		}
		case COMPUTER_HARD:{
			UIPlayer.LIGHT = new ComputerPlayer("Branco", Player.PLAYER_2, new ExpertEvaluator());
			break;
		}
		}
		
		switch (darkPlayerType){
		case HUMAN:{
			UIPlayer.DARK = new HumanPlayer("Preto", Player.PLAYER_1);
			break;
		}
		case COMPUTER_EASY:{
			UIPlayer.DARK = new ComputerPlayer("Preto", Player.PLAYER_1, new NoobEvaluator());
			break;
		}
		case COMPUTER_NORMAL:{
			UIPlayer.DARK = new ComputerPlayer("Preto", Player.PLAYER_1, new AverageEvaluator());
			break;
		}
		case COMPUTER_HARD:{
			UIPlayer.DARK = new ComputerPlayer("Preto", Player.PLAYER_1, new ExpertEvaluator());
			break;
		}
		}
				
		UIPlayer.DARK.setOpponent(UIPlayer.LIGHT);
		UIPlayer.LIGHT.setOpponent(UIPlayer.DARK);
		
		gameState.initialState();
		UIPlayer.DARK.start();
		UIPlayer.LIGHT.start();
		currentGame = new OthelloClient();
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
				OthelloAction nextMove = UIPlayer.getFromSlot(gameState.getNextPlayer()).getMove();
				gameState.changeState(nextMove);
			} catch (InterruptedException e) {
				UIPlayer.DARK.die();
				UIPlayer.LIGHT.die();
				return;
			}
		}
		gameUI.refresh("Fim do Jogo.");
		Player winner = gameState.getWinner();
		String message;
		if (winner == null) message = "Empate!\n";
		else message = gameState.getWinner() + " ganhou!\n";
		message += Player.PLAYER_1 + " " + gameState.getScore(Player.PLAYER_1) + " x " + 
			gameState.getScore(Player.PLAYER_2) + " " + Player.PLAYER_2;
		JOptionPane.showMessageDialog(gameUI, message, "Fim do Jogo", JOptionPane.INFORMATION_MESSAGE);
		state = FINISHED;
		return;
	}
	
	public static void main(String[] args){		
		gameState = OthelloState.getInstance();
		gameUI = new GameUI();
		gameUI.refresh("Escolha (Jogo > Novo) no menu para iniciar.");
	}
	
}