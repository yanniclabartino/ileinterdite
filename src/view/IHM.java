/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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
    
    private JButton bDepl, bAss, bPioch, bGagner;
    private JLabel instructions;
    private ImagePanel tresor1, tresor2, tresor3, tresor4, niveauEau;
    private ImagePanel carte1, carte2, carte3, carte4, carte5, carte6, carte7, carte8, carte9;
    private ImagePanel cartesOranges, cartesBleues, defausseO, defausseB;
    
    private final int lfenetre = 1280;
    private final int hfenetre = 720;
    
    public IHM() {

        // INSTANCIATIONS DES ÉLÉMENTS DE L'IHM
        bDepl = new JButton("Déplacer");
        bAss = new JButton("Assécher");
        bPioch = new JButton("Piocher");
        bGagner = new JButton("Gagner un trésor");
        
        instructions = new JLabel("Instructions");
        
        tresor1 = new ImagePanel();
        tresor1 = new ImagePanel();
        tresor1 = new ImagePanel();
        tresor1 = new ImagePanel();
        niveauEau = new ImagePanel();
        
        carte1 = new ImagePanel();
        carte1 = new ImagePanel();
        carte1 = new ImagePanel();
        carte1 = new ImagePanel();
        carte1 = new ImagePanel();
        carte1 = new ImagePanel();
        carte1 = new ImagePanel();
        carte1 = new ImagePanel();
        carte1 = new ImagePanel();
        
        cartesOranges = new ImagePanel();
        cartesBleues = new ImagePanel();
        defausseO = new ImagePanel();
        defausseB = new ImagePanel();
        

        // INSTANCIATION DES JPANEL DE DISPOSITION
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
        JPanel layer4eastCenterNorth = new JPanel();

        // AFFECTATION DE TYPES AUX PANELS
        layer0.setLayout(new BorderLayout());

        layer1north.setLayout(new BorderLayout());
        layer1south.setLayout(new BorderLayout());

        layer2north.setLayout(new BorderLayout());
        layer2south.setLayout(new GridLayout(1, 6));
        layer2east.setLayout(new BorderLayout());
        layer2west.setLayout(new BorderLayout());

        layer3westCenter.setLayout(new GridLayout(2, 2, 5, 5));
        layer3eastCenter.setLayout(new BorderLayout());

        layer4eastCenterCenter.setLayout(new GridLayout(4, 1));
        layer4eastCenterNorth.setLayout(new GridLayout(1, 4));

        // PLACEMENT DES PANELS
        layer1north.add(layer2north, BorderLayout.NORTH);
        layer1north.add(layer2east, BorderLayout.EAST);
        layer1north.add(layer2west, BorderLayout.WEST);
        layer1north.add(layer2center, BorderLayout.CENTER);
        layer1south.add(layer2south, BorderLayout.CENTER);
        
        layer2west.add(layer3westCenter, BorderLayout.CENTER);
        layer2east.add(layer3eastCenter, BorderLayout.CENTER);
        
        layer3eastCenter.add(layer4eastCenterCenter, BorderLayout.CENTER);
        
        layer0.add(layer1north, BorderLayout.NORTH);
        layer0.add(layer1south, BorderLayout.SOUTH);
        
        // AJOUTS DES MARGES
        layer1south.add(new VidePanel(35, 900), BorderLayout.NORTH);
        layer1south.add(new VidePanel(190, 900), BorderLayout.EAST);
        layer1south.add(new VidePanel(190, 900), BorderLayout.WEST);
        layer1south.add(new VidePanel(40, 900), BorderLayout.SOUTH);
        
        layer2north.add(new VidePanel(400, 40), BorderLayout.NORTH);
        layer2north.add(new VidePanel(440, 95), BorderLayout.EAST);
        layer2north.add(new VidePanel(440, 65), BorderLayout.WEST);
        layer2north.add(new VidePanel(400, 25), BorderLayout.SOUTH);
        
        layer2east.add(new VidePanel(440, 15), BorderLayout.NORTH);
        layer2east.add(new VidePanel(120, 400), BorderLayout.EAST);
        layer2east.add(new VidePanel(80, 400), BorderLayout.WEST);
        layer2east.add(new VidePanel(440, 15), BorderLayout.SOUTH);
        
        layer2west.add(new VidePanel(440, 30), BorderLayout.NORTH);
        layer2west.add(new VidePanel(80, 400), BorderLayout.EAST);
        layer2west.add(new VidePanel(120, 400), BorderLayout.WEST);
        layer2west.add(new VidePanel(440, 30), BorderLayout.SOUTH);
        
        // PLACEMENT DES ÉLÉMENTS    
        layer2north.add(instructions, BorderLayout.CENTER);
        
        layer4eastCenterCenter.add(bDepl);
        layer4eastCenterCenter.add(bAss);
        layer4eastCenterCenter.add(bPioch);
        layer4eastCenterCenter.add(bGagner);

        
        this.add(layer0);
        // PROPRIÉTÉS DU JFRAME
        this.setTitle("L'Île Interdite");
        this.setSize(lfenetre, hfenetre); // équivalent 16:9
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(false);
    }

}
