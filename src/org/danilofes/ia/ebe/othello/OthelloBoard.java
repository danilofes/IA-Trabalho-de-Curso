package org.danilofes.ia.ebe.othello;

import org.danilofes.ia.ebe.core.Player;
import org.danilofes.util.GridCoordinates;

public class OthelloBoard implements Cloneable {

	public static final int SIZE = 8;

	public static final byte EMPTY = 0;	
	public static final byte LIGHT = 1;
	public static final byte DARK = -LIGHT;
	
	byte[][] cell = new byte[SIZE][SIZE];
	byte[][] neighbNumber = new byte[SIZE][SIZE];
	int darkScore;
	int lightScore;
	
	public OthelloBoard(){
		
	}

	public void initialConfiguration(){
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				cell[i][j] = EMPTY;
				neighbNumber[i][j] = 0;
			}
		}
		darkScore = 0;
		lightScore = 0;
		insert(Player.PLAYER_2, new GridCoordinates(3,3));
		insert(Player.PLAYER_2, new GridCoordinates(4,4));
		insert(Player.PLAYER_1, new GridCoordinates(3,4));
		insert(Player.PLAYER_1, new GridCoordinates(4,3));
	}

	public byte get(GridCoordinates c){
		return cell[c.row][c.col];
	}

	public void insert(Player player, GridCoordinates c){
		byte color = player.getValue();
		cell[c.row][c.col] = color;
		if (color == LIGHT) lightScore++; else darkScore++;		
		// update borders
		for (int[] direction : GridCoordinates.DIRECTIONS) {
			GridCoordinates neighbor = new GridCoordinates(c.row, c.col);
			neighbor.moveTo(direction);
			if (neighbor.isValid()){
				neighbNumber[neighbor.row][neighbor.col]++;
			}
		}
	}
	
	boolean processMove(OthelloAction move, boolean updateBoard){
		byte color = move.player.getValue();
		
		int flippedPieces = 0;
		for (int[] direction : GridCoordinates.DIRECTIONS){
			GridCoordinates neighbor = new GridCoordinates(move.coordinates.row, move.coordinates.col);
			neighbor.moveTo(direction);			
			// enquanto o vizinho nesta direcaoo eh uma peca do adversario pula ...
			int enemyPiecesFound = 0;
			while (neighbor.isValid() && color == -cell[neighbor.row][neighbor.col]){
				// indica que passou aqui pelo menos uma vez
				enemyPiecesFound++;
				neighbor.moveTo(direction);
			}
			// o while parou, agora tem que ver se parou numa peca do jogador
			if ((enemyPiecesFound > 0) && neighbor.isValid() && color == cell[neighbor.row][neighbor.col]){
				if (updateBoard){
					// Volta pra traz virando as pecas do adversario
					for (; enemyPiecesFound > 0; enemyPiecesFound--){
						neighbor.moveToOposite(direction);
						cell[neighbor.row][neighbor.col] = (byte)-cell[neighbor.row][neighbor.col];
						flippedPieces++;						
					}
				}
				else return true;
			}
		}
		if (updateBoard){
			if (color == LIGHT){
				lightScore += flippedPieces;
				darkScore -= flippedPieces;
			}
			else {
				lightScore -= flippedPieces;
				darkScore += flippedPieces;
			}
		}		
		if (flippedPieces > 0) return true;
		else return false;
	}

	public boolean isEmpty(GridCoordinates c){
		return cell[c.row][c.col] == EMPTY;
	}

	public boolean isInternal(GridCoordinates c){
		if (neighbNumber[c.row][c.col] == 8){
			return true;
		}
		else{
			if (c.row == 0 || c.row == SIZE-1 || c.col == 0 || c.col == SIZE-1){
				if (c.row == c.col || c.row + c.col == SIZE-1){
					return true;
				}
				else if (neighbNumber[c.row][c.col] == 5) return true;
			}
		}
		return false;
	}

	public Player getPlayer(GridCoordinates c) throws RuntimeException {
		if (cell[c.row][c.col] != EMPTY){
			return cell[c.row][c.col] == DARK ? Player.PLAYER_1 : Player.PLAYER_2;
		}
		return null;
	}

	public OthelloBoard clone(){
		OthelloBoard clonedBoard = new OthelloBoard();
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				clonedBoard.cell[i][j] = cell[i][j];
				clonedBoard.neighbNumber[i][j] = neighbNumber[i][j];
			}
		}
		clonedBoard.darkScore = darkScore;
		clonedBoard.lightScore = lightScore;
		return clonedBoard;		
	}

	public String toString(){
		StringBuffer buffer = new StringBuffer();
		for(int i = 0; i < SIZE; i++){
			for (int j = 0; j < SIZE; j++){
				switch (cell[i][j]){
				case EMPTY:
					buffer.append(".");
					break;
				case DARK:
					buffer.append("X");
					break;
				case LIGHT:
					buffer.append("O");
					break;
				}
			}
			buffer.append("\n");
		}

		for(int i = 0; i < SIZE; i++){
			for (int j = 0; j < SIZE; j++){
				buffer.append(neighbNumber[i][j]);
			}
			buffer.append("\n");
		}
		return buffer.toString();
	}	

}



