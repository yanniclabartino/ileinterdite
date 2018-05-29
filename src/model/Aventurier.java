package model;

import java.util.*;
import util.Utils.Pion;

public abstract class Aventurier {

	private Tuile appartient;
	private ArrayList<CarteOrange> possède;
	private Pion couleur;

	public Tuile getTuile() {
            return this.appartient;
	}

	public void setTuile(Tuile tArrivee) {
                appartient = tArrivee;
	}

	public abstract ArrayList<Tuile> calculTuileDispo(Tuile tDepart, Grille g);

	public abstract ArrayList<Tuile> calculTuileAss(Tuile tPosition, Grille g);

}