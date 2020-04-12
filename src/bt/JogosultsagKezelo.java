/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bt;

import com.sun.mail.handlers.message_rfc822;

/**
 *
 * @author gabor_hanacsek
 */
public class JogosultsagKezelo {

    ControlPanel cp;
    SetPlannObjectData sp;

    public JogosultsagKezelo(ControlPanel cp, SetPlannObjectData sp) {
        this.cp = cp;
        this.sp = sp;

    }

    public void kezel() {

        //control panel beállításai
        //tervek hozzáadás letiltása a  2 es esetben műszakvezető
        if (Variables.jogosultsag == 2) {
            cp.jLabel2.setEnabled(false);
            //setplannobject beállításai
            //ha műszakvezető akkor ne lehessen szerkeszteni a pn-t
            sp.jTextField1.setEditable(false);
            //a job ot
            sp.jTextField2.setEditable(false);
            //a ws-t
            sp.jTextField8.setEditable(false);
            //a tervet
            sp.jTextField3.setEditable(false);
            //a planner kommentet
            sp.jTextField5.setEditable(false);
            //a termelés komment
            sp.jTextField7.setEditable(true);
            //a gyártási idő
            sp.jTextField6.setEditable(false);
            //a mérnöki csúszka
            sp.jSlider1.setEnabled(false);
            //a tény szerkesztése
            sp.jTextField4.setEditable(true);
            //a set gomb
            sp.jButton1.setEnabled(true);

            //a plannobjectek menuitemek letiltasa
            for (int i = 0; i < MainWindow.jTabbedPane1.getTabCount(); i++) {

                BeSheet b = (BeSheet) MainWindow.jTabbedPane1.getComponentAt(i);
                for (int n = 0; n < b.jPanel1.getComponentCount(); n++) {

                    if (b.jPanel1.getComponent(n) instanceof PlannObject) {
                        PlannObject p = (PlannObject) b.jPanel1.getComponent(n);
                        //terv mentése
                        p.getComponentPopupMenu().getComponent(0).setEnabled(true);
                        //adatok módosítása
                        p.getComponentPopupMenu().getComponent(2).setEnabled(true);
                        //áttervez
                        p.getComponentPopupMenu().getComponent(3).setEnabled(false);
                        //sfdc lekérése
                        p.getComponentPopupMenu().getComponent(4).setEnabled(true);
                        //terv törlése
                        p.getComponentPopupMenu().getComponent(5).setEnabled(false);
                        //terv másolása
                        p.getComponentPopupMenu().getComponent(6).setEnabled(false);

                    }

                }

            }

        } //sima megtekintő
        else if (Variables.jogosultsag == 0) {
//semmit nem csinálhat
            cp.jLabel2.setEnabled(false);
            //setplannobject beállításai
            //ha műszakvezető akkor ne lehessen szerkeszteni a pn-t
            sp.jTextField1.setEditable(false);
            //a job ot
            sp.jTextField2.setEditable(false);
            //a ws-t
            sp.jTextField8.setEditable(false);
            //a tervet
            sp.jTextField3.setEditable(false);
            //a planner kommentet
            sp.jTextField5.setEditable(false);
            //a termelés komment
            sp.jTextField7.setEditable(false);
            //a gyártási idő
            sp.jTextField6.setEditable(false);
            //a mérnöki csúszka
            sp.jSlider1.setEnabled(false);
            //a tény szerkesztése
            sp.jTextField4.setEditable(false);
            //a set gomb
            sp.jButton1.setEnabled(false);
            
            //a plannobjectek menuitemek letiltasa
            for (int i = 0; i < MainWindow.jTabbedPane1.getTabCount(); i++) {

                BeSheet b = (BeSheet) MainWindow.jTabbedPane1.getComponentAt(i);
                for (int n = 0; n < b.jPanel1.getComponentCount(); n++) {

                    if (b.jPanel1.getComponent(n) instanceof PlannObject) {
                        PlannObject p = (PlannObject) b.jPanel1.getComponent(n);
                        //terv mentése
                        p.getComponentPopupMenu().getComponent(0).setEnabled(false);
                        //adatok módosítása
                        p.getComponentPopupMenu().getComponent(2).setEnabled(true);
                        //áttervez
                        p.getComponentPopupMenu().getComponent(3).setEnabled(false);
                        //sfdc lekérése
                        p.getComponentPopupMenu().getComponent(4).setEnabled(false);
                        //terv törlése
                        p.getComponentPopupMenu().getComponent(5).setEnabled(false);
                        //terv másolása
                        p.getComponentPopupMenu().getComponent(6).setEnabled(false);

                    }

                }

            }
//ha planner
        } else {
            cp.jLabel2.setEnabled(true);
            sp.jTextField1.setEditable(false);
            sp.jTextField2.setEditable(true);
            sp.jTextField8.setEditable(false);
            sp.jTextField3.setEditable(true);
            sp.jTextField5.setEditable(true);
            sp.jTextField7.setEditable(false);
            sp.jTextField6.setEditable(true);
            sp.jSlider1.setEnabled(true);
            sp.jTextField4.setEditable(true);
            //a set gomb
            sp.jButton1.setEnabled(true);
            //a plannobjectek menuitemek letiltasa
            for (int i = 0; i < MainWindow.jTabbedPane1.getTabCount(); i++) {

                BeSheet b = (BeSheet) MainWindow.jTabbedPane1.getComponentAt(i);
                for (int n = 0; n < b.jPanel1.getComponentCount(); n++) {

                    if (b.jPanel1.getComponent(n) instanceof PlannObject) {
                        PlannObject p = (PlannObject) b.jPanel1.getComponent(n);
                        //terv mentése
                        p.getComponentPopupMenu().getComponent(0).setEnabled(true);
                        //adatok módosítása
                        p.getComponentPopupMenu().getComponent(2).setEnabled(true);
                        //áttervez
                        p.getComponentPopupMenu().getComponent(3).setEnabled(true);
                        //sfdc lekérése
                        p.getComponentPopupMenu().getComponent(4).setEnabled(true);
                        //terv törlése
                        p.getComponentPopupMenu().getComponent(5).setEnabled(true);
                        //terv másolása
                        p.getComponentPopupMenu().getComponent(6).setEnabled(true);

                    }

                }

            }
        }

    }

}
