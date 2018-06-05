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
        possede = new ArrayList<>();
        this.nom = nom;
        this.etat = EtatTuile.ASSECHEE;
    }
        
    public int getColonne() {
        return colonne;
    }

    public EtatTuile getEtat() {
        return etat;
    }

    public NomTuile getNom() {
        return nom;
    }

    public ArrayList<Aventurier> getPossede() {
        return possede;
    }
    
    public int getLigne() {
        return ligne;
    }

    public void suppAventurier(Aventurier a) {
        possede.remove(a);
    }

    public void addAventurier(Aventurier a) {
        possede.add(a);
        a.setTuile(this);
    }

    public void setEtat(EtatTuile nouvelEtat) {
            this.etat = nouvelEtat;
    }

    //interface texte
    public void affiche() {
        System.out.println(getNom().toString()+"["+this.getEtat().toString()+"]"+" : ("+getLigne()+","+getColonne()+")");
    }
}