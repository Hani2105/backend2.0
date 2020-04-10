/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bt;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author gabor_hanacsek
 */
public class BackgroundPanel extends JPanel {

    Variables.background bg;
    ImageIcon img;
    JPanel panel;

    public BackgroundPanel(Variables.background bg) {
        this.bg = bg;
//az inicializálásnál eldől, hogy milyen kép kerül betöltésre
        switch (bg) {
            case sfdc:
                img = new ImageIcon(System.getProperty("user.home") + "\\BT\\Pictures\\sfdc_1.png");
                break;
            case tervmentese:
                img = new ImageIcon(System.getProperty("user.home") + "\\BT\\Pictures\\bigsave.png");
                break;
            case oldalmenu:
                img = new ImageIcon(System.getProperty("user.home") + "\\BT\\Pictures\\oldalmenuhatter.jpg");
                break;
            case controlpanelkulso:
                img = new ImageIcon(System.getProperty("user.home") + "\\BT\\Pictures\\controlpanelkulso.jpg");
                break;
            case controlpanelbelso:
                img = new ImageIcon(System.getProperty("user.home") + "\\BT\\Pictures\\controlpanelbelso.jpg");
                break;

            case setplannobjectback:
                img = new ImageIcon(System.getProperty("user.home") + "\\BT\\Pictures\\setplannobjectback.jpg");
                break;

        }

    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        switch (bg) {
            case sfdc:
                g.drawImage(img.getImage(), (Toolkit.getDefaultToolkit().getScreenSize().width / 2) - (img.getIconWidth() / 2), (Toolkit.getDefaultToolkit().getScreenSize().height / 2) - (img.getIconHeight() / 2), this);
                break;
            case tervmentese:
                g.drawImage(img.getImage(), (Toolkit.getDefaultToolkit().getScreenSize().width / 2) - (img.getIconWidth() / 2), (Toolkit.getDefaultToolkit().getScreenSize().height / 2) - (img.getIconHeight() / 2), this);
                break;
            case oldalmenu:
                g.drawImage(img.getImage(), (this.getSize().width / 2) - (img.getIconWidth() / 2), (this.getHeight() / 2) - (img.getIconHeight() / 2), this);
                break;
            case controlpanelkulso:
                g.drawImage(img.getImage(), (this.getSize().width / 2) - (img.getIconWidth() / 2), (this.getHeight() / 2) - (img.getIconHeight() / 2), this);
                break;
            case controlpanelbelso:
                g.drawImage(img.getImage(), (this.getSize().width / 2) - (img.getIconWidth() / 2), (this.getHeight() / 2) - (img.getIconHeight() / 2), this);
                break;
            case setplannobjectback:
                g.drawImage(img.getImage(), (this.getSize().width / 2) - (img.getIconWidth() / 2), (this.getHeight() / 2) - (img.getIconHeight() / 2), this);
                break;

        }

    }

}
