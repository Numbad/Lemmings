package model;

//import java.util.LinkedList;
//import java.util.List;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Lemming extends Observable implements Runnable {

    protected boolean alive = true;
    protected Direction direction;
    protected Game game;
    protected Coordinate coord;
    protected Direction previousDirection = Direction.Arret;
    protected Direction previous = Direction.Arret;
    protected boolean onGround = false;
    private int speed;
    protected int nbPasDown;
    boolean changed = false;
    //permet de stocker tout les bloqueurs
    static List<Bloqueur> bloqueurs = new ArrayList<>();
    static List<Bombeur> bombers = new ArrayList<>();
    static List<Lemming> lemmings = new ArrayList<>();
    //variable (volatile) sera modifiée par plusieurs Threads
    protected volatile boolean killer;
    boolean first;
    private static final Random rnd = new Random();

    public Lemming(Game game) {
        this.direction = setDirection();
        this.game = game;
        registerObserver(game);
        killer = true;
        first = true;
        nbPasDown = 0;
    }

    public Direction setDirection() {
        boolean random = rnd.nextBoolean();
        if (random) {
            return Direction.Right;
        } else {
            return Direction.Left;
        }
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Direction getDirection() {
        return this.direction;
    }

    public void changeDirection() {
        if (this.direction == Direction.Right) {
            this.direction = Direction.Left;
        } else if (this.direction == Direction.Left) {
            this.direction = Direction.Right;
        } else {
            this.direction = Direction.Right;
        }
    }

    public void changeDirection(Direction direction) {
        if (direction == Direction.Right) {
            this.direction = Direction.Left;
        } else {
            this.direction = Direction.Right;
        }
    }

    public boolean isAlive() {
        return killer;
    }

    public boolean isLemming(Coordinate c) {
        return this.coord.equals(c);
    }

    public boolean isOnAir() {
        return this.direction.equals(Direction.Down);
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public Coordinate getCoordinate() {
        return this.coord;
    }

    public Lemming getLemming(Coordinate c) {
        for (Lemming l : lemmings) {
            if (l.isLemming(c)) {
                return l;
            }
        }
        return null;
    }

    public void move() {
        //on instancie les coordonnée du lemming
        //que quand il lui faudra bouger pour la 1ere fois
        if (first) {
            this.coord = new Coordinate(game.lastEntree().getX() + 1, game.lastEntree().getY() + 1);
            addChange(new Change(coord, Change.ChangeType.LEMMING));
            first = false;
        }
        Coordinate last = this.coord;
        Coordinate diagLeftUp = new Coordinate(last.getX() + Direction.DiagonalLeftUp.x, last.getY() + Direction.DiagonalLeftUp.y);
        Coordinate diagRightUp = new Coordinate(last.getX() + Direction.DiagonalRightUp.x, last.getY() + Direction.DiagonalRightUp.y);
        Coordinate up = new Coordinate(last.getX() + Direction.Up.x, last.getY() + Direction.Up.y);
        Coordinate next = new Coordinate(last.getX() + direction.x, last.getY() + direction.y);
        Coordinate down = new Coordinate(last.getX() + Direction.Down.x, last.getY() + Direction.Down.y);
        if (!this.game.isExit(next)) {
            if (nbPasDown == 4) {
                addChange(new Change(last, Change.ChangeType.VOID));
                notifyObserver();
                unregisterObserver(game);
                game.decremNbLemmings();
                this.game.killLemming(this);
            }
            if (this.game.isAir(down)) {
                if (!changed) {
                    setDirection(Direction.Down);
                }
                if (!onGround) {
                    previous = this.direction;
                }
                System.out.println("" + nbPasDown);
                changed = false;
                nbPasDown++;
            } else if (!this.game.isAir(down) && this.direction == Direction.Down){
                nbPasDown = 0;
                onGround = true;
                if (previous == Direction.Down) {
                    setDirection(setDirection());
                } else {
                    setDirection(previous);
                }
                previousDirection = Direction.Arret;
                next = new Coordinate(last.getX() + direction.x, last.getY() + direction.y);
            }
            if (this.game.isAir(next) && !next.equals(down)) {
                nbPasDown = 0;
                next = new Coordinate(last.getX() + direction.x, last.getY() + direction.y);
                if (!bloqueurs.isEmpty() && isNextTobloquer(next)) {
                    changeDirection();
                    next = new Coordinate(last.getX() + direction.x, last.getY() + direction.y);
                }
            } else if (!this.game.isAir(next) && !next.equals(down)) {
                nbPasDown = 0;
                onGround = true;
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
            addChange(new Change(next, Change.ChangeType.LEMMING));
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

    //permet de vérifier si le next step du lemming est un bloqueur ou pas
    public boolean isNextTobloquer(Coordinate c) {
        for (Bloqueur b : bloqueurs) {
            if (c.equals(b.getCoordinate())) {
                return true;
            }
        }
        return false;
    }

    public boolean deleteBloquer(Coordinate c) throws InterruptedException {
        for (int i = 0; i < bloqueurs.size(); i++) {
            if (c.equals(bloqueurs.get(i).getCoordinate())) {
                for (Thread t : game.getThreads()) {
                    if (t.getName().equals("bloque" + i)) {
                        System.out.println(" " + t.getName() + "bloque" + i);
                        bloqueurs.get(i).lemmingDead();
                        t.join(10);
                        bloqueurs.get(i).unregisterObserver(game);
                        game.decremNbLemmings();
                        //game.removeLemming(bloqueurs.get(i));
                        bloqueurs.remove(bloqueurs.get(i));
                        return true;
                    }

                }
            }
        }
        return false;
    }

    public boolean deleteBomber(Coordinate c) throws InterruptedException {
        for (int i = 0; i < bombers.size(); i++) {
            if (c.equals(bombers.get(i).getCoordinate())) {
                for (Thread t : game.getThreads()) {
                    if (t.getName().equals("bombe" + i)) {
                        System.out.println(" " + t.getName() + "bombe" + i);
                        //game.killLemming(bombers.get(i));
                        lemmingDead();
                        t.join(10);
                        bombers.get(i).unregisterObserver(game);
                        //game.removeLemming(bloqueurs.get(i));
                        bombers.remove(bombers.get(i));
                        return true;
                    }

                }
            }
        }
        return false;
    }

    //permet de changer l'état du lemming (en réalité on en crée un autre :D )
    //retourne le nouvel etat
    public Lemming changeLemming(String etat, int position) {
        Lemming l = null;
        Thread t = null;
        switch (etat) {
            case "bombe":
                l = new Bombeur(game, this.coord, this.direction);
                bombers.add((Bombeur) l);
                t = new Thread(l);
                t.setName(etat + (bombers.size() - 1));
                break;
            case "charpente":
                System.out.println("charpente");
                l = new Charpentier(game, this.coord, this.direction);
                break;
            case "grimpe":
                System.out.println("grimpe");
                l = new Grimpeur(game, this.coord, this.direction, position);
                break;
            case "forreur":
                System.out.println("forreur");
                l = new Foreur(game, this.coord, this.direction, position);
                break;
            case "parachute":
                System.out.println("parachute");
                l = new Parachutiste(game, this.coord, this.direction, position);
                break;
            case "tunnel":
                System.out.println("tunnel");
                l = new Tunnelier(game, this.coord, this.direction, position);
                break;
            case "bloque":
                l = new Bloqueur(game, this.coord);
                bloqueurs.add((Bloqueur) l);
                t = new Thread(l);
                t.setName(etat + (bloqueurs.size() - 1));
                break;
        }
        if (t == null) {
            t = new Thread(l);
        }
        game.getThreads().set(position, t);
        unregisterObserver(game);
        return l;
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

    //permet de stopper le thread du lemming
    public void lemmingDead() {
        this.killer = false;
    }

    protected String getUser() {
        return getClass().getName();
    }
}