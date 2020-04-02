/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bt;

import java.awt.Component;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.xml.sax.SAXException;

/**
 *
 * @author gabor_hanacsek
 */
public class SFDC implements Runnable {

    PlannObject p;

    public SFDC(PlannObject po) {
        this.p = po;
    }

    public void sfdcLeker() throws MalformedURLException, IOException, ParserConfigurationException, SAXException {

//ki kell talalni, hogy mettol meddig kell lekerni az adatot
//a tol a plannobject startimeja lesz
//az ig pedig +12 óra
        String tol = p.getStartdate();
// kitalaljuk a meddiget
        org.joda.time.format.DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");

        DateTime dt = null;
        String ig = "";
        try {
            dt = formatter.parseDateTime(tol);
            dt = dt.plusMinutes(719);
            ig = dt.toString(formatter);
            ig = ig.replace(" ", "%20");
            tol = tol.replace(" ", "%20");
        } catch (Exception e) {
            p.getMainWindow().error.setVisible(true, "Hiba a dátum kiválasztásánál!");
            p.getMainWindow().ss.setVisible(false);
        }
//az url beállítása
        xmlfeldolg xxx = new xmlfeldolg();
        URL url = new URL("http://143.116.140.120/rest/request.php?page=planning_realisation&product=&starttime=" + tol + "&endtime=" + ig + "&format=xml");
        ArrayList<String> lista = new ArrayList();
//az xml adatinak beállítása        
        String nodelist = "planning_realisation";
        lista.add("Workstation");
        lista.add("Part_Number");
        lista.add("Pass");
        lista.add("Shop_Order_Number");
        lista.add("SUMPassQty");
        lista.add("move_qty");
        lista.add("manual_move_qty");
//lekérjük és átalakítjuk tömbbé
        Object sfdcadat[][] = (Object[][]) xxx.xmlfeldolg(url, nodelist, lista);
//bejárjuk a plann objecteket és a lekért adatok alapjánk kitöltjük a megvalósulást ha egyeznek
        Component[] components = p.getbackendSheet().jPanel1.getComponents();
        for (int i = 0; i < components.length; i++) {

            if (components[i] instanceof PlannObject) {

                PlannObject po = (PlannObject) components[i];
//egyezik e a kezdő dátum
                if (po.getStartdate().contains(tol.replace("%20", " "))) {
//mivel tobb jobban szerepelhet össze kell adni
                    int osszterv = 0;
//megvizsgáljuk, hogy szerepel e az sfdc ben
//ha nem kell figyelni a job ot és a first passt sem
                    if (!p.getMainWindow().cp.jCheckBox1.isSelected() && !p.getMainWindow().cp.jCheckBox2.isSelected()) {

                        for (int s = 0; s < sfdcadat.length; s++) {

                            if (po.getPn().equals(sfdcadat[s][1]) && String.valueOf(sfdcadat[s][0]).contains(po.getWorkStation())) {

                                osszterv += Integer.parseInt(String.valueOf(sfdcadat[s][4]));

                            }
                        }

                        po.setTeny(osszterv);

                    }

//ha kell a job ot figyelni de a first passt nem
                    if (p.getMainWindow().cp.jCheckBox1.isSelected() && !p.getMainWindow().cp.jCheckBox2.isSelected()) {

                        for (int s = 0; s < sfdcadat.length; s++) {

                            if (po.getPn().equals(sfdcadat[s][1]) && String.valueOf(sfdcadat[s][0]).contains(po.getWorkStation()) && po.getJob().equals(sfdcadat[s][3])) {

                                osszterv += Integer.parseInt(String.valueOf(sfdcadat[s][4]));

                            }
                        }

                        po.setTeny(osszterv);

                    }

//ha kell a job ot figyelni és a first passt is
                    if (p.getMainWindow().cp.jCheckBox1.isSelected() && p.getMainWindow().cp.jCheckBox2.isSelected()) {

                        for (int s = 0; s < sfdcadat.length; s++) {

                            if (po.getPn().equals(sfdcadat[s][1]) && String.valueOf(sfdcadat[s][0]).contains(po.getWorkStation()) && po.getJob().equals(sfdcadat[s][3]) && sfdcadat[s][2].equals("1")) {

                                osszterv += Integer.parseInt(String.valueOf(sfdcadat[s][4]));

                            }
                        }

                        po.setTeny(osszterv);

                    }

//ha a job ot nem kell figyelni de a first passt igen
                    if (!p.getMainWindow().cp.jCheckBox1.isSelected() && p.getMainWindow().cp.jCheckBox2.isSelected()) {

                        for (int s = 0; s < sfdcadat.length; s++) {

                            if (po.getPn().equals(sfdcadat[s][1]) && String.valueOf(sfdcadat[s][0]).contains(po.getWorkStation()) && sfdcadat[s][2].equals("1")) {

                                osszterv += Integer.parseInt(String.valueOf(sfdcadat[s][4]));

                            }
                        }

                        po.setTeny(osszterv);

                    }

                }
            }

        }
//frissitjuk az adatokat
        p.getbackendSheet().collectData();
        try {
            p.getMainWindow().ss.setVisible(false);
        } catch (Exception e) {
        }

    }

    @Override
    public void run() {
        try {
            sfdcLeker();
        } catch (IOException ex) {
            Logger.getLogger(SFDC.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(SFDC.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(SFDC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
