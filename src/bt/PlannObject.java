/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bt;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPopupMenu;
import javax.swing.ToolTipManager;

/**
 *
 * @author gabor_hanacsek
 */
public class PlannObject extends JLabel {

    private Variables.status stat = Variables.status.NotExists;
    public String neve = "";
    private int mousepozx = 0;
    private int mousepozy = 0;
    private String pn = "";
    private String job = "";
    private int terv = 0;
    private int teny = 0;
    private double dbzold = 0.00;
    private double dbpiros = 0.00;
    private String labeltext = "";
    private String tooltiptext = "";
    private double engineer = 0.00;
    private String plannerkomment = "";
    private String komment = "";
    private double idozold = 0.00;
    private double idopiros = 0.00;
    //gyártási idő órában!
    private double tervezettido = 0.00;
    //ez a tenylegesen megvalosult gyartasi ido. teny*ciklusido
    private double gyartasiido = 0.00;
    private String starttime = "";
    //waterfall
    private int wtf = 0;
    private String workStation = "";
    private BeSheet backendSheet;
    private ImageIcon img;
    private ImageIcon selectedimage;
    private ImageIcon nostartimeimage;
    private MainWindow m;
    private double ciklusido = 0.00;
    private boolean selected = false;
//az egyedi azonosító
    private String pktomig = "";
//mikor gyartottuk utoljara
    private int mikorment = -1;
//az anyaghianyok listaja
    private ArrayList<AnyagHiany> anyaghianylista = new ArrayList<>();
//az állásidők listája
    private ArrayList<AllasidoLista> allasidolista = new ArrayList<>();
//a teljesülés változója
    private boolean teljesult = false;

//construct
    public PlannObject(BeSheet b, int hossz, int magassag, String pn, String job, String startdate, int terv, int teny, String plannerkomment, String komment, double mernoki, int wtf, String workstation, double ciklusido, MainWindow m) {
        this.backendSheet = b;
        this.m = m;
        this.ciklusido = ciklusido;
//szélesség magasság beállítása
        this.setSize(hossz, magassag);

//átlátszatlanság beállítása
        this.setOpaque(true);
//icon és szöveg távolság beállítása
        this.setIconTextGap(0);
//border beállítások
        javax.swing.border.Border border = BorderFactory.createLineBorder(Color.BLUE, 2);
        this.setBorder(border);

//tooltip beállítások
        ToolTipManager.sharedInstance().setInitialDelay(500);
        ToolTipManager.sharedInstance().setDismissDelay(5000);

//ikon beállítása
//        setMainIcon(Variables.status.NotReleased);
//egér motion hozzáadása
        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {

                if (e.getComponent() instanceof JLabel) {
//a plannobject mozgatása/ ha planner
                    if (Variables.jogosultsag == 1) {
                        e.getComponent().setLocation(e.getXOnScreen() - (int) getParent().getLocationOnScreen().getX() - mousepozx, e.getYOnScreen() - (int) getParent().getLocationOnScreen().getY() - mousepozy);
                    }
//helyetcserélünk ha kell
                    //helycsere();

//a scrollpane ujraméretezése amennyiben szükséges
                    setScrollpanel();
                    b.repaint();

                }

            }

            @Override
            public void mouseMoved(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });

        addMouseListener(new MouseListener() {
//a plannobject setupablak megjelenítése
            @Override
            public void mouseClicked(MouseEvent e) {

                //a teljesules megvizsgalasa
                Thread t = new Thread(new Teljesules((PlannObject) e.getComponent()));
                t.start();
            }
//egér pozíció felvétele a jlabelen

            @Override
            public void mousePressed(MouseEvent e) {

                if (e.getComponent() instanceof JLabel) {
                    JLabel l = (JLabel) e.getComponent();
                    getParent().setComponentZOrder(l, 0);
                    Point p = new Point(0, 0);
                    try {
                        p = new Point(l.getMousePosition());
                        mousepozx = (int) p.getX();
                        mousepozy = (int) p.getY();

                    } catch (Exception ex) {

                    }

                }
//kijelölünk
                kijelol();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                //keressük az indulási idejét
                resetStartTime();
//osszerenezzuk
                b.osszerendez();
                setScrollpanel();
                b.repaint();

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

                b.repaint();
            }
        });
//a háttér beállítása
        img = new ImageIcon(System.getProperty("user.home") + "\\BT\\Pictures\\poback.jpg");
//a háttér beállítása ha ki van jlölve
        selectedimage = new ImageIcon(System.getProperty("user.home") + "\\BT\\Pictures\\poselected.jpg");
//nincs startime background
        nostartimeimage = new ImageIcon(System.getProperty("user.home") + "\\BT\\Pictures\\nostarttime.jpg");

//popupmenu csinálása
        JPopupMenu popupMenu = new PlannPopup(this, m);
//setteljuk be a sok szart
        this.setComponentPopupMenu(popupMenu);
        setPn(pn);
        setJob(job);
        setTerv(terv);
        setTeny(teny);
        //ez ki is szamolja a po ra vonatkozo ossz gyartasi idot
        setTervezettido(ciklusido);
        setPlannerKomment(plannerkomment);
        setKomment(komment);
        setEngineer(mernoki);
        setStartdate(startdate);
        setWtf(wtf);
        setWorkStation(workstation);
        formatText();

//keresse meg a helyét
        setStartLocation();

    }
//az anyaghianyok taroloja

    public class AnyagHiany {

        public String pn;
        public String tol;
        public String ig;
        public String felelos;
        public String komment;
        public String id;

        public AnyagHiany(String pn, String tol, String ig, String felelos, String komment, String id) {
            this.pn = pn;
            this.tol = tol;
            this.ig = ig;
            this.felelos = felelos;
            this.komment = komment;
            this.id = id;
        }

    }
//az anyaghianyok taroloja

    public class AllasidoLista {

        public String tol;
        public String ig;
        public String felelos;
        public String komment;
        public String id;

        public AllasidoLista(String tol, String ig, String felelos, String komment, String id) {

            this.tol = tol;
            this.ig = ig;
            this.felelos = felelos;
            this.komment = komment;
            this.id = id;
        }

    }

    public ArrayList<AnyagHiany> getAnyaghianylista() {
        return anyaghianylista;
    }

    public void addAnyaghianylista(String pn, String tol, String ig, String feleos, String komment, String id) {
        AnyagHiany a = new AnyagHiany(pn, tol, ig, feleos, komment, id);
        this.anyaghianylista.add(a);
    }

    public ArrayList<AllasidoLista> getAllasidoLista() {
        return allasidolista;
    }

    public void addAllasidoLista(String tol, String ig, String feleos, String komment, String id) {
        AllasidoLista a = new AllasidoLista(tol, ig, feleos, komment, id);
        this.allasidolista.add(a);
    }

    public void clearAnyaghianylista() {

        this.anyaghianylista.clear();
    }

    public void clearAllasidoLista() {

        this.allasidolista.clear();
    }

//kijelöli a po-t és a többin megszünteti a kijelölest
    public void kijelol() {
//összeszedjük az összes po-t az összes sheeten és false ra állítjuk a kijelölést
        for (int i = 0; i < m.jTabbedPane1.getTabCount(); i++) {
//kiszedjük a besheeteket
            BeSheet b = (BeSheet) m.jTabbedPane1.getComponentAt(i);
//bejárjuk a komponenseit és ha po a selectedet false ra állítom
            for (int p = 0; p < b.jPanel1.getComponentCount(); p++) {
                if (b.jPanel1.getComponent(p) instanceof PlannObject) {
                    PlannObject po = (PlannObject) b.jPanel1.getComponent(p);
                    po.setSelected(false);
                    //de ha ugyan az a job akkor true ra és a pn is ugyan az!
                    if (po.getJob().equals(this.getJob()) && po.getPn().equals(this.getPn())) {
                        po.setSelected(true);
                    }

                }

            }

        }
        //a thist pedig beállítom true ra
        this.setSelected(true);
//a po setupot panelt újrahívjuk, de csak akkor ha most is visible
        if (m.spo.isVisible()) {
            m.spo.setVisible(true, this);
        }
//az anyaghianyt ujrahivjuk de csak ha most is az
        if (m.ahrogzito.isVisible()) {
            m.ahrogzito.setVisible(this, true);
        }

        //az allasidot ujrahivjuk de csak ha most is az
        if (m.allasrogzito.isVisible()) {
            m.allasrogzito.setVisible(this, true);
        }
    }

    public String getPktomig() {
        return pktomig;
    }

    public void setPktomig(String pktomig) {
        this.pktomig = pktomig;
    }

    public int getMikorment() {
        return mikorment;
    }

    public void setMikorment(int mikorment) {
        this.mikorment = mikorment;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public BeSheet getbackendSheet() {
        return backendSheet;
    }

    public String getWorkStation() {
        return workStation;
    }

    public void setWorkStation(String workStation) {
        this.workStation = workStation;
    }

//a waterfall beállítása
    public void setWtf(int wtf) {
        this.wtf = wtf;
    }
//a waterfall kiszedése

    public int getWtf() {
        return wtf;
    }

    public MainWindow getMainWindow() {
        return m;
    }

    public double getCiklusido() {
        return ciklusido;
    }

    public double getGyartasiido() {
        return gyartasiido;
    }

    public void setGyartasiido(double gyartasiido) {
        this.gyartasiido = gyartasiido;
    }

    //kiszamolja es beallitja a gyartai idot
    public void calculateGyaratsiido() {

        setGyartasiido(60 / ciklusido * this.getTeny() / 60);
    }

    public boolean isTeljesult() {
        return teljesult;
    }

    public void setTeljesult(boolean teljesult) {
        this.teljesult = teljesult;
    }

//scrollpanel ujrameretezese ha szukseges
    public void setScrollpanel() {

        Component components[] = backendSheet.jPanel1.getComponents();
//        int maxx = 200;
        int maxy = 200;
        for (int i = 0; i < components.length; i++) {

            if (components[i] instanceof PlannObject) {

                PlannObject po = (PlannObject) components[i];

                if (po.getLocation().y + this.getHeight() > maxy) {

                    maxy = po.getLocation().y + this.getHeight() + 100;
                }

            }

        }

        backendSheet.jPanel1.setPreferredSize(new Dimension((int) backendSheet.jPanel1.getPreferredSize().getWidth(), maxy));
        // backendSheet.jPanel2.setPreferredSize(new Dimension((int) backendSheet.jPanel1.getPreferredSize().getWidth(), (int) backendSheet.jPanel2.getPreferredSize().getHeight()));
        backendSheet.jPanel1.revalidate();
        repaint();

    }

    //a starttime átírása a balról a legközelebbi vt nek megfelelően
    public void resetStartTime() {

        //elindulunk szépen a this lokációtól balra és megkeressük az elő verticaltimelinet és felvesszük az ő idejét
        boolean c = true;
        int x = this.getLocation().x + 50;

        while (c) {

            if (backendSheet.jPanel2.getComponentAt(x, 0) instanceof VerticalTimeline) {

                VerticalTimeline vt = (VerticalTimeline) backendSheet.jPanel2.getComponentAt(x, 0);
                this.setStartdate(vt.getVtstartdate() + ":00");
                c = false;

            } else if (x <= 0) {

                c = false;

            } else {

                x--;
            }

        }

        backendSheet.collectData();

    }

//az objektek elhelyezése a lekérdezés után
    public void setStartLocation() {
//összeszedjük a komponenseket
        Component[] components = backendSheet.jPanel2.getComponents();

//beforgatjuk és megkeressük a vt-ket
        for (int i = 0; i < components.length; i++) {
            if (components[i] instanceof VerticalTimeline) {
                VerticalTimeline vt = (VerticalTimeline) components[i];
//ha vt hez érek megnézem, hogy egyezik e a startdátumuk
                if (this.getStartdate().contains(vt.getVtstartdate())) {

//berakjuk a vt maxy -ra és hozzáadunk valamennyit
                    this.setLocation(vt.getLocation().x, vt.getMaxy());
                    this.setScrollpanel();
                    vt.setMaxy(vt.getMaxy() + this.getHeight() + 5);
                    return;

                }
            }

        }

    }

//gyártási idő beállítása
    public void setTervezettido(double ciklusido) {

//a gyartasi ido oraban van megadva, db/ora, tehat a gyartasi ido a telses po ra tekintve
        this.tervezettido = (60 / ciklusido * this.getTerv()) / 60;
//a grafikon szineinek kiszamitasa
        setProducTime();

    }
//a gyártási idő közvetlen beállítása

    public void setOsszGyartasiIdo(double ido) {

        this.tervezettido = ido;

    }

//mérnöki beállítása
    public void setEngineer(double b) {
        engineer = b;
    }
//komment beállítása

    public void setPlannerKomment(String komment) {
        this.plannerkomment = komment;
    }
//a termelés kommentje

    public void setKomment(String komment) {
        this.komment = komment;
    }

//partnumber beállítása
    public void setPn(String pn) {
        this.pn = pn;
    }
//job beállítása

    public void setJob(String job) {
        this.job = job;
    }
//terv beállítása

    public void setTerv(int qty) {
        this.terv = qty;
        //beállítjuk az indikátor vonalakat
        setProducted();
        setTervezettido(getCiklusido());
    }
//teny beallitasa

    public void setTeny(int teny) {
        this.teny = teny;
        setProducted();
        //kiszamoltatjuk a gyartasi idot
        calculateGyaratsiido();
    }

//a darabszám indikátor számainak kiszámítása
    public void setProducted() {

        DecimalFormat df = new DecimalFormat("#.00");
        df.setRoundingMode(RoundingMode.UP);
        try {
            dbzold = this.getSize().getWidth() * Double.parseDouble(df.format((double) teny / (double) terv));
            dbpiros = this.getSize().getWidth() - dbzold;
        } catch (Exception e) {
//            e.printStackTrace();
//            Starter.e.sendMessage(e);
        }

    }
//gyártási idő indikátor számainak kiszámítása

    public void setProducTime() {

        DecimalFormat df = new DecimalFormat("#.00");
        df.setRoundingMode(RoundingMode.UP);

        try {
            idozold = this.getSize().getWidth() * Double.parseDouble(df.format(((double) tervezettido) / 12));
            idopiros = this.getSize().getWidth() - idozold;
        } catch (Exception e) {
            e.printStackTrace();
            Starter.e.sendMessage(e);

        }

    }

    public void setStat(Variables.status stat) {
        this.stat = stat;
    }

    public Variables.status getStat() {
        return stat;
    }

    public String getPn() {
        return pn;
    }

    public String getJob() {
        return job;
    }

    public int getTerv() {
        return terv;
    }

    public int getTeny() {
        return teny;
    }

    public double getEngineer() {
        return engineer;
    }

    public String getPlannerkomment() {
        return plannerkomment;
    }

    public String getKomment() {
        return komment;
    }

    public double getTervezettido() {
        return tervezettido;
    }

//a startidő beállítása
    public void setStartdate(String startdate) {
        this.starttime = startdate;
    }
//a startdátum kiszedése

    public String getStartdate() {
        return starttime;
    }

//a tooltip és a szöveg formátumának előállítása és beállítása
    public void formatText() {
//
//        labeltext = "<html>WS: <font color=\"red\">" + workStation + "</font><br>PN: <font color=\"red\">" + pn + "</font><br>JOB: <font color=\"red\">" + job + "</font><br>Qty terv/tény: <font color=\"red\">" + terv + "/" + teny + "</font></html>";
//        setText(labeltext);
        tooltiptext = "<html><strong>PN: </strong><font color=\"red\">" + pn + "</font><br><strong>JOB: </strong><font color=\"red\">" + job + "</font><br><strong>Qty terv/tény: </strong><font color=\"red\">" + terv + "/" + teny + "</font><br><strong>Planner komment: </strong><font color=\"red\">" + plannerkomment + "</font><br><strong>Komment: </strong><font color=\"red\">" + komment + "</font><br>";
        for (int i = 0; i < anyaghianylista.size(); i++) {

            tooltiptext += "<strong>AH: </strong>" + anyaghianylista.get(i).pn + " " + anyaghianylista.get(i).tol + " " + anyaghianylista.get(i).ig + " " + anyaghianylista.get(i).felelos + " " + anyaghianylista.get(i).komment + "<br>";
        }

        for (int i = 0; i < allasidolista.size(); i++) {

            tooltiptext += "<strong>Állásidő: </strong>" + allasidolista.get(i).felelos + " " + allasidolista.get(i).tol + " " + allasidolista.get(i).ig + " " + allasidolista.get(i).komment + "<br>";
        }

        setToolTipText(tooltiptext);

    }
//a label ábrái 

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        if (!this.isSelected()) {
            g.drawImage(img.getImage(), 0, 0, null);
        } else if (this.isSelected()) {

            g.drawImage(selectedimage.getImage(), 0, 0, null);
        }

        if (getStartdate().equals("")) {
            g.drawImage(nostartimeimage.getImage(), 0, 0, null);

        }

        Graphics2D g2d = (Graphics2D) g;
//ha megvalósult a dolog legyen pipa
        if (teljesult) {
            g2d.setColor(Variables.pipacolor);
            int[] xek = {40, 55, 65, 170, 155, 62, 50};
            int[] yok = {30, 65, 65, 10, 10, 58, 30};
            g2d.fillPolygon(xek, yok, 7);

        }
// a darabszám indikátor megrajzolása
//zöld
        g2d.setColor(Variables.zold);
        g2d.fillRect(0, this.getHeight() - 7, (int) dbzold, 7);
        g2d.setColor(Variables.piros);
        g2d.fillRect(0 + (int) dbzold, this.getHeight() - 7, (int) dbpiros, 7);
//zöld , ha nincs terv csak tény
        g2d.setColor(Variables.zold);
        if (getTerv() == 0) {

            g2d.fillRect(0, this.getHeight() - 7, 200, 7);

        }

//a gyártási idő indikátor beállítása
        //zöld
        g2d.setColor(Variables.zold);
        g2d.fillRect(0, 0, (int) idozold, 7);
        g2d.setColor(Variables.piros);
        g2d.fillRect(0 + (int) idozold, 0, (int) idopiros, 7);

//ha van komment rajzolunk más ikont is
        if ((plannerkomment.length() > 0) || (komment.length() > 0)) {

            Icon komment = new javax.swing.ImageIcon(getClass().getResource("/pictures/comment.png"));
            komment.paintIcon(this, g, (int) this.getSize().getWidth() - 30, 5);

        }
//mérnöki ikon beállítása
        if (engineer > 0) {
            Icon engineer = new javax.swing.ImageIcon(getClass().getResource("/pictures/engineer.png"));
            engineer.paintIcon(this, g, (int) this.getSize().getWidth() - 30, this.getHeight() - 7 - engineer.getIconHeight());

        }

//anyaghiany és allasido beallitasa
        if (anyaghianylista.size() > 0 && allasidolista.size() > 0) {
            Icon ah = new javax.swing.ImageIcon(getClass().getResource("/pictures/ahallas.png"));
            ah.paintIcon(this, g, (int) this.getSize().getWidth() - 196, this.getHeight() - 48 - ah.getIconHeight());

        } else if (allasidolista.size() > 0) {
            Icon ah = new javax.swing.ImageIcon(getClass().getResource("/pictures/allas.png"));
            ah.paintIcon(this, g, (int) this.getSize().getWidth() - 196, this.getHeight() - 48 - ah.getIconHeight());

        } else if (anyaghianylista.size() > 0) {
            Icon ah = new javax.swing.ImageIcon(getClass().getResource("/pictures/ah.png"));
            ah.paintIcon(this, g, (int) this.getSize().getWidth() - 196, this.getHeight() - 48 - ah.getIconHeight());

        }

//a ws kiiratása
        g2d.setColor(Color.RED);
        g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 11));
        g2d.drawString("WS:", 30, 20);
        g2d.setColor(Color.BLACK);
        g2d.drawString(getWorkStation(), 57, 20);
//a pn kiirása
        g2d.setColor(Color.RED);
        //g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        g2d.drawString("PN:", 30, 35);
        g2d.setColor(Color.BLACK);
        g2d.drawString(getPn(), 57, 35);
//a job kiirása
        g2d.setColor(Color.RED);
        //g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        g2d.drawString("JOB:", 30, 50);
        g2d.setColor(Color.BLACK);
        g2d.drawString(getJob(), 57, 50);

//a terv/tény kiirása
        g2d.setColor(Color.RED);
        //g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        g2d.drawString("Terv/Tény:", 30, 65);
        g2d.setColor(Color.BLACK);
        g2d.drawString(getTerv() + " / " + getTeny(), 90, 65);
//mikor gyártottuk utoljára kiírása

        if (getMikorment() == -1) {
            g2d.setColor(Variables.piros);
            g2d.drawString("NEW", 5, 65);

        } else if (getMikorment() > 90) {
            g2d.setColor(Variables.piros);
            g2d.drawString(String.valueOf(getMikorment()), 10, 65);

        } else {
            g2d.setColor(Variables.zold);
            g2d.drawString(String.valueOf(getMikorment()), 10, 65);
            g2d.setColor(Color.BLACK);
        }

//a mainicon beállítása
        switch (stat) {

            case NotReleased:
                g2d.drawImage(new javax.swing.ImageIcon(getClass().getResource("/pictures/notreleased.png")).getImage(), 0, 15, null);
                break;
            case Released:
                g2d.drawImage(new javax.swing.ImageIcon(getClass().getResource("/pictures/released.png")).getImage(), 0, 15, null);
                break;
            case Complete:
                g2d.drawImage(new javax.swing.ImageIcon(getClass().getResource("/pictures/complete.png")).getImage(), 0, 15, null);
                break;
            case Skeleton:
                g2d.drawImage(new javax.swing.ImageIcon(getClass().getResource("/pictures/skeleton.png")).getImage(), 0, 15, null);
                break;

            case NotExists:
                g2d.drawImage(new javax.swing.ImageIcon(getClass().getResource("/pictures/notexists.png")).getImage(), 0, 15, null);
                break;
            default:
                g2d.drawImage(new javax.swing.ImageIcon(getClass().getResource("/pictures/notreleased.png")).getImage(), 0, 15, null);

        }

    }

}
