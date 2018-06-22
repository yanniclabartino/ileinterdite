/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;

/**
 *
 * @author yannic
 */
public class VidePanel extends JPanel {
    
    VidePanel(int l, int h){
        this.setPreferredSize(new Dimension(l, h));
        this.setBackground(new Color(255, 0, 0, 0));
    }
}
