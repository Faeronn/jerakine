package fr.jikosoft.objects;

import java.awt.Color;
import java.util.Random;

import fr.jikosoft.kernel.Jerakine;

public class Map {
	private int mapID;
	private int height;
	private int width;

	private final Cell[][] cells;

	public Map(int mapID) {
		this.height = Jerakine.MAP_HEIGHT;
		this.width = Jerakine.MAP_WIDTH;
		this.mapID = mapID;
		this.cells = new Cell[this.height * 2 - 1][this.width];
		
		int cellNumber = 1;
		for (int row = 0; row <= (this.height * 2) - 2; row++) {
			for (int col = 0; col < (row % 2 == 0 ? this.width : this.width - 1); col++) {
				int number = new Random().nextInt(100);
				this.cells[row][col] = new Cell(
					col, 
					row, 
					Jerakine.tileWidth, 
					Jerakine.tileHeight, 
					number > 20 ? new Color(88, 157, 32) : new Color(87, 82, 60), 
					cellNumber, 
					number > 20);
				cellNumber++;
			}
		}
	}

	public Map(int mapID, String mapData) {
		this.height = Jerakine.MAP_HEIGHT;
		this.width = Jerakine.MAP_WIDTH;
		this.mapID = mapID;
		this.cells = new Cell[this.height * 2 - 1][this.width];

		int length = mapData.length();
		byte[] mapDataBytes = new byte[length / 2];
		for (int i = 0; i < length; i += 2) {
			mapDataBytes[i / 2] = (byte) ((java.lang.Character.digit(mapData.charAt(i), 16) << 4)
					+ java.lang.Character.digit(mapData.charAt(i + 1), 16));
		}

		int cellNumber = 1;
		int bitIndex = 0;
		for (int row = 0; row <= (this.height * 2) - 2; row++) {
			for (int col = 0; col < (row % 2 == 0 ? this.width : this.width - 1); col++) {
				int data = 0;
				for (int i = 0; i < 25; i++) {
					if ((mapDataBytes[bitIndex / 8] & (1 << (bitIndex % 8))) != 0) {
						data |= (1 << i);
					}
					bitIndex++;
				}
				boolean clickable = (data & (1 << 24)) != 0;
				int colorRGB = data & 0x00FFFFFF;
				Color fillColor = new Color(colorRGB);

				cells[row][col] = new Cell(col, row, Jerakine.tileWidth, Jerakine.tileHeight, fillColor, cellNumber, clickable);
				cellNumber++;
			}
		}
	}

	public Cell getCellAt(int x, int y) {
		if(x >= 0 && x < height && y >= 0 && y < width) return this.cells[x][y];
		return null;
	}

	
	public Cell[][] getCells() {return this.cells;}
	public int getMapID() {return this.mapID;}
	public int getHeight() {return this.height;}
	public int getWidth() {return this.width;}
}
