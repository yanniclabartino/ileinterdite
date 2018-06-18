package ileinterdite;

import model.*;
import util.*;
import util.Utils.Pion;
import view.VueAventurier;
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
    private VueAventurier[] ihm;
    private Trésor[] trésors;
    private HashMap<Aventurier, String> joueurs;
    private Stack<CarteBleue> piocheBleues;
    private ArrayList<CarteBleue> defausseBleues;
    private Stack<CarteOrange> piocheOranges;
    private ArrayList<CarteOrange> defausseOranges;
    private boolean pouvoirPiloteDispo;
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
            try {
                choix = choix.toUpperCase().substring(0, 1);
            } catch (StringIndexOutOfBoundsException e) {
                choix = "non";
            }
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
                    try {
                        X = new Integer(choix);
                    } catch (NumberFormatException e) {
                        X = 0;
                    }
                    System.out.print("Y = ");
                    choix = sc.nextLine();
                    try {
                        Y = new Integer(choix);
                    } catch (NumberFormatException e) {
                        Y = 0;
                    }
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
            try {
                choix = choix.toUpperCase().substring(0, 1);
            } catch (StringIndexOutOfBoundsException e) {
                choix = "non";
            }
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
                        try {
                            X = new Integer(choix);
                        } catch (NumberFormatException e) {
                            X = 0;
                        }
                        System.out.print("Y = ");
                        choix = sc.nextLine();
                        try {
                            Y = new Integer(choix);
                        } catch (NumberFormatException e) {
                            Y = 0;
                        }
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
        int cartesDispo = 0;
        for (CarteOrange c : joueur.getMain()) {
            if (!(c.getRole().equals("Helicoptere") || c.getRole().equals("Sac de sable"))) {
                cartesDispo++;
            }
        }
        if (cartesDispo == 0) {
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
        boolean choixConforme = false;
        if (jDispo.size() > 0) {
            do {
                System.out.println("Voici la liste des aventuriers à qui vous pouvez donner une carte :");
                for (Aventurier a : jDispo) {
                    System.out.println("\t- ["+a.getClass().toString().substring(12)+"] "+getJoueurs().get(a));
                }
                System.out.println("\t- Annuler\n");
                System.out.println("choix (role ou annuler) => ");
                choix = sc.nextLine();
                for (Aventurier a : jDispo) {
                    try {
                        if (choix.toLowerCase().substring(0, 2).equals(getJoueurs().get(a).toLowerCase().substring(0, 2))) {
                            choixConforme = true;
                        }
                    } catch (StringIndexOutOfBoundsException e){
                        choix = "#AUCUNE ENTREE#";
                        System.out.println("CHOIX NON-VALIDE");
                    }
                }
                if (choix.toLowerCase().substring(0, 2).equals("an")) {
                    choixConforme = true;
                }
            } while (!choixConforme);
            if (choix.toLowerCase().substring(0, 2).equals("an")) {
                return !donnationEffectuée;
            } else {
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
                joueur.getTuile().getNom()
            }
        }
        return !gainEffectué;
    }

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
            for (int i = 0; i < (a.getMain().size() - 5); i++) {
                System.out.println("Voici votre main (" + a.getMain().size() + " cartes) : ");
                int numCarte = 1;
                for (CarteOrange c : a.getMain()) {
                    System.out.println("\t [" + numCarte + "] - " + c.getRole());
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
            }
            if (getPiocheBleues().empty()) {
                Collections.shuffle(getDefausseBleues());
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
        //tout ceci dépendant du parametres.ALEAS
        if (!Parameters.ALEAS) {
            Grille g = getGrille();
            g.getTuile(3, 0).setEtat(Utils.EtatTuile.INONDEE);
            g.getTuile(1, 3).setEtat(Utils.EtatTuile.INONDEE);
            g.getTuile(3, 3).setEtat(Utils.EtatTuile.INONDEE);
            g.getTuile(3, 5).setEtat(Utils.EtatTuile.INONDEE);
            g.getTuile(5, 3).setEtat(Utils.EtatTuile.INONDEE);
            g.getTuile(2, 2).setEtat(Utils.EtatTuile.COULEE);
            g.getTuile(2, 3).setEtat(Utils.EtatTuile.COULEE);
            g.getTuile(2, 4).setEtat(Utils.EtatTuile.COULEE);
            g.getTuile(4, 3).setEtat(Utils.EtatTuile.COULEE);

            for (Aventurier aventuriers : joueurs.keySet()) {
                if (aventuriers.getCouleur() == Pion.BLANC) {
                    g.getTuile(1, 2).addAventurier(aventuriers);
                } else if (aventuriers.getCouleur() == Pion.BLEU) {
                    g.getTuile(3, 2).addAventurier(aventuriers);
                } else if (aventuriers.getCouleur() == Pion.JAUNE) {
                    g.getTuile(3, 1).addAventurier(aventuriers);
                } else if (aventuriers.getCouleur() == Pion.NOIR) {
                    g.getTuile(2, 1).addAventurier(aventuriers);
                } else if (aventuriers.getCouleur() == Pion.ROUGE) {
                    g.getTuile(3, 0).addAventurier(aventuriers);
                } else if (aventuriers.getCouleur() == Pion.VERT) {
                    g.getTuile(4, 2).addAventurier(aventuriers);
                }
                gererCarteOrange(aventuriers);
            }
        }
    }
    
    private void iniCartes(){
        //Création des cartes oranges (trésor)        
        ArrayList<CarteOrange> tmpOranges = new ArrayList<CarteOrange>();
        for (int i = 0; i < 3; i++) {
            tmpOranges.add(new CarteMonteeDesEaux());
            tmpOranges.add(new CarteHelicoptere());
        }
        for (int i = 0; i < 5; i++) {
            tmpOranges.add(new CarteTrésor(NomTresor.LE_CRISTAL_ARDENT));
            tmpOranges.add(new CarteTrésor(NomTresor.LA_STATUE_DU_ZEPHYR));
            tmpOranges.add(new CarteTrésor(NomTresor.LE_CALICE_DE_L_ONDE));
            tmpOranges.add(new CarteTrésor(NomTresor.LA_PIERRE_SACREE));
        }
        tmpOranges.add(new CarteSacDeSable());
        tmpOranges.add(new CarteSacDeSable());
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
        if (Parameters.ALEAS){
            Collections.shuffle(Tuiles);
        }
        grille = new Grille(Tuiles);

    }
    
    private void iniTrésor(){
        //Création des Trésors
        trésors[0] = new Trésor(NomTresor.LE_CRISTAL_ARDENT);
        trésors[1] = new Trésor(NomTresor.LA_PIERRE_SACREE);
        trésors[2] = new Trésor(NomTresor.LA_STATUE_DU_ZEPHYR);
        trésors[3] = new Trésor(NomTresor.LE_CALICE_DE_L_ONDE);
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
            try {
                choixInt = new Integer(choix);
            } catch (NumberFormatException e) {
                choixInt = 0;
            }
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
        if (Parameters.ALEAS) {
            aventuriers.add(new Pilote());
            aventuriers.add(new Navigateur());
            aventuriers.add(new Ingénieur());
            aventuriers.add(new Explorateur());
            aventuriers.add(new Messager());
            aventuriers.add(new Plongeur());
            aventuriers = Utils.melangerAventuriers(aventuriers);
        } else {
            ArrayList<String> avDispo = new ArrayList<String>();
            String[] listeAv = {"Pilote", "Navigateur", "Ingénieur", "Explorateur", "Messager", "Plongeur"};
            for (String s : listeAv) {
                avDispo.add(s);
            }
            for (int i = 0 ; i < nombreJ ; i++) {
                System.out.println("Veuillez sélectionner vos aventuriers parmi :");
                for (String s : avDispo) {
                    System.out.print("\t- ");
                    System.out.println(s);
                }
                do {
                    choixConforme = false;
                    System.out.println("Choix");
                    System.out.print("\t =>");
                    choix = sc.nextLine();
                    try {
                        choix = choix.toLowerCase().substring(0, 2);
                    } catch (StringIndexOutOfBoundsException | NullPointerException e) {
                        choix = "AUCUNE SELECTION";
                    }
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
            try {
                choixInt = new Integer(choix);
            } catch (NumberFormatException e) {
                choixInt = 0;
            }
            if (choixInt >= 2 && choixInt <= 4) {
                nbJoueurs = choixInt;
                choixConforme = true;
            }
        } while (!choixConforme);
    }
    
    private void choixNomJoueurs () {
        HashMap<Aventurier, String> players = getJoueurs();
        //sélection des noms de joueurs
        String choix;
        Scanner sc = new Scanner(System.in);
        for (Aventurier a : players.keySet()) {
            System.out.println("Nom du joueur du role "+a.getClass().toString().substring(12)+" : ");
            choix = sc.nextLine();
            players.put(a, choix);
        }
    }
    
    //METHODES UTILES
    private int getNbJoueur(){
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
        
        //Un tour de jeu
        debutJeu();
        pouvoirPiloteDispo = true;
        boolean actionEffectuée;
        for (Aventurier a : joueurs.keySet()) {
            int nbActions = 3;
            String nomRole = a.getClass().toString().substring(12);
            do {
                choixConforme = false;
                grille.afficheGrilleTexte(a);
                System.out.println(joueurs.get(a) + ", quelle action voulez-vous réaliser ?");
                System.out.println("\t1 - Se déplacer");
                System.out.println("\t2 - Assécher");
                System.out.println("\t3 - Finir mon tour");
                System.out.print("choix (1/2/3) : ");
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
                    nbActions = 0;
                }
            } while (!choixConforme || nbActions > 0);
            gererCarteOrange(a);
            gererCarteBleue();
        }
    }

    //MAIN
    public static void main(String[] args) {
        new Controleur();
    }
}
