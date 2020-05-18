/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bt;

import java.util.Date;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JList;

/**
 *
 * @author gabor_hanacsek
 */
public class Visszaszamolo extends TimerTask {

    MainWindow m;
    int perc = 9;
    int masodperc = 60;//az első lekérdezés

    Visszaszamolo(MainWindow m) {
        this.m = m;
    }

    @Override
    public void run() {

//az első futás
        if (perc == 9 && masodperc == 60) {
            //lekérdezzük a jobstátuszokat
            Thread t = new Thread(new JobStatusThread());
            t.start();
        }
//ha nulla minden
        if (masodperc == 0 && perc == 0) {
//visszaállítunk mindent és csinálunk valami-------------------------------------
            masodperc = 59;
            perc = 9;
//lekérdezzük a jobstátuszokat
            Thread t = new Thread(new JobStatusThread());
            t.start();

        } //ha a perc nem nulla de a másodperc igen
        else if (perc > 0 && masodperc == 0) {
//visszaállítjuk a másodpercet és levonunk egyet a percből
            masodperc = 59;
            perc--;

        } //minden más esetben levonunk egyet a másodpercből
        else {

            masodperc--;
        }

//a jlabel szövege
        m.jLabel9.setText(String.format("%02d", perc) + " : " + String.format("%02d", masodperc));

    }

}
