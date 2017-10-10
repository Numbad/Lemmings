package model;

public class Destructible extends Terrain {
	public Destructible(Game game, Coordinate coordonnees) throws ExceptionCoordinate{
		super(game, coordonnees);
	}
	
	@Override
	public void comportement(Lemming lemming){
		lemming.changeDirection();
	}
}
