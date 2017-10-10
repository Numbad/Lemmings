package controller;

import java.util.List;

import model.Change;

//import java.util.List;

public interface GameObjectObserver {
	public void update(List<? extends Change> o); 
}
