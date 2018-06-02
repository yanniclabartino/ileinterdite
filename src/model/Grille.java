package model;
import java.util.ArrayList;
import util.NomTuile;
import util.Utils;

public class Grille {

    private Tuile tuile[][];

    public Grille(ArrayList<Tuile> tuiles){
        tuile = new Tuile[6][6];
        int index = 0;//index pour parcourir l'arraylist des tuiles
        for (int i = 0; i<6; i++){//chaques lignes
            switch(i){
                case 0 : case 5 ://ligne 1, 6 //colonnes 3 à 4
                    for(int j = 2; j<4; j++){
                        this.tuile[i][j]=tuiles.get(index);
                        index++;
                    }
                    break;
                
                case 1 :case 4 : //ligne 2, 5 //colonnes 2 à 5
                    for(int j = 1; j<5; j++){
                        this.tuile[i][j]=tuiles.get(index);
                        index++;
                    }
                    break;
                
                case 2 :case 3 : //ligne 3, 4 //colonnes 1 à 6
                    for(int j = 0; j<6; j++){
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
    public void afficheGrilleTexte(Aventurier joueur) {
        /*
                        +-------+-------+
                        |       |    (4)|
                +-------+-------+-------+-------+
                |       |   #   |       |       |
        +-------+-------+-------+-------+-------+-------+
        |       |       |       |(H)~   |       |       |
        +-------+-------+-------+-------+-------+-------+
        |       |    (1)|       |       |    (X)|       |
        +-------+-------+-------+-------+-------+-------+
                |       |   ~   |       |       |
                +-------+-------+-------+-------+
                        |       |       |             
                        +-------+-------+
        //# = tuile coulée
        //~ = tuile ionnondé
        //(X) = position du joueur actif
        //(H) = héliport
        //(nbJoueurSurCase) = nombre de joueur sur la case
        //(X) prime sur (nbJoueurSurCase) si le joueur actif est sur une case avec d'autre joueurs.
         */

        Tuile tuileActive;
        for (int y = 0; y < 6; y++) {
            switch (y) {
                case 0:
                    System.out.println("                +-------+-------+");
                    System.out.print("                |");
                    break;
                case 1:
                    System.out.println("        +-------+-------+-------+-------+");
                    System.out.print("        |");
                    break;
                case 2: case 3:
                    System.out.println("+-------+-------+-------+-------+-------+-------+");
                    System.out.print("|");
                    break;
                case 4:
                    System.out.println("+-------+-------+-------+-------+-------+-------+");
                    System.out.print("        |");
                    break;
                case 5:
                    System.out.println("        +-------+-------+-------+-------+");
                    System.out.print("                |");
                    break;
                default:
                    break;
            }
            for (int x = 0; x < 6; x++) {
                tuileActive=this.getTuile(x, y);
                if (tuileActive!=null){
                    if (tuileActive.getNom()==NomTuile.HELIPORT) {
                        System.out.print("(H)");
                    } else {
                        System.out.print("   ");
                    }
                    if (tuileActive.getEtat()==Utils.EtatTuile.INONDEE) {
                        System.out.print("~");
                    } else if (tuileActive.getEtat()==Utils.EtatTuile.COULEE){
                        System.out.print("#");
                    } else {
                        System.out.print(" ");
                    }
                    if (tuileActive.getPossede().size() > 0){
                        if (tuileActive.getPossede().contains(joueur)) {
                            System.out.print("(X)");
                        } else {
                            System.out.print("("+tuileActive.getPossede().size()+")");
                        }
                    } else {
                        System.out.print("   ");
                    }
                    System.out.print("|");
                }
                if (x==5){
                    System.out.println();
                }
            }
        }
        System.out.println("                +-------+-------+");
    }
}