package view;

import java.awt.GridLayout;
import javax.swing.*;

public class SaisiJoueur extends JPanel {
    
    private JLabel titre;
    private JTextField nomJ;
    private JComboBox choixAv;
    //private static final String[] listAv = {"Explorateur","Ingénieur","Messager","Navigateur","Pilote","Plongeur"};
    private static String[] AvDispo = {"Explorateur","Ingénieur","Messager","Navigateur","Pilote","Plongeur"};
    SaisiJoueur(Integer num) {
        titre = new JLabel("Joueur "+num);
        nomJ = new JTextField();
        choixAv = new JComboBox(AvDispo);
        this.setLayout(new GridLayout(4, 1, 10, 10));
        JPanel nom = new JPanel(new GridLayout(1, 2));
        nom.add(new JLabel("Nom :"));
        nom.add(nomJ);
        
        this.add(titre);
        this.add(nom);
        this.add(new JLabel("Aventurier :"));
        this.add(choixAv);
        
    }
    
    public String getNom(){
        return this.nomJ.getText();
    }
    
    public String getChoixAv(){
        return this.choixAv.getSelectedItem().toString();
    }
    
}
