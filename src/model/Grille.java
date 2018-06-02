package model;
import java.util.ArrayList;
import util.NomTuile;

public class Grille {

    private Tuile tuile[][];

    public Grille(ArrayList<Tuile> tuiles){
        int index = 0;//index pour parcourir l'arraylist des tuiles
        for (int i = 1; i<7; i++){//chaques lignes
            switch(i){
                case 1 : case 6 ://ligne 1
                    for(int j = 3; j<5; j++){
                        this.tuile[i][j]=tuiles.get(index);
                        index++;
                    }
                    break;
                
                case 2 :case 5 : //ligne 2
                    for(int j = 2; j<6; j++){
                        this.tuile[i][j]=tuiles.get(index);
                        index++;
                    }
                    break;
                
                case 3 :case 4 : //ligne 3, 4 et 5
                    for(int j = 1; j<7; j++){
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
    
    public Tuile getTuile(NomTuile nom){
    //une méthode qui trouve la tuile de NomTuile nom dans la grille et qui la renvoi
        int x = 0;
        int y = 0;
        while (this.getTuile(x, y).getNom()!=nom && y <= 5) {
            x++;
            if (x > 5){
                x = 0;
                y++;
            }
        }
        if (this.getTuile(x, y).getNom()==nom) { 
            return this.getTuile(x, y);
        } else {
            System.err.println("TUILE "+nom.toString()+" NON-TROUVEE !");
            return null;
        }
    }

    public Tuile[][] getGrille() {
            return this.tuile;
    }

}