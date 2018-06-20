package view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author pc
 */
public class ImagePanel extends JPanel {

    private BufferedImage image;
    private int x,
                y,
                width,
                height;
    private double scale;//coeficient multiplicateur de la taille de l'image
    private int construction;//choix selon le constructeur
    

    ImagePanel(String cheminImage) {//création de l'image en choisissant uniquement la position
        this.x = 0;
        this.y = 0;
        try {
            File input = new File(cheminImage);
            image = ImageIO.read(input);
        } catch (IOException ie) {
            System.out.println("Error:" + ie.getMessage());
        }
        construction = 0;
        this.setPreferredSize(new Dimension(image.getWidth(this), image.getHeight(this)));
    }
    ImagePanel(int width, int height, String cheminImage) {//création de l'image en choisissant la taille
        this.x = 0;
        this.y = 0;
        this.width = width;
        this.height = height;
        try {
            File input = new File(cheminImage);
            image = ImageIO.read(input);
        } catch (IOException ie) {
            System.out.println("Error:" + ie.getMessage());
        }
        construction = 1;
        this.setPreferredSize(new Dimension(width, height));
    }
    ImagePanel(double scale, String cheminImage) {//création de l'image en choisissant la position et le ratio d'echelle
        this.x = 0;
        this.y = 0;
        this.scale = scale;
        
        try {
            File input = new File(cheminImage);
            image = ImageIO.read(input);
        } catch (IOException ie) {
            System.out.println("Error:" + ie.getMessage());
        }
        construction = 2;
        this.setPreferredSize(new Dimension((int)(image.getWidth(this)*scale), (int)(image.getHeight(this)*scale)));
    }
    
    protected void paintComponent(Graphics g) {
        //super.paintComponent(g);
        switch (construction){
            case 0:
                g.drawImage(image, x, y, this);
                break;
            case 1:
                g.drawImage(image, x, y, width, height, this);
                break;
                
            case 2:
                g.drawImage(image, x, y, (int)(image.getWidth(this)*scale), (int)(image.getHeight(this)*scale), this);
                break;
            default:
                g.drawImage(image, 0, 0, this);
                break;
        }
    }
    
    public void replacer(int x, int y){
        this.x = x;
        this.y = y;
        this.repaint();
    }
    
}