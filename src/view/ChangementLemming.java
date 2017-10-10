/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.GameObjectObserver;
import java.awt.Button;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.List;
import javax.swing.JPanel;
import model.Bloqueur;
import model.Change;
import model.Game;

/**
 *
 * @author root
 */
public class ChangementLemming extends JPanel {

    private Button bloqueurButton;
    private Button bomberButton;
    private Button charpentierButton;
    private Button parachutisteButton;
    private Button grimpeurButton;
    private Button tunnelierButton;
    private Button forreurBoutton;

    public ChangementLemming(Game game) {
        //pour chaque boutton on s'assure qu'il soit le seul à être de couleur rouge
        //du coup il remet tout les autres en blanc ( quel égoïste :/ )
        bloqueurButton = new Button("Bloqueur");
        bloqueurButton.setBackground(Color.white);
        bloqueurButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((Button) e.getSource()).setBackground(Color.red);
                ((ChangementLemming) ((Button) e.getSource()).getParent()).getBomberButton().setBackground(Color.WHITE);
                ((ChangementLemming) ((Button) e.getSource()).getParent()).getCharpentierButton().setBackground(Color.WHITE);
                ((ChangementLemming) ((Button) e.getSource()).getParent()).getParachutisteButton().setBackground(Color.WHITE);
                ((ChangementLemming) ((Button) e.getSource()).getParent()).getGrimpeurButton().setBackground(Color.WHITE);
                ((ChangementLemming) ((Button) e.getSource()).getParent()).getTunnelierButton().setBackground(Color.WHITE);
                ((ChangementLemming) ((Button) e.getSource()).getParent()).getForreurBoutton().setBackground(Color.WHITE);
            }
        });
        bomberButton = new Button("Bombeur");
        bomberButton.setBackground(Color.white);
        bomberButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((Button) e.getSource()).setBackground(Color.red);
                ((ChangementLemming) ((Button) e.getSource()).getParent()).getBloqueurButton().setBackground(Color.WHITE);
                ((ChangementLemming) ((Button) e.getSource()).getParent()).getCharpentierButton().setBackground(Color.WHITE);
                ((ChangementLemming) ((Button) e.getSource()).getParent()).getParachutisteButton().setBackground(Color.WHITE);
                ((ChangementLemming) ((Button) e.getSource()).getParent()).getGrimpeurButton().setBackground(Color.WHITE);
                ((ChangementLemming) ((Button) e.getSource()).getParent()).getTunnelierButton().setBackground(Color.WHITE);
                ((ChangementLemming) ((Button) e.getSource()).getParent()).getForreurBoutton().setBackground(Color.WHITE);

            }
        });
        charpentierButton = new Button("Charpentier");
        charpentierButton.setBackground(Color.white);
        charpentierButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((Button) e.getSource()).setBackground(Color.red);
                ((ChangementLemming) ((Button) e.getSource()).getParent()).getBloqueurButton().setBackground(Color.WHITE);
                ((ChangementLemming) ((Button) e.getSource()).getParent()).getBomberButton().setBackground(Color.WHITE);
                ((ChangementLemming) ((Button) e.getSource()).getParent()).getParachutisteButton().setBackground(Color.WHITE);
                ((ChangementLemming) ((Button) e.getSource()).getParent()).getGrimpeurButton().setBackground(Color.WHITE);
                ((ChangementLemming) ((Button) e.getSource()).getParent()).getTunnelierButton().setBackground(Color.WHITE);
                ((ChangementLemming) ((Button) e.getSource()).getParent()).getForreurBoutton().setBackground(Color.WHITE);
            }
        });

        parachutisteButton = new Button("Parachutiste");
        parachutisteButton.setBackground(Color.white);
        parachutisteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((Button) e.getSource()).setBackground(Color.red);
                ((ChangementLemming) ((Button) e.getSource()).getParent()).getBloqueurButton().setBackground(Color.WHITE);
                ((ChangementLemming) ((Button) e.getSource()).getParent()).getCharpentierButton().setBackground(Color.WHITE);
                ((ChangementLemming) ((Button) e.getSource()).getParent()).getBomberButton().setBackground(Color.WHITE);
                ((ChangementLemming) ((Button) e.getSource()).getParent()).getGrimpeurButton().setBackground(Color.WHITE);
                ((ChangementLemming) ((Button) e.getSource()).getParent()).getTunnelierButton().setBackground(Color.WHITE);
                ((ChangementLemming) ((Button) e.getSource()).getParent()).getForreurBoutton().setBackground(Color.WHITE);
                
            }
        });
        grimpeurButton = new Button("Grimpeur");
        grimpeurButton.setBackground(Color.white);
        grimpeurButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((Button) e.getSource()).setBackground(Color.red);
                ((ChangementLemming) ((Button) e.getSource()).getParent()).getBloqueurButton().setBackground(Color.WHITE);
                ((ChangementLemming) ((Button) e.getSource()).getParent()).getCharpentierButton().setBackground(Color.WHITE);
                ((ChangementLemming) ((Button) e.getSource()).getParent()).getParachutisteButton().setBackground(Color.WHITE);
                ((ChangementLemming) ((Button) e.getSource()).getParent()).getBomberButton().setBackground(Color.WHITE);
                ((ChangementLemming) ((Button) e.getSource()).getParent()).getTunnelierButton().setBackground(Color.WHITE);
                ((ChangementLemming) ((Button) e.getSource()).getParent()).getForreurBoutton().setBackground(Color.WHITE);
            }
        });
        tunnelierButton = new Button("Tunnelier");
        tunnelierButton.setBackground(Color.white);
        tunnelierButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((Button) e.getSource()).setBackground(Color.red);
                ((ChangementLemming) ((Button) e.getSource()).getParent()).getBloqueurButton().setBackground(Color.WHITE);
                ((ChangementLemming) ((Button) e.getSource()).getParent()).getCharpentierButton().setBackground(Color.WHITE);
                ((ChangementLemming) ((Button) e.getSource()).getParent()).getParachutisteButton().setBackground(Color.WHITE);
                ((ChangementLemming) ((Button) e.getSource()).getParent()).getGrimpeurButton().setBackground(Color.WHITE);
                ((ChangementLemming) ((Button) e.getSource()).getParent()).getBomberButton().setBackground(Color.WHITE);
                ((ChangementLemming) ((Button) e.getSource()).getParent()).getForreurBoutton().setBackground(Color.WHITE);
            }
        });
        forreurBoutton = new Button("Forreur");
        forreurBoutton.setBackground(Color.white);
        forreurBoutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((Button) e.getSource()).setBackground(Color.red);
                ((ChangementLemming) ((Button) e.getSource()).getParent()).getBloqueurButton().setBackground(Color.WHITE);
                ((ChangementLemming) ((Button) e.getSource()).getParent()).getBomberButton().setBackground(Color.WHITE);
                ((ChangementLemming) ((Button) e.getSource()).getParent()).getParachutisteButton().setBackground(Color.WHITE);
                ((ChangementLemming) ((Button) e.getSource()).getParent()).getGrimpeurButton().setBackground(Color.WHITE);
                ((ChangementLemming) ((Button) e.getSource()).getParent()).getTunnelierButton().setBackground(Color.WHITE);
                ((ChangementLemming) ((Button) e.getSource()).getParent()).getCharpentierButton().setBackground(Color.WHITE);
            }
        });
        this.setBackground(Color.black);
        this.add(bloqueurButton);
        this.add(grimpeurButton);
        this.add(tunnelierButton);
        this.add(parachutisteButton);
        this.add(bomberButton);
        this.add(parachutisteButton);
        this.add(forreurBoutton);
        this.add(charpentierButton);

    }

    /**
     * @return the bloqueurButton
     */
    public Button getBloqueurButton() {
        return bloqueurButton;
    }

    /**
     * @param bloqueurButton the bloqueurButton to set
     */
    public void setBloqueurButton(Button bloqueurButton) {
        this.bloqueurButton = bloqueurButton;
    }

    /**
     * @return the bomberButton
     */
    public Button getBomberButton() {
        return bomberButton;
    }

    /**
     * @param bomberButton the bomberButton to set
     */
    public void setBomberButton(Button bomberButton) {
        this.bomberButton = bomberButton;
    }

    /**
     * @return the charpentierButton
     */
    public Button getCharpentierButton() {
        return charpentierButton;
    }

    /**
     * @param charpentierButton the charpentierButton to set
     */
    public void setCharpentierButton(Button charpentierButton) {
        this.charpentierButton = charpentierButton;
    }

    /**
     * @return the parachutisteButton
     */
    public Button getParachutisteButton() {
        return parachutisteButton;
    }

    /**
     * @return the grimpeurButton
     */
    public Button getGrimpeurButton() {
        return grimpeurButton;
    }

    /**
     * @return the tunnelierButton
     */
    public Button getTunnelierButton() {
        return tunnelierButton;
    }

    /**
     * @param tunnelierButton the tunnelierButton to set
     */
    public void setTunnelierButton(Button tunnelierButton) {
        this.tunnelierButton = tunnelierButton;
    }

    /**
     * @return the forreurBoutton
     */
    public Button getForreurBoutton() {
        return forreurBoutton;
    }
}
