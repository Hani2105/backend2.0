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
import javax.swing.JMenu;
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
        JMenuItem setupMenuItem = new JMenuItem("Adatok módosítása", new javax.swing.ImageIcon(getClass().getResource("/pictures/setup.png")));
        setupMenuItem.setOpaque(true);
        setupMenuItem.setBackground(Variables.plannObjectPopupColor);
        setupMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                m.spo.setVisible(true, p);
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        this.add(setupMenuItem);
 //submenu       
        JMenu attervez = new JMenu("Áttervez");
        this.add(attervez);
        
        
//áttervez
        JMenuItem attervezMenuItem = new JMenuItem("Előre tervez", new javax.swing.ImageIcon(getClass().getResource("/pictures/replann.png")));
        attervezMenuItem.setOpaque(true);
        attervezMenuItem.setBackground(Variables.plannObjectPopupColor);
        attervez.add(attervezMenuItem);
        attervezMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RePlann rp = new RePlann();
                rp.rePlannOne(p);
            }
        });

//összeset áttervez
        JMenuItem fullattervezMenuItem = new JMenuItem("Minden elmaradást előre tervez", new javax.swing.ImageIcon(getClass().getResource("/pictures/replann.png")));
        fullattervezMenuItem.setOpaque(true);
        fullattervezMenuItem.setBackground(Variables.plannObjectPopupColor);
        attervez.add(fullattervezMenuItem);
        fullattervezMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RePlann rp = new RePlann();
                rp.fullRePlann(p);
            }
        });

//előregyártás behúzása
        JMenuItem eloregyartasMenuItem = new JMenuItem("Előregyártás behúzása", new javax.swing.ImageIcon(getClass().getResource("/pictures/replann.png")));
        eloregyartasMenuItem.setOpaque(true);
        eloregyartasMenuItem.setBackground(Variables.plannObjectPopupColor);
        attervez.add(eloregyartasMenuItem);
        eloregyartasMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RePlann rp = new RePlann();
                rp.eloreGyartas(p);
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
                Thread t = new Thread(new SFDC(p));
                t.start();
                p.getMainWindow().ss.setVisible(true);

            }
        });

 //terv törlése
        JMenuItem Delete = new JMenuItem("Terv törlése", new javax.swing.ImageIcon(getClass().getResource("/pictures/delete.png")));
        Delete.setOpaque(true);
        Delete.setBackground(Variables.plannObjectPopupColor);
        this.add(Delete);
        Delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //csináljunk valamit
                p.getbackendSheet().jPanel1.remove(p);
                p.getbackendSheet().jPanel1.revalidate();
                p.getbackendSheet().jPanel1.repaint();

            }
        });
        
//terv másolása
        JMenuItem Copy = new JMenuItem("Terv másolása", new javax.swing.ImageIcon(getClass().getResource("/pictures/copy.png")));
        Copy.setOpaque(true);
        Copy.setBackground(Variables.plannObjectPopupColor);
        this.add(Copy);
        Copy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //csináljunk valamit
               PlannObject po = new PlannObject(p.getbackendSheet(), 200, 75, p.getPn(), p.getJob(), "", p.getTerv(), p.getTeny(), p.getPlannerkomment(),p.getKomment(), p.getEngineer(), p.getWtf(), p.getWorkStation(), p.getCiklusido(), m);
               po.setStat(p.getStat());
               p.getbackendSheet().jPanel1.add(po);
               p.getbackendSheet().jPanel1.revalidate();
               p.getbackendSheet().jPanel1.repaint();

            }
        });
        

    }

}
