/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author yannic
 */
public class ImageFond extends JPanel {
    
    private Image image;
    private Integer width, height;
    
    public ImageFond(int width, int height, String chemin){
        super();
        this.width = width ;
        this.height = height ;
        
        try {
            this.image = ImageIO.read(new File(System.getProperty("user.dir") + chemin));
        } catch (IOException ex) {
            
        }
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, width, height, null, this);
    }
    
}
