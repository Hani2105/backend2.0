/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author gabor_hanacsek
 */
public class PlanConnect {

    public ResultSet rs;
    public Connection conn;
    public String driver = Variables.planndriver;
    public String url = Variables.plannurl;
    public String username = Variables.plannusername;
    public String password = Variables.plannpassword;
    public Statement st;

    public PlanConnect() throws SQLException {
        conn = (Connection) DriverManager.getConnection(url, username, password);
        st = conn.createStatement();
    }

    public Object lekerdez(String startQuery) throws SQLException, ClassNotFoundException {

        rs = st.executeQuery(startQuery);
        return rs;

    }

    public void feltolt(String query) throws SQLException {

        st.executeUpdate(query);

    }

    public void kinyir() {

        try {

            if (rs != null) {
                this.rs.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Starter.e.sendMessage(e);
        }
        try {
            if (conn != null) {

                this.conn.close();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            Starter.e.sendMessage(ex);
        }

    }

}
