package model;
public class Grille {

	private Tuile tuile[][];

	public Tuile getTuile(int ligne, int colonne) {
		return this.tuile[ligne][colonne];
	}

	public Tuile[][] getGrille() {
		return this.tuile;
	}

}