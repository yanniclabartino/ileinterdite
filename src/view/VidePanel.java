/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Dimension;
import javax.swing.JPanel;

/**
 *
 * @author yannic
 */
public class VidePanel extends JPanel {
    private int largeur;
    private int hauteur;
    
    VidePanel(int l, int h){
        setLargeur(l);
        setHauteur(h);
        this.setPreferredSize(new Dimension(largeur, hauteur));
    }
    
    
    public void setLargeur(int largeur){
        this.largeur = largeur;
    }
    
    public void setHauteur(int hauteur){
        this.hauteur = hauteur;
    }
}
