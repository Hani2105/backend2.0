/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bt;

import static bt.MainWindow.jList1;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gabor_hanacsek
 */
public class SessionObject implements Serializable {
    

   
    public ArrayList<String> Cellak;
    
    public ArrayList<String> getCellak() {
        return Cellak;
    }
    
    public void setCellak(ArrayList<String> Cellak) {
        this.Cellak = Cellak;
    }
    

    
}
