package model;

public enum Direction {
	Up(0, -1), Down(0, 1), Left(-1, 0), Right(1, 0), DiagonalRightUp(1, -1), DiagonalRightDown(1, 1), 
	DiagonalLeftUp(-1, -1), DiagonalLeftDown(-1,1), Arret(0,0);

	public int x;
	public int y;

	Direction(int x, int y) {
		this.x = x;
		this.y = y;
	}
}