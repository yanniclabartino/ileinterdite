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

public class Controleur implements Observateur {

    private Grille grille;
    private int niveauEau;
    private VueAventurier[] ihm;
    private Trésor[] trésors;
    private HashMap<String, Aventurier> joueurs;
    private String joueurCourant;
    private Stack<CarteBleue> piocheBleues;
    private ArrayList<CarteBleue> defausseBleues;
    private Stack<CarteOrange> piocheOranges;
    private ArrayList<CarteOrange> defausseOranges;
    private boolean pouvoirPiloteDispo = true;

    @Override
    public void traiterMessage(Message m) {
    }

    //METHODES DE GESTION DU JEU
    private void gererDeplacement(Aventurier joueur) {
        //Gère les déplacements d'un aventurier
        Grille g = getGrille();
        ArrayList<Tuile> tuilesDispo = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        String choix;
        Integer X, Y;
        //Pouvoir pilote :
        if (joueur.getCouleur() == Utils.Pion.BLEU && pouvoirPiloteDispo) {
            System.out.println("Voulez-vous vous déplacer sur n'importe qu'elle tuile ? (utilisable une fois par tour) :");
            System.out.print("(oui/non) => ");
            choix = sc.nextLine();
            choix = choix.toUpperCase().substring(0, 1);
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
        g.afficheGrilleTexte(joueur);
        System.out.println("Voici la liste des tuiles disponible :");
        for (Tuile t : tuilesDispo) {
            t.affiche();
        }
        boolean choixValide = false;
        while (!choixValide) {
            System.out.println("\n\tChoix de la tuile :");
            System.out.print("X = ");
            choix = sc.nextLine();
            X = new Integer(choix);
            System.out.print("Y = ");
            choix = sc.nextLine();
            Y = new Integer(choix);
            Tuile tuileChoisie = g.getTuile(X - 1, Y - 1);
            if (tuilesDispo.contains(tuileChoisie)) {
                choixValide = true;
                joueur.seDeplace(tuileChoisie);
            } else {
                System.out.println("\tCHOIX NON-VALIDE.");
            }
        }
    }

    private void gererAssechement(Aventurier joueur) {
        Grille g = getGrille();
        ArrayList<Tuile> tuilesDispo = joueur.calculTuileAss(g);
        Utils.EtatTuile sec = Utils.EtatTuile.ASSECHEE;
        Scanner sc = new Scanner(System.in);
        String choix;
        Integer X, Y;
        int nbAssechement = 1;
        if ((joueur.getCouleur() == Utils.Pion.ROUGE) && (tuilesDispo.size() >= 2)) {
            System.out.println("Voulez-vous assecher 2 tuiles pour cette action ?");
            System.out.print("\t(oui/non)=> ");
            choix = sc.nextLine();
            choix = choix.toUpperCase().substring(0, 1);
            if (choix.equals("O")) {
                nbAssechement = 2;
            }
        }
        System.out.println("Voici la liste des tuiles disponible :");
        for (Tuile t : tuilesDispo) {
            t.affiche();
        }
        g.afficheGrilleTexte(joueur);
        for (int i = 0; i < nbAssechement; i++) {
            boolean choixValide = false;
            while (!choixValide) {
                System.out.println("\n\tChoix de la tuile :");
                System.out.print("X = ");
                choix = sc.nextLine();
                X = new Integer(choix);
                System.out.print("Y = ");
                choix = sc.nextLine();
                Y = new Integer(choix);
                Tuile tuileChoisie = g.getTuile(X - 1, Y - 1);
                if (tuilesDispo.contains(tuileChoisie)) {
                    choixValide = true;
                    tuileChoisie.setEtat(sec);
                } else {
                    System.out.println("\tCHOIX NON-VALIDE.");
                }
            }
        }
    }

    private void gererDonation() {/*Pour plus tard*/
    }

    private void gererGainTresor() {/*Pour plus tard*/
    }

    private void gererCarteOrange(Aventurier a) {
        boolean carteMDEpiochée = false;
        ArrayList<CarteOrange> cartesPiochées = new ArrayList<>();
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
                a.piocheCarte(c);
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
                            //
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
        int nbPioche;
        int nivEau = this.getNiveau();
        System.out.print("Le niveau d'eau s'élève à : " + nivEau + ".\nVous piochez donc : ");
        if (nivEau >= 8) {
            System.out.println("5 cartes.");
            nbPioche = 5;
        } else if (nivEau >= 6) {
            System.out.println("4 cartes.");
            nbPioche = 4;
        } else if (nivEau >= 3) {
            System.out.println("3 cartes.");
            nbPioche = 3;
        } else {
            System.out.println("2 cartes.");
            nbPioche = 2;
        }
        CarteBleue c;
        for (int i = 0; i < nbPioche; i++) {
            c = piocheCarteBleue();
            Tuile t = getGrille().getTuile(c.getInnonde().getNom());
            System.out.println("la carte innondation " + t.getNom().toString() + " à été piochée...");
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

            for (Aventurier aventuriers : joueurs.values()) {
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
            }
        }
    }

    //METHODES UTILES
    private Grille getGrille() {
        return grille;
    }

    private int getNiveau() {
        return niveauEau;
    }

    private ArrayList<Tuile> calculTouteTuileDispo(Grille g) {
        ArrayList<Tuile> touteTuileDispo = new ArrayList<>();
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
        joueurs = new HashMap<>();
        trésors = new Trésor[4];
        piocheOranges = new Stack<>();
        defausseOranges = new ArrayList<>();
        piocheBleues = new Stack<>();
        defausseBleues = new ArrayList<>();

        //Declaration de variable utiles pour l'interface texte
        Scanner sc = new Scanner(System.in);
        String choix;
        Integer choixInt;
        boolean choixConforme = false;
        int nbJoueurs = 2;
        String[] nomJoueurs;

        //Choix de l'utilisateur du nombre de joueurs à jouer la partie (max 6)
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

        //sélection des noms de joueurs
        nomJoueurs = new String[nbJoueurs];
        for (int i = 0; i < nbJoueurs; i++) {
            System.out.println("Nom joueur n°" + (i + 1) + " : ");
            choix = sc.nextLine();
            if (i == 0) {
                nomJoueurs[i] = choix;
            } else if (nomJoueurs[i - 1].equals(choix)) {
                nomJoueurs[i] = choix + i;
            } else {
                nomJoueurs[i] = choix;
            }
        }

        //sélection de la difficulté
        choixConforme = false;
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

        //Génération des 24 tuiles
        ArrayList<Tuile> Tuiles = new ArrayList();
        for (int i = 1; i < 25; i++) {
            Tuiles.add(new Tuile(NomTuile.getFromNb(i)));
        }

        //Initialisation de la Grille
        //Collections.shuffle(Tuiles);
        grille = new Grille(Tuiles);

        //Création des Aventuriers.
        ArrayList<Aventurier> aventuriers = new ArrayList<>();
        if (Parameters.ALEAS) {
            aventuriers.add(new Pilote());
            aventuriers.add(new Navigateur());
            aventuriers.add(new Ingénieur());
            aventuriers.add(new Explorateur());
            aventuriers.add(new Messager());
            aventuriers.add(new Plongeur());
            aventuriers = Utils.melangerAventuriers(aventuriers);
        } else {
            ArrayList<String> avDispo = new ArrayList<>();
            String[] listeAv = {"Pilote", "Navigateur", "Ingénieur", "Explorateur", "Messager", "Plongeur"};
            for (String s : listeAv) {
                avDispo.add(s);
            }
            for (int i = 0; i < nbJoueurs; i++) {
                System.out.println(nomJoueurs[i] + ", veuillez sélectionner votre aventurier parmi :");
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
        for (int i = 0; i < nbJoueurs; i++) {
            joueurs.put(nomJoueurs[i], aventuriers.get(i));
        }

        //Création des cartes oranges (trésor)        
        ArrayList<CarteOrange> tmpOranges = new ArrayList<>();
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
        for (CarteOrange c : tmpOranges) {
            piocheOranges.push(c);
        }

        //Création des cartes bleues (innondation)        
        ArrayList<CarteBleue> tmpBleues = new ArrayList<>();
        for (Tuile t : grille.getGrille()) {
            tmpBleues.add(new CarteBleue(t));
        }
        Collections.shuffle(tmpBleues);
        for (CarteBleue c : tmpBleues) {
            piocheBleues.push(c);
        }

        //Création des Trésors
        trésors[0] = new Trésor(NomTresor.LE_CRISTAL_ARDENT);
        trésors[1] = new Trésor(NomTresor.LA_PIERRE_SACREE);
        trésors[2] = new Trésor(NomTresor.LA_STATUE_DU_ZEPHYR);
        trésors[3] = new Trésor(NomTresor.LE_CALICE_DE_L_ONDE);

        //pioche des cartes innondations nécessaires au commencement du jeu
        //gererCarteBleue(getNiveau());
        //boucle du jeu
        //A FAIRE
        //Un tour de jeu.
        debutJeu();
        for (int i = 0; i < nbJoueurs; i++) {
            joueurCourant = nomJoueurs[i];
            int nbActions = 3;
            do {
                choixConforme = false;
                System.out.println(joueurCourant + ", quelle action voulez-vous réaliser ?");
                System.out.println("\t1 - Se déplacer");
                System.out.println("\t2 - Assécher");
                System.out.println("\t3 - Finir mon tour");
                System.out.print("choix (1/2/3) : ");
                choix = sc.nextLine();
                if (choix.equals("1")) {
                    choixConforme = true;
                    nbActions--;
                    gererDeplacement(joueurs.get(joueurCourant));
                } else if (choix.equals("2")) {
                    choixConforme = true;
                    nbActions--;
                    gererAssechement(joueurs.get(joueurCourant));
                } else if (choix.equals("3")) {
                    choixConforme = true;
                    nbActions = 0;
                }
            } while (!choixConforme || nbActions > 0);
        }
        //test graphique
        grille.afficheGrilleTexte(joueurs.get(1));

    }

    //MAIN
    public static void main(String[] args) {
        new Controleur();
    }
}
