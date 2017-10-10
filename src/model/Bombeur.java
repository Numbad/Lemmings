package model;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Bombeur extends StateLemming {

    private int nombreDePas = 1;
    private boolean nextToBloker = false;
    private Direction prevDir = Direction.Arret;
    private Coordinate nexthere;
    private boolean arroudObs = false;

    public Bombeur(Game game, Coordinate coordinate, Direction direction) {
        super(game);
        this.game = game;
        this.coord = coordinate;
        this.direction = direction;
        addChange(new Change(coord, Change.ChangeType.BOMBEUR));
    }

    @Override
    public void action() {
        Coordinate destruction = new Coordinate(coord.getX() + 1, coord.getY());
        Lemming l = null;
        if (!game.isIndestructible(destruction)) {
            if (isNextTobloquer(destruction)) {
                nextToBloker = true;
                nexthere = destruction;
            }
            if (!game.isAir(destruction)) {
                try {
                    game.getAir().add(new AirLibre(game, destruction));
                } catch (ExceptionCoordinate ex) {
                    Logger.getLogger(Bombeur.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            addChange(new Change(destruction, Change.ChangeType.VOID));

        }
        destruction = new Coordinate(coord.getX() + 2, coord.getY());
        if (!game.isIndestructible(destruction)) {
            if (isNextTobloquer(destruction)) {
                nextToBloker = true;
                nexthere = destruction;
            }
            if (!game.isAir(destruction)) {
                try {
                    game.getAir().add(new AirLibre(game, destruction));
                } catch (ExceptionCoordinate ex) {
                    Logger.getLogger(Bombeur.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            addChange(new Change(destruction, Change.ChangeType.VOID));

        }
        destruction = new Coordinate(coord.getX(), coord.getY() + 1);
        if (!game.isIndestructible(destruction)) {
            if (!game.isAir(destruction)) {
                try {
                    game.getAir().add(new AirLibre(game, destruction));
                } catch (ExceptionCoordinate ex) {
                    Logger.getLogger(Bombeur.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (isNextTobloquer(destruction)) {
                nextToBloker = true;
                nexthere = destruction;
            }
            if (!game.isAir(destruction)) {
                try {
                    game.getAir().add(new AirLibre(game, destruction));
                } catch (ExceptionCoordinate ex) {
                    Logger.getLogger(Bombeur.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            addChange(new Change(destruction, Change.ChangeType.VOID));
        }
        destruction = new Coordinate(coord.getX(), coord.getY() + 2);
        if (!game.isIndestructible(destruction)) {
            if (isNextTobloquer(destruction)) {
                nextToBloker = true;
                nexthere = destruction;
            }
            if (!game.isAir(destruction)) {
                try {
                    game.getAir().add(new AirLibre(game, destruction));
                } catch (ExceptionCoordinate ex) {
                    Logger.getLogger(Bombeur.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (!game.isAir(destruction)) {
                try {
                    game.getAir().add(new AirLibre(game, destruction));
                } catch (ExceptionCoordinate ex) {
                    Logger.getLogger(Bombeur.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            addChange(new Change(destruction, Change.ChangeType.VOID));
        }
        destruction = new Coordinate(coord.getX() - 1, coord.getY());
        if (!game.isIndestructible(destruction)) {
            if (isNextTobloquer(destruction)) {
                nextToBloker = true;
                nexthere = destruction;
            }
            if (!game.isAir(destruction)) {
                try {
                    game.getAir().add(new AirLibre(game, destruction));
                } catch (ExceptionCoordinate ex) {
                    Logger.getLogger(Bombeur.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (!game.isAir(destruction)) {
                try {
                    game.getAir().add(new AirLibre(game, destruction));
                } catch (ExceptionCoordinate ex) {
                    Logger.getLogger(Bombeur.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            addChange(new Change(destruction, Change.ChangeType.VOID));
        }
        destruction = new Coordinate(coord.getX() - 2, coord.getY());
        if (!game.isIndestructible(destruction)) {
            if (isNextTobloquer(destruction)) {
                nextToBloker = true;
                nexthere = destruction;
            }
            if (!game.isAir(destruction)) {
                try {
                    game.getAir().add(new AirLibre(game, destruction));
                } catch (ExceptionCoordinate ex) {
                    Logger.getLogger(Bombeur.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (!game.isAir(destruction)) {
                try {
                    game.getAir().add(new AirLibre(game, destruction));
                } catch (ExceptionCoordinate ex) {
                    Logger.getLogger(Bombeur.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            addChange(new Change(destruction, Change.ChangeType.VOID));
        }
        destruction = new Coordinate(coord.getX(), coord.getY() - 1);
        if (!game.isIndestructible(destruction)) {
            if (isNextTobloquer(destruction)) {
                nextToBloker = true;
                nexthere = destruction;
            }
            if (!game.isAir(destruction)) {
                try {
                    game.getAir().add(new AirLibre(game, destruction));
                } catch (ExceptionCoordinate ex) {
                    Logger.getLogger(Bombeur.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (!game.isAir(destruction)) {
                try {
                    game.getAir().add(new AirLibre(game, destruction));
                } catch (ExceptionCoordinate ex) {
                    Logger.getLogger(Bombeur.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            addChange(new Change(destruction, Change.ChangeType.VOID));
        }
        destruction = new Coordinate(coord.getX(), coord.getY() - 2);
        if (!game.isIndestructible(destruction)) {
            if (isNextTobloquer(destruction)) {
                nextToBloker = true;
                nexthere = destruction;
            }
            if (!game.isAir(destruction)) {
                try {
                    game.getAir().add(new AirLibre(game, destruction));
                } catch (ExceptionCoordinate ex) {
                    Logger.getLogger(Bombeur.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (!game.isAir(destruction)) {
                try {
                    game.getAir().add(new AirLibre(game, destruction));
                } catch (ExceptionCoordinate ex) {
                    Logger.getLogger(Bombeur.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            addChange(new Change(destruction, Change.ChangeType.VOID));
        }
    }

    @Override
    public void move() {
        //on instancie les coordonnée du lemming
        //que quand il lui faudra bouger pour la 1ere fois
        Coordinate last = this.coord;
        Coordinate next = new Coordinate(last.getX() + direction.x, last.getY() + direction.y);
        Coordinate down = new Coordinate(last.getX() + Direction.Down.x, last.getY() + Direction.Down.y);
        if (this.game.isAir(down)) {
            setDirection(Direction.Down);
        } else if (!this.game.isAir(down) && this.direction == Direction.Down) {
            if (previousDirection == Direction.Arret) {
                setDirection(setDirection());
            } else {
                setDirection(previousDirection);
            }
            previousDirection = Direction.Arret;
            next = new Coordinate(last.getX() + direction.x, last.getY() + direction.y);
            nexthere = next;
        }

        if (this.game.isAir(next)) {
            next = new Coordinate(last.getX() + direction.x, last.getY() + direction.y);
            if (!Bloqueur.bloqueurs.isEmpty() && isNextTobloquer(next) && !nextToBloker) {
                prevDir = this.direction;
                stopMoving();
                arroudObs = true;
                next = new Coordinate(last.getX() + direction.x, last.getY() + direction.y);
                nextToBloker = true;
            }

        } else {
            if (this.direction == Direction.Right
                    && this.game.isAir(new Coordinate(next.getX() + Direction.DiagonalRightUp.x, next.getY() + Direction.DiagonalRightUp.y))) {
                next = new Coordinate(next.getX() + Direction.DiagonalRightUp.x, next.getY() + Direction.DiagonalRightUp.y);
                previousDirection = Direction.Right;
            } else if (this.direction == Direction.Left
                    && this.game.isAir(new Coordinate(next.getX() + Direction.DiagonalLeftUp.x, next.getY() + Direction.DiagonalLeftUp.y))) {
                next = new Coordinate(next.getX() + Direction.DiagonalLeftUp.x, next.getY() + Direction.DiagonalLeftUp.y);
                previousDirection = Direction.Left;
            } else {
                nexthere = new Coordinate(last.getX() + direction.x, last.getY() + direction.y);
                stopMoving();
                arroudObs = true;
                next = new Coordinate(last.getX() + direction.x, last.getY() + direction.y);
            }
        }
        if (nextToBloker) {
            nexthere = new Coordinate(last.getX() + prevDir.x, last.getY() + prevDir.y);
        }
        addChange(new Change(next, Change.ChangeType.BOMBEUR));
        if (!arroudObs) {
            addChange(new Change(last, Change.ChangeType.VOID));
        }
        this.coord = next;
        notifyObserver();
        System.out.println("" + nombreDePas);

    }

    public void kill() throws InterruptedException {
        Coordinate last = this.coord;
        System.out.println("boum !");
        addChange(new Change(last, Change.ChangeType.VOID));
        action();
        notifyObserver();
        if (nextToBloker) {
            try {
                deleteBloquer(nexthere);
            } catch (InterruptedException ex) {
                Logger.getLogger(Bombeur.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            deleteBomber(last);
        } catch (InterruptedException ex) {
            Logger.getLogger(Bombeur.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void stopMoving() {
        this.direction = Direction.Arret;
    }

    @Override
    public void run() {
        while (isAlive()) {
            try {
                System.out.println("mouve");
                if (nombreDePas <= 3) {
                    move();
                    if (nombreDePas == 3) {
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
