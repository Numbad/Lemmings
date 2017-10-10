package model;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Charpentier extends StateLemming {

    Coordinate realLast;
    private int nombreDePas = 1;
    boolean firstStep = false;

    public Charpentier(Game game, Coordinate coordinate, Direction direction) {
        super(game);
        this.game = game;
        this.coord = coordinate;
        this.direction = direction;
        realLast = coord;
        addChange(new Change(coord, Change.ChangeType.CHARPENTIER));
    }

    @Override
    public void action() {
        Coordinate last = realLast;
        Coordinate diagLeftUp = new Coordinate(last.getX() + Direction.DiagonalLeftUp.x, last.getY() + Direction.DiagonalLeftUp.y);
        Coordinate diagRightUp = new Coordinate(last.getX() + Direction.DiagonalRightUp.x, last.getY() + Direction.DiagonalRightUp.y);
        if (direction == Direction.Left) {
            //  if (game.isAir(diagLeftUp)) {
            try {
                game.getDest().add(new Destructible(game, last));
                game.removeAir(last);
                System.out.println("ap " + game.getDest().size());
                addChange(new Change(last, Change.ChangeType.DESTRUCTIBLE));
            } catch (ExceptionCoordinate ex) {
                Logger.getLogger(Bombeur.class.getName()).log(Level.SEVERE, null, ex);
            }
            //   }
        } else if (direction == Direction.Right) {
            //  if (game.isAir(diagRightUp)) {
            try {
                System.out.println("av " + game.getDest().size());
                game.getDest().add(new Destructible(game, last));
                game.removeAir(last);
                System.out.println("ap " + game.getDest().size());
                addChange(new Change(last, Change.ChangeType.DESTRUCTIBLE));
            } catch (ExceptionCoordinate ex) {
                Logger.getLogger(Bombeur.class.getName()).log(Level.SEVERE, null, ex);
            }
            //  }
        }
        notifyObserver();
        move();

    }

    @Override
    public void move() {

        Coordinate last = realLast;
        Coordinate last1 = this.coord;
        Coordinate diagLeftUp = new Coordinate(last.getX() + Direction.DiagonalLeftUp.x, last.getY() + Direction.DiagonalLeftUp.y);
        Coordinate diagRightUp = new Coordinate(last.getX() + Direction.DiagonalRightUp.x, last.getY() + Direction.DiagonalRightUp.y);
        Coordinate next = new Coordinate(last.getX(), last.getY());
        if (this.direction == Direction.Left) {
            if (game.isAir(diagLeftUp)) {
                next = new Coordinate(last1.getX(), last.getY() + Direction.DiagonalLeftUp.y);
                if (firstStep) {
                    next = new Coordinate(last1.getX() + Direction.DiagonalLeftUp.x, last.getY() + Direction.DiagonalLeftUp.y);
                }
                realLast = new Coordinate(last.getX() + Direction.DiagonalLeftUp.x, last.getY() + Direction.DiagonalLeftUp.y);
            }
        } else if (this.direction == Direction.Right) {
            next = new Coordinate(last.getX(), last.getY() + Direction.DiagonalRightUp.y);
            if (game.isAir(diagRightUp)) {
                if (firstStep) {
                    next = new Coordinate(last1.getX() + Direction.DiagonalRightUp.x, last.getY() + Direction.DiagonalRightUp.y);
                }
                realLast = new Coordinate(last.getX() + Direction.DiagonalRightUp.x, last.getY() + Direction.DiagonalRightUp.y);

            }
        }
        addChange(new Change(next, Change.ChangeType.CHARPENTIER));
        if (firstStep) {
            addChange(new Change(last1, Change.ChangeType.VOID));
        }
        this.coord = next;
        firstStep = true;
        notifyObserver();
    }

    public void kill() throws InterruptedException {
        Coordinate last = this.coord;
        System.out.println("bye !");
        addChange(new Change(last, Change.ChangeType.VOID));
        notifyObserver();
        game.killLemming(this);
    }

    @Override
    public void run() {
        while (isAlive()) {
            try {
                if (nombreDePas <= 5) {
                    action();
                    if (nombreDePas == 5) {
                        Thread.sleep(game.getSpeed()+150);
                        kill();

                    }
                    nombreDePas++;
                }
                Thread.sleep(game.getSpeed() - 100);
            } catch (Exception ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
