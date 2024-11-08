package fr.jikosoft.objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

public class Cell {
	private int gridXPosition;
	private int gridYPosition;
	private int isometricX;
	private int isometricY;
	private int tileWidth;
	private int tileHeight;
	private Color fillColor;
	private int cellID;
	private boolean isClickable;

	public Cell(int x, int y, int tileWidth, int tileHeight, Color fillColor, int cellNumber, boolean isClickable) {
		this.gridXPosition = x;
		this.gridYPosition = y;
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		this.fillColor = fillColor;
		this.cellID = cellNumber;
		this.isClickable = isClickable;
		calculateIsometricPosition();
	}

	private void calculateIsometricPosition() {
		int offsetX = tileWidth / 2;
		int offsetY = 0;
		if (gridYPosition % 2 == 0) {
			isometricX = offsetX + gridXPosition * tileWidth;
			isometricY = offsetY + gridYPosition * (tileHeight / 2);
		} else {
			isometricX = offsetX + tileWidth / 2 + gridXPosition * tileWidth;
			isometricY = offsetY + gridYPosition * (tileHeight / 2);
		}
	}

	public boolean contains(int mouseX, int mouseY) {
        Polygon tilePolygon = new Polygon(
            new int[]{isometricX, isometricX + tileWidth / 2, isometricX, isometricX - tileWidth / 2},
            new int[]{isometricY, isometricY + tileHeight / 2, isometricY + tileHeight, isometricY + tileHeight / 2},
            4
        );
        return tilePolygon.contains(mouseX, mouseY);
    }

	public void draw(Graphics graphic) {
		graphic.setColor(fillColor);
		graphic.fillPolygon(
			new int[]{isometricX, isometricX + tileWidth / 2, isometricX, isometricX - tileWidth / 2},
			new int[]{isometricY, isometricY + tileHeight / 2, isometricY + tileHeight, isometricY + tileHeight / 2},
			4
		);

		graphic.setColor(new Color(255, 255, 255, 64));
		graphic.drawPolygon(
			new int[]{isometricX, isometricX + tileWidth / 2, isometricX, isometricX - tileWidth / 2},
			new int[]{isometricY, isometricY + tileHeight / 2, isometricY + tileHeight, isometricY + tileHeight / 2},
			4
		);

		String cellText = String.valueOf(this.cellID);
		int textWidth = graphic.getFontMetrics().stringWidth(cellText);
		int textHeight = graphic.getFontMetrics().getAscent();
		graphic.drawString(cellText, isometricX - textWidth / 2, isometricY + tileHeight / 2 + textHeight / 4);
	}

	public int getCellID() {return this.cellID;}
	public boolean isClickable() {return this.isClickable;}
	public void setFillColor(Color fillColor) {this.fillColor = fillColor;}
	public int getIsometricX() {return this.isometricX;}
	public int getIsometricY() {return this.isometricY;}
}
