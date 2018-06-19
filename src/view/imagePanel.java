package view;

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
public class imagePanel extends JPanel {

    private BufferedImage image;
    private int x,
                y,
                width,
                height;
    private double scale;
    private int construction;
    

    public imagePanel(int x, int y, String cheminImage) {//création de l'image en choisissant uniquement la position
        this.x = x;
        this.y = y;
        try {
            File input = new File(cheminImage);
            image = ImageIO.read(input);
        } catch (IOException ie) {
            System.out.println("Error:" + ie.getMessage());
        }
        construction = 0;
    }
    public imagePanel(int x, int y,int width, int height, String cheminImage) {//création de l'image en choisissant la position et la taille
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        
        try {
            File input = new File(cheminImage);
            image = ImageIO.read(input);
        } catch (IOException ie) {
            System.out.println("Error:" + ie.getMessage());
        }
        construction = 1;
    }
    public imagePanel(int x, int y,double scale, String cheminImage) {//création de l'image en choisissant la position et le ratio d'echelle
        this.x = x;
        this.y = y;
        this.scale = scale;
        
        try {
            File input = new File(cheminImage);
            image = ImageIO.read(input);
        } catch (IOException ie) {
            System.out.println("Error:" + ie.getMessage());
        }
        construction = 2;
    }
    
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        switch (construction){
            case 0:
                g.drawImage(image, x, y, null);
                break;
            case 1:
                g.drawImage(image, x, y, width, height, null);
                break;
            case 2:
                g.drawImage(image, x, y, (int)(image.getWidth()*scale), (int)(image.getHeight()*scale), null);
        }
    }
    
}