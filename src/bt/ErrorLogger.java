/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bt;

/**
 *
 * @author gabor_hanacsek
 */
public class ErrorLogger {

    public static void sendMessage(Exception e) {

        Thread t = new Levelkuldes("BT hiba!", e.getMessage(), "gabor.hanacsek@sanmina.com", "BTLogger@sanmina.com");
        t.start();

    }

}
