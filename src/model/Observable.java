package model;

import java.util.ArrayList;
//import java.util.Iterator;
import java.util.List;

import controller.GameObjectObserver;

public abstract class Observable {

    private ArrayList<Change> changes = new ArrayList<>();

    private ArrayList<GameObjectObserver> observers;

    public Observable() {
        this.observers = new ArrayList<>();
    }

    protected synchronized void addChange(Change c) {
        changes.add(c);
    }

    protected synchronized void removeChange(Change c) {
     changes.remove(c);
     }
    public synchronized void registerObserver(GameObjectObserver observer) {
        this.observers.add(observer);
    }

    public synchronized void unregisterObserver(GameObjectObserver observer) {
        this.observers.remove(observer);
    }

    public synchronized void notifyObserver() {
        List<Change> lsChange = getChanges();
        for (GameObjectObserver obs : observers) {
            obs.update(lsChange);
        }
    }

    private List<Change> getChanges() {
        List<Change> result = new ArrayList<>(changes);
        changes.clear();
        return result;
    }

}
