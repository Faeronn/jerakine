package fr.jikosoft.kernel;

import java.awt.Image;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import fr.jikosoft.objects.Direction;

public class Constants {
	public static final	String CLIENT_VERSION = "0.0.1";
	public static final Color EXP_BAR_COLOR = new Color(58, 164, 193);

	public static Map<Direction, Image> CHARACTER_SPRITES = new HashMap<>();
	
	static {
		String basePath = "assets/character/";
		CHARACTER_SPRITES.put(Direction.NORTH, new ImageIcon(basePath + "north.png").getImage());
		CHARACTER_SPRITES.put(Direction.NORTH_EAST, new ImageIcon(basePath + "northeast.png").getImage());
		CHARACTER_SPRITES.put(Direction.EAST, new ImageIcon(basePath + "east.png").getImage());
		CHARACTER_SPRITES.put(Direction.SOUTH_EAST, new ImageIcon(basePath + "southeast.png").getImage());
		CHARACTER_SPRITES.put(Direction.SOUTH, new ImageIcon(basePath + "south.png").getImage());
		CHARACTER_SPRITES.put(Direction.SOUTH_WEST, new ImageIcon(basePath + "southwest.png").getImage());
		CHARACTER_SPRITES.put(Direction.WEST, new ImageIcon(basePath + "west.png").getImage());
		CHARACTER_SPRITES.put(Direction.NORTH_WEST, new ImageIcon(basePath + "northwest.png").getImage());
	}
}
