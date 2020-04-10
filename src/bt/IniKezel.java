/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bt;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import org.ini4j.Wini;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import javax.swing.ImageIcon;

/**
 *
 * @author gabor_hanacsek
 */
public class IniKezel {

    private Wini ini;
//megprobáljuk beolvasni az ini filet

    public void iniOlvas() throws IOException {

        //könyvtár létrehozása
        new File(System.getProperty("user.home") + "\\BT").mkdir();
        //Picture könyvtár létrehozása
        new File(System.getProperty("user.home") + "\\BT\\Pictures").mkdir();
        //képek beállítása
        setPictures();
        //ini file beállítása
        try {
            ini = new Wini(new File(System.getProperty("user.home") + "\\BT\\BT.ini"));
            setVariables();

        } catch (Exception e) {
//ha nem létezik akkor létrehozunk egyet
            System.out.println("Ini file létrehozása..");

            try {

//ini file létrehozása
                createIni();
//akkor most vissza is kell olvasni
                setVariables();

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }

    }

    private void createIni() throws IOException {
        File file = new File(System.getProperty("user.home") + "\\BT\\BT.ini");
        file.createNewFile();
        //ini file kezelése
        ini = new Wini(new File(System.getProperty("user.home") + "\\BT\\BT.ini"));
        ini.put("colors", "plannObjectPopupColor", new Color(250, 250, 172).getRGB());
        ini.put("colors", "zold", new Color(120, 227, 133).getRGB());
        ini.put("colors", "piros", new Color(252, 73, 94).getRGB());
        ini.put("colors", "tabcolor", new Color(217, 219, 219).getRGB());
        ini.put("colors", "selectedtabcolor", new Color(50, 137, 168).getRGB());
        ini.store();

    }

    private void setVariables() {
        Variables.plannObjectPopupColor = new Color(ini.get("colors", "plannObjectPopupColor", int.class));
        Variables.zold = new Color(ini.get("colors", "zold", int.class));
        Variables.piros = new Color(ini.get("colors", "piros", int.class));
        Variables.tabcolor = new Color(ini.get("colors", "tabcolor", int.class));
        Variables.selectedtabcolor = new Color(ini.get("colors", "selectedtabcolor", int.class));

    }

    private void setPictures() throws IOException {

        //kiirjuk a filet,ha nem tudjuk beolvasni
        BufferedImage img = null;
        //főpanelháttér beállítása
        try {
            img = ImageIO.read(new File(System.getProperty("user.home") + "\\BT\\Pictures\\fopanelhatter.jpg"));
        } catch (Exception e) {
            System.out.println("Főpanel háttér létrehozása..");
            img = ImageIO.read(getClass().getResource("/pictures/fopanelhatter.jpg"));
            ImageIO.write(img, "jpg", new File(System.getProperty("user.home") + "\\BT\\Pictures\\fopanelhatter.jpg"));
        }
        //adatpanel beállítása
        try {
            img = ImageIO.read(new File(System.getProperty("user.home") + "\\BT\\Pictures\\adatpanelhatter.jpg"));
        } catch (Exception e) {
            System.out.println("Adatpanel háttér létrehozása..");
            img = ImageIO.read(getClass().getResource("/pictures/fopanelhatter.jpg"));
            ImageIO.write(img, "jpg", new File(System.getProperty("user.home") + "\\BT\\Pictures\\adatpanelhatter.jpg"));
        }

        //menübár beállítása
        try {
            img = ImageIO.read(new File(System.getProperty("user.home") + "\\BT\\Pictures\\menubar.jpg"));
        } catch (Exception e) {
            System.out.println("Menübár háttér létrehozása..");
            img = ImageIO.read(getClass().getResource("/pictures/fopanelhatter.jpg"));
            ImageIO.write(img, "jpg", new File(System.getProperty("user.home") + "\\BT\\Pictures\\menubar.jpg"));
        }

        //sfdc beállítása
        try {
            img = ImageIO.read(new File(System.getProperty("user.home") + "\\BT\\Pictures\\sfdc_1.png"));
        } catch (Exception e) {
            System.out.println("SFDC háttér létrehozása..");
            img = ImageIO.read(getClass().getResource("/pictures/sfdc_1.png"));
            ImageIO.write(img, "png", new File(System.getProperty("user.home") + "\\BT\\Pictures\\sfdc_1.png"));
        }

        //mentés beállítása
        try {
            img = ImageIO.read(new File(System.getProperty("user.home") + "\\BT\\Pictures\\bigsave.png"));
        } catch (Exception e) {
            System.out.println("Mentés háttér létrehozása..");
            img = ImageIO.read(getClass().getResource("/pictures/bigsave.png"));
            ImageIO.write(img, "png", new File(System.getProperty("user.home") + "\\BT\\Pictures\\bigsave.png"));
        }

        //plannobject beállítása
        try {
            img = ImageIO.read(new File(System.getProperty("user.home") + "\\BT\\Pictures\\poback.jpg"));
        } catch (Exception e) {
            System.out.println("Plannobject háttér létrehozása..");
            img = ImageIO.read(getClass().getResource("/pictures/poback.jpg"));
            ImageIO.write(img, "jpg", new File(System.getProperty("user.home") + "\\BT\\Pictures\\poback.jpg"));
        }

        //plannobject selected
        try {
            img = ImageIO.read(new File(System.getProperty("user.home") + "\\BT\\Pictures\\poselected.jpg"));
        } catch (Exception e) {
            System.out.println("Plannobject selected háttér létrehozása..");
            img = ImageIO.read(getClass().getResource("/pictures/csutortok.jpg"));
            ImageIO.write(img, "jpg", new File(System.getProperty("user.home") + "\\BT\\Pictures\\poselected.jpg"));
        }
        //plannobject nostarttime
        try {
            img = ImageIO.read(new File(System.getProperty("user.home") + "\\BT\\Pictures\\nostarttime.jpg"));
        } catch (Exception e) {
            System.out.println("Plannobject nostarttime háttér létrehozása..");
            img = ImageIO.read(getClass().getResource("/pictures/nostarttime.jpg"));
            ImageIO.write(img, "jpg", new File(System.getProperty("user.home") + "\\BT\\Pictures\\nostarttime.jpg"));
        }

        //oldalmenü háttér
        try {
            img = ImageIO.read(new File(System.getProperty("user.home") + "\\BT\\Pictures\\oldalmenuhatter.jpg"));
        } catch (Exception e) {
            System.out.println("Odalmenü háttér létrehozása..");
            img = ImageIO.read(getClass().getResource("/pictures/fopanelhatter.jpg"));
            ImageIO.write(img, "jpg", new File(System.getProperty("user.home") + "\\BT\\Pictures\\oldalmenuhatter.jpg"));
        }
        
         //controlpanel kulso
        try {
            img = ImageIO.read(new File(System.getProperty("user.home") + "\\BT\\Pictures\\controlpanelkulso.jpg"));
        } catch (Exception e) {
            System.out.println("Controlpanel külső létrehozása..");
            img = ImageIO.read(getClass().getResource("/pictures/fopanelhatter.jpg"));
            ImageIO.write(img, "jpg", new File(System.getProperty("user.home") + "\\BT\\Pictures\\controlpanelkulso.jpg"));
        }
        
         //controlpanel belso
        try {
            img = ImageIO.read(new File(System.getProperty("user.home") + "\\BT\\Pictures\\controlpanelbelso.jpg"));
        } catch (Exception e) {
            System.out.println("Controlpanel belső létrehozása..");
            img = ImageIO.read(getClass().getResource("/pictures/controlpanelbelso.jpg"));
            ImageIO.write(img, "jpg", new File(System.getProperty("user.home") + "\\BT\\Pictures\\controlpanelbelso.jpg"));
        }
        
          //setplannobjectback
        try {
            img = ImageIO.read(new File(System.getProperty("user.home") + "\\BT\\Pictures\\setplannobjectback.jpg"));
        } catch (Exception e) {
            System.out.println("SetPlannobject background létrehozása..");
            img = ImageIO.read(getClass().getResource("/pictures/controlpanelbelso.jpg"));
            ImageIO.write(img, "jpg", new File(System.getProperty("user.home") + "\\BT\\Pictures\\setplannobjectback.jpg"));
        }

    }

}
