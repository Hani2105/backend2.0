/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bt;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gabor_hanacsek
 */
public class LoginZaro implements Runnable {
    
    LoginScreen ls;
    int alszik;
    MainWindow m;
    
    LoginZaro(LoginScreen ls, int alszik, MainWindow m) {
        
        this.ls = ls;
        this.alszik = alszik;
        this.m = m;
        
    }
    
    @Override
    public void run() {
        try {
            Thread.sleep(alszik);
            ls.setVisible(false);
            m.cp.jTabbedPane1.setSelectedIndex(0);
            
        } catch (InterruptedException e) {
           e.printStackTrace();
           Starter.e.sendMessage(e);
        }
        
    }
    
}
