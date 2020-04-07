/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bt;

import static bt.ControlPanel.jDateChooser1;
import static bt.ControlPanel.jDateChooser2;
import java.awt.Image;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import org.joda.time.Days;
import org.joda.time.LocalDate;

/**
 *
 * @author gabor_hanacsek
 */
public class TervLeker implements Runnable {
    
    MainWindow m;
    
    public TervLeker(MainWindow m) {
        
        this.m = m;
    }
    
    @Override
    public void run() {
        //lecsekkoljuk, hogy va e valami kiválasztva a datechooserekben és, hogy a tol kisebb legyen mint az ig

        try {
            
            int diff = Days.daysBetween(new LocalDate(jDateChooser1.getDate()), new LocalDate(jDateChooser2.getDate())).getDays();
            
            if (diff <= 0) {
//                m.error.setVisible(true, "Nem jó dátumokat választottál ki!");
                JOptionPane.showMessageDialog(m,
                        "<html>Nem jó dátumot választottál ki!</html>",
                        "Lekérdezési hiba!",
                        JOptionPane.ERROR_MESSAGE);
                
                return;
                
            }
        } catch (Exception e) {
//            m.error.setVisible(true, "Nem jó dátumokat választottál ki!");
            JOptionPane.showMessageDialog(m,
                    "<html>Nem jó dátumot választottál ki!</html>",
                    "Lekérdezési hiba!",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        //annyi besheetet adunk hozzá amennyi a control panel jlist2 ben van
        //        ArrayList<String> tabok = new ArrayList<>();
        m.jTabbedPane1.removeAll();
        for (int i = 0; i < ControlPanel.jList2.getModel().getSize(); i++) {
            
            BeSheet b = new BeSheet(m, ControlPanel.jList2.getModel().getElementAt(i));
            b.setName(ControlPanel.jList2.getModel().getElementAt(i));
            Calendar c = Calendar.getInstance();
            //a datumot be kell formazni stringe
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String stol = dateFormat.format(ControlPanel.jDateChooser1.getDate());
            String sig = dateFormat.format(ControlPanel.jDateChooser2.getDate());
            //ki kell tenni a vt ket, annyit amennyi a két dátum között van *2 és beállítani a dátumukat
            int diff = Days.daysBetween(new LocalDate(ControlPanel.jDateChooser1.getDate()), new LocalDate(ControlPanel.jDateChooser2.getDate())).getDays();
            c.setTime(ControlPanel.jDateChooser1.getDate());
            int x = 50;
            
            for (int n = 0; n < diff; n++) {
                
                VerticalTimeline vt = new VerticalTimeline(b, dateFormat.format(c.getTime()) + " 06:00");
                b.jPanel2.add(vt);
                vt.setLocation(x, 0);
                x += 210;
                vt = new VerticalTimeline(b, dateFormat.format(c.getTime()) + " 18:00");
                b.jPanel2.add(vt);
                vt.setLocation(x, 0);
                x += 210;
                c.add(Calendar.DATE, 1);
                
            }
            //kell egy query ami lekérdezi az adatokat
//        tabneve = "UBT";
            String query = "SELECT tc_bepns.partnumber , tc_terv.job, tc_bestations.workstation,tc_terv.date,tc_terv.qty,tc_terv.qty_teny,tc_terv.mernokiido,tc_terv.wtf,tc_prodmatrix.ciklusido FROM tc_terv\n"
                    + "left join tc_bepns on tc_bepns.idtc_bepns = tc_terv.idtc_bepns\n"
                    + "left join tc_bestations on tc_bestations.idtc_bestations = tc_terv.idtc_bestations\n"
                    + "left join tc_becells on tc_becells.idtc_cells = tc_terv.idtc_becells\n"
                    + "left join tc_prodmatrix on tc_prodmatrix.id_tc_bepns = tc_terv.idtc_bepns\n"
                    + "where tc_terv.active = 2 \n"
                    + "and tc_prodmatrix.id_tc_bepns = tc_terv.idtc_bepns \n"
                    + "and tc_prodmatrix.id_tc_bestations = tc_terv.idtc_bestations \n"
                    + "and tc_terv.date between '" + stol + "' and '" + sig + "'\n"
                    + "and tc_prodmatrix.id_tc_becells = tc_terv.idtc_becells \n"
                    + "and tc_becells.cellname = '" + b.getName() + "'\n" 
                    + "order by wtf asc";
            PlanConnect pc = null;
            try {
                pc = new PlanConnect();
                pc.lekerdez(query);
                while (pc.rs.next()) {
                    String plannerkomment = b.getKommentFromText(pc.rs.getString("qty"));
                    String komment = b.getKommentFromText(pc.rs.getString("qty_teny"));
                    int qty = b.getIntFromText(pc.rs.getString("qty"));
                    int qty_teny = b.getIntFromText(pc.rs.getString("qty_teny"));
                    
                    PlannObject po = new PlannObject(b, 200, 75, pc.rs.getString("partnumber"), pc.rs.getString("job"), pc.rs.getString("date"), qty, qty_teny, plannerkomment, komment, pc.rs.getDouble("mernokiido"), pc.rs.getInt("wtf"), pc.rs.getString("workstation"), pc.rs.getDouble("ciklusido"), b.getM());
                    b.jPanel1.add(po);
                    
                }
                //le kell kérdezni a sheethez azt is, hogy ki és mikor módosította a tervet utoljára, és ezt eltesszük a sheet adataiba
                query = "select valtozasok.ido from valtozasok where valtozasok.cella = '" + b.getName() + "'";
                pc.lekerdez(query);
                while (pc.rs.next()) {
                    b.setUtolsotime(pc.rs.getString("ido"));
                }
                
            } catch (Exception e) {
            } finally {
                
                pc.kinyir();
            }

//a darabszámok és gyártási idők összegyűjtése
            b.collectData();
//a job státusok összegyűjtése egy külön szálban h ne tartsunk fel semmit
            Thread t = new Thread(new JobStatusThread(b));
            t.start();
            m.jTabbedPane1.add(b, b.getName());
            
        }
//elindítjuk a mikor gyártottuk futását
        Thread mikor = new Thread(new Mikorgyartottuk());
        mikor.start();
//visszaállítjuk a gombot semmire
        ControlPanel.jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pictures/blocksstatic.png")));
    }
    
}
