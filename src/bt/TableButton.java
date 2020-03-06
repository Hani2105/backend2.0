/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 *
 * @author gabor_hanacsek
 */
public class TableButton extends JButton {

    public TableButton() {
        ImageIcon img = new javax.swing.ImageIcon(getClass().getResource("/pictures/adatment.png"));
        this.setIcon(img);
       
    }

}
