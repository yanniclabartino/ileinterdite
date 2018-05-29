package model;

import java.util.*;
import util.Utils.Pion;

public abstract class Aventurier {

	private Tuile appartient;
	private ArrayList<CarteOrange> possède;
	private Pion couleur;

        Aventurier(Tuile t, Pion p){
            this.appartient = t;
            this.couleur = p;
        }
        
	public Tuile getTuile() {
            return this.appartient;
	}

	public void setTuile(Tuile tArrivee) {
                appartient = tArrivee;
	}
        
        public void addCarte(CarteOrange c){
            this.possède.add(c);
        }

        public void suppCarte(CarteOrange c){
            this.possède.remove(c);
        }
        
        
	public abstract ArrayList<Tuile> calculTuileDispo(Tuile tDepart, Grille g);

	public abstract ArrayList<Tuile> calculTuileAss(Tuile tPosition, Grille g);

}