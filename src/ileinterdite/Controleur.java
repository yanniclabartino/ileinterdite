package ileinterdite;

import model.CarteBleue;
import model.CarteOrange;
import model.Grille;
import model.Trésor;
import model.Aventurier;
import java.util.*;
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
    }
}
