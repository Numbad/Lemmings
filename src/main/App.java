package main;

import model.ExceptionCoordinate;
import model.Game;
import view.Gui;

public class App {

    private static final int SCALE = 9;
    private static final int WIDTH = 140;
    private static final int HEIGHT = 60;
    private static final int SPEED = 300;

    public static void main(String[] args) throws ExceptionCoordinate, InterruptedException {

        Game game = new Game(SPEED, HEIGHT, WIDTH);
        //game.chargeTerrain("niveau1.txt");
        Gui.createGui(game, SCALE);
        //GamePanel gamepanel = new GamePanel(game, SCALE);
        //gamepanel.paint(g);
        //game.chargeTerrain("niveau1.txt", SPEED);
        game.run();
    }
}
