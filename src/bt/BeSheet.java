/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bt;

import java.awt.Component;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import bt.MainWindow;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JScrollBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableColumn;
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
    //a gyárthatósági adatokhoz kell
    public ArrayList<String[]> gyarthatosagiadatok = new ArrayList<>();
    public ArrayList<String> pnlist = new ArrayList<>();
    public ArrayList<String> wslist = new ArrayList<>();
    //a statisztika grafikonok kirajzolásához kellenek
    public double zold;
    public double piros;
    //a gyartasi ido adatai
    public double idozold;
    public double idopiros;
    //a műszakjelentés szövege legyen itt tárolva , ez amit a muszakvezeto ir
    public String muszakjelentes = "";
    //kell a vt ideje, hogy melyik adatokat kuldtuk el
    public String vtstartime = "";
    //ki mentett utoljara az oldalon
    private String utolsomodisito = "";
    //az utolso id a sheeten
    private String utolsotime = "";

    //a tab neve lesz, és egyben a terv lekérdezéséhez kell
//    private String tabneve = "";
//    public SetPlannObjectData setPlannObjectData = new SetPlannObjectData(this);
    MainWindow m;

    public BeSheet(MainWindow m, String neve) {
        this.m = m;
        initComponents();
        //a splitpane beállítása

        //beállítjuk a grafikát a viewportokhoz
        jScrollPane1.setViewport(new ViewPortGraphics(Variables.viewports.datapanel, this));
        jScrollPane2.setViewport(new ViewPortGraphics(Variables.viewports.plannpanel, this));
        jScrollPane1.setViewportView(jPanel2);
        jScrollPane2.setViewportView(jPanel1);
        this.m = m;
        //getTerv(ControlPanel.jDateChooser1.getDate(), ControlPanel.jDateChooser2.getDate());
        scrollListener();
//lekérjük a gyárthatósági adatokat, milyen pn ek milyen ws milyen ciklusidok udnak itt menni
        adatleker(neve);
//a vertical scrollbar erősségének beállítása
        jScrollPane2.getVerticalScrollBar().setUnitIncrement(16);
    }

    public String getUtolsotime() {
        return utolsotime;
    }

    public void setUtolsotime(String utolsotime) {
        this.utolsotime = utolsotime;
    }

    public String getUtolsomodisito() {
        return utolsomodisito;
    }

    public void setUtolsomodisito(String utolsomodisito) {
        this.utolsomodisito = utolsomodisito;
    }

    public MainWindow getM() {
        return m;
    }

    public void setM(MainWindow m) {
        this.m = m;
    }

    //a gyárthatósági adatok
    public void adatleker(String neve) {
        //a terv hozzáadásához kell

        gyarthatosagiadatok.clear();
        pnlist.clear();
        wslist.clear();
        PlanConnect pc = null;
        try {
            pc = new PlanConnect();
        } catch (SQLException ex) {
            Logger.getLogger(ControlPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {

//ki kell deríteni, hogy melyik tab adataira vagyunk kiváncsiak, a selected tab lesz az
//kell egy query ami összeszedi az ehhez a tabhoz tartozó pn eket, és állomásokat
            String query = "select tc_bepns.partnumber , tc_bestations.workstation ,tc_prodmatrix.ciklusido from tc_becells\n"
                    + "left join tc_prodmatrix on tc_prodmatrix.id_tc_becells = tc_becells.idtc_cells\n"
                    + "left join tc_bepns on tc_bepns.idtc_bepns = tc_prodmatrix.id_tc_bepns\n"
                    + "left join tc_bestations on tc_bestations.idtc_bestations = tc_prodmatrix.id_tc_bestations\n"
                    + "where tc_becells.cellname = '" + neve + "' and partnumber is not null and workstation is not null and tc_prodmatrix.pk is not null order by tc_bepns.partnumber asc,  tc_bestations.workstation asc";

            pc = new PlanConnect();
            pc.lekerdez(query);

            while (pc.rs.next()) {

                if (!pnlist.contains(pc.rs.getString(1))) {

                    pnlist.add(pc.rs.getString(1).trim());

                }

                if (!wslist.contains(pc.rs.getString(2))) {

                    wslist.add(pc.rs.getString(2).trim());

                }

                String[] adatok = new String[3];
                adatok[0] = pc.rs.getString(1).trim();
                adatok[1] = pc.rs.getString(2).trim();
                adatok[2] = pc.rs.getString(3).trim();
                this.gyarthatosagiadatok.add(adatok);

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            pc.kinyir();

        }

    }

    //a plannobjectek összerendezése
    public void osszerendez() {
//begyüjtjük a vertical lineokat
        Component[] vtcomponents = this.jPanel2.getComponents();
        Component[] pocomponents = this.jPanel1.getComponents();
//ha vt-t találunk
        for (int i = 0; i < vtcomponents.length; i++) {
            if (vtcomponents[i] instanceof VerticalTimeline) {
                VerticalTimeline vt = (VerticalTimeline) vtcomponents[i];
//csinálunk egy arrayt a po-knak amik ehhez a vt hez tartoznak
                ArrayList<PlannObject> polist = new ArrayList<>();
//az összes olyan po-t ami ehhez a vt hez tartozik betesszük ebbe a listába
                for (int c = 0; c < pocomponents.length; c++) {

                    if (pocomponents[c] instanceof PlannObject) {
                        PlannObject po = (PlannObject) pocomponents[c];
                        if (po.getStartdate().contains(vt.getVtstartdate())) {

                            polist.add(po);

                        }

                    }

                }

//feltöltöttük a polistunket a megfelelő po-kkal, most be kéne buborékolni őket a lokációjuk alapján
                class sortByLoc implements Comparator<PlannObject> {

                    @Override
                    public int compare(PlannObject a, PlannObject b) {
                        return a.getLocation().y - b.getLocation().y;
                    }

                }
//sorba rendezzük
                Collections.sort(polist, new sortByLoc());

//ha ez kész akkor relokáljuk őket ennek a sorrendnek megfelelően és beállítjuk a wtf et
                int uccsohely = 10;
                int wtf = 0;

                for (int c = 0; c < polist.size(); c++) {

                    polist.get(c).setLocation(vt.getLocation().x, uccsohely);
                    polist.get(c).setWtf(wtf);
                    uccsohely = polist.get(c).getLocation().y + polist.get(c).getHeight() + 5;
//beállítjuk az uj startdatet is
                    //resetStartTime();
                    wtf++;

                }

            }

        }

        //setScrollpanel();
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
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1152, Short.MAX_VALUE)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
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
