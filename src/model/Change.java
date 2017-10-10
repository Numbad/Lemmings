package model;

public class Change {
	
    public static enum ChangeType {LEMMING, BLOQUEUR, BOMBEUR, CHARPENTIER, FOREUR, GRIMPEUR,
    								PARACHUTISTE, TUNNELIER, VOID,
    								INDESTRUCTIBLE, DESTRUCTIBLE, ENTREE, SORTIE, BORDURE}
    //public static enum ChangeTypeTerrain {DESTRUCTIBLE, INDESTRUCTIBLE, ENTREE, SORTIE}

	private Coordinate coordinate;
	private ChangeType changeType;
	//private ChangeTypeTerrain changeTypeTerrain;

	public Change(Coordinate coordinate, ChangeType changeType) {
		this.coordinate = coordinate;
		this.changeType = changeType;
	}
	
	/*public Change(Coordinate coordinate, ChangeTypeTerrain changeTypeTerrain) {
		this.coordinate = coordinate;
		this.changeTypeTerrain = changeTypeTerrain;
	}
	
	public ChangeTypeTerrain getchangeTypeTerrain() {
		return changeTypeTerrain;
	}*/

	public ChangeType getChangeType() {
		return changeType;
	}
	
	public Coordinate getCoordinate() {
		return coordinate;
	}
}
