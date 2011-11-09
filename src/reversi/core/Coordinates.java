package reversi.core;

/**
 * Esta classe representa a coordenada de uma posiï¿½ï¿½o do tabuleiro de Reversi.
 *   
 * @author Danilo
 */
public class Coordinates implements Cloneable{
	
	public static final int[] NORTH = {-1, 0};
	public static final int[] NORTHEAST = {-1, 1};
	public static final int[] EAST = {0, 1};
	public static final int[] SOUTHEAST = {1, 1};
	public static final int[] SOUTH = {1, 0};
	public static final int[] SOUTHWEST = {1, -1};
	public static final int[] WEST = {0, -1};
	public static final int[] NORTHWEST = {-1, -1};
	
	public static final int[][] DIRECTIONS = {NORTH, NORTHEAST, EAST, SOUTHEAST, SOUTH, SOUTHWEST, WEST, NORTHWEST};
	
	public byte row;
	public byte column;
	
	/**
	 * Contrutor que recebe a linha e coluna do tabuleiro. Os parï¿½metros devem estar
	 * no intervalo 0-7.
	 * @param row número da linha começando em zero.
	 * @param column número da coluna começando em zero.
	 */
	public Coordinates(int row, int column) {
		super();
		if (row < 0 || row > 7 || column < 0 || column > 7){
			throw new RuntimeException("Coodenada fora dos limites do tabuleiro: " + row + " " + column);
		}
		this.row = (byte)row;
		this.column = (byte)column;
	}
	
	/**
	 * Indica se a <code>Coordinate</code> estï¿½ dentro do tabuleiro.
	 * @return true se a coordenada ï¿½ vï¿½lida, falso caso contrï¿½rio.
	 */
	public boolean isValid(){
		if (row < 0 || row > 7 || column < 0 || column > 7) return false;
		else return true;
	}
	
	/**
	 * Recupera o nï¿½mero da coluna.
	 * @return o nï¿½mero da coluna.
	 */
	public int getColumn() {
		return column;
	}
	
	/**
	 * Define o nï¿½mero da coluna.
	 * @param column o nï¿½mero da coluna.
	 */
	public void setColumn(int column) {
		if (column < 0 || column > 7){
			throw new RuntimeException("Coluna fora dos limites do tabuleiro.");
		}
		this.column = (byte)column;
	}
	
	/**
	 * Recupera o nï¿½mero da linha.
	 * @return o nï¿½mero da linha.
	 */
	public int getRow() {
		return row;
	}
	
	/**
	 * Define o nï¿½mero da linha.
	 * @param row o nï¿½mero da linha.
	 */
	public void setRow(int row) {
		if (row < 0 || row > 7){
			throw new RuntimeException("Linha fora dos limites do tabuleiro.");
		}
		this.row = (byte)row;
	}
	
	/**
	 * Recupera uma lista com todas as <code>Coordinates</code> dos vizinhos.
	 * @return uma lista de <code>Coordinates</code>.
	 */
	/*public List<Coordinates> getNeighbors(){
		List<Coordinates> neighbors = new LinkedList<Coordinates>();
		Coordinates coordinates;
		for (int[] direction : DIRECTIONS){
			coordinates = getNeighbor(direction);
			if (coordinates != null) neighbors.add(coordinates);
		}
		return neighbors;
	}*/
	
	/**
	 * Muda para a coordenada do vizinho em uma dada direï¿½ï¿½o.
	 * @param direction a direï¿½ï¿½o do vizinho. Pode ser NORTH, NORTHEAST, EAST, SOUTHEAST, 
	 * SOUTH, SOUTHWEST, WEST ou NORTHWEST.
	 */
	public void moveTo(int[] direction){
		this.row = (byte)(row + direction[0]);
		this.column = (byte)(column + direction[1]);
	}
	
	/**
	 * Muda para a coordenada do vizinho na direï¿½ï¿½o oposta de uma dada direï¿½ï¿½o.
	 * @param direction a direï¿½ï¿½o oposta do vizinho. Pode ser NORTH, NORTHEAST, EAST, SOUTHEAST, 
	 * SOUTH, SOUTHWEST, WEST ou NORTHWEST.
	 */
	public void moveToOposite(int[] direction){
		this.row = (byte)(row - direction[0]);
		this.column = (byte)(column - direction[1]);
	}
	
	public boolean equals(Coordinates c){
		return (c != null && row == c.row && column == c.column);
	}
	
	public Coordinates clone(){
		return new Coordinates(this.row, this.column);
	}
	
	public String toString(){
		String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H"};
		return letters[column] + (row + 1);
	}
	
}
