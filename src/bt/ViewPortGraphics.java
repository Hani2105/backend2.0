/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bt;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JViewport;

/**
 *
 * @author gabor_hanacsek
 */
public class ViewPortGraphics extends JViewport {

    ImageIcon img;
    BeSheet b;
    Variables.viewports viewports;

    public ViewPortGraphics(Variables.viewports viewports, BeSheet b) {
        this.b = b;
        this.viewports = viewports;
        switch (viewports) {
            case plannpanel:
//              img = new javax.swing.ImageIcon(getClass().getResource("/pictures/fopanelhatter.jpg"));
                img = new ImageIcon(System.getProperty("user.home") + "\\BT\\Pictures\\fopanelhatter.jpg");
                break;
            case datapanel:
//              img = new javax.swing.ImageIcon(getClass().getResource("/pictures/fopanelhatter.jpg"));
                img = new ImageIcon(System.getProperty("user.home") + "\\BT\\Pictures\\adatpanelhatter.jpg");
                break;
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        switch (viewports) {
            case plannpanel:
//                g.drawImage(img.getImage(), ((int) b.jPanel1.getSize().getWidth() / 2) - (img.getIconWidth() / 2), ((int) b.jPanel1.getSize().getHeight() / 2) - (img.getIconHeight() / 2), this);
                g.drawImage(img.getImage(), (this.getSize().width / 2) - (img.getIconWidth() / 2), (this.getSize().height / 2) - (img.getIconHeight() / 2), this);
                break;
            case datapanel:
                g.drawImage(img.getImage(), (this.getSize().width / 2) - (img.getIconWidth() / 2), (this.getSize().height/ 2) - (img.getIconHeight() / 2), this);
                break;
        }
    }

}
