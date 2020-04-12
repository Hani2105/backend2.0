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

    public void sessionOlvas() {
        try {
            FileInputStream file = new FileInputStream(System.getProperty("user.home") + "\\BT\\session.dat");
            ObjectInputStream in = new ObjectInputStream(file);
            m.so = (SessionObject) in.readObject();
        } catch (Exception e) {
            System.out.println("Hiba a session beolvasásánál!");
            e.printStackTrace();
        }

    }

    public void cellakMentese() {
        ArrayList<String> cellak = new ArrayList<>();
        // Get the index of all the selected items
        int[] selectedIx = jList1.getSelectedIndices();
        // Get all the selected items using the indices
        for (int i = 0; i < selectedIx.length; i++) {
            cellak.add(jList1.getModel().getElementAt(selectedIx[i]));
        }
        m.so.setCellak(cellak);
        try {
            //kiirjuk az so-t
            this.sessionIr(m.so);
        } catch (IOException ex) {
            Logger.getLogger(SessionKezelo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void cellakKijelolese() {
        int[] selected = new int[m.so.getCellak().size()];
        int counter = 0;

        for (int i = 0; i < m.so.getCellak().size(); i++) {

            for (int l = 0; l < m.jList1.getModel().getSize(); l++) {

                if (m.so.getCellak().get(i).equals(m.jList1.getModel().getElementAt(l))) {

                    selected[counter] = l;
                    counter++;

                }
            }
        }
        
        m.jList1.setSelectedIndices(selected);

    }

}
