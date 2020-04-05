/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bt;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author gabor_hanacsek
 */
public class TervMent {

    BeSheet b;
    String valtozasido = "";
    int valtozasjobpos = 0;
    String valtozasuser = "";

    public TervMent(BeSheet b) {
        this.b = b;
    }

    public void ment() {
//először lefuttatjuk a változás ellenőrt
        String valtozas = valtozasEllenor();
        if (valtozas.equals("planner")) {
            JOptionPane.showMessageDialog(b.getM(),
                    "<html>A tervedben planneri változtatás történt!<br>(" + valtozasuser + ")<br>A tervet újból le kell kérned mentés előtt!</html>",
                    "Változás a tervben!",
                    JOptionPane.WARNING_MESSAGE);
            System.out.println("Nem mentem..");
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

                System.out.println("Mentem..");

            } else if (n == 1) {

                System.out.println("Nem mentem..");
            }
        } //ha egyezik az idő
        else if (valtozas.equals("mehet")) {

            System.out.println("Mehet..");

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

}
