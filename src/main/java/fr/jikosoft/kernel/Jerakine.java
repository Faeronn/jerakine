package fr.jikosoft.kernel;

import javax.swing.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Iterator;
import java.util.List;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import fr.jikosoft.managers.LoginManager;
import fr.jikosoft.objects.Cell;
import fr.jikosoft.objects.Character;
import fr.jikosoft.objects.Map;

public class Jerakine extends JPanel {
	private static final int DEFAULT_FRAME_HEIGHT = 1200;
	private static final int DEFAULT_FRAME_WIDTH = 1600;
	public static final int MAP_WIDTH = 14;
	public static final int MAP_HEIGHT = 16;

	private static int frameWidth = DEFAULT_FRAME_WIDTH - MAP_WIDTH;
	public static int tileWidth = frameWidth / MAP_WIDTH;
	public static int tileHeight = tileWidth / 2;

	//private static GameManager gameManager;
	//private static JPanel menu;
	private Character character;
	private Timer moveTimer;
	private Map map;

	public static void main(String[] args) {
		//loginScreen();
		mainGame();
	}

	public static void mainGame() {
		//gameManager = new GameManager();
		JFrame frame = new JFrame("Jerakine");
		Jerakine panel = new Jerakine();
		panel.setBackground(new Color(34, 17, 49));

		/*
		menu = new JPanel();
		menu.setLayout(new FlowLayout(FlowLayout.CENTER));
		menu.setBackground(Color.gray);
		menu.setPreferredSize(new Dimension(DEFAULT_FRAME_WIDTH, DEFAULT_FRAME_HEIGHT - (tileHeight * MAP_HEIGHT + tileHeight)));

		JPanel stats = new JPanel();
		stats.setLayout(new BoxLayout(stats, BoxLayout.Y_AXIS));
		stats.setBackground(Color.darkGray);
		JLabel statsTitle = new JLabel("Character Stats");
		statsTitle.setForeground(Color.white);

		JLabel nameLabel = new JLabel("Name: Xx_AlexDu30_xX");
		nameLabel.setForeground(Color.white);

		JLabel levelLabel = new JLabel("Level: 1");
		levelLabel.setForeground(Color.white);

		JLabel healthLabel = new JLabel("Health: 100/100");
		healthLabel.setForeground(Color.white);

		stats.add(statsTitle);
		stats.add(nameLabel);
		stats.add(levelLabel);
		stats.add(healthLabel);
		menu.add(stats);

		frame.add(menu, BorderLayout.SOUTH); */

		frame.add(panel);
		frame.setSize(DEFAULT_FRAME_WIDTH, DEFAULT_FRAME_HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		Timer timer = new Timer(16, _ -> panel.repaint());
		timer.start();
	}

	public Jerakine() {
		map = new Map(1, "3C5257403AB182746205E9C40AD28915A4132B482756904EAC209D5879A4AE80746205E9C40AD28915A4132B482756904EAC209D58413AB182746205E9C40AD28915A4132B482756904EAC3C525778A4AEF0485D01E9C40AD28915A4132B482756904EAC209D5879A4AE80746205E9C40AD28915A4132B4827561EA92B209D58413AB182746205E9C40AD28915A4132B482756904EAC209D58413AB182746205E9C4CA237505A4132B482756904EAC209D58413AB182746205E9C40AD28915A4132B8FD415904EAC209D5879A4AE80746205E9C4CA237505A4132B482756904EAC209D5879A4AE80746205E9C40AD28915A4132B8FD4151EA92B209D58413AB182746205E9C4CA23758547EA0A482756904EAC209D58413AB182746205E9C40AD28915A4132B482756904EAC209D58413AB182746205E9C40AD28915A4132B8FD4151EA92B209D58413AB1827462E591BA02D28915A4132B482756904EAC209D58413AB1827462E591BA02D28915A4132B482756904EAC209D58413AB182746205E9C4CA237505A4132B8FD415904EAC209D58413AB182746205E9C40AD28915A4132B8FD415904EAC3C5257403AB182746205E9C4CA237505A4132B482756904EAC209D58413AB1827462E591BA02D28915A4132B482756904EAC209D58413AB182746205E9C40AD2899547EA0A482756904EAC209D58413AB1F2485D01E9C40AD28915A4132B482756904EAC209D58413AB1827462E591BA02D28915A4132B482756904EAC3C5257403AB1F2485D01E9C40AD2899547EA0A482756904EAC3C5257403AB1F2485DE191BA02D28915A4132B482756904EAC3C5257403AB182746205E9C40AD28915A4132B8FD415904EAC209D58413AB182746205E9C40AD2899547EA0A8FD415904EAC209D58413AB182746205E9C40AD2899547EA0A482756904EAC209D58413AB182746205E9C4CA23758547EA0A4827561EA92B209D58413AB182746205E9C40AD28915A4132B482756904EAC209D58413AB182746205E9C40AD28915A4132B482756904EAC209D5879A4AE80746205E9C40AD28915A4132B482756904EAC209D58413AB1F2485D01E9C40AD28915A4132B482756904EAC209D58413AB1827462E591BAC223758547EA0A482756904EAC209D58413AB182746205E9C40AD28915A4132B8FD415904EAC3C525778A4AE80746205E9C40AD28915A4132B8FD415904EAC209D58413AB1827462E591BAC223758547EA0A482756904EAC209D5879A4AE80746205E9C40AD28915A4132B482756904EAC209D58413AB182746205E9C40AD28915A4132B8FD415904EAC209D58413AB182746205E9C40AD28915A4132B8FD4151EA92B209D58413AB182746205E9C4CA237505A4132B482756904EAC209D58413AB1827462E591BAC223758547EA0A4827561EA92B209D58413AB1827462E591BA02D2899547EA0A8FD415904EAC3C5257403AB1827462E591BA02D28915A4132B482756904EAC209D58413AB1827462E591BA02D28915A4132B482756904EAC209D58413AB1827462E591BA02D28915A4132B8FD415904EAC209D58413AB182746205E9C40AD28915A4132B482756904EAC209D58413AB1F2485D01E9C40AD28915A4132B482756904EAC209D5879A4AE807462E591BA02D28915A4132B8FD415904EAC209D58413AB182746205E9C4CA237505A4132B482756904EAC209D58413AB1827462E591BA02D28915A4132B4827561EA92B209D5879A4AE80746205E9C40AD28915A4132B4827561EA92B209D5879A4AE807462E591BA02D28915A4132B482756904EAC209D58413AB182746205");
		character = new Character(1, "Xx_AlexDu30_xX", map.getCells()[15][9]);

		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				updateTileDimensions();
				repaint();
			}
		});
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				handleCellClick(e.getX(), e.getY());
			}
		});
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent event) {
				handleMouseHover(event.getX(), event.getY());
			}
		});
	}

	private void updateTileDimensions() {
		frameWidth = getWidth();

		tileWidth = frameWidth / MAP_WIDTH;
		tileHeight = (tileWidth / 2) + 1;

		Cell[][] cells = map.getCells();
		for (int row = 0; row < cells.length; row++) {
			for (int col = 0; col < cells[row].length; col++) {
				if (cells[row][col] != null) {
					cells[row][col].updateTileDimensions(tileWidth, tileHeight);
				}
			}
		}
	}
	/*
	private void updateMenuDimensions() {
        int newWidth = getWidth();
        int newHeight = getHeight() - tileHeight * MAP_HEIGHT + tileHeight;

        menu.setPreferredSize(new Dimension(newWidth, newHeight));
        menu.revalidate();
        menu.repaint();
	} */

	private void handleCellClick(int mouseX, int mouseY) {
		Pathfinding pathfinding = new Pathfinding(map);
		Cell[][] cells = map.getCells();
		for (int row = 0; row < cells.length; row++) {
			for (int col = 0; col < cells[row].length; col++) {
				if (cells[row][col] != null && cells[row][col].contains(mouseX, mouseY) && cells[row][col].isClickable()) {
					System.out.println("From : " + character.getCurrentCell().getCellID() + " | To : " + cells[row][col].getCellID());
					if (character != null) {
						List<Cell> path = pathfinding.findPath(character.getCurrentCell(), cells[row][col]);
						startPathAnimation(path);
					}
					return;
				}
			}
		}
	}

	private void startPathAnimation(List<Cell> path) {
		if (moveTimer != null && moveTimer.isRunning()) {
			moveTimer.stop();
		}
	
		Iterator<Cell> pathIterator = path.iterator();
		moveTimer = new Timer(200, e -> {
			if (pathIterator.hasNext()) {
				Cell nextCell = pathIterator.next();
				character.setCurrentCell(nextCell);
				repaint();
			} else {
				((Timer) e.getSource()).stop();
			}
		});
		moveTimer.start();
	}

	private void handleMouseHover(int mouseX, int mouseY) {
		Cell[][] cells = map.getCells();
		boolean hovering = false;
		for (int row = 0; row < cells.length; row++) {
			for (int col = 0; col < cells[row].length; col++) {
				if (cells[row][col] != null && cells[row][col].contains(mouseX, mouseY) && cells[row][col].isClickable()) {
					setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
					hovering = true;
					return;
				}
			}
		}
		if (!hovering) { setCursor(Cursor.getDefaultCursor());}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Cell[][] cells = map.getCells();
		for (int row = 0; row < cells.length; row++) {
			for (int col = 0; col < cells[row].length; col++) {
				if (cells[row][col] != null) {
					cells[row][col].draw(g);
				}
			}
		}

		if (character != null) {character.draw(g);}
	}

	public static void loginScreen() {
		JFrame frame = new JFrame("Login Screen");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300, 150);
		frame.setLayout(null);

		JLabel userLabel = new JLabel("Username:");
		userLabel.setBounds(20, 20, 80, 25);
		JTextField userText = new JTextField();
		userText.setBounds(100, 20, 160, 25);

		JLabel passLabel = new JLabel("Password:");
		passLabel.setBounds(20, 50, 80, 25);
		JPasswordField passText = new JPasswordField();
		passText.setBounds(100, 50, 160, 25);

		JButton okButton = new JButton("OK");
		okButton.setBounds(100, 90, 80, 25);

		okButton.addActionListener(_ -> tryToLog(frame, userText.getText(), new String(passText.getPassword())));

		frame.add(userLabel);
		frame.add(userText);
		frame.add(passLabel);
		frame.add(passText);
		frame.add(okButton);

		frame.setVisible(true);
	}

	private static void tryToLog(JFrame parent, String username, String password) {
		new LoginManager(username, password, response -> {
			SwingUtilities.invokeLater(() -> {
				JOptionPane.showMessageDialog(parent,
					"Server Response: " + response,
					"Server Response",
					JOptionPane.INFORMATION_MESSAGE);
			});
		});
	}
}
