package model;

import java.util.*;
import util.Utils.Pion;

public abstract class Aventurier {

    private Tuile appartient;
    private ArrayList<CarteOrange> possède;
    private Pion couleur;

    public Aventurier() {
        possède = new ArrayList<CarteOrange>();
    }
    
    public ArrayList<CarteOrange> getMain() {
        return possède;
    }
    
    public Tuile getTuile() {
        return this.appartient;
    }

    public Pion getCouleur() {
        return couleur;
    }

    public final void setTuile(Tuile tArrivee) {
        appartient = tArrivee;
    }

    public final void setCouleur(Pion couleur) {
        this.couleur = couleur;
    }
    
    public void seDeplace(Tuile tArrivee) {
        this.getTuile().suppAventurier(this);
        tArrivee.addAventurier(this);
    }

    public void piocheCarte(CarteOrange c) {
        this.possède.add(c);
        c.setOwner(this);
    }

    public void defausseCarte(CarteOrange c) {
        this.possède.remove(c);
        c.delOwner();
    }

    public void afficheInfo() {
        System.out.println("Main de "+this.getClass().toString().substring(12)+" ("+this.getTuile().getNom().toString()+") : ");
        for (CarteOrange c : getMain()) {
            c.affiche();
        }
    }
    
    public abstract ArrayList<Tuile> calculTuileDispo(Grille g);

    public abstract ArrayList<Tuile> calculTuileAss(Grille g);

}
