package ileinterdite;

import model.*;
import util.*;
import util.Utils.Pion;
//import view.VueAventurier;
import java.util.Stack;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;
/*
    Groupe C2 :  Prapant.B, Labartino.Y, Giroud.T, Malod.V
*/
public class Controleur implements Observateur {

    /*
    Note :  pour une lecture agréable du code, nous vous invitons à
            réduire depuis netbeans chaque méthode pour n'en apercevoir
            que le titre et les paramètres de celles-ci.
    */
    
    private Grille grille;
    private int niveauEau;
    private Trésor[] trésors;
    private HashMap<Aventurier, String> joueurs;
    private Stack<CarteBleue> piocheBleues;
    private ArrayList<CarteBleue> defausseBleues;
    private Stack<CarteOrange> piocheOranges;
    private ArrayList<CarteOrange> defausseOranges;
    private boolean pouvoirPiloteDispo, jeuEnCours;
    private int nbJoueurs;

    @Override
    public void traiterMessage(Message m) {
    }

    //METHODES DE GESTION DU JEU
    private boolean gererDeplacement(Aventurier joueur) {
        //Gère les déplacements d'un joueur
        boolean deplEffectué = true;
        Grille g = getGrille();
        ArrayList<Tuile> tuilesDispo = new ArrayList<Tuile>();
        //variables pour les saisis utilisateur :
        Scanner sc = new Scanner(System.in);
        String choix;
        Integer X, Y;
        //Pouvoir pilote (interface texte) :
        if (joueur.getCouleur() == Utils.Pion.BLEU && pouvoirPiloteDispo) {
            System.out.println("Voulez-vous vous déplacer sur n'importe qu'elle tuile ? (utilisable une fois par tour) :");
            System.out.print("(oui/non) => ");
            choix = sc.nextLine();
            if (choix.equals("O")) {
                pouvoirPiloteDispo = false;
                tuilesDispo = calculTouteTuileDispo(g);
            } else {
                tuilesDispo = joueur.calculTuileDispo(g);
            }
        //Sinon si non-pilote (ou sans pouvoir) :
        } else {
            tuilesDispo = joueur.calculTuileDispo(g);
        }
        //vérification tuileDispo
        if (tuilesDispo.size() > 0) {
            //affichage tuiles dispo
            g.afficheGrilleTexte(joueur);
            System.out.println("Voici la liste des tuiles disponible :");
            for (Tuile t : tuilesDispo) {
                t.affiche();
            }
            //choix utilisateur
            boolean choixValide = false;
            do {
                System.out.println("\n\tChoix de la tuile :");
                System.out.println("Rentrez : X = null\npour annuler tout déplacement.");
                System.out.print("X = ");
                choix = sc.nextLine();
                if (choix.equals("null")) {
                    choixValide = true;
                } else {
                    X = new Integer(choix);
                    System.out.print("Y = ");
                    choix = sc.nextLine();
                    Y = new Integer(choix);
                    Tuile tuileChoisie = g.getTuile(X - 1, Y - 1);
                    if (tuilesDispo.contains(tuileChoisie)) {
                        choixValide = true;
                        //le joueur se déplace.
                        joueur.seDeplace(tuileChoisie);
                    } else {
                        System.out.println("\tCHOIX NON-VALIDE.");
                    }
                }
            } while (!choixValide);
            if (choix.equals("null")) {
                return !deplEffectué;
            } else {
                return deplEffectué;
            }
        } else {
            return !deplEffectué;
        }
    }

    private boolean gererAssechement(Aventurier joueur) {
        //méthode qui permet a un aventurier d'assécher une tuile
        boolean assEffectué = true;
        Grille g = getGrille();
        ArrayList<Tuile> tuilesDispo = joueur.calculTuileAss(g);
        Utils.EtatTuile sec = Utils.EtatTuile.ASSECHEE;
        int nbAssechement = 1;
        //variables pour la saisie utilisateur 
        Scanner sc = new Scanner(System.in);
        String choix;
        Integer X, Y;
        //Si le joueur est un ingénieur : 
        if ((joueur.getCouleur() == Utils.Pion.ROUGE) && (tuilesDispo.size() >= 2)) {
            System.out.println("Voulez-vous assecher 2 tuiles pour cette action ?");
            System.out.print("\t(oui/non)=> ");
            choix = sc.nextLine();
            if (choix.equals("O")) {
                nbAssechement = 2;
            }
        }
        //vérification tuileDispo
        if (tuilesDispo.size() > 0) {
            //affichage des tuiles disponibles 
            System.out.println("Voici la liste des tuiles disponible :");
            for (Tuile t : tuilesDispo) {
                t.affiche();
            }
            g.afficheGrilleTexte(joueur);
            //choix de l'utilisateur
            int i = 0;
            do {
                boolean choixValide = false;
                do {
                    System.out.println("\n\tChoix de la tuile :");
                    System.out.println("Rentrez : X = null\npour annuler tout déplacement.");
                    System.out.print("X = ");
                    choix = sc.nextLine();
                    if (choix.equals("null")) {
                        choixValide = true;
                    } else {
                        X = new Integer(choix);
                        System.out.print("Y = ");
                        choix = sc.nextLine();
                        Y = new Integer(choix);
                        Tuile tuileChoisie = g.getTuile(X - 1, Y - 1);
                        if (tuilesDispo.contains(tuileChoisie)) {
                            choixValide = true;
                            //asséchement de la tuile
                            tuileChoisie.setEtat(sec);
                        } else {
                            System.out.println("\tCHOIX NON-VALIDE.");
                        }
                    }
                } while (!choixValide);
                i++;
            } while (i < nbAssechement && !choix.equals("null"));
            if (choix.equals("null")) {
                return !assEffectué;
            } else {
                return assEffectué;
            }
        } else {
            return !assEffectué;
        }
    }

    private boolean gererDonation(Aventurier joueur) {
        boolean donnationEffectuée = true;
        //vérification de la main du joueur
        if (joueur.getMain().isEmpty()){
            return !donnationEffectuée;
        }
        int nbDispo = 0;
        ArrayList<CarteOrange> cartesDispo = new ArrayList<CarteOrange>();
        for (CarteOrange c : joueur.getMain()) {
            if (!(c.getRole().equals("Helicoptere") || c.getRole().equals("Sac de sable"))) {
                nbDispo++;
                cartesDispo.add(c);
            }
        }
        if (nbDispo == 0) {
            return !donnationEffectuée;
        }
        //début méthode
        Grille g = getGrille();
        ArrayList<Tuile> tuiles = g.getGrille();
        ArrayList<Aventurier> jDispo = new ArrayList<Aventurier>();
        //vérification s'il s'agit du messager pour son pouvoir
        if (joueur.getCouleur()==Pion.BLANC) {
            for (Tuile t : tuiles){
                if (t.getPossede().size() > 0){
                    for (Aventurier a : t.getPossede()){
                        jDispo.add(a);
                    }
                }
            }
        } else {
            if (joueur.getTuile().getPossede().size() > 0) {
                for(Aventurier a : joueur.getTuile().getPossede()){
                    jDispo.add(a);
                }
            }
        }
        //variables pour la saisie utilisateur 
        Scanner sc = new Scanner(System.in);
        String choix;
        Integer choixInt;
        boolean choixConforme = false;
        //Joueur a qui on donne
        Aventurier jRecoit = null;
        //Carte donnée
        CarteOrange cCédée = null;
        
        if (jDispo.size() > 0) {
            do {
                System.out.println("Voici la liste des aventuriers à qui vous pouvez donner une carte :");
                for (Aventurier a : jDispo) {
                    System.out.println("\t- "+a.getClass().toString().substring(12)+" ("+getJoueurs().get(a)+")");
                }
                System.out.println("\t- Annuler\n");
                System.out.println("choix (role ou annuler) => ");
                choix = sc.nextLine();
                choix = choix.toLowerCase().substring(0, 2);
                if (choix.equals("an")) {
                    choixConforme = true;
                } else {
                    for (Aventurier a : jDispo) {
                        if (choix.equals(a.getClass().toString().toLowerCase().substring(12, 14))) {
                            choixConforme = true;
                            jRecoit = a;
                        }
                    }
                }
            } while (!choixConforme);
            if (choix.toLowerCase().substring(0, 2).equals("an")) {
                return !donnationEffectuée;
            } else {
                System.out.println("Voici les cartes que vous pouvez céder : ");
                for (int i = 0 ; i < cartesDispo.size() ; i++) {
                    System.out.print("\t ("+(i+1)+") - ");
                    cartesDispo.get(i).affiche();
                }
                choixConforme = false;
                do {
                    System.out.println("\n\tChoix => ");
                    choix = sc.nextLine();
                    choixInt = new Integer(choix);
                    if (choixInt <= cartesDispo.size() && choixInt >= 1) {
                        choixConforme = true;
                        cCédée = cartesDispo.get(choixInt-1);
                        //on passe la carte d'un joueur à l'autre...
                        cCédée.affiche();
                        joueur.defausseCarte(cCédée);
                        System.out.println("passage de la carte ...");
                        jRecoit.piocheCarte(cCédée);
                        cCédée.affiche();
                    }
                } while (!choixConforme);
                return donnationEffectuée;
            }
        } else {
            return !donnationEffectuée;
        }
    }

    private boolean gererGainTresor(Aventurier joueur) {
        boolean gainEffectué = true;
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
                    cartesTresors.add((CarteTrésor)c);
                }
            }
            for (CarteTrésor c : cartesTresors) {
                if(c.getNomTresor()==NomTresor.LA_PIERRE_SACREE){
                    nbCarteTresorPS++;
                } else if (c.getNomTresor()==NomTresor.LA_STATUE_DU_ZEPHYR) {
                    nbCarteTresorSZ++;
                } else if (c.getNomTresor()==NomTresor.LE_CALICE_DE_L_ONDE) {
                    nbCarteTresorCO++;
                } else if (c.getNomTresor()==NomTresor.LE_CRISTAL_ARDENT) {
                    nbCarteTresorCA++;
                } 
            }
            if ((nbCarteTresorCA | nbCarteTresorCO | nbCarteTresorPS | nbCarteTresorSZ) >= 4) {
                if (joueur.getTuile().getNom()==NomTuile.LE_TEMPLE_DU_SOLEIL || joueur.getTuile().getNom()==NomTuile.LE_TEMPLE_DE_LA_LUNE) {
                    if (nbCarteTresorPS >= 4) {
                        for (CarteOrange cO : joueur.getMain()) {
                            for (int i = 0 ; i < 4 ; i++) {
                                CarteTrésor cTresor = cartesTresors.get(i); 
                                if (cTresor.getNomTresor()==NomTresor.LA_PIERRE_SACREE && cO==cTresor) {
                                    joueur.getMain().remove(cO);
                                }
                            }
                        }
                        getTrésors()[0].setGagne(true);
                        return gainEffectué;
                    }
                } else if (joueur.getTuile().getNom()==NomTuile.LE_JARDIN_DES_HURLEMENTS || joueur.getTuile().getNom()==NomTuile.LE_JARDIN_DES_MURMURES) {
                    if (nbCarteTresorSZ >= 4) {
                        for (CarteOrange cO : joueur.getMain()) {
                            for (int i = 0 ; i < 4 ; i++) {
                                CarteTrésor cTresor = cartesTresors.get(i); 
                                if (cTresor.getNomTresor()==NomTresor.LA_STATUE_DU_ZEPHYR && cO==cTresor) {
                                    joueur.getMain().remove(cO);
                                }
                            }
                        }
                        getTrésors()[1].setGagne(true);
                        return gainEffectué;
                    }  
                } else if (joueur.getTuile().getNom()==NomTuile.LA_CAVERNE_DES_OMBRES || joueur.getTuile().getNom()==NomTuile.LA_CAVERNE_DES_OMBRES) {
                    if (nbCarteTresorCA >= 4) {
                        for (CarteOrange cO : joueur.getMain()) {
                            for (int i = 0 ; i < 4 ; i++) {
                                CarteTrésor cTresor = cartesTresors.get(i); 
                                if (cTresor.getNomTresor()==NomTresor.LE_CRISTAL_ARDENT && cO==cTresor) {
                                    joueur.getMain().remove(cO);
                                }
                            }
                        }
                        getTrésors()[2].setGagne(true);
                        return gainEffectué;
                    }
                } else if (joueur.getTuile().getNom()==NomTuile.LE_PALAIS_DE_CORAIL || joueur.getTuile().getNom()==NomTuile.LE_PALAIS_DES_MAREES) {
                    if (nbCarteTresorCO >= 4) {
                        for (CarteOrange cO : joueur.getMain()) {
                            for (int i = 0 ; i < 4 ; i++) {
                                CarteTrésor cTresor = cartesTresors.get(i); 
                                if (cTresor.getNomTresor()==NomTresor.LE_CALICE_DE_L_ONDE && cO==cTresor) {
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

    //non-complète
    private void gererCarteOrange(Aventurier a) {
        /*Méthode qui permet a un joueur de piocher deux cartes a la fin de son tour*/
        boolean carteMDEpiochée = false;
        ArrayList<CarteOrange> cartesPiochées = new ArrayList<CarteOrange>();
        //On picohe deux cartes et on vérifie a chaque fois si la pioche est vide
        System.out.println("\n"+a.getClass().toString().substring(12)+", vous piochez deux carte oranges.");
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
                System.out.println("Vous avez pioché une carte Montée des eaux, le niveau d'eau monte...");
                this.niveauEau++;
                carteMDEpiochée = true;
                addDefausseOranges(c);
            } else {
                a.piocheCarte(c);
            }
        }
        //Si la pioche de carte bleue n'est pas vide et qu'on a pioché au moins
        //une carte MDE alors on mélange puis remet la defausse de carte bleue sur le tas de pioche
        if (!getPiocheBleues().empty() && carteMDEpiochée) {
            Collections.shuffle(getDefausseBleues());
            System.out.println("Mélange de la défausse de carte innondations sur la pioche");
            for (CarteBleue b : getDefausseBleues()) {
                addPiocheBleue(b);
            }
            viderDefausseBleues();
        }
        //Si la main contient plus de 5 cartes
        if (a.getMain().size() > 5) {
            Scanner sc = new Scanner(System.in);
            String choix;
            Integer numChoix;
            System.out.println("Vous avez plus de 5 cartes dans votre main...");
            int nbCarteDeTrop = a.getMain().size() - 5;
            for (int i = 0; i < nbCarteDeTrop; i++) {
                System.out.println("Voici votre main (" + a.getMain().size() + " cartes) : ");
                int numCarte = 1;
                for (CarteOrange c : a.getMain()) {
                    System.out.println("\t [" + numCarte + "] - " + c.getRole());
                    numCarte++;
                }
                System.out.println("Quelle carte voulez-vous défaussez de votre main ?\nNB: s'il s'agit d'une carte spéciale, vous pourrez l'utiliser.");
                System.out.print("\t choix (numéro de la carte ) : ");
                choix = sc.nextLine();
                numChoix = new Integer(choix);
                if (a.getMain().get(numChoix - 1).getRole().equals("Helicoptere") || a.getMain().get(numChoix - 1).getRole().equals("Sac de sable")) {
                    boolean choixConforme = false;
                    do {
                        System.out.println("Il s'agit d'une carte à pouvoir spécial,\nvoulez-vous vous en débarrasser(1) ou jouer cette carte(2) ?");
                        System.out.print("\tchoix (1/2) : ");
                        choix = sc.nextLine();
                        if (choix.equals("1")) {
                            a.defausseCarte(a.getMain().get(numChoix - 1));
                            choixConforme = true;
                        } else if (choix.equals("2")) {
                            // à  compléter pour que le joueur puisse utiliser sa carte spéciale ou non
                            
                            choixConforme = true;
                        }
                    } while (!choixConforme);
                } else {
                    a.defausseCarte(a.getMain().get(numChoix - 1));
                }
            }
        }
        a.afficheMain();
    }

    private void gererCarteBleue() {
        /*Méthode qui permet au joueur de piocher des cartes innondation a la fin de son tour*/
        //On pioche un certain nombre de cartes en fonction du niveau d'eau
        int nbPioche;
        int nivEau = this.getNiveau();
        System.out.print("Le niveau d'eau s'élève à : " + nivEau + ".\nVous piochez donc : ");
        if (nivEau >= 8) {
            System.out.println("5 cartes.\n");
            nbPioche = 5;
        } else if (nivEau >= 6) {
            System.out.println("4 cartes.\n");
            nbPioche = 4;
        } else if (nivEau >= 3) {
            System.out.println("3 cartes.\n");
            nbPioche = 3;
        } else {
            System.out.println("2 cartes.\n");
            nbPioche = 2;
        }
        CarteBleue c;
        //On influence donc l'état de la tuile concernée par cette carte
        for (int i = 0; i < nbPioche; i++) {
            c = piocheCarteBleue();
            Tuile t = getGrille().getTuile(c.getInnonde().getNom());
            System.out.println("\nla carte innondation " + t.getNom().toString() + " à été piochée...");
            if (t.getEtat() == Utils.EtatTuile.ASSECHEE) {
                t.setEtat(Utils.EtatTuile.INONDEE);
                System.out.print("La tuile correspondante est désormais innondée : ");
                t.affiche();
                addDefausseBleues(c);
            } else if (t.getEtat() == Utils.EtatTuile.INONDEE) {
                t.setEtat(Utils.EtatTuile.COULEE);
                System.out.print("La tuile correspondante est désormais coulée : ");
                t.affiche();
                if (t.getPossede().size() > 0) {
                    System.out.println("La tuile contenait un ou plusieurs aventuriers ! Il faut qu'il(s) nage(nt) :");
                    boolean deplAventurier = false;
                    for (Aventurier a : t.getPossede()) {
                        deplAventurier = gererDeplacement(a);
                        if (!deplAventurier) {
                            a.setTuile(null);
                        }
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

    private void debutJeu() {
        //méthode qui :
        /*
            - tire les premieres cartes innondations
            - place les aventuriers
            - distribue les cartes Trésor
         */
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

            for (Aventurier a : getJoueurs().keySet()) {
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
                gererCarteOrange(a);
            }
        } else { // ALEAS == true
            gererCarteBleue();
            for (Aventurier a : getJoueurs().keySet()) {
                boolean randomCorrect = false;
                do {
                    int x = (int) (Math.random()*5);
                    int y = (int) (Math.random()*5);
                    if (g.getTuile(x, y) != null) {
                        g.getTuile(x, y).addAventurier(a);
                        gererCarteOrange(a);
                        randomCorrect = true;
                    }
                } while (!randomCorrect);
            }
        }
    }
    
    private void iniCartes(){
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
    
    private void iniGrille(){
        //Génération des 24 tuiles
        ArrayList<Tuile> Tuiles = new ArrayList<Tuile>();
        for (int i = 1; i < 25; i++) {
            Tuiles.add(new Tuile(NomTuile.getFromNb(i)));
        }

        //Initialisation de la Grille
        if (Parameters.ALEAS){ //ALEAS == true
            Collections.shuffle(Tuiles);
        }
        grille = new Grille(Tuiles);

    }
    
    private void iniTrésor(){
        //Création des Trésors
        trésors[0] = new Trésor(NomTresor.LA_PIERRE_SACREE);
        trésors[1] = new Trésor(NomTresor.LA_STATUE_DU_ZEPHYR);
        trésors[2] = new Trésor(NomTresor.LE_CRISTAL_ARDENT);
        trésors[3] = new Trésor(NomTresor.LE_CALICE_DE_L_ONDE);
    }
    
    private boolean estTerminé(){
        return (estPerdu() || !jeuEnCours);
    }
    
    private boolean estPerdu(){
        Grille g = getGrille();
        Utils.EtatTuile coulee = Utils.EtatTuile.COULEE;
        int joueurVivant = getNbJoueur();
        for (Aventurier a : getJoueurs().keySet()) {
            if (a.getTuile()==null) {
                joueurVivant--;
            }
        }
        return (   g.getTuile(NomTuile.HELIPORT).getEtat()==coulee
                || getNiveau() >= 10
                || (g.getTuile(NomTuile.LE_TEMPLE_DU_SOLEIL).getEtat()==coulee && g.getTuile(NomTuile.LE_TEMPLE_DE_LA_LUNE).getEtat()==coulee && !getTrésors()[0].isGagne())
                || (g.getTuile(NomTuile.LE_JARDIN_DES_HURLEMENTS).getEtat()==coulee && g.getTuile(NomTuile.LE_JARDIN_DES_MURMURES).getEtat()==coulee && !getTrésors()[1].isGagne())
                || (g.getTuile(NomTuile.LA_CAVERNE_DES_OMBRES).getEtat()==coulee && g.getTuile(NomTuile.LA_CAVERNE_DU_BRASIER).getEtat()==coulee && !getTrésors()[2].isGagne())
                || (g.getTuile(NomTuile.LE_PALAIS_DE_CORAIL).getEtat()==coulee && g.getTuile(NomTuile.LE_PALAIS_DES_MAREES).getEtat()==coulee && !getTrésors()[3].isGagne())
                || joueurVivant < nbJoueurs);
    }
    
    //METHODES INTERFACE TEXTE
    
    private void choixDifficulté(){
        //sélection de la difficulté
        Scanner sc = new Scanner(System.in);
        boolean choixConforme = false;
        String choix;
        Integer choixInt;
        do {
            System.out.println("Quel niveau de difficulté ? (1/2/3/4)");
            System.out.print("\t =>");
            choix = sc.nextLine();
            choixInt = new Integer(choix);
            if (choixInt >= 1 && choixInt <= 4) {
                this.niveauEau = choixInt;
                choixConforme = true;
            }
        } while (!choixConforme);
    }
    
    private void choixAventuriers(){
        //Création des Aventuriers.
        Scanner sc = new Scanner(System.in);
        boolean choixConforme = false;
        String choix;
        ArrayList<Aventurier> aventuriers = new ArrayList<Aventurier>();
        int nombreJ = getNbJoueur();
        if (Parameters.ALEAS) { //ALEAS == true
            aventuriers.add(new Pilote());
            aventuriers.add(new Navigateur());
            aventuriers.add(new Ingénieur());
            aventuriers.add(new Explorateur());
            aventuriers.add(new Messager());
            aventuriers.add(new Plongeur());
            Collections.shuffle(aventuriers);
        } else {
            ArrayList<String> avDispo = new ArrayList<String>();
            String[] listeAv = {"Pilote", "Navigateur", "Ingénieur", "Explorateur", "Messager", "Plongeur"};
            for (String s : listeAv) {
                avDispo.add(s);
            }
            for (int i = 0 ; i < nombreJ ; i++) {
                System.out.println("Veuillez sélectionner vos aventuriers parmi :");
                for (String s : avDispo) {
                    System.out.println("\t- "+s);
                }
                do {
                    choixConforme = false;
                    System.out.println("Choix");
                    System.out.print("\t =>");
                    choix = sc.nextLine();
                    choix = choix.toLowerCase().substring(0, 2);
                    if (choix.equals("pi") && avDispo.contains("Pilote")) {
                        avDispo.remove("Pilote");
                        aventuriers.add(new Pilote());
                        choixConforme = true;
                    } else if (choix.equals("na") && avDispo.contains("Navigateur")) {
                        avDispo.remove("Navigateur");
                        aventuriers.add(new Navigateur());
                        choixConforme = true;
                    } else if (choix.equals("in") && avDispo.contains("Ingénieur")) {
                        avDispo.remove("Ingénieur");
                        aventuriers.add(new Ingénieur());
                        choixConforme = true;
                    } else if (choix.equals("ex") && avDispo.contains("Explorateur")) {
                        avDispo.remove("Explorateur");
                        aventuriers.add(new Explorateur());
                        choixConforme = true;
                    } else if (choix.equals("me") && avDispo.contains("Messager")) {
                        avDispo.remove("Messager");
                        aventuriers.add(new Messager());
                        choixConforme = true;
                    } else if (choix.equals("pl") && avDispo.contains("Plongeur")) {
                        avDispo.remove("Plongeur");
                        aventuriers.add(new Plongeur());
                        choixConforme = true;
                    }
                } while (!choixConforme);
            }
        }
        //ajout de ceux-ci dans le HashMap des joueurs
        for (int i = 0 ; i < nombreJ ; i++) {
            joueurs.put(aventuriers.get(i), null);
        }

    }
    
    private void choixNbJoueurs (){
        //Choix de l'utilisateur du nombre de joueurs à jouer la partie (max 4)
        Scanner sc = new Scanner(System.in);
        boolean choixConforme = false;
        String choix;
        Integer choixInt;
        do {
            System.out.println("Combien de joueurs vont jouer ? Faites un choix (entier entre 2 et 4) : ");
            System.out.print("\t=> ");
            choix = sc.nextLine();
            choixInt = new Integer(choix);
            if (choixInt >= 2 && choixInt <= 4) {
                nbJoueurs = choixInt;
                choixConforme = true;
            }
        } while (!choixConforme);
    }
    
    private void choixNomJoueurs () {
        //sélection des noms de joueurs
        String choix;
        Scanner sc = new Scanner(System.in);
        for (Aventurier a : getJoueurs().keySet()) {
            System.out.println("Nom du joueur du role "+a.getClass().toString().substring(12)+" : ");
            choix = sc.nextLine();
            getJoueurs().put(a, choix);
        }
    }
    
    //METHODES UTILES
    
    private Trésor[] getTrésors(){
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
    public HashMap<Aventurier, String> getJoueurs() {
        return joueurs;
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
        this.defausseOranges.removeAll(this.defausseOranges);
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
        //initialisations des tableaux/vecteurs
        joueurs = new HashMap<Aventurier, String>();
        trésors = new Trésor[4];
        piocheOranges = new Stack<CarteOrange>();
        defausseOranges = new ArrayList<CarteOrange>();
        piocheBleues = new Stack<CarteBleue>();
        defausseBleues = new ArrayList<CarteBleue>();

        //initialisations
        iniTrésor();
        iniGrille();
        iniCartes();
        
        //Paramètres joueurs
        choixNbJoueurs();
        choixAventuriers();
        choixNomJoueurs();
        choixDifficulté();
        
        //Declaration de variable utiles pour l'interface texte
        Scanner sc = new Scanner(System.in);
        String choix;
        boolean choixConforme = false;
        
        //Tours de jeu
        debutJeu();
        pouvoirPiloteDispo = true;
        jeuEnCours = true;
        boolean actionEffectuée;
        while (!this.estTerminé()) {
            for (Aventurier a : joueurs.keySet()) {
                if (!estPerdu()) {
                    /*Lignes de test :*/
                    //grille.getTuile(NomTuile.HELIPORT).setEtat(Utils.EtatTuile.COULEE);
                    /*FIN des lignes de tests*/
                    int nbActions = 3;
                    String nomRole = a.getClass().toString().substring(12);
                    while (!choixConforme || nbActions > 0) {
                        choixConforme = false;
                        grille.afficheGrilleTexte(a);
                        System.out.println(joueurs.get(a) + "(" + nomRole + ") , quelle action voulez-vous réaliser ?");
                        System.out.println("\t1 - Se déplacer");
                        System.out.println("\t2 - Assécher");
                        System.out.println("\t3 - Donner une carte");
                        System.out.println("\t4 - Gagner un trésor");
                        System.out.println("\t5 - Finir mon tour");
                        System.out.print("choix (1 à 5) : ");
                        choix = sc.nextLine();
                        if (choix.equals("1")) {
                            choixConforme = true;
                            actionEffectuée = gererDeplacement(a);
                            if (actionEffectuée) {
                                nbActions--;
                            }
                        } else if (choix.equals("2")) {
                            choixConforme = true;
                            actionEffectuée = gererAssechement(a);
                            if (actionEffectuée) {
                                nbActions--;
                            }
                        } else if (choix.equals("3")) {
                            choixConforme = true;
                            actionEffectuée = gererDonation(a);
                            if (actionEffectuée) {
                                nbActions--;
                                a.afficheMain();
                            }
                        } else if (choix.equals("4")) {
                            choixConforme = true;
                            actionEffectuée = gererGainTresor(a);
                            if (actionEffectuée) {
                                nbActions--;
                            }
                        } else if (choix.equals("5")) {
                            choixConforme = true;
                            nbActions = 0;
                        }
                    }
                    gererCarteOrange(a);
                    gererCarteBleue();
                } else {
                    System.out.println("\033[31mPartie Perdue.\033[0m");
                }
            }
        }
    }

    //MAIN
    public static void main(String[] args) {
        new Controleur();
    }
}
