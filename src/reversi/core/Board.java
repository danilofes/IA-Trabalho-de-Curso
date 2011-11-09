package reversi.core;

public class Board implements Cloneable {

	public static final int SIZE = 8;

	public static final byte EMPTY = 0;	
	public static final byte LIGHT = 1;
	public static final byte DARK = -LIGHT;
	
	byte[][] cell = new byte[SIZE][SIZE];
	byte[][] neighbNumber = new byte[SIZE][SIZE];
	int darkScore;
	int lightScore;
	
	public Board(){
		
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
		insert(Player.LIGHT, new Coordinates(3,3));
		insert(Player.LIGHT, new Coordinates(4,4));
		insert(Player.DARK, new Coordinates(3,4));
		insert(Player.DARK, new Coordinates(4,3));
	}

	public byte get(Coordinates c){
		return cell[c.row][c.column];
	}

	public void insert(Player p, Coordinates c){
		cell[c.row][c.column] = p.COLOR;
		if (p.COLOR == LIGHT) lightScore++; else darkScore++;		
		// update borders
		for (int[] direction : Coordinates.DIRECTIONS) {
			Coordinates neighbor = new Coordinates(c.row, c.column);
			neighbor.moveTo(direction);
			if (neighbor.isValid()){
				neighbNumber[neighbor.row][neighbor.column]++;
			}
		}
	}
	
	boolean processMove(Move move, boolean updateBoard){
		int flippedPieces = 0;
		for (int[] direction : Coordinates.DIRECTIONS){
			Coordinates neighbor = new Coordinates(move.coordinates.row, move.coordinates.column);
			neighbor.moveTo(direction);			
			// enquanto o vizinho nesta direcaoo eh uma peca do adversario pula ...
			int enemyPiecesFound = 0;
			while (neighbor.isValid() && move.player.COLOR == -cell[neighbor.row][neighbor.column]){
				// indica que passou aqui pelo menos uma vez
				enemyPiecesFound++;
				neighbor.moveTo(direction);
			}
			// o while parou, agora tem que ver se parou numa peca do jogador
			if ((enemyPiecesFound > 0) && neighbor.isValid() && move.player.COLOR == cell[neighbor.row][neighbor.column]){
				if (updateBoard){
					// Volta pra traz virando as pecas do adversario
					for (; enemyPiecesFound > 0; enemyPiecesFound--){
						neighbor.moveToOposite(direction);
						cell[neighbor.row][neighbor.column] = (byte)-cell[neighbor.row][neighbor.column];
						flippedPieces++;						
					}
				}
				else return true;
			}
		}
		if (updateBoard){
			if (move.player.COLOR == LIGHT){
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

	public boolean isEmpty(Coordinates c){
		return cell[c.row][c.column] == EMPTY;
	}

	public boolean isInternal(Coordinates c){
		if (neighbNumber[c.row][c.column] == 8){
			return true;
		}
		else{
			if (c.row == 0 || c.row == SIZE-1 || c.column == 0 || c.column == SIZE-1){
				if (c.row == c.column || c.row + c.column == SIZE-1){
					return true;
				}
				else if (neighbNumber[c.row][c.column] == 5) return true;
			}
		}
		return false;
	}

	public Player getPlayer(Coordinates c) throws RuntimeException {
		if (cell[c.row][c.column] != EMPTY){
			return cell[c.row][c.column] == DARK ? Player.DARK : Player.LIGHT;
		}
		return null;
	}

	public Board clone(){
		Board clonedBoard = new Board();
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



