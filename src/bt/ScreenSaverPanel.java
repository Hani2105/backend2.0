/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bt;

import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JDialog;
import javax.swing.JPanel;

/**
 *
 * @author gabor_hanacsek
 */
public class ScreenSaverPanel extends JPanel {

    Variables.screensaver s;
    ScreenSaver ss;

    public ScreenSaverPanel(ScreenSaver ss) {
        this.ss = ss;

    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        switch (ss.getS()) {
            case sfdc:
                g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 45));
                g.drawImage(new javax.swing.ImageIcon(getClass().getResource("/pictures/sfdc_1.png")).getImage(), (this.getWidth() / 2) - 460, (this.getHeight() / 2) - 53, this);
                break;
            case tervmentese:
                g.drawImage(new javax.swing.ImageIcon(getClass().getResource("/pictures/bigsave.png")).getImage(), (this.getWidth() / 2) - 175, (this.getHeight() / 2) - 175, this);

        }

    }

}
