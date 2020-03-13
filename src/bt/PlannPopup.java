/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bt;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import static javax.swing.SwingConstants.CENTER;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author gabor_hanacsek
 */
public class PlannPopup extends JPopupMenu {

    PlannPopup(PlannObject p, MainWindow m) {

        this.setOpaque(true);
        this.setBackground(new Color(251, 255, 222));
        this.setBorder(BorderFactory.createTitledBorder(BorderFactory.createCompoundBorder(), "Menü", CENTER, 1));
//setup
        JMenuItem setupMenuItem = new JMenuItem("Beállítások", new javax.swing.ImageIcon(getClass().getResource("/pictures/setup.png")));
        setupMenuItem.setOpaque(true);
        setupMenuItem.setBackground(Variables.plannObjectPopupColor);
        setupMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                m.spo.setVisible(true, p, p.getLocation());
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        this.add(setupMenuItem);
//áttervez
        JMenuItem attervezMenuItem = new JMenuItem("Áttervez", new javax.swing.ImageIcon(getClass().getResource("/pictures/replann.png")));
        attervezMenuItem.setOpaque(true);
        attervezMenuItem.setBackground(Variables.plannObjectPopupColor);
        this.add(attervezMenuItem);
        attervezMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RePlann rp = new RePlann();
                rp.rePlannOne(p);
            }
        });

//összeset áttervez
        JMenuItem fullattervezMenuItem = new JMenuItem("Teljes áttervezés", new javax.swing.ImageIcon(getClass().getResource("/pictures/replann.png")));
        fullattervezMenuItem.setOpaque(true);
        fullattervezMenuItem.setBackground(Variables.plannObjectPopupColor);
        this.add(fullattervezMenuItem);
        fullattervezMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RePlann rp = new RePlann();
                rp.fullRePlann(p);
            }
        });
//SFDC
        JMenuItem Sfdc = new JMenuItem("SFDC lekérése", new javax.swing.ImageIcon(getClass().getResource("/pictures/sfdc.png")));
        Sfdc.setOpaque(true);
        Sfdc.setBackground(Variables.plannObjectPopupColor);
        this.add(Sfdc);
        Sfdc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               SFDC s = new SFDC(p);
                try {
                    s.sfdcLeker();
                } catch (MalformedURLException ex) {
                    Logger.getLogger(PlannPopup.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(PlannPopup.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ParserConfigurationException ex) {
                    Logger.getLogger(PlannPopup.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SAXException ex) {
                    Logger.getLogger(PlannPopup.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

}
