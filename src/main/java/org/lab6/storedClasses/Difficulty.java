package org.lab6.storedClasses;

import java.io.Serializable;

public enum Difficulty implements Serializable {
	VERY_HARD,
	INSANE,
	TERRIBLE;

	/**
	 * parse difficulty
	 * @param col
	 * @return
	 */
	public static Difficulty parse(String col){
		switch(col){
			case "VERY_HARD", "1":
				return Difficulty.VERY_HARD;
			case "INSANE", "2":
				return Difficulty.INSANE;
			case "TERRIBLE", "3":
				return Difficulty.TERRIBLE;
		}
		return null;
	}
}