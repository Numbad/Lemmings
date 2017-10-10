package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.EnumMap;
import java.util.List;

import javax.swing.JComponent;

import controller.GameObjectObserver;
import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Change;
import model.Coordinate;
import model.Game;

public class GamePanel extends JComponent implements GameObjectObserver {

    private final EnumMap<Change.ChangeType, Color> color;
    private static final long serialVersionUID = -746841458539162873L;
    private int rdm = 1;
    private BufferedImage img;
    ChangementLemming ch;
    private int scale;
    private Game game;

    class MyClickListener extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {
            Coordinate c = new Coordinate(e.getX() / scale, e.getY() / scale);
            //on vérifie aprés chaque click
            //que le user l'a fait sur un bouton de du JFrame changement lemming (coulerrouge du boutton)
            //et que le user a cliqué sur un lemming(en envoyant les coordonnées du click)
            //et en envoyant le nom du boutton pour le changement d'état (dans game) 
            try {
                if (ch.getBloqueurButton().getBackground() == Color.red && game.isLemmingChanged(c, "bloque")) {
                    System.out.println("bloque");
                }
                if (ch.getBomberButton().getBackground() == Color.red && game.isLemmingChanged(c, "bombe")) {
                    System.out.println("bombe");
                }
                if (ch.getForreurBoutton().getBackground() == Color.red && game.isLemmingChanged(c, "forreur")) {
                    System.out.println("forreur");
                }
                if (ch.getCharpentierButton().getBackground() == Color.red && game.isLemmingChanged(c, "charpente")) {
                    System.out.println("charpente");
                }
                if (ch.getGrimpeurButton().getBackground() == Color.red && game.isLemmingChanged(c, "grimpe")) {
                    System.out.println("grimpe");
                }
                if (ch.getParachutisteButton().getBackground() == Color.red && game.isLemmingChanged(c, "parachute")) {
                    System.out.println("parachute");
                }
                if (ch.getTunnelierButton().getBackground() == Color.red && game.isLemmingChanged(c, "tunnel")) {
                    System.out.println("tunnel");
                }

            } catch (InterruptedException ex) {
                Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    public GamePanel(Game game, int scale, ChangementLemming ch) {
        this.scale = scale;
        this.game = game;
        this.ch = ch;
        int width = game.getWidth() * scale;
        int height = game.getHeight() * scale;
        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        setPreferredSize(new Dimension(width, height));
        game.registerObserver(this);
        MouseAdapter cl1 = new MyClickListener();
        this.addMouseListener(cl1);
        color = new EnumMap<>(Change.ChangeType.class);
        color.put(Change.ChangeType.LEMMING, Color.ORANGE);
        color.put(Change.ChangeType.VOID, Color.BLACK);
        color.put(Change.ChangeType.INDESTRUCTIBLE, Color.BLUE);
        color.put(Change.ChangeType.DESTRUCTIBLE, Color.WHITE);
        color.put(Change.ChangeType.ENTREE, Color.YELLOW);
        color.put(Change.ChangeType.SORTIE, Color.RED);
        color.put(Change.ChangeType.BORDURE, Color.GRAY);
        color.put(Change.ChangeType.TUNNELIER, Color.PINK);
        color.put(Change.ChangeType.BLOQUEUR, Color.GREEN);
        color.put(Change.ChangeType.CHARPENTIER, Color.DARK_GRAY);
        color.put(Change.ChangeType.GRIMPEUR, Color.cyan);
        color.put(Change.ChangeType.FOREUR, Color.magenta);
        color.put(Change.ChangeType.PARACHUTISTE, Color.pink);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, 0, 0, null);
        g.setColor(Color.WHITE);
        g.drawString("Nombre de lemmings : " + this.game.getNbLemming(), getWidth() - 250, 15);
        g.drawString("Nombre de lemmings sortis : " + this.game.getNbLemmingSortis(), getWidth() - 250, 30);

    }

    @Override
    public void update(List<? extends Change> o) {
        Graphics g = img.getGraphics();
        for (Change c : o) {
            g.setColor(color.get(c.getChangeType()));
            g.fillRect(c.getCoordinate().getX() * scale, c.getCoordinate().getY() * scale, scale, scale);
        }
        g.dispose();
        repaint();
    }

}
