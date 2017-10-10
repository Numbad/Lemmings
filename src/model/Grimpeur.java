package model;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Grimpeur extends StateLemming {

    boolean blok = false;
    Coordinate last1;
    int position;

    public Grimpeur(Game game, Coordinate coordinate, Direction direction, int position) {
        super(game);
        this.game = game;
        this.coord = coordinate;
        this.direction = direction;
        onGround = true;
        nbPasDown = 0;
        previous = this.direction;
        addChange(new Change(coord, Change.ChangeType.GRIMPEUR));
        this.position = position;
    }

    @Override
    public void action() {
        Coordinate last = null;
        Coordinate lastDown = null;
        while (true) {
            last = this.coord;
            lastDown = new Coordinate(last.getX() + previous.x, last.getY() + previous.y);
            if (!game.isAir(lastDown)) {
                setDirection(Direction.Up);
                Coordinate next = new Coordinate(last.getX() + direction.x, last.getY() + direction.y);
                addChange(new Change(next, Change.ChangeType.GRIMPEUR));
                addChange(new Change(last, Change.ChangeType.VOID));
                notifyObserver();

                try {
                    Thread.sleep(game.getSpeed() - 100);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Grimpeur.class.getName()).log(Level.SEVERE, null, ex);
                }
                this.coord = next;
                blok = false;
            } else {
                addChange(new Change(last, Change.ChangeType.VOID));
                try {
                    backToLemming();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Grimpeur.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            }
        }
        this.direction = previous;
    }

    @Override
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public void changeDirection() {
        if (this.direction == Direction.Right) {
            this.direction = Direction.Left;
        } else if (this.direction == Direction.Left) {
            this.direction = Direction.Right;
        } else if (this.direction == Direction.Up) {
            this.direction = Direction.Down;
        }
    }

    public void backToLemming() throws InterruptedException {
        Lemming l = new Lemming(game);
        l.coord = this.coord;
        l.first = false;
        l.changed = true;
        l.onGround = true;
        l.first = false;
        l.direction = previous;
        l.previous = this.direction;
        addChange(new Change(l.coord, Change.ChangeType.LEMMING));
        notifyObserver();
        game.newLemming(l, position);
        unregisterObserver(game);
        //game.killLemming(l);
    }

    @Override
    public void move() {
        //on instancie les coordonnée du lemming
        //que quand il lui faudra bouger pour la 1ere fois
        Coordinate last = this.coord;
        Coordinate diagLeftUp = new Coordinate(last.getX() + Direction.DiagonalLeftUp.x, last.getY() + Direction.DiagonalLeftUp.y);
        Coordinate diagRightUp = new Coordinate(last.getX() + Direction.DiagonalRightUp.x, last.getY() + Direction.DiagonalRightUp.y);
        Coordinate up = new Coordinate(last.getX() + Direction.Up.x, last.getY() + Direction.Up.y);
        Coordinate next = new Coordinate(last.getX() + direction.x, last.getY() + direction.y);
        Coordinate down = new Coordinate(last.getX() + Direction.Down.x, last.getY() + Direction.Down.y);
        if (!this.game.isExit(next)) {
            if (game.isBordure(next)) {
                changeDirection();
                next = new Coordinate(last.getX() + direction.x, last.getY() + direction.y);
            } else {
                if (nbPasDown == 4) {
                    addChange(new Change(last, Change.ChangeType.VOID));
                    notifyObserver();
                    unregisterObserver(game);
                    game.decremNbLemmings();
                    this.game.killLemming(this);
                }
                if (this.game.isAir(down)) {
                    setDirection(Direction.Down);
                    if (!onGround) {
                        previous = this.direction;
                    }
                    nbPasDown++;
                } else if (!this.game.isAir(down) && this.direction == Direction.Down) {
                    nbPasDown = 0;
                    if (previous == Direction.Down) {
                        setDirection(setDirection());
                        onGround = true;
                    } else {
                        setDirection(previous);
                    }
                    previousDirection = Direction.Arret;
                    next = new Coordinate(last.getX() + direction.x, last.getY() + direction.y);
                }
                if (this.game.isAir(next) && !next.equals(down)) {
                    nbPasDown = 0;
                    next = new Coordinate(last.getX() + direction.x, last.getY() + direction.y);
                } else if (!this.game.isAir(next) && !next.equals(down)) {
                    nbPasDown = 0;
                    if (direction == Direction.Left) {
                        //previous = this.direction;
                        if ((game.isAir(diagLeftUp) && !game.isAir(next)) && game.isAir(up)) {
                            next = diagLeftUp;
                        } else {
                            previous = this.direction;
                            action();
                            next = new Coordinate(this.coord.getX() + direction.x, this.coord.getY() + direction.y);
                        }

                    } else if (direction == Direction.Right) {
                        if ((game.isAir(diagRightUp) && !game.isAir(next) && game.isAir(up))) {
                            next = diagRightUp;
                        } else {
                            previous = this.direction;
                            action();
                            next = new Coordinate(this.coord.getX() + direction.x, this.coord.getY() + direction.y);
                        }
                    }
                }
            }
            addChange(new Change(next, Change.ChangeType.GRIMPEUR));
            addChange(new Change(last, Change.ChangeType.VOID));
            this.coord = next;
        } else {
            this.game.incNbLemmingSortis();
            addChange(new Change(last, Change.ChangeType.VOID));
            notifyObserver();
            unregisterObserver(game);
            game.decremNbLemmings();
            this.game.killLemming(this);
        }
        notifyObserver();
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
