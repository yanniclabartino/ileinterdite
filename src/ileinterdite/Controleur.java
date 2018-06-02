package ileinterdite;

import model.CarteBleue;
import model.CarteOrange;
import model.Grille;
import model.Trésor;
import model.Aventurier;
import java.util.*;
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

public class Controleur implements Observateur {

    private Grille grille;
    private int niveauEau;
    private VueAventurier[] ihm;
    private Trésor[] trésors;
    private ArrayList<Aventurier> joueurs;
    private ArrayList<CarteBleue> piocheBleues;
    private ArrayList<CarteBleue> defausseBleues;
    private ArrayList<CarteOrange> piocheOranges;
    private ArrayList<CarteOrange> defausseOranges;
    private boolean pouvoirPiloteDispo = true;

    @Override
    public void traiterMessage(Message m) {
    }

    public void gererDeplacement(Aventurier joueur) {
        //Gère les déplacements d'un aventurier
        Scanner sc = new Scanner(System.in);
        String choix;
        if (joueur.getCouleur() == Utils.Pion.JAUNE && pouvoirPiloteDispo) {
            System.out.println("Voulez-vous vous déplacer sur n'importe qu'elle tuile ? (utilisable une fois par tour) :");
            System.out.print("(oui/non) => ");
            choix = sc.nextLine();
            if (choix == "oui" || choix == "Oui" || choix == "O" || choix == "o") {
                pouvoirPiloteDispo = false;
            }
        }

    }

    public void gererAssechement(Aventurier joueur) {
        /*
        if (joueur.getCouleur() == Utils.Pion.ROUGE &&) {
            for (int i = 1; i < 3; i++) {
                joueur.calculTuileAss(grille);
            }
        } else {
            joueur.calculTuileAss(grille);
        }
        */
    }

    public void afficheGrilleTexte(Grille g, Aventurier joueur) {
        /*
                        +-------+-------+
                        |       |    (4)|
                +-------+-------+-------+-------+
                |       |   #   |       |       |
        +-------+-------+-------+-------+-------+-------+
        |       |       |       |(H)~   |       |       |
        +-------+-------+-------+-------+-------+-------+
        |       |    (1)|       |       |    (X)|       |
        +-------+-------+-------+-------+-------+-------+
                |       |   ~   |       |       |
                +-------+-------+-------+-------+
                        |       |       |             
                        +-------+-------+
        //# = tuile coulée
        //~ = tuile ionnondé
        //(X) = position du joueur actif
        //(H) = héliport
        //(nbJoueurSurCase) = nombre de joueur sur la case
        //(X) prime sur (nbJoueurSurCase) si le joueur actif est sur une case avec d'autre joueurs.
         */

        Tuile tuileActive;
        for (int y = 0; y < 6; y++) {
            switch (y) {
                case 0:
                    System.out.println("                +-------+-------+");
                    System.out.print("                |");
                    break;
                case 1:
                    System.out.println("        +-------+-------+-------+-------+");
                    System.out.print("        |");
                    break;
                case 2: case 3:
                    System.out.println("+-------+-------+-------+-------+-------+-------+");
                    System.out.print("|");
                    break;
                case 4:
                    System.out.println("+-------+-------+-------+-------+-------+-------+");
                    System.out.print("        |");
                    break;
                case 5:
                    System.out.println("        +-------+-------+-------+-------+");
                    System.out.print("                |");
                    break;
                default:
                    break;
            }
            for (int x = 0; x < 6; x++) {
                tuileActive=getGrille().getTuile(x, y);
                if (tuileActive!=null){
                    if (tuileActive.getNom()==NomTuile.HELIPORT) {
                        System.out.print("(H)");
                    } else {
                        System.out.print("   ");
                    }
                    if (tuileActive.getEtat()==Utils.EtatTuile.INONDEE) {
                        System.out.print("~");
                    } else if (tuileActive.getEtat()==Utils.EtatTuile.COULEE){
                        System.out.print("#");
                    } else {
                        System.out.print(" ");
                    }
                    if (tuileActive.getPossede().size() > 0){
                        if (tuileActive.getPossede().contains(joueur)) {
                            System.out.print("(X)");
                        } else {
                            System.out.print("("+tuileActive.getPossede().size()+")");
                        }
                    } else {
                        System.out.print("   ");
                    }
                    System.out.print("|");
                } else {
                    System.out.print("        ");
                }
                if (x==5){
                    System.out.println();
                }
            }
        }
        System.out.println("                +-------+-------+");
    }

    public void gererDonnation() {}
    public void gererGainTresor() {}
    public void piocheCarteOrange() {}
    public void gererCarteOrange() {}
    public void piocheCarteBleue() {}
    public void gererCarteBleue() {}

    public Grille getGrille() {
        return grille;
    }
    
    public void bouger(int ligne, int colonne) {
    }

    public void calculTouteTuileDispo(Grille g) {
    }

    public Controleur() {
        ArrayList<Tuile> Tuiles = new ArrayList();
        for (int i = 1; i < 25; i++) {
            Tuiles.add(new Tuile(NomTuile.getFromNb(i)));
        }

        //Initialisation de la Grille
        Collections.shuffle(Tuiles);
        grille = new Grille(Tuiles);

        //Création des Trésors
        trésors = new Trésor[4];
        trésors[0] = new Trésor(NomTresor.LE_CRISTAL_ARDENT);
        trésors[1] = new Trésor(NomTresor.LA_PIERRE_SACREE);
        trésors[2] = new Trésor(NomTresor.LA_STATUE_DU_ZEPHYR);
        trésors[3] = new Trésor(NomTresor.LE_CALICE_DE_L_ONDE);

        /*
                INTERFACE TEXTE.
         */
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
            choix = sc.nextLine();
            choixInt = new Integer(choix);
            if (choixInt >= 2 || choixInt <= 6) {
                nbJoueurs = choixInt;
                choixConforme = true;
            }
        } while (!choixConforme);

        //sélection des noms de joueurs.
        nomJoueurs = new String[nbJoueurs];
        for (int i = 0; i < nbJoueurs; i++) {
            System.out.println("Nom joueur n°" + (i+1) + " : ");
            choix = sc.nextLine();
            nomJoueurs[i] = choix;
        }

        //Création des Aventuriers.       
        joueurs = new ArrayList<>();
        joueurs.add(new Pilote(grille.getTuile(4, 3), Pion.BLEU));
        joueurs.add(new Navigateur(grille.getTuile(4, 2), Pion.JAUNE));
        joueurs.add(new Ingénieur(grille.getTuile(4, 1), Pion.ROUGE));
        joueurs.add(new Explorateur(grille.getTuile(5, 3), Pion.VERT));
        joueurs.add(new Messager(grille.getTuile(2, 3), Pion.BLANC));
        joueurs.add(new Plongeur(grille.getTuile(3, 2), Pion.NOIR));

        //Mélange de ceux-ci dans joueurs.
        joueurs = Utils.melangerAventuriers(joueurs);

        //Création d'une vue pour chaque aventurier...
        ihm = new VueAventurier[nbJoueurs];
        for (int i = 1; i < nbJoueurs; i++) {
            ihm[i] = new VueAventurier(nomJoueurs[i], joueurs.get(i - 1).getNomAventurier(), joueurs.get(i - 1).getCouleur().getCouleur());
        }

        //boucle du jeu
        boolean fin = false;
        /*
        while (!fin) {
            //On regarde si tout les trésors sont gagnés...
            fin = true;
            int i = 1;
            while(i < nbJoueurs && fin!=false){
                if (!trésors[i].isGagne()){
                    fin = false;
                }
                i++;
            }
        }*/
        
        //test graphiques
        afficheGrilleTexte(grille, joueurs.get(1));
        
    }

    public static void main(String[] args) {
        new Controleur();
    }
}
