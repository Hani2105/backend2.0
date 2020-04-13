/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bt;

import static bt.MainWindow.jList1;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gabor_hanacsek
 */
public class SessionKezelo {

    MainWindow m;

    public SessionKezelo(MainWindow m) {
        this.m = m;
    }

    public void sessionIr(SessionObject so) throws FileNotFoundException, IOException {
        FileOutputStream fos = new FileOutputStream(System.getProperty("user.home") + "\\BT\\session.dat");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(so);
        oos.close();
        fos.close();
    }

    public SessionObject sessionOlvas() {
        SessionObject so = null;
        try {
            FileInputStream file = new FileInputStream(System.getProperty("user.home") + "\\BT\\session.dat");
            ObjectInputStream in = new ObjectInputStream(file);
            so = (SessionObject) in.readObject();
        } catch (Exception e) {
            System.out.println("Hiba a session beolvasásánál!");
           
        }
        return so;
    }



}
