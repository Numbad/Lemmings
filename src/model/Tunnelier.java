package model;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Tunnelier extends StateLemming {

    private boolean alive = true;
    private Direction direction;
    private Game game;
    private Coordinate coord;
    private boolean nextToBloker = false;
    private boolean destruct = false;
    private int position;

    public Tunnelier(Game game, Coordinate coordinate, Direction direction, int position) {
        super(game);
        this.game = game;
        this.coord = coordinate;
        this.direction = direction;
        this.position = position;
        addChange(new Change(coord, Change.ChangeType.TUNNELIER));
    }

    @Override
    public void action() {
        Coordinate destruction = new Coordinate(coord.getX(), coord.getY());
        if (!game.isAir(destruction)) {
            if (game.isDestructible(destruction)) {
                try {
                    game.getAir().add(new AirLibre(game, destruction));
                    destruct = true;
                    addChange(new Change(destruction, Change.ChangeType.VOID));
                } catch (ExceptionCoordinate ex) {
                    Logger.getLogger(Bombeur.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
        if (isNextTobloquer(destruction)) {
            nextToBloker = true;
            try {
                deleteBloquer(destruction);

            } catch (InterruptedException ex) {
                Logger.getLogger(Tunnelier.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (destruct && game.isAir(new Coordinate(coord.getX() + direction.x, coord.getY() + direction.y))) {
            
            try {
                backToLemming();
            } catch (InterruptedException ex) {
                Logger.getLogger(Tunnelier.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        notifyObserver();

    }

    @Override
    public void changeDirection() {
        if (this.direction == Direction.Right) {
            this.direction = Direction.Left;
        } else if (this.direction == Direction.Left) {
            this.direction = Direction.Right;
        } else {
            this.direction = Direction.Right;
        }
    }

    @Override
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public void move() {
        Coordinate last = this.coord;
        Coordinate next = new Coordinate(last.getX() + direction.x, last.getY() + direction.y);
        Coordinate diagLeftUp = new Coordinate(last.getX() + Direction.DiagonalLeftUp.x, last.getY() + Direction.DiagonalLeftUp.y);
        Coordinate diagRightUp = new Coordinate(last.getX() + Direction.DiagonalRightUp.x, last.getY() + Direction.DiagonalRightUp.y);
        Coordinate down = new Coordinate(last.getX() + Direction.Down.x, last.getY() + Direction.Down.y);
        Coordinate up = new Coordinate(last.getX() + Direction.Up.x, last.getY() + Direction.Up.y);
        if (!this.game.isExit(next)) {
            if (game.isIndestructible(next)) {
            System.out.println("" + direction);
            changeDirection();
            System.out.println("" + direction);
            next = new Coordinate(last.getX() + direction.x, last.getY() + direction.y);
            previous = this.direction;
        } else {
            action();
            if (this.game.isAir(down)) {
                setDirection(Direction.Down);
                 next = new Coordinate(last.getX() + direction.x, last.getY() + direction.y);
            } else if (!this.game.isAir(down) && this.direction == Direction.Down) {
                setDirection(previous);
                next = new Coordinate(last.getX() + direction.x, last.getY() + direction.y);
            }
            if (!this.game.isAir(next)) {
                if (direction == Direction.Left) {
                    //previous = this.direction;
                    if (game.isAir(diagLeftUp) && !game.isAir(next) && game.isAir(up)) {
                        next = diagLeftUp;
                    }
                    previous = this.direction;
                } else if (direction == Direction.Right) {
                    if (game.isAir(diagRightUp) && !game.isAir(next)&& game.isAir(up)) {
                        next = diagRightUp;
                    }
                    previous = this.direction;
                }
            }

        }
        addChange(new Change(next, Change.ChangeType.TUNNELIER));
        addChange(new Change(last, Change.ChangeType.VOID));
        this.coord = next;
        }else {
            this.game.incNbLemmingSortis();
            addChange(new Change(last, Change.ChangeType.VOID));
            notifyObserver();
            unregisterObserver(game);
            game.decremNbLemmings();
            this.game.killLemming(this);
            //this.game.makeAir(this.coord);
        }
        notifyObserver();
    }

    public void backToLemming() throws InterruptedException {
        Lemming l = new Lemming(game);
        l.coord = this.coord;
        l.first = false;
        l.onGround = true;
        l.first = false;
        l.direction = previous;
        l.previous = this.direction;
        l.changed = true;
        addChange(new Change(l.coord, Change.ChangeType.LEMMING));
        notifyObserver();
        game.newLemming(l, position);
        unregisterObserver(game);
        //game.killLemming(l);
    }

    @Override
    public void run() {
        while (isAlive()) {
            try {
                move();
                Thread.sleep(game.getSpeed() - 100);
            } catch (Exception ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

}
