/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bt;

import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author gabor_hanacsek
 */
public class LoaderPanelBackground extends JPanel {

    ImageIcon img;

    public LoaderPanelBackground() {
        img = new javax.swing.ImageIcon(getClass().getResource("/pictures/loaderbackground.jpg"));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img.getImage(), 0, 0, null);

    }

}
