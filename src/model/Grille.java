package model;
import java.util.ArrayList;
import util.NomTuile;

public class Grille {

    private Tuile tuile[][];

    public Grille(ArrayList<Tuile> tuiles){
        //à toi de jouer T-bow !
    }

    public Tuile getTuile(int ligne, int colonne) {
            return this.tuile[ligne][colonne];
    }

    public Tuile[][] getGrille() {
            return this.tuile;
    }

}