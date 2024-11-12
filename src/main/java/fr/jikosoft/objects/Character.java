package fr.jikosoft.objects;

import java.awt.Color;
import java.awt.Graphics;

public class Character {
	private Cell currentCell;
	private int characterID;
	private Stats stats;
	private String name;

	public Character(int characterID, String name, Cell currentCell) {
		this.characterID = characterID;
		this.currentCell = currentCell;
		this.name = name;
		this.stats = new Stats(0, 0, 0);
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
	public Stats getStats() {return this.stats;}
	public String getName() {return this.name;}

	public void setCurrentCell(Cell cell) {this.currentCell = cell;}

	public class Stats {
		private int strength;
		private int agility;
		private int intelligence;

		public Stats(int strength, int agility, int intelligence) {
			this.strength = strength;
			this.agility = agility;
			this.intelligence = intelligence;
		}

		public int getAgility() {return agility;}
		public int getStrength() {return strength;}
		public int getIntelligence() {return intelligence;}

		public void setAgility(int agility) {this.agility = agility;}
		public void setStrength(int strength) {this.strength = strength;}
		public void setIntelligence(int intelligence) {this.intelligence = intelligence;}
	}
}
