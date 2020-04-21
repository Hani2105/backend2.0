/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bt;

import java.io.IOException;
import java.sql.SQLException;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author gabor_hanacsek
 */
public class Starter {

    public static ErrorLogger e = new ErrorLogger();

    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException, UnsupportedLookAndFeelException, InterruptedException {
        new IniKezel().iniOlvas();
        MainWindow m = new MainWindow();
        m.setVisible(true);

    }

}
