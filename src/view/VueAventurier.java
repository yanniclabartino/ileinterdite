/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import ileinterdite.Message;
import ileinterdite.Observe;
import ileinterdite.TypesMessages;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import model.Grille;
import model.Tuile;
import util.NomTuile;

/**
 *
 * @author yannic
 */
public class VueAventurier extends Observe {

    private JFrame window;
    private JButton bDepl, bAss, bPioch, bGagner, bSpecial, bAnnuler, bFinir, bPerso;
    private JLabel instructions;

    private ImagePanel tresor1, tresor2, tresor3, tresor4, niveauEau;
    private ImagePanel carte1, carte2, carte3, carte4, carte5, carte6, carte7, carte8, carte9;
    private ImagePanel cartesOranges, cartesBleues, defausseO, defausseB, perso;
    private JPanel grille;

    private final int lfenetre = 1280;
    private final int hfenetre = 720;

    public VueAventurier() {

        // INSTANCIATIONS DES ÉLÉMENTS DE L'IHM
        window = new JFrame();

        bDepl = new JButton("Déplacer");
        bAss = new JButton("Assécher");
        bPioch = new JButton("Donner une carte");
        bGagner = new JButton("Gagner un trésor");
        bSpecial = new JButton("Jouer une carte spéciale");
        bAnnuler = new JButton("Annuler");
        bFinir = new JButton("Finir de jouer");
        bPerso = new JButton("Autres joueurs");
        bPerso.setPreferredSize(new Dimension(87,130));

        instructions = new JLabel("Instructions");

        tresor1 = new ImagePanel(60, 90, System.getProperty("user.dir") + "/src/images/tresors/calice.png");
        tresor2 = new ImagePanel(60, 90, System.getProperty("user.dir") + "/src/images/tresors/cristal.png");;
        tresor3 = new ImagePanel(60, 90, System.getProperty("user.dir") + "/src/images/tresors/pierre.png");
        tresor4 = new ImagePanel(60, 90, System.getProperty("user.dir") + "/src/images/tresors/zephyr.png");
        niveauEau = new ImagePanel(70, 240, System.getProperty("user.dir") + "/src/images/autre/Niveau.png");

        carte1 = new ImagePanel(67, 100, System.getProperty("user.dir") + "/src/images/cartes/LaPorteDeFer.png");
        carte2 = new ImagePanel(67, 100, System.getProperty("user.dir") + "/src/images/cartes/LaPorteDeFer.png");
        carte3 = new ImagePanel(67, 100, System.getProperty("user.dir") + "/src/images/cartes/LaPorteDeFer.png");
        carte4 = new ImagePanel(67, 100, System.getProperty("user.dir") + "/src/images/cartes/LaPorteDeFer.png");
        carte5 = new ImagePanel(67, 100, System.getProperty("user.dir") + "/src/images/cartes/LaPorteDeFer.png");
        carte6 = new ImagePanel(67, 100, System.getProperty("user.dir") + "/src/images/cartes/LaPorteDeFer.png");
        carte7 = new ImagePanel(67, 100, System.getProperty("user.dir") + "/src/images/cartes/LaPorteDeFer.png");
        carte8 = new ImagePanel(67, 100, System.getProperty("user.dir") + "/src/images/cartes/LaPorteDeFer.png");
        carte9 = new ImagePanel(67, 100, System.getProperty("user.dir") + "/src/images/cartes/LaPorteDeFer.png");

        cartesOranges = new ImagePanel(87, 130, System.getProperty("user.dir") + "/src/images/cartes/Fond rouge.png");
        cartesBleues = new ImagePanel(87, 130, System.getProperty("user.dir") + "/src/images/cartes/Fond bleu.png");
        defausseO = new ImagePanel(87, 130, System.getProperty("user.dir") + "/src/images/cartes/Fond rouge.png");
        defausseB = new ImagePanel(87, 130, System.getProperty("user.dir") + "/src/images/cartes/Fond bleu.png");
        perso = new ImagePanel(87, 130, System.getProperty("user.dir") + "/src/images/personnages/explorateur.png");

        ArrayList<Tuile> tuiles = new ArrayList<Tuile>();
        for (int i = 1; i < 25; i++) {
            tuiles.add(new Tuile(NomTuile.getFromNb(i)));
        }
        grille = new Grille(tuiles);
        grille.setPreferredSize(new Dimension(500, 500));
        grille.setBackground(new Color(50, 50, 230));

        // INSTANCIATION DES JPANEL DE DISPOSITION
        JPanel layer0 = new JPanel();

        JPanel layer1north = new JPanel();
        JPanel layer1south = new JPanel();

        JPanel layer2north = new JPanel();
        JPanel layer2south = new JPanel();
        JPanel layer2east = new JPanel();
        JPanel layer2west = new JPanel();

        JPanel layer3westCenter = new JPanel();
        JPanel layer3eastCenter = new JPanel();

        JPanel layer4eastCenterCenter = new JPanel();
        JPanel layer4eastCenterNorth = new JPanel();

        // AFFECTATION DE TYPES AUX PANELS
        layer0.setLayout(new BorderLayout());

        layer1north.setLayout(new BorderLayout());
        layer1south.setLayout(new BorderLayout());

        layer2north.setLayout(new BorderLayout());
        layer2south.setLayout(new GridLayout(1, 9));
        layer2east.setLayout(new BorderLayout());
        layer2west.setLayout(new BorderLayout());

        layer3westCenter.setLayout(new GridLayout(3, 2));
        layer3westCenter.setPreferredSize(new Dimension(200, 300));
        layer3eastCenter.setLayout(new BorderLayout());

        layer4eastCenterCenter.setLayout(new GridLayout(7, 1));
        layer4eastCenterNorth.setLayout(new GridLayout(1, 4));

        // PLACEMENT DES PANELS
        layer1north.add(layer2north, BorderLayout.NORTH);
        layer1north.add(layer2east, BorderLayout.EAST);
        layer1north.add(layer2west, BorderLayout.WEST);

        layer1south.add(layer2south, BorderLayout.CENTER);

        layer2west.add(layer3westCenter, BorderLayout.CENTER);
        layer2east.add(layer3eastCenter, BorderLayout.CENTER);

        layer3eastCenter.add(layer4eastCenterCenter, BorderLayout.CENTER);
        layer3eastCenter.add(layer4eastCenterNorth, BorderLayout.NORTH);

        layer0.add(layer1north, BorderLayout.NORTH);
        layer0.add(layer1south, BorderLayout.SOUTH);

        // AJOUTS DES MARGES
        layer1south.add(new VidePanel(200, 35), BorderLayout.NORTH);
        layer1south.add(new VidePanel(190, 1), BorderLayout.EAST);
        layer1south.add(new VidePanel(190, 1), BorderLayout.WEST);
        layer1south.add(new VidePanel(1, 20), BorderLayout.SOUTH);

        layer2north.add(new VidePanel(400, 25), BorderLayout.NORTH);
        layer2north.add(new VidePanel(400, 0), BorderLayout.EAST);
        layer2north.add(new VidePanel(400, 0), BorderLayout.WEST);
        layer2north.add(new VidePanel(400, 10), BorderLayout.SOUTH);

        layer2east.add(new VidePanel(300, 15), BorderLayout.NORTH);
        layer2east.add(new VidePanel(80, 400), BorderLayout.EAST);
        layer2east.add(new VidePanel(60, 400), BorderLayout.WEST);
        layer2east.add(new VidePanel(300, 15), BorderLayout.SOUTH);

        layer2west.add(new VidePanel(300, 60), BorderLayout.NORTH);
        layer2west.add(new VidePanel(60, 400), BorderLayout.EAST);
        layer2west.add(new VidePanel(80, 400), BorderLayout.WEST);
        layer2west.add(new VidePanel(300, 60), BorderLayout.SOUTH);

        // AJOUT DES ÉLÉMENTS    
        layer1north.add(grille, BorderLayout.CENTER);

        layer2north.add(instructions, BorderLayout.CENTER);

        layer2south.add(carte1);
        layer2south.add(carte2);
        layer2south.add(carte3);
        layer2south.add(carte4);
        layer2south.add(carte5);
        layer2south.add(carte6);
        layer2south.add(carte7);
        layer2south.add(carte8);
        layer2south.add(carte9);

        layer3eastCenter.add(niveauEau, BorderLayout.WEST);

        layer3westCenter.add(defausseO);
        layer3westCenter.add(cartesOranges);
        layer3westCenter.add(defausseB);
        layer3westCenter.add(cartesBleues);
        layer3westCenter.add(perso);
        layer3westCenter.add(bPerso);

        layer4eastCenterCenter.add(bDepl);
        layer4eastCenterCenter.add(bAss);
        layer4eastCenterCenter.add(bPioch);
        layer4eastCenterCenter.add(bGagner);
        layer4eastCenterCenter.add(bSpecial);
        layer4eastCenterCenter.add(bAnnuler);
        layer4eastCenterCenter.add(bFinir);

        layer4eastCenterNorth.add(tresor1);
        layer4eastCenterNorth.add(tresor2);
        layer4eastCenterNorth.add(tresor3);
        layer4eastCenterNorth.add(tresor4);

        window.add(layer0);

        //ACTION BOUTON
        bDepl.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Message m = new Message();
                m.type = TypesMessages.SOUHAITE_DEPLACEMENT;

                notifierObservateur(m);
            }
        });
        bAnnuler.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Message m = new Message();
                m.type = TypesMessages.ANNULER;

                notifierObservateur(m);
            }
        });
        bAss.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Message m = new Message();
                m.type = TypesMessages.SOUHAITE_ASSECHER;

                notifierObservateur(m);
            }
        });
        bFinir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Message m = new Message();
                m.type = TypesMessages.FINIR_TOUR;

                notifierObservateur(m);
            }
        });

        bGagner.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Message m = new Message();
                m.type = TypesMessages.ACTION_GAGNER_TRESOR;

                notifierObservateur(m);
            }
        });

        bPioch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Message m = new Message();
                m.type = TypesMessages.SOUHAITE_DONNER;

                notifierObservateur(m);
            }
        });
        bSpecial.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Message m = new Message();
                m.type = TypesMessages.SOUHAITE_JOUER_SPECIALE;

                notifierObservateur(m);
            }
        });
        // PROPRIÉTÉS DU JFRAME
        window.setTitle("L'Île Interdite");
        window.setSize(lfenetre, hfenetre); // équivalent 16:9
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
        window.setResizable(false);
    }

}
