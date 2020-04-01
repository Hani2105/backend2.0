/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bt;

import java.awt.Component;
import java.util.ArrayList;

/**
 *
 * @author gabor_hanacsek
 */
public class RePlann {

    public RePlann() {

    }

//csak a kijelölt elem áttervezése  
    public void rePlannOne(PlannObject p) {

//megnézzük, hogy nincs e teljesen kész a terv
        if (p.getTerv() - p.getTeny() > 0) {

//megnézzük, hogy mi a következő vt tőle jobbra
            for (int i = p.getLocation().x + p.getWidth(); i < p.getbackendSheet().jPanel2.getPreferredSize().width; i++) {
//ha megtaláljuk a következő vt-t
                if (p.getbackendSheet().jPanel2.getComponentAt(i, 0) instanceof VerticalTimeline) {
                    VerticalTimeline vt = (VerticalTimeline) p.getbackendSheet().jPanel2.getComponentAt(i, 0);
//meg kell nézni, hogy van e már ilyen terv,(pn job ws) és ha igen összeadjuk a terv mennyiségeket
                    for (int n = 0; n < p.getbackendSheet().jPanel1.getComponentCount(); n++) {

                        if (p.getbackendSheet().jPanel1.getComponent(n) instanceof PlannObject) {

                            PlannObject po = (PlannObject) p.getbackendSheet().jPanel1.getComponent(n);
                            if (po.getStartdate().contains(vt.getVtstartdate()) && po.getPn().equals(p.getPn()) && po.getJob().equals(p.getJob()) && po.getWorkStation().equals(p.getWorkStation())) {
                                //az átvitelhez hozzáadjuk az eredeti tervből azt a mennyiséget ami fentmaradt                         
                                po.setTerv(po.getTerv() + (p.getTerv() - p.getTeny()));
                                if (p.getTeny() == 0) {

                                    p.getbackendSheet().jPanel1.remove(p);

                                } else {

                                    p.setTerv(p.getTeny());

                                }
                                p.osszerendez();
                                p.getbackendSheet().repaint();
                                return;

                            }

                        }

                    }

//ha idáig eljutunk ,létrehozunk egy új po-t és kitöltjük a megfelelő adatokkal,és a megmaradt mennyiséggel
                    PlannObject po = new PlannObject(p.getbackendSheet(), p.getWidth(), p.getHeight(), p.getPn(), p.getJob(), vt.getVtstartdate(), p.getTerv() - p.getTeny(), 0, p.getPlannerkomment(), p.getKomment(), p.getEngineer(), 0, p.getWorkStation(), p.getCiklusido(), p.getMainWindow());
//hozzáadjuk a panelhoz oda ahol a tobbi van
                    po.setStat(p.getStat());
                    p.getbackendSheet().jPanel1.add(po);
                    po.setLocation(vt.getLocation().x + 10, 0);

//az eredeti po nak legyen egyenlő a tervezett mennyisége a megvalósulással, ha ez a szám a nulla lesz akkor törlődjön
                    if (p.getTeny() == 0) {

                        p.getbackendSheet().jPanel1.remove(p);

                    } else {

                        p.setTerv(p.getTeny());

                    }
                    p.osszerendez();
                    p.getbackendSheet().repaint();
                    return;

                }

            }

        }
    }

    public void fullRePlann(PlannObject p) {
        String starttime = p.getStartdate();
        ArrayList<PlannObject> polist = new ArrayList<>();

//begyűjtjük az összes ehhez a vt hez tartozó plannobjectet , tehát az ugyan olyan startidővel rendelkezőket
        for (int i = 0; i < p.getbackendSheet().jPanel1.getComponentCount(); i++) {

            if (p.getbackendSheet().jPanel1.getComponent(i) instanceof PlannObject) {
                PlannObject po = (PlannObject) p.getbackendSheet().jPanel1.getComponent(i);
                if (po.getStartdate().contains(starttime)) {
                    polist.add((PlannObject) p.getbackendSheet().jPanel1.getComponent(i));
                }

            }
        }

        for (int i = 0; i < polist.size(); i++) {

            rePlannOne(polist.get(i));

        }

    }

    public void eloreGyartas(PlannObject p) {
//felszedjük az összes po-t a sheetről
        ArrayList<PlannObject> polista = new ArrayList<>();
//ezt az egészet addig csináljuk míg p tény nagyobb p terv vagy végig nem érünk az összes vt-n
        while (p.getTeny() > p.getTerv()) {
            //megnézzük, hogy mi a következő vt tőle jobbra
            for (int i = p.getLocation().x + p.getWidth(); i < p.getbackendSheet().jPanel2.getPreferredSize().width; i++) {
//ha megtaláljuk a következő vt-t
                if (p.getbackendSheet().jPanel2.getComponentAt(i, 0) instanceof VerticalTimeline) {
                    VerticalTimeline vt = (VerticalTimeline) p.getbackendSheet().jPanel2.getComponentAt(i, 0);
//megkeressük a hozzá tartozó po-kat
                    for (int n = 0; n < p.getbackendSheet().jPanel1.getComponentCount(); n++) {
                        if (p.getbackendSheet().jPanel1.getComponent(n) instanceof PlannObject) {
                            PlannObject po = (PlannObject) p.getbackendSheet().jPanel1.getComponent(n);
                            //kiszedtünk egy po-t, most meg kell vizsgálni, hogy ez a jövőben van e, és egyeznek e az adatok és van visszamaradt mennyiség (terv-tény) > 0
                            if (p.getLocation().x < po.getLocation().x && po.getPn().equals(p.getPn()) && po.getJob().equals(p.getJob()) && po.getWorkStation().equals(p.getWorkStation()) && ((po.getTerv() - po.getTeny()) > 0) && po.getStartdate().contains(vt.getVtstartdate())) {
//indítunk egy ciklust és elkezdjük növelni a p tervét amíg el nem éri a tényt, közben csökkentjük a po tervét, de megállunk akkor is ha po terve eléri a po tényét vagy a nullát
                                while (p.getTerv() != p.getTeny() && po.getTerv() != po.getTeny()) {

                                    p.setTerv(p.getTerv() + 1);
                                    po.setTerv(po.getTerv() - 1);
//ha eléri a terv a nullát, töröljük is
                                    if (po.getTerv() == 0) {

                                        p.getbackendSheet().jPanel1.remove(n);
                                        break;
                                    }

                                }

                            }
                        }
                    }
                }

            }
            //végigértünk a teljes backend sheeten
            p.getbackendSheet().repaint();
            return;

        }

        p.getbackendSheet().repaint();

    }

}
