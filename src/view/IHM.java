/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author yannic
 */
public class IHM extends JFrame {

    private JButton bDepl;
    private JButton bAss;
    private JButton bPioch;
    private JButton bGagner;
    private JLabel layer3north, layer3west, grid1, grid2, grid3, grid4;
    private JLabel layer2westnorth, layer2westwest, layer2westest, layer2westsouth;
    private JLabel layer2northwest, layer2northcenter, layer2northest;
    private JLabel layer2eastnorth, layer2eastwest, layer2easteast, layer2eastsouth;
    private JLabel vide1,vide2,layer2grid1, layer2grid2, layer2grid3, layer2grid4, layer2grid5, layer2grid6;

    public IHM() {

        bDepl = new JButton("Déplacer");
        bAss = new JButton("Assécher");
        bPioch = new JButton("Piocher");
        bGagner = new JButton("Gagner un trésor");

        layer3north = new JLabel("NORTH");
        layer3west = new JLabel("WEST");

        grid1 = new JLabel("grid1");
        grid2 = new JLabel("grid2");
        grid3 = new JLabel("grid3");
        grid4 = new JLabel("grid4");

        layer2westnorth = new JLabel("NORTH");
        layer2westest = new JLabel("EAST");
        layer2westsouth = new JLabel("SOUTH");
        layer2westwest = new JLabel("WEST");

        layer2northcenter = new JLabel("CENTER");
        layer2northest = new JLabel("EAST");
        layer2northwest = new JLabel("WEST");

        layer2eastnorth = new JLabel("NORTH");
        layer2easteast = new JLabel("EAST");
        layer2eastsouth = new JLabel("SOUTH");
        layer2eastwest = new JLabel("WEST");

        layer2grid1 = new JLabel("grid1");
        layer2grid2 = new JLabel("grid2");
        layer2grid3 = new JLabel("grid3");
        layer2grid4 = new JLabel("grid4");
        layer2grid5 = new JLabel("grid5");
        layer2grid6 = new JLabel("grid6");
        
        vide1=new JLabel("vide");
        vide2=new JLabel("vide");

        // CRÉATION DES JPANEL
        JPanel layer0 = new JPanel();

        JPanel layer1north = new JPanel();
        JPanel layer1south = new JPanel();

        JPanel layer2north = new JPanel();
        JPanel layer2south = new JPanel();
        JPanel layer2east = new JPanel();
        JPanel layer2west = new JPanel();
        JPanel layer2center = new JPanel();

        JPanel layer3westCenter = new JPanel();
        JPanel layer3eastCenter = new JPanel();

        JPanel layer4eastCenterCenter = new JPanel();

        // AFFECTATION DE TYPES AUX PANELS
        layer0.setLayout(new BorderLayout()); // = layer 0

        layer1north.setLayout(new BorderLayout());
        layer1south.setLayout(new BorderLayout());

        layer2north.setLayout(new BorderLayout());
        layer2south.setLayout(new GridLayout(1, 6));
        layer2east.setLayout(new BorderLayout());
        layer2west.setLayout(new BorderLayout());

        layer3westCenter.setLayout(new GridLayout(2, 2));
        layer3eastCenter.setLayout(new BorderLayout());

        layer4eastCenterCenter.setLayout(new GridLayout(4, 1));

        // PLACEMENT DES PANELS
        layer3westCenter.add(grid1);
        layer3westCenter.add(grid2);
        layer3westCenter.add(grid3);
        layer3westCenter.add(grid4);

        layer3eastCenter.add(layer4eastCenterCenter, BorderLayout.CENTER);
        layer3eastCenter.add(layer3north, BorderLayout.NORTH);
        layer3eastCenter.add(layer3west, BorderLayout.WEST);

        layer2west.add(layer2westnorth, BorderLayout.NORTH);
        layer2west.add(layer2westest, BorderLayout.EAST);
        layer2west.add(layer2westwest, BorderLayout.WEST);
        layer2west.add(layer2westsouth, BorderLayout.SOUTH);

        layer2west.add(layer3westCenter, BorderLayout.CENTER);

        layer2north.add(layer2northwest, BorderLayout.WEST);
        layer2north.add(layer2northcenter, BorderLayout.CENTER);
        layer2north.add(layer2northest, BorderLayout.EAST);

        layer2east.add(layer2eastnorth, BorderLayout.NORTH);
        layer2east.add(layer2easteast, BorderLayout.EAST);
        layer2east.add(layer2eastwest, BorderLayout.WEST);
        layer2east.add(layer2eastsouth, BorderLayout.SOUTH);
        layer2east.add(layer3eastCenter, BorderLayout.CENTER);

        layer2south.add(layer2grid1);
        layer2south.add(layer2grid2);
        layer2south.add(layer2grid3);
        layer2south.add(layer2grid4);
        layer2south.add(layer2grid5);
        layer2south.add(layer2grid6);
        
        layer1north.add(layer2north, BorderLayout.NORTH);
        layer1north.add(layer2east, BorderLayout.EAST);
        layer1north.add(layer2west, BorderLayout.WEST);
        layer1north.add(layer2center, BorderLayout.CENTER);
        layer1south.add(vide1,BorderLayout.WEST);
        layer1south.add(vide2,BorderLayout.EAST);
        layer1south.add(layer2south, BorderLayout.CENTER);

        layer0.add(layer1north, BorderLayout.NORTH);
        layer0.add(layer1south, BorderLayout.SOUTH);

        //AJOUT ZONE DE TEXTE
        layer3eastCenter.add(layer3north, BorderLayout.NORTH);
        // PLACEMENT DES BOUTONS
        layer4eastCenterCenter.add(bDepl);
        layer4eastCenterCenter.add(bAss);
        layer4eastCenterCenter.add(bPioch);
        layer4eastCenterCenter.add(bGagner);

        this.add(layer0);
        // PROPRIÉTÉS DU JFRAME
        this.setTitle("L'Île Interdite");
        this.setSize(1000, 563); // équivalent 16:9
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

}
