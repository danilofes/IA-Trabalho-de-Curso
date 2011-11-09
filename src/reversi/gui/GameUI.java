package reversi.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;

import reversi.GameClient;
import reversi.core.Player;


public class GameUI extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;

	private JLabel statusBar = null;

	private JMenuBar gameMenu = null;

	private JMenu jMenuGame = null;

	private JMenuItem jMenuItemNovo = null;

	private JMenuItem jMenuItemExit = null;

	private JPanel boardPanel = null;

	private JMenu jMenuPlayers = null;

	private JMenu jMenuDarkPlayer = null;

	private JRadioButtonMenuItem jRadioButtonDarkHuman = null;

	private JMenu jMenuLightPlayer = null;

	private JRadioButtonMenuItem jRadioButtonLightHuman = null;

	private JRadioButtonMenuItem jRadioButtonDarkEasy = null;

	private JRadioButtonMenuItem jRadioButtonDarkNormal = null;

	private JRadioButtonMenuItem jRadioButtonDarkHard = null;

	private JRadioButtonMenuItem jRadioButtonLightEasy = null;

	private JRadioButtonMenuItem jRadioButtonLightNormal = null;

	private JRadioButtonMenuItem jRadioButtonLightHard = null;

	/**
	 * This is the default constructor
	 */
	public GameUI() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setJMenuBar(getGameMenu());
		this.setContentPane(getJContentPane());
		this.setSize(395, 463);
		this.setResizable(false);
		this.setTitle("Reversi");
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				GameClient.exit();
			}
		});
		this.setVisible(true);
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			statusBar = new JLabel();
			statusBar.setText("Status");
			statusBar.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);
			statusBar.setFont(new Font("Dialog", Font.PLAIN, 14));
			statusBar.setSize(statusBar.getPreferredSize());
			jContentPane = new JPanel();
			jContentPane.setLayout(new BoxLayout(getJContentPane(), BoxLayout.Y_AXIS));
			jContentPane.add(getBoardPanel(), null);
			jContentPane.add(statusBar, null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes gameMenu	
	 * 	
	 * @return javax.swing.JMenuBar	
	 */
	private JMenuBar getGameMenu() {
		if (gameMenu == null) {
			gameMenu = new JMenuBar();
			gameMenu.add(getJMenuGame());
			gameMenu.add(getJMenuPlayers());
		}
		return gameMenu;
	}

	/**
	 * This method initializes jMenuGame	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getJMenuGame() {
		if (jMenuGame == null) {
			jMenuGame = new JMenu();
			jMenuGame.setName("");
			jMenuGame.setText("Jogo");
			jMenuGame.setPreferredSize(new Dimension(40, 15));
			jMenuGame.add(getJMenuItemNovo());
			jMenuGame.add(getJMenuItemExit());
		}
		return jMenuGame;
	}

	/**
	 * This method initializes jMenuItemNovo	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getJMenuItemNovo() {
		if (jMenuItemNovo == null) {
			jMenuItemNovo = new JMenuItem();
			jMenuItemNovo.setText("Novo jogo");
			jMenuItemNovo.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					GameClient.newGame();
				}
			});
		}
		return jMenuItemNovo;
	}

	/**
	 * This method initializes jMenuItemExit	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getJMenuItemExit() {
		if (jMenuItemExit == null) {
			jMenuItemExit = new JMenuItem();
			jMenuItemExit.setText("Sair");
			jMenuItemExit.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					GameClient.exit();
				}
			});
		}
		return jMenuItemExit;
	}

	/**
	 * This method initializes boardPanel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getBoardPanel() {
		if (boardPanel == null) {
			boardPanel = new BoardPanel();
			boardPanel.setSize(boardPanel.getPreferredSize());
		}
		return boardPanel;
	}

	public void refresh(String message){
		this.statusBar.setText(message);
		this.repaint();
		//this.boardPanel.repaint();
	}

	/**
	 * This method initializes jMenuPlayers	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getJMenuPlayers() {
		if (jMenuPlayers == null) {
			jMenuPlayers = new JMenu();
			jMenuPlayers.setText("Jogadores");
			jMenuPlayers.add(getJMenuDarkPlayer());
			jMenuPlayers.add(getJMenuLightPlayer());
		}
		return jMenuPlayers;
	}

	/**
	 * This method initializes jMenuDarkPlayer	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getJMenuDarkPlayer() {
		if (jMenuDarkPlayer == null) {
			jMenuDarkPlayer = new JMenu();
			jMenuDarkPlayer.setText("Player 1 (Preto)");
			jMenuDarkPlayer.add(getJRadioButtonDarkHuman());
			jMenuDarkPlayer.add(getJRadioButtonDarkEasy());
			jMenuDarkPlayer.add(getJRadioButtonDarkNormal());
			jMenuDarkPlayer.add(getJRadioButtonDarkHard());
			ButtonGroup buttonGroup = new ButtonGroup();
			buttonGroup.add(getJRadioButtonDarkHuman());
			buttonGroup.add(getJRadioButtonDarkEasy());
			buttonGroup.add(getJRadioButtonDarkNormal());
			buttonGroup.add(getJRadioButtonDarkHard());

		}
		return jMenuDarkPlayer;
	}

	/**
	 * This method initializes jRadioButtonDarkHuman	
	 * 	
	 * @return javax.swing.JRadioButtonMenuItem	
	 */
	private JRadioButtonMenuItem getJRadioButtonDarkHuman() {
		if (jRadioButtonDarkHuman == null) {
			jRadioButtonDarkHuman = new JRadioButtonMenuItem();
			jRadioButtonDarkHuman.setText("Humano");
			jRadioButtonDarkHuman.setSelected(true);
			jRadioButtonDarkHuman.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					if(jRadioButtonDarkHuman.isSelected()) GameClient.setDarkPlayer(Player.HUMAN);
				}
			});
		}
		return jRadioButtonDarkHuman;
	}

	/**
	 * This method initializes jMenuLightPlayer	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getJMenuLightPlayer() {
		if (jMenuLightPlayer == null) {
			jMenuLightPlayer = new JMenu();
			jMenuLightPlayer.setText("Player 2 (Branco)");
			jMenuLightPlayer.add(getJRadioButtonLightHuman());
			jMenuLightPlayer.add(getJRadioButtonLightEasy());
			jMenuLightPlayer.add(getJRadioButtonLightNormal());
			jMenuLightPlayer.add(getJRadioButtonLightHard());
			ButtonGroup buttonGroup = new ButtonGroup();
			buttonGroup.add(getJRadioButtonLightHuman());
			buttonGroup.add(getJRadioButtonLightEasy());
			buttonGroup.add(getJRadioButtonLightNormal());
			buttonGroup.add(getJRadioButtonLightHard());
		}
		return jMenuLightPlayer;
	}

	/**
	 * This method initializes jRadioButtonLightHuman	
	 * 	
	 * @return javax.swing.JRadioButtonMenuItem	
	 */
	private JRadioButtonMenuItem getJRadioButtonLightHuman() {
		if (jRadioButtonLightHuman == null) {
			jRadioButtonLightHuman = new JRadioButtonMenuItem();
			jRadioButtonLightHuman.setText("Humano");
			jRadioButtonLightHuman.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					if (jRadioButtonLightHuman.isSelected()) GameClient.setLightPlayer(Player.HUMAN);
				}
			});
		}
		return jRadioButtonLightHuman;
	}

	/**
	 * This method initializes jRadioButtonDarkEasy	
	 * 	
	 * @return javax.swing.JRadioButtonMenuItem	
	 */
	private JRadioButtonMenuItem getJRadioButtonDarkEasy() {
		if (jRadioButtonDarkEasy == null) {
			jRadioButtonDarkEasy = new JRadioButtonMenuItem();
			jRadioButtonDarkEasy.setText("Computador (Fácil)");
			jRadioButtonDarkEasy.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					GameClient.setDarkPlayer(Player.COMPUTER_EASY);
				}
			});
		}
		return jRadioButtonDarkEasy;
	}

	/**
	 * This method initializes jRadioButtonDarkNormal	
	 * 	
	 * @return javax.swing.JRadioButtonMenuItem	
	 */
	private JRadioButtonMenuItem getJRadioButtonDarkNormal() {
		if (jRadioButtonDarkNormal == null) {
			jRadioButtonDarkNormal = new JRadioButtonMenuItem();
			jRadioButtonDarkNormal.setText("Computador (Normal)");
			jRadioButtonDarkNormal
			.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					GameClient.setDarkPlayer(Player.COMPUTER_NORMAL);
				}
			});
		}
		return jRadioButtonDarkNormal;
	}

	/**
	 * This method initializes jRadioButtonDarkHard	
	 * 	
	 * @return javax.swing.JRadioButtonMenuItem	
	 */
	private JRadioButtonMenuItem getJRadioButtonDarkHard() {
		if (jRadioButtonDarkHard == null) {
			jRadioButtonDarkHard = new JRadioButtonMenuItem();
			jRadioButtonDarkHard.setText("Computador (Difícil)");
			jRadioButtonDarkHard.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					GameClient.setDarkPlayer(Player.COMPUTER_HARD);
				}
			});
		}
		return jRadioButtonDarkHard;
	}

	/**
	 * This method initializes jRadioButtonLightEasy	
	 * 	
	 * @return javax.swing.JRadioButtonMenuItem	
	 */
	private JRadioButtonMenuItem getJRadioButtonLightEasy() {
		if (jRadioButtonLightEasy == null) {
			jRadioButtonLightEasy = new JRadioButtonMenuItem();
			jRadioButtonLightEasy.setText("Computador (Fácil)");
			jRadioButtonLightEasy.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					GameClient.setLightPlayer(Player.COMPUTER_EASY);
				}
			});
		}
		return jRadioButtonLightEasy;
	}

	/**
	 * This method initializes jRadioButtonLightNormal	
	 * 	
	 * @return javax.swing.JRadioButtonMenuItem	
	 */
	private JRadioButtonMenuItem getJRadioButtonLightNormal() {
		if (jRadioButtonLightNormal == null) {
			jRadioButtonLightNormal = new JRadioButtonMenuItem();
			jRadioButtonLightNormal.setText("Computador (Normal)");
			jRadioButtonLightNormal.setSelected(true);
			jRadioButtonLightNormal
			.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					GameClient.setLightPlayer(Player.COMPUTER_NORMAL);
				}
			});
		}
		return jRadioButtonLightNormal;
	}

	/**
	 * This method initializes jRadioButtonLightHard	
	 * 	
	 * @return javax.swing.JRadioButtonMenuItem	
	 */
	private JRadioButtonMenuItem getJRadioButtonLightHard() {
		if (jRadioButtonLightHard == null) {
			jRadioButtonLightHard = new JRadioButtonMenuItem();
			jRadioButtonLightHard.setText("Computador (Difícil)");
			jRadioButtonLightHard.addChangeListener(new javax.swing.event.ChangeListener() {
				public void stateChanged(javax.swing.event.ChangeEvent e) {
					GameClient.setLightPlayer(Player.COMPUTER_HARD);
				}
			});
		}
		return jRadioButtonLightHard;
	}

}  //  @jve:decl-index=0:visual-constraint="10,10"
