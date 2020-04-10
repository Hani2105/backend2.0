/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bt;


import java.awt.Color;
import java.util.ArrayList;
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
    public static ArrayList<String[]> pnkomment = new ArrayList<>();
//a felhasználó valaki_valaki
    public static String user = "";
//a job státusainak lehetőségei
    public static enum status {
        NotReleased, Released, Complete,Skeleton,NotExists
    };
//mit rajzoljon a screensaver
    public static enum background {
        sfdc, tervmentese, oldalmenu, controlpanelkulso, controlpanelbelso, setplannobjectback
    };
    public static enum viewports {
        plannpanel, datapanel
    };
//a felhasználó pozíciója, planner vagy műszakvezető vagy csak megtekintés, 1,2,0
    public static int planner = 0;


}
