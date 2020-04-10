/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bt;

import java.awt.Graphics;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JMenuBar;

/**
 *
 * @author gabor_hanacsek
 */
public class MyMenubar extends JMenuBar {

    ImageIcon icon = new ImageIcon(System.getProperty("user.home") + "\\BT\\Pictures\\menubar.jpg");

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(icon.getImage(), (Toolkit.getDefaultToolkit().getScreenSize().width / 2) - (icon.getIconWidth() / 2), (Toolkit.getDefaultToolkit().getScreenSize().height / 2) - (icon.getIconHeight() / 2), this);

    }

}
