package model;

public abstract class StateLemming extends Lemming {
	
	public abstract void action();
	
	public StateLemming(Game game){
		super(game);
	}
	
}
