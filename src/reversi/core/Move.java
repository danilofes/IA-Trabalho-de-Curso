package reversi.core;

import reversi.core.Player;

public class Move implements Cloneable, Comparable<Move>{
	
	public static final int moveValue[][] = {
		{ 99, -8,  8,  6,  6,  8, -8, 99}, 
		{ -8,-24, -4, -3, -3, -4,-24, -8}, 
		{  8, -4,  7,  4,  4,  7, -4,  8}, 
		{  6, -3,  4,  0,  0,  4, -3,  6}, 
		{  6, -3,  4,  0,  0,  4, -3,  6}, 
		{  8, -4,  7,  4,  4,  7, -4,  8}, 
		{ -8,-24, -4, -3, -3, -4,-24, -8}, 
		{ 99, -8,  8,  6,  6,  8, -8, 99}
	};
	
	Player player;
	Coordinates coordinates;
		
	public Move(Player player, Coordinates coordinates) {
		super();
		this.player = player;
		this.coordinates = coordinates;
	}
	
	public Player getPlayer() {
		return player;
	}

	public Coordinates getCoordinates() {
		return coordinates;
	}
	
	public Move clone(){
		return new Move(this.player, this.coordinates.clone());
	}
	
	public String toString(){
		return player + ": " + coordinates;
	}

	public int compareTo(Move arg0) {
		if (moveValue[coordinates.row][coordinates.column] > moveValue[arg0.coordinates.row][arg0.coordinates.column]){
			return 1;
		}
		else if (moveValue[coordinates.row][coordinates.column] < moveValue[arg0.coordinates.row][arg0.coordinates.column]){
			return -1;
		}
		else{
			return 0;
		}
	}
	
}
