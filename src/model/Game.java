package model;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import controller.GameObjectObserver;
import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.Position;
import static model.Lemming.bombers;

public class Game extends Observable implements GameObjectObserver {

    //private Lemming lemming;
    private int height;
    private int width;
    private int speed;
    private List<String> decor;
    private List<Indestructible> indest;
    private List<Entree> entree;
    private List<Sortie> sortie;
    private List<Destructible> dest;
    private List<Bordure> bordure;
    private List<AirLibre> air;
    //chaque lemming tournera dans son propre thread
    private List<Thread> threads;
    private final int bordureX = 1;//d�tour du niveau
    private final int bordureY = 1;
    private List<Lemming> lemmings;

    private final int nbMaxLemmings = 4;
    private int nbLemmings = nbMaxLemmings + 1;
    private int nbLemmingsSortis = 0;
    private int temps = 5;

    public Game(int speed, int height, int width) {
        this.speed = speed;
        this.height = height;
        this.width = width;
        this.lemmings = new ArrayList<>();
    }

    @Override
    public void update(List<? extends Change> o) {
        for (Change c : o) {
            addChange(c);
        }
        notifyObserver();
    }

    public void changeLemmingDirection(Lemming lemming) {
        lemming.changeDirection();
    }

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }

    public boolean isOut(Coordinate c) {
        if (c.getX() < bordureX || c.getY() < bordureY) {
            return true;
        }
        if (c.getX() > width - 2 || c.getY() > height - 2) {
            return true;
        }
        return false;
    }

    //permet de changer un lemming (return vrai si oui) 
    //réagit quand l'utilisateur fait un click dans gamePanel
    public boolean isLemmingChanged(Coordinate c, String etat) throws InterruptedException {
        for (int i = 0; i < getLemmings().size(); i++) {
            //si l'élément sur lequel on a cliqué est
            //un lemming,n'est pas dans les air et est un lemming(pas une sous classe)
            if (getLemmings().get(i).coord != null) {
                if (getLemmings().get(i).isLemming(c) && !lemmings.get(i).isOnAir() && getLemmings().get(i).getClass().equals(Lemming.class)) {
                    //on stop le thread du lemming
                    killLemming(getLemmings().get(i));
                    //on remplace le lemming par son nouvel etat
                    getLemmings().set(i, getLemmings().get(i).changeLemming(etat, i));
                    threads.get(i).start();
                    return true;
                }
            }
        }
        return false;
    }

    public void killLemming(Lemming lemming) {
        for (int i = 0; i < getLemmings().size(); i++) {
            if (getLemmings().get(i).equals(lemming)) {
                try {
                    getLemmings().get(i).lemmingDead();
                    threads.get(i).join(10);
                } catch (Exception e) {
                    System.out.println("" + e);
                }
            }
        }
    }

    public void newLemming(Lemming l, int position) throws InterruptedException {
        killLemming(lemmings.get(position));
        lemmings.set(position, l);
        Thread t = new Thread(l);
        threads.set(position, t);
        threads.get(position).start();
    }

    public Lemming getLemming(Coordinate c) {
        for (Lemming l : getLemmings()) {
            if (l.isLemming(c)) {
                return l;
            }
        }
        return null;
    }

    public void removeLemming(Lemming l) throws InterruptedException {
        for (int i = 0; i < getLemmings().size(); i++) {
            if (getLemmings().get(i).equals(l)) {
                getLemmings().get(i).lemmingDead();
                getLemmings().remove(l);
                threads.get(i).join();
                threads.remove(i);
            }
        }
    }

    public boolean isAir(Coordinate c) {
        for (AirLibre air1 : this.getAir()) {
            if (air1.coordonnees.equals(c)) {
                return true;
            }
        }
        return false;
    }

    public boolean removeAir(Coordinate c) {
        for (AirLibre air1 : this.getAir()) {
            if (air1.coordonnees.equals(c)) {
                air.remove(air1);
                return  true;
            }
        }
        return false;
    }

    public boolean isIndestructible(Coordinate c) {
        for (Indestructible indestructible : this.indest) {
            if (indestructible.coordonnees.equals(c)) {
                return true;
            }
        }
        return false;
    }
    public boolean isBordure(Coordinate c) {
        for (Bordure bord : this.bordure) {
            if (bord.coordonnees.equals(c)) {
                return true;
            }
        }
        return false;
    }

    public boolean isDestructible(Coordinate c) {
        for (Destructible destructible : this.dest) {
            if (destructible.coordonnees.equals(c)) {
                return true;
            }
        }
        return false;
    }

    public Coordinate lastEntree() {
        int last = this.entree.size();
        Coordinate n = new Coordinate(entree.get(last - 3).coordonnees.getX(), entree.get(last - 3).coordonnees.getY() + 1);
        return n;
    }

    public void chargeTerrain(String nom) throws ExceptionCoordinate {

        this.decor = new ArrayList<String>();
        this.indest = new ArrayList<Indestructible>();
        this.setDest(new ArrayList<Destructible>());
        this.entree = new ArrayList<Entree>();
        this.sortie = new ArrayList<Sortie>();
        this.setAir(new ArrayList<AirLibre>());
        this.setBordure(new ArrayList<Bordure>());

        Path source = Paths.get(nom);
        try {
            decor = Files.readAllLines(source, StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.out.println("Erreur de lecture du fichier");
            System.out.println(e.getMessage());
        }
        int height = decor.size(), width = decor.get(0).length();
        int x, y;
        for (y = 0; y < height; y++) {
            for (x = 0; x < width; x++) {
                switch (this.decor.get(y).charAt(x)) {
                    case '#': 	//System.out.print("#");
                        Indestructible ind = new Indestructible(this, new Coordinate(x, y));
                        this.indest.add(ind);
                        addChange(new Change(new Coordinate(x, y), Change.ChangeType.INDESTRUCTIBLE));
                        break;
                    case 'E':
                        Entree ent = new Entree(this, new Coordinate(x, y));
                        //System.out.println(x + " " + y);
                        this.entree.add(ent);
                        addChange(new Change(new Coordinate(x, y), Change.ChangeType.ENTREE));
                        break;

                    case 'S':
                        Sortie sort = new Sortie(this, new Coordinate(x, y));
                        this.sortie.add(sort);
                        addChange(new Change(new Coordinate(x, y), Change.ChangeType.SORTIE));
                        break;

                    case 'D':
                        Destructible dest = new Destructible(this, new Coordinate(x, y));
                        this.getDest().add(dest);
                        addChange(new Change(new Coordinate(x, y), Change.ChangeType.DESTRUCTIBLE));
                        break;

                    case '-':
                        Bordure bord = new Bordure(this, new Coordinate(x, y));
                        this.getBordure().add(bord);
                        addChange(new Change(new Coordinate(x, y), Change.ChangeType.BORDURE));
                        break;

                    case ' ':
                        AirLibre air = new AirLibre(this, new Coordinate(x, y));
                        this.getAir().add(air);
                        addChange(new Change(new Coordinate(x, y), Change.ChangeType.VOID));
                        break;

                }
                this.height = this.getBordure().size() - 1;
                this.width = width - 1;
                notifyObserver();
            }
        }
    }

    public void run() throws ExceptionCoordinate, InterruptedException {
        chargeTerrain("/home/number/NetBeansProjects/trie/src/view/niveau1.txt");
        for (int i = 0; i <= nbMaxLemmings; i++) {
            Lemming lemmingg = new Lemming(this);
            Lemming.lemmings.add(lemmingg);
            this.getLemmings().add(lemmingg);
        }
        setThreads(threads);
        for (int i = 0; i < getLemmings().size(); i++) {
            //met chaque lemming dans un thread, puis le met en mouvement
            threads.add(new Thread(getLemmings().get(i)));
            //threads.get(i).setName("Thread " + i);
            threads.get(i).start();
            try {
                Thread.sleep(speed * 10);
            } catch (InterruptedException ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void makeAir(Coordinate c) {
        try {
            this.air.add(new AirLibre(this, c));
        } catch (ExceptionCoordinate e) {
            e.printStackTrace();
        }
    }

    //on vérifie si le lemming a atteint la sortie
    public boolean isExit(Coordinate c) {
        for (Sortie exit : this.sortie) {
            if (exit.coordonnees.equals(c)) {
                //System.out.println(exit.coordonnees.getX() + " " + exit.coordonnees.getY());
                return true;
            }
        }
        return false;
    }

    public int getSpeed() {
        return speed;
    }

    /**
     * @param speed the speed to set
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    /**
     * @return the threads
     */
    public List<Thread> getThreads() {
        return threads;
    }

    /**
     * @param threads the threads to set
     */
    public void setThreads(List<Thread> threads) {
        this.threads = new ArrayList<>();
    }

    /**
     * @return the dest
     */
    public List<Destructible> getDest() {
        return dest;
    }

    /**
     * @param dest the dest to set
     */
    public void setDest(List<Destructible> dest) {
        this.dest = dest;
    }

    /**
     * @return the bordure
     */
    public List<Bordure> getBordure() {
        return bordure;
    }

    /**
     * @param bordure the bordure to set
     */
    public void setBordure(List<Bordure> bordure) {
        this.bordure = bordure;
    }

    /**
     * @return the air
     */
    public List<AirLibre> getAir() {
        return air;
    }

    /**
     * @param air the air to set
     */
    public void setAir(List<AirLibre> air) {
        this.air = air;
    }

    /**
     * @return the lemmings
     */
    public List<Lemming> getLemmings() {
        return lemmings;
    }

    /**
     * @param lemmings the lemmings to set
     */
    public void setLemmings(List<Lemming> lemmings) {
        this.lemmings = lemmings;
    }

    public int getNbLemming() {
        return this.getNbLemmings();
    }

    public int getNbLemmingSortis() {
        return this.nbLemmingsSortis;
    }

    public void incNbLemmingSortis() {
        this.nbLemmingsSortis++;
    }

    /**
     * @return the nbLemmings
     */
    public int getNbLemmings() {
        return nbLemmings;
    }

    public void decremNbLemmings() {
        this.nbLemmings--;
    }
}
