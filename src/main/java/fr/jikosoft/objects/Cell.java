package fr.jikosoft.objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

public class Cell {
	private int cellID;
	private int gridX;
	private int gridY;
	private int isometricX;
	private int isometricY;
	private int tileWidth;
	private int tileLength;
	private int tileHeight;
	private Color fillColor;
	private boolean isClickable;

	public Cell(int x, int y, int tileWidth, int tileLength, Color fillColor, int cellNumber, boolean isClickable) {
		this.gridX = x;
		this.gridY = y;
		this.tileWidth = tileWidth;
		this.tileLength = tileLength;
		this.tileHeight = isClickable ? 0 : 20;
		this.fillColor = fillColor;
		this.cellID = cellNumber;
		this.isClickable = isClickable;
		calculateIsometricPosition();
	}

	private void calculateIsometricPosition() {
		int offsetX = this.tileWidth / 2;
		int offsetY = 0;

		this.isometricX = ((gridY % 2 == 0) ? offsetX : offsetX *2) + gridX * tileWidth;
		this.isometricY = offsetY + gridY * (tileLength / 2);
	}

	public boolean contains(int mouseX, int mouseY) {
		Polygon tilePolygon = new Polygon(
			new int[]{isometricX, isometricX + tileWidth / 2, isometricX, isometricX - tileWidth / 2},
			new int[]{isometricY, isometricY + tileLength / 2, isometricY + tileLength, isometricY + tileLength / 2},
			4
		);
		return tilePolygon.contains(mouseX, mouseY);
	}

	public void draw(Graphics graphic) {
		int[] xPoints = { isometricX, isometricX + tileWidth / 2, isometricX, isometricX - tileWidth / 2 };
		int[] yPoints = { isometricY - tileHeight, isometricY + tileLength / 2 - tileHeight, isometricY + tileLength - tileHeight, isometricY + tileLength / 2 - tileHeight };
		graphic.setColor(fillColor);
		graphic.fillPolygon(xPoints, yPoints, 4);

		graphic.setColor(new Color(255, 255, 255, 96));
		graphic.drawPolygon(xPoints, yPoints, 4);

		if (tileHeight > 0) {
			graphic.setColor(fillColor.darker());

			//Right side
			graphic.fillPolygon(new int[]{ xPoints[1], xPoints[2], xPoints[2], xPoints[1]},
            					new int[]{ yPoints[1], yPoints[2], yPoints[2] + tileHeight, yPoints[1] + tileHeight}, 4);

			//Left side
			graphic.fillPolygon(
				new int[]{ xPoints[0], xPoints[0], xPoints[3], xPoints[3] },
				new int[]{ yPoints[2] + tileHeight, yPoints[2], yPoints[1], yPoints[1] + tileHeight }, 4);
			graphic.setColor(new Color(255, 255, 255, 96));
		}

		String cellText = String.valueOf(this.cellID);
		int textWidth = graphic.getFontMetrics().stringWidth(cellText);
		int textHeight = graphic.getFontMetrics().getAscent();
		graphic.drawString(cellText, isometricX - textWidth / 2, isometricY + tileLength / 2 - tileHeight + textHeight / 4);
	}

	public void updateTileDimensions(int newTileWidth, int newTileLength) {
		this.tileWidth = newTileWidth;
		this.tileLength = newTileLength;
		calculateIsometricPosition();
	}

	public int getCellID() {return this.cellID;}
	public boolean isClickable() {return this.isClickable;}
	public void setFillColor(Color fillColor) {this.fillColor = fillColor;}
	public int getIsometricX() {return this.isometricX;}
	public int getIsometricY() {return this.isometricY;}
	public Color getFillColor() {return this.fillColor;}
	
	public int getGridX() {return this.gridX;}
	public int getGridY() {return this.gridY;}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		Cell other = (Cell) obj;
		return gridX == other.gridX && gridY == other.gridY;
	}

	@Override
	public int hashCode() {return 31 * gridX + gridY;}
}
