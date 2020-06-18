/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bt;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Timer;
import javax.swing.ImageIcon;

/**
 *
 * @author gabor_hanacsek
 */
public class Variables {

//a popup menu szine
    public static Color plannObjectPopupColor;
//a statisztikai vonalak szinei       
    public static Color zold;
    public static Color piros;
//a tab szinei
    public static Color tabcolor;
    public static Color selectedtabcolor;
//a megvalósult terv pipájának színe
    public static Color pipacolor;
    
    public static ArrayList<String[]> pnkomment = new ArrayList<>();
//a felhasználó valaki_valaki
    public static String user = "";
//a job státusainak lehetőségei

    public static enum status {
        NotReleased, Released, Complete, Skeleton, NotExists
    };
//mit rajzoljon a screensaver

    public static enum background {
        sfdc, tervmentese, oldalmenu, controlpanelkulso, controlpanelbelso, setplannobjectback
    };

    public static enum viewports {
        plannpanel, datapanel
    };
//a statisztika enumja
    
    public static enum statisztika{
       heti
    };

//a felhasználó pozíciója, planner vagy műszakvezető vagy csak megtekintés, 1,2,0
    public static int jogosultsag = 0;
//a planner adatbázis változói
    public static String plannurl;
    public static String plannusername;
    public static String plannpassword;
    public static String planndriver;
//az anyaghiany legordulo listaja
    public static String anyaghianylegordulo;
//az allasido legorduloje
    public static String allasidolegordulo;
//  a program verzió száma
    public static double version = 1.9;
//a timer
    public static Timer timer = new Timer();


}
