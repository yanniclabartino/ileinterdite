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
            System.out.println("Voulez-vous vous déplacer sur n'importe qu'elle tuile ? (\033[31mutilisable une fois par tour\033[0m) :");
            System.out.print("(oui/non) => ");
            choix = sc.nextLine();
            try {
                choix = choix.toUpperCase().substring(0, 1);
            } catch (StringIndexOutOfBoundsException e) {
                choix = "N";
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
                System.out.println("Rentrez : \033[33mX = null\033[0m\npour annuler tout déplacement.");
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
                        System.out.println("\t\033[31mCHOIX NON-VALIDE.\033[0m");
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
            System.out.print("\t(\033[32moui\033[0m/\033[31mnon\033[0m)=> ");
            choix = sc.nextLine();
            try {
                choix = choix.toUpperCase().substring(0, 1);
            } catch (StringIndexOutOfBoundsException e) {
                choix = "N";
            }
            if (choix.equals("O")) {
                nbAssechement = 2;
            }
        }
        //vérification tuileDispo
        if (tuilesDispo.size() > 0) {
            g.afficheGrilleTexte(joueur);
            //affichage des tuiles disponibles 
            System.out.println("Voici la liste des tuiles disponible :");
            for (Tuile t : tuilesDispo) {
                t.affiche();
            }
            //choix de l'utilisateur
            int i = 0;
            do {
                boolean choixValide = false;
                do {
                    System.out.println("\n\tChoix de la tuile :");
                    System.out.println("Rentrez : \033[33mX = null\033[0m\npour annuler tout assèchement.");
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
                            System.out.println("\t\033[31mCHOIX NON-VALIDE.\033[0m");
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
        
        jDispo.remove(joueur);
        if (jDispo.size() > 0) {
            do {
                System.out.println("Voici la liste des aventuriers à qui vous pouvez donner une carte :");
                for (Aventurier a : jDispo) {
                    System.out.println("\t- "+a.getClass().toString().substring(12)+" ("+getJoueurs().get(a)+")");
                }
                System.out.println("\t- Annuler\n");
                System.out.println("choix (\033[31mrole\033[0m ou annuler) => ");
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
                    try {
                        choixInt = new Integer(choix);
                    } catch (NumberFormatException e){
                        choixInt = 0;
                    }
                    if (choixInt <= cartesDispo.size() && choixInt >= 1) {
                        choixConforme = true;
                        cCédée = cartesDispo.get(choixInt-1);
                        //on passe la carte d'un joueur à l'autre...
                        cCédée.affiche();
                        joueur.defausseCarte(cCédée);
                        System.out.println("\033[33mpassage de la carte ...\033[0m");
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
                        System.out.println("\033[21;33mVous gagnez le trésor "+getTrésors()[0].toString()+"\033[0m");
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
                        System.out.println("\033[21;33mVous gagnez le trésor "+getTrésors()[1].toString()+"\033[0m");
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
                        System.out.println("\033[21;33mVous gagnez le trésor "+getTrésors()[2].toString()+"\033[0m");
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
                        System.out.println("\033[21;33mVous gagnez le trésor "+getTrésors()[3].toString()+"\033[0m");
                        return gainEffectué;
                    }
                }
            } else {
                return !gainEffectué;
            }
            return !gainEffectué;
        }
    }

    private boolean gererCarteSpecial(Aventurier joueur, CarteOrange carte){
        boolean carteUtilisée = true;
        boolean carteHeliDispo = true;
        boolean carteSacDispo = true;
        //Variable saisie utilisateur
        Scanner sc = new Scanner(System.in);
        String choix;
        Integer X, Y;
        boolean choixValide = false;
        //
        if (carte == null) {
            for (CarteOrange c : joueur.getMain()) {
                if (c.getRole().equals("Helicoptere") && carteHeliDispo) {
                    Tuile heliport = getGrille().getTuile(NomTuile.HELIPORT);
                    int nbAventurier = getNbJoueur();
                    if (heliport.getPossede().size()==nbAventurier) {
                        System.out.println("Vous possèdez une carte \033[33mHelicoptère\033[0m et vous pouvez l'utiliser.");
                        System.out.print("Confirmer (\033[32moui\033[0m/\033[31mnon\033[0m) ? ");
                        choix = sc.nextLine();
                        choix = choix.toUpperCase().substring(0, 1);
                        if (choix.equals("O")) {
                            this.jeuEnCours = false;
                            return carteUtilisée;
                        } else {
                            carteHeliDispo = false;
                        }
                    } else {
                        carteHeliDispo=false;
                    }
                } else if (c.getRole().equals("Sac de sable") && carteSacDispo) {
                    ArrayList<Tuile> tuilesInnondées = new ArrayList<Tuile>();
                    for (Tuile t : getGrille().getGrille()) {
                        if (t.getEtat()==Utils.EtatTuile.INONDEE) {
                            tuilesInnondées.add(t);
                        }
                    }
                    if (!tuilesInnondées.isEmpty() && carteSacDispo) {
                        System.out.println("Vous possèdez une carte \033[33mSac de Sable\033[0m et vous pouvez l'utiliser.");
                        System.out.print("Confirmer (\033[32moui\033[0m/\033[31mnon\033[0m) ? ");
                        choix = sc.nextLine();
                        choix = choix.toUpperCase().substring(0, 1);
                        if (choix.equals("O")) {
                            System.out.println("Voici la liste des tuiles dispo à assecher :");
                            for (Tuile t : tuilesInnondées) {
                                t.affiche();
                            }
                            do { 
                                System.out.print("X = ");
                                choix = sc.nextLine();
                                try {
                                    X = new Integer(choix);
                                } catch (NumberFormatException e){
                                    X = 0;
                                }
                                System.out.print("Y = ");
                                choix = sc.nextLine();
                                try {
                                    Y = new Integer(choix);
                                } catch (NumberFormatException e) {
                                    Y = 0;
                                }
                                Tuile tuileChoisie = getGrille().getTuile(X - 1, Y - 1);
                                if (tuilesInnondées.contains(tuileChoisie)) {
                                    tuileChoisie.setEtat(Utils.EtatTuile.ASSECHEE);
                                    choixValide = true;
                                }
                            } while (!choixValide);
                            if (choixValide) {
                                return carteUtilisée;
                            }
                        } else {
                            carteSacDispo = false;
                        }
                    } else {
                        carteSacDispo = false;
                    }
                }
            }
        } else {
            if (carte.getRole().equals("Helicoptere")) {
                Tuile heliport = getGrille().getTuile(NomTuile.HELIPORT);
                int nbAventurier = getNbJoueur();
                if (heliport.getPossede().size()==nbAventurier) {
                    System.out.print("Confirmer (oui/non) ? ");
                    choix = sc.nextLine();
                    choix = choix.toUpperCase().substring(0, 1);
                    if (choix.equals("O")) {
                        this.jeuEnCours = false;
                        return carteUtilisée;
                    }
                }
            } else if (carte.getRole().equals("Sac de sable")) {
                ArrayList<Tuile> tuilesInnondées = new ArrayList<Tuile>();
                for (Tuile t : getGrille().getGrille()) {
                    if (t.getEtat()==Utils.EtatTuile.INONDEE) {
                        tuilesInnondées.add(t);
                    }
                }
                if (!tuilesInnondées.isEmpty()) {
                    System.out.print("Confirmer (oui/non) ? ");
                    choix = sc.nextLine();
                    choix = choix.toUpperCase().substring(0, 1);
                    if (choix.equals("O")) {
                        System.out.println("Voici la liste des tuiles dispo à assecher :");
                        for (Tuile t : tuilesInnondées) {
                            t.affiche();
                        }
                        do {
                            System.out.print("X = ");
                            choix = sc.nextLine();
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
                            Tuile tuileChoisie = getGrille().getTuile(X - 1, Y - 1);
                            if (tuilesInnondées.contains(tuileChoisie)) {
                                tuileChoisie.setEtat(Utils.EtatTuile.ASSECHEE);
                                choixValide = true;
                            }
                        } while (!choixValide);
                        if (choixValide) {
                            return carteUtilisée;
                        }
                    }
                }
            }
        }
        return !carteUtilisée;
    }
    
    private void gererCarteOrange(Aventurier joueur) {
        /*Méthode qui permet a un joueur de piocher deux cartes a la fin de son tour*/
        boolean carteMDEpiochée = false;
        ArrayList<CarteOrange> cartesPiochées = new ArrayList<CarteOrange>();
        //On picohe deux cartes et on vérifie a chaque fois si la pioche est vide
        System.out.println("\n"+joueur.getClass().toString().substring(12)+", \033[32mvous piochez deux carte oranges.\033[0m");
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
                System.out.println("Vous avez pioché une carte Montée des eaux, \033[31mle niveau d'eau monte...\033[0m");
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
            System.out.println("\033[33mMélange de la défausse de carte innondations sur la pioche\033[0m");
            for (CarteBleue b : getDefausseBleues()) {
                addPiocheBleue(b);
            }
            viderDefausseBleues();
        }
        //Si la main contient plus de 5 cartes
        if (joueur.getMain().size() > 5) {
            Scanner sc = new Scanner(System.in);
            String choix;
            Integer numChoix;
            boolean choixConforme = false;
            System.out.println("\033[31mVous avez plus de 5 cartes dans votre main...\033[0m");
            int nbCarteDeTrop = joueur.getMain().size() - 5;
            for (int i = 0; i < nbCarteDeTrop; i++) {
                System.out.println("Voici votre main (" + joueur.getMain().size() + " cartes) : ");
                int numCarte = 1;
                for (CarteOrange c : joueur.getMain()) {
                    System.out.print("\t "+numCarte+" - ");
                    c.affiche();
                    numCarte++;
                }
                System.out.println("\033[33mQuelle carte voulez-vous défaussez de votre main ?\033[0m\nNB: s'il s'agit d'une carte spéciale, vous pourrez l'utiliser.");
                do {
                    System.out.print("\t choix (numéro de la carte ) : ");
                    choix = sc.nextLine();
                    try {
                        numChoix = new Integer(choix);
                        choixConforme = true;
                    } catch (NumberFormatException e) {
                        System.out.println("\033[31mSAISIE INVALIDE\033[0m");
                        numChoix = 0;
                    }
                } while (!choixConforme && numChoix <= 0 && numChoix > joueur.getMain().size());
                if (joueur.getMain().get(numChoix - 1).getRole().equals("Helicoptere") || joueur.getMain().get(numChoix - 1).getRole().equals("Sac de sable")) {
                    choixConforme = false;
                    do {
                        System.out.println("Il s'agit d'une carte à pouvoir spécial,\nvoulez-vous vous en débarrasser(\033[31m1\033[0m) ou jouer cette carte(\033[31m2\033[0m) ?");
                        System.out.print("\tchoix (1/2) : ");
                        choix = sc.nextLine();
                        if (choix.equals("1")) {
                            joueur.defausseCarte(joueur.getMain().get(numChoix - 1));
                            choixConforme = true;
                        } else if (choix.equals("2")) {
                            choixConforme = gererCarteSpecial(joueur, joueur.getMain().get(numChoix - 1));
                        }
                    } while (!choixConforme);
                } else {
                    joueur.defausseCarte(joueur.getMain().get(numChoix - 1));
                }
            }
        }
        joueur.afficheInfo();
    }

    private void gererCarteBleue() {
        /*Méthode qui permet au joueur de piocher des cartes innondation a la fin de son tour*/
        //On pioche un certain nombre de cartes en fonction du niveau d'eau
        int nbPioche;
        int nivEau = this.getNiveau();
        System.out.print("Le niveau d'eau s'élève à : \033[36;44m" + nivEau + "\033[m.\nVous piochez donc : ");
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
                System.out.print("La tuile correspondante est désormais \033[36minnondée\033[0m : ");
                t.affiche();
                addDefausseBleues(c);
            } else if (t.getEtat() == Utils.EtatTuile.INONDEE) {
                t.setEtat(Utils.EtatTuile.COULEE);
                System.out.print("La tuile correspondante est désormais \033[31mcoulée\033[0m : ");
                t.affiche();
                if (t.getPossede().size() > 0 && !this.estPerdu()) {
                    System.out.println("\033[31mLa tuile contenait un ou plusieurs aventuriers !\033[0m Il faut qu'il(s) nage(nt) :");
                    boolean deplAventurier = false;
                    boolean joueurNoyé = false;
                    int indice = 0;
                    ArrayList<Aventurier> jEnDetresse = new ArrayList<Aventurier>();
                    jEnDetresse.addAll(t.getPossede());
                    while (indice < jEnDetresse.size() && !joueurNoyé) {
                        deplAventurier = gererDeplacement(jEnDetresse.get(indice));
                        if (!deplAventurier) {
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
            System.out.println("Quel niveau de difficulté ? (\033[36m1\033[0m/\033[32m2\033[0m/\033[33m3\033[0m/\033[31m4\033[0m)");
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
                    try {
                        choix = choix.toLowerCase().substring(0, 2);
                    } catch (StringIndexOutOfBoundsException e) {
                        choix = "//";
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
            System.out.println("Combien de joueurs vont jouer ? Faites un choix (\033[32mentier entre 2 et 4\033[0m) : ");
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
        //sélection des noms de joueurs
        String choix;
        Scanner sc = new Scanner(System.in);
        for (Aventurier a : getJoueurs().keySet()) {
            System.out.println("Nom du joueur du role \033[36m"+a.getClass().toString().substring(12)+"\033[0m : ");
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
        
        //Tours de jeu
        debutJeu();
        pouvoirPiloteDispo = true;
        jeuEnCours = true;
        boolean actionEffectuée;
        while (!this.estTerminé()) {
            for (Aventurier a : joueurs.keySet()) {
                if (!this.estTerminé()) {
                    /*Lignes de test :*/
                    //grille.getTuile(NomTuile.HELIPORT).setEtat(Utils.EtatTuile.COULEE);
                    /*FIN des lignes de tests*/
                    int nbActions;
                    if (a.getCouleur()==Pion.JAUNE) {
                        nbActions = 4;
                        System.out.println("\n\033[32mEn tant que navigateur vous avez 4 actions.\033[0m\n");
                    } else {
                        nbActions = 3;
                    }
                    String nomRole = a.getClass().toString().substring(12);
                    while (nbActions > 0) {
                        grille.afficheGrilleTexte(a);
                        System.out.println(joueurs.get(a) + "(" + nomRole + ") , quelle action voulez-vous réaliser ?");
                        System.out.println("\t\033[30;42m[Action réstante = \033[21;31;42m"+nbActions+"\033[30;42m]\033[0m");
                        System.out.println("\t\033[33m1 - Se déplacer       \033[0m");
                        System.out.println("\t\033[33m2 - Assécher          \033[0m");
                        System.out.println("\t\033[33m3 - Donner une carte  \033[0m");
                        System.out.println("\t\033[33m4 - Gagner un trésor  \033[0m");
                        System.out.println("\t\033[33m5 - Finir mon tour    \033[0m");
                        System.out.println("Autre (pas de cout) :");
                        System.out.println("\t\033[35m0 - Informations sur la grille, main des joueurs et leur position\033[0m");
                        System.out.println("\t\033[35m6 - Utiliser une carte spéciale de votre main\033[0m");
                        System.out.print("choix (0 à 6) : ");
                        choix = sc.nextLine();
                        if (choix.equals("1")) {
                            actionEffectuée = gererDeplacement(a);
                            if (actionEffectuée) {
                                nbActions--;
                            }
                        } else if (choix.equals("2")) {
                            actionEffectuée = gererAssechement(a);
                            if (actionEffectuée) {
                                nbActions--;
                            }
                        } else if (choix.equals("3")) {
                            actionEffectuée = gererDonation(a);
                            if (actionEffectuée) {
                                nbActions--;
                                a.afficheInfo();
                            }
                        } else if (choix.equals("4")) {
                            actionEffectuée = gererGainTresor(a);
                            if (actionEffectuée) {
                                nbActions--;
                            }
                        } else if (choix.equals("5")) {
                            nbActions = 0;
                        } else if (choix.equals("0")) {
                            for (Tuile t : grille.getGrille()) {
                                t.affiche();
                            }
                            for (Aventurier joueur : joueurs.keySet()) {
                                joueur.afficheInfo();
                            }
                        } else if (choix.equals("6")) {
                            gererCarteSpecial(a, null);
                        }
                    }
                    gererCarteOrange(a);
                    gererCarteBleue();
                }
            }
        }
        if (estPerdu()) {
            System.out.println("Partie perdue");
        } else {
            System.out.println("Partie gagnée");
        }
    }

    //MAIN
    public static void main(String[] args) {
        new Controleur();
    }
}
