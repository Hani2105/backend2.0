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
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JViewport;

/**
 *
 * @author gabor_hanacsek
 */
public class ViewPortGraphics extends JViewport {

    ImageIcon img;

    public ViewPortGraphics(Variables.viewports viewports) {

        switch (viewports) {
            case plannpanel:
                img = new javax.swing.ImageIcon(getClass().getResource("/pictures/17545.jpg"));
                break;
            case datapanel:
                img = new javax.swing.ImageIcon(getClass().getResource("/pictures/3438857.jpg"));
//                img = Variables.datapanelbackground;
                break;
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img.getImage(), 0, 0, this);
    }

}
