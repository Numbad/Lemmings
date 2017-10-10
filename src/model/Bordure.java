package model;

public class Bordure extends Terrain {
	public Bordure(Game game, Coordinate coordonnees) throws ExceptionCoordinate{
		super(game, coordonnees);
	}
	
	@Override
	public void comportement(Lemming lemming){
		lemming.changeDirection();
	}
}
