/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bt;

import javax.swing.JOptionPane;

/**
 *
 * @author gabor_hanacsek
 */
public class Versioncheck implements Runnable {

    MainWindow m;

    public Versioncheck(MainWindow m) {
        this.m = m;
    }

    @Override
    public void run() {
        //beállítjuk a főablaknak a verzió számot
        m.setTitle("Version: " + String.valueOf(Variables.version));
        //lekérdezzük az adatbázisból az aktuális verziót és ha nem egyezik a programban megadottal akkor kilépünk
        PlanConnect pc = null;
        String query = "select * from tc_version order by idtc_version desc limit 1";
        try {
            pc = new PlanConnect();
            pc.lekerdez(query);
            while (pc.rs.next()) {
                //összehasonlítjuk a verziókat
                if (Variables.version != pc.rs.getDouble(2)) {
                    m.ls.setVisible(false);
                    //custom title, warning icon
                    JOptionPane.showMessageDialog(m,
                            "A program egy elavult verzióját használod! \n A program kilép!",
                            "Verzió hiba!",
                            JOptionPane.WARNING_MESSAGE);
                    System.exit(0);

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
