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
import javax.swing.JOptionPane;

/**
 *
 * @author gabor_hanacsek
 */
public class AllasidoInterface implements Runnable {

  
    BeSheet b;
 

    public AllasidoInterface(BeSheet b) {

        this.b = b;

    }

    @Override
    public void run() {
//ha le kell kerni az adatokat
     
            leker();
        

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

}
