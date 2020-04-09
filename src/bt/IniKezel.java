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

//megprobáljuk beolvasni az ini filet
    public void iniOlvas(MainWindow m) throws IOException {

        //könyvtár létrehozása
        new File(System.getProperty("user.home") + "\\BT").mkdir();
        //Picture könyvtár létrehozása
        new File(System.getProperty("user.home") + "\\BT\\Pictures").mkdir();
        //kiirjuk a filet

        BufferedImage img =  ImageIO.read(getClass().getResource("/pictures/3298158.jpg"));
        ImageIO.write(img, "jpg",new File(System.getProperty("user.home") + "\\BT\\Pictures\\valami.jpg"));
        


        try {
            Wini ini = new Wini(new File(System.getProperty("user.home") + "\\BT\\BT.ini"));
            Variables.plannObjectPopupColor = new Color(ini.get("colors", "plannObjectPopupColor", int.class));
            Variables.zold = new Color(ini.get("colors", "zold", int.class));
            Variables.piros = new Color(ini.get("colors", "piros", int.class));
            Variables.tabcolor = new Color(ini.get("colors", "tabcolor", int.class));
            Variables.selectedtabcolor = new Color(ini.get("colors", "selectedtabcolor", int.class));

        } catch (Exception e) {
//ha nem létezik akkor létrehozunk egyet
            //m.info.setVisible(true, "<html>Jelenleg nem létezik ini file!<br>Most létrehozunk egyet a következő helyen:<br>C:\\Users\\" + System.getProperty("user.name") + "\\" + "BT.ini</html>");
            JOptionPane.showMessageDialog(m,
                    "<html>Jelenleg nem létezik ini file!<br>Most létrehozunk egyet a következő helyen:<br>C:\\Users\\" + System.getProperty("user.home") + "\\BT\\" + "BT.ini</html>",
                    "Figyelem!",
                    JOptionPane.WARNING_MESSAGE);

            try {

                //ini file létrehozása
                File file = new File(System.getProperty("user.home") + "\\BT\\BT.ini");
                file.createNewFile();
                //ini file kezelése
                Wini ini = new Wini(new File(System.getProperty("user.home") + "\\BT\\BT.ini"));
                ini.put("colors", "plannObjectPopupColor", "16777195");
                ini.put("colors", "zold", "11468631");
                ini.put("colors", "piros", "16544370");
                ini.put("colors", "tabcolor", "15775206");
                ini.store();

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }

    }

}
