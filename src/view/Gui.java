package view;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import model.Game;

public class Gui {

    private static ChangementLemming ch;

    public static JFrame createGui(Game game, int scale) {
        return createGui(0, 0, game, scale);
    }

    public static JFrame createGui(int x, int y, Game game, int scale) {

        GamePanel gamePanel;
        ChangementLemming changementLemming;
        JFrame frame = new JFrame("Lemming");
        changementLemming = new ChangementLemming(game);

        ch = changementLemming;
        gamePanel = new GamePanel(game, scale,ch);
        frame.add(changementLemming, BorderLayout.PAGE_START);
        frame.add(gamePanel);
        frame.pack();
        frame.setLocation(x, y);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        gamePanel.requestFocusInWindow();
        frame.setVisible(true);
        return frame;

    }
}
