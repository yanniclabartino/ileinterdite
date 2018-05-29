package model;
import util.NomTuile;

public class Grille {

    private Tuile tuile[][];

    Grille(){
                  
    }

    public Tuile getTuile(int ligne, int colonne) {
            return this.tuile[ligne][colonne];
    }

    public Tuile[][] getGrille() {
            return this.tuile;
    }

}