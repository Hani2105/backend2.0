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
import javax.swing.JOptionPane;
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
        org.joda.time.format.DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

        DateTime dt = null;
        String ig = "";
        try {
            dt = formatter.parseDateTime(tol);
            dt = dt.plusMinutes(719);
            ig = dt.toString(formatter);
            ig = ig.replace(" ", "%20");
            tol = tol.replace(" ", "%20");
        } catch (Exception e) {
//            p.getMainWindow().error.setVisible(true, "Hiba a dátum kiválasztásánál!");
            JOptionPane.showMessageDialog(p.getMainWindow(),
                    "<html>Hiba a dátum kiválasztásánál!</html>",
                    "Lekérdezési hiba!",
                    JOptionPane.ERROR_MESSAGE);

            p.getMainWindow().sfdchatter.setVisible(false);
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
//betesszük egy arraylistbe a po-kat
        ArrayList<PlannObject> polist = new ArrayList<>();
        for (int i = 0; i < components.length; i++) {

            if (components[i] instanceof PlannObject) {

                PlannObject po = (PlannObject) components[i];
                polist.add(po);
            }
        }
        for (int i = 0; i < polist.size(); i++) {
            PlannObject po = polist.get(i);
//egyezik e a kezdő dátum
            if (po.getStartdate().contains(tol.replace("%20", " "))) {

//mivel tobb jobban szerepelhet össze kell adni
                int osszteny = 0;
//megvizsgáljuk, hogy szerepel e az sfdc ben
//ha nem kell figyelni a job ot és a first passt sem
                if (!p.getMainWindow().jCheckBox1.isSelected() && !p.getMainWindow().jCheckBox2.isSelected()) {

                    for (int s = 0; s < sfdcadat.length; s++) {

                        if (po.getPn().equals(sfdcadat[s][1]) && String.valueOf(sfdcadat[s][0]).contains(po.getWorkStation())) {

                            osszteny += Integer.parseInt(String.valueOf(sfdcadat[s][4]));

                        }
                    }
                    i--;

                    po.setTeny(osszteny);
                    //kiszedjuk az osszes olyan po-t a listabol hol egyezik a pn és a ws, hogy tobbet ne irjunk mert beírtuk most az osszes adatot
                    for (int n = 0; n < polist.size(); n++) {

                        if (polist.get(n).getPn().equals(po.getPn()) && polist.get(n).getWorkStation().equals(po.getWorkStation())) {

                            polist.remove(n);
                            n--;
                        }
                    }

                }

//ha kell a job ot figyelni de a first passt nem
                if (p.getMainWindow().jCheckBox1.isSelected() && !p.getMainWindow().jCheckBox2.isSelected()) {

                    for (int s = 0; s < sfdcadat.length; s++) {

                        if (po.getPn().equals(sfdcadat[s][1]) && String.valueOf(sfdcadat[s][0]).contains(po.getWorkStation()) && po.getJob().equals(sfdcadat[s][3])) {

                            osszteny += Integer.parseInt(String.valueOf(sfdcadat[s][4]));

                        }
                    }
                    i--;
                    po.setTeny(osszteny);
                    //kiszedjuk az osszes olyan po-t a listabol hol egyezik a pn és a ws és a job!
                    for (int n = 0; n < polist.size(); n++) {

                        if (polist.get(n).getPn().equals(po.getPn()) && polist.get(n).getWorkStation().equals(po.getWorkStation()) && polist.get(n).getJob().equals(po.getJob())) {

                            polist.remove(n);
                            n--;
                        }
                    }

                }

//ha kell a job ot figyelni és a first passt is
                if (p.getMainWindow().jCheckBox1.isSelected() && p.getMainWindow().jCheckBox2.isSelected()) {

                    for (int s = 0; s < sfdcadat.length; s++) {

                        if (po.getPn().equals(sfdcadat[s][1]) && String.valueOf(sfdcadat[s][0]).contains(po.getWorkStation()) && po.getJob().equals(sfdcadat[s][3]) && sfdcadat[s][2].equals("1")) {

                            osszteny += Integer.parseInt(String.valueOf(sfdcadat[s][4]));

                        }
                    }
                    i--;
                    po.setTeny(osszteny);
                    //kiszedjuk az osszes olyan po-t a listabol hol egyezik a pn és a ws
                    for (int n = 0; n < polist.size(); n++) {

                        if (polist.get(n).getPn().equals(po.getPn()) && polist.get(n).getWorkStation().equals(po.getWorkStation()) && polist.get(n).getJob().equals(po.getJob())) {

                            polist.remove(n);
                            n--;
                        }
                    }

                }

//ha a job ot nem kell figyelni de a first passt igen
                if (!p.getMainWindow().jCheckBox1.isSelected() && p.getMainWindow().jCheckBox2.isSelected()) {

                    for (int s = 0; s < sfdcadat.length; s++) {

                        if (po.getPn().equals(sfdcadat[s][1]) && String.valueOf(sfdcadat[s][0]).contains(po.getWorkStation()) && sfdcadat[s][2].equals("1")) {

                            osszteny += Integer.parseInt(String.valueOf(sfdcadat[s][4]));

                        }
                    }
                    i--;
                    po.setTeny(osszteny);
                    //kiszedjuk az osszes olyan po-t a listabol hol egyezik a pn és a ws
                    for (int n = 0; n < polist.size(); n++) {

                        if (polist.get(n).getPn().equals(po.getPn()) && polist.get(n).getWorkStation().equals(po.getWorkStation())) {

                            polist.remove(n);
                            n--;
                        }
                    }

                }

            }

        }

//bejárjuk a polistet és ha még találunk olyanpokat ahol egyezik a pn és a ws akkor rákérdezünk, hogy hozzáadjuk e a tervhez és nem egyezik a startdate--------------------------------------------------
        for (int i = 0; i < polist.size(); i++) {

            PlannObject po = polist.get(i);
            //ha nem egyezik a startdate de a pn és ws igen
            if (!po.getStartdate().contains(tol.replace("%20", " "))) {
                //mivel tobb jobban szerepelhet össze kell adni
                int osszterv = 0;
//megvizsgáljuk, hogy szerepel e az sfdc ben
//ha nem kell figyelni a job ot és a first passt sem
                if (!p.getMainWindow().jCheckBox1.isSelected() && !p.getMainWindow().jCheckBox2.isSelected()) {

                    for (int s = 0; s < sfdcadat.length; s++) {

                        if (po.getPn().equals(sfdcadat[s][1]) && String.valueOf(sfdcadat[s][0]).contains(po.getWorkStation())) {

                            osszterv += Integer.parseInt(String.valueOf(sfdcadat[s][4]));

                        }
                    }

                    //kell hozzáadni egy uj plannobjectet de rakerdezunk, ha nagyobb mint nulla az oszterv
                    if (osszterv > 0) {
                        Object[] options = {"Igen, adjuk hozzá!", "Nem, ne adjuk hozzá!"};
                        int n = JOptionPane.showOptionDialog(po.getMainWindow(), "A " + po.getPn() + "-t hozzáadjuk a szakos tervhez?", "Új adat!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
                        if (n == 0) {
//letrehozunk egy uj po-t es beallitjuk az adatait majd hozzaadjuk a japnelhez
                            PlannObject ujpo = new PlannObject(po.getbackendSheet(), 200, 75, po.getPn(), po.getJob(), tol.replace("%20", " "), 0, osszterv, "Nem tervezett gyártás!", "", 0.0, 0, po.getWorkStation(), po.getCiklusido(), po.getMainWindow());
                            p.getbackendSheet().jPanel1.add(ujpo);
                            ujpo.setStat(po.getStat());
                            ujpo.getbackendSheet().osszerendez();

                            i--;

                        }
                    }

                    //kiszedjuk az osszes olyan po-t a listabol hol egyezik a pn és a ws
                    for (int o = 0; o < polist.size(); o++) {

                        if (polist.get(o).getPn().equals(po.getPn()) && polist.get(o).getWorkStation().equals(po.getWorkStation())) {

                            polist.remove(o);
                            o--;

                        }
                    }
                }

//ha kell a job ot figyelni de a first passt nem
                if (p.getMainWindow().jCheckBox1.isSelected() && !p.getMainWindow().jCheckBox2.isSelected()) {

                    for (int s = 0; s < sfdcadat.length; s++) {

                        if (po.getPn().equals(sfdcadat[s][1]) && String.valueOf(sfdcadat[s][0]).contains(po.getWorkStation()) && po.getJob().equals(sfdcadat[s][3])) {

                            osszterv += Integer.parseInt(String.valueOf(sfdcadat[s][4]));

                        }
                    }
                    //kell hozzáadni egy uj plannobjectet de rakerdezunk, ha nagyobb mint nulla az oszterv
                    if (osszterv > 0) {
                        Object[] options = {"Igen, adjuk hozzá!", "Nem, ne adjuk hozzá!"};
                        int n = JOptionPane.showOptionDialog(po.getMainWindow(), "A " + po.getPn() + "-t hozzáadjuk a szakos tervhez?", "Új adat!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
                        if (n == 0) {
//letrehozunk egy uj po-t es beallitjuk az adatait majd hozzaadjuk a japnelhez
                            PlannObject ujpo = new PlannObject(po.getbackendSheet(), 200, 75, po.getPn(), po.getJob(), tol.replace("%20", " "), 0, osszterv, "Nem tervezett gyártás!", "", 0.0, 0, po.getWorkStation(), po.getCiklusido(), po.getMainWindow());
                            p.getbackendSheet().jPanel1.add(ujpo);
                            ujpo.setStat(po.getStat());
                            ujpo.getbackendSheet().osszerendez();
                            i--;

                        }
                    }

                    //kiszedjuk az osszes olyan po-t a listabol hol egyezik a pn és a ws és a job
                    for (int o = 0; o < polist.size(); o++) {

                        if (polist.get(o).getPn().equals(po.getPn()) && polist.get(o).getWorkStation().equals(po.getWorkStation()) && polist.get(o).getJob().equals(po.getJob())) {

                            polist.remove(o);
                            o--;

                        }
                    }
                }

//ha kell a job ot figyelni és a first passt is
                if (p.getMainWindow().jCheckBox1.isSelected() && p.getMainWindow().jCheckBox2.isSelected()) {

                    for (int s = 0; s < sfdcadat.length; s++) {

                        if (po.getPn().equals(sfdcadat[s][1]) && String.valueOf(sfdcadat[s][0]).contains(po.getWorkStation()) && po.getJob().equals(sfdcadat[s][3]) && sfdcadat[s][2].equals("1")) {

                            osszterv += Integer.parseInt(String.valueOf(sfdcadat[s][4]));

                        }
                    }
                    //kell hozzáadni egy uj plannobjectet de rakerdezunk, ha nagyobb mint nulla az oszterv
                    if (osszterv > 0) {
                        Object[] options = {"Igen, adjuk hozzá!", "Nem, ne adjuk hozzá!"};
                        int n = JOptionPane.showOptionDialog(po.getMainWindow(), "A " + po.getPn() + "-t hozzáadjuk a szakos tervhez?", "Új adat!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
                        if (n == 0) {
//letrehozunk egy uj po-t es beallitjuk az adatait majd hozzaadjuk a japnelhez
                            PlannObject ujpo = new PlannObject(po.getbackendSheet(), 200, 75, po.getPn(), po.getJob(), tol.replace("%20", " "), 0, osszterv, "Nem tervezett gyártás!", "", 0.0, 0, po.getWorkStation(), po.getCiklusido(), po.getMainWindow());
                            p.getbackendSheet().jPanel1.add(ujpo);
                            ujpo.setStat(po.getStat());
                            ujpo.getbackendSheet().osszerendez();
                            i--;

                        }
                    }

                    //kiszedjuk az osszes olyan po-t a listabol hol egyezik a pn és a ws
                    for (int o = 0; o < polist.size(); o++) {

                        if (polist.get(o).getPn().equals(po.getPn()) && polist.get(o).getWorkStation().equals(po.getWorkStation()) && polist.get(o).getJob().equals(po.getJob())) {

                            polist.remove(o);
                            o--;

                        }
                    }
                }

//ha a job ot nem kell figyelni de a first passt igen
                if (!p.getMainWindow().jCheckBox1.isSelected() && p.getMainWindow().jCheckBox2.isSelected()) {

                    for (int s = 0; s < sfdcadat.length; s++) {

                        if (po.getPn().equals(sfdcadat[s][1]) && String.valueOf(sfdcadat[s][0]).contains(po.getWorkStation()) && sfdcadat[s][2].equals("1")) {

                            osszterv += Integer.parseInt(String.valueOf(sfdcadat[s][4]));

                        }
                    }
                    //kell hozzáadni egy uj plannobjectet de rakerdezunk, ha nagyobb mint nulla az oszterv
                    if (osszterv > 0) {
                        Object[] options = {"Igen, adjuk hozzá!", "Nem, ne adjuk hozzá!"};
                        int n = JOptionPane.showOptionDialog(po.getMainWindow(), "A " + po.getPn() + "-t hozzáadjuk a szakos tervhez?", "Új adat!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
                        if (n == 0) {
//letrehozunk egy uj po-t es beallitjuk az adatait majd hozzaadjuk a japnelhez
                            PlannObject ujpo = new PlannObject(po.getbackendSheet(), 200, 75, po.getPn(), po.getJob(), tol.replace("%20", " "), 0, osszterv, "Nem tervezett gyártás!", "", 0.0, 0, po.getWorkStation(), po.getCiklusido(), po.getMainWindow());
                            p.getbackendSheet().jPanel1.add(ujpo);
                            ujpo.setStat(po.getStat());
                            ujpo.getbackendSheet().osszerendez();
                            i--;

                        }
                    }

                    //kiszedjuk az osszes olyan po-t a listabol hol egyezik a pn és a ws
                    for (int o = 0; o < polist.size(); o++) {

                        if (polist.get(o).getPn().equals(po.getPn()) && polist.get(o).getWorkStation().equals(po.getWorkStation())) {

                            polist.remove(o);
                            o--;

                        }
                    }
                }
            }
        }

//frissitjuk az adatokat
        p.getbackendSheet().collectData();
//jogosultságok beállítása
        p.getMainWindow().j.kezel();
        try {
            p.getMainWindow().sfdchatter.setVisible(false);
        } catch (Exception e) {
            e.printStackTrace();
            Starter.e.sendMessage(e);
        }

    }

    @Override
    public void run() {
        try {
            sfdcLeker();
        } catch (Exception e) {
            e.printStackTrace();
            Starter.e.sendMessage(e);
        }
    }

}
