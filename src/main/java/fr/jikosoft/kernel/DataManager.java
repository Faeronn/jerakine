package fr.jikosoft.kernel;

import fr.jikosoft.objects.Cell;

public class DataManager {
	private static String generateMapData(int mapHeight, int mapWidth, Cell[][] cells) {
		byte[] mapDataBytes = new byte[(int) Math.ceil(419 * 25 / 8.0)];
		int bitIndex = 0;

		for (int row = 0; row <= (mapHeight * 2) - 2; row++) {
			for (int col = 0; col < (row % 2 == 0 ? mapWidth : mapWidth - 1); col++) {
				int data = (cells[row][col].getFillColor().getRGB() & 0xFFFFFF) | ((cells[row][col].isClickable() ? 1 : 0) << 24);
				for (int i = 0; i < 25; i++) {
					if (((data >> i) & 1) == 1) mapDataBytes[bitIndex / 8] |= 1 << (bitIndex % 8);
					bitIndex++;
				}
			}
		}
		StringBuilder mapData = new StringBuilder();
		for (byte b : mapDataBytes) {
			mapData.append(String.format("%02X", b));
		}
		return mapData.toString();
	}
}
