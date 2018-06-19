package view;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class SaisiJoueur extends JPanel {
    
    private JLabel titre;
    private JTextField nomJ;
    private JComboBox choixAv;
    //private static final String[] listAv = {"Explorateur","Ingénieur","Messager","Navigateur","Pilote","Plongeur"};
    private static String[] avDispo = {"Explorateur","Ingénieur","Messager","Navigateur","Pilote","Plongeur"};
    private String aventurier;
    SaisiJoueur(Integer num) {
        titre = new JLabel("Joueur "+num);
        nomJ = new JTextField();
        choixAv = new JComboBox(avDispo);
        this.setLayout(new GridLayout(4, 1, 10, 10));
        JPanel nom = new JPanel(new GridLayout(1, 2));
        nom.add(new JLabel("Nom :"));
        nom.add(nomJ);
        
        choixAv.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                aventurier = choixAv.getSelectedItem().toString();
            }
        });
        
        this.add(titre);
        this.add(nom);
        this.add(new JLabel("Aventurier :"));
        this.add(choixAv);
        
    }
    
    public String getNom(){
        return this.nomJ.getText();
    }
    
    public String getAventurier(){
        return this.aventurier;
    }
    
    public JComboBox getChoixAv(){
        return this.choixAv;
    }
    
    @Override
    public void setEnabled(boolean b){
        this.choixAv.setEnabled(b);
        this.choixAv.setBackground(Color.gray);
        this.nomJ.setEnabled(b);
        this.nomJ.setBackground(Color.gray);
        this.setBackground(new Color(180, 180, 180));
    }
}