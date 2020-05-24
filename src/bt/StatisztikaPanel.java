/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bt;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

/**
 *
 * @author gabor_hanacsek
 */
public class StatisztikaPanel extends JPanel{

    Statisztika s;

    public StatisztikaPanel(Statisztika s) {
        this.s = s;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        if (s.getStat() == Variables.statisztika.heti) {
            g2.drawString("Heti kimutatás", 0, 10);
            g2.setColor(Color.red);
            g2.fill3DRect(23, 20, 50, 100, true);
//az osszter/teny szoveg
            g2.setColor(Color.black);
            g2.drawString("Össz terv / tény", 10, 135);
            
            
//            g.setColor(Color.red);
//            g.drawOval(100, 50, 30, 30);
        }

    }

}
