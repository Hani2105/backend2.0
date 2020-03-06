/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bt;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.text.DecimalFormat;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author gabor_hanacsek
 */
public class UpperPanel extends JPanel {

    BeSheet b;

    UpperPanel(BeSheet b) {

        this.b = b;

    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        for (int i = 0; i < b.jPanel2.getComponentCount(); i++) {

            if (b.jPanel2.getComponent(i) instanceof VerticalTimeline) {
                VerticalTimeline vt = (VerticalTimeline) b.jPanel2.getComponent(i);

//megkeressük az adatok között a vt-t
                int y = 40;
                for (int a = 0; a < b.adatok.size(); a++) {
                    if (b.adatok.get(a)[0].contains(vt.getVtstartdate())) {
                        g.setFont(new Font("default", Font.BOLD, 12));
                        g.drawString("Workstation: " + b.adatok.get(a)[1], vt.getLocation().x + 2, vt.getLocation().y + y);
                        y += 15;
//kiszámoljuk az adatokat a rajzoláshoz
                        b.getGraphicon(Integer.parseInt(b.adatok.get(a)[3]), Integer.parseInt(b.adatok.get(a)[2]));

//zöldre állítjuk a színt és rajzoljuk a zöldet
                        g.setColor(Variables.zold);
                        g.fillRect(vt.getLocation().x + 2, vt.getLocation().y + y - 11, (int) b.zold, 13);
//pirosra állítjuk és rajzoljuk a pirosat
                        g.setColor(Variables.piros);
                        g.fillRect(vt.getLocation().x + 2 + (int) b.zold, vt.getLocation().y + y - 11, (int) b.piros, 13);
                        g.setColor(Color.BLACK);
//kiirjuk az adatot betűvel
                        g.drawString("Terv/Tény: " + b.adatok.get(a)[2] + " / " + b.adatok.get(a)[3], vt.getLocation().x + 2, vt.getLocation().y + y);
                        y += 15;
//kiszámoljuk az adatokat a gyártási idő rajzoláshoz
                        b.getGraphiconToProductTime(Double.parseDouble(b.adatok.get(a)[4]), 12);
                        //zöldre állítjuk a színt és rajzoljuk a zöldet
                        g.setColor(Variables.zold);
                        g.fillRect(vt.getLocation().x + 2, vt.getLocation().y + y - 11, (int) b.idozold, 13);
//pirosra állítjuk és rajzoljuk a pirosat
                        g.setColor(Variables.piros);
                        g.fillRect(vt.getLocation().x + 2 + (int) b.idozold, vt.getLocation().y + y - 11, (int) b.idopiros, 13);
                        g.setColor(Color.BLACK);
                        g.drawString("Össz. gyártási idő: " + new DecimalFormat("#.##").format(Double.parseDouble(b.adatok.get(a)[4])), vt.getLocation().x + 2, vt.getLocation().y + y);
                        y += 25;
//beállítjuk a jpanel preferred sizejet ha az nagyobb mint a jelenlegi
                        if (y + 10 > b.jPanel2.getPreferredSize().height) {
                            b.jPanel2.setPreferredSize(new Dimension(b.jPanel2.getPreferredSize().width, y + 10));
                        }

                    }

                }
            }

        }

    }

}
