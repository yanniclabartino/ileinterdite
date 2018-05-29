package model;
public class Grille {

	private Tuile tuile[][];

	/**
	 * 
	 * @param ligne
	 * @param colonne
     * @return 
	 */
	public Tuile getTuile(int ligne, int colonne) {
		return this.tuile[ligne][colonne];
	}

	public Tuile[][] getGrille() {
		return this.tuile;
	}

}