package model;

public class AirLibre extends Terrain {
	public AirLibre(Game game, Coordinate coordonnees) throws ExceptionCoordinate{
		super(game, coordonnees);
	}
	
	@Override
	public void comportement(Lemming lemming){}

}
