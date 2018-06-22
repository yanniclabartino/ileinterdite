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
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import static javax.swing.SwingConstants.CENTER;
import model.CarteOrange;
import model.CarteTrésor;
import model.Grille;
import model.Trésor;

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

    private TypesMessages MESSAGE_PRECEDENT;

    private JFrame window;
    private JButton bDepl, bAss, bPioch, bGagner, bSpecial, bAnnuler, bFinir, bPerso;
    private JLabel instructions;
    private ImageFond imageFond;

    private ImagePanel tresor1, tresor2, tresor3, tresor4, niveauEau;
    private ImagePanel carte1, carte2, carte3, carte4, carte5, carte6, carte7, carte8, carte9;
    private ArrayList<ImagePanel> lesCartes;
    private ImagePanel cartesOranges, cartesBleues, defausseO, defausseB, perso;
    private Grille grille;
    private VidePanel margeHaut;

    private final int lfenetre = 1280;
    private final int hfenetre = 720;

    public VueAventurier(Grille grille) {
        
        // INSTANCIATION DE L'IMAGE DE FOND
        imageFond = new ImageFond(lfenetre, hfenetre, "/src/images/autre/fondJeu.jpg");

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
        Font font = new Font("Arial", Font.BOLD, 15);
        instructions.setFont(font);

        tresor1 = new ImagePanel(60, 90, System.getProperty("user.dir") + "/src/images/tresors/calice.png");
        tresor2 = new ImagePanel(60, 90, System.getProperty("user.dir") + "/src/images/tresors/cristal.png");;
        tresor3 = new ImagePanel(60, 90, System.getProperty("user.dir") + "/src/images/tresors/pierre.png");
        tresor4 = new ImagePanel(60, 90, System.getProperty("user.dir") + "/src/images/tresors/zephyr.png");
        niveauEau = new ImagePanel(70, 240, System.getProperty("user.dir") + "/src/images/autre/Niveau.png");

        carte1 = new ImagePanel(67, 100, "", 14);
        carte2 = new ImagePanel(67, 100, "", 14);
        carte3 = new ImagePanel(67, 100, "", 14);
        carte4 = new ImagePanel(67, 100, "", 14);
        carte5 = new ImagePanel(67, 100, "", 14);
        carte6 = new ImagePanel(67, 100, "", 14);
        carte7 = new ImagePanel(67, 100, "", 14);
        carte8 = new ImagePanel(67, 100, "", 14);
        carte9 = new ImagePanel(67, 100, "", 14);

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

        cartesOranges = new ImagePanel(87, 130, System.getProperty("user.dir") + "/src/images/cartes/Fond rouge.png", 6);
        cartesBleues = new ImagePanel(87, 130, System.getProperty("user.dir") + "/src/images/cartes/Fond bleu.png", 6);
        defausseO = new ImagePanel(87, 130, System.getProperty("user.dir") + "/src/images/cartes/Fond rouge.png", 6);
        defausseB = new ImagePanel(87, 130, System.getProperty("user.dir") + "/src/images/cartes/Fond bleu.png", 6);
        perso = new ImagePanel(87, 130, System.getProperty("user.dir") + "/src/images/personnages/explorateur.png", 6);

        this.MESSAGE_PRECEDENT = null;

        this.grille = grille;
        this.grille.setPreferredSize(new Dimension(500, 500));
        this.grille.setBackground(new Color(50, 50, 230));

        margeHaut = new VidePanel(400, 25);

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
        layer0.setBackground(Color.RED);
        
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
        
        layer2north.add(margeHaut, BorderLayout.NORTH);
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

        window.add(imageFond);
        imageFond.add(layer0);
        
        tresor1.setVisible(false);
        tresor2.setVisible(false);
        tresor3.setVisible(false);
        tresor4.setVisible(false);
        
        // MISE EN TRANSPARENCE DE TOUS LES PANELS POUR VOIR L'ARRIÈRE PLAN
        layer0.setOpaque(false);
        layer1north.setOpaque(false);
        layer1south.setOpaque(false);
        layer2north.setOpaque(false);
            instructions.setBackground(new Color(255, 242, 230, 40));
        layer2south.setOpaque(false);
            carte1.setBackground(new Color(255, 229, 204));
            carte2.setBackground(new Color(255, 229, 204));
            carte3.setBackground(new Color(255, 229, 204));
            carte4.setBackground(new Color(255, 229, 204));
            carte5.setBackground(new Color(255, 229, 204));
            carte6.setBackground(new Color(255, 229, 204));
            carte7.setBackground(new Color(255, 229, 204));
            carte8.setBackground(new Color(255, 229, 204));
            carte9.setBackground(new Color(255, 229, 204));
            
        layer2east.setOpaque(false);
        layer2west.setOpaque(false);
        layer3west.setBackground(new Color(255, 242, 230, 90));
        layer3east.setOpaque(false);
        layer4center.setOpaque(false);
        layer4north.setBackground(new Color(255, 229, 204, 90));

        // ACTIONLISTENER DES BOUTONS
        bDepl.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Message m = new Message();
                m.type = TypesMessages.SOUHAITE_DEPLACEMENT;
                MESSAGE_PRECEDENT = m.type;

                notifierObservateur(m);
            }
        });
        bAnnuler.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Message m = new Message();
                m.type = TypesMessages.ANNULER;
                MESSAGE_PRECEDENT = m.type;

                notifierObservateur(m);
            }
        });
        bAss.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Message m = new Message();
                m.type = TypesMessages.SOUHAITE_ASSECHER;
                MESSAGE_PRECEDENT = m.type;

                notifierObservateur(m);
            }
        });
        bFinir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Message m = new Message();
                m.type = TypesMessages.FINIR_TOUR;
                MESSAGE_PRECEDENT = m.type;

                notifierObservateur(m);
            }
        });

        bGagner.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Message m = new Message();
                m.type = TypesMessages.ACTION_GAGNER_TRESOR;
                MESSAGE_PRECEDENT = m.type;

                notifierObservateur(m);
            }
        });

        bPioch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Message m = new Message();
                m.type = TypesMessages.SOUHAITE_DONNER;
                MESSAGE_PRECEDENT = m.type;

                notifierObservateur(m);
            }
        });
        bSpecial.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Message m = new Message();
                m.type = TypesMessages.SOUHAITE_JOUER_SPECIALE;
                MESSAGE_PRECEDENT = m.type;

                notifierObservateur(m);
            }
        });

        // CLIC SUR LA GRILLE (à compléter)
        this.grille.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Message m = new Message();
                switch (MESSAGE_PRECEDENT) {
                    case SOUHAITE_DEPLACEMENT:
                        m.type = TypesMessages.ACTION_DEPLACEMENT;
                        break;
                    case SOUHAITE_ASSECHER:
                        m.type = TypesMessages.ACTION_ASSECHER;
                        break;
                    /*case SOUHAITE_DONNER:
                        m.type = TypesMessages.ACTION_DONNER;
                        
                        break;*/
                    case SOUHAITE_JOUER_SPECIALE:
                        m.type = TypesMessages.JOUER_SPECIALE;
                        break;
                }
                if (m.type != MESSAGE_PRECEDENT) {
                    m.tuile = grille.getTuile(e.getX() * 6 / grille.getWidth(), e.getY() * 6 / grille.getHeight());
                    MESSAGE_PRECEDENT = m.type;
                    notifierObservateur(m);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
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
                margeHaut.setPreferredSize(new Dimension(400, 9));
                instructions.setText("<html><center>" + "Bienvenue " + joueur + "<br>" + ". Il vous reste " + nbaction + " action(s)" + "</center></html>");
                break;
            case ETAT_SOUHAITE_DEPLACEMENT:
                instructions.setText("<html>" + "Choissisez une tuile :" + "<br>" + "<br>" + "<html>");
                break;
            case ETAT_DEPLACEMENT:
                margeHaut.setPreferredSize(new Dimension(400, 9));
                instructions.setText("<html><center>" + "Le déplacement vers la tuile " + tuile + " a été " + "<br>" + "effectué. Il vous reste " + nbaction + " action(s)" + "<html><center>");
                break;
            case ETAT_SOUHAITE_ASSECHER:
                instructions.setText("<html>" + "Choissisez une tuile :" + "<br>" + "<br>" + "<html>");
                break;
            case ETAT_ASSECHER:
                margeHaut.setPreferredSize(new Dimension(400, 9));
                instructions.setText("<html><center>" + "La tuile " + tuile + " a été asséchée." + "<br>" + " Il vous reste " + nbaction + " action(s)" + "<html><center>");
                break;
            case ETAT_SOUHAITE_DONNER:
                instructions.setText("<html>" + "Choissisez une carte à donner :" + "<br>" + "<br>" + "<html>");
                break;
            case ETAT_DONNER:
                margeHaut.setPreferredSize(new Dimension(400, 9));
                instructions.setText("<html><center>" + "La carte " + carte + " a été donnée au joueur " + joueur + " ." + "<br>" + "Il vous reste " + nbaction + " action(s)" + "<html><center>");
                break;
            case ETAT_GAGNER_TRESOR:
                margeHaut.setPreferredSize(new Dimension(400, 9));
                instructions.setText("<html><center>" + joueur + "a gagné un trésor." + "<br>" + "Il vous reste " + nbaction + " action(s)" + "<html><center>");
                break;
            case ETAT_TROP_CARTES:
                margeHaut.setPreferredSize(new Dimension(400, 9));
                instructions.setText("<html><center>" + "Vous avez trop de cartes dans votre main, vous devez en défausser une" + "<html><center>");
                break;
            case ETAT_DEFAUSSE_CARTE:
                margeHaut.setPreferredSize(new Dimension(400, 9));
                instructions.setText("<html><center>" + "La carte " + carte + " a été défaussée." + "<br>" + " Il vous reste " + nbaction + " action(s)" + "<html><center>");
                break;
            case ETAT_SOUHAITE_JOUER_SPECIALE:
                instructions.setText("<html>" + "Choissisez une carte :" + "<br>" + "<br>" + "<html>");
                break;
            case ETAT_JOUER_SPECIALE:
                margeHaut.setPreferredSize(new Dimension(400, 9));
                instructions.setText("<html><center>" + "La carte spéciale " + carte + " est utilisée." + "<br>" + " Il vous reste " + nbaction + " action(s)" + "<html><center>");
                break;
            case ETAT_FINIR_TOUR:
                margeHaut.setPreferredSize(new Dimension(400, 9));
                instructions.setText("<html><center>" + "Joueur " + joueur + " c'est à vous de jouer." + "<br>" + " Il vous reste " + nbaction + " action(s)" + "<html><center>");
                break;
            case ETAT_ANNULER:
                margeHaut.setPreferredSize(new Dimension(400, 9));
                instructions.setText("<html><center>" + "L'action a été annulée." + "<br>" + " Il vous reste " + nbaction + " actions" + "<html><center>");
                break;
        }

    }

    public void interfaceParDefaut() {
        /*
        -actualise la grille
        -remet l'IHM en état comme au début d'un tour (ne modifie pas l'état courant)
         */
        this.grille.repaint();
        bDepl.setEnabled(true);
        bAss.setEnabled(true);
        bPioch.setEnabled(true);
        bGagner.setEnabled(true);
        bSpecial.setEnabled(true);
        bAnnuler.setEnabled(true);
        bFinir.setEnabled(true);
        bPerso.setEnabled(true);
    }

    public void afficheCartesHelico() {
        /*
        -désactive toutes intéractions sauf : annuler (et le bouton d'aide)
        -met en valeur les cartes hélico de la main du joueur, elle deviennent utilisable
         */
        this.grille.repaint();
        //à compléter
    }

    public void afficheCartesSac() {
        /*
        -désactive toutes intéractions sauf : annuler (et le bouton d'aide)
        -met en valeur les cartes sac de sable de la main du joueur, elle deviennent utilisable
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
        bDepl.setEnabled(false);
        bAss.setEnabled(false);
        bPioch.setEnabled(false);
        bGagner.setEnabled(false);
        bSpecial.setEnabled(false);
    }

    public void afficherTuilesPilote() {
        // actualise la grille
        this.grille.repaint();
    }

    public void actualiserTrésor(Trésor[] trésors) {
        /*
        en fonction des trésors gagnés, l'IHM change.
         */
        //[0] = LA_PIERRE_SACREE
        //[1] = LA_STATUE_DU_ZEPHYR
        //[2] = LE_CRISTAL_ARDENT
        //[3] = LE_CALICE_DE_L_ONDE
        if (trésors[1].isGagne()) {
            tresor1.setVisible(true);
        } else if (trésors[2].isGagne()) {
            tresor2.setVisible(true);
        } else if (trésors[3].isGagne()) {
            tresor3.setVisible(true);
        } else if (trésors[4].isGagne()) {
            tresor4.setVisible(true);
        }
    }

}
