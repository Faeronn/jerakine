package fr.jikosoft.kernel;

import javax.swing.*;

import fr.jikosoft.managers.SocketManager;

public class Jerakine {
	private static SocketManager socketManager;
	public static void main(String[] args) {
		socketManager = new SocketManager();
		
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