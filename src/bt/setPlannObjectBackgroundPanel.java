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
public class setPlannObjectBackgroundPanel extends JPanel {

    ImageIcon img;

    public setPlannObjectBackgroundPanel() {
        img = new javax.swing.ImageIcon(getClass().getResource("/pictures/controlblue.jpg"));
    }

    @Override
    public void paintComponent(Graphics g) {

        g.drawImage(img.getImage(), 0, 0, null);
    }

}
