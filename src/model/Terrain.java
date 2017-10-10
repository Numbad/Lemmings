package model;

public abstract class Terrain implements ComportementTerrain{
	Game game;
	Coordinate coordonnees;
	
	public Terrain(Game game, Coordinate coordonnees){
		this.game = game;
		this.coordonnees = coordonnees;
	}
	
	public Coordinate getCoordonnees(){
		return this.coordonnees;
	}
}
