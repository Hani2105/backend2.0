/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bt;

import java.awt.Component;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author gabor_hanacsek
 */
public class JobStatusThread implements Runnable {

    BeSheet b;

    public JobStatusThread(BeSheet b) {
        this.b = b;
    }

    @Override
    public void run() {
//begyüjtjük az összes JOB számot ami a sheeten van
        Component[] components = b.jPanel1.getComponents();
        String jobok = "";

        for (int i = 0; i < components.length; i++) {

            if (components[i] instanceof PlannObject) {
                PlannObject po = (PlannObject) components[i];
                if (!jobok.contains(po.getJob())) {
                    jobok += po.getJob() + ";";
                }

            }

        }
        if (jobok.length() > 0) {
            jobok = jobok.substring(0, jobok.length() - 1);
            xmlfeldolg xxx = new xmlfeldolg();
            Object rowdata[][] = null;
            URL url = null;
            try {
                url = new URL("http://143.116.140.120/rest/request.php?page=planning_shop_order&shoporder=" + jobok + "&format=xml");
                ArrayList<String> lista = new ArrayList();

                String nodelist = "planning_shop_order";
                lista.add("Shop_Order_Number");
                lista.add("Part_Number");
                lista.add("Workstation");
                lista.add("Qty");
                lista.add("Unit_Status");
                lista.add("Order_Status");
                try {
                    rowdata = (Object[][]) xxx.xmlfeldolg(url, nodelist, lista);
                } catch (Exception e) {
//                    b.m.error.setVisible(true, "Nem sikerült a JOB adatok összegyűjtése!");
                    JOptionPane.showMessageDialog(b.getM(),
                            "Nem sikerült a JOB adatok összegyűjtése!",
                            "Lekérdezési hiba!",
                            JOptionPane.ERROR_MESSAGE);

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

//végigtekerjük a componenteket mégegyszer és az adatokat is hozzá
            outerloop:
            for (int i = 0; i < components.length; i++) {
                if (components[i] instanceof PlannObject) {
                    PlannObject po = (PlannObject) components[i];
                    po.setStat(Variables.status.NotExists);

                    for (int a = 0; a < rowdata.length; a++) {
//ha oda érünk, hogy egyezik a jobszám apo jobszámával                
                        if (rowdata[a][0].equals(po.getJob())) {
//ha találkozunk not released ikonnal
                            if (rowdata[a][5].equals("N")) {

                                po.setStat(Variables.status.NotReleased);
//hozzáadjuk a kommenthez, hogy nincs releasálva
                                String tooltiptext = po.getToolTipText() + "JOB not released in 42Q!";
                                po.setToolTipText(tooltiptext);
//ki is léphetünk a kövi po-ra
                                continue outerloop;
                            } //ha released a státusz akkor beállítjuk az ikont majd a tooltiphez hozzáfűzzük a mennyiségeket de itt két eset van, ha van benne skeleton és ha nincs                       
                            else if (rowdata[a][5].equals("R")) {
                                String ws = "Skeleton/Tp 15";
                                if (!rowdata[a][2].equals("")) {

                                    ws = rowdata[a][2].toString();
                                }

                                String tooltiptext = po.getToolTipText() + ws + ": " + rowdata[a][3] + " DB<br>";
                                po.setToolTipText(tooltiptext);
                                //ha a tooltip tartalmaz skeletont akkor s ikon, ha nem akkor r
                                if (po.getToolTipText().contains("Skeleton")) {
                                    po.setStat(Variables.status.Skeleton);

                                } else {
                                    po.setStat(Variables.status.Released);
                                }
                            } //ha se nem released se nem notreleased akkor c
                            else if (!rowdata[a][5].equals("N") && !rowdata[a][5].equals("R")) {
                                String tooltiptext = po.getToolTipText() + rowdata[a][2] + ": " + rowdata[a][3] + " DB<br>";
                                po.setToolTipText(tooltiptext);
                                po.setStat(Variables.status.Complete);

                            }

                        }

                    }

                }

            }

            b.repaint();

        }
    }

}
