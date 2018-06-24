package view;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class SaisiJoueur extends JPanel {

    private JLabel titre;
    private JTextField nomJ;
    private JComboBox choixAv;
    //private static final String[] listAv = {"Explorateur","Ingénieur","Messager","Navigateur","Pilote","Plongeur"};
    public static final String[] avDispo = {"Explorateur", "Ingénieur", "Messager", "Navigateur", "Pilote", "Plongeur"};

    SaisiJoueur(Integer num) {
        titre = new JLabel("Joueur " + num);
        nomJ = new JTextField("");
        choixAv = new JComboBox(avDispo);
        this.setLayout(new GridLayout(4, 1, 10, 10));
        JPanel nom = new JPanel(new GridLayout(1, 2));
        nom.add(new JLabel("Nom :"));
        nom.add(nomJ);

        this.add(titre);
        this.add(nom);
        this.add(new JLabel("Aventurier :"));
        this.add(choixAv);

        this.setBackground(new Color(230, 247, 255, 190));
        nom.setOpaque(false);
        choixAv.setOpaque(false);
    }

    public String getNom() {
        return this.nomJ.getText();
    }

    public String getAventurier() {
        return choixAv.getSelectedItem().toString();
    }

    public JComboBox getChoixAv() {
        return this.choixAv;
    }

    public void setAlea(boolean b) {
        this.choixAv.setEditable(b);
    }

    public void setenabled(boolean b) {
        this.setEnabled(b);
        this.choixAv.setEnabled(b);
        this.nomJ.setEnabled(b);
        this.nomJ.setBackground((b) ? (new Color(230, 247, 255, 190)) : new Color(179, 236, 255, 90));
        this.setBackground((b) ? (new Color(230, 247, 255, 190)) : new Color(179, 236, 255, 90));
        this.titre.setBackground((b) ? (new Color(230, 247, 255, 190)) : new Color(179, 236, 255, 90));
    }

    void setAv(Integer i) {
        this.choixAv.setSelectedIndex(i);
    }
}
