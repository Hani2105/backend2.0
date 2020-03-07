/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bt;

import static bt.ControlPanel.jDateChooser1;
import static bt.ControlPanel.jDateChooser2;
import java.awt.Component;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import bt.MainWindow;
import java.awt.Dimension;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import javax.swing.JScrollBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.joda.time.Days;
import org.joda.time.LocalDate;

/**
 *
 * @author gabor_hanacsek
 */
public class BeSheet extends javax.swing.JPanel {

    /**
     * Creates new form BeSheet
     */
    //kell egy lista az adatoknak amiben a gyártásra vonatkozó adatokat tároljuk
    public ArrayList<String[]> adatok = new ArrayList<>();
    //a statisztika grafikonok kirajzolásához kellenek
    public double zold;
    public double piros;
    //a gyartasi ido adatai
    public double idozold;
    public double idopiros;

    //a tab neve lesz, és egyben a terv lekérdezéséhez kell
//    private String tabneve = "";
//    public SetPlannObjectData setPlannObjectData = new SetPlannObjectData(this);
    MainWindow m;

    public BeSheet(MainWindow m) {
        this.m = m;
        initComponents();
        //beállítjuk a grafikát a viewportokhoz
        jScrollPane1.setViewport(new ViewPortGraphics(Variables.viewports.datapanel));
        jScrollPane2.setViewport(new ViewPortGraphics(Variables.viewports.plannpanel));
        jScrollPane1.setViewportView(jPanel2);
        jScrollPane2.setViewportView(jPanel1);
        //getTerv(ControlPanel.jDateChooser1.getDate(), ControlPanel.jDateChooser2.getDate());
        scrollListener();

    }
//a gyártási darabszám grafikonához a számolás

    public void getGraphicon(int teny, int terv) {
        DecimalFormat df = new DecimalFormat("#.00");
        df.setRoundingMode(RoundingMode.UP);
        try {
            this.zold = 200 * Double.parseDouble(df.format((double) teny / (double) terv));
            if (this.zold > 200) {
                this.zold = 200;
            }
            this.piros = 200 - this.zold;
        } catch (Exception e) {
        }

    }

//a gyártási idő grafikonjához a számolás
    public void getGraphiconToProductTime(double gyartasiido, int teljesido) {
        DecimalFormat df = new DecimalFormat("#.00");
        df.setRoundingMode(RoundingMode.UP);
        try {
            this.idozold = 200 * Double.parseDouble(df.format((double) gyartasiido / (double) teljesido));
            if (this.idozold > 200) {
                this.idozold = 200;
            }
            this.idopiros = 200 - this.idozold;
        } catch (Exception e) {
        }

    }

    public void scrollListener() {

        jScrollPane2.getViewport().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {

                jScrollPane1.getHorizontalScrollBar().setModel(jScrollPane2.getHorizontalScrollBar().getModel());

            }
        });

    }

    public void getTerv(Date tol, Date ig) throws SQLException {
        Calendar c = Calendar.getInstance();
        //a datumot be kell formazni stringe
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String stol = dateFormat.format(tol);
        String sig = dateFormat.format(ig);
        //ki kell tenni a vt ket, annyit amennyi a két dátum között van *2 és beállítani a dátumukat
        int diff = Days.daysBetween(new LocalDate(jDateChooser1.getDate()), new LocalDate(jDateChooser2.getDate())).getDays();
        c.setTime(tol);
        int x = 0;

        for (int i = 0; i < diff; i++) {

            VerticalTimeline vt = new VerticalTimeline(this, dateFormat.format(c.getTime()) + " 06:00");
            jPanel2.add(vt);
            vt.setLocation(x, 0);
            x += 210;
            vt = new VerticalTimeline(this, dateFormat.format(c.getTime()) + " 18:00");
            jPanel2.add(vt);
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
                + "and tc_becells.cellname = '" + getName() + "'";
        PlanConnect pc = new PlanConnect();
        try {
            pc.lekerdez(query);
            while (pc.rs.next()) {
                String plannerkomment = getKommentFromText(pc.rs.getString("qty"));
                String komment = getKommentFromText(pc.rs.getString("qty_teny"));
                int qty = getIntFromText(pc.rs.getString("qty"));
                int qty_teny = getIntFromText(pc.rs.getString("qty_teny"));
                
                PlannObject po = new PlannObject(this, 200, 75, pc.rs.getString("partnumber"), pc.rs.getString("job"), pc.rs.getString("date"), qty, qty_teny, plannerkomment, komment,  pc.rs.getDouble("mernokiido"), pc.rs.getInt("wtf"), pc.rs.getString("workstation"), pc.rs.getDouble("ciklusido"), m);
                jPanel1.add(po);

            }

        } catch (SQLException ex) {
            Logger.getLogger(BeSheet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(BeSheet.class.getName()).log(Level.SEVERE, null, ex);
        }
//a darabszámok és gyártási idők összegyűjtése
        collectData();
//a job státusok összegyűjtése egy külön szálban h ne tartsunk fel semmit
        Thread t = new Thread(new JobStatusThread(this));
        t.start();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel2 = new UpperPanel(this);
        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        jPanel2.setBackground(new java.awt.Color(204, 255, 204));
        jPanel2.setOpaque(false);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1346, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 112, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(jPanel2);

        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        jPanel1.setBackground(new java.awt.Color(204, 255, 255));
        jPanel1.setOpaque(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1346, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 753, Short.MAX_VALUE)
        );

        jScrollPane2.setViewportView(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1140, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 475, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
//kiszedi a sztringől a számot, ez kell mert az adatbázisban egymás mellett vannak tárolva

    public int getIntFromText(String szoveg) {
        int szam = 0;
        for (int i = 0; i < szoveg.length(); i++) {

            try {

                szam = Integer.parseInt(szoveg.substring(0, i + 1));
            } catch (Exception e) {
                break;
            }

        }

        return szam;
    }
//kiszedi a sztringől a szöveget, ez kell mert az adatbázisban egymás mellett vannak tárolva

    public String getKommentFromText(String szoveg) {
        String komment = "";
        for (int i = 0; i < szoveg.length(); i++) {

            try {

                Integer.parseInt(szoveg.substring(0, i + 1));
            } catch (Exception e) {
                komment = szoveg.substring(i, szoveg.length()).trim();
                break;
            }

        }
        return komment;

    }

    public void collectData() {

//darabszám grafikon a szakban
//az összes timelinehez és összes állomáshoz kell ilyen adat        
//kinullázzuk az adatokat
        adatok.clear();
//felvesszuk a plannobkjekteket a panel1 ről
        Component[] pocomponents = this.jPanel1.getComponents();
//megkeressük a po kat
        nextpo:
        for (int i = 0; i < pocomponents.length; i++) {

            if (pocomponents[i] instanceof PlannObject) {
                PlannObject po = (PlannObject) pocomponents[i];
//meg kell nezni, hogy a po-ban levo allomas szerepel e mar az adatok kozott és a po starttime szerepel e az adatok kozott
                for (int a = 0; a < adatok.size(); a++) {

                    if ((po.getStartdate().trim().contains(adatok.get(a)[0].trim())) && (adatok.get(a)[1].trim().equals(po.getWorkStation().trim()))) {
                        //ha találunk az adatokban olyat ami egyezik astarttimeval és a ws el akko hozzá kell adni a tervet a tenyt es a gyartasi idot
//addterv
                        adatok.get(a)[2] = String.valueOf(Integer.parseInt(adatok.get(a)[2]) + po.getTerv());
//addteny
                        adatok.get(a)[3] = String.valueOf(Integer.parseInt(adatok.get(a)[3]) + po.getTeny());
//addgyartasiido
                        adatok.get(a)[4] = String.valueOf(Double.parseDouble(adatok.get(a)[4]) + po.getGyartasiido());
                        //mehetunk a következő po-ra
                        continue nextpo;
                    }

                }
                //ha ide eljutunk az azt jelenti, hogy nincs még ilyen paraméterekkel rendelkező elem a listánkban, bele kell tenni
                String[] adatok = new String[5];
                adatok[0] = po.getStartdate();
                adatok[1] = po.getWorkStation();
                adatok[2] = String.valueOf(po.getTerv());
                adatok[3] = String.valueOf(po.getTeny());
                adatok[4] = String.valueOf(po.getGyartasiido());
                this.adatok.add(adatok);

            }

        }
        repaint();
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JPanel jPanel1;
    public javax.swing.JPanel jPanel2;
    public javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
