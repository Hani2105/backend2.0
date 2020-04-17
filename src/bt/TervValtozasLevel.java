/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bt;

import java.awt.Point;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gabor_hanacsek
 */
public class TervValtozasLevel extends Thread {

    String tabla;
    MainWindow m;

    public TervValtozasLevel(MainWindow m) {
        this.m = m;

    }

    @Override
    public void run() {
        //lekérdezzük a címlistát
        //behuzzuk a cimlistat
        String query = "SELECT tc_tervvaltozas_cimlista.email FROM planningdb.tc_tervvaltozas_cimlista";
        PlanConnect pc = null;
        String cimlista = "";
        try {
            pc = new PlanConnect();
            pc.lekerdez(query);
            while (pc.rs.next()) {

                cimlista += pc.rs.getString(1) + ",\n";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                pc.kinyir();
            } catch (Exception e) {
            }

        }

        osszeallit();
        //a dátum beformázása
        Date date = new Date();
        String modifiedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date);
        Levelkuldes l = new Levelkuldes("Terv változás a(z) " + MainWindow.jTabbedPane1.getComponentAt(MainWindow.jTabbedPane1.getSelectedIndex()).getName() + " cellában! " + modifiedDate, tabla, cimlista, "BackendTervezo@sanmina.com", m);
        l.start();

    }

    public void osszeallit() {
        tabla = "<html><h2>Az aktuális terv a következő:</h2></br><table border = '1'><tr align=\"center\" bgcolor = '#999999'><th>PartNumber</th><th>JOB</th><th>WorkStation</th><th>Qty Terv / Tény</th><th>StartDate</th></tr>";
//kiszedjük a backendsheetet ami ki van jelolve
        BeSheet b = (BeSheet) MainWindow.jTabbedPane1.getComponentAt(MainWindow.jTabbedPane1.getSelectedIndex());
//kiszedjuk a po-kat egy listbe
        ArrayList<PlannObject> polist = new ArrayList<>();
        for (int i = 0; i < b.jPanel1.getComponentCount(); i++) {

            if (b.jPanel1.getComponent(i) instanceof PlannObject) {
                PlannObject po = (PlannObject) b.jPanel1.getComponent(i);
                polist.add(po);

            }

        }

//kell egy comparator amivel sorba tudjuk rakni startdátum szerint
        class sortByStartdate implements Comparator<PlannObject> {

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            @Override
            public int compare(PlannObject a, PlannObject b) {

                return a.getStartdate().compareTo(b.getStartdate());
            }
        }
        Collections.sort(polist, new sortByStartdate());
//tovább építjük a táblát
        for (int i = 0; i < polist.size(); i++) {

            tabla += "<tr align=\"center\"><td>" + polist.get(i).getPn() + "</td><td>" + polist.get(i).getJob() + "</td><td>" + polist.get(i).getWorkStation() + "</td><td>" + polist.get(i).getTerv() + " / " + polist.get(i).getTeny() + "</td><td>" + polist.get(i).getStartdate() + "</td></tr>";

        }
//lezárjuk a táblát
        tabla += "</table></html>";

    }

}
