package model;
import util.NomTuile;
import java.util.*;
import util.Utils.EtatTuile;

public class Tuile {

    ArrayList<Aventurier> possede;
    private EtatTuile etat;
    private int ligne;
    private int colonne;
    private NomTuile nom;

    public Tuile(NomTuile nom) {
        this.nom = nom;
    }
        


    public void suppAventurier(Aventurier a) {
        possede.remove(a);
    }

    public void addAventurier(Aventurier a) {
        possede.add(a);
    }

    public void setEtat(EtatTuile nouvelEtat) {
            this.etat = nouvelEtat;
    }

}