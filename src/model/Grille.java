package model;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;
import util.NomTuile;
import util.Utils;

public class Grille extends JPanel {

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

    @Override
    public void paintComponent(Graphics g) {
        Dimension size = getSize();
        
        double largeurTuile = (size.width / 6) * 0.89;
        double hauteurTuile = (size.height / 6) * 0.89;
        double ecartTuilesL = (size.width / 6) * 0.1;
        double ecartTuilesH = (size.height / 6) * 0.1;
        
        /* DESSIN DU FOND DE LA GRILLE */
        g.setColor(new Color(135, 74, 48));
        g.fillRect((int)(ecartTuilesL*2 + largeurTuile*2), (int) (ecartTuilesH), (int) (ecartTuilesL*3+largeurTuile*2), (int) (ecartTuilesH*5+hauteurTuile*6));
        g.fillRect((int)(ecartTuilesL*3+largeurTuile*2),(int)(0),(int)(ecartTuilesL+largeurTuile*2),(int)(ecartTuilesH*7+hauteurTuile*6));
        g.fillRect((int)(ecartTuilesL*2+largeurTuile),(int)(ecartTuilesH+hauteurTuile),(int)(largeurTuile*4+ecartTuilesL*3),(int)(hauteurTuile*4+ecartTuilesH*5));
        g.fillRect((int)(ecartTuilesL+largeurTuile),(int)(ecartTuilesH*2+hauteurTuile),(int)(largeurTuile*4+ecartTuilesL*5),(int)(hauteurTuile*4+ecartTuilesH*3));
        g.fillRect((int)(ecartTuilesL),(int)(ecartTuilesH*2+hauteurTuile*2),(int)(ecartTuilesL*5+largeurTuile*6),(int)(ecartTuilesH*3+hauteurTuile*2));
        g.fillRect((int)(0),(int)(ecartTuilesH*3+hauteurTuile*2),(int)(ecartTuilesL*7+largeurTuile*6),(int)(hauteurTuile*2+ecartTuilesH));
        g.fillOval((int)(ecartTuilesL*2+largeurTuile*2),(int)(0),(int)(ecartTuilesL*2),(int)(ecartTuilesH*2));
        g.fillOval((int)(ecartTuilesL*3+largeurTuile*4),(int)(0),(int)(ecartTuilesL*2),(int)(ecartTuilesH*2));
        g.fillOval((int)(ecartTuilesL*1+largeurTuile),(int)(ecartTuilesH+hauteurTuile),(int)(ecartTuilesL*2),(int)(ecartTuilesH*2));
        g.fillOval((int)(ecartTuilesL*4+largeurTuile*5),(int)(ecartTuilesH+hauteurTuile),(int)(ecartTuilesL*2),(int)(ecartTuilesH*2));
        g.fillOval((int)(0),(int)(ecartTuilesH*2+hauteurTuile*2),(int)(ecartTuilesL*2),(int)(ecartTuilesH*2));
        g.fillOval((int)(0),(int)(ecartTuilesH*3+hauteurTuile*4),(int)(ecartTuilesL*2),(int)(ecartTuilesH*2));
        g.fillOval((int)(ecartTuilesL*5+largeurTuile*6),(int)(ecartTuilesH*2+hauteurTuile*2),(int)(ecartTuilesL*2),(int)(ecartTuilesH*2));
        g.fillOval((int)(ecartTuilesL*5+largeurTuile*6),(int)(ecartTuilesH*3+hauteurTuile*4),(int)(ecartTuilesL*2),(int)(ecartTuilesH*2));
        g.fillOval((int)(ecartTuilesL+largeurTuile),(int)(ecartTuilesH*4+hauteurTuile*5),(int)(ecartTuilesL*2),(int)(ecartTuilesH*2));
        g.fillOval((int)(ecartTuilesL*4+largeurTuile*5),(int)(ecartTuilesH*4+hauteurTuile*5),(int)(ecartTuilesL*2),(int)(ecartTuilesH*2));
        g.fillOval((int)(ecartTuilesL*2+largeurTuile*2),(int)(ecartTuilesH*5+hauteurTuile*6),(int)(ecartTuilesL*2),(int)(ecartTuilesH*2));
        g.fillOval((int)(ecartTuilesL*3+largeurTuile*4),(int)(ecartTuilesH*5+hauteurTuile*6),(int)(ecartTuilesL*2),(int)(ecartTuilesH*2));
        /*                             */
        
        int coordX, coordY;
        
        g.setColor(new Color(0, 0, 0, 1));
        g.fillRect(0, 0, size.width, size.height);
        
        for (int l = 0; l < 6; l++) {
            for (int c = 0; c < 6; c++) {
                if (this.getTuile(c, l) != null) {
                    
                    coordX = (int) (ecartTuilesL * (c + 1) + largeurTuile * c);
                    coordY = (int) (ecartTuilesH * (l + 1) + hauteurTuile * l);
                    
                    BufferedImage image = null;
                    
                    if (this.getTuile(c, l).getEtat() != Utils.EtatTuile.COULEE) {
                        switch (this.getTuile(c, l).getSelected()) {
                            case 1:
                                //illustrer la tuile pour déplacement.
                                try {
                                    File input = new File(System.getProperty("user.dir") + "/src/images/tuiles/"+this.getTuile(c, l).getNom().toString()+"Dispo.png");
                                    image = ImageIO.read(input);
                                } catch (IOException ie) {
                                    
                                }
                                g.drawImage(image, coordX, coordY, (int) largeurTuile,(int)  hauteurTuile, this);
                                break;
                            case 2:
                                //illustrer la tuile pour le déplacement pilote.
                                try {
                                    File input = new File(System.getProperty("user.dir") + "/src/images/tuiles/"+this.getTuile(c, l).getNom().toString()+"Spe.png");
                                    image = ImageIO.read(input);
                                } catch (IOException ie) {
                                    
                                }
                                g.drawImage(image, coordX, coordY, (int) largeurTuile,(int)  hauteurTuile, this);
                                break;
                            case 0:
                                try {
                                    File input = new File(System.getProperty("user.dir") + "/src/images/tuiles/"+this.getTuile(c, l).getNom().toString()+this.getTuile(c, l).getEtat().toString()+".png");
                                    image = ImageIO.read(input);
                                } catch (IOException ie) {
                                    
                                }
                                g.drawImage(image, coordX, coordY, (int) largeurTuile,(int)  hauteurTuile, this);
                                break;
                        }
                    } else {
                        g.setColor(new Color(135, 74, 48));
                        g.fillRect(coordX, coordY, (int) largeurTuile, (int) hauteurTuile);
                    }
                    
                    int décalage = 2;
                    
                    if (!this.getTuile(c, l).getPossede().isEmpty()) {
                        for (Aventurier a : this.getTuile(c, l).getPossede()) {
                            g.setColor(Color.WHITE);
                            g.fillOval(coordX+décalage, coordY+18, (int) (largeurTuile/5+4), (int)  (hauteurTuile/5+4));
                            g.setColor(a.getCouleur().getCouleur());
                            g.fillOval(coordX+décalage+2, coordY+20, (int) largeurTuile/5, (int)  hauteurTuile/5);
                            décalage = décalage + 18;
                        }
                    }
                }
            }
        }

    }

    public void selectionTuileDispo(ArrayList<Tuile> tuilesDispo, int typeSelection) {
        for (Tuile t1 : this.getGrille()) {
            for (Tuile t2 : tuilesDispo) {
                if (t1==t2) {
                    t1.setSelected(typeSelection);
                }
            }
        }
    }
    
    public void deselectionnerTuiles() {
        for (Tuile t : this.getGrille()) {
            t.setSelected(0);
        }
    }
    
    public Aventurier getAventurier(Tuile t, int X, int Y){
        //X et Y sont les coordonées d'un clique sur une tuile de la grille.
        X = X - 3; //pour régler la marge perdue
        Y = Y - 3; //pour régler la marge perdue
        
        
        Dimension size = getSize();
        double largeurTuile = (size.width / 6) * 0.89;
        double hauteurTuile = (size.height / 6) * 0.89;
        double ecartTuilesL = (size.width / 6) * 0.1;
        double ecartTuilesH = (size.height / 6) * 0.1;
        
        while (X > ecartTuilesL + largeurTuile) {
            X = (int) (X - ecartTuilesL - largeurTuile);
        }
        while (Y > ecartTuilesH + hauteurTuile) {
            Y = (int) (Y - ecartTuilesH - hauteurTuile);
        }
        if (Y >= 18+ecartTuilesH && Y <= 23+hauteurTuile/4+ecartTuilesH) {
            X = X - (int) ecartTuilesL;
            if (X >= 2 && X <= largeurTuile / 5 + 6 && !t.getPossede().isEmpty()){
                return t.getPossede().get(0);
            } else if (X >= 20 && X <= largeurTuile / 5 + 24 && t.getPossede().size() > 1) {
                return t.getPossede().get(1);
            } else if (X >= 38 && X <= largeurTuile / 5 + 42 && t.getPossede().size() > 2) {
                return t.getPossede().get(2);
            } else if (X >= 56 && X <= largeurTuile / 5 + 60 && t.getPossede().size() > 3) {
                return t.getPossede().get(3);
            } else {
                return null;
            }
        }
        return null;
    }
}
