package view;

import ileinterdite.Message;
import ileinterdite.Observe;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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
    private imagePanel titre;
    private static final Integer[] nbJ = {2,3,4};
    private ArrayList<SaisiJoueur> saisiJoueurs;
    private JComboBox choixJoueurs;
    
    
    VueAccueil() {
        //Accueil :
        accueil = new JFrame();
        accueil.setLayout(new BorderLayout());
        
        choixJoueurs = new JComboBox(nbJ);
        
        JPanel lehaut = new JPanel(new GridLayout(3, 1));
        JPanel letitre = new JPanel();
        
        lehaut.add(new JLabel());
        
            titre = new imagePanel( 0 , 0 , 0.7 , System.getProperty("user.dir") + "/src/images/logo_ileinterdite.png");
            //System.out.println(Integer.toString(accueil.getWidth()/2));
            
            letitre.add(new JLabel("L'île Interdite"));
        lehaut.add(letitre);
        
            JPanel choixNb = new JPanel();
            choixNb.add(new JLabel("Nombre de joueurs :"));
            choixJoueurs.setPreferredSize(new Dimension(40, 20));
            choixNb.add(choixJoueurs);
        lehaut.add(choixNb);
        
        accueil.add(lehaut, BorderLayout.NORTH);
        
        JPanel lesjoueurs = new JPanel();
        saisiJoueurs = new ArrayList();
        SaisiJoueur j1 = new SaisiJoueur(1);
        SaisiJoueur j2 = new SaisiJoueur(2);
        SaisiJoueur j3 = new SaisiJoueur(3);
        SaisiJoueur j4 = new SaisiJoueur(4);
        saisiJoueurs.add(j1);
        saisiJoueurs.add(j2);
        saisiJoueurs.add(j3);
        saisiJoueurs.add(j4);
        
        j3.setenabled(false);
        j4.setenabled(false);
        lesjoueurs.add(j1);
        lesjoueurs.add(j2);
        lesjoueurs.add(j3);
        lesjoueurs.add(j4);
        
        choixJoueurs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for ( int i = 0 ; i < 4 ; i ++ ){
                    if ( i < (int)choixJoueurs.getSelectedItem() ){
                        saisiJoueurs.get(i).setenabled(true);
                        saisiJoueurs.get(i).updateUI();
                        
                    }else{
                        saisiJoueurs.get(i).setenabled(false);
                        saisiJoueurs.get(i).updateUI();
                    }
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
