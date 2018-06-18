package view;

import ileinterdite.Observe;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;

public class VueAccueil extends Observe{
    
    //les trois "écran" de l'accueil
    private JFrame accueil;
    private JFrame parametres;
    private JFrame regles;
    
    //accueil :
    private JButton valider;
    private JLabel titre;
    private static final Integer[] nbJ = {2,3,4};
    private JComboBox choixJoueurs;
    
    
    VueAccueil() {
        //Accueil :
        accueil = new JFrame();
        accueil.setLayout(new BorderLayout());
        
        choixJoueurs = new JComboBox(nbJ);
        titre = new JLabel("L'Île Interdite");
        JPanel lehaut = new JPanel(new GridLayout(3, 1));
        lehaut.add(new JLabel());
        lehaut.add(titre);
        lehaut.add(choixJoueurs);
        accueil.add(lehaut, BorderLayout.NORTH);
        
        JPanel lesjoueurs = new JPanel();
        for ( int i = 0 ; i < 4 ; i ++ ){
            SaisiJoueur j = new SaisiJoueur(i+1);
            lesjoueurs.add(j);
        }
        
        choixJoueurs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lesjoueurs.removeAll();
                for ( int i = 0 ; i < (int)choixJoueurs.getSelectedItem() ; i ++ ){
                    SaisiJoueur j = new SaisiJoueur(i+1);
                    lesjoueurs.add(j);
                }
                lesjoueurs.updateUI();
            }
        });
        
        
        JPanel lecentre = new JPanel();
        lecentre.add(lesjoueurs);
        
        accueil.add(lecentre, BorderLayout.CENTER);
        accueil.setTitle("L'Île Interdite");
        accueil.setSize(1080, 720);
        accueil.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        accueil.setVisible(true);
        
        
        //Parametres :
        
        
        
        //Regles :
        
        
    }
    
}
