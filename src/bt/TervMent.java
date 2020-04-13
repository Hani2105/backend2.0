/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bt;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author gabor_hanacsek
 */
public class TervMent implements Runnable {

    BeSheet b;
    String valtozasido = "";
    int valtozasjobpos = 0;
    String valtozasuser = "";

    public TervMent(BeSheet b) {
        this.b = b;
    }
//itt ellenorizzuk, hogy menthetunk e es ha igen akkor lefuttatjuk a eaktivalast, a mentest majd az adatok visszaírását a sheetre

    private void ment() {
//először lefuttatjuk a változás ellenőrt
        String valtozas = valtozasEllenor();
        if (valtozas.equals("planner")) {
            JOptionPane.showMessageDialog(b.getM(),
                    "<html>A tervedben planneri változtatás történt!<br>(" + valtozasuser + ")<br>A tervet újból le kell kérned mentés előtt!</html>",
                    "Változás a tervben!",
                    JOptionPane.WARNING_MESSAGE);

            b.getM().menteshatter.setVisible(false);
            return;
        } else if (valtozas.equals("muszakvez")) {

            //Custom button text
            Object[] options = {"Igen, mentem!",
                "Nem, megszakítom!"};
            int n = JOptionPane.showOptionDialog(b.getM(),
                    "<html>A tervedben műszakvezetői változtatás történt!<br>(" + valtozasuser + ")<br>Ha folytatod, az ő módosításai elvesznek!</html>",
                    "Változás a tervben!",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[1]);
            //ha folytatom a mentést
            if (n == 0) {

                //deaktiváljuk a régi tervet
                deActivate();
                mentes();
                //visszakérdezzük és letároljuk az adatokat, hogy mi mentettünk utoljára
                valtozasFrissito();

//ha nem mentem
            } else if (n == 1) {
                b.getM().menteshatter.setVisible(false);
                return;
            }
        } //ha egyezik az idő, menthetem
        else if (valtozas.equals("mehet")) {

            //deaktiváljuk az eddigi tervet
            deActivate();
            mentes();
            //visszakérdezzük és letároljuk az adatokat, hogy mi mentettünk utoljára
            valtozasFrissito();

        }

    }
    //deactiváljuk az aktuális tervet a tól, ig időközön ami le van kérdezve

    private void deActivate() {
//kell egy query a 
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String query = "update tc_terv set active = CASE when tc_terv.active = 2 then 1 when tc_terv.active = 1 then 0 end where tc_terv.active in (2,1) and tc_terv.date between '" + dateFormat.format(b.getM().jDateChooser1.getDate()) + "' and '" + dateFormat.format(b.getM().jDateChooser2.getDate()) + "' and tc_terv.idtc_becells = (select tc_becells.idtc_cells from tc_becells where tc_becells.cellname ='" + b.getName() + "')";
        PlanConnect pc = null;
        try {
            pc = new PlanConnect();
            pc.feltolt(query);
        } catch (SQLException ex) {
            Logger.getLogger(TervMent.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                pc.kinyir();
            } catch (Exception e) {
            }
        }

    }

//ez szedi össze az adatokat és írja az adatbázisba
    private void mentes() {

        StringBuffer adatok = new StringBuffer();
        StringBuffer query = new StringBuffer("insert into tc_terv (date, idtc_becells, idtc_bestations, idtc_bepns,qty,wtf,active, tt, user, job, pktomig, qty_teny, mernokiido) values ");
        for (int i = 0; i < b.jPanel1.getComponentCount(); i++) {
            if (b.jPanel1.getComponent(i) instanceof PlannObject) {
                PlannObject po = (PlannObject) b.jPanel1.getComponent(i);
                adatok.append("('" + po.getStartdate() + "',(select tc_becells.idtc_cells from tc_becells where tc_becells.cellname = '" + b.getName() + "'), (select tc_bestations.idtc_bestations from tc_bestations where tc_bestations.workstation = '" + po.getWorkStation() + "'), (select tc_bepns.idtc_bepns from tc_bepns where tc_bepns.partnumber = '" + po.getPn() + "'),'" + po.getTerv() + "','" + po.getWtf() + "', '2', '3','" + Variables.user + "','" + po.getJob() + "', concat('" + po.getStartdate() + "', (select tc_becells.idtc_cells from tc_becells where tc_becells.cellname = '" + b.getName() + "'), (select tc_bestations.idtc_bestations from tc_bestations where tc_bestations.workstation = '" + po.getWorkStation() + "'), (select tc_bepns.idtc_bepns from tc_bepns where tc_bepns.partnumber = '" + po.getPn() + "'), '3', '" + po.getJob() + "'),'" + po.getTeny() + "','" + po.getEngineer() + "'),");
            }

        }

        adatok.setLength(adatok.length() - 1);
        query.append(adatok);
        query.append("on duplicate key update qty = values(qty), qty_teny = values(qty_teny), wtf = values(wtf), user = values(user), active = (2)");
        PlanConnect pc = null;
        try {
            pc = new PlanConnect();
            pc.feltolt(query.toString());
            b.getM().menteshatter.setVisible(false);
            JOptionPane.showMessageDialog(b.getM(),
                    "Sikeres mentés!");
//ha planner van bejelentkezve megkerdezzuk, hogy kuldjunk e levelet a valtozasrol
            if (Variables.jogosultsag == 1) {
                //Custom button text
                Object[] options = {"Igen, küldjünk!",
                    "Nem, ne küldjünk!"};
                int n = JOptionPane.showOptionDialog(b.getM(),
                        "<html>Küldjünk levelet a terv változásról?</html>",
                        "Terv változás!",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[1]);

                if (n == 0) {

                    new TervValtozasLevel(b.getM()).start();
                }

            }

        } catch (SQLException ex) {
            Logger.getLogger(TervMent.class.getName()).log(Level.SEVERE, null, ex);
            //custom title, error icon
            JOptionPane.showMessageDialog(b.getM(),
                    "<html>A mentés nem sikerült!<br>" + ex.getMessage() + "<html>",
                    "Feltöltési hiba!",
                    JOptionPane.ERROR_MESSAGE);
        } finally {

            try {
                pc.kinyir();
            } catch (Exception e) {
            }
        }

    }

    //frissíti a backendsheeten a változás figyelő adatait
    private void valtozasFrissito() {
//ekérdezzük a változás tábláól az időt és azt meg az usert frissitjuk a backend sheeten
        String query = "select valtozasok.ido from valtozasok where valtozasok.cella = '" + b.getName() + "'";
        PlanConnect pc = null;

        try {
            pc = new PlanConnect();
            pc.lekerdez(query);
            while (pc.rs.next()) {
                b.setUtolsotime(pc.rs.getString("ido"));
                b.setUtolsomodisito(Variables.user);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TervMent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TervMent.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                pc.kinyir();
            } catch (Exception e) {
            }
        }

    }

//ez ellenőrzi, hogy változott e a terv az utolsó lekérés óta
    private String valtozasEllenor() {
        String ret = "";
//lekérdezzük a változás táblát a cellára nézve
        String query = "select valtozasok.ido , valtozasok.jobpos , valtozasok.user from valtozasok where valtozasok.cella = '" + b.getName() + "'";
        PlanConnect pc = null;

        try {
            pc = new PlanConnect();
            pc.lekerdez(query);
//összevetjük a változás idejét a tároltal
            while (pc.rs.next()) {

                valtozasido = pc.rs.getString("ido");
                valtozasjobpos = pc.rs.getInt("jobpos");
                valtozasuser = pc.rs.getString("user");
            }

        } catch (SQLException ex) {
            Logger.getLogger(TervMent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TervMent.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            try {
                pc.kinyir();
            } catch (Exception e) {
            }
        }

//itt összehasonlítjuk az időket
//ha nem egyezik az idő és planner vagy master a változtató akkor ujból le kell kérni mindenképpen
        if (!b.getUtolsotime().equals(valtozasido) && (valtozasjobpos == 1 || valtozasjobpos == 2 || valtozasjobpos == 5 || valtozasjobpos == 9)) {

            //custom title, warning icon
            ret = "planner";
            return ret;
        } //ha egyezik az idő
        else if (b.getUtolsotime().equals(valtozasido)) {

            ret = "mehet";
            return ret;
        } //ha van változás de nem planneri
        else if (!b.getUtolsotime().equals(valtozasido) && (valtozasjobpos == 6 || valtozasjobpos == 7)) {
            ret = "muszakvez";
        }

        return ret;
    }

    @Override
    public void run() {
        ment();
    }

}
