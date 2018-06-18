/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

    public imagePanel(String cheminImage) {
        try {

            File input = new File(cheminImage);

            image = ImageIO.read(input);
        } catch (IOException ie) {
            System.out.println("Error:" + ie.getMessage());
        }
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null);
    }
}
