package ileinterdite;

import java.util.*;

public abstract class Aventurier {

	private Tuile appartient;
	private ArrayList<CarteOrange> possède;
	private Pion couleur;

	public Tuile getTuile() {
		// TODO - implement Aventurier.getTuile
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param tArrivee
	 */
	public void setTuile(Tuile tArrivee) {
                appartient = tArrivee;
	}

	/**
	 * 
	 * @param tDepart
	 * @param g
     * @return 
	 */
	public abstract ArrayList<Tuile> calculTuileDispo(Tuile tDepart, Grille g);

	/**
	 * 
	 * @param tPosition
	 * @param g
     * @return 
	 */
	public abstract ArrayList<Tuile> calculTuileAss(Tuile tPosition, Grille g);

}