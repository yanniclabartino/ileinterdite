package view;

import ileinterdite.Message;
import ileinterdite.Observe;
import ileinterdite.TypesMessages;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import javax.swing.*;
import model.Aventurier;
import model.Explorateur;
import model.Ingenieur;
import model.Messager;
import model.Navigateur;
import model.Pilote;
import model.Plongeur;

public class VueAccueil extends Observe {
    
    // Image de fond:
    private ImageFond image;

    // Les trois "écrans" de l'accueil
    private JFrame accueil;
    private JFrame parametres;
    private JFrame regles;

    // Accueil :
    private JButton valider, boutregles, boutparam;
    private JLabel messageErreur;
    private ImagePanel titre;
    private final Integer[] nbJ = {2, 3, 4};
    private ArrayList<SaisiJoueur> saisiJoueurs;
    private JComboBox choixNbJoueurs;
    private final String[] diff = {"Novice","Normal","Elite","Légendaire"};
    private JComboBox choixDiff;
    private JButton randomize;

    // Parametres :
    private JButton retourPar;
    private ButtonGroup alea;
    private JRadioButton ouiA, nonA;
    private ButtonGroup logs;
    private JRadioButton ouiL, nonL;
    
    // Regles :
    private JScrollPane lesregles;
    private JButton retourReg;
    private ImagePanel imageregles;

    public VueAccueil() {
    //Accueil :
        accueil = new JFrame();
        accueil.setTitle("L'Île Interdite");
        accueil.setSize(720, 480);
        accueil.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        accueil.setResizable(false);
        accueil.setLocation((int)((Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2) - (accueil.getWidth() / 2)), (int) ((Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2) - (accueil.getHeight() / 2)));
        
        
        image = new ImageFond(720, 480, "/src/images/autre/fondAccueil.jpg");
        accueil.add(image);
        
        image.setLayout(new BorderLayout());
        
        choixNbJoueurs = new JComboBox(nbJ);

        JPanel lehaut = new JPanel(new GridLayout(2, 1, 0, 5));
        lehaut.setOpaque(false);
        
        titre = new ImagePanel(0.45, System.getProperty("user.dir") + "/src/images/ile-interdite-logo.png");
        titre.replacer((accueil.getWidth() / 2) - (titre.getPreferredSize().width / 2), 0);
        lehaut.add(titre);

        JPanel choixNb = new JPanel();
        JLabel leNombreDeJoueurs = new JLabel("Nombre de joueurs :");
        leNombreDeJoueurs.setForeground(Color.WHITE);
        choixNb.add(leNombreDeJoueurs);
        choixNbJoueurs.setPreferredSize(new Dimension(40, 20));
        choixNb.add(choixNbJoueurs);
        choixNb.setOpaque(false);
        lehaut.add(choixNb);

        image.add(lehaut, BorderLayout.NORTH);

        JPanel lesjoueurs = new JPanel();
        lesjoueurs.setOpaque(false);
        saisiJoueurs = new ArrayList();
        SaisiJoueur j1 = new SaisiJoueur(1);
        SaisiJoueur j2 = new SaisiJoueur(2);
        SaisiJoueur j3 = new SaisiJoueur(3);
        SaisiJoueur j4 = new SaisiJoueur(4);
        saisiJoueurs.add(j1);
        saisiJoueurs.add(j2);
        saisiJoueurs.add(j3);
        saisiJoueurs.add(j4);
        j1.setenabled(true);
        j2.setenabled(true);
        j3.setenabled(false);
        j4.setenabled(false);
        lesjoueurs.add(j1);
        lesjoueurs.add(j2);
        lesjoueurs.add(j3);
        lesjoueurs.add(j4);

        JPanel lebas = new JPanel(new GridLayout(1, 4));
        lebas.setOpaque(false);
        valider = new JButton("Valider");
        boutparam = new JButton("Parametres");
        boutregles = new JButton("Regles du jeu");
        messageErreur = new JLabel(" ");
        randomize = new JButton("Randomize");
        choixDiff = new JComboBox(diff);
        
        JPanel lecentre = new JPanel(new BorderLayout());
        lecentre.setOpaque(false);
        JPanel jouRandDiff = new JPanel(new BorderLayout());
        jouRandDiff.setOpaque(false);
        JPanel messageserreur = new JPanel(new BorderLayout());
        messageserreur.setOpaque(false);
        JPanel randDiff = new JPanel();
        randDiff.setBackground(new Color(230, 247, 255, 190));
        jouRandDiff.add(lesjoueurs, BorderLayout.CENTER);
        randDiff.add(randomize);
        randDiff.add(new JLabel("  Difficulté"));
        randDiff.add(choixDiff);
        jouRandDiff.add(randDiff, BorderLayout.SOUTH);
        lecentre.add(jouRandDiff, BorderLayout.CENTER);
        messageserreur.add(messageErreur, BorderLayout.EAST);
        lecentre.add(messageserreur, BorderLayout.SOUTH);
        
        image.add(lecentre, BorderLayout.CENTER);

        JPanel basgauche1 = new JPanel();
        JPanel basgauche2 = new JPanel();
        basgauche1.setOpaque(false);
        basgauche2.setOpaque(false);
        boutparam.setPreferredSize(new Dimension(150, 30));
        basgauche1.add(boutparam);
        boutregles.setPreferredSize(new Dimension(150, 30));
        basgauche2.add(boutregles);
        lebas.add(basgauche1);
        lebas.add(basgauche2);
        lebas.add(new JLabel(""));
        JPanel basdroit = new JPanel();
        basdroit.setOpaque(false);
        valider.setPreferredSize(new Dimension(80, 30));
        basdroit.add(valider);
        lebas.add(basdroit);
        image.add(lebas, BorderLayout.SOUTH);

        accueil.setVisible(true);

//Parametres :
        parametres = new JFrame("Parametres");
        parametres.setResizable(false);
        parametres.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        parametres.setLayout(new GridLayout(5, 1));

        JPanel espaceHaut = new JPanel();
        espaceHaut.setPreferredSize(new Dimension(0, 20));//marge du haut
        JPanel espaceMilieu = new JPanel();
        espaceMilieu.setPreferredSize(new Dimension(0, 50));//marge du milieu

        JPanel zoneAlea = new JPanel(new GridLayout(1, 2));
        JPanel zoneBoutA = new JPanel(new GridLayout(1, 2));

        JPanel zoneLog = new JPanel(new GridLayout(1, 2));
        JPanel zoneBoutL = new JPanel(new GridLayout(1, 2));

        JPanel zoneBoutBas = new JPanel(new GridLayout(1, 2));
        JPanel zoneBoutAcc = new JPanel(new GridLayout(1, 2));
        JPanel BoutAcc = new JPanel();

        alea = new ButtonGroup();
        ouiA = new JRadioButton("Oui");
        nonA = new JRadioButton("Non");
        nonA.setSelected(true);
        alea.add(ouiA);
        alea.add(nonA);
        zoneBoutA.add(nonA);
        zoneBoutA.add(ouiA);
        JLabel alea = new JLabel("Aléatoire :");
        alea.setToolTipText("Disposition aléatoire des Tuiles et Atribution aléatoire d'aventurier");
        zoneAlea.add(alea);
        zoneAlea.setToolTipText("Disposition aléatoire des Tuiles et Atribution aléatoire d'aventurier");
        zoneAlea.add(zoneBoutA);

        logs = new ButtonGroup();
        ouiL = new JRadioButton("Oui");
        nonL = new JRadioButton("Non");
        nonL.setSelected(true);
        logs.add(ouiL);
        logs.add(nonL);
        zoneBoutL.add(nonL);
        zoneBoutL.add(ouiL);
        JLabel log = new JLabel("Logs :");
        log.setToolTipText("Sortie textuel dans la console");
        zoneLog.add(log);
        zoneLog.setToolTipText("Sortie textuel dans la console");
        zoneLog.add(zoneBoutL);

        retourPar = new JButton("Valider");
        retourPar.setPreferredSize(new Dimension(90, 30));
        BoutAcc.add(retourPar);
        zoneBoutAcc.add(BoutAcc);
        zoneBoutBas.add(new JLabel(""));
        zoneBoutBas.add(zoneBoutAcc);


        parametres.add(espaceHaut);
        parametres.add(zoneAlea);
        parametres.add(zoneLog);
        parametres.add(espaceMilieu);
        parametres.add(zoneBoutBas);

        parametres.setSize(400, 200);
        parametres.setLocation((int)((Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2) - (parametres.getWidth() / 2)), (int) ((Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2) - (parametres.getHeight() / 2)));

        parametres.setVisible(false);

//Regles :
        regles = new JFrame("Regles du jeu");
        regles.setResizable(false);
        regles.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        int verticalp = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
        int horizontalp = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER;
        imageregles = new ImagePanel(0.688, System.getProperty("user.dir") + "/src/images/règles.jpg");
        lesregles = new JScrollPane(imageregles, verticalp, horizontalp);
        lesregles.getVerticalScrollBar().setUnitIncrement(20);
        retourReg = new JButton("Retour");
        retourReg.setPreferredSize(new Dimension(90, 30));
        
        regles.setLayout(new BorderLayout(10, 10));
        regles.add(lesregles, BorderLayout.CENTER);
        JPanel regBas = new JPanel(new GridLayout(1,4));
        JPanel boutRegAc = new JPanel();
        boutRegAc.add(retourReg);
        regBas.add(new JLabel(""));
        regBas.add(new JLabel(""));
        regBas.add(new JLabel(""));
        regBas.add(boutRegAc);
        regles.add(regBas, BorderLayout.SOUTH);
        regles.setSize(720, 480);
        regles.setLocation((int)((Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2) - (regles.getWidth() / 2)), (int) ((Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2) - (regles.getHeight() / 2)));

        
        
// Action listener Accueil :
        choixNbJoueurs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < 4; i++) {
                    if (i < (int) choixNbJoueurs.getSelectedItem()) {
                        saisiJoueurs.get(i).setenabled(true);
                        saisiJoueurs.get(i).updateUI();

                    } else {
                        saisiJoueurs.get(i).setenabled(false);
                        saisiJoueurs.get(i).updateUI();
                    }
                }
                lesjoueurs.updateUI();
            }
        });
        
        valider.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                Message m = new Message();
                if (avpareil()) {
                    messageErreur.setText("Plusieurs joueurs ont le même aventurier");
                } else {
                    //création du message de début de partie
                    m.type = TypesMessages.COMMENCER;
                    m.difficulté = choixDiff.getSelectedIndex();
                    m.logs = ouiL.isSelected();
                    m.nbJoueurs = (int)choixNbJoueurs.getSelectedItem();
                    m.joueurs = new HashMap<>();
                    for (SaisiJoueur joueur : saisiJoueurs) {
                        if(joueur.isEnabled()){               //si le nom du joueur n'as pas était saisi on renvoi "Joueur 1" ou "Joueur 2" etc. selon le rang du joueur
                            m.joueurs.put(stringEnAventurier(joueur), (joueur.getNom().equals(null))?"Joueur "+(saisiJoueurs.indexOf(joueur)+1):joueur.getNom());
                        }
                        m.aleas = ouiA.isSelected();
                    }

                    notifierObservateur(m);
                }
            }
        });

        boutparam.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                affParam();
            }
        });

        boutregles.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                affRegles();
            }
        });
        
        randomize.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                randomize();
            }
        });
        
//ActionListener Parametre :
        retourPar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                affAccueil();
                choixAlea(ouiA.isSelected());
            }
        });

//ActionListener Règle :
        retourReg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                affAccueil();
            }
        });
        
    }

    private void affRegles() {
        accueil.setVisible(false);
        regles.setVisible(true);
    }

    private void affParam() {
        accueil.setVisible(false);
        parametres.setVisible(true);
    }

    private void affAccueil() {
        if (regles.isVisible()) {
            regles.setVisible(false);
        }
        if (parametres.isVisible()) {
            parametres.setVisible(false);
        }
        accueil.setVisible(true);
    }

    private boolean avpareil() {
        
        switch (choixNbJoueurs.getSelectedIndex()){
            case 0:
                return (saisiJoueurs.get(0).getAventurier().equals(saisiJoueurs.get(1).getAventurier()));
            case 1:
                return (saisiJoueurs.get(0).getAventurier().equals(saisiJoueurs.get(1).getAventurier())
                     || saisiJoueurs.get(0).getAventurier().equals(saisiJoueurs.get(2).getAventurier())
                     || saisiJoueurs.get(1).getAventurier().equals(saisiJoueurs.get(2).getAventurier()));
            case 2:
                return (saisiJoueurs.get(0).getAventurier().equals(saisiJoueurs.get(1).getAventurier())
                     || saisiJoueurs.get(0).getAventurier().equals(saisiJoueurs.get(2).getAventurier())
                     || saisiJoueurs.get(0).getAventurier().equals(saisiJoueurs.get(3).getAventurier())
                     || saisiJoueurs.get(1).getAventurier().equals(saisiJoueurs.get(2).getAventurier())
                     || saisiJoueurs.get(1).getAventurier().equals(saisiJoueurs.get(3).getAventurier())
                     || saisiJoueurs.get(2).getAventurier().equals(saisiJoueurs.get(3).getAventurier()));
            default:
                return (saisiJoueurs.get(0).getAventurier().equals(saisiJoueurs.get(1).getAventurier()));
        }
    }

    private Aventurier stringEnAventurier(SaisiJoueur joueur) {

        Aventurier aventurier=null;
        if (joueur.getAventurier() == "Pilote") {
            aventurier = new Pilote();
        } else if (joueur.getAventurier() == "Navigateur") {
            aventurier = new Navigateur();
        } else if (joueur.getAventurier() == "Ingénieur") {
            aventurier = new Ingenieur();
        } else if (joueur.getAventurier() == "Explorateur") {
            aventurier = new Explorateur();
        } else if (joueur.getAventurier() == "Messager") {
            aventurier = new Messager();
        } else if (joueur.getAventurier() == "Plongeur") {
            aventurier = new Plongeur();
        }
        return aventurier;
    }
    
    private void randomize(){
        String[] avDispo = SaisiJoueur.avDispo;
            Random random = new Random();
            ArrayList<Integer> arrayList = new ArrayList<Integer>();

            while (arrayList.size() < 6) {
                int a = random.nextInt(avDispo.length);
                if (!arrayList.contains(a)) {
                    arrayList.add(a);
                }
            }
            for (int i = 0; i < saisiJoueurs.size(); i++){
                saisiJoueurs.get(i).setAv(arrayList.get(i));
                saisiJoueurs.get(i).updateUI();
            }
    }
    
    private void choixAlea(boolean b){//répartition des aventurier aléatoirement
        if (b){//si parametre aléatoiree :
            String[] avDispo = SaisiJoueur.avDispo;
            Random random = new Random();
            ArrayList<Integer> arrayList = new ArrayList<Integer>();

            while (arrayList.size() < 6) {
                int a = random.nextInt(avDispo.length);
                if (!arrayList.contains(a)) {
                    arrayList.add(a);
                }
            }
            for (int i = 0; i < saisiJoueurs.size(); i++){
                saisiJoueurs.get(i).setAv(arrayList.get(i));
                saisiJoueurs.get(i).setAlea(!b);
                saisiJoueurs.get(i).updateUI();
            }
            
        }
    }
    
}
