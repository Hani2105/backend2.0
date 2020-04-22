/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bt;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;

/**
 *
 * @author gabor_hanacsek
 */
public class ErrorLogger {

    public static void sendMessage(Exception e) {

        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        String exceptionAsString = sw.toString();
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());

        Thread t = new Levelkuldes("BT hiba! " + timeStamp, System.getProperty("user.name") +" " + exceptionAsString, "gabor.hanacsek@sanmina.com", "BTLogger@sanmina.com");
        t.start();

    }

}
