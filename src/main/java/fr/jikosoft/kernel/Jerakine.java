package fr.jikosoft.kernel;

import javax.swing.*;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import fr.jikosoft.managers.SocketManager;

public class Jerakine extends JPanel {
	private static SocketManager socketManager;
	private static final int DEFAULT_FRAME_HEIGHT = 595;
	private static final int DEFAULT_FRAME_WIDTH = 756;
	private static final int COLS = 14;
	private static final int ROWS = 16;

	private static int frameWidth = DEFAULT_FRAME_WIDTH - COLS;
	private static int tileWidth = frameWidth / COLS;
	private static int tileHeight = tileWidth / 2;

	public static void main(String[] args) {
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
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				updateTileDimensions();
				repaint();
			}
		});
	}

	private void updateTileDimensions() {
		frameWidth = getWidth();

		tileWidth = frameWidth / COLS;
		tileHeight = (tileWidth / 2) + 1;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		int offsetX = tileWidth / 2;
		int offsetY = 0;
		int cellNumber = 15;

		for (int row = 0; row <= (ROWS * 2) - 2; row++) {
			for (int col = 0; col < (row % 2 == 0 ? COLS : COLS - 1); col++) {
				int isoX;
				int isoY;

				if (row % 2 == 0) {
					isoX = offsetX + col * tileWidth;
					isoY = offsetY + row * (tileHeight / 2);
				} else {
					isoX = offsetX + tileWidth / 2 + col * tileWidth;
					isoY = offsetY + row * (tileHeight / 2);
				}

				g.setColor(new Color(88, 157, 32));
				g.fillPolygon(
					new int[]{isoX, isoX + tileWidth / 2, isoX, isoX - tileWidth / 2},
					new int[]{isoY, isoY + tileHeight / 2, isoY + tileHeight, isoY + tileHeight / 2},
					4
				);

				g.setColor(Color.BLACK);
				g.drawPolygon(
					new int[]{isoX, isoX + tileWidth / 2, isoX, isoX - tileWidth / 2},
					new int[]{isoY, isoY + tileHeight / 2, isoY + tileHeight, isoY + tileHeight / 2},
					4
				);

				g.setColor(Color.BLACK);
				String cellText = String.valueOf(cellNumber);
				int textWidth = g.getFontMetrics().stringWidth(cellText);
				int textHeight = g.getFontMetrics().getAscent();
				g.drawString(cellText, isoX - textWidth / 2, isoY + tileHeight / 2 + textHeight / 4);

				cellNumber++;
			}
		}
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

		okButton.addActionListener(e -> {
				socketManager = new SocketManager();
				String username = userText.getText();
				String password = new String(passText.getPassword());

				socketManager.sendUserInfos(username, password);
				JOptionPane.showMessageDialog(frame,
				"Server Response: ",
				"Server Response", JOptionPane.INFORMATION_MESSAGE);
		});

		frame.add(userLabel);
		frame.add(userText);
		frame.add(passLabel);
		frame.add(passText);
		frame.add(okButton);

		frame.setVisible(true);
	}
}
