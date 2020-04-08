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
    public static Color plannObjectPopupColor = new Color(16777195);
//a statisztikai vonalak szinei       
    public static Color zold = new Color(11468631);
    public static Color piros = new Color(16544370);
//a tab szinei
    public static Color tabcolor = new Color(15775206);
    public static Color selectedtabcolor = new Color(7191285);
    public static ArrayList<String[]> pnkomment = new ArrayList<>();

    
    
    
//a felhasználó valaki_valaki
    public static String user = "";
//a job státusainak lehetőségei
    public static enum status {
        NotReleased, Released, Complete,Skeleton,NotExists
    };
//mit rajzoljon a screensaver
    public static enum screensaver {
        sfdc, tervmentese
    };
    public static enum viewports {
        plannpanel, datapanel
    };
//a felhasználó pozíciója, planner vagy műszakvezető vagy csak megtekintés, 1,2,0
    public static int planner = 0;


}
