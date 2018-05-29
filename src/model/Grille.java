package model;
import util.NomTuile;

public class Grille {

    private Tuile tuile[][];

    Grille(){
        tuile = { {new Tuile(NomTuile.LE_PONT_DES_ABIMES),},
                  {},
                  {},
                  {},
                  {},
                  {} };
    }

    public Tuile getTuile(int ligne, int colonne) {
            return this.tuile[ligne][colonne];
    }

    public Tuile[][] getGrille() {
            return this.tuile;
    }

}