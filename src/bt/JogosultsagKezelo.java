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
        if (Variables.planner == 2) {
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
            //terv törlése
            sp.jButton2.setEnabled(false);
            //a tény szerkesztése
            sp.jTextField4.setEditable(true);
            //az áttervezés letiltása
            
        } //sima megtekintő
        else if (Variables.planner == 0) {
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
            //terv törlése
            sp.jButton2.setEnabled(false);
            //a tény szerkesztése
            sp.jTextField4.setEditable(false);

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
            sp.jButton2.setEnabled(true);
            sp.jTextField4.setEditable(true);
        }

    }

}
