package ileinterdite;

import model.CarteBleue;
import model.CarteOrange;
import model.Grille;
import model.Trésor;
import model.Aventurier;
import java.util.*;
import model.Tuile;
import util.NomTuile;
import view.VueAventurier;

public class Controleur implements Observateur {

    private VueAventurier ihm;
    private Grille grille;
    private Aventurier joueur;
    private Trésor trésor;
    private int niveauEau;
    private ArrayList<CarteBleue> piocheBleues;
    private ArrayList<CarteBleue> defausseBleues;
    private ArrayList<CarteOrange> piocheOranges;
    private ArrayList<CarteOrange> defausseOranges;

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

    public static void main(String[] args) {
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
    }
}
