/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author yannic
 */
public class IHM extends JPanel {

    private JButton bDepl;
    private JButton bAss;
    private JButton bPioch;
    private JButton bGagner;
    
    
    
    public IHM() {
        
        bDepl = new JButton("Déplacer");
        bAss = new JButton("Assécher");
        bPioch = new JButton("Piocher");
        bGagner = new JButton("Gagner un trésor");
        
        // CRÉATION DES JPANEL
        JPanel layer1north = new JPanel();
        JPanel layer1south = new JPanel();
        
        JPanel layer2north = new JPanel();
        JPanel layer2south = new JPanel();
        JPanel layer2east = new JPanel();
        JPanel layer2west = new JPanel();
        JPanel layer2center = new JPanel();
        
        JPanel layer3westCenter = new JPanel();
        JPanel layer3eastCenter = new JPanel();
        
        JPanel layer4eastCenterCenter = new JPanel();
        
        // AFFECTATION DE TYPES AUX PANELS
        this.setLayout(new BorderLayout()); // = layer 0
        
        layer1north.setLayout(new BorderLayout());
        layer1south.setLayout(new BorderLayout());
        
        layer2north.setLayout(new BorderLayout());
        layer2south.setLayout(new GridLayout(1,6));
        layer2east.setLayout(new BorderLayout());
        layer2west.setLayout(new BorderLayout());
        
        layer3westCenter.setLayout(new GridLayout(2,2));
        layer3eastCenter.setLayout(new BorderLayout());
        
        layer4eastCenterCenter.setLayout(new GridLayout(4,1));
        
        // PLACEMENT DES PANELS
        this.add(layer1north, BorderLayout.NORTH);
        this.add(layer1south, BorderLayout.SOUTH);
        
        layer1north.add(layer2north, BorderLayout.NORTH);
        layer1north.add(layer2east, BorderLayout.EAST);
        layer1north.add(layer2west, BorderLayout.WEST);
        layer1north.add(layer2center, BorderLayout.CENTER);
        layer1south.add(layer2south, BorderLayout.CENTER);
        
        layer2west.add(layer3westCenter, BorderLayout.CENTER);
        layer2east.add(layer3eastCenter, BorderLayout.CENTER);
        
        layer3eastCenter.add(layer4eastCenterCenter, BorderLayout.CENTER);
        
        // PLACEMENT DES BOUTONS
        layer4eastCenterCenter.add(bDepl);
        layer4eastCenterCenter.add(bAss);
        layer4eastCenterCenter.add(bPioch);
        layer4eastCenterCenter.add(bGagner);
        
        
        }
        
            
}
