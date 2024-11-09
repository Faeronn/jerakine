package fr.jikosoft.objects;

import java.awt.Color;
import java.awt.Graphics;

public class Character {
	private Cell currentCell;
	private int characterID;
	private String name;
	public Character(int characterID, String name, Cell currentCell) {
		this.characterID = characterID;
		this.name = name;
		this.currentCell = currentCell;
	}

	public void draw(Graphics g) {
		if (currentCell == null) return;
		int isoX = currentCell.getIsometricX();
		int isoY = currentCell.getIsometricY();

		int size = 20;
		g.setColor(Color.RED);
		g.fillOval(isoX - size / 2, isoY - size / 2, size, size);

		g.setColor(Color.BLACK);
		g.drawString(name, isoX - g.getFontMetrics().stringWidth(name) / 2, isoY - size / 2 - 5);
	}
	public Cell getCurrentCell() {return this.currentCell;}
	public int getCharacterID() {return this.characterID;}
	public String getName() {return this.name;}

	
	public void setCurrentCell(Cell cell) {this.currentCell = cell;}
}
