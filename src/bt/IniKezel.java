/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bt;

import java.awt.Color;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import org.ini4j.Wini;

/**
 *
 * @author gabor_hanacsek
 */
public class IniKezel {

//megprobáljuk beolvasni az ini filet
    public void iniOlvas(MainWindow m) throws IOException {

        try {
            Wini ini = new Wini(new File("C:\\Users\\" + System.getProperty("user.name") + "\\" + "BT.ini"));
            Variables.plannObjectPopupColor = new Color(ini.get("colors", "plannObjectPopupColor", int.class));
            Variables.zold = new Color(ini.get("colors", "zold", int.class));
            Variables.piros = new Color(ini.get("colors", "piros", int.class));
            Variables.tabcolor = new Color(ini.get("colors", "tabcolor", int.class));
            Variables.selectedtabcolor = new Color(ini.get("colors", "selectedtabcolor", int.class));

//            int age = ini.get("owner", "age", int.class);
//            double height = ini.get("owner", "height", double.class);
//            String neve = ini.get("owner", "name");
//
//            System.out.print("Age: " + age + "\n");
//            System.out.print("Geight: " + height + "\n");
//            System.out.print("Server IP: " + neve + "\n");
            // To catch basically any error related to finding the file e.g
            // (The system cannot find the file specified)
        } catch (Exception e) {
//ha nem létezik akkor létrehozunk egyet
            //m.info.setVisible(true, "<html>Jelenleg nem létezik ini file!<br>Most létrehozunk egyet a következő helyen:<br>C:\\Users\\" + System.getProperty("user.name") + "\\" + "BT.ini</html>");
            JOptionPane.showMessageDialog(m,
                    "<html>Jelenleg nem létezik ini file!<br>Most létrehozunk egyet a következő helyen:<br>C:\\Users\\" + System.getProperty("user.name") + "\\" + "BT.ini</html>",
                    "Figyelem!",
                    JOptionPane.WARNING_MESSAGE);
            ArrayList<String> sorok = new ArrayList<>();
            sorok.add("[colors]");
            sorok.add("//a terv popupjának háttérszíne");
            sorok.add("plannObjectPopupColor=16777195");
            sorok.add("//a statisztikai piros és zöld");
            sorok.add("zold=11468631");
            sorok.add("piros=16544370");
            sorok.add("//a tabok színei");
            sorok.add("tabcolor=15775206");
            sorok.add("selectedtabcolor=7191285");
            sorok.add("//a panelek háttere");
            sorok.add("datapanelbackground=C:\\Users\\" + System.getProperty("user.name") + "\\" + "valami.jpg");

            //ki kell írni egy fileba az adatokat
            File file = new File("C:\\Users\\" + System.getProperty("user.name") + "\\" + "BT.ini");
            file.createNewFile();
            FileWriter writer = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(writer);

            for (int i = 0; i < sorok.size(); i++) {

                bw.write(sorok.get(i));
                bw.newLine();

            }

            bw.close();

        }

    }

}
