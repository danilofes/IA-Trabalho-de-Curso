package org.danilofes.util;

public class GridCoordinates implements Cloneable{
	
	public static final int[] NORTH = {-1, 0};
	public static final int[] NORTHEAST = {-1, 1};
	public static final int[] EAST = {0, 1};
	public static final int[] SOUTHEAST = {1, 1};
	public static final int[] SOUTH = {1, 0};
	public static final int[] SOUTHWEST = {1, -1};
	public static final int[] WEST = {0, -1};
	public static final int[] NORTHWEST = {-1, -1};
	
	public static final int[][] DIRECTIONS = {NORTH, NORTHEAST, EAST, SOUTHEAST, SOUTH, SOUTHWEST, WEST, NORTHWEST};
	
	public int row;
	public int col;
	
	public GridCoordinates(int row, int column) {
		super();
		if (row < 0 || row > 7 || column < 0 || column > 7){
			throw new RuntimeException("Coodenada fora dos limites do tabuleiro: " + row + " " + column);
		}
		this.row = row;
		this.col = column;
	}
	
	public boolean isValid(){
		if (row < 0 || row > 7 || col < 0 || col > 7) return false;
		else return true;
	}
	
	public int getColumn() {
		return col;
	}
	
	public void setColumn(int column) {
		if (column < 0 || column > 7){
			throw new RuntimeException("Coluna fora dos limites do tabuleiro.");
		}
		this.col = column;
	}
	
	public int getRow() {
		return row;
	}
	
	public void setRow(int row) {
		if (row < 0 || row > 7){
			throw new RuntimeException("Linha fora dos limites do tabuleiro.");
		}
		this.row = row;
	}
	
	public void moveTo(int[] direction){
		this.row = (row + direction[0]);
		this.col = (col + direction[1]);
	}
	
	public void moveToOposite(int[] direction){
		this.row = (row - direction[0]);
		this.col = (col - direction[1]);
	}
	
	public boolean equals(GridCoordinates c){
		return (c != null && row == c.row && col == c.col);
	}
	
	public GridCoordinates clone(){
		return new GridCoordinates(this.row, this.col);
	}
	
	public String toString(){
		String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H"};
		return letters[col] + (row + 1);
	}
	
}
