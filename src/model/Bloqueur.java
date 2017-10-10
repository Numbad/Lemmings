package model;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Bloqueur extends StateLemming {

    public Bloqueur(Game game, Coordinate coordinate) {
        super(game);
        this.game = game;
        this.coord = coordinate;
        addChange(new Change(coord, Change.ChangeType.BLOQUEUR));
    }

    @Override
    public void action() {
        setDirection(Direction.Arret);
        notifyObserver();
    }
    
    @Override
    public void run() {
        while (isAlive()) {
            try {
                action();
                Thread.sleep(game.getSpeed() - 100);
            } catch (Exception ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
}
