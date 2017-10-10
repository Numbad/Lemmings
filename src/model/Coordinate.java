package model;

import java.util.Random;

public class Coordinate {
	
	private static final Random RND = new Random();

	private int y;
	private int x;

	public Coordinate(int x, int y) {
		this.y = y;
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public int getX() {
		return x;
	}

	public static Coordinate getRandomCoordinate(Game game) {
		int x = RND.nextInt(game.getWidth());
		int y = RND.nextInt(game.getHeight());
		return new Coordinate(x, y);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Coordinate other = (Coordinate) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

}
