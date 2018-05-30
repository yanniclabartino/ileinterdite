package model;

import java.util.*;
import util.Utils.Pion;

public abstract class Aventurier {

    private String nomAventurier;
    private Tuile appartient;
    private ArrayList<CarteOrange> possède;
    private Pion couleur;

    Aventurier(Tuile t, Pion p) {
        setTuile(t);
        this.couleur = p;
    }

    public Tuile getTuile() {
        return this.appartient;
    }

    public String getNomAventurier() {
        return nomAventurier;
    }

    public Pion getCouleur() {
        return couleur;
    }

    public void setTuile(Tuile tArrivee) {
        appartient = tArrivee;
    }

    public void piocheCarte(CarteOrange c) {
        this.possède.add(c);
        c.setOwner(this);
    }

    public void defausseCarte(CarteOrange c) {
        this.possède.remove(c);
        c.delOwner();
    }

    public abstract ArrayList<Tuile> calculTuileDispo(Tuile tDepart, Grille g);

    public abstract ArrayList<Tuile> calculTuileAss(Tuile tPosition, Grille g);

}
