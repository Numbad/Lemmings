package model;

public class Indestructible extends Terrain {
	
	public Indestructible(Game game, Coordinate coordonnees) throws ExceptionCoordinate{
		super(game, coordonnees);
	}
	
	@Override
	public void comportement(Lemming lemming){
		
	}
}
