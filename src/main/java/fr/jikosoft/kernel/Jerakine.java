package fr.jikosoft.kernel;

import javax.swing.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import fr.jikosoft.managers.LoginManager;
import fr.jikosoft.objects.Cell;
import fr.jikosoft.objects.Character;

public class Jerakine extends JPanel {
	private static final int DEFAULT_FRAME_HEIGHT = 595;
	private static final int DEFAULT_FRAME_WIDTH = 756;
	private static final int COLS = 14;
	private static final int ROWS = 16;

	private Cell[][] cells = new Cell[ROWS * 2 - 1][COLS];
	private static int frameWidth = DEFAULT_FRAME_WIDTH - COLS;
	private static int tileWidth = frameWidth / COLS;
	private static int tileHeight = tileWidth / 2;

	private Character character;

	public static void main(String[] args) {
		//loginScreen();
		mainGame();
	}

	public static void mainGame() {
		JFrame frame = new JFrame("Isometric Game");
		Jerakine panel = new Jerakine();

		frame.add(panel);
		frame.setSize(frameWidth, DEFAULT_FRAME_HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		Timer timer = new Timer(16, e -> panel.repaint());
		timer.start();
	}

	public Jerakine() {
		initializeCells();
		character = new Character(1, "Xx_AlexDu30_xX", cells[5][5]);
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				updateTileDimensions();
				initializeCells();
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
			public void mouseMoved(MouseEvent e) {
				handleMouseHover(e.getX(), e.getY());
			}
		});
	}

	private void updateTileDimensions() {
		frameWidth = getWidth();

		tileWidth = frameWidth / COLS;
		tileHeight = (tileWidth / 2) + 1;
	}

	private void initializeCells() {
		int cellNumber = 15;
		for (int row = 0; row <= (ROWS * 2) - 2; row++) {
			for (int col = 0; col < (row % 2 == 0 ? COLS : COLS - 1); col++) {
				cells[row][col] = new Cell(col, row, tileWidth, tileHeight, new Color(88, 157, 32), cellNumber, col % 2 == 0);
				cellNumber++;
			}
		}
	}

	private void handleCellClick(int mouseX, int mouseY) {
		for (int row = 0; row < cells.length; row++) {
			for (int col = 0; col < cells[row].length; col++) {
				if (cells[row][col] != null && cells[row][col].contains(mouseX, mouseY) && cells[row][col].isClickable()) {
					System.out.println("Clicked on Cell: " + cells[row][col].getCellID());
					if (character != null) character.setCurrentCell(cells[row][col]);
					repaint();
					return;
				}
			}
		}
	}

	private void handleMouseHover(int mouseX, int mouseY) {
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

		okButton.addActionListener(e -> tryToLog(frame, userText.getText(), new String(passText.getPassword())));

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
