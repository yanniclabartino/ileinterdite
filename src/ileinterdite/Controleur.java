package ileinterdite;

import model.CarteBleue;
import model.CarteOrange;
import model.Grille;
import model.Trésor;
import model.Aventurier;
import model.Explorateur;
import model.Ingénieur;
import model.Messager;
import model.Navigateur;
import model.Pilote;
import model.Plongeur;
import model.Tuile;
import util.*;
import util.Utils.Pion;
import view.VueAventurier;
import java.util.*;

public class Controleur implements Observateur {

    private Grille grille;
    private int niveauEau;
    private VueAventurier[] ihm;
    private Trésor[] trésors;
    private ArrayList<Aventurier> joueurs;
    private Stack<CarteBleue> piocheBleues;
    private ArrayList<CarteBleue> defausseBleues;
    private Stack<CarteOrange> piocheOranges;
    private ArrayList<CarteOrange> defausseOranges;
    private boolean pouvoirPiloteDispo = true;

    @Override
    public void traiterMessage(Message m) {
    }

    public void gererDeplacement(Aventurier joueur) {
        //Gère les déplacements d'un aventurier
        Grille g = getGrille();
        ArrayList<Tuile> tuilesDispo = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        String choix;
        Integer X, Y;
        //Pouvoir pilote :
        if (joueur.getCouleur() == Utils.Pion.JAUNE && pouvoirPiloteDispo) {
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
        } else {
            tuilesDispo = joueur.calculTuileDispo(g);
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
                Tuile tuileChoisie = g.getTuile(X, Y);
                if (tuilesDispo.contains(tuileChoisie)) {
                    choixValide = true;
                    joueur.seDeplace(tuileChoisie);
                    //Voir VP pour savoir s'il est plus judicieux
                    //de passer par une unique méthode (qu'on
                    //détail par la suite niveau MVC)
                } else {
                    System.out.println("\tCHOIX NON-VALIDE.");
                }
            }
        }

    }

    public void gererAssechement(Aventurier joueur) {
        Grille g = getGrille();
        ArrayList<Tuile> tuilesDispo = joueur.calculTuileAss(g);
        Utils.EtatTuile sec = Utils.EtatTuile.ASSECHEE;
        Scanner sc = new Scanner(System.in);
        String choix;
        Integer X, Y;
        int nbAssechement = 1;
        if (joueur.getCouleur() == Utils.Pion.ROUGE && tuilesDispo.size() >= 2) {
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
                Tuile tuileChoisie = g.getTuile(X, Y);
                if (tuilesDispo.contains(tuileChoisie)) {
                    choixValide = true;
                    tuileChoisie.setEtat(sec);
                } else {
                    System.out.println("CHOIX NON-VALIDE.");
                }
            }
        }
    }

    public void gererDonation() {/*Pour plus tard*/}

    public void gererGainTresor() {/*Pour plus tard*/}

    public void gererCarteOrange(Aventurier a) {
        
    }

    public void gererCarteBleue(int n) {
        
    }

    public Grille getGrille() {
        return grille;
    }
    
    public int getNiveau() {
        return niveauEau;
    }

    public ArrayList calculTouteTuileDispo(Grille g) {
        ArrayList<Tuile> touteTuileDispo = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (g.getTuile(i, j).getEtat() != Utils.EtatTuile.COULEE) {
                    touteTuileDispo.add(g.getTuile(i, j));
                }
            }
        }

        return touteTuileDispo;
    }

    public Controleur() {
        //Génération des 24 tuiles
        ArrayList<Tuile> Tuiles = new ArrayList();
        for (int i = 1; i < 25; i++) {
            Tuiles.add(new Tuile(NomTuile.getFromNb(i)));
        }

        //Initialisation de la Grille
        //Collections.shuffle(Tuiles);
        this.grille = new Grille(Tuiles);

        //Création des Trésors
        trésors = new Trésor[4];
        trésors[0] = new Trésor(NomTresor.LE_CRISTAL_ARDENT);
        trésors[1] = new Trésor(NomTresor.LA_PIERRE_SACREE);
        trésors[2] = new Trésor(NomTresor.LA_STATUE_DU_ZEPHYR);
        trésors[3] = new Trésor(NomTresor.LE_CALICE_DE_L_ONDE);

        //Declaration de variable utiles
        Scanner sc = new Scanner(System.in);
        String choix;
        Integer choixInt;
        boolean choixConforme = false;
        int nbJoueurs = 2;
        String[] nomJoueurs;
        //Choix de l'utilisateur du nombre de joueurs à jouer la partie (max 6)
        do {
            System.out.println("Combien de joueurs vont jouer ? Faites un choix (entier entre 2 et 6) : ");
            System.out.print("\t=> ");
            choix = sc.nextLine();
            choixInt = new Integer(choix);
            if (choixInt >= 2 || choixInt <= 6) {
                nbJoueurs = choixInt;
                choixConforme = true;
            }
        } while (!choixConforme);

        //sélection des noms de joueurs
        nomJoueurs = new String[nbJoueurs];
        for (int i = 0; i < nbJoueurs; i++) {
            System.out.println("Nom joueur n°" + (i + 1) + " : ");
            choix = sc.nextLine();
            nomJoueurs[i] = choix;
        }

        //sélection de la difficulté
        choixConforme = false;
        do {
            System.out.println("Quel niveau de difficulté ? (1/2/3/4)");
            System.out.println("\t =>");
            choix = sc.nextLine();
            choixInt = new Integer(choix);
            if (choixInt >= 1 || choixInt <= 4) {
                this.niveauEau=choixInt;
                choixConforme = true;
            }
        } while (!choixConforme);
        
        //Création des Aventuriers.       
        joueurs = new ArrayList<>();
        joueurs.add(new Pilote(grille.getTuile(3, 2), Pion.BLEU));
        joueurs.add(new Navigateur(grille.getTuile(3, 1), Pion.JAUNE));
        joueurs.add(new Ingénieur(grille.getTuile(3, 0), Pion.ROUGE));
        joueurs.add(new Explorateur(grille.getTuile(4, 2), Pion.VERT));
        joueurs.add(new Messager(grille.getTuile(1, 2), Pion.BLANC));
        joueurs.add(new Plongeur(grille.getTuile(2, 1), Pion.NOIR));

        //Mélange de ceux-ci dans joueurs.
        //joueurs = Utils.melangerAventuriers(joueurs);

        //Création d'une vue pour chaque aventurier...
        /*
        ihm = new VueAventurier[nbJoueurs];
        for (int i = 1; i < nbJoueurs; i++) {
            ihm[i] = new VueAventurier(nomJoueurs[i], joueurs.get(i - 1).getNomAventurier(), joueurs.get(i - 1).getCouleur().getCouleur());
        }
        */
        
        //Création des cartes oranges (trésors)
        // A FAIRE
        //Création des cartes bleues (innondations)
        // A FAIRE
        
        //pioche des cartes innondations nécessaires au commencement du jeu
        gererCarteBleue(niveauEau);
        
        //boucle du jeu
        
        
        
        //test graphique
        grille.afficheGrilleTexte(joueurs.get(1));

    }

    public static void main(String[] args) {
        new Controleur();
    }
}
