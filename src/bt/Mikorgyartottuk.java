/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bt;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gabor_hanacsek
 */
public class Mikorgyartottuk implements Runnable {

    @Override
    public void run() {
//kellenek a pn ek a po-kbol, az összesből
        //ebben fogjuk tárolni
        ArrayList<String> polist = new ArrayList<>();
   

        for (int i = 0; i < MainWindow.jTabbedPane1.getTabCount(); i++) {

            BeSheet b = (BeSheet) MainWindow.jTabbedPane1.getComponentAt(i);
            for (int n = 0; n < b.jPanel1.getComponentCount(); n++) {

                if (b.jPanel1.getComponent(n) instanceof PlannObject) {

                    PlannObject po = (PlannObject) b.jPanel1.getComponent(n);
                    if (!polist.contains(po.getPn())) {

                        polist.add(po.getPn());
                    }

                }
            }
        }
//ha megvan az összes pn ünk a listában kell egy jó query, ha a lista nagyobb nulla
        if (polist.size() == 0) {
            return;
        }
        //összeállítjuk a listából a megfelelő formátumot
        String pnek = "";
        for (int i = 0; i < polist.size(); i++) {

            pnek += "'" + polist.get(i) + "',";
        }
        pnek = pnek.substring(0, pnek.length() - 1);

        String query = "select tc_bepns.partnumber as pn, datediff (now(), max(tc_terv.date)) as diff from tc_terv\n"
                + "left join tc_bepns on tc_bepns.idtc_bepns = tc_terv.idtc_bepns\n"
                + "where tc_terv.date < now() and tc_terv.active = 2 and tc_bepns.partnumber in(" + pnek + ")\n"
                + "and tc_terv.qty_teny > 0 \n"
                + "group by partnumber";

        PlanConnect pc = null;

        try {
            pc = new PlanConnect();
            pc.lekerdez(query);
            while (pc.rs.next()) {
//a pn nek megfelelő plannobikat besettelem
                for (int i = 0; i < MainWindow.jTabbedPane1.getTabCount(); i++) {

                    BeSheet b = (BeSheet) MainWindow.jTabbedPane1.getComponentAt(i);
                    for (int n = 0; n < b.jPanel1.getComponentCount(); n++) {

                        if (b.jPanel1.getComponent(n) instanceof PlannObject) {

                            PlannObject po = (PlannObject) b.jPanel1.getComponent(n);

                            if (po.getPn().equals(pc.rs.getString("pn"))) {

                                po.setMikorment(Integer.parseInt(pc.rs.getString("diff")));
                            }

                        }
                    }
                }

            }
        } catch (SQLException ex) {
           ex.printStackTrace();
           Starter.e.sendMessage(ex);
        } catch (ClassNotFoundException ex) {
           ex.printStackTrace();
           Starter.e.sendMessage(ex);
        } finally {
            try {
                pc.kinyir();

            } catch (Exception e) {
                e.printStackTrace();
                Starter.e.sendMessage(e);
            }
        }

    }

}
