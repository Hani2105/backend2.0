package bt;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author gabor_hanacsek
 */
public class VerticalTimeline extends JLabel {

    private int mousepozx = 0;
    private int mousepozy = 0;
    private int maxy = 10;
    BeSheet be;
    private Component[] components;
    private Calendar c = Calendar.getInstance();
    String vtstartdate;

    public VerticalTimeline(BeSheet be, String vtstartdate) {
        super();
        this.be = be;
        this.vtstartdate = vtstartdate;
        this.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pictures/haromszog.png")));
        this.setIconTextGap(0);
        this.setSize(200, 25);
        this.setLocation(30, 0);
        //this.setBackground(Color.red);
        this.setOpaque(true);
        javax.swing.border.Border border = BorderFactory.createLineBorder(Color.BLUE, 1);
        this.setBorder(border);

        //egér motion hozzáadása
        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {

            }

            @Override
            public void mouseMoved(MouseEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        }
        );

        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                muszakjelentes();

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseEntered(MouseEvent e) {
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void mouseExited(MouseEvent e) {
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });

    }

    public int getDayOfWeekFromVtStartdate(String vtstartdate) {
        int dayofweek = 100;
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(vtstartdate);
            c.setTime(date);
            dayofweek = c.get(Calendar.DAY_OF_WEEK);

        } catch (ParseException ex) {
            ex.printStackTrace();
            Starter.e.sendMessage(ex);
        }

        return dayofweek;
    }

    public void setVtstartdate(String vtstartdate) {
        this.vtstartdate = vtstartdate;
    }

    public String getVtstartdate() {
        return vtstartdate;
    }

    public void muszakjelentes() {
        //eltároljuk a starttimejat a besheeten
        be.vtstartime = this.getVtstartdate();
        //műszakjelentés összeállítása
        DecimalFormat df = new DecimalFormat("0.00");
        //össze kell szedni az adatokat amik ehez a vt hez tartoznak és be kell írni a műszakjelentésbe
        Component[] components = be.jPanel1.getComponents();
        int osszterv = 0;
        int osszteny = 0;
        //az állomásonkénti tároló
        TreeMap<String, Double[]> map = new TreeMap<String, Double[]>();
        //a jobonkénti tároló
        ArrayList<String[]> jobonkent = new ArrayList<>();
        //az összidő tároló

        for (int i = 0; i < components.length; i++) {
            if (components[i] instanceof PlannObject) {

                PlannObject po = (PlannObject) components[i];

                if (po.getStartdate().contains(vtstartdate)) {

                    if (map.get(po.getWorkStation()) != null) {
                        Double eddigiterv = map.get(po.getWorkStation())[0] + po.getTerv();
                        Double eddigteny = map.get(po.getWorkStation())[1] + po.getTeny();
                        Double eddigtervezettido = map.get(po.getWorkStation())[2] + po.getTervezettido();
                        Double eddiggyartottido = map.get(po.getWorkStation())[3] + po.getGyartasiido();
                        Double[] eddigadat = new Double[4];
                        eddigadat[0] = eddigiterv;
                        eddigadat[1] = eddigteny;
                        eddigadat[2] = eddigtervezettido;
                        eddigadat[3] = eddiggyartottido;

                        map.put(po.getWorkStation(), eddigadat);
                    } else {
                        Double[] eddigadat = new Double[4];
                        eddigadat[0] = (double) po.getTerv();
                        eddigadat[1] = (double) po.getTeny();
                        eddigadat[2] = (double) po.getTervezettido();
                        eddigadat[3] = (double) po.getGyartasiido();
                        map.put(po.getWorkStation(), eddigadat);

                    }
                    osszterv += po.getTerv();
                    osszteny += po.getTeny();
                    String[] adatok = new String[9];
                    adatok[0] = po.getPn();
                    adatok[1] = po.getJob();
                    adatok[2] = String.valueOf(po.getTerv());
                    adatok[3] = String.valueOf(po.getTeny());
                    adatok[4] = po.getWorkStation();
                    adatok[5] = po.getPlannerkomment();
                    adatok[6] = po.getKomment();
                    adatok[7] = "";
                    for (int n = 0; n < po.getAnyaghianylista().size(); n++) {
                        adatok[7] += po.getAnyaghianylista().get(n).pn + " " + po.getAnyaghianylista().get(n).tol + " " + po.getAnyaghianylista().get(n).ig + " " + po.getAnyaghianylista().get(n).felelos +" " + po.getAnyaghianylista().get(n).komment + "<br>";
                    }
                    adatok[8] = "";
                    for (int n = 0; n < po.getAllasidoLista().size(); n++) {
                        adatok[8] += po.getAllasidoLista().get(n).felelos + " " + po.getAllasidoLista().get(n).tol + " " + po.getAllasidoLista().get(n).ig + " " + po.getAllasidoLista().get(n).komment + "<br>";
                    }

                    jobonkent.add(adatok);
                }

            }

        }
//össze kell rakni a ws enkénti szöveget
        String wsenkent = "Állomásonként: <font color=\"black\"><br><table border = \"4\">";
        String allomasido = "Idő állomásonként: <br><table border = \"4\">";
        Set<String> keys = map.keySet();
        for (String key : keys) {

            wsenkent += "<tr><td><font color=\"red\">" + key + ": </td><td>Terv: " + (int) Math.round(map.get(key)[0]) + "</td><td> Tény: " + (int) Math.round(map.get(key)[1]) + "</td></tr>";
            allomasido += "<tr><font color=\"red\">Tervezett/Gyártott idő az állomáson: <td>" + key + ": </td><td>" + df.format(map.get(key)[2]) + " / " + df.format(map.get(key)[3]) + "h</td></tr>";
        }
//összerakjuk a jobonkenti szoveget
        String jobonkentszoveg = "Tervenként: <br><table border = \"4\"><tr><td><font color=\"red\">Partnumber</td><td><font color=\"red\">Job</td><td><font color=\"red\">Workstation</td><td><font color=\"red\">Terv:</td><td><font color=\"red\">Tény:</td><td><font color=\"red\">Planner komment</td><td><font color=\"red\">Termelés komment</td><td><font color=\"red\">Anyaghiány</td><td><font color=\"red\">Állásidő</td></tr><font color=\"black\">";
        for (int i = 0; i < jobonkent.size(); i++) {
            jobonkentszoveg += "<tr><td>" + jobonkent.get(i)[0] + "</td><td>" + jobonkent.get(i)[1] + "</td><td>" + jobonkent.get(i)[4] + "</td><td>" + jobonkent.get(i)[2] + "</td><td>" + jobonkent.get(i)[3] + "</td><td>" + jobonkent.get(i)[5] + "</td><td>" + jobonkent.get(i)[6] + "</td><td>" + jobonkent.get(i)[7] + "</td><td>" + jobonkent.get(i)[8] + "</td></tr>";
        }

        be.m.cp.jTextPane1.setText("<html>Tervezett mennyiség a cellára nézve:<br><table border = \"4\"><tr><td><font color=\"red\">Össz terv: </td><td><font color=\"black\">" + osszterv + "</td><tr><td><font color=\"red\">Össz tény: <font color=\"black\"></td>" + osszteny + "</tr></table><br>"
                + wsenkent + "</table><br>" + allomasido + "</table><br>" + jobonkentszoveg + "</table></html>");

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
//ha hétfő van
        if (getDayOfWeekFromVtStartdate(vtstartdate) == 2) {
            g.drawImage(new javax.swing.ImageIcon(getClass().getResource("/pictures/hetfo.jpg")).getImage(), 0, 0, null);
            g.drawString(vtstartdate + " Hétfő", 30, 17);
            this.setName(vtstartdate + " Hétfő");
            if (vtstartdate.contains("06:")) {
                g.drawImage(new javax.swing.ImageIcon(getClass().getResource("/pictures/sun.png")).getImage(), 0, 0, null);

            } else if (vtstartdate.contains("18:")) {
                g.drawImage(new javax.swing.ImageIcon(getClass().getResource("/pictures/moon.png")).getImage(), 0, 0, null);

            }

        } //ha kedd van
        else if (getDayOfWeekFromVtStartdate(vtstartdate) == 3) {
            g.drawImage(new javax.swing.ImageIcon(getClass().getResource("/pictures/kedd.jpg")).getImage(), 0, 0, null);
            g.drawString(vtstartdate + " Kedd", 30, 17);
            this.setName(vtstartdate + " Kedd");
            if (vtstartdate.contains("06:")) {
                g.drawImage(new javax.swing.ImageIcon(getClass().getResource("/pictures/sun.png")).getImage(), 0, 0, null);

            } else if (vtstartdate.contains("18:")) {
                g.drawImage(new javax.swing.ImageIcon(getClass().getResource("/pictures/moon.png")).getImage(), 0, 0, null);

            }
        } //szerda
        else if (getDayOfWeekFromVtStartdate(vtstartdate) == 4) {
            g.drawImage(new javax.swing.ImageIcon(getClass().getResource("/pictures/szerda.jpg")).getImage(), 0, 0, null);
            g.drawString(vtstartdate + " Szerda", 30, 17);
            this.setName(vtstartdate + " Szerda");
            if (vtstartdate.contains("06:")) {
                g.drawImage(new javax.swing.ImageIcon(getClass().getResource("/pictures/sun.png")).getImage(), 0, 0, null);

            } else if (vtstartdate.contains("18:")) {
                g.drawImage(new javax.swing.ImageIcon(getClass().getResource("/pictures/moon.png")).getImage(), 0, 0, null);

            }
        } //csutortok
        else if (getDayOfWeekFromVtStartdate(vtstartdate) == 5) {
            g.drawImage(new javax.swing.ImageIcon(getClass().getResource("/pictures/csutortok.jpg")).getImage(), 0, 0, null);
            g.drawString(vtstartdate + " Csütörtök", 30, 17);
            this.setName(vtstartdate + " Csütörtök");
            if (vtstartdate.contains("06:")) {
                g.drawImage(new javax.swing.ImageIcon(getClass().getResource("/pictures/sun.png")).getImage(), 0, 0, null);

            } else if (vtstartdate.contains("18:")) {
                g.drawImage(new javax.swing.ImageIcon(getClass().getResource("/pictures/moon.png")).getImage(), 0, 0, null);

            }
        } //pentek
        else if (getDayOfWeekFromVtStartdate(vtstartdate) == 6) {
            g.drawImage(new javax.swing.ImageIcon(getClass().getResource("/pictures/pentek.jpg")).getImage(), 0, 0, null);
            g.drawString(vtstartdate + " Péntek", 30, 17);
            this.setName(vtstartdate + " Péntek");
            if (vtstartdate.contains("06:")) {
                g.drawImage(new javax.swing.ImageIcon(getClass().getResource("/pictures/sun.png")).getImage(), 0, 0, null);

            } else if (vtstartdate.contains("18:")) {
                g.drawImage(new javax.swing.ImageIcon(getClass().getResource("/pictures/moon.png")).getImage(), 0, 0, null);

            }
        } //szombat
        else if (getDayOfWeekFromVtStartdate(vtstartdate) == 7) {
            g.drawImage(new javax.swing.ImageIcon(getClass().getResource("/pictures/szombat.jpg")).getImage(), 0, 0, null);
            g.drawString(vtstartdate + " Szombat", 30, 17);
            this.setName(vtstartdate + " Szombat");
            if (vtstartdate.contains("06:")) {
                g.drawImage(new javax.swing.ImageIcon(getClass().getResource("/pictures/sun.png")).getImage(), 0, 0, null);

            } else if (vtstartdate.contains("18:")) {
                g.drawImage(new javax.swing.ImageIcon(getClass().getResource("/pictures/moon.png")).getImage(), 0, 0, null);

            }
        } //vasarnap
        else if (getDayOfWeekFromVtStartdate(vtstartdate) == 1) {
            g.drawImage(new javax.swing.ImageIcon(getClass().getResource("/pictures/vasarnap.jpg")).getImage(), 0, 0, null);
            g.drawString(vtstartdate + " Vasárnap", 30, 17);
            this.setName(vtstartdate + " Vasárnap");
            if (vtstartdate.contains("06:")) {
                g.drawImage(new javax.swing.ImageIcon(getClass().getResource("/pictures/sun.png")).getImage(), 0, 0, null);

            } else if (vtstartdate.contains("18:")) {
                g.drawImage(new javax.swing.ImageIcon(getClass().getResource("/pictures/moon.png")).getImage(), 0, 0, null);

            }
        }

    }

//a komponensek elhelyezkedese
    public void setMaxy(int maxy) {
        this.maxy = maxy;
    }
//a komponensek elhelyezkedese

    public int getMaxy() {
        return maxy;
    }

}
