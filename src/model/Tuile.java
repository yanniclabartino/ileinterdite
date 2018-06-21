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
    private int selected; //0 => tuile non-sélectionné, 
                          //1 => sélectionnée pour le déplacement, 
                          //2 => selectionnée pour le déplacmeent du pilote.

    public Tuile(NomTuile nom) {
        possede = new ArrayList<Aventurier>();
        this.nom = nom;
        this.etat = EtatTuile.ASSECHEE;
        this.selected = 0;
    }
        
    public int getLigne() {
        return ligne;
    }
    
    public int getColonne() {
        return colonne;
    }

    public void setLigEtCol(int x, int y) {
        this.ligne = y;
        this.colonne = x;
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

    public int getSelected() {
        return selected;
    }
    
    public void setSelected(int selected) {
        this.selected = selected;
    }

    //interface texte
    public void affiche() {
        System.out.println(getNom().toString()+" [\033[33m"+this.getEtat().toString()+"\033[0m]"+" : (\033[32m"+(getColonne()+1)+"\033[0m,\033[32m"+(getLigne()+1)+"\033[0m)");
    }
}