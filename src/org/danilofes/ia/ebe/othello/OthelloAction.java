package org.danilofes.ia.ebe.othello;

import org.danilofes.ia.ebe.core.GameAction;
import org.danilofes.ia.ebe.core.Player;
import org.danilofes.util.GridCoordinates;

public class OthelloAction implements GameAction, Cloneable, Comparable<OthelloAction>{

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
	GridCoordinates coordinates;
		
	public OthelloAction(Player player, GridCoordinates coordinates) {
		super();
		this.player = player;
		this.coordinates = coordinates;
	}
	
	public Player getPlayer() {
		return player;
	}

	public GridCoordinates getCoordinates() {
		return coordinates;
	}
	
	public OthelloAction clone(){
		return new OthelloAction(this.player, this.coordinates.clone());
	}
	
	public String toString(){
		return player + ": " + coordinates;
	}

	public int compareTo(OthelloAction arg0) {
		if (moveValue[coordinates.row][coordinates.col] > moveValue[arg0.coordinates.row][arg0.coordinates.col]){
			return 1;
		}
		else if (moveValue[coordinates.row][coordinates.col] < moveValue[arg0.coordinates.row][arg0.coordinates.col]){
			return -1;
		}
		else{
			return 0;
		}
	}
	
}
