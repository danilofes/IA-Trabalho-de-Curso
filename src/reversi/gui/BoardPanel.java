package reversi.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JPanel;

import reversi.core.Coordinates;
import reversi.core.GameState;
import reversi.core.Move;
import reversi.core.Player;


public class BoardPanel extends JPanel{
	
	private static final int CELL_SIZE = 40;
	private static final int BOARD_MARGIN = 30;
	private static final int LINE_WIDTH = 1;
	private static final int CELLS_NUM = 8;
	private static final int CELL_MARGIN = 4;
	private static final int BOARD_SIZE = CELLS_NUM*(CELL_SIZE + LINE_WIDTH) + LINE_WIDTH;
	private static final int PANEL_SIZE = BOARD_SIZE + 2*BOARD_MARGIN;
	
	private static final long serialVersionUID = 1L;
	
	private GameState gameState;
	private Coordinates mouseOver = null;
	
	/**
	 * This is the default constructor
	 */
	public BoardPanel() {
		super();
		this.gameState = GameState.getInstance();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setPreferredSize(new Dimension(PANEL_SIZE, PANEL_SIZE));
		this.setLayout(new GridBagLayout());
		this.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				Coordinates mouse = getCoordinatesFromMouse(e.getX(), e.getY());				
				if (mouse != null) gameState.getNextPlayer().listenToMouse(mouse);
			}
		});
		this.addMouseMotionListener(new MouseMotionAdapter(){
			public void mouseMoved(MouseEvent e){
				Coordinates mouse = getCoordinatesFromMouse(e.getX(), e.getY());
				if (mouseOver == null || !mouseOver.equals(mouse)){
					mouseOver = mouse;
					repaint();
				}
			}
		});
		this.setVisible(true);
		
	}
	
	private Coordinates getCoordinatesFromMouse(int x, int y){
		if (x >= BOARD_MARGIN && x < BOARD_MARGIN + BOARD_SIZE - 1 && 
			y >= BOARD_MARGIN && y < BOARD_MARGIN + BOARD_SIZE - 1){
			int column = (x - BOARD_MARGIN) / (CELL_SIZE + LINE_WIDTH);
			int row = (y - BOARD_MARGIN) / (CELL_SIZE + LINE_WIDTH);
			return new Coordinates(row, column);
		}
		return null;
	}
	
	public void paint(Graphics g){
		Graphics2D g2D = (Graphics2D)g;		
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		Color boardColor = new Color(0.0f, 0.6f, 0.0f);
		Color marginColor = new Color(0.5f, 0.25f, 0.0f);
		Color darkGreen = new Color(0.0f, 0.5f, 0.0f);
		Color lightGreen = new Color(0.3f, 0.7f, 0.3f);
		Font font = new Font("SansSerif", Font.PLAIN, 20);
		g2D.setFont(font);
		
		String[] letter = {"A", "B", "C", "D", "E", "F", "G", "H"};
		
		g2D.setColor(marginColor);
		g2D.fillRect(1, 1, PANEL_SIZE - 2, PANEL_SIZE - 2);
		
		g2D.setColor(Color.BLACK);
		g2D.drawRect(0, 0, PANEL_SIZE - 1, PANEL_SIZE - 1);
				
		g2D.setColor(boardColor);
		g2D.fillRect(BOARD_MARGIN, BOARD_MARGIN, BOARD_SIZE, BOARD_SIZE);
		
		for (int i = 0; i < CELLS_NUM; i++){
			for (int j = 0; j < CELLS_NUM; j++){
				
				g2D.setColor(Color.BLACK);
				g2D.drawRect(BOARD_MARGIN + j*(LINE_WIDTH + CELL_SIZE), 
						   BOARD_MARGIN + i*(LINE_WIDTH + CELL_SIZE), 
						   CELL_SIZE + 2*LINE_WIDTH - 1, 
						   CELL_SIZE + 2*LINE_WIDTH - 1);
				
				if (!(gameState.getBoard().isEmpty(new Coordinates(i, j)))){
					if (Player.LIGHT.equals(gameState.getBoard().getPlayer(new Coordinates(i, j)))) g2D.setColor(Color.WHITE);
					else g2D.setColor(Color.BLACK);
					g2D.fillOval(BOARD_MARGIN + j*(LINE_WIDTH + CELL_SIZE) + LINE_WIDTH + CELL_MARGIN, 
							   BOARD_MARGIN + i*(LINE_WIDTH + CELL_SIZE) + LINE_WIDTH + CELL_MARGIN, 
							   CELL_SIZE - 2*CELL_MARGIN, 
							   CELL_SIZE - 2*CELL_MARGIN);
				}
			}			
			g.setColor(Color.YELLOW);
			g.drawString((i + 1) + "", BOARD_MARGIN/2 - 7, BOARD_MARGIN + i*(LINE_WIDTH + CELL_SIZE) + CELL_SIZE/2 + 9);
			g.drawString((i + 1) + "", PANEL_SIZE - 22, BOARD_MARGIN + i*(LINE_WIDTH + CELL_SIZE) + CELL_SIZE/2 + 9);
			g.drawString(letter[i], BOARD_MARGIN + i*(LINE_WIDTH + CELL_SIZE) + CELL_SIZE/2 - 9, BOARD_MARGIN/2 + 9);
			g.drawString(letter[i], BOARD_MARGIN + i*(LINE_WIDTH + CELL_SIZE) + CELL_SIZE/2 - 9, PANEL_SIZE - 6);
		}
		Player player = gameState.getNextPlayer();
		if (mouseOver != null && player != null && player.isHuman()){
			if (gameState.isValid(new Move(player, mouseOver))){
				if (Player.LIGHT.equals(player)) g2D.setColor(lightGreen);
				else g2D.setColor(darkGreen);
				g2D.fillOval(BOARD_MARGIN + mouseOver.getColumn()*(LINE_WIDTH + CELL_SIZE) + LINE_WIDTH + CELL_MARGIN, 
						   BOARD_MARGIN + mouseOver.getRow()*(LINE_WIDTH + CELL_SIZE) + LINE_WIDTH + CELL_MARGIN, 
						   CELL_SIZE - 2*CELL_MARGIN, 
						   CELL_SIZE - 2*CELL_MARGIN);
			}			
		}
	}

}
