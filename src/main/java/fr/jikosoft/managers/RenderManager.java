package fr.jikosoft.managers;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class RenderManager {
	public static JPanel createPanel(Color color) {
		JPanel panel = new JPanel();
		panel.setBackground(color);
		return panel;
	}

	public static JLabel createLabel(String text, Color color) {
		JLabel label = new JLabel(text);
		label.setForeground(color);
		return label;
	}
}
