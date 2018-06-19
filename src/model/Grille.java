package model;

import java.util.ArrayList;
import util.NomTuile;
import util.Utils;

public class Grille {

    private Tuile tuile[][];

    public Grille(ArrayList<Tuile> tuiles) {
        tuile = new Tuile[6][6];
        int index = 0;//index pour parcourir l'arraylist des tuiles
        for (int y = 0; y < 6; y++) {//chaques colonnes
            switch (y) {
                case 0:
                case 5://colonnes 1, 6 //lignes 3 à 4
                    for (int x = 2; x < 4; x++) {
                        this.tuile[x][y] = tuiles.get(index);
                        this.tuile[x][y].setLigEtCol(x, y);
                        index++;
                    }
                    break;

                case 1:
                case 4: //colonne 2, 5 //lignes 2 à 5 
                    for (int x = 1; x < 5; x++) {
                        this.tuile[x][y] = tuiles.get(index);
                        this.tuile[x][y].setLigEtCol(x, y);
                        index++;
                    }
                    break;

                case 2:
                case 3: //colonne 3, 4 //lignes 1 à 6
                    for (int x = 0; x < 6; x++) {
                        this.tuile[x][y] = tuiles.get(index);
                        this.tuile[x][y].setLigEtCol(x, y);
                        index++;
                    }
                    break;
            }
        }
    }

    public Tuile getTuile(int x, int y) {
        if (((x == 0 || x == 5) && y >= 2 && y <= 3) || ((x == 1 || x == 4) && y >= 1 && y <= 4) || (x >= 2 && x <= 3 && y >= 0 && y <= 5)) {
            return this.tuile[x][y];
        } else {
            return null;
        }
    }

    public Tuile getTuile(NomTuile nom) {
        //une méthode qui trouve la tuile de NomTuile nom dans la grille et qui la renvoi
        Tuile tmp = null;
        for (int j = 0; j < 6; j++) {
            for (int i = 0; i < 6; i++) {
                try {
                    if (this.getTuile(i, j).getNom() == nom) {
                        tmp = getTuile(i, j);
                    }
                } catch (NullPointerException n) {
                    //Si la tuile n'existe pas en (1,1) par exemple (ce qui est logique) on ne traite pas le cas
                }
            }
        }
        return tmp;
    }

    public ArrayList<Tuile> getGrille() {
        ArrayList<Tuile> tuilesGrille = new ArrayList<Tuile>();
        for (int i = 0; i < 6; i++) {//chaques lignes
            switch (i) {
                case 0:
                case 5://ligne 1, 6 //colonnes 3 à 4
                    for (int j = 2; j < 4; j++) {
                        tuilesGrille.add(this.getTuile(j, i));
                    }
                    break;

                case 1:
                case 4: //ligne 2, 5 //colonnes 2 à 5
                    for (int j = 1; j < 5; j++) {
                        tuilesGrille.add(this.getTuile(j, i));
                    }
                    break;

                case 2:
                case 3: //ligne 3, 4 //colonnes 1 à 6
                    for (int j = 0; j < 6; j++) {
                        tuilesGrille.add(this.getTuile(j, i));
                    }
                    break;
            }
        }
        return tuilesGrille;
    }

    public void afficheGrilleTexte(Aventurier joueur) {
        /*
            0       1       2       3       4       5
                        +-------+-------+
                        |       |    (4)|                   0
                +-------+-------+-------+-------+
                |       |   #   |       |       |           1
        +-------+-------+-------+-------+-------+-------+
        |       |       |       |(H)~   |       |       |   2
        +-------+-------+-------+-------+-------+-------+
        |       |    (1)|       |       |    (X)|       |   3
        +-------+-------+-------+-------+-------+-------+
                |       |   ~   |       |       |           4
                +-------+-------+-------+-------+
                        |       |       |                   5
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
                case 2:
                case 3:
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
                tuileActive = this.getTuile(x, y);
                if (tuileActive != null) {
                    if (tuileActive.getNom() == NomTuile.HELIPORT) {
                        System.out.print("(H)");
                    } else {
                        System.out.print("   ");
                    }
                    if (tuileActive.getEtat() == Utils.EtatTuile.INONDEE) {
                        System.out.print("\033[36m~\033[0m");
                    } else if (tuileActive.getEtat() == Utils.EtatTuile.COULEE) {
                        System.out.print("\033[31;40m#\033[0m");
                    } else {
                        System.out.print(" ");
                    }
                    if (tuileActive.getPossede().size() > 0) {
                        if (tuileActive.getPossede().contains(joueur)) {
                            System.out.print("(\033[31mX\033[0m)");
                        } else {
                            System.out.print("(" + tuileActive.getPossede().size() + ")");
                        }
                    } else {
                        System.out.print("   ");
                    }
                    System.out.print("|");
                }
                if (x == 5) {
                    System.out.println();
                }
            }
        }
        System.out.println("                +-------+-------+");
    }
}
