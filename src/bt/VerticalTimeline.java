package bt;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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

//                if (e.getComponent() instanceof JLabel) {
//
//                    e.getComponent().setLocation(e.getXOnScreen() - (int) getParent().getLocationOnScreen().getX() - mousepozx, 0);
//                    a plannobjectek ujrapozicionálása
//                    setPlannObjectsPoz();
//                    be.repaint();
//
//                }

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
                //                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

            }

            @Override
            public void mousePressed(MouseEvent e) {
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
            Logger.getLogger(VerticalTimeline.class.getName()).log(Level.SEVERE, null, ex);
        }

        return dayofweek;
    }

    public void setVtstartdate(String vtstartdate) {
        this.vtstartdate = vtstartdate;
    }

    public String getVtstartdate() {
        return vtstartdate;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
//ha hétfő van
        if (getDayOfWeekFromVtStartdate(vtstartdate) == 2) {
            g.drawImage(new javax.swing.ImageIcon(getClass().getResource("/pictures/hetfo.jpg")).getImage(), 0, 0, null);
            g.drawString(vtstartdate + " Hétfő", 30, 17);
            if (vtstartdate.contains("06:")) {
                g.drawImage(new javax.swing.ImageIcon(getClass().getResource("/pictures/sun.png")).getImage(), 0, 0, null);

            } else if (vtstartdate.contains("18:")) {
                g.drawImage(new javax.swing.ImageIcon(getClass().getResource("/pictures/moon.png")).getImage(), 0, 0, null);

            }

        } //ha kedd van
        else if (getDayOfWeekFromVtStartdate(vtstartdate) == 3) {
            g.drawImage(new javax.swing.ImageIcon(getClass().getResource("/pictures/kedd.jpg")).getImage(), 0, 0, null);
            g.drawString(vtstartdate + " Kedd", 30, 17);
            if (vtstartdate.contains("06:")) {
                g.drawImage(new javax.swing.ImageIcon(getClass().getResource("/pictures/sun.png")).getImage(), 0, 0, null);

            } else if (vtstartdate.contains("18:")) {
                g.drawImage(new javax.swing.ImageIcon(getClass().getResource("/pictures/moon.png")).getImage(), 0, 0, null);

            }
        } //szerda
        else if (getDayOfWeekFromVtStartdate(vtstartdate)  == 4) {
            g.drawImage(new javax.swing.ImageIcon(getClass().getResource("/pictures/szerda.jpg")).getImage(), 0, 0, null);
            g.drawString(vtstartdate + " Szerda", 30, 17);
            if (vtstartdate.contains("06:")) {
                g.drawImage(new javax.swing.ImageIcon(getClass().getResource("/pictures/sun.png")).getImage(), 0, 0, null);

            } else if (vtstartdate.contains("18:")) {
                g.drawImage(new javax.swing.ImageIcon(getClass().getResource("/pictures/moon.png")).getImage(), 0, 0, null);

            }
        } //csutortok
        else if (getDayOfWeekFromVtStartdate(vtstartdate)  == 5) {
            g.drawImage(new javax.swing.ImageIcon(getClass().getResource("/pictures/csutortok.jpg")).getImage(), 0, 0, null);
            g.drawString(vtstartdate+ " Csütörtök", 30, 17);
            if (vtstartdate.contains("06:")) {
                g.drawImage(new javax.swing.ImageIcon(getClass().getResource("/pictures/sun.png")).getImage(), 0, 0, null);

            } else if (vtstartdate.contains("18:")) {
                g.drawImage(new javax.swing.ImageIcon(getClass().getResource("/pictures/moon.png")).getImage(), 0, 0, null);

            }
        } //pentek
        else if (getDayOfWeekFromVtStartdate(vtstartdate)  == 6) {
            g.drawImage(new javax.swing.ImageIcon(getClass().getResource("/pictures/pentek.jpg")).getImage(), 0, 0, null);
            g.drawString(vtstartdate + " Péntek", 30, 17);
            if (vtstartdate.contains("06:")) {
                g.drawImage(new javax.swing.ImageIcon(getClass().getResource("/pictures/sun.png")).getImage(), 0, 0, null);

            } else if (vtstartdate.contains("18:")) {
                g.drawImage(new javax.swing.ImageIcon(getClass().getResource("/pictures/moon.png")).getImage(), 0, 0, null);

            }
        } //szombat
        else if (getDayOfWeekFromVtStartdate(vtstartdate)  == 7) {
            g.drawImage(new javax.swing.ImageIcon(getClass().getResource("/pictures/szombat.jpg")).getImage(), 0, 0, null);
            g.drawString(vtstartdate + " Szombat", 30, 17);
            if (vtstartdate.contains("06:")) {
                g.drawImage(new javax.swing.ImageIcon(getClass().getResource("/pictures/sun.png")).getImage(), 0, 0, null);

            } else if (vtstartdate.contains("18:")) {
                g.drawImage(new javax.swing.ImageIcon(getClass().getResource("/pictures/moon.png")).getImage(), 0, 0, null);

            }
        } //vasarnap
        else if (getDayOfWeekFromVtStartdate(vtstartdate)  == 1) {
            g.drawImage(new javax.swing.ImageIcon(getClass().getResource("/pictures/vasarnap.jpg")).getImage(), 0, 0, null);
            g.drawString(vtstartdate + " Vasárnap", 30, 17);
            if (vtstartdate.contains("06:")) {
                g.drawImage(new javax.swing.ImageIcon(getClass().getResource("/pictures/sun.png")).getImage(), 0, 0, null);

            } else if (vtstartdate.contains("18:")) {
                g.drawImage(new javax.swing.ImageIcon(getClass().getResource("/pictures/moon.png")).getImage(), 0, 0, null);

            }
        }

    }

//a vt hez tartozó plannobjectek áthelyezése ha mozgatjuk a timelinet
//    public void setPlannObjectsPoz() {
//
//        components = be.jPanel1.getComponents();
//
//        for (int i = 0; i < components.length; i++) {
//
//            if (components[i] instanceof PlannObject) {
//
//                PlannObject po = (PlannObject) components[i];
//                if (po.getStartdate().contains(this.getVtstartdate()) && !this.getVtstartdate().equals("")) {
//
//                    po.setLocation(this.getLocation().x, po.getLocation().y);
//
//                }
//            }
//
//        }
//
//    }

//a komponensek elhelyezkedese
    public void setMaxy(int maxy) {
        this.maxy = maxy;
    }
//a komponensek elhelyezkedese

    public int getMaxy() {
        return maxy;
    }

   
}
