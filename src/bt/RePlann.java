/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bt;

import java.awt.Component;
import java.awt.Cursor;
import java.util.ArrayList;
import java.util.Comparator;
import javax.swing.JOptionPane;

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
                                p.getbackendSheet().osszerendez();
                                p.getbackendSheet().repaint();
                                return;

                            }

                        }

                    }

//ha idáig eljutunk ,létrehozunk egy új po-t és kitöltjük a megfelelő adatokkal,és a megmaradt mennyiséggel
                    PlannObject po = new PlannObject(p.getbackendSheet(), p.getWidth(), p.getHeight(), p.getPn(), p.getJob(), vt.getVtstartdate(), p.getTerv() - p.getTeny(), 0, p.getPlannerkomment(), p.getKomment(), p.getEngineer(), 0, p.getWorkStation(), p.getCiklusido(), p.getMainWindow(), 0);
//hozzáadjuk a panelhoz oda ahol a tobbi van
                    po.setStat(p.getStat());
                    po.setMikorment(p.getMikorment());
                    po.setPktomig(p.getPktomig());
                    p.getbackendSheet().jPanel1.add(po);
                    po.setLocation(vt.getLocation().x + 10, 0);

//az eredeti po nak legyen egyenlő a tervezett mennyisége a megvalósulással, ha ez a szám a nulla lesz akkor törlődjön
                    if (p.getTeny() == 0) {

                        p.getbackendSheet().jPanel1.remove(p);

                    } else {

                        p.setTerv(p.getTeny());

                    }
                    p.getbackendSheet().osszerendez();
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

    public void varazslo1(PlannObject p) {

        //átállítjuk a mouse icont
        Cursor cursor = new Cursor(Cursor.WAIT_CURSOR);
        p.getbackendSheet().setCursor(cursor);
//végigmegyünk a jtable 1 en po-kat keresve a targetdátumig
        for (int sz = 50; sz < p.getLocation().x; sz += 210) {
            outerloop:
            for (int m = 0; m < p.getbackendSheet().jPanel1.getHeight(); m++) {

                if (p.getbackendSheet().jPanel1.getComponentAt(sz, m) instanceof PlannObject) {
                    PlannObject po = (PlannObject) p.getbackendSheet().jPanel1.getComponentAt(sz, m);
//ha ez a po problémás, azaz terv nem egyenlo a tennyel akkor foglalkozni kell vele, ha nem akkor ugrunk egy po magasságot lefelé
                    if (po.getTeny() != po.getTerv()) {

//ha foglalkozni kell vele akkor 2 eset lehetsége,
//1, a terv nagyobb a ténynél, ekkor a tőle jobbra eső po-khoz kell hozzáadni a maradék tervet ami a megvalósulás felett van >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
                        if (po.getTerv() > po.getTeny()) {
//bejárjuk a jobbra lévő po-kat és ha találunk egyezőt akkor hozzágöngyöljük a tervet
                            for (int i = po.getLocation().x; i <= p.getLocation().x; i++) {
                                outerloop2:
                                for (int r = 0; r < p.getbackendSheet().jPanel1.getHeight(); r++) {
//ha találunk olyan po-t ami egyező és nem önmaga akkor hozzáírjuk a maradék tervet
                                    if (p.getbackendSheet().jPanel1.getComponentAt(i, r) instanceof PlannObject) {

                                        PlannObject poaz = (PlannObject) p.getbackendSheet().jPanel1.getComponentAt(i, r);

                                        if (!poaz.equals(po) && poaz.getPn().equals(po.getPn()) && poaz.getJob().equals(po.getJob()) && poaz.getWorkStation().equals(po.getWorkStation())) {
                                            //hozzáírjuk a maradék tervet
                                            poaz.setTerv(poaz.getTerv() + (po.getTerv() - po.getTeny()));
                                            //beállítjuk a po-hoz az uj tervet ami egyenlő kell legyen a ténnyel
                                            po.setTerv(po.getTeny());
                                            //ha ez nulla, akkor levesszük a sheetről
                                            if (po.getTerv() == 0) {

                                                p.getbackendSheet().jPanel1.remove(po);

                                            }
                                            //ugorhatunk egy po-t mert ezzel már elvileg minden rendben
                                            m += poaz.getHeight();
                                            continue outerloop;
                                        } //tehát ha önmagát találtuk meg vagy nem egyforma po-t akkor uorhatunk egy pomagasságot lefelé
                                        else {
                                            r += poaz.getHeight();
                                            continue outerloop2;

                                        }

                                    }

                                }

                            }

//ha ide elérünk akkor kell uj po-t létrehozni
                            PlannObject pouj = new PlannObject(po.getbackendSheet(), po.getWidth(), po.getHeight(), po.getPn(), po.getJob(), p.getStartdate(), po.getTerv() - po.getTeny(), 0, po.getPlannerkomment(), po.getKomment(), po.getEngineer(), 0, po.getWorkStation(), po.getCiklusido(), po.getMainWindow(), 0);
                            //hozzáadjuk a panelhoz oda ahol a tobbi van
                            pouj.setStat(po.getStat());
                            pouj.setMikorment(po.getMikorment());
                            pouj.setPktomig(po.getPktomig());
                            pouj.getbackendSheet().jPanel1.add(pouj);
                            pouj.setLocation(p.getLocation().x, 0);
//a po terve legyen egyenlo a tennyel
                            po.setTerv(po.getTeny());
//ha teny = 0 akkor töröljük ki
                            if (po.getTeny() == 0) {

                                p.getbackendSheet().jPanel1.remove(po);
                            }

                        }
//abban az esetben ha nagyobb a teny mint a terv (mögötte nem lehet olyan ahol a terv nagyobb mint a tény mert az előre raktuk volna tehát előtte kell keresgélni szintén)
                        if (po.getTeny() > po.getTerv()) {

                            for (int i = po.getLocation().x; i <= p.getLocation().x; i++) {
                                outerloop2:
                                for (int r = 0; r < p.getbackendSheet().jPanel1.getHeight(); r++) {

                                    //ha találunk olyan po-t ami egyező és nem önmaga és a terve nagyobb mint a ténye
                                    if (p.getbackendSheet().jPanel1.getComponentAt(i, r) instanceof PlannObject) {
                                        PlannObject poaz = (PlannObject) p.getbackendSheet().jPanel1.getComponentAt(i, r);

                                        if (!poaz.equals(po) && poaz.getPn().equals(po.getPn()) && poaz.getJob().equals(po.getJob()) && poaz.getWorkStation().equals(po.getWorkStation()) && poaz.getTerv() > poaz.getTeny()) {

                                            //hozzáadunk annyi tervet a po-hoz amennyit a poaz enged vagy amennyit kell,ezt egy while ciklussal csinaljuk
                                            while (poaz.getTerv() > poaz.getTeny() && po.getTerv() != po.getTeny()) {

                                                po.setTerv(po.getTerv() + 1);
                                                poaz.setTerv(poaz.getTerv() - 1);

                                            }

                                            //ha nulla lett a terv és a tény akkor töröljük
                                            if (poaz.getTerv() == 0 && poaz.getTeny() == 0) {

                                                p.getbackendSheet().jPanel1.remove(poaz);

                                            }

                                            //ha kiugrottunk a ciklusból akkor meg kell vizsgálni, hogy kell e még tovább foglalkozni a po-val
                                            if (po.getTeny() == po.getTerv()) {
                                                //ha nem akkor ugorhatunk a kövi po-ra
                                                m += poaz.getHeight();
                                                continue outerloop;

                                            }

//                                   
                                        } //tehát ha önmagát találtuk meg vagy nem egyforma po-t akkor uorhatunk egy pomagasságot lefelé
                                        else {
                                            r += poaz.getHeight();
                                            continue outerloop2;
                                        }

                                    }

                                }
                            }

                            //ha ide eljutunk akkor ki kell írni, hogy rendezhetetlen gyártás mert a tény több mint a terv
                            //custom title, warning icon
                            JOptionPane.showMessageDialog(p.getbackendSheet(),
                                    "A megvalósulás a lekért intervallumon nagyobb \n mint az összes tervezett mennyiég a \n PN: " + po.getPn() + " JOB: " + po.getJob() + " WS: " + po.getWorkStation() + "esetében!",
                                    "Tervezési hiba!",
                                    JOptionPane.WARNING_MESSAGE);
                            //ugrunk a következő po-ra

                            m += po.getHeight();
                            continue outerloop;

                        }

                    } else {

                        m += po.getHeight();

                    }

                }

            }

        }

        p.getbackendSheet().osszerendez();
        p.getbackendSheet().repaint();
        //visszaállítjuk a cursort
        cursor = new Cursor(Cursor.DEFAULT_CURSOR);
        p.getbackendSheet().setCursor(cursor);

    }

}
