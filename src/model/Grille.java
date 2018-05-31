package model;
import java.util.ArrayList;
import util.NomTuile;

public class Grille {

    private Tuile tuile[][];

    public Grille(ArrayList<Tuile> tuiles){
        int index = 0;//index pour parcourir l'arraylist des tuiles
        for (int i = 1; i<7; i++){//chaques lignes
            switch(i){
                case 1 : //ligne 1
                    for(int j = 3; j<5; j++){
                        this.tuile[i][j]=tuiles.get(index);
                        index++;
                    }
                    break;
                
                case 2 : //ligne 2
                    for(int j = 2; j<6; j++){
                        this.tuile[i][j]=tuiles.get(index);
                        index++;
                    }
                    break;
                
                case 3 :case 4 :case 5 : //ligne 3, 4 et 5
                    for(int j = 1; j<7; j++){
                        this.tuile[i][j]=tuiles.get(index);
                        index++;
                    }
                    break;
                
                case 6 : //ligne 6
                    for(int j = 3; j<5; j++){
                        this.tuile[i][j]=tuiles.get(index);
                        index++;
                    }
                    break;
            }
        }
    }

    public Tuile getTuile(int ligne, int colonne) {
            return this.tuile[ligne][colonne];
    }

    public Tuile[][] getGrille() {
            return this.tuile;
    }

}