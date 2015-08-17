package model;

import java.util.ArrayList;
import java.util.List;

public enum Category {
	
	MOVIES,
	GAMES,
	TV,
	BOOKS,
	MUSIC,
	LOCATION;


	public static List<Category> getCategorias() {
		List<Category> result = new ArrayList<Category>();
		result.add(GAMES);
		result.add(MOVIES);
		result.add(TV);
		result.add(BOOKS);
		result.add(MUSIC);
		return result;
	}
}
