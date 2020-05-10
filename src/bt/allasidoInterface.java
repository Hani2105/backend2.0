/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bt;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gabor_hanacsek
 */
public class allasidoInterface implements Runnable {

    private Variables.allasidoInterfaceParam p;
    BeSheet b;

    public allasidoInterface(Variables.allasidoInterfaceParam p, BeSheet b) {
        this.p = p;
        this.b = b;
    }

    @Override
    public void run() {
//ha le kell kerni az adatokat
        if (p.equals(Variables.allasidoInterfaceParam.leker)) {
            leker();
        } else if (p.equals(Variables.allasidoInterfaceParam.ment)) {

            ment();
        }

    }

    private void leker() {

        String tol = null, ig = null;

        //a datumok stringesitese
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        tol = dateFormat.format(MainWindow.jDateChooser1.getDate());
        ig = dateFormat.format(MainWindow.jDateChooser2.getDate());
//az anyaghiany lekerdezese
        String query = "select * from tc_anyaghiany where tol >= '" + tol + "' and ig <= '" + ig + "'";
        PlanConnect pc = null;
        try {

            pc = new PlanConnect();
            pc.lekerdez(query);
            while (pc.rs.next()) {

                //meg kell keresni azt a plannobjectet aminek a pktomige egyezik
                for (int i = 0; i < b.jPanel1.getComponentCount(); i++) {

                    if (b.jPanel1.getComponent(i) instanceof PlannObject) {
                        PlannObject po = (PlannObject) b.jPanel1.getComponent(i);
                        //megnezzuk, hogy a pktomig egyezik e  

                        if (po.getPktomig().equals(pc.rs.getString("pktomig"))) {

                            po.clearAnyaghianylista();
                            po.addAnyaghianylista(pc.rs.getString("pn"), pc.rs.getString("tol"), pc.rs.getString("ig"), pc.rs.getString("felelos"), pc.rs.getString("comment"));

                        }

                    }

                }

            }
//az allasidő lekerese
            query = "select * from downtimes_production where datefrom >= '" + tol + "' and dateto <= '" + ig + "'";
            pc.lekerdez(query);
            while (pc.rs.next()) {

                //meg kell keresni azt a plannobjectet aminek a pktomige egyezik
                for (int i = 0; i < b.jPanel1.getComponentCount(); i++) {

                    if (b.jPanel1.getComponent(i) instanceof PlannObject) {
                        PlannObject po = (PlannObject) b.jPanel1.getComponent(i);
                        //megnezzuk, hogy a pktomig egyezik e  

                        if (po.getPktomig().equals(pc.rs.getString("pktomig"))) {

                            po.clearAllasidoLista();
                            po.addAllasidoLista(pc.rs.getString("datefrom"), pc.rs.getString("dateto"), pc.rs.getString("downtimename"), pc.rs.getString("comments"));

                        }

                    }

                }

            }

        } catch (Exception e) {
        } finally {
            try {
                pc.kinyir();
            } catch (Exception e) {
            }
        }

    }

    private void ment() {
        String query = "insert into downtimes_production (line,datefrom,dateto,downtimename,comments,pktomig) values";
        String adatok = "";

        //bejarjuk a plannobjecteket es ha nagyobb az allasidos lista nullanal begyujtjuk az adatokat
        for (int i = 0; i < b.jPanel1.getComponentCount(); i++) {

            if (b.jPanel1.getComponent(i) instanceof PlannObject) {

                PlannObject po = (PlannObject) b.jPanel1.getComponent(i);
                if (po.getAllasidoLista().size() > 0) {

                    for (int m = 0; m < po.getAllasidoLista().size(); m++) {

                        adatok += "('" + b.getName() + "','" + po.getAllasidoLista().get(m).tol + "','" + po.getAllasidoLista().get(m).ig + "','" + po.getAllasidoLista().get(m).felelos + "','" + po.getAllasidoLista().get(m).komment + "',concat('" + po.getStartdate() + "', (select tc_becells.idtc_cells from tc_becells where tc_becells.cellname = '" + b.getName() + "'), (select tc_bestations.idtc_bestations from tc_bestations where tc_bestations.workstation = '" + po.getWorkStation() + "'), (select tc_bepns.idtc_bepns from tc_bepns where tc_bepns.partnumber = '" + po.getPn() + "'), '3', '" + po.getJob() + "')),";

                    }

                }

            }

        }
        if (adatok.length() > 0) {

            adatok = adatok.substring(0, adatok.length() - 1);
            PlanConnect pc = null;
            try {
                query = query + adatok;
                pc = new PlanConnect();
                pc.feltolt(query);

            } catch (Exception e) {
                e.printStackTrace();
                Starter.e.sendMessage(e);
            } finally {
                try {
                    pc.kinyir();
                } catch (Exception e) {
                }
            }

        }

    }

}
