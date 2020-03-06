/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bt;

import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JMenuBar;

/**
 *
 * @author gabor_hanacsek
 */
public class MyMenubar extends JMenuBar {
ImageIcon icon = new javax.swing.ImageIcon(getClass().getResource("/pictures/3438857.jpg"));

   
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(icon.getImage(), 0, 0, this);
        
    }

}
