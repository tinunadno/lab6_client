package org.lab6.storedClasses;

import java.io.Serializable;

public enum Color implements Serializable {
	RED,
	BLUE,
	ORANGE,
	WHITE;

	/**
	 * parse color
	 * @param col
	 * @return
	 */
	public static Color parse(String col){
		switch(col){
			case "RED", "1":
				return Color.RED;
			case "BLUE", "2":
				return Color.BLUE;
			case "ORANGE", "3":
				return Color.ORANGE;
			case "WHITE", "4":
				return Color.WHITE;
		}
		return null;
	}
}