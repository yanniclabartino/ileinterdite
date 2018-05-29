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
		// TODO - implement Aventurier.setTuile
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param tDepart
	 * @param g
	 */
	public abstract ArrayList calculTuileDispo(Tuile tDepart, Grille g);

	/**
	 * 
	 * @param tPosition
	 * @param g
	 */
	public abstract Tuile calculTuileAssecher(Tuile tPosition, Grille g);

}