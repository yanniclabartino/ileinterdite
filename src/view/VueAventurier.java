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
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import static javax.swing.SwingConstants.CENTER;
import model.CarteOrange;
import model.CarteTrésor;
import model.Grille;
import model.Tuile;
import util.NomTuile;

/**
 *
 * @author yannic
 */
public class VueAventurier extends Observe {

    public static final int ETAT_COMMENCER = 1;
    public static final int ETAT_SOUHAITE_DEPLACEMENT = 2;
    public static final int ETAT_DEPLACEMENT = 3;
    public static final int ETAT_SOUHAITE_ASSECHER = 4;
    public static final int ETAT_ASSECHER = 5;
    public static final int ETAT_SOUHAITE_DONNER = 6;
    public static final int ETAT_DONNER = 7;
    public static final int ETAT_GAGNER_TRESOR = 8;
    public static final int ETAT_TROP_CARTES = 9;
    public static final int ETAT_DEFAUSSE_CARTE = 10;
    public static final int ETAT_DONNER_CARTE = 11;
    public static final int ETAT_SOUHAITE_JOUER_SPECIALE = 12;
    public static final int ETAT_JOUER_SPECIALE = 13;
    public static final int ETAT_FINIR_TOUR = 14;
    public static final int ETAT_ANNULER = 15;

    private JFrame window;
    private JButton bDepl, bAss, bPioch, bGagner, bSpecial, bAnnuler, bFinir, bPerso;
    private JLabel instructions;

    private ImagePanel tresor1, tresor2, tresor3, tresor4, niveauEau;
    private ImagePanel carte1, carte2, carte3, carte4, carte5, carte6, carte7, carte8, carte9;
    private ArrayList<ImagePanel> lesCartes;
    private ImagePanel cartesOranges, cartesBleues, defausseO, defausseB, perso;
    private Grille grille;

    private final int lfenetre = 1280;
    private final int hfenetre = 720;

    public VueAventurier(Grille grille) {

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
        bPerso.setPreferredSize(new Dimension(87, 130));
        bPerso.setText("<html><center>" + "Autres" + "<br>" + "joueurs" + "</center></html>");

        instructions = new JLabel();
        instructions.setHorizontalAlignment(CENTER);
        Font font = new Font("Arial", Font.BOLD, 15);
        instructions.setFont(font);

        tresor1 = new ImagePanel(60, 90, System.getProperty("user.dir") + "/src/images/tresors/calice.png");
        tresor2 = new ImagePanel(60, 90, System.getProperty("user.dir") + "/src/images/tresors/cristal.png");;
        tresor3 = new ImagePanel(60, 90, System.getProperty("user.dir") + "/src/images/tresors/pierre.png");
        tresor4 = new ImagePanel(60, 90, System.getProperty("user.dir") + "/src/images/tresors/zephyr.png");
        niveauEau = new ImagePanel(70, 240, System.getProperty("user.dir") + "/src/images/autre/Niveau.png");

        carte1 = new ImagePanel(67, 100, "");
        carte2 = new ImagePanel(67, 100, "");
        carte3 = new ImagePanel(67, 100, "");
        carte4 = new ImagePanel(67, 100, "");
        carte5 = new ImagePanel(67, 100, "");
        carte6 = new ImagePanel(67, 100, "");
        carte7 = new ImagePanel(67, 100, "");
        carte8 = new ImagePanel(67, 100, "");
        carte9 = new ImagePanel(67, 100, "");

        lesCartes = new ArrayList<>();
        lesCartes.add(carte1);
        lesCartes.add(carte2);
        lesCartes.add(carte3);
        lesCartes.add(carte4);
        lesCartes.add(carte5);
        lesCartes.add(carte6);
        lesCartes.add(carte7);
        lesCartes.add(carte8);
        lesCartes.add(carte9);

        cartesOranges = new ImagePanel(87, 130, System.getProperty("user.dir") + "/src/images/cartes/Fond rouge.png");
        cartesBleues = new ImagePanel(87, 130, System.getProperty("user.dir") + "/src/images/cartes/Fond bleu.png");
        defausseO = new ImagePanel(87, 130, System.getProperty("user.dir") + "/src/images/cartes/Fond rouge.png");
        defausseB = new ImagePanel(87, 130, System.getProperty("user.dir") + "/src/images/cartes/Fond bleu.png");
        perso = new ImagePanel(87, 130, System.getProperty("user.dir") + "/src/images/personnages/explorateur.png");

        ArrayList<Tuile> tuiles = new ArrayList<Tuile>();
        for (int i = 1; i < 25; i++) {
            tuiles.add(new Tuile(NomTuile.getFromNb(i)));
        }
        this.grille = grille;
        this.grille.setPreferredSize(new Dimension(500, 500));
        this.grille.setBackground(new Color(50, 50, 230));

        // INSTANCIATION DES JPANEL DE DISPOSITION
        JPanel layer0 = new JPanel();

        JPanel layer1north = new JPanel();
        JPanel layer1south = new JPanel();

        JPanel layer2north = new JPanel();
        JPanel layer2south = new JPanel();
        JPanel layer2east = new JPanel();
        JPanel layer2west = new JPanel();

        JPanel layer3west = new JPanel();
        JPanel layer3east = new JPanel();

        JPanel layer4center = new JPanel();
        JPanel layer4north = new JPanel();

        // AFFECTATION DE TYPES AUX PANELS DE DISPOSITION
        layer0.setLayout(new BorderLayout());

        layer1north.setLayout(new BorderLayout());
        layer1south.setLayout(new BorderLayout());

        layer2north.setLayout(new BorderLayout());
        layer2south.setLayout(new GridLayout(1, 9));
        layer2east.setLayout(new BorderLayout());
        layer2west.setLayout(new BorderLayout());

        layer3west.setLayout(new GridLayout(3, 2));
        layer3west.setPreferredSize(new Dimension(200, 300));
        layer3east.setLayout(new BorderLayout());

        layer4center.setLayout(new GridLayout(7, 1));
        layer4north.setLayout(new GridLayout(1, 4));

        // PLACEMENT DES PANELS DE DISPOSITION
        layer1north.add(layer2north, BorderLayout.NORTH);
        layer1north.add(layer2east, BorderLayout.EAST);
        layer1north.add(layer2west, BorderLayout.WEST);

        layer1south.add(layer2south, BorderLayout.CENTER);

        layer2west.add(layer3west, BorderLayout.CENTER);
        layer2east.add(layer3east, BorderLayout.CENTER);

        layer3east.add(layer4center, BorderLayout.CENTER);
        layer3east.add(layer4north, BorderLayout.NORTH);

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

        // AJOUT DES ÉLÉMENTS AUX PANELS DE DISPOSITION
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

        layer3east.add(niveauEau, BorderLayout.WEST);

        layer3west.add(defausseO);
        layer3west.add(cartesOranges);
        layer3west.add(defausseB);
        layer3west.add(cartesBleues);
        layer3west.add(perso);
        layer3west.add(bPerso);

        layer4center.add(bDepl);
        layer4center.add(bAss);
        layer4center.add(bPioch);
        layer4center.add(bGagner);
        layer4center.add(bSpecial);
        layer4center.add(bAnnuler);
        layer4center.add(bFinir);

        layer4north.add(tresor1);
        layer4north.add(tresor2);
        layer4north.add(tresor3);
        layer4north.add(tresor4);

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

    public void dessinCartes(ArrayList<CarteOrange> cartes) {

        ArrayList<CarteTrésor> cartesTresors = new ArrayList<CarteTrésor>();
        for (CarteOrange c : cartes) {
            if (c.getRole().equals("Trésor")) {
                cartesTresors.add((CarteTrésor) c);
            } else {
                cartesTresors.add(null);
            }
        }

        for (int i = 0; i < cartes.size(); i++) {

            lesCartes.get(i).setImage(System.getProperty("user.dir") + "/src/images/cartes/" + cartes.get(i).getRole() + ((cartes.get(i).getRole() == "Trésor") ? cartesTresors.get(i).getNomTresor() : "") + ".png");
        }
    }

    public void afficherEtatAction(int etat, String joueur, Integer nbaction, String tuile, String carte) {
        switch (etat) {
            case ETAT_COMMENCER:
                instructions.setText("Bienvenue " + joueur + ". Il vous reste " + nbaction + " action(s)");
                break;
            case ETAT_SOUHAITE_DEPLACEMENT:
                instructions.setText("Choissisez une tuile :");
                break;
            case ETAT_DEPLACEMENT:
                instructions.setText("Le déplacement vers la tuile " + tuile + " a été effectué. Il vous reste " + nbaction + " action(s)");
                break;
            case ETAT_SOUHAITE_ASSECHER:
                instructions.setText("Choissisez une tuile :");
                break;
            case ETAT_ASSECHER:
                instructions.setText("La tuile " + tuile + " a été asséchée. Il vous reste " + nbaction + " action(s)");
                break;
            case ETAT_SOUHAITE_DONNER:
                instructions.setText("Choissisez une carte à donner :");
                break;
            case ETAT_DONNER:
                instructions.setText("La carte " + carte + " a été donnée au joueur " + joueur + " . Il vous reste " + nbaction + " action(s)");
                break;
            case ETAT_GAGNER_TRESOR:
                instructions.setText("Vous avez gagnez un trésor");
                break;
            case ETAT_TROP_CARTES:
                instructions.setText("Vous avez trop de cartes dans votre main, vous devez en défausser une");
                break;
            case ETAT_DEFAUSSE_CARTE:
                instructions.setText("La carte " + carte + " a été défaussée. Il vous reste " + nbaction + " action(s)");
                break;
            case ETAT_SOUHAITE_JOUER_SPECIALE:
                instructions.setText("Choissisez une carte :");
                break;
            case ETAT_JOUER_SPECIALE:
                instructions.setText("La carte spéciale " + carte + " est utilisée. Il vous reste " + nbaction + " action(s)");
                break;
            case ETAT_FINIR_TOUR:
                instructions.setText("Joueur " + joueur + " c'est à vous de jouer. Il vous reste " + nbaction + " action(s)");
                break;
            case ETAT_ANNULER:
                instructions.setText("L'action a été annulée. Il vous reste "+nbaction+" actions");
                break;

        }

    }

    public void annulerAction() {
        /*
        -actualise la grille
        -remet l'IHM en état comme au début d'un tour
        */
        this.grille.repaint();
        //à compléter
    }

    public void afficheCartesHelico() {
        /*
        -actualise la main du joueur
        -désactive toutes intéractions sauf : annuler (et le bouton d'aide)
        -met en valeur les cartes hélico de la main du joeur, elle deviennent utilisable
        */
        this.grille.repaint();
        //à compléter
    }

    public void afficheCartesSac() {
        /*
        -actualise la main du joueur
        -désactive toutes intéractions sauf : annuler (et le bouton d'aide)
        -met en valeur les cartes sac de sable de la main du joeur, elle deviennent utilisable
        */
        this.grille.repaint();
        //à compléter
    }

    public void afficherTuilesDispo() {
        /*
        -actualise la grille
        -désactive toutes intéractions sauf : annuler (et le bouton d'aide)
        */
        this.grille.repaint();
        //à compléter
    }

    public void afficherTuilesPilote() {
        // actualise la grille
        this.grille.repaint();
    }
    
}
