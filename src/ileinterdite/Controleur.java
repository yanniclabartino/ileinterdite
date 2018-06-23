package ileinterdite;

import model.*;
import util.*;
import util.Utils.Pion;
import java.util.Stack;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import view.VueAccueil;
import view.VueAventurier;

/*
    Groupe C2 :  Prapant.B, Labartino.Y, Giroud.T, Malod.V
 */
public class Controleur implements Observateur {

    /*
    Note :  pour une lecture agréable du code, nous vous invitons à
            réduire depuis netbeans chaque méthode pour n'en apercevoir
            que le titre et les paramètres de celles-ci.
     */
    private VueAccueil accueil;
    private VueAventurier ihm;

    private Grille grille;
    private Trésor[] trésors;
    private HashMap<Aventurier, String> nomJoueurs;
    private ArrayList<Aventurier> joueurs;
    private Aventurier joueurCourant;
    private Stack<CarteBleue> piocheBleues;
    private ArrayList<CarteBleue> defausseBleues;
    private Stack<CarteOrange> piocheOranges;
    private ArrayList<CarteOrange> defausseOranges;
    private boolean pouvoirPiloteDispo, pouvoirIngénieurUsé, jeuEnCours;
    private int niveauEau, nbJoueurs, nbActions;

    @Override
    public void traiterMessage(Message m) {
        switch (m.type) {
            case COMMENCER:
                /*
                iniJeu();
                this.niveauEau = m.difficulté;
                this.nbJoueurs = m.nbJoueurs;
                this.joueurs.putAll(m.joueurs);
                Parameters.setLogs(m.logs);
                Parameters.setAleas(m.aleas);
                debutJeu();
                 */
                getIHM().afficherEtatAction(ihm.ETAT_COMMENCER, getNomJoueurs().get(getJoueurCourant()), getNbAction());
                break;
            case SOUHAITE_DEPLACEMENT:
                if (deplacementPossible(getJoueurCourant())) {
                    gererDeplacement(getJoueurCourant());
                    getIHM().afficherEtatAction(ihm.ETAT_SOUHAITE_DEPLACEMENT, getNomJoueurs().get(getJoueurCourant()), getNbAction());
                }
                break;
            case ACTION_DEPLACEMENT:
                if (m.tuile.getSelected() != 0) {
                    if (m.tuile.getSelected() == 2) {
                        pouvoirPiloteDispo = false;
                    }
                    getJoueurCourant().seDeplace(m.tuile);
                    this.nbActions--;
                    actualiserJeu();
                    getIHM().afficherEtatAction(ihm.ETAT_JOUEUR, getNomJoueurs().get(getJoueurCourant()), getNbAction());
                    getGrille().deselectionnerTuiles();
                    getIHM().interfaceParDefaut(getJoueurCourant().getMain());
                }
                break;
            case SOUHAITE_ASSECHER:
                if (assechementPossible()) {
                    gererAssechement();
                    getIHM().afficherEtatAction(ihm.ETAT_SOUHAITE_ASSECHER, getNomJoueurs().get(getJoueurCourant()), getNbAction());
                }
                break;
            case ACTION_ASSECHER:
                if (m.tuile.getSelected() == 1) {
                    if (getJoueurCourant().getCouleur() != Pion.ROUGE) { 
                        //application en règle générale pour les autres aventuriers que l'ingénieur
                        m.tuile.setEtat(Utils.EtatTuile.ASSECHEE);
                        this.nbActions--;
                        getGrille().deselectionnerTuiles();
                        actualiserJeu();
                        getIHM().afficherEtatAction(ihm.ETAT_JOUEUR, getNomJoueurs().get(getJoueurCourant()), getNbAction());
                        getIHM().interfaceParDefaut(getJoueurCourant().getMain());
                    } else {
                        if (!pouvoirIngénieurUsé) {
                            //il s'agit de l'ingénieur et c'est son premier assèchement.
                            pouvoirIngénieurUsé = true;
                            m.tuile.setEtat(Utils.EtatTuile.ASSECHEE);
                            this.nbActions--;
                            getGrille().deselectionnerTuiles();
                            if (assechementPossible()) {
                                gererAssechement();
                            } else {
                                actualiserJeu();
                                getIHM().afficherEtatAction(ihm.ETAT_JOUEUR, getNomJoueurs().get(getJoueurCourant()), getNbAction());
                                getIHM().interfaceParDefaut(getJoueurCourant().getMain());
                            }
                        } else {
                            //c'est l'ingénieur mais il a déjà assècher une fois.
                            m.tuile.setEtat(Utils.EtatTuile.ASSECHEE);
                            getGrille().deselectionnerTuiles();
                            actualiserJeu();
                            getIHM().afficherEtatAction(ihm.ETAT_JOUEUR, getNomJoueurs().get(getJoueurCourant()), getNbAction());
                            getIHM().interfaceParDefaut(getJoueurCourant().getMain());
                        }
                    }
                }
                break;
            case SOUHAITE_DONNER:
                if (donationPossible()) {
                    gererDonation();
                    getIHM().afficherEtatAction(ihm.ETAT_SOUHAITE_DONNER, getNomJoueurs().get(getJoueurCourant()), getNbAction());
                }
                break;
            case ACTION_DONNER:
                getJoueurCourant().defausseCarte(getJoueurCourant().getMain().get(m.numCarte));
                m.receveur.piocheCarte(getJoueurCourant().getMain().get(m.numCarte));
                getIHM().afficherEtatAction(ihm.ETAT_JOUEUR, getNomJoueurs().get(m.receveur), getNbAction());
                break;
            case ACTION_GAGNER_TRESOR:
                if (gererGainTresor()) {
                    getIHM().afficherEtatAction(ihm.ETAT_JOUEUR, getNomJoueurs().get(getJoueurCourant()), getNbAction());
                    getIHM().actualiserTrésor(getTrésors());
                }
                break;
            case DEFAUSSE_CARTE:
                getJoueurCourant().defausseCarte(getJoueurCourant().getMain().get(m.numCarte));
                addDefausseOranges(getJoueurCourant().getMain().get(m.numCarte));
                getIHM().afficherEtatAction(ihm.ETAT_JOUEUR, getNomJoueurs().get(getJoueurCourant()), getNbAction());
                break;
            case SOUHAITE_JOUER_SPECIALE:
                if (specialePossible() != 0) {
                    gererCarteSpecial();
                    getIHM().afficherEtatAction(ihm.ETAT_SOUHAITE_JOUER_SPECIALE, getNomJoueurs().get(getJoueurCourant()), getNbAction());
                }
                break;
            case JOUER_SPECIALE:
                if (getJoueurCourant().getMain().get(m.numCarte).getRole().equals("Helicoptere") && specialePossible() == 1) {
                    getJoueurCourant().defausseCarte(getJoueurCourant().getMain().get(m.numCarte));
                    addDefausseOranges(getJoueurCourant().getMain().get(m.numCarte));
                    this.jeuEnCours = false;
                    getIHM().afficherEtatAction(ihm.ETAT_JOUEUR, getNomJoueurs().get(getJoueurCourant()), getNbAction());
                } else if (getJoueurCourant().getMain().get(m.numCarte).getRole().equals("Sac de sable") && specialePossible() == 2) {
                    m.tuile.setEtat(Utils.EtatTuile.ASSECHEE);
                    getJoueurCourant().defausseCarte(getJoueurCourant().getMain().get(m.numCarte));
                    addDefausseOranges(getJoueurCourant().getMain().get(m.numCarte));
                    getIHM().afficherEtatAction(ihm.ETAT_JOUEUR, getNomJoueurs().get(getJoueurCourant()), getNbAction());
                }
                break;
            case FINIR_TOUR:
                this.nbActions = 0;
                actualiserJeu();
                getIHM().afficherEtatAction(ihm.ETAT_JOUEUR, getNomJoueurs().get(getJoueurCourant()), getNbAction());
                getGrille().deselectionnerTuiles();
                getIHM().interfaceParDefaut(getJoueurCourant().getMain());
                break;
            case ANNULER:
                actualiserJeu();
                getIHM().afficherEtatAction(ihm.ETAT_JOUEUR, getNomJoueurs().get(getJoueurCourant()), getNbAction());
                getGrille().deselectionnerTuiles();
                getIHM().interfaceParDefaut(getJoueurCourant().getMain());
                break;
        }
    }

    //METHODES DE GESTION DU JEU
    private boolean deplacementPossible(Aventurier joueur) {
        boolean deplDispo = true;
        Grille g = getGrille();
        ArrayList<Tuile> tuilesDispo = new ArrayList<Tuile>();
        if (joueur.getCouleur() == Utils.Pion.BLEU && pouvoirPiloteDispo) {
            return deplDispo;
        } else {
            tuilesDispo = joueur.calculTuileDispo(g);
            if (!tuilesDispo.isEmpty()) {
                return deplDispo;
            }
        }
        return !deplDispo;
    }

    private void gererDeplacement(Aventurier joueur) {
        Grille g = getGrille();
        ArrayList<Tuile> tuilesDispo = new ArrayList<Tuile>();
        ArrayList<Tuile> tuilesPilote = new ArrayList<Tuile>();
        tuilesDispo.addAll(joueur.calculTuileDispo(g));
        g.selectionTuileDispo(tuilesDispo, 1);
        getIHM().afficherTuilesDispo();
        if (joueur.getCouleur() == Utils.Pion.BLEU && pouvoirPiloteDispo) {
            tuilesPilote.addAll(calculTouteTuileDispo(g));
            tuilesPilote.removeAll(tuilesDispo);
            g.selectionTuileDispo(tuilesPilote, 2);
            getIHM().afficherTuilesPilote();
        }
    }

    private boolean assechementPossible() {
        ArrayList<Tuile> tuilesDispo = getJoueurCourant().calculTuileAss(getGrille());
        return (!tuilesDispo.isEmpty());
    }

    private void gererAssechement() {
        //méthode qui permet a un aventurier d'assécher une tuile
        Aventurier joueur = getJoueurCourant();
        Grille g = getGrille();
        ArrayList<Tuile> tuilesDispo = joueur.calculTuileAss(g);
        g.selectionTuileDispo(tuilesDispo, 1);
        getIHM().afficherTuilesDispo();
    }

    private boolean donationPossible() {
        boolean donnDispo = true;
        Aventurier joueur = getJoueurCourant();
        if (!joueur.getMain().isEmpty()) {
            ArrayList<CarteOrange> cartesDispo = new ArrayList<CarteOrange>();
            for (CarteOrange c : joueur.getMain()) {
                if (!(c.getRole().equals("Helicoptere") || c.getRole().equals("Sac de sable"))) {
                    cartesDispo.add(c);
                }
            }
            if (!cartesDispo.isEmpty()) {
                Grille g = getGrille();
                ArrayList<Tuile> tuiles = g.getGrille();
                ArrayList<Aventurier> jDispo = new ArrayList<Aventurier>();
                //vérification s'il s'agit du messager pour son pouvoir
                if (joueur.getCouleur() == Pion.BLANC) {
                    for (Tuile t : tuiles) {
                        if (!t.getPossede().isEmpty()) {
                            for (Aventurier a : t.getPossede()) {
                                if (a.getMain().size() < 9) {
                                    jDispo.add(a);
                                }
                            }
                        }
                    }
                } else if (!joueur.getTuile().getPossede().isEmpty()) {
                    for (Aventurier a : joueur.getTuile().getPossede()) {
                        if (a.getMain().size() < 9) {
                            jDispo.add(a);
                        }
                    }
                }
                jDispo.remove(joueur);
                if (!jDispo.isEmpty()) {
                    return donnDispo;
                }
            }
        }
        return !donnDispo;
    }

    //à compléter avec l'ihm
    private void gererDonation() {
        ArrayList<CarteOrange> cartesDispo = new ArrayList<CarteOrange>();
        Aventurier joueur = getJoueurCourant();
        for (CarteOrange c : joueur.getMain()) {
            if (!(c.getRole().equals("Helicoptere") || c.getRole().equals("Sac de sable"))) {
                cartesDispo.add(c);
            }
        }
        //début méthode
        Grille g = getGrille();
        ArrayList<Tuile> tuiles = g.getGrille();
        ArrayList<Aventurier> jDispo = new ArrayList<Aventurier>();
        //vérification s'il s'agit du messager pour son pouvoir
        if (joueur.getCouleur() == Pion.BLANC) {
            for (Tuile t : tuiles) {
                if (!t.getPossede().isEmpty()) {
                    for (Aventurier a : t.getPossede()) {
                        if (a.getMain().size() < 9) {
                            jDispo.add(a);
                        }
                    }
                }
            }
        } else if (!joueur.getTuile().getPossede().isEmpty()) {
            for (Aventurier a : joueur.getTuile().getPossede()) {
                if (a.getMain().size() < 9) {
                    jDispo.add(a);
                }
            }
        }
        jDispo.remove(joueur);

        //Joueur a qui on donne
        Aventurier jRecoit = null;
        //Carte donnée
        CarteOrange cCédée = null;

        //compléter avec l'ihm pour proposer les cartes possibles a céder ainsi que les aventurier receuveurs possibles.
    }

    private boolean gererGainTresor() {
        boolean gainEffectué = true;
        Aventurier joueur = getJoueurCourant();
        if (joueur.getMain().size() < 4) {
            return !gainEffectué;
        } else {
            int nbCarteTresorPS = 0;
            int nbCarteTresorSZ = 0;
            int nbCarteTresorCA = 0;
            int nbCarteTresorCO = 0;
            ArrayList<CarteTrésor> cartesTresors = new ArrayList<CarteTrésor>();
            for (CarteOrange c : joueur.getMain()) {
                if (c.getRole().equals("Trésor")) {
                    cartesTresors.add((CarteTrésor) c);
                }
            }
            for (CarteTrésor c : cartesTresors) {
                if (c.getNomTresor() == NomTresor.LA_PIERRE_SACREE) {
                    nbCarteTresorPS++;
                } else if (c.getNomTresor() == NomTresor.LA_STATUE_DU_ZEPHYR) {
                    nbCarteTresorSZ++;
                } else if (c.getNomTresor() == NomTresor.LE_CALICE_DE_L_ONDE) {
                    nbCarteTresorCO++;
                } else if (c.getNomTresor() == NomTresor.LE_CRISTAL_ARDENT) {
                    nbCarteTresorCA++;
                }
            }
            if ((nbCarteTresorCA | nbCarteTresorCO | nbCarteTresorPS | nbCarteTresorSZ) >= 4) {
                ArrayList<CarteOrange> cartesDuJoueur = new ArrayList<CarteOrange>();
                cartesDuJoueur.addAll(joueur.getMain());
                if (joueur.getTuile().getNom() == NomTuile.LE_TEMPLE_DU_SOLEIL || joueur.getTuile().getNom() == NomTuile.LE_TEMPLE_DE_LA_LUNE) {
                    if (nbCarteTresorPS >= 4) {
                        for (CarteOrange cO : cartesDuJoueur) {
                            for (int i = 0; i < 4; i++) {
                                CarteTrésor cTresor = cartesTresors.get(i);
                                if (cTresor.getNomTresor() == NomTresor.LA_PIERRE_SACREE && cO == cTresor) {
                                    joueur.getMain().remove(cO);
                                }
                            }
                        }
                        getTrésors()[0].setGagne(true);
                        return gainEffectué;
                    }
                } else if (joueur.getTuile().getNom() == NomTuile.LE_JARDIN_DES_HURLEMENTS || joueur.getTuile().getNom() == NomTuile.LE_JARDIN_DES_MURMURES) {
                    if (nbCarteTresorSZ >= 4) {
                        for (CarteOrange cO : cartesDuJoueur) {
                            for (int i = 0; i < 4; i++) {
                                CarteTrésor cTresor = cartesTresors.get(i);
                                if (cTresor.getNomTresor() == NomTresor.LA_STATUE_DU_ZEPHYR && cO == cTresor) {
                                    joueur.getMain().remove(cO);
                                }
                            }
                        }
                        getTrésors()[1].setGagne(true);
                        return gainEffectué;
                    }
                } else if (joueur.getTuile().getNom() == NomTuile.LA_CAVERNE_DES_OMBRES || joueur.getTuile().getNom() == NomTuile.LA_CAVERNE_DES_OMBRES) {
                    if (nbCarteTresorCA >= 4) {
                        for (CarteOrange cO : cartesDuJoueur) {
                            for (int i = 0; i < 4; i++) {
                                CarteTrésor cTresor = cartesTresors.get(i);
                                if (cTresor.getNomTresor() == NomTresor.LE_CRISTAL_ARDENT && cO == cTresor) {
                                    joueur.getMain().remove(cO);
                                }
                            }
                        }
                        getTrésors()[2].setGagne(true);
                        return gainEffectué;
                    }
                } else if (joueur.getTuile().getNom() == NomTuile.LE_PALAIS_DE_CORAIL || joueur.getTuile().getNom() == NomTuile.LE_PALAIS_DES_MAREES) {
                    if (nbCarteTresorCO >= 4) {
                        for (CarteOrange cO : cartesDuJoueur) {
                            for (int i = 0; i < 4; i++) {
                                CarteTrésor cTresor = cartesTresors.get(i);
                                if (cTresor.getNomTresor() == NomTresor.LE_CALICE_DE_L_ONDE && cO == cTresor) {
                                    joueur.getMain().remove(cO);
                                }
                            }
                        }
                        getTrésors()[3].setGagne(true);
                        return gainEffectué;
                    }
                }
            } else {
                return !gainEffectué;
            }
            return !gainEffectué;
        }
    }

    private int specialePossible() {
        // retourne : 0 si pas possible
        // 1 si carte Helicoptere
        // 2 si carte Sac de sable
        Grille g = getGrille();
        Aventurier joueur = getJoueurCourant();
        ArrayList<CarteOrange> cartesJoueur = new ArrayList<CarteOrange>();
        cartesJoueur.addAll(joueur.getMain());
        int nbJoueur = getNbJoueur();
        if (!joueur.getMain().isEmpty()) {
            if (g.getTuile(NomTuile.HELIPORT).getPossede().size() == nbJoueur) {
                boolean carteHelico = false;
                for (CarteOrange c : cartesJoueur) {
                    if (c.getRole().equals("Helicoptere")) {
                        carteHelico = true;
                    }
                }
                if (carteHelico) {
                    return 1;
                }
            } else {
                ArrayList<Tuile> tuilesInnondées = new ArrayList<Tuile>();
                for (Tuile t : g.getGrille()) {
                    if (t.getEtat() == Utils.EtatTuile.INONDEE) {
                        tuilesInnondées.add(t);
                    }
                }
                if (!tuilesInnondées.isEmpty()) {
                    boolean carteSac = false;
                    for (CarteOrange c : cartesJoueur) {
                        if (c.getRole().equals("Sac de sable")) {
                            carteSac = true;
                        }
                    }
                    if (carteSac) {
                        return 2;
                    }
                }
            }
        }
        return 0;
    }

    private void gererCarteSpecial() {
        if (specialePossible() == 1) {
            getIHM().afficheCartesHelico(getJoueurCourant().getMain());
        } else { //specialePossible() == 2
            getIHM().afficheCartesSac(getJoueurCourant().getMain());
        }
    }

    //imcomplète par rapport à l'ihm
    private void gererCarteOrange() {
        /*
            ATTENTION : cette méthode influence les piles de cartes, il faudra donc rajouté des lignes pour actualiser l'IHM
         */
 /*Méthode qui permet a un joueur de piocher deux cartes a la fin de son tour*/
        Aventurier joueur = getJoueurCourant();
        boolean carteMDEpiochée = false;
        ArrayList<CarteOrange> cartesPiochées = new ArrayList<CarteOrange>();
        VueAventurier vue = getIHM();
        //On picohe deux cartes et on vérifie a chaque fois si la pioche est vide
        for (int i = 0; i < 2; i++) {
            cartesPiochées.add(piocheCarteOrange());
            if (getPiocheOranges().empty()) {
                Collections.shuffle(getDefausseOranges());
                for (CarteOrange c : getDefausseOranges()) {
                    addPiocheOrange(c);
                }
                viderDefausseOranges();
            }
        }
        //Pour les cartes les piochées, on vérifie si celle-ci sont des cartes "montée des eaux"
        for (CarteOrange c : cartesPiochées) {
            if (c.getRole().equals("Montée des eaux")) {
                this.niveauEau++;
                carteMDEpiochée = true;
                addDefausseOranges(c);
            } else {
                joueur.piocheCarte(c);
            }
        }
        //Si la pioche de carte bleue n'est pas vide et qu'on a pioché au moins
        //une carte MDE alors on mélange puis remet la defausse de carte bleue sur le tas de pioche
        if (!getPiocheBleues().empty() && carteMDEpiochée) {
            Collections.shuffle(getDefausseBleues());
            for (CarteBleue b : getDefausseBleues()) {
                addPiocheBleue(b);
            }
            viderDefausseBleues();
        }
        //Si la main contient plus de 5 cartes
        if (joueur.getMain().size() > 5) {
            int nbCarteDeTrop = joueur.getMain().size() - 5;
            for (int i = 0; i < nbCarteDeTrop; i++) {
                //appeler une méthode de l'ihm pour défausser une carte.
            }
        }
        if (getIHM() != null){
            vue.majNiveauEau(niveauEau);
        }
    }

    private void gererCarteBleue() {
        /*Méthode qui permet au joueur de piocher des cartes innondation a la fin de son tour*/
        //On pioche un certain nombre de cartes en fonction du niveau d'eau
        int nbPioche;
        int nivEau = this.getNiveau();
        if (nivEau >= 8) {
            nbPioche = 5;
        } else if (nivEau >= 6) {
            nbPioche = 4;
        } else if (nivEau >= 3) {
            nbPioche = 3;
        } else {
            nbPioche = 2;
        }
        CarteBleue c;
        //On influence donc l'état de la tuile concernée par cette carte
        for (int i = 0; i < nbPioche; i++) {
            c = piocheCarteBleue();
            Tuile t = getGrille().getTuile(c.getInnonde().getNom());
            if (t.getEtat() == Utils.EtatTuile.ASSECHEE) {
                t.setEtat(Utils.EtatTuile.INONDEE);
                addDefausseBleues(c);
            } else if (t.getEtat() == Utils.EtatTuile.INONDEE) {
                t.setEtat(Utils.EtatTuile.COULEE);
                if (!t.getPossede().isEmpty() && !this.estPerdu()) {
                    boolean joueurNoyé = false;
                    int indice = 0;
                    ArrayList<Aventurier> jEnDetresse = new ArrayList<Aventurier>();
                    jEnDetresse.addAll(t.getPossede());
                    while (indice < jEnDetresse.size() && !joueurNoyé) {
                        if (deplacementPossible(jEnDetresse.get(indice))) {
                            gererDeplacement(jEnDetresse.get(indice));
                        } else {
                            jEnDetresse.get(indice).setTuile(null);
                            joueurNoyé = true;
                        }
                        indice++;
                    }
                }
            }
            if (getPiocheBleues().empty()) {
                if (Parameters.ALEAS) {
                    Collections.shuffle(getDefausseBleues());
                }
                for (CarteBleue b : getDefausseBleues()) {
                    addPiocheBleue(b);
                }
                viderDefausseBleues();
            }
        }
    }

    private void iniJeu() {
        //initialisations des tableaux/vecteurs
        nomJoueurs = new HashMap<Aventurier, String>();
        trésors = new Trésor[4];
        piocheOranges = new Stack<CarteOrange>();
        defausseOranges = new ArrayList<CarteOrange>();
        piocheBleues = new Stack<CarteBleue>();
        defausseBleues = new ArrayList<CarteBleue>();
        joueurs = new ArrayList<Aventurier>();

        //initialisations
        iniTrésor();
        iniGrille();
        iniCartes();
    }

    //à compléter avec l'ihm
    private void debutJeu() {
        //méthode qui :
        /*
            - démarre le jeu
            - tire les premieres cartes innondations
            - place les aventuriers
            - distribue les cartes Trésor
         */

        for (Aventurier a : getNomJoueurs().keySet()) {
            getJoueurs().add(a);
        }
        this.pouvoirPiloteDispo = true;
        this.pouvoirIngénieurUsé = false;
        this.jeuEnCours = true;
        Grille g = getGrille();
        //tout ceci dépendant du parametres.ALEAS
        if (!Parameters.ALEAS) {
            g.getTuile(3, 0).setEtat(Utils.EtatTuile.INONDEE);
            g.getTuile(1, 3).setEtat(Utils.EtatTuile.INONDEE);
            g.getTuile(3, 3).setEtat(Utils.EtatTuile.INONDEE);
            g.getTuile(3, 5).setEtat(Utils.EtatTuile.INONDEE);
            g.getTuile(5, 3).setEtat(Utils.EtatTuile.INONDEE);
            g.getTuile(2, 2).setEtat(Utils.EtatTuile.COULEE);
            g.getTuile(2, 3).setEtat(Utils.EtatTuile.COULEE);
            g.getTuile(2, 4).setEtat(Utils.EtatTuile.COULEE);
            g.getTuile(4, 3).setEtat(Utils.EtatTuile.COULEE);

            for (Aventurier a : getJoueurs()) {
                if (a.getCouleur() == Pion.BLANC) {
                    g.getTuile(1, 2).addAventurier(a);
                } else if (a.getCouleur() == Pion.BLEU) {
                    g.getTuile(3, 2).addAventurier(a);
                } else if (a.getCouleur() == Pion.JAUNE) {
                    g.getTuile(3, 1).addAventurier(a);
                } else if (a.getCouleur() == Pion.NOIR) {
                    g.getTuile(2, 1).addAventurier(a);
                } else if (a.getCouleur() == Pion.ROUGE) {
                    g.getTuile(3, 0).addAventurier(a);
                } else if (a.getCouleur() == Pion.VERT) {
                    g.getTuile(4, 2).addAventurier(a);
                }
                this.joueurCourant = a;
                gererCarteOrange();
            }
        } else { // ALEAS == true
            gererCarteBleue();
            for (Aventurier a : getJoueurs()) {
                boolean randomCorrect = false;
                do {
                    int x = (int) (Math.random() * 5);
                    int y = (int) (Math.random() * 5);
                    if (g.getTuile(x, y) != null) {
                        g.getTuile(x, y).addAventurier(a);
                        this.joueurCourant = a;
                        gererCarteOrange();
                        randomCorrect = true;
                    }
                } while (!randomCorrect);
            }
        }
        this.joueurCourant = getJoueurs().get(0);
        if (getJoueurCourant().getCouleur() == Pion.JAUNE) {
            this.nbActions = 4;
        } else {
            this.nbActions = 3;
        }
        ihm = new VueAventurier(getGrille());
        getIHM().dessinCartes(getJoueurCourant().getMain());
        ihm.addObservateur(this);
    }

    private void iniCartes() {
        //Création des cartes oranges (trésor)        
        ArrayList<CarteOrange> tmpOranges = new ArrayList<CarteOrange>();
        for (int i = 0; i < 3; i++) {
            tmpOranges.add(new CarteHelicoptere());
        }
        for (int i = 0; i < 5; i++) {
            tmpOranges.add(new CarteTrésor(NomTresor.LE_CRISTAL_ARDENT));
            tmpOranges.add(new CarteTrésor(NomTresor.LA_STATUE_DU_ZEPHYR));
            tmpOranges.add(new CarteTrésor(NomTresor.LE_CALICE_DE_L_ONDE));
            tmpOranges.add(new CarteTrésor(NomTresor.LA_PIERRE_SACREE));
        }
        for (int i = 0; i < 2; i++) {
            tmpOranges.add(new CarteMonteeDesEaux()); //2 cartes montée des eaux, modification : 18/06/2018 (m2107 consignes m.à.j.)
            tmpOranges.add(new CarteSacDeSable());
        }
        Collections.shuffle(tmpOranges);
        //Ajout de celles-ci dans la pioche orange.
        for (CarteOrange c : tmpOranges) {
            piocheOranges.push(c);
        }

        //Création des cartes bleues (innondation)        
        ArrayList<CarteBleue> tmpBleues = new ArrayList<CarteBleue>();
        for (Tuile t : grille.getGrille()) {
            tmpBleues.add(new CarteBleue(t));
        }
        Collections.shuffle(tmpBleues);
        //Ajout de celles-ci dans la pioche bleue.
        for (CarteBleue c : tmpBleues) {
            piocheBleues.push(c);
        }
    }
    private void iniGrille() {
        //Génération des 24 tuiles
        ArrayList<Tuile> Tuiles = new ArrayList<Tuile>();
        for (int i = 1; i < 25; i++) {
            Tuiles.add(new Tuile(NomTuile.getFromNb(i)));
        }

        //Initialisation de la Grille
        if (Parameters.ALEAS) { //ALEAS == true
            Collections.shuffle(Tuiles);
        }
        grille = new Grille(Tuiles);

    }
    private void iniTrésor() {
        //Création des Trésors
        trésors[0] = new Trésor(NomTresor.LA_PIERRE_SACREE);
        trésors[1] = new Trésor(NomTresor.LA_STATUE_DU_ZEPHYR);
        trésors[2] = new Trésor(NomTresor.LE_CRISTAL_ARDENT);
        trésors[3] = new Trésor(NomTresor.LE_CALICE_DE_L_ONDE);
    }

    private boolean estTerminé() {
        return (estPerdu() || !jeuEnCours);
    }
    private boolean estPerdu() {
        Grille g = getGrille();
        Utils.EtatTuile coulee = Utils.EtatTuile.COULEE;
        int joueurVivant = getNbJoueur();
        for (Aventurier a : getNomJoueurs().keySet()) {
            if (a.getTuile() == null) {
                joueurVivant--;
            }
        }
        return (g.getTuile(NomTuile.HELIPORT).getEtat() == coulee
                || getNiveau() >= 10
                || (g.getTuile(NomTuile.LE_TEMPLE_DU_SOLEIL).getEtat() == coulee && g.getTuile(NomTuile.LE_TEMPLE_DE_LA_LUNE).getEtat() == coulee && !getTrésors()[0].isGagne())
                || (g.getTuile(NomTuile.LE_JARDIN_DES_HURLEMENTS).getEtat() == coulee && g.getTuile(NomTuile.LE_JARDIN_DES_MURMURES).getEtat() == coulee && !getTrésors()[1].isGagne())
                || (g.getTuile(NomTuile.LA_CAVERNE_DES_OMBRES).getEtat() == coulee && g.getTuile(NomTuile.LA_CAVERNE_DU_BRASIER).getEtat() == coulee && !getTrésors()[2].isGagne())
                || (g.getTuile(NomTuile.LE_PALAIS_DE_CORAIL).getEtat() == coulee && g.getTuile(NomTuile.LE_PALAIS_DES_MAREES).getEtat() == coulee && !getTrésors()[3].isGagne())
                || joueurVivant < nbJoueurs);
    }
    
    private void actualiserJeu() {

        Aventurier jCourant = getJoueurCourant();
        pouvoirIngénieurUsé = false;
        
        if (getNbAction() == 0) {
            pouvoirPiloteDispo = true;
            int indexJNext;
            if (getJoueurs().indexOf(jCourant) + 1 >= getNbJoueur()) {
                indexJNext = 0;
            } else {
                indexJNext = getJoueurs().indexOf(jCourant) + 1;
            }
            gererCarteOrange();
            gererCarteBleue();
            this.joueurCourant = getJoueurs().get(indexJNext);
            if (getJoueurCourant().getCouleur() == Pion.JAUNE) {
                this.nbActions = 4;
            } else {
                this.nbActions = 3;
            }
        }

        /*while (!this.estTerminé()) {}
        //à compléter avec l'ihm.
        if (estPerdu()) {
            //partie perdue (a afficher sur l'ihm)
            int joueurVivant = getNbJoueur();
            for (Aventurier a : getNomJoueurs().keySet()) {
                if (a.getTuile() == null) {
                    joueurVivant--;
                }
            }
            if (grille.getTuile(NomTuile.HELIPORT).getEtat() == Utils.EtatTuile.COULEE) {
                //L'héliport à sombré.
            } else if (getNiveau() >= 10) {
                //Le niveau d'eau vous a submergé.
            } else if (joueurVivant < getNbJoueur()) {
                //Un de vos coéquipier à sombré.
            } else {
                //Les tuiles de trésor ont sombrées.
            }
        } else {
            //Partie gagnée, WP !
        }*/
    }

    //METHODES UTILES
    private int getNbAction() {
        return this.nbActions;
    }
    private VueAventurier getIHM() {
        return this.ihm;
    }
    private Aventurier getJoueurCourant() {
        return this.joueurCourant;
    }
    private Trésor[] getTrésors() {
        return trésors;
    }
    private int getNbJoueur() {
        return this.nbJoueurs;
    }
    private Grille getGrille() {
        return grille;
    }
    private int getNiveau() {
        return niveauEau;
    }
    private ArrayList<Tuile> calculTouteTuileDispo(Grille g) {
        ArrayList<Tuile> touteTuileDispo = new ArrayList<Tuile>();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (g.getTuile(i, j) != null) {
                    if (g.getTuile(i, j).getEtat() != Utils.EtatTuile.COULEE) {
                        touteTuileDispo.add(g.getTuile(i, j));
                    }
                }
            }
        }
        return touteTuileDispo;
    }
    public HashMap<Aventurier, String> getNomJoueurs() {
        return nomJoueurs;
    }
    public ArrayList<Aventurier> getJoueurs() {
        return this.joueurs;
    }

    //méthodes pour les cartes bleues
    //pioche
    private Stack<CarteBleue> getPiocheBleues() {
        return piocheBleues;
    }
    private CarteBleue piocheCarteBleue() {
        return this.piocheBleues.pop();
    }
    private void addPiocheBleue(CarteBleue c) {
        this.piocheBleues.push(c);
    }
    //défausse
    private ArrayList<CarteBleue> getDefausseBleues() {
        return this.defausseBleues;
    }
    private void addDefausseBleues(CarteBleue carte) {
        this.defausseBleues.add(carte);
    }
    private void viderDefausseBleues() {
        this.defausseBleues.removeAll(this.defausseBleues);
    }

    //méthodes pour les cartes oranges
    //pioche
    private Stack<CarteOrange> getPiocheOranges() {
        return piocheOranges;
    }
    private CarteOrange piocheCarteOrange() {
        return this.piocheOranges.pop();
    }
    private void addPiocheOrange(CarteOrange c) {
        this.piocheOranges.push(c);
    }
    //défausse
    private ArrayList<CarteOrange> getDefausseOranges() {
        return this.defausseOranges;
    }
    private void addDefausseOranges(CarteOrange carte) {
        this.defausseOranges.add(carte);
    }
    private void viderDefausseOranges() {
        this.defausseOranges.removeAll(this.defausseOranges);
    }

    //CONSTUCTEUR
    public Controleur() {
        /*
        accueil = new VueAccueil();
        accueil.addObservateur(this);
         */
        iniJeu();
        this.niveauEau = 1;
        this.nbJoueurs = 2;
        //this.nomJoueurs.put(new Pilote(), "j1");
        //this.nomJoueurs.put(new Messager(), "j2");
        this.nomJoueurs.put(new Ingénieur(), "j3");
        this.nomJoueurs.put(new Plongeur(), "j4");
        Parameters.setLogs(false);
        Parameters.setAleas(true);
        debutJeu();
    }

    //MAIN
    public static void main(String[] args) {
        new Controleur();
    }
}
