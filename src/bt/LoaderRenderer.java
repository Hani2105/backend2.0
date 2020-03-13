/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bt;

import java.awt.Color;
import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author gabor_hanacsek
 */
public class LoaderRenderer extends DefaultTableCellRenderer {

    ControlPanel lp;

    public LoaderRenderer(ControlPanel lp) {
        this.lp = lp;
    }

    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        JLabel c = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        JButton b = new TableButton();

        try {
            c.setBackground(new Color(0, 0, 0, 0));
            c.setIcon(null);

            if (column == 1 && !table.getValueAt(row, 1).equals("") && !table.getValueAt(row, 2).equals("")) {

                for (int i = 0; i < lp.adatok.size(); i++) {
//megnézzük, hogy ugyan ez a felállás létezik e az adatok tömbben
                    if (lp.adatok.get(i)[0].equals(table.getValueAt(row, 1)) && lp.adatok.get(i)[1].equals(table.getValueAt(row, 2))) {

                        c.setBackground(Color.green);
                        table.setValueAt(lp.adatok.get(i)[2], row, 5);
                        break;
                    } else {
                        table.setValueAt(null, row, 5);
                    }

                }
//beállítjuk a pn kommentet is
                for (int i = 0; i < Variables.pnkomment.size(); i++) {

                    if (table.getValueAt(row, 1).equals(Variables.pnkomment.get(i)[0])) {

                        table.setValueAt(Variables.pnkomment.get(i)[1], row, 6);
                        break;
                    }

                }

            } //ha a 6. columban vagyunk rajzoljunk mentés gombot            
            else if (column == 7) {

//                c.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pictures/adatment.png")));
                return b;

            }
        } catch (Exception e) {
        }

        return c;
    }

}
