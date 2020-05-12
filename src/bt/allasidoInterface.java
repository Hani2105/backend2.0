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
    PlannObject po;

    public allasidoInterface(Variables.allasidoInterfaceParam p, BeSheet b) {
        this.p = p;
        this.b = b;

    }

    public allasidoInterface(Variables.allasidoInterfaceParam p, BeSheet b, PlannObject po) {
        this.p = p;
        this.b = b;
        this.po = po;

    }

    @Override
    public void run() {
//ha le kell kerni az adatokat
        if (p.equals(Variables.allasidoInterfaceParam.leker)) {
            leker();
        } else if (p.equals(Variables.allasidoInterfaceParam.ment)) {

            ment(po);
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

                            po.addAnyaghianylista(pc.rs.getString("pn"), pc.rs.getString("tol"), pc.rs.getString("ig"), pc.rs.getString("felelos"), pc.rs.getString("comment"), pc.rs.getString("idtc_anyaghiany"));
                            po.formatText();

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

                            po.addAllasidoLista(pc.rs.getString("datefrom"), pc.rs.getString("dateto"), pc.rs.getString("downtimename"), pc.rs.getString("comments"), pc.rs.getString("id"));
                            po.formatText();

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

    private void ment(PlannObject po) {
        String query = "insert ignore downtimes_production (line,datefrom,dateto,downtimename,comments,pktomig,id) values";
        String adatok = "";

        //bejarjuk a plannobjecteket es ha nagyobb az allasidos lista nullanal begyujtjuk az adatokat
        if (po.getAllasidoLista().size() > 0) {

            for (int m = 0; m < po.getAllasidoLista().size(); m++) {

                adatok += "('" + b.getName() + "','" + po.getAllasidoLista().get(m).tol + "','" + po.getAllasidoLista().get(m).ig + "','" + po.getAllasidoLista().get(m).felelos + "','" + po.getAllasidoLista().get(m).komment + "',concat('" + po.getStartdate() + "', (select tc_becells.idtc_cells from tc_becells where tc_becells.cellname = '" + b.getName() + "'), (select tc_bestations.idtc_bestations from tc_bestations where tc_bestations.workstation = '" + po.getWorkStation() + "'), (select tc_bepns.idtc_bepns from tc_bepns where tc_bepns.partnumber = '" + po.getPn() + "'), '3', '" + po.getJob() + "'),'" + po.getAllasidoLista().get(m).id + "'),";

            }

        }

        if (adatok.length() > 0) {

            adatok = adatok.substring(0, adatok.length() - 1);
            PlanConnect pc = null;
            try {
                query = query + adatok + "on duplicate key update datefrom = values (datefrom), dateto = values (dateto), downtimename = values (downtimename), comments = values(comments)";
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

        //az anyaghianyok adatait is mentjuk
        query = "insert ignore tc_anyaghiany (pktomig,pn,comment,felelos,tol,ig,cella, idtc_anyaghiany) values";
        adatok = "";

        for (int i = 0; i < b.jPanel1.getComponentCount(); i++) {

            if (po.getAnyaghianylista().size() > 0) {

                for (int m = 0; m < po.getAnyaghianylista().size(); m++) {

                    adatok += "(concat('" + po.getStartdate() + "', (select tc_becells.idtc_cells from tc_becells where tc_becells.cellname = '" + b.getName() + "'), (select tc_bestations.idtc_bestations from tc_bestations where tc_bestations.workstation = '" + po.getWorkStation() + "'), (select tc_bepns.idtc_bepns from tc_bepns where tc_bepns.partnumber = '" + po.getPn() + "'), '3', '" + po.getJob() + "'),'" + po.getAnyaghianylista().get(m).pn + "','" + po.getAnyaghianylista().get(m).komment + "','" + po.getAnyaghianylista().get(m).felelos + "','" + po.getAnyaghianylista().get(m).tol + "','" + po.getAnyaghianylista().get(m).ig + "','" + b.getName() + "','" + po.getAnyaghianylista().get(m).id + "'),";

                }

            }

        }

        if (adatok.length() > 0) {

            adatok = adatok.substring(0, adatok.length() - 1);
            PlanConnect pc = null;
            try {
                query = query + adatok + "on duplicate key update comment = values(comment), felelos = values(felelos), tol = values(tol), ig = values(ig), pn = values (pn)";
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
