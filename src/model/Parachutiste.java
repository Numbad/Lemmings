package model;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Parachutiste extends StateLemming {

    private int position;
    int onGroundcpt = 1;

    public Parachutiste(Game game, Coordinate coordinate, Direction direction, int positon) {
        super(game);
        this.game = game;
        this.coord = coordinate;
        this.direction = direction;
        onGround = false;

        previous = this.direction;
        this.position = positon;
        addChange(new Change(coord, Change.ChangeType.FOREUR));
    }

    @Override
    public void action() {
        try {
            Thread.sleep(game.getSpeed() - 100);
        } catch (InterruptedException ex) {
            Logger.getLogger(Parachutiste.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void move() {
        //on instancie les coordonnée du lemming
        //que quand il lui faudra bouger pour la 1ere fois
        action();
        Coordinate last = this.coord;
        Coordinate diagLeftUp = new Coordinate(last.getX() + Direction.DiagonalLeftUp.x, last.getY() + Direction.DiagonalLeftUp.y);
        Coordinate diagRightUp = new Coordinate(last.getX() + Direction.DiagonalRightUp.x, last.getY() + Direction.DiagonalRightUp.y);
        Coordinate up = new Coordinate(last.getX() + Direction.Up.x, last.getY() + Direction.Up.y);
        Coordinate next = new Coordinate(last.getX() + direction.x, last.getY() + direction.y);
        Coordinate down = new Coordinate(last.getX() + Direction.Down.x, last.getY() + Direction.Down.y);
        if (!this.game.isExit(next)) {
            if (this.game.isAir(down)) {
                setDirection(Direction.Down);
                if (!onGround) {
                    previous = this.direction;
                }
                System.out.println("" + nbPasDown);
            } else if (!this.game.isAir(down) && this.direction == Direction.Down) {
                onGround = true;
                if (onGround) {
                    try {
                        backToLemming();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Parachutiste.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                if (previous == Direction.Down) {
                    setDirection(setDirection());
                } else {
                    setDirection(previous);
                }
                previousDirection = Direction.Arret;
                next = new Coordinate(last.getX() + direction.x, last.getY() + direction.y);
            }
            if (this.game.isAir(next) && !next.equals(down)) {
                if (onGround) {
                    try {
                        backToLemming();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Parachutiste.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                nbPasDown = 0;
                next = new Coordinate(last.getX() + direction.x, last.getY() + direction.y);
                if (!bloqueurs.isEmpty() && isNextTobloquer(next)) {
                    changeDirection();
                    next = new Coordinate(last.getX() + direction.x, last.getY() + direction.y);
                }
            } else if (!this.game.isAir(next) && !next.equals(down)) {
                nbPasDown = 0;
                if (direction == Direction.Left) {
                    //previous = this.direction;
                    if ((game.isAir(diagLeftUp) && !game.isAir(next)) && game.isAir(up)) {
                        next = diagLeftUp;
                    } else {
                        changeDirection();
                        next = new Coordinate(last.getX() + direction.x, last.getY() + direction.y);
                    }
                    previous = this.direction;
                } else if (direction == Direction.Right) {
                    if ((game.isAir(diagRightUp) && !game.isAir(next) && game.isAir(up))) {
                        next = diagRightUp;
                    } else {
                        changeDirection();
                        next = new Coordinate(last.getX() + direction.x, last.getY() + direction.y);
                    }
                    previous = this.direction;
                }
            }
            addChange(new Change(next, Change.ChangeType.PARACHUTISTE));
            addChange(new Change(last, Change.ChangeType.VOID));
            this.coord = next;
        } else {
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
        l.onGround = false;
        l.first = false;
        l.previous = this.direction;
        l.direction = previous;
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
