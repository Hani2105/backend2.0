/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bt;

import java.awt.Dimension;
import java.awt.Image;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
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

        if (m.jCheckBox3.isSelected()) {
            allCell();
        } else {

            singleCell();
        }

    }

    public void allCell() {

        //a megfelő dátum formátum a po starttimenak
        DateFormat poformatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //lecsekkoljuk, hogy van e valami kiválasztva a datechooserekben és, hogy a tol kisebb legyen mint az ig

        try {

            int diff = Days.daysBetween(new LocalDate(m.jDateChooser1.getDate()), new LocalDate(m.jDateChooser2.getDate())).getDays();

            if (diff <= 0) {
//                m.error.setVisible(true, "Nem jó dátumokat választottál ki!");
                JOptionPane.showMessageDialog(m,
                        "<html>Nem jó dátumot választottál ki!</html>",
                        "Lekérdezési hiba!",
                        JOptionPane.ERROR_MESSAGE);
                m.jProgressBar1.setString("Lekér!");
                return;

            }
        } catch (Exception e) {
//            m.error.setVisible(true, "Nem jó dátumokat választottál ki!");
            JOptionPane.showMessageDialog(m,
                    "<html>Nem jó dátumot választottál ki!</html>",
                    "Lekérdezési hiba!",
                    JOptionPane.ERROR_MESSAGE);
            m.jProgressBar1.setString("Lekér!");
            return;
        }
        //annyi besheetet adunk hozzá amennyi a control panel jlist2 ben van
        //        ArrayList<String> tabok = new ArrayList<>();
        m.jTabbedPane1.removeAll();
        //ki kell szedni a kijelölt elemeket a listából
        // Get the index of all the selected items
        int[] selectedIx = m.jList1.getSelectedIndices();
        // Get all the selected items using the indices
        String[] selectedItems = new String[selectedIx.length];
        for (int i = 0; i < selectedIx.length; i++) {
            selectedItems[i] = m.jList1.getModel().getElementAt(selectedIx[i]);
        }
        for (int i = 0; i < selectedItems.length; i++) {

            BeSheet b = new BeSheet(m, selectedItems[i]);
            b.setName(selectedItems[i]);
            Calendar c = Calendar.getInstance();
            //a datumot be kell formazni stringe
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String stol = dateFormat.format(m.jDateChooser1.getDate());
            String sig = dateFormat.format(m.jDateChooser2.getDate());
            //ki kell tenni a vt ket, annyit amennyi a két dátum között van *2 és beállítani a dátumukat
            int diff = Days.daysBetween(new LocalDate(m.jDateChooser1.getDate()), new LocalDate(m.jDateChooser2.getDate())).getDays();
            c.setTime(m.jDateChooser1.getDate());
            int x = 50;

            for (int n = 0; n < diff; n++) {

                VerticalTimeline vt = new VerticalTimeline(b, dateFormat.format(c.getTime()) + " 06:00");
                b.jPanel2.add(vt);
                vt.setLocation(x, 0);
                x += 210;
                vt = new VerticalTimeline(b, dateFormat.format(c.getTime()) + " 18:00");
                b.jPanel2.add(vt);
                vt.setLocation(x, 0);
//hozzáállítjuk a panelek méretét is
                b.jPanel1.setPreferredSize(new Dimension(x + 210, (int) b.jPanel1.getPreferredSize().getHeight()));
                b.jPanel2.setPreferredSize(new Dimension(x + 210, (int) b.jPanel2.getPreferredSize().getHeight()));
                x += 210;
                c.add(Calendar.DATE, 1);

            }
            //kell egy query ami lekérdezi az adatokat
            //a formattert átalakítjuk a megfelelő formátummá

//        tabneve = "UBT";
            String query = "SELECT tc_bepns.partnumber , tc_terv.job, tc_bestations.workstation,tc_terv.date,tc_terv.qty,tc_terv.qty_teny,tc_terv.mernokiido,tc_terv.wtf,tc_prodmatrix.ciklusido, tc_terv.pktomig, tc_terv.lathato FROM tc_terv\n"
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
                pc.rs.last();
                int poszam = pc.rs.getRow();
                int szamlalo = 1;
                pc.rs.beforeFirst();
                while (pc.rs.next()) {
                    m.jProgressBar1.setMaximum(poszam);
                    m.jProgressBar1.setValue(szamlalo);
                    m.jProgressBar1.setString(szamlalo + "/" + poszam);
                    String plannerkomment = b.getKommentFromText(pc.rs.getString("qty"));
                    String komment = b.getKommentFromText(pc.rs.getString("qty_teny"));
                    int qty = b.getIntFromText(pc.rs.getString("qty"));
                    int qty_teny = b.getIntFromText(pc.rs.getString("qty_teny"));

                    PlannObject po = new PlannObject(b, 200, 75, pc.rs.getString("partnumber"), pc.rs.getString("job"), poformatter.format(pc.rs.getTimestamp("date")), qty, qty_teny, plannerkomment, komment, pc.rs.getDouble("mernokiido"), pc.rs.getInt("wtf"), pc.rs.getString("workstation"), pc.rs.getDouble("ciklusido"), b.getM(), pc.rs.getInt("lathato"));
                    po.setPktomig(pc.rs.getString("pktomig"));
                    b.jPanel1.add(po);
                    szamlalo++;

                }
                //le kell kérdezni a sheethez azt is, hogy ki és mikor módosította a tervet utoljára, és ezt eltesszük a sheet adataiba
                query = "select valtozasok.ido from valtozasok where valtozasok.cella = '" + b.getName() + "'";
                pc.lekerdez(query);
                while (pc.rs.next()) {
                    b.setUtolsotime(pc.rs.getString("ido"));
                }

            } catch (Exception e) {
                e.printStackTrace();
                Starter.e.sendMessage(e);
            } finally {
                try {
                    pc.kinyir();
                } catch (Exception e) {
                }
            }

//a darabszámok és gyártási idők összegyűjtése
            b.collectData();
            m.jTabbedPane1.add(b, b.getName());
            //elindítjuk az állásidők legyűjtését
            Thread allaidok = new Thread(new AllasidoInterfac(b));
            allaidok.start();

        }
        m.jProgressBar1.setValue(0);
        m.jProgressBar1.setString("Lekér!");
//elindítjuk a mikor gyártottuk futását
        Thread mikor = new Thread(new Mikorgyartottuk());
        mikor.start();

//lefuttatjuk a jogosultság kezelőt, hogy az uj plannobjectek is rendben legyenek
        m.j.kezel();
//kiszedjük a pipát, hogy többször ne kérjük le az összesre
        m.jCheckBox3.setSelected(false);
//elindítjuk a timert ami a jobstátusokat kerdezi le

//megrobáljuk updatelni
        try {
            Variables.timer.cancel();
            Variables.timer = new Timer();
            Variables.timer.scheduleAtFixedRate(new Visszaszamolo(m), 0, 1000);
        } catch (Exception e) {
            Variables.timer.scheduleAtFixedRate(new Visszaszamolo(m), 0, 1000);
        }
    }

    private void singleCell() {
        //a megfelő dátum formátum a po starttimenak
        DateFormat poformatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        BeSheet b = (BeSheet) m.jTabbedPane1.getComponentAt(m.jTabbedPane1.getSelectedIndex());

        //kitoroljuk az osszes po-t rola 
        b.jPanel1.removeAll();
        //kitoroljuk az osszes vt-t rola

        b.jPanel2.removeAll();

        Calendar c = Calendar.getInstance();
        //a datumot be kell formazni stringe
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String stol = dateFormat.format(m.jDateChooser1.getDate());
        String sig = dateFormat.format(m.jDateChooser2.getDate());
        //ki kell tenni a vt ket, annyit amennyi a két dátum között van *2 és beállítani a dátumukat
        int diff = Days.daysBetween(new LocalDate(m.jDateChooser1.getDate()), new LocalDate(m.jDateChooser2.getDate())).getDays();
        c.setTime(m.jDateChooser1.getDate());
        int x = 50;

        for (int n = 0; n < diff; n++) {

            VerticalTimeline vt = new VerticalTimeline(b, dateFormat.format(c.getTime()) + " 06:00");
            b.jPanel2.add(vt);
            vt.setLocation(x, 0);
            x += 210;
            vt = new VerticalTimeline(b, dateFormat.format(c.getTime()) + " 18:00");
            b.jPanel2.add(vt);
            vt.setLocation(x, 0);
//hozzáállítjuk a panelek méretét is
            b.jPanel1.setPreferredSize(new Dimension(x + 210, (int) b.jPanel1.getPreferredSize().getHeight()));
            b.jPanel2.setPreferredSize(new Dimension(x + 210, (int) b.jPanel2.getPreferredSize().getHeight()));
            x += 210;
            c.add(Calendar.DATE, 1);

        }
        //kell egy query ami lekérdezi az adatokat
        //a formattert átalakítjuk a megfelelő formátummá

//        tabneve = "UBT";
        String query = "SELECT distinct tc_bepns.partnumber , tc_terv.job, tc_bestations.workstation,tc_terv.date,tc_terv.qty,tc_terv.qty_teny,tc_terv.mernokiido,tc_terv.wtf,tc_prodmatrix.ciklusido, tc_terv.pktomig, tc_terv.lathato FROM tc_terv\n"
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
            pc.rs.last();
            int poszam = pc.rs.getRow();
            int szamlalo = 1;
            pc.rs.beforeFirst();
            while (pc.rs.next()) {
                m.jProgressBar1.setMaximum(poszam);
                m.jProgressBar1.setValue(szamlalo);
                m.jProgressBar1.setString(szamlalo + "/" + poszam);
                String plannerkomment = b.getKommentFromText(pc.rs.getString("qty"));
                String komment = b.getKommentFromText(pc.rs.getString("qty_teny"));
                int qty = b.getIntFromText(pc.rs.getString("qty"));
                int qty_teny = b.getIntFromText(pc.rs.getString("qty_teny"));

                PlannObject po = new PlannObject(b, 200, 75, pc.rs.getString("partnumber"), pc.rs.getString("job"), poformatter.format(pc.rs.getTimestamp("date")), qty, qty_teny, plannerkomment, komment, pc.rs.getDouble("mernokiido"), pc.rs.getInt("wtf"), pc.rs.getString("workstation"), pc.rs.getDouble("ciklusido"), b.getM(), pc.rs.getInt("lathato"));
                po.setPktomig(pc.rs.getString("pktomig"));
                b.jPanel1.add(po);
                szamlalo++;

            }
            //le kell kérdezni a sheethez azt is, hogy ki és mikor módosította a tervet utoljára, és ezt eltesszük a sheet adataiba
            query = "select valtozasok.ido from valtozasok where valtozasok.cella = '" + b.getName() + "'";
            pc.lekerdez(query);
            while (pc.rs.next()) {
                b.setUtolsotime(pc.rs.getString("ido"));
            }

        } catch (Exception e) {
            e.printStackTrace();
            Starter.e.sendMessage(e);
        } finally {
            try {
                pc.kinyir();
            } catch (Exception e) {
                e.printStackTrace();
                Starter.e.sendMessage(e);
            }
        }

//a darabszámok és gyártási idők összegyűjtése
        b.collectData();
//        m.jTabbedPane1.add(b, b.getName());
        m.jProgressBar1.setValue(0);
        m.jProgressBar1.setString("Lekér!");
//elindítjuk a mikor gyártottuk futását
        Thread mikor = new Thread(new Mikorgyartottuk());
        mikor.start();
//elindítjuk az állásidők legyűjtését
        Thread allaidok = new Thread(new AllasidoInterfac(b));
        allaidok.start();
//lefuttatjuk a jogosultság kezelőt, hogy az uj plannobjectek is rendben legyenek
        m.j.kezel();
//kiszedjük a pipát, hogy többször ne kérjük le az összesre
        m.jCheckBox3.setSelected(false);
//elindítjuk a timert ami a jobstátusokat kerdezi le
//elindítjuk a timert ami a jobstátusokat kerdezi le

//megrobáljuk updatelni
//megrobáljuk updatelni
        try {
            Variables.timer.cancel();
            Variables.timer = new Timer();
            Variables.timer.scheduleAtFixedRate(new Visszaszamolo(m), 0, 1000);
        } catch (Exception e) {
            Variables.timer.scheduleAtFixedRate(new Visszaszamolo(m), 0, 1000);
        }

    }

}
