package ileinterdite;

import java.awt.Color;
import model.CarteBleue;
import model.CarteOrange;
import model.Grille;
import model.Trésor;
import model.Aventurier;
import java.util.*;
import model.Explorateur;
import model.Tuile;
import util.*;
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

    @Override
    public void traiterMessage(Message m) {
    }
    public void gererDeplacement() {
    }
    public void gererAssechement() {
    }
    public void gererDonnation() {
    }
    public void gererGainTresor() {
    }
    public void piocheCarteOrange() {
    }
    public void gererCarteOrange() {
    }
    public void piocheCarteBleue() {

    }
    public void gererCarteBleue() {

    }
    public void bouger(int ligne, int colonne) {
    }
    public void calculTouteTuileDispo(Grille g) {
    }

    public Controleur() {
        ArrayList<Tuile> Tuiles = new ArrayList();
        for (int i = 1; i < 24; i++) {
            Tuiles.add(new Tuile(NomTuile.getFromNb(i)));
        }
        
        /*
        Tuiles.add(new Tuile(NomTuile.LE_PONT_DES_ABIMES));
        Tuiles.add(new Tuile(NomTuile.LA_PORTE_DE_BRONZE));
        Tuiles.add(new Tuile(NomTuile.LA_CAVERNE_DES_OMBRES));
        ,new Tuile(NomTuile.LA_PORTE_DE_FER),        new Tuile(NomTuile.LA_PORTE_D_OR),     new Tuile(NomTuile.LES_FALAISES_DE_L_OUBLI), null},
        {new Tuile(NomTuile.LE_PALAIS_DE_CORAIL),new Tuile(NomTuile.LA_PORTE_D_ARGENT),    new Tuile(NomTuile.LES_DUNES_DE_L_ILLUSION),new Tuile(NomTuile.HELIPORT),          new Tuile(NomTuile.LA_PORTE_DE_CUIVRE),      new Tuile(NomTuile.LE_JARDIN_DES_HURLEMENTS)},
         */
        
        //Initialisation de la Grille
        grille = new Grille(Tuiles);

        /*
                INTERFACE TEXTE.
        */
        //Declaration de variable utiles
        Scanner sc = new Scanner(System.in);
        String choix = "0";
        int nbJoueurs = 2;
        String[] nomJoueurs = null;
        
        //Choix de l'utilisateur du nombre de joueurs à jouer la partie (max 6)
        do {
        System.out.println("Combien de joueurs vont jouer ? Faites un choix (entier entre 2 et 6) : ");
        choix = sc.nextLine();
            switch (choix) {
                case "2": {nbJoueurs=2;break;}
                case "3": {nbJoueurs=3;break;}
                case "4": {nbJoueurs=4;break;}
                case "5": {nbJoueurs=5;break;}
                case "6": {nbJoueurs=6;break;}
                case "0": return; default:
                                    System.out.println("Choix non valide");
                                    break;
                }
        } while (choix != "0");
        
        //sélection des noms de joueurs.
        for (int i = 0 ; i < nbJoueurs ; i++){
            System.out.println("Nom joueur n°"+i+" : ");
            choix = sc.nextLine();
            nomJoueurs[i]=choix;
        }
        
        //Création des Aventuriers.
        joueurs.add(new Pilote(grille.getTuile(4,3), Pion.BLEU));
        joueurs.add(new Navigateur(grille.getTuile(4,2), Pion.JAUNE));
        joueurs.add(new Ingénieur(grille.getTuile(4,1), Pion.ROUGE));
        joueurs.add(new Explorateur(grille.getTuile(5,3), Pion.VERT));
        joueurs.add(new Messager(grille.getTuile(2,3), Pion.BLANC));
        joueurs.add(new Plongeur(grille.getTuile(3,2), Pion.NOIR));
        //Mélange de ceux-ci dans joueurs.
        joueurs = Utils.melangerAventuriers(joueurs);
        
        //Création d'une vue pour chaque aventurier...
        for (int i = 0 ; i < nbJoueurs ; i++){
            ihm[i] = new VueAventurier(nomJoueurs[i], joueurs.get(i+1).getNomAventurier(), joueurs.get(i+1).getCouleur().getCouleur());
        }
        
    }

    public static void main(String[] args) {
        new Controleur();
    }
}
